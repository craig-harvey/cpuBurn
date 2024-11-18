package com.charvey.example.cpuBurn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static java.lang.Thread.sleep;

@RestController
class cpuBurnController {

    private static final Logger log = LoggerFactory.getLogger(cpuBurnController.class);

    cpuBurnController() {

    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/cpuBurn/{id}")
    long cpuBurn(@PathVariable Long id) throws InterruptedException {
        int numThreads = Runtime.getRuntime().availableProcessors(); // Set one thread per core
        Thread[] threads = new Thread[numThreads + 4];
        long endTime = Instant.now().getEpochSecond() + id;
        long currentTime = 0L;
        log.info("Starting CPU burner for " + id + " seconds, thread count: " + (numThreads+4));

        // Create and start CPU-intensive threads
        for (int i = 0; i < (numThreads + 3); i++) {
            threads[i] = new Thread(() -> {
                while (Instant.now().getEpochSecond() < endTime) { // Loop to keep CPU busy
                    double x = Math.random() * Math.random(); // Perform a simple calculation
                }
            });
            threads[i].setDaemon(true); // Make thread terminate when main ends
            threads[i].start();
        }

        // Pause for the specified duration to ensure threads are finished
        sleep(id * 1000);

        log.info("CPU burner completed.");

        return 0;
    }
    // end::get-aggregate-root[]

}