package org.laban.learning.spring.timeserver.time;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TimeProvider implements CommandLineRunner {
    private final TimeService timeService;

    public TimeProvider(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.timeService.printCurrentTime();
    }
}
