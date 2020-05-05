import { ComponentFactory, ComponentFactoryResolver, EventEmitter, Injectable, Type } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ModalService {

    private _eventEmitter = new EventEmitter<ComponentFactory<any>>();

    constructor(private componentFactoryResolver: ComponentFactoryResolver) {
    }

    show(component: Type<any>) {
        this._eventEmitter.emit(this.componentFactoryResolver.resolveComponentFactory(component))
    }

    close() {
        this._eventEmitter.emit(undefined);
    }


    get eventEmitter(): EventEmitter<ComponentFactory<any>> {
        return this._eventEmitter;
    }
}
