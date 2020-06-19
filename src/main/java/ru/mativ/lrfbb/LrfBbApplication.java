package ru.mativ.lrfbb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

import ru.mativ.lrfbb.data.ManagerUserUtil;

@SpringBootApplication
public class LrfBbApplication {

    @Autowired
    private ManagerUserUtil managerUserUtil;

    public static void main(String[] args) {
        SpringApplication.run(LrfBbApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void checkManager() {
        System.out.println(managerUserUtil.getOrCreateManagerUser());
    }

    @EventListener(InteractiveAuthenticationSuccessEvent.class)
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        System.out.println("### LOGIN: " + event);
        //TODO перенести в отдельный класс событий
    }
}
