package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class ResponseCategories {
    @SerializedName("valid")
    private boolean valid ;
    @SerializedName("categories")
    private List<HashMap<String,String>> categories;

    public ResponseCategories(boolean valid, List<HashMap<String,String>> categories) {
        this.valid = valid;
        this.categories = categories;
    }

    public boolean getValid() {
        return valid;
    }

    public List<HashMap<String,String>> getCategories()
    {
        return categories;
    }
}
