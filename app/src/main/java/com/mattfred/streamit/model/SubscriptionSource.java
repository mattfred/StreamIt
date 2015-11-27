package com.mattfred.streamit.model;

/**
 * Subscription Source object
 */
public class SubscriptionSource {

    private int id;
    private String source;
    private String display_name;
    private String type;
    private String info;
    private String android_app;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAndroid_app() {
        return android_app;
    }

    public void setAndroid_app(String android_app) {
        this.android_app = android_app;
    }

    @Override
    public String toString() {
        return "SubscriptionSource{" +
                "display_name='" + display_name + '\'' +
                '}';
    }
}
