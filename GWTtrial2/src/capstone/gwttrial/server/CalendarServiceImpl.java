package capstone.gwttrial.server;

import capstone.gwttrial.client.calendar.CalendarDetails;
import capstone.gwttrial.client.calendar.Constants;
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

		// TODO Pull events from database, serialize and send to user
		CalendarDetails cal = new CalendarDetails(un);
		Constants.logger.severe("INSTANTIATING CALENDARDETAILS IN SERVICEIMPL");

		return cal;
	}

	@Override
	public Boolean addCalendarEvent(EventDetails event)
			throws IllegalArgumentException {

		// TODO Put user event in database, return success
		Boolean added = true;

		return added;
	}

	@Override
	public Boolean removeCalendarEvent(EventDetails event)
			throws IllegalArgumentException {

		// TODO Remove event from database, return success
		Boolean removed = true;

		return removed;
	}

}
