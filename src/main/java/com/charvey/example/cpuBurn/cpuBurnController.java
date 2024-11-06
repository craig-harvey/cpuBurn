package com.charvey.example.cpuBurn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.sleep;

@RestController
class cpuBurnController {

    private final cpuBurnRepository repository;

    private static final Logger log = LoggerFactory.getLogger(cpuBurnController.class);

    cpuBurnController(cpuBurnRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/cpuBurn/{id}")
    long cpuBurn(@PathVariable Long id) throws InterruptedException {
        int numThreads = Runtime.getRuntime().availableProcessors(); // Set one thread per core
        Thread[] threads = new Thread[numThreads];

        log.info("Starting CPU burner for " + id + " seconds, thread count: " + (numThreads-1));

        // Create and start CPU-intensive threads
        for (int i = 0; i < (numThreads - 1); i++) {
            threads[i] = new Thread(() -> {
                while (true) { // Infinite loop to keep CPU busy
                    double x = Math.random() * Math.random(); // Perform a simple calculation
                }
            });
            threads[i].setDaemon(true); // Make thread terminate when main ends
            threads[i].start();
        }

        // Run for 10 seconds
        sleep(id * 1000);

        log.info("CPU burner completed.");

        return 0;
    }
    // end::get-aggregate-root[]

}