import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContasCadastrarEditarComponent } from './contas-cadastrar-editar.component';

describe('ContasCadastrarEditarComponent', () => {
  let component: ContasCadastrarEditarComponent;
  let fixture: ComponentFixture<ContasCadastrarEditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContasCadastrarEditarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContasCadastrarEditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
