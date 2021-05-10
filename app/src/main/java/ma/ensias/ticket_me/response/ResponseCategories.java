package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class ResponseCategories {

    @SerializedName("categories")
    private List<HashMap<String,String>> categories;

    public ResponseCategories(List<HashMap<String,String>> categories) {
        this.categories = categories;
    }

    public boolean getValid() {
        return (categories == null) ? false : true;
    }

    public List<HashMap<String,String>> getCategories()
    {
        return categories;
    }
}
