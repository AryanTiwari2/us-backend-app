package com.aryan.us_backend_app.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveTask {
    
    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 15*60*1000)
    public void pingSelf(){
        try{
        String response = restTemplate.getForObject("https://us-backend-app.onrender.com/profile/health", String.class);
        System.out.println(response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
