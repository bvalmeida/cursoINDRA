import { ICliente } from './../../interfaces/cliente';
import { ClientesService } from './../../services/clientes.service';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css'],
})
export class ClientesComponent implements OnInit {
  clientes: ICliente[] = [];

  constructor(private clienteService: ClientesService) {}

  ngOnInit(): void {
    this.listarTodos();
  }

  listarTodos() {
    this.clienteService
      .listarTodosClientes()
      .subscribe((result: ICliente[]) => {
        this.clientes = result;
        console.log(this.clientes);
      });
  }

  confirmar(id: number) {
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
        this.clienteService.remover(id).subscribe(
          () => {
            Swal.fire({
              title: 'Are you sure?',
              text: "You won't be able to revert this!",
              icon: 'warning',
            });
            this.listarTodos();
          },
          (error) => {
            console.log(error);
          }
        );
      }
    });
  }
}
