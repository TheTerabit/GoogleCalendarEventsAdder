import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import dao.EventsDao;
import connection.GoogleCalendarConnector;
import model.EventModel;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleCalendarEventsAdderApplication {

    private static final String jsonName = "events.json";
    private static final EventsDao eventsDao = new EventsDao();
    private static final GoogleCalendarConnector connection = new GoogleCalendarConnector();

    public static void main(String[] args) throws IOException, GeneralSecurityException, ParseException {
        List<EventModel> events=eventsDao.getData(jsonName);

        Calendar calendar = connection.getCalendar();

        for(int i=0;i<events.size();i++){

            EventModel jsonEvent = events.get(i);
            Event event = new Event()
                    .setLocation(jsonEvent.getLocation())
                    .setDescription(jsonEvent.getDescription())
                    .setSummary(jsonEvent.getName());
            DateTime startDateTime = jsonEvent.getStart();

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime);

            event.setStart(start);


            Long ending= startDateTime.getValue()+jsonEvent.getDuration();
            DateTime e = new DateTime(ending);
            System.out.println(e);


            DateTime endDateTime = new DateTime(ending);
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime);
            event.setEnd(end);

            event = calendar.events().insert("primary", event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }
    }
}

