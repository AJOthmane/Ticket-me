package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("id")
    private int id ;
    @SerializedName("auth")
    private boolean auth;

    public ResponseLogin(int id, Boolean auth) {
        this.id = id;
        this.auth = auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}
