import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Poliza } from 'src/app/models/poliza';
import { PolizasService } from 'src/app/services/polizas/polizas.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-show-poliza',
  templateUrl: './show-poliza.component.html',
  styleUrls: ['./show-poliza.component.scss']
})
export class ShowPolizaComponent {

  poliza: Poliza | undefined;

  constructor(
    private fb: FormBuilder,
    private polizasService: PolizasService,
    private router: Router,
    private route: ActivatedRoute,
  ) {

    this.route.params.subscribe({
      next: (params) => {
        this.polizasService.findById(params['id']).subscribe({
          next: (response) => {

            this.poliza = response.data;

          },
          error: (error) => {
            console.error(error);
            Swal.fire({
              icon: 'error',
              title: error.error.data.message,
            });
          }
        });
      }
    });
  }

  get nombreCompleto() {
    return `${this.poliza!.empleadoGenero.nombre} ${this.poliza!.empleadoGenero.apellido}`;
  }

  get producto() {
    return `${this.poliza!.productoInventario.sku} - ${this.poliza!.productoInventario.nombre}`;
  }

  get isDeleted() {
    return this.poliza!.fechaEliminacion !== null;
  }
}
