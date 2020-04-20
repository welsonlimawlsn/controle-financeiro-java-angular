import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ComponentesModule } from './componentes/componentes.module';
import { LoadingService } from './componentes/loading/loading.service';
import { RequisicaoService } from './componentes/http/requisicao.service';
import { BASE_PATH, DefaultService } from './servicos';
import { environment } from '../environments/environment';
import { HttpClientModule } from '@angular/common/http';
import { CustomErrorHandler } from './custom-error-handler';
import { MensagemService } from './componentes/mensagem-erro/mensagem.service';

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
        {
            provide: BASE_PATH,
            useValue: environment.baseUrl
        },
        {
            provide: ErrorHandler,
            useClass: CustomErrorHandler
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
