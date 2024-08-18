package be.iccbxl.pid.Controller;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @PostMapping("/create-checkout-session")
    public String createCheckoutSession(@RequestParam("amount") String amountStr, Model model) {
        Stripe.apiKey = stripeApiKey;

        try {
            // Convertir le montant en centimes
            int amount = (int) (Double.parseDouble(amountStr.replace(",", ".")) * 100);

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("eur")
                                                    .setUnitAmount((long) amount)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Réservation de places")
                                                                    .build())
                                                    .build())
                                    .setQuantity(1L)
                                    .build())
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/payment/success")
                    .setCancelUrl("http://localhost:8080/payment/error")
                    .build();

            Session session = Session.create(params);

            // Ajouter le sessionId au modèle
            model.addAttribute("sessionId", session.getId());

            // Rediriger vers la page checkout.html (généré par Stripe Checkout)
            return "redirect:" + session.getUrl(); // redirection vers l'URL de Stripe Checkout
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/payment/error";
        }
    }

    @RequestMapping("/success")
    public String paymentSuccess() {
        return "stripe/success"; // Correspond à src/main/resources/stripe/success.html
    }

    @RequestMapping("/error")
    public String paymentError() {
        return "stripe/error"; // Correspond à src/main/resources/stripe/error.html
    }
}
