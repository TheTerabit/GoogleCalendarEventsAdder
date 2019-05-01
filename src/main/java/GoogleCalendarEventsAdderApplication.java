import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import dao.EventsDao;
import connection.GoogleCalendarConnector;
import model.EventModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoogleCalendarEventsAdderApplication {

    private static final String jsonName = "events.json";
    private static final EventsDao eventsDao = new EventsDao();
    private static final GoogleCalendarConnector connection = new GoogleCalendarConnector();

    public static void main(String[] args) throws IOException, GeneralSecurityException {

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

            DateTime endDateTime = new DateTime(startDateTime.getValue()+jsonEvent.getDuration());
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime);
            event.setEnd(end);

            if(jsonEvent.getReminder()!=null) {
                Long time = ((startDateTime.getValue() - jsonEvent.getReminder().getValue()) / 60000);

                EventReminder[] reminderOverrides = new EventReminder[]{
                        new EventReminder().setMethod("popup").setMinutes(time.intValue())
                };
                Event.Reminders reminders = new Event.Reminders()
                        .setUseDefault(false)
                        .setOverrides(Arrays.asList(reminderOverrides));
                event.setReminders(reminders);
            }

            event = calendar.events().insert("primary", event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }
    }
}

