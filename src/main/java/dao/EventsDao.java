package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.EventModel;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class EventsDao {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<EventModel> getData(String jsonName) throws IOException {

        FileReader reader = new FileReader(System.getProperty("user.dir")+"\\src\\main\\resources\\"+jsonName);
        List<EventModel> events = mapper.readValue(reader, new TypeReference<List<EventModel>>(){});

        return events;
    }
}
