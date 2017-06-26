package com.shub.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@RestController
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/hello")
    public String RestCall(){
        return "Hello";
    }

    @RequestMapping("/schedule")
    public void Schedule(){

        try{
            new MyScheduler().Schedule();
        }catch (Exception e){
            System.out.println(e);
        }

    }

}
