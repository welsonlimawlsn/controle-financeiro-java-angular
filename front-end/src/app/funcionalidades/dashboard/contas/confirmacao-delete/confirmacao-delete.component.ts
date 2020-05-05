import { Component, OnInit } from '@angular/core';
import { ModalService } from '../../../../componentes/modal/modal.service';

@Component({
    selector: 'app-confirmacao-delete',
    templateUrl: './confirmacao-delete.component.html',
    styleUrls: ['./confirmacao-delete.component.scss']
})
export class ConfirmacaoDeleteComponent implements OnInit {

    constructor(private modalService: ModalService) {
    }

    ngOnInit(): void {
    }

    confirma() {
        this.modalService.close();
    }

}
