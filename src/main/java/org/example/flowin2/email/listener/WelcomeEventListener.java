package org.example.flowin2.email.listener;
import org.example.flowin2.application.usuario.UserRegisteredEvent;
import org.example.flowin2.email.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class WelcomeEventListener {
    private final EmailService emailService;

    public WelcomeEventListener(org.example.flowin2.email.service.EmailService emailService) {
        this.emailService = emailService;
    }

    @Async("asyncExecutor")
    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        emailService.sendWelcomeEmail(event.getEmail());
    }
}
