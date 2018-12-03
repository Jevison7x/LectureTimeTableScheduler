/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Jevison7x
 */
public final class WindowsSetter
{

    public static void centralizeWindow(Window window)
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = window.getSize().width;
        int h = window.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        window.setLocation(x, y);
    }

    public static Image getDefaultWindowIconImage(Window currentWindow)
    {
        return new ImageIcon(currentWindow.getClass().getResource("../icons/icon-logo.gif")).getImage();
    }

    public static void setNimbusLookAndFeel(Window parentComponent)
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException xcp)
        {
            JOptionPane.showMessageDialog(parentComponent, xcp.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            xcp.printStackTrace(System.err);
        }
    }

    public static void setWindowsLookAndFeel(Window parentComponent)
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException xcp)
        {
            JOptionPane.showMessageDialog(parentComponent, xcp.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            xcp.printStackTrace(System.err);
        }
    }
}
