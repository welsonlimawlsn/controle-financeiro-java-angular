import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ModalService } from './modal.service';

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.scss']
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
        this.onConfirma.emit();
    }

    cancela() {
        this.modalService.close()
        this.onCancela.emit();
    }
}
