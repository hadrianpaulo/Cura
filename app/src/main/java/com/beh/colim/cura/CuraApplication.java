package com.beh.colim.cura;

import android.app.Application;

/**
 * Created by jerelynco on 4/25/16.
 */
public class CuraApplication extends Application {
    private String m_s_username = "";


    public String getM_s_username() {
        return m_s_username;
    }

    public void setM_s_username(String m_s_username) {
        this.m_s_username = m_s_username;
    }
}
