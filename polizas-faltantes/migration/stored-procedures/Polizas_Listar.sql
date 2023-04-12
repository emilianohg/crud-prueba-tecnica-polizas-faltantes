DROP PROCEDURE IF EXISTS Polizas_Listar;
GO
CREATE PROCEDURE Polizas_Listar
    @limit INT,
    @offset INT,
	@total INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT @total = COUNT(*) FROM Polizas;

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
        ON Polizas.SKU = Inventario.SKU
    ORDER BY Polizas.IdPoliza DESC
    OFFSET @offset ROWS
    FETCH NEXT @limit ROWS ONLY;

	RETURN;
END