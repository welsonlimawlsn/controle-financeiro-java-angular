import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RequisicaoService } from '../../../../componentes/http/requisicao.service';
import { DefaultService } from '../../../../servicos';
import { ModalService } from '../../../../componentes/modal/modal.service';

@Component({
    selector: 'app-nova-conta',
    templateUrl: './nova-conta.component.html',
    styleUrls: ['./nova-conta.component.scss']
})
export class NovaContaComponent implements OnInit {
    formularioNovaConta: FormGroup;

    constructor(
        private fb: FormBuilder,
        private requisicaoService: RequisicaoService,
        private requisicao: DefaultService,
        private modalService: ModalService
    ) {
    }

    ngOnInit(): void {
        this.formularioNovaConta = this.fb.group({
            nome: ['', Validators.required],
            valorInicial: ['', Validators.required]
        })
    }

    criaConta() {
        this.requisicaoService.realizaRequisicao(
            this.requisicao.novaConta(this.formularioNovaConta.value)
        ).subscribe(() => this.modalService.close());
    }
}
