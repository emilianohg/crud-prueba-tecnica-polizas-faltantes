
DROP PROCEDURE IF EXISTS Empleados_Listar;
GO
CREATE PROCEDURE Empleados_Listar
    @offset INT = 0,
    @limit INT = 10,
    @buscar VARCHAR(250) = NULL,
	@total INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF @buscar IS NOT NULL
    BEGIN

        SELECT @total = COUNT(*)
        FROM Empleados
        WHERE CONCAT(Empleados.Nombre, ' ', Empleados.Apellido) LIKE '%' + @buscar + '%';

        SELECT
            e.IdEmpleado,
            e.Nombre,
            e.Apellido,
            e.IdPuesto,
            p.Nombre AS Puesto
        FROM Empleados e
        INNER JOIN Puestos p
            ON e.IdPuesto = p.IdPuesto
        WHERE NombreCompleto LIKE @buscar + '%'
        ORDER BY e.IdEmpleado
        OFFSET @offset ROWS
        FETCH NEXT @limit ROWS ONLY;

        RETURN;
    END

    SELECT @total = COUNT(*) FROM Empleados;

    SELECT
        e.IdEmpleado,
        e.Nombre,
        e.Apellido,
        e.IdPuesto,
        p.Nombre AS Puesto
    FROM Empleados e
    INNER JOIN Puestos p
        ON e.IdPuesto = p.IdPuesto
    ORDER BY e.IdEmpleado
    OFFSET @offset ROWS
    FETCH NEXT @limit ROWS ONLY;

END

DECLARE @totalEmpleados INT;
EXEC Empleados_Listar 0, 10, "Emil", @totalEmpleados OUTPUT;
SELECT @totalEmpleados;