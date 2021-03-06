import { ComponentFactory, ComponentFactoryResolver, EventEmitter, Injectable, Type } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ModalService {

    private _eventEmitter = new EventEmitter<ComponentFactory<any>>();

    ultimaAcao: AcaoModal;

    data: any;

    constructor(private componentFactoryResolver: ComponentFactoryResolver) {
    }

    show(component: Type<any>, data?: any): Observable<any> {
        this._eventEmitter.emit(this.componentFactoryResolver.resolveComponentFactory(component))
        this.data = data;
        return new Observable<any>(subscriber => {
            this._eventEmitter.subscribe(value => {
                if (!value) {
                    subscriber.next();
                    subscriber.complete();
                }
            })
        })
    }

    close() {
        this._eventEmitter.emit(undefined);
    }


    get eventEmitter(): EventEmitter<ComponentFactory<any>> {
        return this._eventEmitter;
    }
}

export enum AcaoModal {
    CONFIRMA,
    CANCELA
}
