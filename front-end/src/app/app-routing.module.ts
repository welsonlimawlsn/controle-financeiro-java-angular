import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuarioModule } from './funcionalidades/usuario/usuario.module';


const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'usuario',
                loadChildren: () => UsuarioModule
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
