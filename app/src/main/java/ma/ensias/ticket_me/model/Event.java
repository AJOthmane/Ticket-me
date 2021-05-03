package ma.ensias.ticket_me.model;

import java.util.Date;

public class Event {

    public int id;

    public User admin;

    public String name;

    public Date date;

    public String location;

    public String keyEvent;

    public Event() { }

    public Event(int id, User admin, String name, Date date, String location, String keyEvent) {
        this.id = id;
        this.admin = admin;
        this.name = name;
        this.date = date;
        this.location = location;
        this.keyEvent = keyEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKeyEvent() {
        return keyEvent;
    }

    public void setKeyEvent(String keyEvent) {
        this.keyEvent = keyEvent;
    }
}
