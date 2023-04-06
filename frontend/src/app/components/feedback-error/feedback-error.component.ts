import { Component, Input } from '@angular/core';
import { NgControl } from '@angular/forms';

@Component({
  selector: 'app-feedback-error',
  styles: [
    ` :host {
        font-size: 0.8rem;
      }

      .invalid-feedback {
        display: block;
        color: #dc3545;
      }
    `,
  ],
  template: `<div *ngIf="hasError" class="text-danger">{{ errorMessage }}</div>`,
})
export class FeedbackErrorComponent {
  @Input() control!: NgControl;
  @Input() fieldName!: string;

  get hasError(): boolean| null {
    return this.control.invalid && (this.control.dirty || this.control.touched);
  }

  get errorMessage(): string | undefined {
    if (this.control.errors) {
      for (const errorName in this.control.errors) {
        if (this.control.errors.hasOwnProperty(errorName)) {
          return this.getErrorMessage(errorName);
        }
      }
    }

    return undefined;
  }

  private getErrorMessage(errorName: string): string {
    
    const errors: { [key: string] : string } = {
      required: `El campo ${this.fieldName} es requerido.`,
      minlength: `El campo ${this.fieldName} debe tener al menos ${this.control.errors![errorName].requiredLength} caracteres.`,
      maxlength: `El campo ${this.fieldName} no puede tener más de ${this.control.errors![errorName].requiredLength} caracteres.`,
      pattern: `El formato de ${this.fieldName} es inválido.`,
      email: `El campo ${this.fieldName} debe ser una dirección de correo electrónico válida.`,
      unique: `El campo ${this.fieldName} ya está en uso.`
    };

    return errors[errorName];
  }
}
