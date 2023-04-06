import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BaseResponse } from '../../models/base-response';
import { Pagination } from '../../models/pagination';
import { Poliza } from '../../models/poliza';
import { PolizaRequest } from './poliza-request';

@Injectable({
  providedIn: 'root'
})
export class PolizasService {

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

    return this.http.get<BaseResponse<Pagination<Poliza>>>(`${environment.apiUrl}/polizas`, { params });
  }

  findById(id: number) {
    return this.http.get<BaseResponse<Poliza>>(`${environment.apiUrl}/polizas/${id}`);
  }

  create(poliza: PolizaRequest) {
    return this.http.post<BaseResponse<Poliza>>(`${environment.apiUrl}/polizas`, poliza);
  }

  update(id: number, poliza: PolizaRequest) {
    return this.http.put<BaseResponse<Poliza>>(`${environment.apiUrl}/polizas/${id}`, poliza);
  }

  delete(id: number, motivoEliminacion: string) {
    return this.http.put<BaseResponse<Poliza>>(`${environment.apiUrl}/polizas/${id}/eliminar`, {
      motivoEliminacion: motivoEliminacion
    });
  }
}
