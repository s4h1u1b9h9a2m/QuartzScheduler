package com.carecloud.publish.service;

import org.quartz.*;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ScheduleJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("Job Calling");

    }

}
