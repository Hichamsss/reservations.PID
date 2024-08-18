package be.iccbxl.pid.Controller;

import be.iccbxl.pid.Dto.SessionDTO;
import be.iccbxl.pid.Model.PaymentSession;
import be.iccbxl.pid.Service.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

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

            // Créer et enregistrer la session de paiement dans la base de données
            PaymentSession paymentSession = new PaymentSession();
            paymentSession.setSessionId(session.getId());
            paymentSession.setProductName("Réservation de places");
            paymentSession.setAmount(amount);
            paymentSession.setCurrency("eur");
            paymentSession.setCreatedDate(new Date());

            paymentService.addSession(paymentSession);

            model.addAttribute("sessionId", session.getId());

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

    @GetMapping("/sessions-json")
    @ResponseBody
    public List<SessionDTO> getAllSessionsJson() {
        return paymentService.getAllSessions();
    }

    @GetMapping("/sessions-rss")
    public ResponseEntity<String> getAllSessionsRss() {
        try {
            List<SessionDTO> sessions = paymentService.getAllSessions();

            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType("rss_2.0");
            feed.setTitle("Payment Sessions Feed");
            feed.setLink("http://localhost:8080/payment/sessions-rss");
            feed.setDescription("List of payment sessions");
            feed.setPublishedDate(new Date());

            List<SyndEntry> entries = new ArrayList<>();
            for (SessionDTO session : sessions) {
                SyndEntry entry = new SyndEntryImpl();
                entry.setTitle("Payment for " + session.getProductName());
                entry.setLink("http://localhost:8080/payment/session/" + session.getSessionId());
                entry.setPublishedDate(session.getCreatedDate());

                SyndContent description = new SyndContentImpl();
                description.setType("text/plain");
                description.setValue("Amount: " + session.getAmount() + " " + session.getCurrency());

                entry.setDescription(description);
                entries.add(entry);
            }

            feed.setEntries(entries);

            SyndFeedOutput output = new SyndFeedOutput();
            String rssFeed = output.outputString(feed);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/rss+xml; charset=UTF-8");

            return ResponseEntity.ok().headers(headers).body(rssFeed);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating RSS feed");
        }
    }
}
