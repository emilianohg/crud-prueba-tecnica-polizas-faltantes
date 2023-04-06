import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BaseResponse } from "src/app/models/base-response";
import { Empleado } from "src/app/models/empleado";
import { Pagination } from "src/app/models/pagination";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
  })
  export class EmpleadosService {
  
    constructor(
      private http: HttpClient,
    ) { }
  
    getAll({page = 1, limit = 10, search}: {page: number, limit: number, search?: string}) {

        const params: any = {
            page: page.toString(),
            limit: limit.toString(),
        };

        if (search) {
            params['search'] = search;
        }

        return this.http.get<BaseResponse<Pagination<Empleado>>>(`${environment.apiUrl}/empleados`, {
            params,
        });
    }

}