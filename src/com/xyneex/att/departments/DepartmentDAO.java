/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.departments;

import com.xyneex.att.database.DataBaseDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jevison7x
 */
public class DepartmentDAO
{
    public static List<Department> getDepartments() throws SQLException
    {
        DataBaseDAO dbdao = new DataBaseDAO();
        try(Connection conn = dbdao.getDatabaseConnection())
        {
            String sql = "SELECT * FROM APP.DEPARTMENTS";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Department> departments = new ArrayList<>();
            while(rs.next())
            {
                Department department = new Department();
                department.setId(rs.getInt("ID"));
                department.setUniversityName(rs.getString("UNIVERSITY_NAME"));
                department.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
                department.setSession(rs.getString("SESSION"));
                department.setSemester(rs.getString("SEMESTER"));
                department.setTimeTableOfficer(rs.getString("TTO"));
                departments.add(department);
            }
            return departments;
        }
    }

    public static boolean createNewDepartment(Department d) throws SQLException
    {
        DataBaseDAO dbdao = new DataBaseDAO();
        try(Connection conn = dbdao.getDatabaseConnection())
        {
            String sql = getCreateDepartmentSQL();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, d.getUniversityName());
            pst.setString(2, d.getDepartmentName());
            pst.setString(3, d.getSession());
            pst.setString(4, d.getSemester());
            pst.setString(5, d.getTimeTableOfficer());
            int update = pst.executeUpdate();
            return update == 1;
        }
    }

    private static String getCreateDepartmentSQL()
    {
        String sql = "INSERT INTO APP.DEPARTMENTS ("
                + "UNIVERSITY_NAME, "
                + "DEPARTMENT_NAME, "
                + "SESSION, "
                + "SEMESTER, "
                + "TTO) "
                + "	VALUES (?, ?, ?, ?, ?)";
        return sql;
    }
}
