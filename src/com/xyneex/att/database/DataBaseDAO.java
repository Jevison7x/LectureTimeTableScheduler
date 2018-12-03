/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.database;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Jevison7x
 */
public class DataBaseDAO
{
    private Properties dbProperties;

    private Connection dbConnection;

    private String dbUrl;

    private final String strCreateDepartmentsTable = "create table APP.DEPARTMENTS ("
            + "ID                                   INTEGER                     NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
            + "UNIVERSITY_NAME       VARCHAR(150)           NOT NULL, "
            + "DEPARTMENT_NAME   VARCHAR(150)           NOT NULL, "
            + "SESSION                       VARCHAR(9)               NOT NULL, "
            + "SEMESTER                   VARCHAR(16)             NOT NULL, "
            + "TTO                              VARCHAR(300)           NOT NULL "
            + ")";

    private final String strCreateCoursesTable = "create table APP.COURSES ("
            + "COURSE_CODE              VARCHAR(8)           NOT NULL PRIMARY KEY, "
            + "COURSE_TITLE               VARCHAR(150)       NOT NULL, "
            + "CREDIT_HOUR                 INTEGER                NOT NULL, "
            + "YEAR_OF_STUDY           VARCHAR(50)         NOT NULL, "
            + "SEMESTER                      VARCHAR(16)         NOT NULL, "
            + "DEPARTMENT_ID            INTEGER                 NOT NULL, "
            + "DEPARTMENTAL             VARCHAR(5)           NOT NULL        DEFAULT 'false', "
            + "VENUES                           LONG VARCHAR FOR BIT DATA"
            + ")";

    private final String strCreateVenuesTable = "create table APP.VENUES ("
            + "VENUE_NAME                 VARCHAR(20)          NOT NULL PRIMARY KEY, "
            + "INT_EXT                          VARCHAR(5)            NOT NULL       DEFAULT 'false', "
            + "DYNAMIC                        VARCHAR(5)            NOT NULL        DEFAULT 'false' "
            + ")";
    /*
     private final String strCreateTimeTable = "create table APP.TIME_TABLE ("
     + "TIME_TABLE_ID           INTEGER            NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
     + "DEPARTMENT_ID        INTEGER             NOT NULL, "
     + "SEMESTER                  VARCHAR(16)     NOT NULL, "
     + "TIME_TABLE               LONG VARCHAR FOR BIT DATA"
     + ")";
     */

    public DataBaseDAO()
    {
        try
        {
            this.dbProperties = this.loadDBProperties();
            String driverName = this.dbProperties.getProperty("derby.driver");
            this.loadDatabaseDriver(driverName);
            if(!this.dbExists())
                this.createDatabase();
        }
        catch(IOException xcp)
        {
            xcp.printStackTrace(System.err);
        }
    }

    public final Properties loadDBProperties() throws IOException
    {
        InputStream dbPropInputStream = DataBaseDAO.class.getResourceAsStream("Configuration.properties");
        this.dbProperties = new Properties();
        try
        {
            this.dbProperties.load(dbPropInputStream);
            return this.dbProperties;
        }
        catch(IOException xcp)
        {
            throw xcp;
        }
    }

    private void loadDatabaseDriver(String driverName)
    {
        try
        {
            Class.forName(driverName);
        }
        catch(ClassNotFoundException xcp)
        {
            xcp.printStackTrace(System.err);
        }
    }

    private boolean dbExists()
    {
        String dbLocation = this.getDatabaseLocation();
        //System.out.println("The database location is: " + dbLocation);
        File dbFileDir = new File(dbLocation);
        return dbFileDir.exists();
    }

    public String getDatabaseLocation()
    {
        String dbLocation = System.getProperty("app.system.database");
        return dbLocation;
    }

    private boolean createDatabase()
    {
        boolean bCreated = false;
        this.dbConnection = null;

        this.dbUrl = this.getDatabaseUrl();
        System.out.println("Database URL: " + this.dbUrl);
        this.dbProperties.put("create", "true");
        try
        {
            this.dbConnection = DriverManager.getConnection(this.dbUrl, this.dbProperties);
            bCreated = this.createDepartmentsTable(this.dbConnection);
            bCreated = this.createCoursesTable(this.dbConnection);
            bCreated = this.createVenuesTable(this.dbConnection);
            //bCreated = this.createTimeTable(this.dbConnection);
        }
        catch(SQLException xcp)
        {
            xcp.printStackTrace(System.err);
        }
        this.dbProperties.remove("create");
        return bCreated;
    }

    public String getDatabaseUrl()
    {
        String derbyURL = this.dbProperties.getProperty("derby.url");
        String databaseDir = System.getProperty("app.system.database");
        this.dbUrl = (derbyURL + databaseDir);
        return this.dbUrl;
    }

    private boolean createDepartmentsTable(Connection dbConnection) throws SQLException
    {
        Statement statement = dbConnection.createStatement();
        statement.execute(this.strCreateDepartmentsTable);
        System.out.println("Departments Table Successfully Created !!");
        return true;
    }

    private boolean createCoursesTable(Connection dbConnection) throws SQLException
    {
        Statement statement = dbConnection.createStatement();
        statement.executeUpdate(this.strCreateCoursesTable);
        System.out.println("Courses Table Successfully Created !!");
        return true;
    }

    private boolean createVenuesTable(Connection dbConnection) throws SQLException
    {
        Statement statement = dbConnection.createStatement();
        statement.executeUpdate(this.strCreateVenuesTable);
        System.out.println("Venues Table Successfully Created !!");
        return true;
    }
    /*
     private boolean createTimeTable(Connection dbConnection) throws SQLException
     {
     Statement statement = dbConnection.createStatement();
     statement.executeUpdate(this.strCreateTimeTable);
     System.out.println("Time Table Successfully Created !!");
     return true;
     }
     */

    public void databaseErrorHandler(Component parentComponent, SQLException sqlex)
    {
        if(this.dbConnection == null)
        {
            JOptionPane.showMessageDialog(parentComponent, "The application was launched twice, you are not allowed to run the \n"
                    + "application more than once.", "Application Error!", JOptionPane.ERROR_MESSAGE);
            System.err.println("Communication Link Failure with Java DB Embedded Server !!");
            System.exit(0);
        }
        else
        {
            System.out.println(sqlex.getMessage());
            JOptionPane.showMessageDialog(parentComponent, sqlex.getMessage(), "Data Retrieval Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Connection getDatabaseConnection() throws SQLException
    {
        this.dbConnection = null;
        this.dbUrl = this.getDatabaseUrl();
        return DriverManager.getConnection(this.dbUrl, this.dbProperties);
    }
}
