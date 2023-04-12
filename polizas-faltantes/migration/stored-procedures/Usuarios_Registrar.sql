DROP PROCEDURE IF EXISTS Usuarios_Registrar;
GO
CREATE PROCEDURE Usuarios_Registrar
    @Email VARCHAR(250),
    @Pass VARCHAR(250),
    @Nombre VARCHAR(250),
    @Apellido VARCHAR(250),
    @IdPuesto INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @IdEmpleado INT;

    BEGIN TRY
        BEGIN TRANSACTION;

            INSERT INTO Empleados (
                Nombre,
                Apellido,
                IdPuesto
            ) VALUES (
                @Nombre,
                @Apellido,
                @IdPuesto
            );

            SET @IdEmpleado = SCOPE_IDENTITY();

            INSERT INTO Usuarios (
                Email,
                Pass,
                IdEmpleado
            ) VALUES (
                @Email,
                @Pass,
                @IdEmpleado
            );

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH

    EXEC Usuarios_ConsultarPorEmail @Email;
END

GO

EXEC Usuarios_Registrar 'example@email.com', '123456', 'Nombre', 'Apellido', 1