package ru.mativ.lrfbb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

import ru.mativ.lrfbb.data.service.UserService;

@SpringBootApplication
public class LrfBbApplication {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(LrfBbApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void checkManager() {
        try {
            userService.checkManagerUser();
        } catch (Exception e) {
            System.out.println("Error create manager: " + e.getMessage());
        }
    }

    @EventListener(InteractiveAuthenticationSuccessEvent.class)
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        System.out.println("### LOGIN: " + event);
        //TODO перенести в отдельный класс событий
    }
}
