import { Component, ViewChild } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap, tap } from 'rxjs';
import { ProductoInventario } from 'src/app/models/producto-inventario';
import { InventarioService } from 'src/app/services/inventario/inventario.service';

@Component({
  selector: 'app-input-search-producto',
  templateUrl: './input-search-producto.component.html',
  styleUrls: ['./input-search-producto.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi:true,
      useExisting: InputSearchProductoComponent
    },
  ],
})
export class InputSearchProductoComponent {
  @ViewChild('instance', { static: true }) instance!: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  isDisabled: boolean = false;
  touched: boolean = false;
  searching: boolean = false;

  productos: ProductoInventario[] = [];

  producto: ProductoInventario | undefined;

  constructor(
    private inventarioService: InventarioService,
  ) {
    this.getEmpleados();
  }

  getEmpleados() {
    this.inventarioService.getAll({page: 1, limit: 10}).subscribe((response) => {
      this.productos = response.data.records;
    });
  }

  search: OperatorFunction<string, readonly ProductoInventario[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term: string) => this.inventarioService.getAll({page: 1, limit: 10, search: term})
        .pipe(
          map((response) => response.data.records),
        )
      ),
      tap(() => (this.searching = false)),
    );

  onSelect(event: {item: ProductoInventario, preventDefault: () => void}): void {
    event.preventDefault();
    this.addToModel(event.item);
  }

  onChangeText(event: any): void {
    event.preventDefault();
    const text = event.target.value;

    if (text === '') {
      this.removeSelected();
      return;
    }

    const producto = this.productos.find((producto) => {
      return producto.nombre.toLowerCase().includes(text.toLowerCase());
    });

    if (producto) {
      this.addToModel(producto);
    } else {
      this.removeSelected();
    }
  }

  addToModel(producto: ProductoInventario): void {
    this.producto = producto;
    this.onChange(producto);
  }

  removeSelected(): void {
    this.producto = undefined;
    this.onChange(undefined);
  }

  removeFromSelected(producto: ProductoInventario): void {
    this.producto = undefined;

    this.onChange(undefined);
  }

  onChange = (producto?: ProductoInventario) => {};

  onTouched = () => {};

  writeValue(producto: ProductoInventario): void {
    this.producto = producto;
  }

  formatter = (producto: ProductoInventario) => producto.nombre;

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
    return typeof this.producto === 'object' && this.producto !== null;
  }
}
