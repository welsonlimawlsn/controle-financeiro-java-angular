import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appLocalModal]'
})
export class LocalModalDirective {

  constructor(public viewContainerRef: ViewContainerRef) { }

}
