package com.codemaster.devflow.quartz.schedular;

import com.codemaster.devflow.enumeration.JobConfigEnumeration;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QuartzJobScheduler {

    private Scheduler scheduler;

    @Autowired
    public QuartzJobScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    private void scheduleQuartzJob() throws SchedulerException {
       JobConfigEnumeration[] jobConfigEnumerations = JobConfigEnumeration.values();
       if(jobConfigEnumerations != null && jobConfigEnumerations.length > 0)
       {
            for (JobConfigEnumeration jobConfigEnumeration : jobConfigEnumerations)
            {
                JobDetail jobDetail  = JobBuilder.newJob(jobConfigEnumeration.getJobClass())
                        .withIdentity(UUID.randomUUID().toString(), jobConfigEnumeration.getClass().getSimpleName())
                        .withDescription(jobConfigEnumeration.getDescription())
                        .storeDurably()
                        .build();

                Trigger trigger =  TriggerBuilder.newTrigger().forJob(jobDetail)
                        .withIdentity(jobDetail.getKey().getName(), "Trigger-"+jobConfigEnumeration.getClass().getSimpleName())
                        .withSchedule(CronScheduleBuilder.cronSchedule(jobConfigEnumeration.getCronExpression()))
                        .build();

                scheduler.scheduleJob(jobDetail, trigger);
            }

           System.out.println("Quartz Jobs scheduled successfully");
       }
    }
}
