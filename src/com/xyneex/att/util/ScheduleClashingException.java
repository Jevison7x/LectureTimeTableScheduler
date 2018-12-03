/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.util;

import com.xyneex.att.beans.Period;
import com.xyneex.att.beans.Schedule;

/**
 *
 * @author Jevison7x
 */
public class ScheduleClashingException extends Exception
{
    private String message;

    private Schedule schedule;

    private Period period;

    private int errorCode;

    public static final int SEMINAR_PERIOD = 1;

    public static final int FIXED_SCHEDULE = 2;

    public static final int CLASHING_VENUE = 3;

    public static final int CLASHING_COURSE = 4;

    public static final int CLASHING_YEAR_OF_STUDY = 5;

    public ScheduleClashingException(String message, Schedule schedule, int errorCode)
    {
        this.message = message;
        this.schedule = schedule;
        this.errorCode = errorCode;
    }

    public ScheduleClashingException(String message, Period period, int errorCode)
    {
        this.message = message;
        this.period = period;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }

    public Schedule getSchedule()
    {
        return this.schedule;
    }

    public Period getPeriod()
    {
        return this.period;
    }

    public int getErrorCode()
    {
        return this.errorCode;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setSchedule(Schedule schedule)
    {
        this.schedule = schedule;
    }

    public void setPeriod(Period period)
    {
        this.period = period;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }
}
