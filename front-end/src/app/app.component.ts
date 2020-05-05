import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MensagemService } from './componentes/mensagem-erro/mensagem.service';
import { DefaultService } from './servicos';
import { SessaoService } from './componentes/seguranca/sessao.service';
import { LocalModalDirective } from './componentes/local-modal.directive';
import { ModalService } from './componentes/modal/modal.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styles: []
})
export class AppComponent {

    @ViewChild(LocalModalDirective, {static: true}) appLocalModal: LocalModalDirective;

    constructor(
        private router: Router,
        private mensagemService: MensagemService,
        private requisicao: DefaultService,
        private sessaoService: SessaoService,
        private modalService: ModalService
    ) {
        router.events.subscribe(event => {
            this.mensagemService.limpaMensagens();
            if (this.router.getCurrentNavigation().extras.state && this.router.getCurrentNavigation().extras.state.mensagens) {
                this.mensagemService.addMensagens(this.router.getCurrentNavigation().extras.state.mensagens);
            }
        });

        if (!this.sessaoService.isDispositivoCadastrado()) {
            this.requisicao.novoDispositivo({
                sistemaOperacional: window.navigator.platform
            }).subscribe(resposta => this.sessaoService.novoDispositivo(resposta));
        }

        this.modalService.eventEmitter.subscribe((component) => {
            let viewContainerRef = this.appLocalModal.viewContainerRef;
            viewContainerRef.clear();
            if (component) {
                viewContainerRef.createComponent(component);
            }
        })
    }
}
