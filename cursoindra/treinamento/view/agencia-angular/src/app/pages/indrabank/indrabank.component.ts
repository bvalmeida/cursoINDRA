import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-indrabank',
  templateUrl: './indrabank.component.html',
  styleUrls: ['./indrabank.component.css'],
})
export class IndrabankComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  entrar() {
    this.router.navigate(['/bank']);
  }
}
