package com.bepay.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com")
@PropertySource("classpath:component.properties")
@Getter
@Setter
public class ComponentConfig {
    private String reOpenCaseManagement;
    private String searchCaseManagement;
    private String saveCaseManagement;
    private String approveCaseManagement;
    private String closeCaseManagement;
    private String rejectCaseManagement;
    private String searchScenario;
    private String detailScenario;
    private String addScenario;
    private String editScenario;
    private String disableScenario;
    private String permissionManagerHO;
    private String permissionStaffHO;
    private String searchAlert;
    private String detailAlert;
    private String alertAddCase;
    private String searchRule;
    private String addRule;
    private String editRule;
    private String disableRule;
    private String searchBlacklist;
    private String importBlacklist;
    private String addBlacklist;
    private String editBlacklist;
    private String disableBlacklist;
    private String searchCaseSummary;
    private String exportCaseSummary;
    private String searchExpiredAlert;
    private String exportExpiredAlert;
    private String searchCaseDetail;
    private String exportCaseDetail;
    private String searchRiskProfile;
    private String exportRiskProfile;
    private String searchCashInCashOutHighValue;
    private String exportCashInCashOutHighValue;
    private String searchTransferHighValue;
    private String exportTransferHighValue;
    private String searchThresholdConfiguration;
    private String editThresholdConfiguration;
}
