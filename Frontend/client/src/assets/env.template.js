(function(window) {
  window["env"] = window["env"] || {};

  // Environment variables
  window["env"]["catalogUrl"] = "${CART_URL}";
  window["env"]["cartUrl"] = "${CATALOG_URL}";
  window["env"]["checkoutUrl"] = "${CHECKOUT_URL}";

  window["env"]["cartUser"] = "${CART_USER}";
  window["env"]["cartPw"] = "${CART_PW}";

  window["env"]["checkoutUser"] = "${CHECKOUT_USER}";
  window["env"]["checkoutPw"] = "${CHECKOUT_PW}";

  window["env"]["catalogUser"] = "${CATALOG_USER}";
  window["env"]["CatalogPw"] = "${CATALOG_PW}";
})(this);
