import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RequisicaoService } from '../../../componentes/http/requisicao.service';
import { DefaultService, UsuarioDTO } from '../../../servicos';
import { SessaoService } from '../../../componentes/seguranca/sessao.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login-usuario',
    templateUrl: './login-usuario.component.html',
    styleUrls: ['./login-usuario.component.scss']
})
export class LoginUsuarioComponent implements OnInit {

    formularioLogin: FormGroup;

    usuario: UsuarioDTO;

    constructor(
        private fb: FormBuilder,
        private requisicaoService: RequisicaoService,
        private requisicao: DefaultService,
        private sessaoService: SessaoService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.formularioLogin = this.fb.group({
            email: ['', Validators.required],
            senha: ['', Validators.required]
        });
    }

    login() {
        this.requisicaoService.realizaRequisicao(
            this.requisicao.loginUsuario(this.formularioLogin.value)
        ).subscribe(response => {
            this.sessaoService.novaSessao(response);
            this.router.navigate(['dashboard'])
        });
    }
}
