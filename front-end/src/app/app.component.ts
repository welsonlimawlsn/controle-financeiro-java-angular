import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MensagemService } from './componentes/mensagem-erro/mensagem.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styles: []
})
export class AppComponent {
    constructor(private router: Router, private mensagemService: MensagemService) {
        router.events.subscribe(event => {
            this.mensagemService.limpaMensagens();
        });
    }
}
