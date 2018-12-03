/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.departments;

import java.io.Serializable;

/**
 *
 * @author Jevison7x
 */
public class Department implements Serializable
{
    private int id;

    private String universityName;

    private String semester;

    private String session;

    private String departmentName;

    private String timeTableOfficer;

    public Department()
    {
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUniversityName()
    {
        return universityName;
    }

    public void setUniversityName(String universityName)
    {
        this.universityName = universityName;
    }

    public String getSemester()
    {
        return semester;
    }

    public void setSemester(String semester)
    {
        this.semester = semester;
    }

    public String getSession()
    {
        return session;
    }

    public void setSession(String session)
    {
        this.session = session;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getTimeTableOfficer()
    {
        return timeTableOfficer;
    }

    public void setTimeTableOfficer(String timeTableOfficer)
    {
        this.timeTableOfficer = timeTableOfficer;
    }

    @Override
    public String toString()
    {
        return this.departmentName;
    }
}
