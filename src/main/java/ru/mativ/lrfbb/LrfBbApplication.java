package ru.mativ.lrfbb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

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
            e.printStackTrace();
        }
    }

    @EventListener(InteractiveAuthenticationSuccessEvent.class)
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        String username = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
        System.out.println("### LOGIN: " + username);
        //TODO перенести в отдельный класс событий
    }
}
