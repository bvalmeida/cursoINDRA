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
  constructor(private contasService: ContasService) {}

  formConta: FormGroup = new FormGroup({
    id: new FormControl(null),
    agencia: new FormControl('', Validators.required),
    numero: new FormControl('', Validators.required),
    saldo: new FormControl(null),
    cliente: new FormControl(null),
  });

  enviarConta() {
    const conta: IConta = this.formConta.value;
    this.contasService.cadastrarConta(conta).subscribe((result) => {
      console.log(result);
    });
  }

  ngOnInit(): void {}
}
