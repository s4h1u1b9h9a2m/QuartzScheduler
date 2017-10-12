package com.shub.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Shubhanshuc on 24/06/17.
 */
@Component
public class MyScheduler {

    public void Schedule() throws SchedulerException{

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //create first JobDetail and Trigger
        JobDetail jobDetail = JobBuilder.newJob(PrintNameJob.class).withIdentity("ramjob1", "ourgroup").build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("ramtrigger", "ourgroup")
                .startAt(new Date(Calendar.getInstance().getTimeInMillis()+ 5000))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
                        .withRepeatCount(30)).build();
        //add passing parameters to JobDataMap for first JobDetail
        jobDetail.getJobDataMap().put(PrintNameJob.NAME, "RAM");
        jobDetail.getJobDataMap().put(PrintNameJob.COUNT, 11);
        scheduler.scheduleJob(jobDetail, trigger);
        //create second JobDetail and Trigger
//        jobDetail = JobBuilder.newJob(PrintNameJob.class).withIdentity("rahimjob", "ourgroup").build();
//        trigger = TriggerBuilder.newTrigger().withIdentity("rahimtrigger", "ourgroup")
//                .startAt(new Date(Calendar.getInstance().getTimeInMillis()+ 5000))
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
//                        .withRepeatCount(3)).build();
//        //add passing parameters to JobDataMap for second JobDetail
//        jobDetail.getJobDataMap().put(PrintNameJob.NAME, "RAHIM");
//        jobDetail.getJobDataMap().put(PrintNameJob.COUNT, 21);
//        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
//        try {
//            //wait for 30 seconds to finish the job
//            Thread.sleep(300000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //shutdown scheduler gracefully
//        scheduler.shutdown(true);

    }

    public void getJobs(){

        try{

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();

                    System.out.println("[jobName] : " + jobName + " [groupName] : "
                            + jobGroup + " - " + nextFireTime);

                }

            }

        }catch(Exception e){
            System.out.println(e);
        }


    }

    @PostConstruct
    public void reSchedule() throws SchedulerException
    {

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        for (String groupName : scheduler.getJobGroupNames()) {

            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();

                for(Trigger trigger : scheduler.getTriggersOfJob(jobKey)){
                    scheduler.rescheduleJob(trigger.getKey(), trigger);
                }

                System.out.println("[jobName] : " + jobName + " [groupName] : " + jobGroup );

            }

        }

        // start the scheduler
        scheduler.start();
    }

}
