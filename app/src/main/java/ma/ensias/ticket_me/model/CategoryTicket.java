package ma.ensias.ticket_me.model;

public class CategoryTicket {

    private int id;

    private String name;

    private String picture;

    private Double price;

    private int numberOfPlaces;

    private Event event;

    public CategoryTicket() {}

    public CategoryTicket(int id, String name, String picture, Double price, int numberOfPlaces, Event event) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.numberOfPlaces = numberOfPlaces;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
