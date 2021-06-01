package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

public class ResponseJoin {
    @SerializedName("valid")
    private boolean valid ;

    public ResponseJoin(boolean valid) {
        this.valid = valid;
    }

    public boolean getValid() {
        return valid;
    }
}
