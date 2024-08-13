// This is your test publishable API key.
const stripe = Stripe("pk_test_51OL1SZEE3iAsaZHFl9Icdjscg4Dp5Z1i0tFJdNuXZ3qicMzIXMrew4DeOKN3MTgRl3l9ty3cDNMe6jq7X41wdvyy00uReJD0Ww");

initialize();

// Create a Checkout Session as soon as the page loads
async function initialize() {
  const response = await fetch("/create-checkout-session", {
    method: "POST",
  });

  const { clientSecret,  } = await response.json();

  const checkout = await stripe.initEmbeddedCheckout({
    clientSecret,
  });

  // Mount Checkout
  checkout.mount('#checkout');
}