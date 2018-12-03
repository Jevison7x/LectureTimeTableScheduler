/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jevison7x
 */
public class Day implements Serializable, Comparable<Day>
{
    private String name;

    private List<Period> periods;

    public static final String MONDAY = "MONDAY";

    public static final String TUESDAY = "TUESDAY";

    public static final String WEDNESDAY = "WEDNESDAY";

    public static final String THURSDAY = "THURSDAY";

    public static final String FRIDAY = "FRIDAY";

    public static final String[] STR_DAYS = new String[]
    {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    };

    public Day(String name)
    {
        Period[] ps = new Period[]
        {
            new Period(Period.P7_8),
            new Period(Period.P8_9),
            new Period(Period.P9_10),
            new Period(Period.P10_11),
            new Period(Period.P11_12),
            new Period(Period.P12_1),
            new Period(Period.P1_2),
            new Period(Period.P2_3),
            new Period(Period.P3_4),
            new Period(Period.P4_5),
            new Period(Period.P5_6)
        };
        this.name = name;
        this.periods = Arrays.asList(ps);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Period> getPeriods()
    {
        Collections.sort(periods);
        return periods;
    }

    public void setPeriods(List<Period> periods)
    {
        this.periods = periods;
    }

    public int getDayIndex()
    {
        switch(this.name)
        {
            case MONDAY:
                return 0;
            case TUESDAY:
                return 1;
            case WEDNESDAY:
                return 2;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            default:
                throw new IllegalArgumentException("The Day name is invalid: " + this.name);
        }
    }

    @Override
    public int compareTo(Day day)
    {
        return this.getDayIndex() - day.getDayIndex();
    }
}
