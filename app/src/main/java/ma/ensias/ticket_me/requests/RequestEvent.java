package ma.ensias.ticket_me.requests;

public class RequestEvent {

    private int admin_event;
    private String name_event;
    private String date_event;
    private String location;

    public RequestEvent(int admin_event, String name_Event, String date_event, String location) {
        this.admin_event = admin_event;
        this.name_event = name_Event;
        this.date_event = date_event;
        this.location = location;
    }

    public int getAdmin_event() {
        return admin_event;
    }

    public void setAdmin_event(int admin_event) {
        this.admin_event = admin_event;
    }

    public String getName_Event() {
        return name_event;
    }

    public void setName_Event(String name_Event) {
        this.name_event = name_Event;
    }

    public String getDate_event() {
        return date_event;
    }

    public void setDate_event(String date_event) {
        this.date_event = date_event;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
