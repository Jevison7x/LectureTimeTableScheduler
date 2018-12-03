/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.courses;

import com.xyneex.att.beans.Venue;
import com.xyneex.att.venues.VenueDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Jevison7x
 */
public class VenuePanel extends JPanel
{
    private List<Venue> allVenues;

    private List<JCheckBox> venueCheckBoxes;

    private List<JLabel> dummyLbls;

    private NavigableSet<Venue> selectedVenues;

    private int noOfRows;

    public VenuePanel() throws SQLException
    {
        this.allVenues = VenueDAO.getAllVenues();
        this.selectedVenues = new TreeSet<>();
        this.initComponents();
    }

    private void initCheckBoxes()
    {
        this.venueCheckBoxes = new ArrayList<>();
        for(Venue venue : this.allVenues)
        {
            JCheckBox checkBox = new JCheckBox(venue.getVenueName());
            checkBox.addItemListener(new ItemListener()
            {
                @Override
                public void itemStateChanged(ItemEvent evt)
                {
                    if(checkBox.isSelected())
                        selectedVenues.add(venue);
                    else
                        selectedVenues.remove(venue);
                    System.out.print("Selected Venues: [ ");
                    for(Venue v : selectedVenues)
                        System.out.print(v.getVenueName() + ", ");
                    System.out.println("]");
                    displaySize();
                }
            });
            this.venueCheckBoxes.add(checkBox);
        }
    }

    private void initDummyLbls()
    {
        int noOfVenues = this.allVenues.size();
        this.noOfRows = noOfVenues / 4;
        this.noOfRows += (noOfVenues % 4) > 0 ? 1 : 0;
        int noOfComponents = this.noOfRows * 4;
        int noOfDummyLbls = noOfComponents - noOfVenues;
        this.dummyLbls = new ArrayList<>();
        for(int i = 0; i < noOfDummyLbls; i++)
            this.dummyLbls.add(new JLabel());
    }

    private void initComponents()
    {
        this.initCheckBoxes();
        this.initDummyLbls();
        List<JComponent> allJComponents = new ArrayList<>();
        allJComponents.addAll(venueCheckBoxes);
        allJComponents.addAll(dummyLbls);

        /* Set Group Layout for this Panel */
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);
        /* Turn on automatically creating gaps between components that touch
         the edge of the container and the container. */
        layout.setAutoCreateContainerGaps(true);
        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        // Create four Parallel Groups for the horizontal group
        int beginIndex = -1;
        for(int i = 0; i < 4; i++)
        {
            GroupLayout.ParallelGroup hParallelGroup = layout.createParallelGroup();
            beginIndex++;
            for(int groupIndex = beginIndex; groupIndex <= allJComponents.size() - 1; groupIndex += 4)
                hParallelGroup = hParallelGroup.addComponent(allJComponents.get(groupIndex)).addGap(100, 100, 100);
            hGroup.addGroup(hParallelGroup);
        }
        layout.setHorizontalGroup(hGroup);

        // Create Parallel Groups for the vertical group according to the number of rows
        int componentIndex = 0;
        for(int i = 0; i < this.noOfRows; i++)
        {
            GroupLayout.ParallelGroup vParallelGroup = layout.createParallelGroup();
            for(int j = 0; j < 4; j++, componentIndex++)
                vParallelGroup = vParallelGroup.addComponent(allJComponents.get(componentIndex));
            vGroup.addGroup(vParallelGroup);
        }
        layout.setVerticalGroup(vGroup);
        //Set a title border for this Panel
        this.setBorder(BorderFactory.createTitledBorder(null, "Select the possible venues for this course",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Tahoma", 0, 11), new Color(0, 0, 204))); // NOI18N
    }

    public void selectAllVenues()
    {
        for(JCheckBox checkBox : this.venueCheckBoxes)
            checkBox.setSelected(true);
        this.selectedVenues.addAll(allVenues);
    }

    public void unselectAllVenues()
    {
        for(JCheckBox checkBox : this.venueCheckBoxes)
            checkBox.setSelected(false);
        this.selectedVenues.clear();
    }

    public NavigableSet<Venue> getSelectedVenues()
    {
        return selectedVenues;
    }

    public void setSelectedVenues(NavigableSet<Venue> selectedVenues)
    {
        this.selectedVenues = selectedVenues;
        this.checkSelectedVenues(selectedVenues);
    }

    private void checkSelectedVenues(NavigableSet<Venue> selectedVenues)
    {
        for(Venue venue : selectedVenues)
            for(JCheckBox checkBox : this.venueCheckBoxes)
                if(checkBox.getText().equals(venue.getVenueName()))
                {
                    checkBox.setSelected(true);
                    break;
                }
    }

    private void displaySize()
    {
        System.out.println("The size of the selected venue is " + selectedVenues.size());
    }
}
