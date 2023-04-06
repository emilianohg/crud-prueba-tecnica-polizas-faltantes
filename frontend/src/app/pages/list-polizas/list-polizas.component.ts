import { Component } from '@angular/core';
import { PolizasService } from 'src/app/services/polizas/polizas.service';

@Component({
  selector: 'app-list-polizas',
  templateUrl: './list-polizas.component.html',
  styleUrls: ['./list-polizas.component.scss']
})
export class ListPolizasComponent {
  
    constructor(
      private polizasService: PolizasService,
    ) {
      this.polizasService.getAll().subscribe((response) => {
        console.log(response);
      });
    }
}
