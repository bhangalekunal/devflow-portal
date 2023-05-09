package com.codemaster.devflow.enumeration;


import com.codemaster.devflow.quartz.job.PasswordChangeReminderJob;
import com.codemaster.devflow.quartz.job.BirthdayWishJob;

import org.quartz.Job;

public enum JobConfigEnumeration {
    PASSWORD_CHANGE_REMINDER_JOB("0/30 * * * * ?", PasswordChangeReminderJob.class, "Invoke PasswordChangeReminderJob service..."),
    BIRTHDAY_WISH_JOB("0/30 * * * * ?", BirthdayWishJob.class, "Invoke BirthdayWishJob service...");

    private final String cronExpression;
    private final Class<? extends Job> jobClass;
    private final String description;

    JobConfigEnumeration(String cronExpression, Class<? extends Job> jobClass, String description) {
        this.cronExpression = cronExpression;
        this.jobClass = jobClass;
        this.description = description;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public String getDescription() {
        return description;
    }
}

