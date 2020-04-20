import { Component, OnInit } from '@angular/core';
import { CodigoSegurancaService } from '../codigo-seguranca.service';
import { LoadingService } from '../../loading/loading.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-codigo-seguranca',
    templateUrl: './codigo-seguranca.component.html',
    styleUrls: ['./codigo-seguranca.component.scss']
})
export class CodigoSegurancaComponent implements OnInit {

    mostraJanela = false;


    formulario: FormGroup;

    constructor(
        private codigoSegurancaService: CodigoSegurancaService,
        private loadingService: LoadingService,
        private fb: FormBuilder
    ) {
    }

    ngOnInit(): void {
        this.codigoSegurancaService.apresentaJanela.subscribe(() => {
            this.mostraJanela = true;
            this.loadingService.hide();
        });

        this.formulario = this.fb.group({
            codigo: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]]
        })
    }

    enviaCodigo() {
        this.mostraJanela = false;
        this.codigoSegurancaService.enviaCodigo(this.formulario.value.codigo);
    }

}
