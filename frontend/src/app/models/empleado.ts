import { Puesto } from "./puesto";

export interface Empleado {
    idEmpleado: number,
    nombre: String,
    apellido: String,
    puesto: Puesto,
}