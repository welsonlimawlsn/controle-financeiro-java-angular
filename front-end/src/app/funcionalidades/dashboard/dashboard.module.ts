import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { RouterModule } from '@angular/router';
import { ContasComponent } from './contas/contas.component';
import { ComponentesModule } from '../../componentes/componentes.module';
import { NovaContaComponent } from './contas/nova-conta/nova-conta.component';
import { ReactiveFormsModule } from '@angular/forms';


const routes = [
    {
        path: '',
        component: DashboardComponent
    }
];

@NgModule({
    declarations: [DashboardComponent, ContasComponent, NovaContaComponent],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        ComponentesModule,
        ReactiveFormsModule
    ]
})
export class DashboardModule {
}
