package ma.ensias.ticket_me.forms;

import com.google.gson.annotations.SerializedName;

public class ResponseSignUp {

    @SerializedName("success")
    private Boolean success ;
    @SerializedName("error")
    private String error;

    public ResponseSignUp() {
    }

    public ResponseSignUp(Boolean id,String auth) {
        this.success = id;
        this.error = auth;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
