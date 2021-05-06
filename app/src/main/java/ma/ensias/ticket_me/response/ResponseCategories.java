package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ResponseCategories {
    @SerializedName("valid")
    private boolean valid ;
    @SerializedName("categories")
    private HashMap<String,String> categories;

    public ResponseCategories(boolean valid, HashMap<String,String> categories) {
        this.valid = valid;
        this.categories = categories;
    }

    public boolean getValid() {
        return valid;
    }

    public String getTicket(String param)
    {
        return categories.get(param);
    }
}
