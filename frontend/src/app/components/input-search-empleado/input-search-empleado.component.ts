import { Component, ViewChild } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, distinctUntilChanged, filter, map, merge, Observable, OperatorFunction, Subject, switchMap, tap } from 'rxjs';
import { Empleado } from 'src/app/models/empleado';
import { EmpleadosService } from 'src/app/services/empleados/empleados.service';

@Component({
  selector: 'app-input-search-empleado',
  templateUrl: './input-search-empleado.component.html',
  styleUrls: ['./input-search-empleado.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi:true,
      useExisting: InputSearchEmpleadoComponent
    },
  ]
})
export class InputSearchEmpleadoComponent {
  
  @ViewChild('instance', { static: true }) instance!: NgbTypeahead;

  isDisabled: boolean = false;
  touched: boolean = false;
  searching: boolean = false;

  empleados: Empleado[] = [];

  empleado: Empleado | undefined;

  constructor(
    private empleadosService: EmpleadosService,
  ) {
    this.getEmpleados();
  }

  getEmpleados() {
    this.empleadosService.getAll({page: 1, limit: 10}).subscribe((response) => {
      this.empleados = response.data.records;
    });
  }

  search: OperatorFunction<string, readonly Empleado[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term: string) => this.empleadosService.getAll({page: 1, limit: 10, search: term})
        .pipe(
          map((response) => response.data.records),
        )
      ),
      tap(() => (this.searching = false)),
    );

  onSelect(event: {item: Empleado, preventDefault: () => void}): void {
    event.preventDefault();
    this.addToSelectedEmpleado(event.item);
  }

  onChangeText(event: any): void {
    event.preventDefault();
    const text = event.target.value;

    if (text === '') {
      this.removeSelected();
      return;
    }

    const empleado = this.empleados.find((empleado) => {
      const nombreCompleto = `${empleado.nombre} ${empleado.apellido}`;
      return nombreCompleto.toLowerCase().includes(text.toLowerCase());
    });

    if (empleado) {
      this.addToSelectedEmpleado(empleado);
    } else {
      this.removeSelected();
    }
  }

  addToSelectedEmpleado(empleado: Empleado): void {
    this.empleado = empleado;
    this.onChange(empleado);
  }

  removeSelected(): void {
    this.empleado = undefined;
    this.onChange(undefined);
  }

  onChange = (empleado?: Empleado) => {};

  onTouched = () => {};

  writeValue(empleado: Empleado): void {
    this.empleado = empleado;
  }

  formatter = (empleado: Empleado) => `${empleado?.nombre} ${empleado?.apellido}`;

  registerOnChange(onChange: any): void {
    this.onChange = onChange;
  }

  registerOnTouched(onTouched: any): void {
    this.onTouched = onTouched;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.isDisabled = isDisabled;
  }

  get isSelected(): boolean {
    return typeof this.empleado === 'object' && this.empleado !== null;
  }
}