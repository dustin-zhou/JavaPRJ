package com.dir.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryWatcherScheduler {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryWatcherScheduler.class);

    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail job = JobBuilder.newJob(DirectoryMonitorJob.class)
                    .withIdentity("directoryMonitorJob", "group1")
                    .build();

            ConfigLoader config = new ConfigLoader("config.properties");
            int checkInterval = config.getIntProperty("check.interval.seconds");

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("directoryMonitorTrigger", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(checkInterval)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();
            logger.info("Scheduler started.");

        } catch (SchedulerException se) {
            logger.error("Scheduler exception", se);
        }
    }
}