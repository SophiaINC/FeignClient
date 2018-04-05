/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abel.cruz.feignclient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author acruzb
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@Controller
public class FeignClientApplication {
    
    final static Logger LOGGER = Logger.getLogger(FeignClientApplication.class);
    
    @Autowired
    private GreetingClient greetingClient;
 
    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
    }
 
    @RequestMapping("/get-greeting")
    public String greeting(Model model) {
        LOGGER.info("Se solicita la el servicio /get-greeting al cliente.");
        model.addAttribute("greeting", greetingClient.greeting());
        return "greeting-view";
    }
}
