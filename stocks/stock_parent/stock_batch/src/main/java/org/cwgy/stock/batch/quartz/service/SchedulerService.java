package org.cwgy.stock.batch.quartz.service;

import org.cwgy.stock.batch.enitiy.SchedulerJobInfo;

public interface SchedulerService {
	void startAllSchedulers();

	void scheduleNewJob(SchedulerJobInfo jobInfo);

	void updateScheduleJob(SchedulerJobInfo jobInfo);

	boolean unScheduleJob(String jobName);

	boolean deleteJob(SchedulerJobInfo jobInfo);

	boolean pauseJob(SchedulerJobInfo jobInfo);

	boolean resumeJob(SchedulerJobInfo jobInfo);

	boolean startJobNow(SchedulerJobInfo jobInfo);
}
