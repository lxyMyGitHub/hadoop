package main.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            
            scheduler.start();
            JobDetail job =JobBuilder.newJob(HelloJob.class).withIdentity("myJob","group1").build();
            Trigger trigger =TriggerBuilder.newTrigger().withIdentity("myTrigger","group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();
            scheduler.scheduleJob(job,trigger);
//            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
