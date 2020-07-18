import {AfterViewChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { CatalogService } from '../services/catalog.service';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private metaItemList: Subject<any> = new Subject<any>();
  public catalog$: Observable<any[]>;
  private realItemList: any[];

  constructor(private catalogService: CatalogService, private cartService: CartService) {
    this.catalog$ = this.metaItemList.asObservable();
    this.catalogService.getCatalog().subscribe( x => {
      x = (x as any[]);
      const cartItems = this.cartService.itemsInCart;
      this.realItemList = x.Filter( x => !cartItems.includes(x.id));
      this.metaItemList.next(this.realItemList);
    });
   }

  ngOnInit(): void {
  }

  addItemToCart(item: any){
    this.cartService.addItemToCart(item.id).subscribe( x => {
      this.cartService.itemCounter$.next(x.length);
      this.realItemList = this.realItemList.filter( x => {
        return x.id !== item.id;
      });
      this.cartService.itemsInCart.push(item.id);
      this.metaItemList.next(this.realItemList);
    });
  }

}
