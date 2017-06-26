package com.shub.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by Shubhanshuc on 24/06/17.
 */
public class MyScheduler {

    public void Schedule() throws SchedulerException{

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //create first JobDetail and Trigger
        JobDetail jobDetail = JobBuilder.newJob(PrintNameJob.class).withIdentity("ramjob", "ourgroup").build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("ramtrigger", "ourgroup")
                .startAt(new Date(Calendar.getInstance().getTimeInMillis()+ 5000))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
                        .withRepeatCount(3)).build();
        //add passing parameters to JobDataMap for first JobDetail
        jobDetail.getJobDataMap().put(PrintNameJob.NAME, "RAM");
        jobDetail.getJobDataMap().put(PrintNameJob.COUNT, 11);
        scheduler.scheduleJob(jobDetail, trigger);
        //create second JobDetail and Trigger
        jobDetail = JobBuilder.newJob(PrintNameJob.class).withIdentity("rahimjob", "ourgroup").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("rahimtrigger", "ourgroup")
                .startAt(new Date(Calendar.getInstance().getTimeInMillis()+ 5000))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
                        .withRepeatCount(3)).build();
        //add passing parameters to JobDataMap for second JobDetail
        jobDetail.getJobDataMap().put(PrintNameJob.NAME, "RAHIM");
        jobDetail.getJobDataMap().put(PrintNameJob.COUNT, 21);
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        try {
            //wait for 30 seconds to finish the job
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //shutdown scheduler gracefully
        scheduler.shutdown(true);

    }

}
