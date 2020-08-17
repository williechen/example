package org.cwgy.stock.batch.config.batch;

import org.cwgy.stock.batch.quartz.job.DailyTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig{
	
	@Autowired
    private JobBuilderFactory jobs;
 
    @Autowired
    private StepBuilderFactory steps;
    
    @Bean
    public Step stepOne(){
        return steps.get("stepOne")
                .tasklet(new DailyTask())
                .build();
    }
    
    @Bean(name="demoJobOne")
    public Job demoJobOne(){
        return jobs.get("demoJobOne")
                .start(stepOne())
                .build();
    }
    
}
