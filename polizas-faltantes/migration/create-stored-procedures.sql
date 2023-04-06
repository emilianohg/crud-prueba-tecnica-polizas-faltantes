
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
        WHERE CONCAT(e.Nombre, ' ', e.Apellido) LIKE '%' + @buscar + '%'
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
    ORDER BY Polizas.IdPoliza
    OFFSET @offset ROWS
    FETCH NEXT @limit ROWS ONLY;

	RETURN;
END


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

SELECT * FROM Inventario;
SELECT * FROM Polizas;

EXEC Polizas_Registrar 1, 1, 'SKU1', 111111, 'Prueba';




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

SELECT * FROM Polizas;

EXEC Polizas_Eliminar 3, "Error humano";

SELECT * FROM Polizas;
SELECT * FROM Inventario;


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

                -- Ejemplo:
                -- Cantidad anterior: 500
                -- Cantidad nueva: 700
                -- Cantidad a descontar: 700 - 500 = 200
                -- Ejemplo:
                -- Cantidad anterior: 500
                -- Cantidad nueva: 300
                -- Cantidad a descontar: 300 - 500 = -200
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

SELECT * FROM Polizas;
SELECT * FROM Inventario;

EXEC Polizas_Actualizar 1, 1, 1, 'SKU2', 100, 'Actualizar';



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

EXEC Polizas_Consultar 1



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


EXEC Usuarios_ConsultarPorEmail 'admin@example.com'

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


EXEC Usuarios_Registrar 'example@email.com', '123456', 'Nombre', 'Apellido', 1


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


DECLARE @totalEmpleados INT;
EXEC Inventario_Listar 0, 10, "Laptop ACER 123", @totalEmpleados OUTPUT;
SELECT @totalEmpleados;