import { Component, OnInit } from '@angular/core';
import { navItems } from '../../_nav';
import { Observable, Subject } from 'rxjs';

import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.css']
})
export class DefaultLayoutComponent {
  public sidebarMinimized = false;
  public navItems = navItems;
  public itemCounter$: Observable<number>;

  constructor(private cartService: CartService) {
    this.itemCounter$ = this.cartService.itemCounter$.asObservable();
   }

   toggleMinimize(e){
     this.sidebarMinimized = e;
   }




}
