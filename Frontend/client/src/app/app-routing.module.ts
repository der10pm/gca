import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DefaultLayoutComponent } from './containers/default-layout/default-layout.component';
import { HomeComponent } from './home/home.component';
import { CartComponent } from './cart/cart.component';
import { OderConfirmComponent } from './oder-confirm/oder-confirm.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },

  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: 'cart',
        component: CartComponent
      },
      {
        path: 'order-confirm',
        component: OderConfirmComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
