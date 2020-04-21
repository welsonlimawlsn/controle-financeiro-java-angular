import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RequisicaoService } from '../../../componentes/http/requisicao.service';
import { DefaultService } from '../../../servicos';
import { Router } from '@angular/router';

@Component({
    selector: 'app-recuperacao-senha',
    templateUrl: './recuperacao-senha.component.html',
    styleUrls: ['./recuperacao-senha.component.scss']
})
export class RecuperacaoSenhaComponent implements OnInit {

    formularioNovaSenha: FormGroup;

    constructor(
        private fb: FormBuilder,
        private requisicaoService: RequisicaoService,
        private requisicao: DefaultService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.formularioNovaSenha = this.fb.group({
            email: ['', [Validators.required, Validators.email]],
            novaSenha: ['', [Validators.required, Validators.minLength(8)]]
        });
    }

    enviaNovaSenha() {
        this.requisicaoService.realizaRequisicao(
            this.requisicao.recuperaSenha(this.formularioNovaSenha.value), 'put'
        ).subscribe(() => {
            this.router.navigate(['usuario', 'login'])
        })
    }
}
