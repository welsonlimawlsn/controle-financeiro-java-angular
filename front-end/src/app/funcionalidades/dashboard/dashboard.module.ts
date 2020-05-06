import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { RouterModule } from '@angular/router';
import { ContasComponent } from './contas/contas.component';
import { ComponentesModule } from '../../componentes/componentes.module';
import { NovaContaComponent } from './contas/nova-conta/nova-conta.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ConfirmacaoDeleteComponent } from './contas/confirmacao-delete/confirmacao-delete.component';
import { AtualizaContaComponent } from './contas/atualiza-conta/atualiza-conta.component';


const routes = [
    {
        path: '',
        component: DashboardComponent
    }
];

@NgModule({
    declarations: [
        DashboardComponent,
        ContasComponent,
        NovaContaComponent,
        ConfirmacaoDeleteComponent,
        AtualizaContaComponent
    ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        ComponentesModule,
        ReactiveFormsModule
    ]
})
export class DashboardModule {
}
