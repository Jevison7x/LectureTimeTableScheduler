/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att;

import com.xyneex.att.departments.SelectDepartmentFrame;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author Jevison7x
 */
public class AutomatedTimeTable
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            SelectDepartmentFrame sdf = new SelectDepartmentFrame();
            sdf.setVisible(true);
        }
        catch(Exception xcp)
        {
            if(xcp.getMessage().contains("Failed to start database"))
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "There was an Error: " + "Another Instance of this application is running!", "Error Message", JOptionPane.ERROR_MESSAGE);
                xcp.printStackTrace(System.err);
            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "There was an Error: " + xcp.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                xcp.printStackTrace(System.err);
            }
        }
    }
}
