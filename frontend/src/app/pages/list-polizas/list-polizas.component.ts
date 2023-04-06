import { Component } from '@angular/core';
import { PolizasService } from 'src/app/services/polizas/polizas.service';
import { Pagination } from "src/app/models/pagination";
import { Poliza } from 'src/app/models/poliza';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmAlert } from 'src/app/decorators/confirm-alert';
import Swal from 'sweetalert2';
import { BaseResponse } from 'src/app/models/base-response';
import Toast from 'src/app/utils/toast';

@Component({
  selector: 'app-list-polizas',
  templateUrl: './list-polizas.component.html',
  styleUrls: ['./list-polizas.component.scss']
})
export class ListPolizasComponent {

  polizas: Pagination<Poliza> | undefined;

  filters : {
    page: number,
    limit: number,
    search?: string,
  } = {
    page: 1,
    limit: 10,
  };

  search: Subject<string> = new Subject();
  _searchText: string = '';
  
  constructor(
    private polizasService: PolizasService,
    private route: ActivatedRoute,
    private router: Router,
  ) {

    const queryParams = this.route.snapshot.queryParams;

    this._searchText = queryParams['search'];

    this.filters = {
      ...this.filters,
      ...queryParams,
    };

    this.loadPolizas();

    this.search.asObservable().pipe(
      debounceTime(500),
      distinctUntilChanged(),
    ).subscribe((search) => {
      this.filters.search = search;
      this.loadPolizas();
    });
  }

  loadPolizas() {
    if (!this.filters.search) {
      delete this.filters.search;
    }

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: this.filters,
    });

    if (this.filters.search && !isNaN(+this.filters.search)) {
      this.polizasService.findById(+this.filters.search).subscribe({
        next: (response: BaseResponse<Poliza>) => {
          const poliza = response.data;
          this.polizas = {
            records: [poliza],
            totalPages: 1,
            currentPage: 1,
            totalRecords: 1,
            page: 1,
            limit: 10,
          };
        },
        error: (error) => {
          Toast.fire({
            icon: 'error',
            title: error.error.data.message,
          });
        }
      });

      return;
    }

    this.polizasService.getAll(this.filters).subscribe((response) => {
      this.polizas = response.data;
    });
  }

  changePage(page: number) {
    this.filters.page = page;
    this.loadPolizas();
  }

  changeSearch(event: any) {
    const search = event.target.value;
    this.search.next(search);
  }

  edit(poliza: Poliza) {
    this.router.navigateByUrl(`/polizas/${poliza.idPoliza}/edit`);
  }

  delete(poliza: Poliza) {
    this.router.navigateByUrl(`/polizas/${poliza.idPoliza}/delete`);
  }

  show(poliza: Poliza) {
    this.router.navigateByUrl(`/polizas/${poliza.idPoliza}`);
  }

}
