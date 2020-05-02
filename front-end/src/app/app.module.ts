import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, LOCALE_ID, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ComponentesModule } from './componentes/componentes.module';
import { LoadingService } from './componentes/loading/loading.service';
import { RequisicaoService } from './componentes/http/requisicao.service';
import { BASE_PATH, DefaultService } from './servicos';
import { environment } from '../environments/environment';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CustomErrorHandler } from './custom-error-handler';
import { MensagemService } from './componentes/mensagem-erro/mensagem.service';
import { SessaoService } from './componentes/seguranca/sessao.service';
import { CustomHttpInterceptorService } from './componentes/http/custom-http-interceptor.service';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt);

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        ComponentesModule
    ],
    providers: [
        LoadingService,
        RequisicaoService,
        DefaultService,
        MensagemService,
        SessaoService,
        {
            provide: BASE_PATH,
            useValue: environment.baseUrl
        },
        {
            provide: ErrorHandler,
            useClass: CustomErrorHandler
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: CustomHttpInterceptorService,
            multi: true
        },
        {
            provide: LOCALE_ID,
            useValue: 'pt-BR'
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
