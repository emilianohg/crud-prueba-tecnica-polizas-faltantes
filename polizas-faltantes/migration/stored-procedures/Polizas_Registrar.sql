DROP PROCEDURE IF EXISTS Polizas_Registrar;
GO
CREATE PROCEDURE Polizas_Registrar
    @IdUsuario INT,
    @IdEmpleadoGenero INT,
    @SKU VARCHAR(100),
    @Cantidad INT,
    @Observaciones TEXT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @IdPoliza INT;

    IF @Cantidad > (SELECT Cantidad FROM Inventario WITH (updlock)  WHERE SKU = @SKU)
    BEGIN
        THROW 50001, 'No hay suficiente cantidad en inventario', 1;
    END

    BEGIN TRY
        BEGIN TRANSACTION;

            INSERT INTO Polizas (IdUsuario, IdEmpleadoGenero, SKU, Cantidad, Observaciones)
            VALUES (@IdUsuario, @IdEmpleadoGenero, @SKU, @Cantidad, @Observaciones);

            SELECT @IdPoliza = SCOPE_IDENTITY();

            UPDATE Inventario SET Cantidad = Cantidad - @Cantidad WHERE SKU = @SKU;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
    
    EXEC Polizas_Consultar @IdPoliza;
   
END

GO

EXEC Polizas_Registrar 1, 1, 'SKU1', 111111, 'Prueba';
