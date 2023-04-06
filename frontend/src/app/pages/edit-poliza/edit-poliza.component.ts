import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Poliza } from 'src/app/models/poliza';
import { PolizasService } from 'src/app/services/polizas/polizas.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-poliza',
  templateUrl: './edit-poliza.component.html',
  styleUrls: ['./edit-poliza.component.scss']
})
export class EditPolizaComponent {
  
  form: FormGroup;

  poliza: Poliza | undefined;

  constructor(
    private fb: FormBuilder,
    private polizasService: PolizasService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.form = this.fb.group({
      empleadoGenero: [null, [Validators.required]],
      producto: [null, [Validators.required]],
      cantidad: [null, [Validators.required, Validators.min(1)]],
      observaciones: [null, [Validators.required]],
    });

    this.route.params.subscribe({
      next: (params) => {
        this.polizasService.findById(params['id']).subscribe({
          next: (response) => {

            this.poliza = response.data;

            const data = {
              producto: response.data.productoInventario,
              empleadoGenero: response.data.empleadoGenero,
              cantidad: response.data.cantidad,
              observaciones: response.data.observaciones,
            };

            this.form.patchValue(data);
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

  save() {

    this.form.markAllAsTouched();
    
    if (this.form.invalid) {
      return;
    }

    const data = this.form.value;

    this.polizasService.update(
      this.poliza!.idPoliza,
      {
        idEmpleadoGenero: data.empleadoGenero.idEmpleado,
        sku: data.producto.sku,
        cantidad: data.cantidad,
        observaciones: data.observaciones,
      }).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Poliza actualizada correctamente',
        });
        this.router.navigateByUrl('/polizas');
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

}
