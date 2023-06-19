/*******************************************************************************
 * Class        FirebaseReaderConfig
 * Created date 17/12/2021
 * Lasted date
 * Author       thanhsang1999
 * Change log   17/12/2021 thanhsang1999 Create New
 ******************************************************************************/
package com.bepay.application.utils;

import com.bepay.application.config.FirebaseConfig;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author thanhsang1999
 * @see FirebaseUtil
 */
@Configuration
public class FirebaseUtil {

    @Autowired
    FirebaseConfig config;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/serviceAccountKey.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream()))
                .setDatabaseUrl(config.getUrlDatabase())
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }
}
