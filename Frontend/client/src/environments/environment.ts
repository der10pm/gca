// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  CART_URL: 'http://localhost:8081',
  CATALOG_URL: 'http://localhost:8080',
  CHECKOUT_URL: 'http://localhost:8083',

  CART_USER: 'cart',
  CART_PW: 'cart_pw',
  CHECKOUT_USER: 'checkout',
  CHECKOUT_PW: 'checkout_pw',
  CATALOG_USER: 'catalog',
  CATALOG_PW: 'catalog_pw'

};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
