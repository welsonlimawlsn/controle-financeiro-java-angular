import { Component, OnInit } from '@angular/core';
import { ContaDTO, DefaultService } from '../../../servicos';
import { RequisicaoService } from '../../../componentes/http/requisicao.service';

@Component({
    selector: 'app-contas',
    templateUrl: './contas.component.html',
    styleUrls: ['./contas.component.scss']
})
export class ContasComponent implements OnInit {

    contas: ContaDTO[];

    constructor(private requisicao: DefaultService, private requisicaoService: RequisicaoService) {
    }

    ngOnInit(): void {
        this.requisicaoService.realizaRequisicao(
            this.requisicao.consultaContasUsuario(), 'get'
        ).subscribe(resposta => this.contas = resposta.contas);
    }

}
