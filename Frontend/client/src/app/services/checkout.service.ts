import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Subject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ObserveOnSubscriber } from 'rxjs/internal/operators/observeOn';
@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private checkoutApi = `/checkout/api/`;

  public confirmCheckoutData: any;

  private checkoutApiUser = `${environment.CHECKOUT_USER}`;
  private checkoutApiPW = `${environment.CHECKOUT_PW}`;

  constructor( private http: HttpClient) {
    console.log({Checkout: { url: this.checkoutApi, PW: this.checkoutApiPW, User: this.checkoutApiUser}});
   }

   public getCheckout(cardID: string): Observable<any>{
    const headers = new HttpHeaders( {
      authorization: 'Basic ' + btoa(this.checkoutApiUser + ':' + this.checkoutApiPW)
    } );

    return this.http.get<any>(this.checkoutApi + cardID, {headers});
   }

   public confirmCheckout(cardID: string, body: any): Observable<any>{
    const headers = new HttpHeaders( {
      authorization: 'Basic ' + btoa(this.checkoutApiUser + ':' + this.checkoutApiPW)
    } );

    return this.http.post<any>(this.checkoutApi + cardID, body, {headers});
   }
}
