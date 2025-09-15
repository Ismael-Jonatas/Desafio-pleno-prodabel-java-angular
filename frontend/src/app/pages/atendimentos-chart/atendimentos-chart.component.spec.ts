import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AtendimentosChartComponent } from './atendimentos-chart.component';

describe('AtendimentosChartComponent', () => {
  let component: AtendimentosChartComponent;
  let fixture: ComponentFixture<AtendimentosChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AtendimentosChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AtendimentosChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
