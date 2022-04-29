import { IContaEdit } from './../../../interfaces/contas-edit';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ContasService } from './../../../services/contas.service';
import { Component, OnInit } from '@angular/core';
import { IConta } from 'src/app/interfaces/conta';
import { ICliente } from 'src/app/interfaces/cliente';
import { ClientesService } from 'src/app/services/clientes.service';

@Component({
  selector: 'app-contas-cadastrar-editar',
  templateUrl: './contas-cadastrar-editar.component.html',
  styleUrls: ['./contas-cadastrar-editar.component.css'],
})
export class ContasCadastrarEditarComponent implements OnInit {
  emptyConta: IContaEdit = {
    id: 0,
    agencia: '',
    numero: '',
    saldo: 0,
  };

  formConta: FormGroup = this.preencheFormGroup(this.emptyConta);
  clientes: ICliente[] = [];
  clientePorCPF: any = null;

  constructor(
    private clienteService: ClientesService,
    private contaService: ContasService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const cpf = this.activatedRoute.snapshot.paramMap.get('cpf');
    if (!cpf) {
      return this.buscaClientes();
    }
    this.buscaPorCpf(cpf);
  }

  buscaClientes() {
    this.clienteService.listarTodosClientes().subscribe((result) => {
      this.clientes = result;
    });
  }

  buscaPorCpf(cpf: string) {
    this.clienteService.buscarPorCPF(cpf).subscribe((result) => {
      this.clientePorCPF = result;
      this.formConta.get('idCliente')?.setValue(result.id);
    });
  }
  preencheFormGroup(conta: IContaEdit): FormGroup {
    return new FormGroup({
      id: new FormControl(conta.id ? conta.id : null),
      agencia: new FormControl(
        conta.agencia ? conta.agencia : '',
        Validators.required
      ),
      numero: new FormControl(
        conta.numero ? conta.numero : '',
        Validators.required
      ),
      saldo: new FormControl(
        conta.saldo ? conta.saldo : 0,
        Validators.required
      ),
      idCliente: new FormControl(null, Validators.required),
    });
  }

  enviar() {
    const conta: IContaEdit = {
      agencia: this.formConta.get('agencia')?.value,
      numero: this.formConta.get('numero')?.value,
      saldo: this.formConta.get('saldo')?.value,
      cliente: { id: this.formConta.get('idCliente')?.value } as ICliente,
    };
    this.contaService.cadastrarConta(conta).subscribe((result) => {
      Swal.fire('Sucesso', `Conta cadastrada com sucesso!`, 'success');
    });
    this.router.navigate(['/contas']);
  }
}
