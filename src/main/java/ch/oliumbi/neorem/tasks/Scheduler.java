package ch.oliumbi.neorem.tasks;

import ch.oliumbi.neorem.properties.SchedulerProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

@Component
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    private final SchedulerProperties schedulerProperties;

    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private Map<UUID, Future<?>> scheduled = new HashMap<>();

    public Scheduler(SchedulerProperties schedulerProperties) {
        this.schedulerProperties = schedulerProperties;
    }

    @PostConstruct
    public void initialize() {
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(schedulerProperties.getThreadPool());
        threadPoolTaskScheduler.setErrorHandler(t -> LOGGER.warn("Unexpected error occurred in scheduler", t));
        // todo virtual threads??
        threadPoolTaskScheduler.initialize();
    }

    @PreDestroy
    public void shutdown() {
        threadPoolTaskScheduler.shutdown();
    }

    public void run(UUID id, Task task) {
        Future<?> future = threadPoolTaskScheduler.submit(task);

        scheduled.put(id, future);
    }

    public void schedule(UUID id, Task task, String cron) {
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(task, new CronTrigger(cron));

        scheduled.put(id, scheduledFuture);
    }

    public void stop(UUID id) {
        Future<?> future = scheduled.get(id);

        if (future == null) {
            return;
        }

        future.cancel(true);
    }
}
