import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputTextComponent } from './formulario/input-text/input-text.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoadingComponent } from './loading/loading.component';
import { MensagemComponent } from './mensagem-erro/mensagem.component';
import { InformacaoComponent } from './informacao/informacao.component';
import { TogglePasswordComponent } from './formulario/toggle-password/toggle-password.component';
import { CodigoSegurancaComponent } from './seguranca/codigo-seguranca/codigo-seguranca.component';
import { ModalComponent } from './modal/modal.component';
import { LocalModalDirective } from './local-modal.directive';
import { InputValorMonetarioComponent } from './formulario/input-valor-monetario/input-valor-monetario.component';
import { SomenteNumeroDirective } from './formulario/diretivas/somente-numero.directive';


@NgModule({
    declarations: [
        InputTextComponent,
        LoadingComponent,
        MensagemComponent,
        InformacaoComponent,
        TogglePasswordComponent,
        CodigoSegurancaComponent,
        ModalComponent,
        LocalModalDirective,
        InputValorMonetarioComponent,
        SomenteNumeroDirective
    ],
    exports: [
        InputTextComponent,
        LoadingComponent,
        MensagemComponent,
        InformacaoComponent,
        CodigoSegurancaComponent,
        ModalComponent,
        LocalModalDirective,
        InputValorMonetarioComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule
    ]
})
export class ComponentesModule {
}
