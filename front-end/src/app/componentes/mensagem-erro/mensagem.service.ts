import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class MensagemService {

    mensagens: Mensagem[] = [];
    emitter = new EventEmitter();

    constructor() {
    }

    addMensagem(mensagem: Mensagem) {
        this.mensagens.push(mensagem);
        this.emitter.emit();
    }

    addMensagens(mensagens: Mensagem[]) {
        for (let mensagem of mensagens) {
            this.mensagens.push(mensagem);
        }
        this.emitter.emit();
    }

    limpaMensagens() {
        this.mensagens = [];
        this.emitter.emit();
    }
}

export class Mensagem {
    mensagem: string;
    tipo: TipoMensagem;

    constructor(mensagem: string, tipo: TipoMensagem) {
        this.mensagem = mensagem;
        this.tipo = tipo;
    }
}

export enum TipoMensagem {
    DANGER = 'danger',
    SUCCESS = 'success',
    WARN = 'warning'
}