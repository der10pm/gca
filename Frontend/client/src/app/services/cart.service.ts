import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Subject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartApi = '${environment.CART_URL/api';

  private currentCardID: string;
  private cartApiUser = '${environment.CART_USER}';
  private cartApiPW = '${environment.CART_PW}';

  public itemCounter$: Subject<number> = new Subject<number>();
  public itemsInCart: string[];
  public itemCounter = 0;


  constructor(private http: HttpClient) {
    console.log({Cart: { url : this.cartApi}});
    this.itemCounter$.asObservable().subscribe(x => this.itemCounter = x);
   }

   getCart(id: string): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(this.cartApiUser + ':' + this.cartApiPW)
    });

    return this.http.get<any>(this.cartApi + '/Carts/' + id, {headers}).pipe(map(x => {
      this.itemsInCart = x.items;
      this.itemCounter$.next(x.items.length);
      return x;
    }));
   }

   getLocalCardID(): string{
     return this.currentCardID;
   }

   newCart(): Observable<string>{
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(this.cartApiUser + ':' + this.cartApiPW)
    });

    return this.http.post<any>(this.cartApi + '/newCart', {}, {headers}).pipe(map(x => {
      console.log('newCart');
      this.itemsInCart = [];
      this.itemCounter$.next(0);
      this.currentCardID = x.id;
      return x;
    }));
   }

   addItemToCart(itemID: string): Observable<any>{
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(this.cartApiUser + ':' + this.cartApiPW)
    });

    return this.http.put<any>(this.cartApi + '/addItem' + this.currentCardID + '?newItem=' + itemID, {}, {headers});
   }

   setLocalCardID(localStorageID: string){
     this.getCart(localStorageID).subscribe();
     this.currentCardID = localStorageID;
   }

   deleteCart(itemdID: string): Observable<any>{
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(this.cartApiUser + ':' + this.cartApiPW)
    });

    return this.http.delete<any>(this.cartApi + '/delete/' + itemdID, {headers});
   }

   emptyCart(){
     const localName = 'cartID';
     const id = localStorage.getItem(localName);

     this.deleteCart(id).toPromise().then(() => {
       localStorage.removeItem(localName)
     }).then(() =>{
       this.newCart().subscribe(x => localStorage.setItem(localName, x));
     });
   }
}
