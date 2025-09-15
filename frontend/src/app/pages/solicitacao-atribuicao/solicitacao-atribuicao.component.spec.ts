import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitacaoAtribuicaoComponent } from './solicitacao-atribuicao.component';

describe('SolicitacaoAtribuicaoComponent', () => {
  let component: SolicitacaoAtribuicaoComponent;
  let fixture: ComponentFixture<SolicitacaoAtribuicaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitacaoAtribuicaoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SolicitacaoAtribuicaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
