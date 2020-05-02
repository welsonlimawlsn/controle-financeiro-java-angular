import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuarioModule } from './funcionalidades/usuario/usuario.module';
import { DashboardModule } from './funcionalidades/dashboard/dashboard.module';
import { DashboardAuthGuard } from './dashboard-auth-guard.service';


const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'usuario',
                loadChildren: () => UsuarioModule
            },
            {
                path: 'dashboard',
                loadChildren: () => DashboardModule,
                canActivate: [DashboardAuthGuard]
            },
            {
                path: '',
                redirectTo: 'dashboard',
                pathMatch: 'full'
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
