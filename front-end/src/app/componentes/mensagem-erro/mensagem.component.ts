import { Component, OnInit } from '@angular/core';
import { Mensagem, MensagemService } from './mensagem.service';

@Component({
    selector: 'app-mensagem-erro',
    templateUrl: './mensagem.component.html',
    styleUrls: ['./mensagem.component.scss']
})
export class MensagemComponent implements OnInit {

    mensagens: Mensagem[];

    constructor(private mensagemService: MensagemService) {
    }

    ngOnInit(): void {
        this.mensagens = this.mensagemService.mensagens;
        this.mensagemService.emitter.subscribe(() => this.mensagens = this.mensagemService.mensagens);
    }

}
