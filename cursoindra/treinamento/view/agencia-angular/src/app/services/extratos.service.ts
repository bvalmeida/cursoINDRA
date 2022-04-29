import { IExtrato } from './../interfaces/extrato';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ExtratosService {
  constructor(private http: HttpClient) {}

  api = environment.api;
  endpoint = 'extratos';
}
