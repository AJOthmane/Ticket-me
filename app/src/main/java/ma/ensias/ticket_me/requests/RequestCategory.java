package ma.ensias.ticket_me.requests;

import com.google.gson.annotations.SerializedName;

public class RequestCategory {


    private String nom;

    private double prix;

    private int places;

    private int id_event;

    public RequestCategory(String nom, double prix, int places, int id_event) {
        this.nom = nom;
        this.prix = prix;
        this.places = places;
        this.id_event = id_event;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }
}
