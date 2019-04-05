package com.wisekrakr.androidmain.retainers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeKeeper {

    public float time = 0;
    public float powerTime = 3f; //todo change time of powerup spawning

    public void setTime(float time) {
        this.time = time;
    }

    private float totalGameTime = 0;

    public float getTotalGameTime() {
        return totalGameTime;
    }

    public void setTotalGameTime(float totalGameTime) {
        this.totalGameTime = totalGameTime;
    }

    public float gameClock = 0;

    public float speedUp = -1000;

    public void reset(){
        time = 0f;
        speedUp = -1000;
        powerTime = 3f;
    }


    private Calendar calendarG = new GregorianCalendar();

    public Date getDate(){
        Date date = new Date();
        calendarG.setTime(date);

        return date;
    }

}
