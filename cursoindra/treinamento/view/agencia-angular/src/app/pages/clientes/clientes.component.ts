import { ClientesService } from './../../services/clientes.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css'],
})
export class ClientesComponent implements OnInit {
  clientes: any[] = [];

  constructor(private clienteService: ClientesService) {}

  ngOnInit(): void {
    this.listarTodos();
  }

  listarTodos() {
    this.clienteService.listarTodosClientes().subscribe((result: any) => {
      this.clientes = result;
      console.log(this.clientes);
    });
  }
}
