import { Component, HostBinding } from '@angular/core';

@Component({
  selector: 'button[base]',
  templateUrl: './button.component.html',
})
export class ButtonComponent {
  @HostBinding() class =
    'bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded';
}
