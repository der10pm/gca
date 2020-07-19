import { Component, OnInit } from '@angular/core';
import {CartService} from '../services/cart.service';
import {Observable, Subject} from 'rxjs';

import {CheckoutService} from '../services/checkout.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  public catalog$: Observable<any[]>;

  public registerForm: FormGroup;

  submitted = false;

  constructor(public checkout: CheckoutService,
              public cart: CartService,
              private formBuilder: FormBuilder,
              private router: Router) {

    this.catalog$ =   checkout.getCheckout(cart.getLocalCardID());
    
  }

  ngOnInit(): void {

    this.registerForm = this.formBuilder.group({
      email: ['max@mustermann.de', [Validators.required, Validators.email]],
      street: ['Musterstrasse 1', Validators.required],
      zip: ['12345', [Validators.required]],
      city: ['Musterstadt', [Validators.required]],
      country: ['Deutschland', [Validators.required]],
      creditCard: ['1000', [Validators.required]],
      month: ['1', [Validators.required]],
      year: ['1970', [Validators.required]],
      cvv: ['100', [Validators.required]]
    });

  }

  get f() { return this.registerForm.controls; }

  emptyCart() {
    this.catalog$ = null;
    this.cart.emptyCart();
    this.router.navigate(['/']);
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    this.checkout.confirmCheckout(this.cart.getLocalCardID(),this.registerForm.value)
      .subscribe(x => {
      this.checkout.confirmCheckoutData = x;
      console.log(x);
      this.catalog$ = null;
      this.cart.emptyCart();
      this.router.navigate(['/order-confirm']);
      });
  }
}
