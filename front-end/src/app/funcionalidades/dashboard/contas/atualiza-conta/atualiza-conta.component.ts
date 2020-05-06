import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AcaoModal, ModalService } from '../../../../componentes/modal/modal.service';
import { ContaDTO, DefaultService } from '../../../../servicos';
import { RequisicaoService } from '../../../../componentes/http/requisicao.service';
import { Mensagem, MensagemService, TipoMensagem } from '../../../../componentes/mensagem-erro/mensagem.service';

@Component({
    selector: 'app-atualiza-conta',
    templateUrl: './atualiza-conta.component.html',
    styleUrls: ['./atualiza-conta.component.scss']
})
export class AtualizaContaComponent implements OnInit {
    formularioAtualizaConta: FormGroup;
    conta: ContaDTO;

    constructor(
        private modalService: ModalService,
        private fb: FormBuilder,
        private requisicaoService: RequisicaoService,
        private requisicao: DefaultService,
        private mensagemService: MensagemService
    ) {
    }

    ngOnInit(): void {
        this.conta = this.modalService.data.conta;
        this.formularioAtualizaConta = this.fb.group({
            id: [this.conta.id, Validators.required],
            nome: [this.conta.nome, Validators.required],
            valorInicial: [this.conta.valorInicial, Validators.required]
        })
    }

    atualizaConta() {
        this.requisicaoService.realizaRequisicao(
            this.requisicao.atualizaContaUsuario(this.formularioAtualizaConta.value)
        ).subscribe(() => {
            this.mensagemService.addMensagem(new Mensagem('conta atualizada com sucesso!', TipoMensagem.SUCCESS))
            this.modalService.ultimaAcao = AcaoModal.CONFIRMA;
            this.modalService.close();
        })
    }
}
