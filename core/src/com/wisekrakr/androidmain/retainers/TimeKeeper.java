package com.wisekrakr.androidmain.retainers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeKeeper {

    public float time = 60;

    public void setTime(float time) {
        this.time = time;
    }

    public float gameClock = 0;

    public float timeToChase = 0;
    public float powerTime = 23f;
    public float spawnInterval = 2f;

    public float getTimeToChase() {
        return timeToChase;
    }

    public void setTimeToChase(float timeToChase) {
        this.timeToChase = timeToChase;
    }

    public void reset(){
        time = 60f;
        powerTime = 23f;
        timeToChase = 0;
        spawnInterval = 2f;
    }


    private Calendar calendarG = new GregorianCalendar();

    public Date getDate(){
        Date date = new Date();
        calendarG.setTime(date);

        return date;
    }

}
