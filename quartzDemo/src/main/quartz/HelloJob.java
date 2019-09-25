package main.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        
        try {
            System.out.println(context.getJobRunTime());
            System.out.println(context.getScheduler().getSchedulerName().toString());
            System.out.println(context.toString());
            System.out.println(context.getTrigger().getJobKey().toString());
            System.out.println("hello quartz");
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }

}
