/*******************************************************************************
 * Class        FirebaseConfig
 * Created date 16/12/2021
 * Lasted date
 * Author       thanhsang1999
 * Change log   16/12/2021 thanhsang1999 Create New
 ******************************************************************************/
package com.bepay.application.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author thanhsang1999
 * @see FirebaseConfig
 */
@Component
@ConfigurationProperties(prefix = "firebase") // prefix app, find app.* values
@PropertySource("classpath:firebase.properties")
@Getter
@Setter
@NoArgsConstructor
public class FirebaseConfig {
    private String uid;
    private String nodeChild;
    private String urlDatabase;
    private String notification;
    private String alert;
    private String urlEmulator;
}
