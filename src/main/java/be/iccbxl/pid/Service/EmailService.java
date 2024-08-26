package be.iccbxl.pid.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendConfirmationEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("serifi-100@hotmail.com"); // Explicitement définir l'expéditeur
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

    public void sendTestEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("serifihicham@hotmail.fr"); // Remplacez par votre adresse e-mail pour recevoir l'e-mail de test
        message.setSubject("Test Email");
        message.setText("This is a test email to verify SMTP settings.");
        emailSender.send(message);
    }

}
