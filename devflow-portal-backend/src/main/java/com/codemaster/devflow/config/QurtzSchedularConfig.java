package com.codemaster.devflow.config;

import com.codemaster.devflow.enumeration.JobConfigEnumeration;
import com.codemaster.devflow.job.BirthdayWishJob;
import com.codemaster.devflow.job.PasswordChangeReminderJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import javax.sql.DataSource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class QurtzSchedularConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    
    @Bean
    public JobDetailFactoryBean passwordChangeReminderJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(JobConfigEnumeration.PASSWORD_CHANGE_REMINDER_JOB.getJobClass());
        jobDetailFactory.setDescription(JobConfigEnumeration.PASSWORD_CHANGE_REMINDER_JOB.getDescription());
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean passwordChangeReminderTrigger(@Qualifier("passwordChangeReminderJobDetail") JobDetail job) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setCronExpression(JobConfigEnumeration.PASSWORD_CHANGE_REMINDER_JOB.getCronExpression());
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean passwordChangeReminderScheduler(@Qualifier("passwordChangeReminderTrigger") Trigger trigger, @Qualifier("passwordChangeReminderJobDetail") JobDetail job, DataSource quartzDataSource) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);
        schedulerFactory.setDataSource(quartzDataSource);
        return schedulerFactory;
    }


    @Bean
    public JobDetailFactoryBean birthdayWishJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(JobConfigEnumeration.BIRTHDAY_WISH_JOB.getJobClass());
        jobDetailFactory.setDescription(JobConfigEnumeration.BIRTHDAY_WISH_JOB.getDescription());
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean birthdayWishTrigger(@Qualifier("birthdayWishJobDetail") JobDetail job) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setCronExpression(JobConfigEnumeration.BIRTHDAY_WISH_JOB.getCronExpression());
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean birthdayWishScheduler(@Qualifier("birthdayWishTrigger") Trigger trigger, @Qualifier("birthdayWishJobDetail") JobDetail job, DataSource quartzDataSource) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);
        schedulerFactory.setDataSource(quartzDataSource);
        return schedulerFactory;
    }
}
