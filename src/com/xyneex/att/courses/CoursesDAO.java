/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.courses;

import com.xyneex.att.beans.Course;
import com.xyneex.att.beans.Venue;
import com.xyneex.att.database.DataBaseDAO;
import com.xyneex.att.departments.Department;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 *
 * @author Jevison7x
 */
public class CoursesDAO
{
    private static List<Course> getAllCourses(ResultSet rs) throws SQLException, IOException, ClassNotFoundException
    {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try
        {
            List<Course> courses = new ArrayList<>();
            while(rs.next())
            {
                Course course = new Course();
                byte[] data = rs.getBytes("VENUES");
                bais = new ByteArrayInputStream(data);
                ois = new ObjectInputStream(bais);
                NavigableSet<Venue> venues = (TreeSet<Venue>)ois.readObject();

                course.setCourseCode(rs.getString("COURSE_CODE"));
                course.setCourseTile(rs.getString("COURSE_TITLE"));
                course.setCreditHours(rs.getInt("CREDIT_HOUR"));
                course.setYearOfStudy(rs.getString("YEAR_OF_STUDY"));
                course.setSemester(rs.getString("SEMESTER"));
                course.setDepartmentID(rs.getInt("DEPARTMENT_ID"));
                course.setDepartmental(rs.getString("DEPARTMENTAL").equals("true"));
                course.setPosibleVenues(venues);
                courses.add(course);
            }
            return courses;
        }
        finally
        {
            if(ois != null)
                ois.close();
            if(bais != null)
                bais.close();
        }
    }

    public static List<Course> getAllCourses(Department department) throws SQLException, IOException, ClassNotFoundException
    {
        String sql = "SELECT * FROM APP.COURSES WHERE DEPARTMENT_ID = ?";
        try(Connection conn = new DataBaseDAO().getDatabaseConnection())
        {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, department.getId());
            ResultSet rs = pst.executeQuery();
            List<Course> courses = getAllCourses(rs);
            return courses;
        }
    }

    public static List<Course> getCoursesBySemester(Department department) throws SQLException, IOException, ClassNotFoundException
    {
        String sql = "SELECT * FROM APP.COURSES WHERE DEPARTMENT_ID = ? AND SEMESTER = ?";
        try(Connection conn = new DataBaseDAO().getDatabaseConnection())
        {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, department.getId());
            pst.setString(2, department.getSemester());
            ResultSet rs = pst.executeQuery();
            List<Course> courses = getAllCourses(rs);
            return courses;
        }
    }

    public static boolean insertCourse(Course course) throws SQLException, IOException
    {
        try(
                Connection conn = new DataBaseDAO().getDatabaseConnection();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);)
        {

            oos.writeObject(course.getPosibleVenues());
            String sql = insertCourseSQL();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, course.getCourseCode());
            pst.setString(2, course.getCourseTile());
            pst.setInt(3, course.getCreditHours());
            pst.setString(4, course.getYearOfStudy());
            pst.setString(5, course.getSemester());
            pst.setInt(6, course.getDepartmentID());
            pst.setString(7, Boolean.toString(course.isDepartmental()));
            pst.setBytes(8, baos.toByteArray());
            int update = pst.executeUpdate();
            return update == 1;
        }
    }

    public static String insertCourseSQL()
    {
        String sql = "INSERT INTO APP.COURSES ("
                + "COURSE_CODE, "
                + "COURSE_TITLE, "
                + "CREDIT_HOUR, "
                + "YEAR_OF_STUDY, "
                + "SEMESTER, "
                + "DEPARTMENT_ID, "
                + "DEPARTMENTAL, "
                + "VENUES"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return sql;
    }

    public static boolean updateCourse(Course course, String oldCourseCode) throws SQLException, IOException
    {
        try(
                Connection conn = new DataBaseDAO().getDatabaseConnection();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);)
        {

            oos.writeObject(course.getPosibleVenues());
            String sql = updateCourseSQL();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, course.getCourseCode());
            pst.setString(2, course.getCourseTile());
            pst.setInt(3, course.getCreditHours());
            pst.setString(4, course.getYearOfStudy());
            pst.setString(5, course.getSemester());
            pst.setInt(6, course.getDepartmentID());
            pst.setString(7, Boolean.toString(course.isDepartmental()));
            pst.setBytes(8, baos.toByteArray());
            pst.setString(9, oldCourseCode);
            int update = pst.executeUpdate();
            return update == 1;
        }
    }

    public static String updateCourseSQL()
    {
        String sql = "UPDATE APP.COURSES SET "
                + "COURSE_CODE = ?, "
                + "COURSE_TITLE = ?, "
                + "CREDIT_HOUR = ?, "
                + "YEAR_OF_STUDY = ?, "
                + "SEMESTER = ?, "
                + "DEPARTMENT_ID = ?, "
                + "DEPARTMENTAL = ?, "
                + "VENUES = ? "
                + "WHERE COURSE_CODE = ?";
        return sql;
    }

    public static Course getCourse(String courseCode) throws SQLException, IOException, ClassNotFoundException
    {
        String sql = "SELECT * FROM APP.COURSES WHERE COURSE_CODE = ?";
        try(Connection conn = new DataBaseDAO().getDatabaseConnection())
        {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, courseCode);
            ResultSet rs = pst.executeQuery();
            List<Course> courses = getAllCourses(rs);
            if(!courses.isEmpty())
                return courses.get(0);
            else
                return null;
        }
    }

    public static boolean removeCourse(String courseCode) throws SQLException
    {
        String sql = "DELETE FROM APP.COURSES WHERE COURSE_CODE = ?";
        try(Connection conn = new DataBaseDAO().getDatabaseConnection())
        {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, courseCode);
            int update = pst.executeUpdate();
            return update == 1;
        }
    }
}
