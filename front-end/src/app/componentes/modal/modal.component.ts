import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AcaoModal, ModalService } from './modal.service';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.scss'],
    animations: [
        trigger('fade', [
            state('void', style({
                opacity: 0
            })),
            transition('* => void', animate('.1s', style({
                opacity: 0,
            }))),
            transition('void => *', animate('.1s', style({
                opacity: 1,
            })))
        ]),
    ]
})
export class ModalComponent implements OnInit {
    visivel: boolean = true;

    @Output() onConfirma = new EventEmitter();
    @Output() onCancela = new EventEmitter();

    @Input() desabilitaConfirma = false;

    constructor(private modalService: ModalService) {
    }

    ngOnInit(): void {
    }

    confirma() {
        this.modalService.ultimaAcao = AcaoModal.CONFIRMA;
        this.onConfirma.emit();
    }

    cancela() {
        this.modalService.ultimaAcao = AcaoModal.CANCELA;
        this.modalService.close()
        this.onCancela.emit();
    }
}
