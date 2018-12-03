/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.beans;

import java.io.Serializable;
import java.util.NavigableSet;
import java.util.Objects;

/**
 *
 * @author Jevison7x
 */
public class Course implements Serializable, Comparable<Course>
{
    private String courseCode;

    private String courseTile;

    private int creditHours;

    private String yearOfStudy;

    private String semester;

    private int departmentID;

    private boolean departmental;

    private NavigableSet<Venue> posibleVenues;

    public Course()
    {

    }

    public String getCourseCode()
    {
        return courseCode;
    }

    public void setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
    }

    public String getCourseTile()
    {
        return courseTile;
    }

    public void setCourseTile(String courseTile)
    {
        this.courseTile = courseTile;
    }

    public int getCreditHours()
    {
        return creditHours;
    }

    public void setCreditHours(int creditHours)
    {
        this.creditHours = creditHours;
    }

    public String getYearOfStudy()
    {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy)
    {
        this.yearOfStudy = yearOfStudy;
    }

    public String getSemester()
    {
        return semester;
    }

    public void setSemester(String semester)
    {
        this.semester = semester;
    }

    public int getDepartmentID()
    {
        return departmentID;
    }

    public void setDepartmentID(int departmentID)
    {
        this.departmentID = departmentID;
    }

    public boolean isDepartmental()
    {
        return departmental;
    }

    public void setDepartmental(boolean departmental)
    {
        this.departmental = departmental;
    }

    public NavigableSet<Venue> getPosibleVenues()
    {
        return posibleVenues;
    }

    public void setPosibleVenues(NavigableSet<Venue> posibleVenues)
    {
        this.posibleVenues = posibleVenues;
    }

    @Override
    public int compareTo(Course course)
    {
        return this.getCourseCode().compareTo(course.getCourseCode());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Course)
            return ((Course)obj).getCourseCode().equals(this.getCourseCode());
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.courseCode);
        hash = 97 * hash + Objects.hashCode(this.courseTile);
        hash = 97 * hash + this.creditHours;
        hash = 97 * hash + Objects.hashCode(this.yearOfStudy);
        hash = 97 * hash + Objects.hashCode(this.semester);
        hash = 97 * hash + this.departmentID;
        hash = 97 * hash + (this.departmental ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.posibleVenues);
        return hash;
    }

    @Override
    public String toString()
    {
        return this.courseCode;
    }
}
