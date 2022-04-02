import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ClientesService {
  api = environment.api;
  endpoint = 'clientes';

  constructor(private http: HttpClient) {}

  listarTodosClientes() {
    return this.http.get(`${this.api}/${this.endpoint}/`);
  }
}
