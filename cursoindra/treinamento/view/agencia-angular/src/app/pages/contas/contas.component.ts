import { IConta } from './../../interfaces/conta';
import { ContasService } from './../../services/contas.service';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-contas',
  templateUrl: './contas.component.html',
  styleUrls: ['./contas.component.css'],
})
export class ContasComponent implements OnInit {
  contas: IConta[] = [];

  constructor(private contasService: ContasService) {}

  ngOnInit(): void {
    this.listarTodas();
  }

  listarTodas() {
    this.contasService.listarTodasContas().subscribe((result: IConta[]) => {
      this.contas = result;
      console.log(this.contas);
    });
  }

  excluir(id: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.contasService.remover(id).subscribe(
          () => {
            Swal.fire({
              title: 'Are you sure?',
              text: "You won't be able to revert this!",
              icon: 'warning',
            });
            this.listarTodas();
          },
          (error) => {
            console.log(error);
          }
        );
      }
    });
  }
}
