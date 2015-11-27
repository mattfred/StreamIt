package com.mattfred.streamit.model;

/**
 * Channel Object
 */
public class Channel {

    private int id;
    private String name;
    private String channel_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    @Override
    public String toString() {
        return name;
    }
}
