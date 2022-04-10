import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { ContasService } from './../../../services/contas.service';
import { IDepositoSaque } from './../../../interfaces/deposito-saque';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-deposito',
  templateUrl: './deposito.component.html',
  styleUrls: ['./deposito.component.css'],
})
export class DepositoComponent implements OnInit {
  constructor(
    private contaService: ContasService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  preencherFormDeposito(deposito: IDepositoSaque): FormGroup {
    return new FormGroup({
      agencia: new FormControl(deposito.agencia),
      numeroConta: new FormControl(deposito.numeroConta),
      valor: new FormControl(null),
    });
  }

  formContaDeposito: FormGroup = new FormGroup({
    agencia: new FormControl(''),
    numeroConta: new FormControl('', Validators.required),
    valor: new FormControl(null, Validators.required),
  });

  enviarDeposito() {
    const deposito: IDepositoSaque = this.formContaDeposito.value;
    this.contaService.depositar(deposito).subscribe((result) => {
      Swal.fire('Sucesso!', 'Depósito realizado com sucesso!', 'success');
      this.router.navigate(['/contas']);
      console.log(result);
    });
  }

  ngOnInit(): void {}
}
