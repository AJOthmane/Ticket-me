package ma.ensias.ticket_me.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {
    @SerializedName("id_event")
    public int id;
    @SerializedName("admin_event")
    public int admin;
    @SerializedName("name_event")
    public String name;
    @SerializedName("date_event")
    public Date date;
    @SerializedName("location")
    public String location;
    @SerializedName("key_event")
    public String keyEvent;

    public Event() { }

    public Event(int id_event,int admin, String name, Date date, String location, String keyEvent) {
        this.id = id_event;
        this.admin = admin;
        this.name = name;
        this.date = date;
        this.location = location;
        this.keyEvent = keyEvent;
    }

//    public Event(int id, String name, Date date, String location, String keyEvent) {
//        this.id = id;
//        this.name = name;
//        this.date = date;
//        this.location = location;
//        this.keyEvent = keyEvent;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
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
