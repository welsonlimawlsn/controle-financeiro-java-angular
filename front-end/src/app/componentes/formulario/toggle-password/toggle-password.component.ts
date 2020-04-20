import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-toggle-password',
    templateUrl: './toggle-password.component.html',
    styleUrls: ['./toggle-password.component.scss']
})
export class TogglePasswordComponent implements OnInit {

    @Input() mostraSenha: boolean;

    @Output()
    onClick: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor() {
    }

    ngOnInit(): void {
    }

    togglePassword() {
        this.mostraSenha = !this.mostraSenha;
        this.onClick.emit(this.mostraSenha);
    }
}
