import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { CartService } from './services/cart.service';

@Component({
  selector: 'body',
  template: '<router-outlet></router-outlet>'
})
export class AppComponent implements OnInit {
  constructor(private router: Router, private cartService: CartService) {}

  ngOnInit(){
    this.manageCart();

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0);
    });
  }

  public manageCart(){
    const localName = 'cartID';
    const id = localStorage.getItem(localName);

    if (id){
      console.log('load CardID');
      this.cartService.setLocalCardID(id);
    }else{
      this.cartService.newCart().subscribe( x => localStorage.setItem(localName, x));
    }
  }

}


