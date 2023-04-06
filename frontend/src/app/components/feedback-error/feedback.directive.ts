import {Directive, HostBinding} from '@angular/core';
import {NgControl} from "@angular/forms";

@Directive({
  selector: '[appFeedback]',
  exportAs:  'appFeedback'
})
export class FeedbackDirective {

  @HostBinding('class.is-invalid') get isInvalid(){
    return this.ngControl.invalid && (this.ngControl.touched || this.ngControl.dirty)
  };

  @HostBinding('class.is-valid') get isValid(){
    return this.ngControl.valid && (this.ngControl.touched || this.ngControl.dirty)
  };

  constructor(private ngControl : NgControl) { }

  control() : NgControl{
    return this.ngControl;
  }

}
