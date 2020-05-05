import { AbstractControl, ValidationErrors } from '@angular/forms';

export class Validadores {
    static email(control: AbstractControl): ValidationErrors | null {
        if (control.value) {
            let emailPattern =
                /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (emailPattern.test(control.value)) {
                return null;
            }
            return {
                'email': true
            }
        }
        return null;
    }
}
