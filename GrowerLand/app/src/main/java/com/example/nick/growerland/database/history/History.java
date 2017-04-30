package com.example.nick.growerland.database.history;

class History {

    private int mId;
    private int mTime;
    private String mType;

    public int getId() {
        return mId;
    }

    public int getTime() {
        return mTime;
    }

    public String getType() {
        return mType;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public void setType(String type) {
        mType = type;
    }
}
