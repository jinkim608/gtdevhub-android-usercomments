package dev.jinkim.usercommentsdemo;

import android.app.Application;

/**
 * Created by Jin on 2/4/15.
 */
public class Singleton extends Application {

    private static Singleton mInstance = null;

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String sessionName;
    private String sessionId;

    private Singleton() {
        sessionName = "Hello";
    }

    public static Singleton getInstance() {
        if (mInstance == null) {
            mInstance = new Singleton();
        }
        return mInstance;
    }

}
