package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ma.ensias.ticket_me.model.Event;

public class ResponseListEvents {


    @SerializedName("events")
    private LinkedList<Event> events ;

    public ResponseListEvents(LinkedList<Event> events)
    {
        this.events = events;
    }


    public LinkedList<Event> getEvents() {
        return events;
    }

    public void setEvents(LinkedList<Event>  events) {
        this.events = events;
    }
}
