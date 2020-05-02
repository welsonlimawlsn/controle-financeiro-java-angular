import { Component, OnInit } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RequisicaoService } from '../../../componentes/http/requisicao.service';
import { DefaultService } from '../../../servicos';
import { switchMap } from 'rxjs/operators';
import { SessaoService } from '../../../componentes/seguranca/sessao.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-novo-usuario',
    templateUrl: './novo-usuario.component.html',
    styleUrls: ['./novo-usuario.component.scss'],
    animations: [
        trigger('transicao', [
            state('void', style({
                transform: 'translate(50px, 0)',
                opacity: 0
            })),
            transition('* => void', animate('.1s', style({
                opacity: 0,
                transform: 'translate(-100px, 0)'
            }))),
            transition('void => *', animate('.1s', style({
                opacity: 1,
                transform: 'translate(0, 0)'
            })))
        ])
    ]
})
export class NovoUsuarioComponent implements OnInit {

    controls: FormControl[];
    etapaAtual = 0;
    formularioCadastro: FormGroup;

    NOME = 0;
    SOBRENOME = 1;
    EMAIL = 2;
    SENHA = 3;

    get naoEhUltimaEtapa(): boolean {
        return this.etapaAtual < this.controls.length;
    }

    constructor(
        private fb: FormBuilder,
        private requisicaoService: RequisicaoService,
        private requisicao: DefaultService,
        private sessaoService: SessaoService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.controls = [
            this.fb.control('', Validators.required),
            this.fb.control('', Validators.required),
            this.fb.control('', [Validators.required, Validators.email]),
            this.fb.control('', Validators.required),
        ];

        this.formularioCadastro = this.fb.group({
            nome: this.controls[this.NOME],
            sobrenome: this.controls[this.SOBRENOME],
            email: this.controls[this.EMAIL],
            senha: this.controls[this.SENHA]
        });
    }

    avancaEtapa() {
        this.etapaAtual++;
    }

    criaConta() {
        this.requisicaoService.realizaRequisicao(
            this.requisicao.novoUsuario(this.formularioCadastro.value)
        ).pipe(
            switchMap(
                () => this.requisicaoService.realizaRequisicao(
                    this.requisicao.loginUsuario({
                        email: this.formularioCadastro.value.email,
                        senha: this.formularioCadastro.value.senha
                    })
                )
            ),
            switchMap(resposta => {
                this.sessaoService.novaSessao(resposta);
                return this.requisicaoService.realizaRequisicao(
                    this.requisicao.novaConta({
                        nome: 'Carteira',
                        valorInicial: 0
                    })
                );
            })
        ).subscribe(response => this.router.navigate(['dashboard']));
    }

    corrige() {
        this.etapaAtual = 0;
    }
}
