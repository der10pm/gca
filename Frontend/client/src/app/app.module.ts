import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { CartComponent } from './cart/cart.component';
import { OderConfirmComponent } from './oder-confirm/oder-confirm.component';
import { DefaultLayoutComponent } from './containers/default-layout/default-layout.component';

import { AppAsideModule, AppBreadcrumbModule, AppHeaderModule, AppFooterModule, AppSidebarModule } from '@coreui/angular';
import { HttpClientModule} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const APP_CONTAINERS = [
  DefaultLayoutComponent
];

@NgModule({
  declarations: [
    AppComponent,
    ...APP_CONTAINERS,
    HomeComponent,
    CartComponent,
    OderConfirmComponent,
    DefaultLayoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AppAsideModule,
    AppBreadcrumbModule,
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
