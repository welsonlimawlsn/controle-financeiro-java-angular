import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { SessaoService } from './componentes/seguranca/sessao.service';

@Injectable({
    providedIn: 'root',
})
export class LoginCadastroAuthGuard implements CanActivate {

    constructor(private sessaoService: SessaoService, private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        if (!this.sessaoService.isAutenticado()) {
            return true;
        }
        this.router.navigate(['dashboard']);
        return false;
    }

}
