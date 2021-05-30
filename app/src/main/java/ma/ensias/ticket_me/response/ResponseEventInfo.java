package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

import ma.ensias.ticket_me.model.Event;

public class ResponseEventInfo {


    @SerializedName("event")
    private Event event;

    public ResponseEventInfo( Event key) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
