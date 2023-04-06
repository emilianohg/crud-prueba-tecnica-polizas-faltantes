import { Empleado } from "./empleado";
import { ProductoInventario } from "./producto-inventario";

export interface Poliza {
    idPoliza: number,
    idUsuario: number,
    idEmpleadoGenero: number,
    empleadoGenero: Empleado,
    sku: String,
    productoInventario: ProductoInventario,
    cantidad: number,
    observaciones: String,
    fecha: String,
    motivoEliminacion: String,
    fechaEliminacion: Date,
}