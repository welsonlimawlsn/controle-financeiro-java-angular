import { AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { ControlValueAccessor, FormGroup, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
    selector: 'app-input-text',
    templateUrl: './input-text.component.html',
    styleUrls: ['./input-text.component.scss'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: InputTextComponent,
            multi: true
        }
    ],
    animations: [
        trigger('comportamentoLabel', [
            state('labelRecolhida', style({
                fontSize: '.8rem',
                marginTop: '-30px'
            })),
            state('labelNormal', style({
                fontSize: '1rem',
                marginTop: '-13px'
            })),
            transition('labelRecolhida => labelNormal', [
                animate('0.1s ease-in-out')
            ]),
            transition('labelNormal => labelRecolhida', [
                animate('0.1s ease-in-out')
            ])
        ]),
        trigger('mensagemErro', [
            state('void', style({
                transform: 'translate(0, -10px)',
                opacity: 0,
                height: 0
            })),
            transition('* => void', animate('.1s')),
            transition('void => *', animate('.1s', style({
                opacity: 1,
                transform: 'translate(0, 0)',
                height: 'auto'
            })))
            ]
        )
    ]
})
export class InputTextComponent implements OnInit, ControlValueAccessor, AfterViewInit {

    @Input() label: string = '';
    @Input() id;
    @Input() formGroup: FormGroup;
    @Input() formControlName: string;
    @Input() autoFocus: boolean = false;
    @Input() password: boolean = false;
    texto: string;
    oldValue: string = '';
    focused: boolean = false;
    onChange: any;
    onTouched: any;
    mostraSenha: boolean = false;
    dirty: boolean = false;
    @ViewChild('inputElement')
    inputElement: ElementRef;

    constructor() {
    }

    ngOnInit(): void {
    }

    ngAfterViewInit(): void {
        if (this.autoFocus) {
            setTimeout(() => this.inputElement.nativeElement.focus());
        }
    }

    get labelRecolhida(): boolean {
        return !!this.texto || this.focused;
    }

    focus() {
        this.focused = true;
        this.onTouched(this.texto);
    }

    blur() {
        this.focused = false;
    }

    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    writeValue(obj: any): void {
        this.texto = obj;
    }

    change() {
        this.onChange(this.texto);
        if (this.texto !== this.oldValue) {
            this.dirty = true;
        }
        this.oldValue = this.texto;
    }

    errors() {
        return this.getAbstractControl().errors;
    }

    private getAbstractControl() {
        return this.formGroup.get(this.formControlName);
    }

    togglePassword(event: boolean) {
        this.mostraSenha = event;
    }
}
