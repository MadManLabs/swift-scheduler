package capstone.gwttrial.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import capstone.gwttrial.client.calendar.CalendarDetails;
import capstone.gwttrial.client.calendar.EventDetails;
import capstone.gwttrial.client.calendar.service.CalendarService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CalendarServiceImpl extends RemoteServiceServlet implements
		CalendarService {

	@Override
	public CalendarDetails getCalendarEvents(String un)
			throws IllegalArgumentException {

		// Pull events from database, serialize and send to user
		// Dates are returned in 'yyyy-mm-dd hh:mm:ss' form
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		CalendarDetails cal = new CalendarDetails(un);
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/swiftdb?" + "user=root&password=pass");
		
			
		 preparedStatement = connect.prepareStatement("SELECT * FROM Events)");
		 resultSet = preparedStatement.executeQuery();
		 while (resultSet.next()) {
		 int eventID = resultSet.getInt(1);
		 String name = resultSet.getString(2);
		 String location = resultSet.getString(3);
		 String start = resultSet.getDate(4).toString();
		 String end = resultSet.getDate(5).toString();
		 String creator = resultSet.getString(6);
		 String description = resultSet.getString(7);
		 EventDetails event = new EventDetails(eventID,name,location,start,end,creator,description);
		 cal.addEvent(event);
		 }
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cal;
	}

	@Override
	public int addCalendarEvent(EventDetails event)
			throws IllegalArgumentException {

		// Put user event in database, return eventID
		// Dates are to be entered in 'yyyy-mm-dd hh:mm:ss' form
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int eventID = -1;
		
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/swiftdb?" + "user=root&password=pass");
		
			
		 preparedStatement = connect.prepareStatement("INSERT INTO Events VALUES (default"
		 		+ ",'"+event.getName()
		 		+ "','"+event.getLocation()
		 		+ "','"+java.sql.Date.valueOf(event.getDate())
		 		+ "','"+java.sql.Date.valueOf(event.getDate())
		 		+ "','username"
		 		+ "','"+event.getDescription()+"');");
		 resultSet = preparedStatement.executeQuery();
		 
		 preparedStatement = connect.prepareStatement("SELECT COUNT(*) FROM Events;");
		 resultSet = preparedStatement.executeQuery();
		 resultSet.next();
		 eventID = resultSet.getInt(1);
		} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
		}
		
		return eventID;
	}

	@Override
	public Boolean removeCalendarEvent(EventDetails event)
			throws IllegalArgumentException {

		// Remove event from database, return success
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		Boolean removed = true;
		
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/swiftdb?" + "user=root&password=pass");
		
			
		 preparedStatement = connect.prepareStatement("DELETE FROM Events WHERE eventIDe='"+event.getEventID()+"';");
		 preparedStatement.executeQuery();
		 removed = true;
				 
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return removed;
	}

}
