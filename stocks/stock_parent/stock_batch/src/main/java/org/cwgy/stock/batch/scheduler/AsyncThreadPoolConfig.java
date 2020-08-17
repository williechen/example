package org.cwgy.stock.batch.scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration 
@EnableAsync
public class AsyncThreadPoolConfig {

	@Bean("taskExecutor") 
	public Executor taskExecutor() { 
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); 
		// 核心線程數 
		executor.setCorePoolSize(10);
		// 最大線程數 
		executor.setMaxPoolSize(20); 
		// 緩衝執行任務的隊列長度 
		executor.setQueueCapacity(200); 
		// 允許線程的空閒時間，超過了核心線程出之外的線程在空閒時間到達之後會被銷毀 
		executor.setKeepAliveSeconds(60); 
		// 線程池名的前綴，輸出log的時候比較有用 
		executor.setThreadNamePrefix("taskExecutor-"); 
		// 線程池對拒絕任務的處理策略，這裡採用了CallerRunsPolicy策略，當線程池沒有處理能力的時候，該策略會直接在 execute 方法的調用線程中運行被拒絕的任務；如果執行程序已關閉，則會丟棄該任務。 
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); 
		// 用來設置線程池關閉的時候等待所有任務都完成再繼續銷毀其他的Bean，這樣異步任務的銷毀就會先於Redis線程池的銷毀。 
		executor.setWaitForTasksToCompleteOnShutdown(true); 
		// 該方法用來設置線程池中任務的等待時間，如果超過這個時候還沒有銷毀就強制銷毀 
		executor.setAwaitTerminationSeconds(60); 
		return executor;
	}

	
}
