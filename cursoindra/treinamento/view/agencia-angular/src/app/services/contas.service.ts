import { IContaEdit } from './../interfaces/contas-edit';
import { ITransferencia } from './../interfaces/transferencia';
import { Observable } from 'rxjs';
import { IDepositoSaque } from './../interfaces/deposito-saque';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { IConta } from '../interfaces/conta';

@Injectable({
  providedIn: 'root',
})
export class ContasService {
  api = environment.api;
  endpoint = 'contas';

  constructor(private http: HttpClient) {}

  listarTodasContas() {
    return this.http.get<IConta[]>(`${this.api}/${this.endpoint}/`);
  }

  buscarContaPorId(id: number): Observable<IConta> {
    return this.http.get<IConta>(`${this.api}/${this.endpoint}/${id}`);
  }

  cadastrarConta(conta: IContaEdit) {
    return this.http.post(`${this.api}/${this.endpoint}/`, conta);
  }

  alterarConta(conta: IConta) {
    return this.http.put(`${this.api}/${this.endpoint}/${conta.id}`, conta);
  }

  cadastrarEditarConta(conta: IContaEdit) {
    if (conta.id) {
      return this.http.put(`${this.api}/${this.endpoint}/${conta.id}`, conta);
    }
    return this.http.post(`${this.api}/${this.endpoint}/`, conta);
  }

  remover(id: number) {
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`);
  }

  depositar(deposito: IDepositoSaque) {
    return this.http.put(`${this.api}/${this.endpoint}/deposito/`, deposito);
  }

  sacar(saque: IDepositoSaque) {
    return this.http.put(`${this.api}/${this.endpoint}/saque/`, saque);
  }

  transferir(transferencia: ITransferencia) {
    return this.http.put(
      `${this.api}/${this.endpoint}/transferencia/`,
      transferencia
    );
  }
}
