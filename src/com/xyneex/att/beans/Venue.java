/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.beans;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jevison7x
 */
public class Venue implements Serializable, Comparable<Venue>
{
    private String venueName;

    private boolean internal;

    private boolean dynamic;

    private static final long serialVersionUID = -4917967265793928289L;

    public Venue()
    {

    }

    public String getVenueName()
    {
        return venueName;
    }

    public void setVenueName(String venueName)
    {
        this.venueName = venueName;
    }

    public boolean isInternal()
    {
        return internal;
    }

    public void setInternal(boolean internal)
    {
        this.internal = internal;
    }

    public boolean isDynamic()
    {
        return dynamic;
    }

    public void setDynamic(boolean dynamic)
    {
        this.dynamic = dynamic;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Venue)
            return ((Venue)obj).getVenueName().equals(this.getVenueName());
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.venueName);
        return hash;
    }

    @Override
    public int compareTo(Venue venue)
    {
        String thisVenueName = this.getVenueName();
        String otherVenueName = venue.getVenueName();
        return thisVenueName.compareTo(otherVenueName);
    }

    @Override
    public String toString()
    {
        return this.venueName;
    }
}
