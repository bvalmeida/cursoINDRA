import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ContasService } from './../../../services/contas.service';
import { Component, OnInit } from '@angular/core';
import { IConta } from 'src/app/interfaces/conta';

@Component({
  selector: 'app-contas-cadastrar-editar',
  templateUrl: './contas-cadastrar-editar.component.html',
  styleUrls: ['./contas-cadastrar-editar.component.css'],
})
export class ContasCadastrarEditarComponent implements OnInit {
  constructor(
    private contasService: ContasService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  formConta: FormGroup = new FormGroup({
    id: new FormControl(null),
    agencia: new FormControl('', Validators.required),
    numero: new FormControl('', Validators.required),
    saldo: new FormControl(null),
    cliente: new FormControl(null),
  });

  preencheFormConta(conta: IConta): FormGroup {
    return new FormGroup({
      id: new FormControl(conta.id ? conta.id : null),
      agencia: new FormControl(conta.agencia, Validators.required),
      numero: new FormControl(conta.numero, Validators.required),
      saldo: new FormControl(conta.saldo),
      cliente: new FormControl(conta.cliente.cpf),
    });
  }

  enviarConta() {
    const conta: IConta = this.formConta.value;
    this.contasService.cadastrarConta(conta).subscribe((result) => {
      Swal.fire(
        'Sucesso!',
        `${this.estaEditandoConta() ? 'Editado' : 'Cadastrado'} com sucesso!`,
        'success'
      );
      console.log(result);
      this.router.navigate(['/contas']);
    });
  }

  ngOnInit(): void {
    const id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    if (id) {
      this.contasService.buscarContaPorId(id).subscribe(
        (result: IConta) => {
          this.formConta = this.preencheFormConta(result);
        },
        (error) => console.log(error)
      );
    }
  }

  estaEditandoConta() {
    return !!this.formConta.get('id')?.value;
  }
}
