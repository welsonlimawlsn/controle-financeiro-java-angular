import { Component, OnInit } from '@angular/core';
import { ContaDTO, DefaultService } from '../../../servicos';
import { RequisicaoService } from '../../../componentes/http/requisicao.service';
import { ModalService } from '../../../componentes/modal/modal.service';
import { NovaContaComponent } from './nova-conta/nova-conta.component';

@Component({
    selector: 'app-contas',
    templateUrl: './contas.component.html',
    styleUrls: ['./contas.component.scss']
})
export class ContasComponent implements OnInit {

    contas: ContaDTO[];

    constructor(
        private requisicao: DefaultService,
        private requisicaoService: RequisicaoService,
        private modalService: ModalService) {
    }

    ngOnInit(): void {
        this.requisicaoService.realizaRequisicao(
            this.requisicao.consultaContasUsuario(), 'get'
        ).subscribe(resposta => this.contas = resposta.contas);
    }

    novaConta() {
        this.modalService.show(NovaContaComponent);
    }

}
