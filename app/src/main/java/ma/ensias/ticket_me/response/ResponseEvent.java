package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

public class ResponseEvent {
    @SerializedName("success")
    private String success;
    @SerializedName("key")
    private String key;

    public ResponseEvent(String success, String key) {
        this.success = success;
        this.key = key;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
