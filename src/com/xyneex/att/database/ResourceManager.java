/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.database;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Jevison7x
 */
public class ResourceManager
{
    private Properties dbProperties;

    public ResourceManager()
    {
        try
        {
            this.setDBProperties();
            this.createUserSystemDirectories();
        }
        catch(IOException xcp)
        {
            xcp.printStackTrace(System.err);
        }
    }

    private void setDBProperties() throws IOException
    {
        try
        {
            InputStream dbPropInputStream = ResourceManager.class.getResourceAsStream("Configuration.properties");
            this.dbProperties = new Properties();
            this.dbProperties.load(dbPropInputStream);
        }
        catch(IOException xcp)
        {
            throw xcp;
        }
    }

    private void createUserSystemDirectories()
    {
        String userHomeDir = System.getProperty("user.home", ".");
        //Create and set the application system directory
        String app_sys_dir = this.dbProperties.getProperty("app_sys.dir");
        String appSystemDir = userHomeDir + "\\" + app_sys_dir;
        System.setProperty("app.system.home", appSystemDir);
        File appSystemFileDir = new File(appSystemDir);
        appSystemFileDir.mkdir();
        //Set the application database directory
        String database_dir = this.dbProperties.getProperty("database.dir");
        String databaseDir = appSystemDir + "\\" + database_dir;
        System.setProperty("app.system.database", databaseDir);
    }

    public String getAppSystemDirectory()
    {
        return System.getProperty("app.system.home");
    }

    public String getDatabaseDirectory()
    {
        return System.getProperty("app.system.database");
    }

    /**
     * Copies file from one directory called the source to another called the destination.
     *
     * @since 2.4
     */
    public static void moveFile(Component parentComponent, String source, String destination)
    {
        File des = new File(destination);
        File src = new File(source);
        try(FileOutputStream fout = new FileOutputStream(des); FileInputStream in = new FileInputStream(src))
        {
            BufferedInputStream bin = new BufferedInputStream(in);
            byte[] pic = new byte[(int)src.length()];
            bin.read(pic, 0, pic.length - 1);
            des.createNewFile();
            fout.write(pic, 0, pic.length - 1);
            fout.flush();
            JOptionPane.showMessageDialog(parentComponent, "File Successfully Copied!");
        }
        catch(IOException xcp)
        {
            xcp.printStackTrace(System.err);
        }
    }
}
