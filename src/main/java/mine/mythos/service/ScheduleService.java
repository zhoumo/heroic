package mine.mythos.service;

import mine.mythos.base.BaseClass;
import mine.mythos.vo.ScheduleJob;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdScheduler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduleService extends BaseClass {

	private static Scheduler scheduler;
	static {
		scheduler = (StdScheduler) new ClassPathXmlApplicationContext("spring/applicationContext-scheduled.xml").getBean("schedulerFactory");
	}

	public static boolean enableCronSchedule(ScheduleJob scheduleJob, JobDataMap paramsMap, boolean isStateFull) {
		if (scheduleJob == null) {
			return false;
		}
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(scheduleJob.getTriggerName(), scheduleJob.getJobGroup());
			if (null == trigger) {
				JobDetail jobDetail = null;
				if (isStateFull) {
					jobDetail = new JobDetail(scheduleJob.getJobId(), scheduleJob.getJobGroup(), scheduleJob.getStateFulljobExecuteClass());
				} else {
					jobDetail = new JobDetail(scheduleJob.getJobId(), scheduleJob.getJobGroup(), scheduleJob.getJobExecuteClass());
				}
				jobDetail.setJobDataMap(paramsMap);
				trigger = new CronTrigger(scheduleJob.getTriggerName(), scheduleJob.getJobGroup(), scheduleJob.getCronExpression());
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				trigger.setCronExpression(scheduleJob.getCronExpression());
				scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}