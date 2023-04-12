DROP TABLE Polizas;
DROP TABLE Inventario;
DROP TABLE Usuarios;
DROP TABLE Puestos;
DROP TABLE Empleados;

CREATE TABLE Puestos (
	IdPuesto INT IDENTITY PRIMARY KEY,
	Nombre VARCHAR(100) NOT NULL
);

GO

CREATE TABLE Empleados (
	IdEmpleado INT IDENTITY PRIMARY KEY,
	Nombre VARCHAR(250) NOT NULL,
	Apellido VARCHAR(250) NOT NULL,
	IdPuesto INT NOT NULL FOREIGN KEY REFERENCES Puestos(IdPuesto)
);

ALTER TABLE Empleados ADD NombreCompleto AS CONCAT(Nombre, ' ', Apellido) PERSISTED;

CREATE INDEX idx_Empleados_NombreCompleto ON Empleados (NombreCompleto);

GO

CREATE INDEX idx_Empleados_Apellido_Nombre ON Empleados (Apellido, Nombre);

GO

CREATE TABLE Usuarios (
	IdUsuario INT IDENTITY PRIMARY KEY,
	Email VARCHAR(250) NOT NULL UNIQUE,
	Pass VARCHAR(250) NOT NULL,
	IdEmpleado INT NOT NULL FOREIGN KEY REFERENCES Empleados(IdEmpleado),
);

GO

CREATE INDEX idx_Usuarios_Email ON Usuarios (Email);


GO

CREATE TABLE Inventario (
	SKU VARCHAR(100) PRIMARY KEY,
	Nombre VARCHAR(250) NOT NULL,
	Cantidad INT NOT NULL
);

GO

CREATE INDEX idx_Inventario_Nombre ON Inventario (Nombre);

GO

CREATE TABLE Polizas (
	IdPoliza INT IDENTITY PRIMARY KEY,
	IdUsuario INT NOT NULL FOREIGN KEY REFERENCES Usuarios(IdUsuario),
	IdEmpleadoGenero INT NOT NULL FOREIGN KEY REFERENCES Empleados(IdEmpleado),
	SKU VARCHAR(100) NOT NULL FOREIGN KEY REFERENCES Inventario(SKU),
	Cantidad INT NOT NULL,
	Observaciones TEXT,
	Fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
	MotivoEliminacion TEXT,
	FechaEliminacion DATETIME
);

GO

CREATE INDEX idx_Polizas_SKU ON Polizas (SKU);
CREATE INDEX idx_Polizas_Fecha ON Polizas (Fecha);