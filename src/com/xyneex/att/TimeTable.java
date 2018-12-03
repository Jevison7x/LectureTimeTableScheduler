/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att;

import com.xyneex.att.beans.Course;
import com.xyneex.att.beans.Day;
import com.xyneex.att.beans.Period;
import com.xyneex.att.beans.Schedule;
import com.xyneex.att.beans.Venue;
import com.xyneex.att.courses.CoursesDAO;
import com.xyneex.att.departments.Department;
import com.xyneex.att.util.ScheduleClashingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;

/**
 *
 * @author Jevison7x
 */
public class TimeTable implements Serializable
{
    private Department department;

    private List<Day> days;

    public TimeTable()
    {
    }

    public TimeTable(Department d, List<Day> days)
    {
        this.department = d;
        this.days = days;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public List<Day> getDays()
    {
        return days;
    }

    public void setDays(List<Day> days)
    {
        this.days = days;
    }

    public boolean addFixedSchedule(Schedule schedule, String strDay, String timePeriod) throws ScheduleClashingException
    {
        schedule.setFixed(true);
        for(Day day : this.days)
            if(strDay.equals(day.getName()))
                for(Period period : day.getPeriods())
                    if(timePeriod.equals(period.getTimePeriod()))
                        if(TimeTable.courseIsNotRepeatedOnSameDay(schedule, period, day))
                            if(!period.isSeminarPeriod())
                                if(TimeTable.isNotClashing(schedule, period))
                                    if(this.creditHoursIsNotExhausted(schedule.getCourse()))
                                    {
                                        period.addSchedule(schedule);
                                        return true;
                                    }
                                    else
                                        throw new IllegalArgumentException("The credit hours for this course is exhausted.");
                                else
                                    return false;
                            else
                                throw new ScheduleClashingException("This Period has been reserved for Seminar Discusion.", period,
                                        ScheduleClashingException.SEMINAR_PERIOD);
                        else
                            throw new IllegalArgumentException("This course: " + schedule.getCourse().getCourseCode() + " was already scheduled on " + day.getName() + ".");
        return false;
    }

    public void scheduleAutomatically() throws SQLException, IOException, ClassNotFoundException
    {
        List<Course> courses = CoursesDAO.getCoursesBySemester(this.department);
        List<Schedule> allInternalSchedules = new ArrayList<>();
        for(Course course : courses)
            if(course.isDepartmental())
                for(int i = 0; i < course.getCreditHours(); i++)
                {
                    Schedule schedule = new Schedule();
                    schedule.setCourse(course);
                    allInternalSchedules.add(schedule);
                }

        this.shuffleAndDistribute(allInternalSchedules);
    }

    private void shuffleAndDistribute(List<Schedule> internalSchedules)
    {
        Collections.shuffle(internalSchedules);
        loop1:
        for(Schedule schedule : internalSchedules)
        {
            Collections.shuffle(this.days);
            loop2:
            for(Day day : this.days)
            {
                List<Period> periods = day.getPeriods();
                Collections.shuffle(periods);
                loop3:
                for(Period period : periods)
                    if(TimeTable.courseIsNotRepeatedOnSameDay(schedule, period, day))
                    {
                        if(!period.isSeminarPeriod())
                        {
                            Course course = schedule.getCourse();
                            NavigableSet<Venue> possibleVenues = course.getPosibleVenues();
                            loop4:
                            for(Venue venue : possibleVenues)
                            {
                                schedule.setVenue(venue);
                                try
                                {
                                    if(TimeTable.isNotClashing(schedule, period))
                                        if(this.creditHoursIsNotExhausted(schedule.getCourse()))
                                        {
                                            period.addSchedule(schedule);
                                            continue loop1;
                                        }
                                        else
                                            continue loop1;
                                }
                                catch(ScheduleClashingException xcp)
                                {
                                    if(xcp.getPeriod() != null)
                                        continue loop3;
                                    else if(xcp.getSchedule() != null)
                                        continue loop4;
                                }
                            }
                        }
                    }
                    else
                        continue loop2;
            }
        }
        Collections.sort(this.days);
    }

    public boolean autoSubstitution(String dayStr, String timePeriod, Schedule schedule1, Schedule schedule2)
    {
        for(Day day : this.days)
            if(day.getName().equals(dayStr))
                for(Period period : day.getPeriods())
                    if(period.getTimePeriod().equals(timePeriod))
                        for(Schedule schedule : period.getSchedules())
                            if(schedule.getCourse().equals(schedule1.getCourse()))
                                if(schedule.isFixed())
                                    return false;
                                else
                                {
                                    period.replaceSchedule(schedule1, schedule2);
                                    //TimeTable.printPeriodContents(period);
                                    List<Schedule> internalSchedules = new ArrayList<>();
                                    internalSchedules.add(schedule1);
                                    shuffleAndDistribute(internalSchedules);
                                    return true;
                                }
        return false;
    }

    public static void printPeriodContents(Period period)
    {
        for(Schedule schedule : period.getSchedules())
            System.out.print(schedule.getCourse() + ", \t");
    }

    public boolean fixSeminarPeriod(String strDay, String strTimePeriod) throws ScheduleClashingException
    {
        String startPeriod = new String();
        for(int i = 0; i < Period.SEMINAR_PERIODS.length; i++)
            if(Period.SEMINAR_PERIODS[i].equals(strTimePeriod))
                startPeriod = Period.STR_PERIODS[i];

        for(Day day : this.days)
            if(day.getName().equals(strDay))
            {
                List<Period> periods = day.getPeriods();
                for(int i = 0; i < periods.size(); i++)
                {
                    Period period1 = periods.get(i);
                    if(period1.getTimePeriod().equals(startPeriod))
                    {
                        //Check for the existence of fixed schedules
                        for(Schedule schedule : period1.getSchedules())
                            if(schedule.isFixed())
                                throw new ScheduleClashingException("There is a fixed course scheduled on " + day.getName() + " "
                                        + period1.getTimePeriod() + ". You cannot schedule seminar here.",
                                        schedule, ScheduleClashingException.FIXED_SCHEDULE);
                        //Check for the existence of fixed schedules in the next period
                        Period period2 = periods.get(i + 1);
                        for(Schedule schedule : period2.getSchedules())
                            if(schedule.isFixed())
                                throw new ScheduleClashingException("There is a fixed course scheduled on " + day.getName() + " "
                                        + period2.getTimePeriod() + ". You cannot schedule seminar here.",
                                        schedule, ScheduleClashingException.FIXED_SCHEDULE);
                        List<Schedule> schedules1 = period1.getSchedules();
                        List<Schedule> schedules2 = period2.getSchedules();
                        List<Schedule> combinedDisplacedSchedules = new ArrayList<>();
                        combinedDisplacedSchedules.addAll(schedules1);
                        combinedDisplacedSchedules.addAll(schedules2);
                        period1.setSeminarPeriod(true);
                        period1.setSchedules(new ArrayList<>());
                        period2.setSeminarPeriod(true);
                        period2.setSchedules(new ArrayList<>());
                        this.shuffleAndDistribute(combinedDisplacedSchedules);
                        return true;
                    }
                }
            }
        return false;
    }

    public boolean creditHoursIsNotExhausted(Course course)
    {
        int totalCreditHour = course.getCreditHours();
        int usedCreditHour = 0;
        for(Day day : this.days)
        {
            List<Period> periods = day.getPeriods();
            for(Period period : periods)
            {
                List<Schedule> schedules = period.getSchedules();
                for(Schedule schedule : schedules)
                {
                    Course c = schedule.getCourse();
                    if(course.getCourseCode().equals(c.getCourseCode()))
                    {
                        usedCreditHour++;
                        if(usedCreditHour >= totalCreditHour)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean courseIsNotRepeatedOnSameDay(Schedule schedule, Period period, Day day)
    {
        List<Period> periods = day.getPeriods();
        for(int i = 0; i < periods.size(); i++)
        {
            Period p = periods.get(i);
            for(Schedule sch : p.getSchedules())
                if(schedule.getCourse().equals(sch.getCourse()))
                    if(TimeTable.previosPeriodCheck(periods, period, i))
                        return true;
                    else if(TimeTable.nextPeriodCheck(periods, period, i))
                        return true;
                    else
                        return false;
        }
        return true;
    }

    private static boolean previosPeriodCheck(List<Period> periods, Period period, int currIndex)
    {
        try
        {
            if(periods.get(currIndex - 1).getTimePeriod().equals(period.getTimePeriod()))
                return true;
            else
                return false;
        }
        catch(ArrayIndexOutOfBoundsException xcp)
        {
            return false;
        }
    }

    private static boolean nextPeriodCheck(List<Period> periods, Period period, int currIndex)
    {
        try
        {
            if(periods.get(currIndex + 1).getTimePeriod().equals(period.getTimePeriod()))
                return true;
            else
                return false;
        }
        catch(ArrayIndexOutOfBoundsException xcp)
        {
            return false;
        }
    }

    public static boolean isNotClashing(Schedule schedule, Period period) throws ScheduleClashingException
    {
        //Get all the schedules in the period
        List<Schedule> schedules = period.getSchedules();
        //Iterate through the schedules in the period
        for(Schedule sch : schedules)
        {
            //Check for clashing venues
            Venue venue1 = sch.getVenue();
            Venue venue2 = schedule.getVenue();
            if(venue1.equals(venue2))
                throw new ScheduleClashingException("The venue: " + schedule.getVenue() + " is already assigned to "
                        + "" + sch.getCourse().getCourseCode() + " by "
                        + "" + period.getTimePeriod() + ".", sch, ScheduleClashingException.CLASHING_VENUE);
            //Check for duplicate courses in the same period
            Course course1 = sch.getCourse();
            Course course2 = schedule.getCourse();
            if(course1.getCourseCode().equals(course2.getCourseCode()))
                throw new ScheduleClashingException(course1.getCourseCode() + " is already assigned to this period. "
                        + period.getTimePeriod() + ".", period, ScheduleClashingException.CLASHING_COURSE);
            //Check for courses of the same year of study in the same period
            if(course1.getYearOfStudy().equals(course2.getYearOfStudy()))
                throw new ScheduleClashingException(course1.getCourseCode() + " and " + course2.getCourseCode()
                        + " cannot be assigned to the same period.\nReasons: Both are " + course1.getYearOfStudy() + " courses.",
                        sch, ScheduleClashingException.CLASHING_YEAR_OF_STUDY);
        }
        return true;
    }

    public String generateHTML()
    {
        String html = "<html>";
        html += "   <style>";
        html += "       table {";
        html += "           border-spacing: 0;";
        html += "           border-collapse: collapse;";
        html += "       }";
        html += "       tr {page-break-inside: avoid;}";
        html += "       td {font-size: 8px;}";
        html += "   </style>";
        html += "   <table height=\"287\" border=\"1\">";
        html += "       <tr>";
        html += "           <th scope=\"col\">Day/Time</th>";
        for(String strPeriod : Period.STR_PERIODS)
            html += "           <th scope=\"col\">" + strPeriod + "</th>";
        html += "       </tr>";
        for(Day day : this.getDays())
        {
            html += "       <tr>";
            html += "           <th scope=\"row\">" + day.getName() + "</th>";
            List<Period> periods = day.getPeriods();
            for(int i = 0; i < periods.size(); i++)
            {
                Period period = periods.get(i);
                if(period.isSeminarPeriod())
                {
                    html += "           <td colspan=\"2\" align=\"center\">";
                    html += "               SEMINAR DISCUSSION";
                    html += "           </td>";
                    i++;
                }
                else
                {
                    html += "           <td valign=\"top\">";
                    List<Schedule> schedules = period.getSchedules();
                    for(int j = 0; j < schedules.size(); j++)
                    {
                        Schedule schedule = schedules.get(j);
                        String courseCode = schedule.getCourse().getCourseCode();
                        String venueName = schedule.getVenue().getVenueName();
                        html += "               " + courseCode + "<br/>(" + venueName + ")";
                        if(j < schedules.size() - 1)
                            html += "<br/>";
                    }
                    html += "           </td>";
                }
            }
            html += "       </tr>";
        }
        html += "   </table>";
        html += "</html>";
        return html;
    }

    public String highlightCourse(String cc)
    {
        String html = "<html>";
        html += "   <style>";
        html += "       table {";
        html += "           border-spacing: 0;";
        html += "           border-collapse: collapse;";
        html += "       }";
        html += "       tr {page-break-inside: avoid;}";
        html += "       td {font-size: 8px;}";
        html += "   </style>";
        html += "   <table height=\"287\" border=\"1\">";
        html += "       <tr>";
        html += "           <th scope=\"col\">Day/Time</th>";
        for(String strPeriod : Period.STR_PERIODS)
            html += "           <th scope=\"col\">" + strPeriod + "</th>";
        html += "       </tr>";
        for(Day day : this.getDays())
        {
            html += "       <tr>";
            html += "           <th scope=\"row\">" + day.getName() + "</th>";
            List<Period> periods = day.getPeriods();
            for(int i = 0; i < periods.size(); i++)
            {
                Period period = periods.get(i);
                if(period.isSeminarPeriod())
                {
                    html += "           <td colspan=\"2\" align=\"center\">";
                    html += "               SEMINAR DISCUSSION";
                    html += "           </td>";
                    i++;
                }
                else
                {
                    html += "           <td valign=\"top\">";
                    List<Schedule> schedules = period.getSchedules();
                    for(int j = 0; j < schedules.size(); j++)
                    {
                        Schedule schedule = schedules.get(j);
                        String courseCode = schedule.getCourse().getCourseCode();
                        String venueName = schedule.getVenue().getVenueName();
                        if(cc.equals(courseCode))
                            html += "               <font color=\"red\"><strong>" + courseCode + "<br/>(" + venueName + ")</strong></font>";
                        else
                            html += "               " + courseCode + "<br/>(" + venueName + ")";
                        if(j < schedules.size() - 1)
                            html += "<br/>";
                    }
                    html += "           </td>";
                }
            }
            html += "       </tr>";
        }
        html += "   </table>";
        html += "</html>";
        return html;
    }
}
