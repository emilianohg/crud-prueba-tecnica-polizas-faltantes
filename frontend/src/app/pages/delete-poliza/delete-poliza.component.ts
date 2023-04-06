import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmAlert } from 'src/app/decorators/confirm-alert';
import { Poliza } from 'src/app/models/poliza';
import { PolizasService } from 'src/app/services/polizas/polizas.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-delete-poliza',
  templateUrl: './delete-poliza.component.html',
  styleUrls: ['./delete-poliza.component.scss']
})
export class DeletePolizaComponent {

  form: FormGroup;

  poliza: Poliza | undefined;

  constructor(
    private fb: FormBuilder,
    private polizasService: PolizasService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.form = this.fb.group({
      motivoEliminacion: [null, [Validators.required]],
    });

    this.loadPoliza();
  }

  loadPoliza() {
    const idPoliza = this.route.snapshot.params['id'];
    this.polizasService.findById(idPoliza).subscribe({
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

  save() {
    this.form.markAllAsTouched();

    if (this.form.invalid) {
      return;
    }

    this.delete();
  }

  @ConfirmAlert('¿Estás seguro de eliminar esta poliza?')
  delete() {
    const motivo = this.form.get('motivoEliminacion')?.value;

    this.polizasService.delete(this.poliza!.idPoliza, motivo).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Poliza eliminada correctamente',
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


  get nombreCompleto() {
    return `${this.poliza!.empleadoGenero.nombre} ${this.poliza!.empleadoGenero.apellido}`;
  }

  get producto() {
    return `${this.poliza!.productoInventario.sku} - ${this.poliza!.productoInventario.nombre}`;
  }
}
