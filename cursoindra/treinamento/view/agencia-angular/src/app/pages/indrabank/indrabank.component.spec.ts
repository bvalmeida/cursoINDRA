import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndrabankComponent } from './indrabank.component';

describe('IndrabankComponent', () => {
  let component: IndrabankComponent;
  let fixture: ComponentFixture<IndrabankComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IndrabankComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndrabankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
