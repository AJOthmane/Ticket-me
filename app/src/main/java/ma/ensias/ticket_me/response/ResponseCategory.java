package ma.ensias.ticket_me.response;

import com.google.gson.annotations.SerializedName;

public class ResponseCategory {
    @SerializedName("success")
    private boolean success;
    @SerializedName("error")
    private String error;

    public ResponseCategory(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
