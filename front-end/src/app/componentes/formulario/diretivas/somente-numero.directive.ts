import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
    selector: '[somenteNumero]'
})
export class SomenteNumeroDirective {

    private teclasPermitidas: string[] = [
        'Backspace',
        'Delete',
        'Tab',
        'Escape',
        'Enter',
        'Home',
        'End',
        'ArrowLeft',
        'ArrowRight',
        'Clear',
        'Copy',
        'Paste',
    ]

    private teclasCtrlPermitidas: string[] = [
        'c',
        'v',
        'a',
        'x'
    ]

    inputElement: HTMLInputElement;

    constructor(public el: ElementRef) {
        this.inputElement = el.nativeElement;
    }

    @HostListener('keydown', ['$event'])
    onKeyDown(e: KeyboardEvent) {
        if (
            this.ehNumero(e)
            || this.ehTeclaPermitida(e)
            || this.ehCtrl(e)
        ) {
            return;
        }
        e.preventDefault();
    }

    @HostListener('keyup', ['event'])
    onKeyUp(e: KeyboardEvent) {
        let value = this.limpaNumero();

        console.log('Limpo: ' +value)

        while (value.length < 3) {
            value = '0' + value;
        }

        value = value.substr(0, value.length - 2) + ',' + value.substr(-2)

        for (let i = -6; i > -value.length; i -= 4) {
            value = value.substr(0, value.length - -i) + '.' + value.substr(i)
        }

        value = 'R$ ' + value;

        console.log(value);

        this.inputElement.value = value;
    }

    private limpaNumero() {
        let value = this.inputElement.value;
        value = value
            .replace('R$ ', '')
            .replace(/[.]|,/g, '')
        while (value.charAt(0) === '0') {
            value = value.substr(1);
        }
        return value;
    }

    private ehTeclaPermitida(e: KeyboardEvent) {
        return this.teclasPermitidas.indexOf(e.key) !== -1;
    }

    private ehNumero(e: KeyboardEvent) {
        return /[0-9]/.test(e.key);
    }

    private ehCtrl(e: KeyboardEvent) {
        return (e.ctrlKey || e.metaKey) && !e.shiftKey
            && this.teclasCtrlPermitidas.find((tecla) => tecla == e.key.toLowerCase());
    }
}
