import { Injectable } from '@angular/core';
import { LoginUsuarioRespostaDTO, NovoDispositivoRespostaDTO, TokenDTO, UsuarioDTO } from '../../servicos';

@Injectable({
    providedIn: 'root'
})
export class SessaoService {

    private _dispositivo: string;
    private _token: TokenDTO;
    private _usuario: UsuarioDTO;

    constructor() {
        this._dispositivo = localStorage.getItem('dispositivo');
        this._token = JSON.parse(sessionStorage.getItem('token'));
        this._usuario = JSON.parse(sessionStorage.getItem('usuario'));
    }

    novoDispositivo(resposta: NovoDispositivoRespostaDTO) {
        this._dispositivo = resposta.idDispositivo;
        localStorage.setItem('dispositivo', this._dispositivo);
    }

    novaSessao(resposta: LoginUsuarioRespostaDTO) {
        this._token = resposta.token;
        this._usuario = resposta.usuario;
        sessionStorage.setItem('token', JSON.stringify(this._token));
        sessionStorage.setItem('usuario', JSON.stringify(this._usuario));
    }

    logout() {
        this._token = undefined;
        this._usuario = undefined;
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('usuario');
    }

    isAutenticado(): boolean {
        return !!this._token && !!this._usuario;
    }

    isDispositivoCadastrado(): boolean {
        return !!this._dispositivo;
    }

    get dispositivo(): string {
        return this._dispositivo;
    }


    get token(): TokenDTO {
        return this._token;
    }

    get usuario(): UsuarioDTO {
        return this._usuario;
    }
}
