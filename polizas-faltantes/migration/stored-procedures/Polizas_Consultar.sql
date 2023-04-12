DROP PROCEDURE IF EXISTS Polizas_Consultar;
GO
CREATE PROCEDURE Polizas_Consultar
    @IdPoliza INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        Polizas.IdPoliza,
        Polizas.IdUsuario,
        Polizas.IdEmpleadoGenero,
        Empleados.Nombre as NombreEmpleadoGenero,
        Empleados.Apellido as ApellidoEmpleadoGenero,
        Empleados.IdPuesto as IdPuestoEmpleadoGenero,
        Puestos.Nombre as PuestoEmpleadoGenero,
        Polizas.SKU,
        Inventario.Nombre as NombreProducto,
        Polizas.Cantidad,
        Polizas.Observaciones,
        Polizas.Fecha,
        Polizas.MotivoEliminacion,
        Polizas.FechaEliminacion
    FROM Polizas
    INNER JOIN Empleados
        ON Empleados.IdEmpleado = Polizas.IdEmpleadoGenero
    INNER JOIN Puestos
        ON Empleados.IdPuesto = Puestos.IdPuesto
    INNER JOIN Inventario
        on Polizas.SKU = Inventario.SKU
    WHERE Polizas.IdPoliza = @IdPoliza;
   
END

GO

EXEC Polizas_Consultar 1
