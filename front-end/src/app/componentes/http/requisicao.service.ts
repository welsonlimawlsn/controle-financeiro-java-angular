import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { LoadingService } from '../loading/loading.service';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { Mensagem, MensagemService, TipoMensagem } from '../mensagem-erro/mensagem.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CodigoSegurancaService } from '../seguranca/codigo-seguranca.service';

@Injectable({
    providedIn: 'root'
})
export class RequisicaoService {

    constructor(
        private loadingService: LoadingService,
        private mensagemService: MensagemService,
        private codigoSegurancaService: CodigoSegurancaService,
        private httpClient: HttpClient
    ) {
    }

    realizaRequisicao<T>(observable: Observable<T>, metodo: string = 'post'): Observable<T> {
        this.loadingService.show();
        this.mensagemService.limpaMensagens();
        return observable
            .pipe(
                tap(() => this.loadingService.hide()),
                catchError(err => {
                    if ((err instanceof HttpErrorResponse && this.isCodigoSeguranca(err))) {
                        return this.codigoSegurancaService.informaCodigoSeguranca(new ChamadaCodigoSeguraca(err.url, metodo, null))
                            .pipe(
                                switchMap((chamada: ChamadaCodigoSeguraca) => this.refazRequisicaoComCodigoSeguranca(chamada))
                            );
                    }
                    return throwError(err);
                })
            );
    }

    private refazRequisicaoComCodigoSeguranca(value: ChamadaCodigoSeguraca): Observable<any> {
        return this.realizaRequisicao(
            this.httpClient.request(value.metodo, value.url, {body: {codigoSeguranca: value.codigo}}), value.metodo
        );
    }

    private isCodigoSeguranca(err: HttpErrorResponse) {
        const codigos = ['023', '026'];
        let resultado = err.error.erros.map(erro => erro.codigo).find(codigo => codigos.indexOf(codigo) > -1);
        if (resultado === '026') {
            this.mensagemService.addMensagem(new Mensagem(
                err.error.erros.filter(error => error.codigo === '026')[0].mensagem, TipoMensagem.WARN));
        }
        return resultado !== undefined;
    }
}

export class ChamadaCodigoSeguraca {
    url: string;
    metodo: string;
    codigo?: string;

    constructor(url: string, metodo: string, codigo: string) {
        this.url = url;
        this.metodo = metodo;
        this.codigo = codigo;
    }
}
