import { EventEmitter, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ChamadaCodigoSeguraca } from '../http/requisicao.service';

@Injectable({
    providedIn: 'root'
})
export class CodigoSegurancaService {

    submeteCodigo = new EventEmitter<string>();

    apresentaJanela = new EventEmitter();

    constructor() {
    }

    informaCodigoSeguranca(chamada: ChamadaCodigoSeguraca): Observable<ChamadaCodigoSeguraca> {
        return new Observable<ChamadaCodigoSeguraca>(subscriber => {
            this.apresentaJanela.emit();
            this.submeteCodigo.subscribe((value) => {
                chamada.codigo = value;
                subscriber.next(chamada);
                subscriber.complete();
            });
        });
    }

    enviaCodigo(codigo: string) {
        this.submeteCodigo.emit(codigo);
    }
}
