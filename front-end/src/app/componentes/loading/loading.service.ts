import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class LoadingService {

    emitter = new EventEmitter<boolean>();

    constructor() {
    }

    show() {
        this.emitter.emit(true);
    }

    hide() {
        this.emitter.emit(false);
    }
}
