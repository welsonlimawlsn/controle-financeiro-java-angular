import { Component, Input, OnInit } from '@angular/core';

@Component({
    selector: 'app-informacao',
    templateUrl: './informacao.component.html',
    styleUrls: ['./informacao.component.scss']
})
export class InformacaoComponent implements OnInit {
    @Input() label: string;
    @Input() valor: string;
    @Input() valorEscondido = false;
    escondido;

    constructor() {
    }

    ngOnInit(): void {
        this.escondido = this.valorEscondido;
    }

    toggle(event: boolean) {
        this.escondido = event;
    }
}
