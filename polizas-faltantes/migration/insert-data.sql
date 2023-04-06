INSERT INTO Puestos
(Nombre)
VALUES
('Administrador'),
('Responsable de Almacen');

INSERT INTO Empleados
(Nombre, Apellido, IdPuesto)
VALUES
('Emiliano', 'Hernandez Guerrero', 1),
('John', 'Doe', 2),
('Jane', 'Doe', 2),
('Empleado 4', 'Doe', 2),
('Empleado 5', 'Doe', 2),
('Empleado 6', 'Doe', 2),
('Empleado 7', 'Doe', 2),
('Empleado 8', 'Doe', 2),
('Empleado 9', 'Doe', 2),
('Empleado 10', 'Doe', 2),
('Empleado 11', 'Doe', 2),
('Empleado 12', 'Doe', 2),
('Empleado 13', 'Doe', 2),
('Empleado 14', 'Doe', 2);

INSERT INTO Usuarios
(Email, Pass, IdEmpleado)
VALUES
('admin@example.com', 'password', 1);

INSERT INTO Inventario
(SKU, Nombre, Cantidad)
VALUES
('SKU1', 'Laptop ACER 123', 1000),
('SKU2', 'Laptop ACER 456', 2000),
('SKU3', 'Laptop ACER 789', 500);


INSERT INTO Polizas
(IdUsuario, IdEmpleadoGenero, SKU, Cantidad)
VALUES
(1, 2, 'SKU3', 100),
(1, 2, 'SKU2', 500);

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