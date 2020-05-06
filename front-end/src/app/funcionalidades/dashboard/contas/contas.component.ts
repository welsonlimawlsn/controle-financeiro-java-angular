import { Component, OnInit } from '@angular/core';
import { ContaDTO, DefaultService } from '../../../servicos';
import { RequisicaoService } from '../../../componentes/http/requisicao.service';
import { AcaoModal, ModalService } from '../../../componentes/modal/modal.service';
import { NovaContaComponent } from './nova-conta/nova-conta.component';
import { ConfirmacaoDeleteComponent } from './confirmacao-delete/confirmacao-delete.component';
import { AtualizaContaComponent } from './atualiza-conta/atualiza-conta.component';

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
        ).subscribe(resposta => this.contas = resposta.contas.sort((a, b) => a.nome.localeCompare(b.nome)));
    }

    novaConta() {
        this.modalService.show(NovaContaComponent).subscribe(() => {
            if (this.modalService.ultimaAcao == AcaoModal.CONFIRMA) {
                this.ngOnInit();
            }
        })
    }

    excluiConta(id: string) {
        this.modalService.show(ConfirmacaoDeleteComponent).subscribe(() => {
            if (this.modalService.ultimaAcao == AcaoModal.CONFIRMA) {
                this.requisicaoService.realizaRequisicao(
                    this.requisicao.removeContaUsuario(id)
                ).subscribe(() => this.ngOnInit());
            }
        });
    }

    atualizaConta(conta: ContaDTO) {
        this.modalService.show(AtualizaContaComponent, {conta: conta}).subscribe(() => {
            if (this.modalService.ultimaAcao == AcaoModal.CONFIRMA) {
                this.ngOnInit();
            }
        })
    }
}
