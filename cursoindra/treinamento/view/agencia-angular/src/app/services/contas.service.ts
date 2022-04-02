import { HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ContasService {
  api = environment.api;
  endpoint = 'contas';

  constructor(private http: HttpClient) {}

  listarTodasContas() {
    return this.http.get(`${this.api}/${this.endpoint}/`);
  }
}
