import com.google.api.services.calendar.Calendar;
import dao.EventsDao;
import connection.GoogleCalendarConnector;
import model.EventModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleCalendarEventsAdderApplication {

    private static final String jsonName = "events.json";
    private static final EventsDao eventsDao = new EventsDao();
    private static final GoogleCalendarConnector connection = new GoogleCalendarConnector();
    private static final EventsAdder eventsAdder = new EventsAdder();

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        List<EventModel> events=eventsDao.getData(jsonName);

        Calendar calendar = connection.getCalendar();

        eventsAdder.addEvents(events, calendar);


    }
}

