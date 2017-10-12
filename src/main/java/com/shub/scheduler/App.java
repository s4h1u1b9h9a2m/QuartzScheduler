package com.shub.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
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

//        JobDetail job = JobBuilder.newJob(PrintNameJob.class)
//                .withIdentity("dummyJobName", "group1").build();
//
//        job.getJobDataMap().put(PrintNameJob.NAME, "RAM");
//        job.getJobDataMap().put(PrintNameJob.COUNT, 11);
//
//        Trigger trigger = TriggerBuilder
//                .newTrigger()
//                .withIdentity("dummyTriggerName", "group1")
//                .withSchedule(
//                        CronScheduleBuilder.cronSchedule("0 52 12 1/1 * ? *"))
//                .build();
//
//        // schedule it
//        try{
//            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//            scheduler.start();
//            scheduler.scheduleJob(job, trigger);
//            scheduler.shutdown();
//        }catch (Exception e){
//            System.out.println(e);
//        }

    }

    @RequestMapping("/hello")
    public String RestCall(){
        return "Hello ";
    }

    @RequestMapping("/hello2")
    public String RestCall2(@RequestBody Long data){
        return "Hello " + data;
    }

    @RequestMapping("/schedule")
    public String Schedule(){

        try{
            new MyScheduler().Schedule();
            return "Scheduled";
        }catch (Exception e){
            System.out.println(e);
        }

        return "Not Scheduled";

    }

    @RequestMapping("/getJobs")
    public String getDetails(){

        try{
            new MyScheduler().getJobs();
            return "Details";
        }catch (Exception e){
            System.out.println(e);
        }

        return "Not Scheduled";

    }

//    @PostConstruct
//    public void reSchedule(){
//
//        try{
//            new MyScheduler().reSchedule();
//            //return "Details";
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//    }

}
