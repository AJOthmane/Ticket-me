package ma.ensias.ticket_me.requests;


import java.util.HashMap;
import java.util.List;

import ma.ensias.ticket_me.forms.ReponseLogin;
import ma.ensias.ticket_me.forms.ResponseSignUp;
import ma.ensias.ticket_me.model.Event;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {



    @POST("login")
    Call<ReponseLogin> VerifyLogin(@Body HashMap<String,String> userinfos);

    @POST("signup")
    Call<ResponseSignUp> signUp(@Body HashMap<String,String> infos );

    @POST("createEvent")
    Call<Event> createEvent(@Body HashMap<String,String> infos);

    @GET("events")
    Call<List<Event>> getUserEvent(@Query("userid") int id);


}
