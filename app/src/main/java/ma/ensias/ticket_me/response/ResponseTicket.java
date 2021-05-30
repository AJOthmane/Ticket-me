package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ResponseTicket {
    @SerializedName("valid")
    private boolean valid ;
    @SerializedName("ticket")
    private HashMap<String,String> ticket;

    public ResponseTicket(boolean valid, HashMap<String,String> ticket) {
        this.valid = valid;
        this.ticket = ticket;
    }

    public boolean getValid() {
        return valid;
    }

    public String getTicket(String param)
    {
        return ticket.get(param);
    }
}
