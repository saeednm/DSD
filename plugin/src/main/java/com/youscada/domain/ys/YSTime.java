package com.youscada.domain.ys;

public class YSTime {

    // timestamp - UTC milliseconds - mandatory
    private long utcTimestamp;

    // timezone - minutes - mandatory
    private long utcOffset;

    // daylight saving time - true : summer, false : winter - mandatory
    private boolean isDst;

    /**
     * Constructor - Mandatory Fields
     * TODO : Safe checks
     **/
    public YSTime(long utcTimestamp, long utcOffset, boolean isDst) {
        this.utcTimestamp = utcTimestamp;
        this.utcOffset = utcOffset;
        this.isDst = isDst;
    }

    public void setUtcTimestamp(long utcTimestamp) {
        this.utcTimestamp = utcTimestamp;
    }

    public long getUtcTimestamp() {
        return this.utcTimestamp;
    }

    public void setUtcOffset(long utcOffset) {
        this.utcOffset = utcOffset;
    }

    public long getUtcOffset() {
        return this.utcOffset;
    }

    public void setIsDst(boolean isDst) {
        this.isDst = isDst;
    }

    public boolean isDst() {
        return this.isDst;
    }

}
