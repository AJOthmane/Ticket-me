package ma.ensias.ticket_me.model;

public class CategoryTicket {

    private int id;

    private String name;

    private String picture;

    private Double prix;

    private int numberOfPlaces;

    private Event event;

    public CategoryTicket() {}

    public CategoryTicket(int id, String name, String picture, Double prix, int numberOfPlaces, Event event) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.prix = prix;
        this.numberOfPlaces = numberOfPlaces;
        this.event = event;
    }
}
