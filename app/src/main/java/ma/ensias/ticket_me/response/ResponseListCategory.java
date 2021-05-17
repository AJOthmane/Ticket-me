package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

import ma.ensias.ticket_me.model.CategoryTicket;


public class ResponseListCategory {

    @SerializedName("categories")
    private LinkedList<CategoryTicket> listOfEvents;

    public ResponseListCategory(LinkedList<CategoryTicket> listOfEvents) {
        this.listOfEvents = listOfEvents;
    }

    public LinkedList<CategoryTicket> getListOfEvents() {
        return listOfEvents;
    }

    public void setListOfEvents(LinkedList<CategoryTicket> listOfEvents) {
        this.listOfEvents = listOfEvents;
    }
}
