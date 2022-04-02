import { ContasService } from './../../services/contas.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contas',
  templateUrl: './contas.component.html',
  styleUrls: ['./contas.component.css'],
})
export class ContasComponent implements OnInit {
  contas: any[] = [];

  constructor(private contasService: ContasService) {}

  ngOnInit(): void {
    this.listarTodas();
  }

  listarTodas() {
    this.contasService.listarTodasContas().subscribe((result: any) => {
      this.contas = result;
      console.log(this.contas);
    });
  }
}