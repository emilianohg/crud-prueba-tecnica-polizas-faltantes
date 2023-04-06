import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PolizasService } from 'src/app/services/polizas/polizas.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-poliza',
  templateUrl: './create-poliza.component.html',
  styleUrls: ['./create-poliza.component.scss']
})
export class CreatePolizaComponent {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private polizasService: PolizasService,
    private router: Router,
  ) {
    this.form = this.fb.group({
      empleadoGenero: [null, [Validators.required]],
      producto: [null, [Validators.required]],
      cantidad: [null, [Validators.required, Validators.min(1)]],
      observaciones: [null, [Validators.required]],
    });
  }

  save() {

    this.form.markAllAsTouched();
    
    if (this.form.invalid) {
      return;
    }

    const data = this.form.value;

    this.polizasService.create({
      idEmpleadoGenero: data.empleadoGenero.idEmpleado,
      sku: data.producto.sku,
      cantidad: data.cantidad,
      observaciones: data.observaciones,
    }).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Poliza creada correctamente',
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
