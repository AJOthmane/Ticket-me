package ma.ensias.ticket_me.requests;


import java.util.HashMap;
import java.util.List;

import ma.ensias.ticket_me.forms.ReponseLogin;
import ma.ensias.ticket_me.model.Event;
import ma.ensias.ticket_me.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {



    @POST("login")
    Call<ReponseLogin> VerifyLogin(@Body HashMap<String,String> user);

    @POST("signup")
    Call<User> signUp(@Query("username") String username, @Query("passwod") String password);

    @GET("events")
    Call<List<Event>> getUserEvent(@Query("userid") int id);


}
