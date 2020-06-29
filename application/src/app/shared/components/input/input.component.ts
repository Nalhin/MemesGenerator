import { Component, HostBinding } from '@angular/core';

@Component({
  selector: 'input[base]',
  templateUrl: './input.component.html',
})
export class InputComponent {
  @HostBinding('class') class = `shadow appearance-none border
    rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline`;
}
