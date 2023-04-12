DROP PROCEDURE IF EXISTS Polizas_Eliminar;
GO
CREATE PROCEDURE Polizas_Eliminar
    @IdPoliza INT,
    @MotivoEliminacion TEXT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @Cantidad INT;
    DECLARE @SKU VARCHAR(100);

    -- Si la poliza ya esta eliminada, no se hace nada
    IF EXISTS (SELECT * FROM Polizas WHERE IdPoliza = @IdPoliza AND FechaEliminacion IS NOT NULL)
    BEGIN
        THROW 50004, 'La poliza ya esta eliminada', 1;
        RETURN;
    END

    BEGIN TRY
        BEGIN TRANSACTION;

            UPDATE Polizas SET
                MotivoEliminacion = @MotivoEliminacion,
                FechaEliminacion = CURRENT_TIMESTAMP
            WHERE IdPoliza = @IdPoliza;

            SELECT
                @Cantidad = Cantidad,
                @SKU = SKU
            FROM Polizas
            WHERE IdPoliza = @IdPoliza;

            UPDATE Inventario SET Cantidad = Cantidad + @Cantidad WHERE SKU = @SKU;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH

    EXEC Polizas_Consultar @IdPoliza;
   
END

GO

EXEC Polizas_Eliminar 2, "Error humano";
