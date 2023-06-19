package com.bepay.application;

import org.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author vidal
 * @return
 * @see BepaySystemStopApplication
 */
@SpringBootApplication
public class BepaySystemStopApplication {
    private static final Logger LOGGER=LoggerFactory.getLogger(BepaySystemStopApplication.class);
    public static void main(String[] args) {
        StringBuilder lg = new StringBuilder();
        lg.append("\n#################################################################################################");
        lg.append("\n#                     STOP SPRING BOOT APPLICATION (Bepay SYSTEM)                                                          ");
        lg.append("\n#################################################################################################");
        lg.append("\n");
        LOGGER.info(lg.toString());
        SpringApplication.run(BepaySystemStopApplication.class, args).close();
    }
}
