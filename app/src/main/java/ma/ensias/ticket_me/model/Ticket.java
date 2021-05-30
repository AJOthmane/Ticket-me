package ma.ensias.ticket_me.model;

import java.util.Date;

public  class Ticket {

    private int id;

    private Event event;

    private CategoryTicket category;

    private Date dateOfCreation;

    private Date dateofConsumed;

    private int cinClient;

    private String nomClient;

    private String pictureClient;

    private int phoneNumberClient;

    private String email;

    private User userCreation;

    private User userConsumed;

    public Ticket() {}

    public Ticket(int id, Event event, CategoryTicket category, Date dateOfCreation, Date dateofConsumed,
                  int cinClient, String nomClient, String pictureClient, int phoneNumberClient, String email,
                  User userCreation, User userConsumed) {

        this.id = id;
        this.event = event;
        this.category = category;
        this.dateOfCreation = dateOfCreation;
        this.dateofConsumed = dateofConsumed;
        this.cinClient = cinClient;
        this.nomClient = nomClient;
        this.pictureClient = pictureClient;
        this.phoneNumberClient = phoneNumberClient;
        this.email = email;
        this.userCreation = userCreation;
        this.userConsumed = userConsumed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public CategoryTicket getCategory() {
        return category;
    }

    public void setCategory(CategoryTicket category) {
        this.category = category;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateofConsumed() {
        return dateofConsumed;
    }

    public void setDateofConsumed(Date dateofConsumed) {
        this.dateofConsumed = dateofConsumed;
    }

    public int getCinClient() {
        return cinClient;
    }

    public void setCinClient(int cinClient) {
        this.cinClient = cinClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPictureClient() {
        return pictureClient;
    }

    public void setPictureClient(String pictureClient) {
        this.pictureClient = pictureClient;
    }

    public int getPhoneNumberClient() {
        return phoneNumberClient;
    }

    public void setPhoneNumberClient(int phoneNumberClient) {
        this.phoneNumberClient = phoneNumberClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(User userCreation) {
        this.userCreation = userCreation;
    }

    public User getUserConsumed() {
        return userConsumed;
    }

    public void setUserConsumed(User userConsumed) {
        this.userConsumed = userConsumed;
    }
}
