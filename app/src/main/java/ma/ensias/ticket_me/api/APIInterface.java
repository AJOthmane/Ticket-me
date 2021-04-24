package ma.ensias.ticket_me.api;


import java.util.HashMap;

import ma.ensias.ticket_me.requests.RequestCategory;
import ma.ensias.ticket_me.response.ResponseCategory;
import ma.ensias.ticket_me.response.ResponseLogin;
import ma.ensias.ticket_me.response.ResponseEvent;
import ma.ensias.ticket_me.response.ResponseSignUp;
import ma.ensias.ticket_me.requests.RequestEvent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("login")
    Call<ResponseLogin> VerifyLogin(@Body HashMap<String,String> userinfos);

    @POST("signup")
    Call<ResponseSignUp> signUp(@Body HashMap<String,String> infos );

    @POST("createevent")
    Call<ResponseEvent> createEvent(@Body RequestEvent infos);

    @POST("createcategory")
    Call<ResponseCategory> createCategory(@Body RequestCategory infos);
}
