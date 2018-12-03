/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Jevison7x
 */
public class Period implements Serializable, Comparable<Period>
{
    private String timePeriod;

    private List<Schedule> schedules;

    private boolean seminarPeriod;

    //Time Period constants
    public static final String P7_8 = "7:00-8:00";

    public static final String P8_9 = "8:00-9:00";

    public static final String P9_10 = "9:00-10:00";

    public static final String P10_11 = "10:00-11:00";

    public static final String P11_12 = "11:00-12:00";

    public static final String P12_1 = "12:00-1:00";

    public static final String P1_2 = "1:00-2:00";

    public static final String P2_3 = "2:00-3:00";

    public static final String P3_4 = "3:00-4:00";

    public static final String P4_5 = "4:00-5:00";

    public static final String P5_6 = "5:00-6:00";

    public static final String[] STR_PERIODS = new String[]
    {
        P7_8, P8_9, P9_10, P10_11, P11_12, P12_1, P1_2, P2_3, P3_4, P4_5, P5_6
    };

    public static final String[] SEMINAR_PERIODS = new String[]
    {
        "7.00-9.00", "8.00-10.00", "9.00-11.00", "10.00-12.00", "11.00-1.00", "12.00-2.00", "1.00-3.00", "2.00-4.00", "3.00-5.00"
    };

    public Period()
    {

    }

    public Period(String timePeriod)
    {
        this.timePeriod = timePeriod;
        this.schedules = new ArrayList<>();
    }

    public String getTimePeriod()
    {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod)
    {
        this.timePeriod = timePeriod;
    }

    public List<Schedule> getSchedules()
    {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules)
    {
        this.schedules = schedules;
    }

    public boolean isSeminarPeriod()
    {
        return seminarPeriod;
    }

    public void setSeminarPeriod(boolean seminarPeriod)
    {
        this.seminarPeriod = seminarPeriod;
    }

    public void addSchedule(Schedule schedule)
    {
        this.schedules.add(schedule);
    }

    public Schedule removeSchedule(Schedule schedule)
    {
        for(int i = 0; i < this.schedules.size(); i++)
        {
            Schedule sch = this.schedules.get(i);
            if(sch.getCourse().equals(schedule.getCourse()))
                return this.schedules.remove(i);
        }
        return null;
    }

    public Schedule replaceSchedule(Schedule schedule1, Schedule schedule2)
    {
        for(int i = 0; i < this.schedules.size(); i++)
        {
            Schedule sch = this.schedules.get(i);
            if(sch.getCourse().equals(schedule1.getCourse()))
            {
                this.schedules.add(schedule2);
                return this.schedules.remove(i);
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Period)
            return ((Period)obj).getTimePeriod().equals(this.getTimePeriod());
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.timePeriod);
        return hash;
    }

    public int getPeriodIndex()
    {
        for(int i = 0; i < Period.STR_PERIODS.length; i++)
            if(this.timePeriod.equals(Period.STR_PERIODS[i]))
                return i;
        throw new IllegalArgumentException("The time period is invalid: " + this.timePeriod);
    }

    @Override
    public int compareTo(Period period)
    {
        return this.getPeriodIndex() - period.getPeriodIndex();
    }
}
