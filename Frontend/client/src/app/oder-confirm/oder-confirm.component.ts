import { Component, OnInit } from '@angular/core';
import { CheckoutService } from '../services/checkout.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-oder-confirm',
  templateUrl: './oder-confirm.component.html',
  styleUrls: ['./oder-confirm.component.css']
})
export class OderConfirmComponent implements OnInit {

  constructor(public checkout: CheckoutService, private router: Router) { }

  public orderData: any;

  ngOnInit(): void {
    this.orderData = this.checkout.confirmCheckoutData;
    if (!this.orderData){
      this.router.navigate(['/']);
    }
  }

  getAddress(user: any): string{
    return '${user.street}, ${user.zip} - ${user.city}, ${user.country}';
  }

}
