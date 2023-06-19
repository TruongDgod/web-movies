package com.bepay.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author vidal
 * @return
 * @see BepaySystemApplication
 */
@SpringBootApplication
public class BepaySystemApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(BepaySystemApplication.class);

    public static void main(String[] args) {
        StringBuilder lg = new StringBuilder();
        lg.append("\n#################################################################################################");
        lg.append("\n#                     START SPRING BOOT APPLICATION (Bepay SYSTEM)                               ");
        lg.append("\n#################################################################################################");
        lg.append("\n");
        LOGGER.info(lg.toString());
        SpringApplication.run(BepaySystemApplication.class, args);
    }
}