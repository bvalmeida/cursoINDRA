import { ExtratosService } from './../../../services/extratos.service';
import { IExtrato } from './../../../interfaces/extrato';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ContasService } from './../../../services/contas.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-extrato',
  templateUrl: './extrato.component.html',
  styleUrls: ['./extrato.component.css'],
})
export class ExtratoComponent implements OnInit {
  constructor(
    private extratoService: ExtratosService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  formContaExtrato: FormGroup = new FormGroup({
    agencia: new FormControl('', Validators.required),
    numero: new FormControl('', Validators.required),
    dataInicio: new FormControl('', Validators.required),
    dataFinal: new FormControl('', Validators.required),
  });

  preencherFormExtrato(extrato: IExtrato): FormGroup {
    return new FormGroup({
      agencia: new FormControl(extrato.agencia),
      numero: new FormControl(extrato.numero),
      dataInicio: new FormControl(''),
      dataFinal: new FormControl(''),
    });
  }

  buscarExtrato() {
    const extrato: IExtrato = this.formContaExtrato.value;
  }

  ngOnInit(): void {}
}
