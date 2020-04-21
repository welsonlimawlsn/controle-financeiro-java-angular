import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NovoUsuarioComponent } from './novo-usuario/novo-usuario.component';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { ComponentesModule } from '../../componentes/componentes.module';
import { LoginUsuarioComponent } from './login-usuario/login-usuario.component';
import { RecuperacaoSenhaComponent } from './recuperacao-senha/recuperacao-senha.component';


const routes: Routes = [
    {
        path: 'novo',
        component: NovoUsuarioComponent
    },
    {
        path: 'login',
        component: LoginUsuarioComponent
    },
    {
        path: 'recuperacao-senha',
        component: RecuperacaoSenhaComponent
    }
];

@NgModule({
    declarations: [NovoUsuarioComponent, LoginUsuarioComponent, RecuperacaoSenhaComponent],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        ReactiveFormsModule,
        ComponentesModule
    ]
})
export class UsuarioModule {
}
