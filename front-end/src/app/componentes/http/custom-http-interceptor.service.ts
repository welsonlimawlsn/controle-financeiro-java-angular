import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SessaoService } from '../seguranca/sessao.service';

@Injectable({
    providedIn: 'root'
})
export class CustomHttpInterceptorService implements HttpInterceptor {

    constructor(
        private sessaoService: SessaoService
    ) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let newHeaders = req.headers;

        if (this.sessaoService.isDispositivoCadastrado()) {
            newHeaders = newHeaders.set('Id-Dispositivo', this.sessaoService.dispositivo);
        }

        if (this.sessaoService.isAutenticado()) {
            newHeaders = newHeaders.set('Authorization', this.sessaoService.token.autorizacao);
        }

        req = req.clone({
            headers: newHeaders
        });
        return next.handle(req);
    }
}
