/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.courses;

import com.xyneex.att.beans.Course;
import com.xyneex.att.beans.Venue;
import com.xyneex.att.util.XyneexTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jevison7x
 */
public class CoursesTableModel extends XyneexTableModel<Object[]>
{

    public String[] childColumnNames;

    public ArrayList<Object[]> data;

    public Class[] types;

    public boolean[] canEdit;

    protected static final String SN = "Sn";

    protected static final String COURSE_CODE = "Course Code";

    protected static final String COURSE_TITLE = "Course Title";

    protected static final String CREDIT_HOUR = "Credit Hour";

    protected static final String SEMESTER = "Semester";

    protected static final String YEAR_OF_STUDY = "Year of Study";

    protected static final String EXT_INT = "Ext/Int";

    protected static final String VENUES = "Possible Venues";

    public CoursesTableModel(List<Course> courses)
    {
        super(ArrayList.class);
        this.setColumnNames();
        this.setData(courses);
        this.setDataAndColumnNames();
        this.setClass();
        this.setEditableColumns();
    }

    private void setColumnNames()
    {
        this.childColumnNames = new String[]
        {
            SN, COURSE_CODE, COURSE_TITLE, CREDIT_HOUR, SEMESTER, YEAR_OF_STUDY, EXT_INT, VENUES
        };
    }

    protected String[] getColumnNames()
    {
        return this.childColumnNames;
    }

    private void setEditableColumns()
    {
        this.canEdit = new boolean[]
        {
            false, false, false, false, false, false, false, false
        };
        for(int i = 0; i < this.canEdit.length; i++)
            super.setColumnEditable(i, this.canEdit[i]);
    }

    private void setDataAndColumnNames()
    {
        List columnNamesLocal = Arrays.asList(this.childColumnNames);
        super.setDataAndColumnNames(this.data, columnNamesLocal);
    }

    private void setData(List<Course> courses)
    {
        this.data = new ArrayList<>();
        int sn = 0;
        for(Course course : courses)
        {
            Object[] row = new Object[this.childColumnNames.length];
            row[this.findColumn(SN)] = ++sn;
            row[this.findColumn(COURSE_CODE)] = course.getCourseCode();
            row[this.findColumn(COURSE_TITLE)] = course.getCourseTile();
            row[this.findColumn(CREDIT_HOUR)] = course.getCreditHours();
            row[this.findColumn(SEMESTER)] = course.getSemester().equals("FIRST SEMESTER") ? "FIRST" : "SECOND";
            row[this.findColumn(YEAR_OF_STUDY)] = course.getYearOfStudy();
            row[this.findColumn(EXT_INT)] = course.isDepartmental() ? "Internal" : "Borrowed";
            String venuesStr = new String();
            List<Venue> venues = new ArrayList<>(course.getPosibleVenues());
            int totalVenues = venues.size();
            for(int i = 0; i < totalVenues; i++)
                if(i < totalVenues - 1)
                    venuesStr += venues.get(i).getVenueName() + ", ";
                else
                    venuesStr += venues.get(i).getVenueName();
            row[this.findColumn(VENUES)] = venuesStr;
            this.data.add(row);
        }
    }

    private void setClass()
    {
        this.types = new Class[]
        {
            Integer.class, String.class, String.class, Integer.class, String.class, String.class, String.class, String.class
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return ((Object[])this.data.get(rowIndex))[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Object[] row = this.getRow(rowIndex);
        row[columnIndex] = (aValue);
        this.replaceRow(rowIndex, row);
    }

    @Override
    public int findColumn(String columnNames)
    {
        for(int i = 0; i < this.childColumnNames.length; i++)
            if(columnNames.equals(this.childColumnNames[i]))
                return i;
        throw new IllegalArgumentException("The column name: \"" + columnNames + "\"does not exit");
    }
}
