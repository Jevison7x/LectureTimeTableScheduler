/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.venues;

import com.xyneex.att.beans.Venue;
import com.xyneex.att.util.XyneexTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jevison7x
 */
public class VenueTableModel extends XyneexTableModel<Object[]>
{
    public String[] childColumnNames;

    public ArrayList<Object[]> data;

    public Class[] types;

    public boolean[] canEdit;

    protected static final String SN = "Sn";

    protected static final String VENUE_NAME = "Venue Name";

    protected static final String INT_EXT = "Int/Ext";

    protected static final String DYNAMIC = "Dynamic";

    public VenueTableModel(List<Venue> venues)
    {
        super(ArrayList.class);
        this.setColumnNames();
        this.setData(venues);
        this.setDataAndColumnNames();
        this.setClass();
        this.setEditableColumns();
    }

    private void setColumnNames()
    {
        this.childColumnNames = new String[]
        {
            SN, VENUE_NAME, INT_EXT, DYNAMIC
        };
    }

    protected String[] getColunNames()
    {
        return this.childColumnNames;
    }

    private void setEditableColumns()
    {
        this.canEdit = new boolean[]
        {
            false, false, false, false
        };
        for(int i = 0; i < this.canEdit.length; i++)
            super.setColumnEditable(i, this.canEdit[i]);
    }

    private void setDataAndColumnNames()
    {
        List columnNamesLocal = Arrays.asList(this.childColumnNames);
        super.setDataAndColumnNames(this.data, columnNamesLocal);
    }

    private void setData(List<Venue> venues)
    {
        this.data = new ArrayList<>();
        int sn = 0;
        for(Venue venue : venues)
        {
            Object[] row = new Object[this.childColumnNames.length];
            row[this.findColumn(SN)] = ++sn;
            row[this.findColumn(VENUE_NAME)] = venue.getVenueName();
            row[this.findColumn(INT_EXT)] = venue.isInternal() ? "Internal" : "External";
            row[this.findColumn(DYNAMIC)] = venue.isDynamic();
            this.data.add(row);
        }
    }

    private void setClass()
    {
        this.types = new Class[]
        {
            Integer.class, String.class, Boolean.class, Boolean.class
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
