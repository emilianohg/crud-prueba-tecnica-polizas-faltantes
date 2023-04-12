DROP PROCEDURE IF EXISTS Polizas_Actualizar;
GO
CREATE PROCEDURE Polizas_Actualizar
    @IdPoliza INT,
    @IdUsuario INT,
    @IdEmpleadoGenero INT,
    @SKU VARCHAR(100),
    @Cantidad INT,
    @Observaciones TEXT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @CantidadAnterior INT;
    DECLARE @SKUAnterior VARCHAR(100);
    DECLARE @EstaEliminado BIT;

    BEGIN TRY
        BEGIN TRANSACTION;

            SELECT
                @CantidadAnterior = Cantidad,
                @SKUAnterior = SKU,
                @EstaEliminado = IIF(FechaEliminacion IS NOT NULL, 1, 0)
            FROM Polizas
            WITH (updlock)
            WHERE IdPoliza = @IdPoliza;

            IF @EstaEliminado = 1
            BEGIN
                THROW 50002, 'No se puede actualizar una poliza eliminada', 1;
            END

            UPDATE Polizas SET
                IdUsuario = @IdUsuario,
                IdEmpleadoGenero = @IdEmpleadoGenero,
                SKU = @SKU,
                Cantidad = @Cantidad,
                Observaciones = @Observaciones
            WHERE IdPoliza = @IdPoliza;

            -- Si el producto es diferente
            IF @SKUAnterior <> @SKU 
            BEGIN
                -- Regresamos la cantidad del producto anterior al inventario
                UPDATE Inventario SET Cantidad = Cantidad + @CantidadAnterior WHERE SKU = @SKUAnterior;

                -- Verificamos que haya suficiente cantidad del producto nuevo para descontar
                IF @Cantidad > (SELECT Cantidad FROM Inventario WITH (updlock) WHERE SKU = @SKU)
                BEGIN
                    THROW 50001, 'No hay suficiente cantidad en inventario', 1;
                END

                -- Descontamos la cantidad del producto nuevo al inventario
                UPDATE Inventario SET Cantidad = Cantidad - @Cantidad WHERE SKU = @SKU;
            END
            -- Si el producto es igual pero la cantidad es diferente
            ELSE IF @SKUAnterior = @SKU and @CantidadAnterior <> @Cantidad
            BEGIN

                -- Verificamos que haya suficiente cantidad del producto nuevo para descontar
                IF (@Cantidad - @CantidadAnterior) > (SELECT Cantidad FROM Inventario WITH (updlock) WHERE SKU = @SKU)
                BEGIN
                    THROW 50001, 'No hay suficiente cantidad en inventario', 1;
                END

                -- Descontamos la cantidad del producto al inventario y regresamos la cantidad anterior
                UPDATE Inventario SET Cantidad = Cantidad - (@Cantidad - @CantidadAnterior) WHERE SKU = @SKU;
            END

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH

    EXEC Polizas_Consultar @IdPoliza;
   
END

GO

EXEC Polizas_Actualizar 1, 1, 1, 'SKU2', 100, 'Actualizar';
