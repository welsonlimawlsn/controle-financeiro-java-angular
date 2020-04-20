import { ErrorHandler, Injectable, NgZone } from '@angular/core';
import { LoadingService } from './componentes/loading/loading.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Mensagem, MensagemService, TipoMensagem } from './componentes/mensagem-erro/mensagem.service';

const statusWarnMessages = [400];
const statusErrorMessages = [401, 403];

@Injectable()
export class CustomErrorHandler extends ErrorHandler {

    constructor(
        private loadingService: LoadingService,
        private mensagemService: MensagemService,
        private zone: NgZone) {
        super();
    }

    handleError(error: any): void {
        this.zone.run(() => {
            try {
                this.loadingService.hide();
                if (error instanceof HttpErrorResponse) {
                    if (statusWarnMessages.indexOf(error.status) > -1) {
                        this.apresentaMensagem(error, TipoMensagem.WARN);
                    } else if (statusErrorMessages.indexOf(error.status) > -1) {
                        this.apresentaMensagem(error, TipoMensagem.DANGER);
                    } else {
                        this.apresentaMensagemErroInterno(error);
                    }
                } else {
                    this.apresentaMensagemErroInterno(error);
                }
            } catch (e) {
                this.apresentaMensagemErroInterno(error);
            }
        });
    }

    private apresentaMensagemErroInterno(error: any) {
        this.mensagemService
            .addMensagem(new Mensagem('Erro interno. Contate o administrador para reportar.', TipoMensagem.DANGER));
        console.error(error);
    }

    private apresentaMensagem(error: HttpErrorResponse, tipoMensagem: TipoMensagem = TipoMensagem.SUCCESS) {
        this.mensagemService.addMensagens(error.error.erros.map(erro => new Mensagem(erro.mensagem, tipoMensagem)));
    }
}