package com.georgiaandtours.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tours")
public class Constants {
    private String ADMIN_ROLE;
    private String CLIENT_ROLE;
    private String SERVER_ROLE;
    private String WEBSOCKET_SID;
    private String USER_CREATION;
    private String SAVE_ADMIN_SID;

    public static String ADMIN_ROLE_STATIC;
    public static String CLIENT_ROLE_STATIC;
    public static String SERVER_ROLE_STATIC;
    public static String WEBSOCKET_SID_STATIC;
    public static String USER_CREATION_STATIC;
    public static String SAVE_ADMIN_SID_STATIC;

    public void setADMIN_ROLE(String ADMIN_ROLE) {
        this.ADMIN_ROLE = ADMIN_ROLE;
        ADMIN_ROLE_STATIC = ADMIN_ROLE;
    }

    public void setCLIENT_ROLE(String CLIENT_ROLE) {
        this.CLIENT_ROLE = CLIENT_ROLE;
        CLIENT_ROLE_STATIC = CLIENT_ROLE;
    }

    public void setSERVER_ROLE(String SERVER_ROLE) {
        this.SERVER_ROLE = SERVER_ROLE;
        SERVER_ROLE_STATIC = SERVER_ROLE;
    }

    public void setWEBSOCKET_SID(String WEBSOCKET_SID) {
        this.WEBSOCKET_SID = WEBSOCKET_SID;
        WEBSOCKET_SID_STATIC = WEBSOCKET_SID;
    }

    public void setUSER_CREATION(String USER_CREATION) {
        this.USER_CREATION = USER_CREATION;
        USER_CREATION_STATIC = USER_CREATION;
    }

    public void setSAVE_ADMIN_SID(String SAVE_ADMIN_SID) {
        this.SAVE_ADMIN_SID = SAVE_ADMIN_SID;
        SAVE_ADMIN_SID_STATIC = SAVE_ADMIN_SID;
    }
}
