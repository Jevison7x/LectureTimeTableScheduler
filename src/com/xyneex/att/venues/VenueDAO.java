/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.venues;

import com.xyneex.att.beans.Venue;
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
public class VenueDAO
{
    public static boolean insertVenue(Venue venue) throws SQLException
    {
        String sql = "INSERT INTO APP.VENUES (VENUE_NAME, INT_EXT) VALUES (?, ?)";
        try(Connection conn = new DataBaseDAO().getDatabaseConnection())
        {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, venue.getVenueName());
            pst.setString(2, String.valueOf(venue.isInternal()));
            int update = pst.executeUpdate();
            return update == 1;
        }
    }

    public static List<Venue> getAllVenues() throws SQLException
    {
        String sql = "SELECT * FROM APP.VENUES";
        try(Connection conn = new DataBaseDAO().getDatabaseConnection())
        {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Venue> venues = new ArrayList<>();
            while(rs.next())
            {
                Venue venue = new Venue();
                venue.setVenueName(rs.getString("VENUE_NAME"));
                venue.setInternal(Boolean.valueOf(rs.getString("INT_EXT")));
                venues.add(venue);
            }
            return venues;
        }
    }

    public static boolean removeVenue(String venueName) throws SQLException
    {
        String sql = "DELETE FROM APP.VENUES WHERE VENUE_NAME = ?";
        try(Connection conn = new DataBaseDAO().getDatabaseConnection())
        {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, venueName);
            int update = pst.executeUpdate();
            return update == 1;
        }
    }
}
