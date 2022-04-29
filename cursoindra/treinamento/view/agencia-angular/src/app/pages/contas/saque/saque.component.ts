import { Router, ActivatedRoute } from '@angular/router';
import { ContasService } from './../../../services/contas.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { IConta } from 'src/app/interfaces/conta';
import { IDepositoSaque } from 'src/app/interfaces/deposito-saque';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-saque',
  templateUrl: './saque.component.html',
  styleUrls: ['./saque.component.css'],
})
export class SaqueComponent implements OnInit {
  constructor(
    private contaService: ContasService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  preencherFormSaque(saque: IConta): FormGroup {
    return new FormGroup({
      agencia: new FormControl(saque.agencia),
      numeroConta: new FormControl(saque.numero),
      valor: new FormControl(null),
    });
  }

  formContaSaque: FormGroup = new FormGroup({
    agencia: new FormControl('', Validators.required),
    numeroConta: new FormControl('', Validators.required),
    valor: new FormControl(null, Validators.required),
  });

  enviarSaque() {
    const saque: IDepositoSaque = this.formContaSaque.value;
    this.contaService.sacar(saque).subscribe((result) => {
      Swal.fire('Sucesso!', 'Saque realizado com sucesso!', 'success');
      this.router.navigate(['/contas']);
      console.log(result);
    });
  }

  ngOnInit(): void {
    const id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    if (id) {
      this.contaService.buscarContaPorId(id).subscribe(
        (result: IConta) => {
          this.formContaSaque = this.preencherFormSaque(result);
        },
        (error) => console.log(error)
      );
    }
  }
}
