package org.example.flowin2.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async("asyncExecutor")
    public void sendWelcomeEmail(String to) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("🎧 ¡Bienvenido a FlowIn!");

            String htmlContent =
                    "<html>" +
                            "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 0; margin: 0;'>" +
                            "<div style='max-width: 600px; margin: auto; background-color: white; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1);'>" +
                            "<div style='background-color: #13ad32; padding: 20px; color: white; text-align: center;'>" +
                            "<h1 style='margin: 0;'>🎶 FlowIn</h1>" +
                            "<p style='margin: 5px 0 0;'>Tu nueva red para compartir música</p>" +
                            "</div>" +
                            "<div style='padding: 30px;'>" +
                            "<h2 style='color: #4A00E0;'>¡Bienvenido/a! 🎉</h2>" +
                            "<p>Estamos <strong>muy emocionados</strong> de tenerte en FlowIn. Aquí podrás:</p>" +
                            "<ul>" +
                            "<li>🎵 Compartir tus canciones favoritas</li>" +
                            "<li>💬 Conectar con otros amantes de la música</li>" +
                            "<li>📻 Crear salas para escuchar música en tiempo real</li>" +
                            "</ul>" +
                            "<p style='margin-top: 20px;'>Haz clic en el siguiente botón para comenzar:</p>" +
                            "<div style='text-align: center; margin: 30px 0;'>" +
                            "<a href='https://flowin.tuapp.com' " +
                            "style='background-color: #13ad32; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-size: 16px;'>Explorar FlowIn 🚀</a>" +
                            "</div>" +
                            "<p style='color: #777;'>Gracias por unirte a FlowIn. ¡Nos vemos dentro! 👋</p>" +
                            "</div>" +
                            "<div style='background-color: #eeeeee; text-align: center; padding: 10px; font-size: 12px; color: #888;'>" +
                            "© 2025 FlowIn • Todos los derechos reservados" +
                            "</div>" +
                            "</div>" +
                            "</body>" +
                            "</html>";

            helper.setText(htmlContent, true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
