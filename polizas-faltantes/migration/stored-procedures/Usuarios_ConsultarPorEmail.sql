DROP PROCEDURE IF EXISTS Usuarios_ConsultarPorEmail;
GO
CREATE PROCEDURE Usuarios_ConsultarPorEmail
    @Email VARCHAR(250)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        Usuarios.IdUsuario,
        Usuarios.Email,
        Usuarios.Pass,
        Usuarios.IdEmpleado,
        Empleados.Nombre as NombreEmpleado,
        Empleados.Apellido as ApellidoEmpleado,
        Empleados.IdPuesto as IdPuestoEmpleado,
        Puestos.Nombre as NombrePuestoEmpleado
    FROM Usuarios
    INNER JOIN Empleados
        ON Empleados.IdEmpleado = Usuarios.IdEmpleado
    INNER JOIN Puestos
        ON Puestos.IdPuesto = Empleados.IdPuesto
    WHERE Usuarios.Email = @Email;
END

GO

EXEC Usuarios_ConsultarPorEmail 'admin@example.com';