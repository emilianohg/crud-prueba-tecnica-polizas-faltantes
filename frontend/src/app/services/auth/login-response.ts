import { BaseResponse } from "src/app/models/base-response";

export interface LoginResponseData {
    token: string,
    type: "Bearer",
    email: string,
    idUsuario: number,
    tiempoExpiracion: string,
};

export type LoginResponse = BaseResponse<LoginResponseData>;