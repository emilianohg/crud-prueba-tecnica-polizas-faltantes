DROP PROCEDURE IF EXISTS Inventario_Listar;
GO
CREATE PROCEDURE Inventario_Listar
    @offset INT = 0,
    @limit INT = 10,
    @buscar VARCHAR(250) = NULL,
	@total INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF @buscar IS NOT NULL
    BEGIN
        SELECT @total = COUNT(*) FROM Inventario WHERE Inventario.Nombre LIKE @buscar + '%';

        SELECT
            Inventario.SKU,
            Inventario.Nombre,
            Inventario.Cantidad
        FROM Inventario
        WHERE Inventario.Nombre LIKE @buscar + '%'
        ORDER BY Inventario.Nombre
        OFFSET @offset ROWS
        FETCH NEXT @limit ROWS ONLY;

        RETURN;
    END

    SELECT @total = COUNT(*) FROM Inventario;

    SELECT
        Inventario.SKU,
        Inventario.Nombre,
        Inventario.Cantidad
    FROM Inventario
    ORDER BY Inventario.Nombre
    OFFSET @offset ROWS
    FETCH NEXT @limit ROWS ONLY;
    
END

GO

DECLARE @total INT;
EXEC Inventario_Listar 0, 10, "Laptop ACER 123", @total OUTPUT;
SELECT @total;