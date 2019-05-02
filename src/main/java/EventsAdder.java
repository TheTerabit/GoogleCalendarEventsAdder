import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import model.EventModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EventsAdder {

    public void addEvents(List<EventModel> events, Calendar calendar) throws IOException {

        for(int i=0;i<events.size();i++){

            EventModel jsonEvent = events.get(i);

            Event event = convertEventModelToGoogleEvent(jsonEvent);

            event = calendar.events().insert("primary", event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }

    }
    private Event convertEventModelToGoogleEvent(EventModel jsonEvent){

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
        return event;
    }

}
