package com.bepay.application.enums;

import java.util.HashMap;
import java.util.Map;

public enum NewsTypeClientStatus {
    ALL("BOTH", "Eu, Agent, Merchant"),
    EU("EU", "Eu "),
    AGENT("AGENT", "Agent"),
    MERCHANT("MERCHANT", "Merchant "),
    EU_AGENT("EU_AGENT", "Eu,Agent"),
    EU_MERCHANT("EU_MERCHANT", "Eu, Merchant"),
    AGENT_MERCHANT("AGENT_MERCHANT", "Agent,Merchant");

    private final String code;
    private final String message;

    private static final Map<String, NewsTypeClientStatus> mValues = new HashMap<String, NewsTypeClientStatus>();

    static {
        for (NewsTypeClientStatus s : NewsTypeClientStatus.values()) {
            mValues.put(s.code, s);
        }
    }

    NewsTypeClientStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Map<String, NewsTypeClientStatus> getMapStatus() {
        return mValues;
    }
}
