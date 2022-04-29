import { ITransferencia } from './../../../interfaces/transferencia';
import { ContasService } from './../../../services/contas.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { IConta } from 'src/app/interfaces/conta';
import { IDepositoSaque } from 'src/app/interfaces/deposito-saque';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-transferencia',
  templateUrl: './transferencia.component.html',
  styleUrls: ['./transferencia.component.css'],
})
export class TransferenciaComponent implements OnInit {
  constructor(
    private contaService: ContasService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  preencherFormTransferencia(transferencia: IConta): FormGroup {
    return new FormGroup({
      agenciaOrigem: new FormControl(transferencia.agencia),
      numeroContaOrigem: new FormControl(transferencia.numero),
      agenciaDestino: new FormControl(''),
      numeroContaDestino: new FormControl(''),
      valor: new FormControl(null),
    });
  }

  formContaTransferencia: FormGroup = new FormGroup({
    agenciaOrigem: new FormControl('', Validators.required),
    numeroContaOrigem: new FormControl('', Validators.required),
    agenciaDestino: new FormControl('', Validators.required),
    numeroContaDestino: new FormControl('', Validators.required),
    valor: new FormControl(null, Validators.required),
  });

  enviarTransferencia() {
    const transferencia: ITransferencia = this.formContaTransferencia.value;
    this.contaService.transferir(transferencia).subscribe((result) => {
      Swal.fire('Sucesso!', 'Transferência realizada com sucesso!', 'success');
      this.router.navigate(['/contas']);
      console.log(result);
    });
  }

  ngOnInit(): void {
    const id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    if (id) {
      this.contaService.buscarContaPorId(id).subscribe(
        (result: IConta) => {
          this.formContaTransferencia = this.preencherFormTransferencia(result);
        },
        (error) => console.log(error)
      );
    }
  }
}
