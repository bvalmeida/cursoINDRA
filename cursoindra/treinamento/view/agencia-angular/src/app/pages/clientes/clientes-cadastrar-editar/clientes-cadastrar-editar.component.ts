import Swal from 'sweetalert2';
import { ClientesService } from './../../../services/clientes.service';
import { ICliente } from './../../../interfaces/cliente';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-clientes-cadastrar-editar',
  templateUrl: './clientes-cadastrar-editar.component.html',
  styleUrls: ['./clientes-cadastrar-editar.component.css'],
})
export class ClientesCadastrarEditarComponent implements OnInit {
  constructor(
    private clientesService: ClientesService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  emptyCliente: ICliente = {
    id: 0,
    nome: '',
    cpf: '',
    email: '',
    observacoes: '',
    ativo: true,
  };

  formCliente: FormGroup = this.preencheFormGroup(this.emptyCliente);

  preencheFormGroup(cliente: ICliente): FormGroup {
    return new FormGroup({
      id: new FormControl(cliente.id ? cliente.id : null),
      nome: new FormControl(cliente.nome, Validators.required),
      cpf: new FormControl(cliente.cpf, Validators.required),
      email: new FormControl(cliente.email, [
        Validators.required,
        Validators.email,
      ]),
      observacoes: new FormControl(cliente.observacoes),
      ativo: new FormControl(cliente.ativo),
    });
  }

  ngOnInit(): void {
    const id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    if (id) {
      this.clientesService.buscarPorId(id).subscribe(
        (result: ICliente) => {
          this.formCliente = this.preencheFormGroup(result);
        },
        (error) => console.log(error)
      );
    }
  }

  enviar() {
    const cliente: ICliente = this.formCliente.value;
    this.clientesService.cadastrarEditar(cliente).subscribe((result) => {
      Swal.fire(
        'Sucesso!',
        `${this.estaEditando() ? 'Editado' : 'Cadastrado'} com sucesso! `,
        'success'
      );
      this.router.navigate(['/clientes']);
    });
  }

  estaEditando() {
    return !!this.formCliente.get('id')?.value;
  }
}
