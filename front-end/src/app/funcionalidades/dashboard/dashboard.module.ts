import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { RouterModule } from '@angular/router';
import { ContasComponent } from './contas/contas.component';
import { ComponentesModule } from '../../componentes/componentes.module';


const routes = [
    {
        path: '',
        component: DashboardComponent
    }
];

@NgModule({
    declarations: [DashboardComponent, ContasComponent],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        ComponentesModule
    ]
})
export class DashboardModule {
}
