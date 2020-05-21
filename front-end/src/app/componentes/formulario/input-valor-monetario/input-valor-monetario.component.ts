import { Component, OnInit } from '@angular/core';
import { InputTextComponent } from '../input-text/input-text.component';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
    selector: 'app-input-valor-monetario',
    templateUrl: './input-valor-monetario.component.html',
    styleUrls: ['./input-valor-monetario.component.scss'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: InputValorMonetarioComponent,
            multi: true
        }
    ],
})
export class InputValorMonetarioComponent extends InputTextComponent implements OnInit {

    ngOnInit(): void {
    }


    focus() {
        this.focused = true;
        let valor = this.limpaNumero();
        if (valor == '0.00') {
            this.onTouched('')
        } else {
            this.onTouched(valor);
        }

    }

    change() {
        let valor = this.limpaNumero();
        console.log(valor);
        if (valor == '0.00') {
            this.onChange('')
        } else {
            this.onChange(valor);
        }
        this.setDirty();
    }

    private limpaNumero() {
        let value = this.texto;
        value = value
            .replace('R$ ', '')
            .replace(/[.]/g, '')
            .replace(',', '.')
        return value;
    }
}
