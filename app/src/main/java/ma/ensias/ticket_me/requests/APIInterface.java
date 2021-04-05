package ma.ensias.ticket_me.requests;


import ma.ensias.ticket_me.model.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("login")
    Call<Boolean> VerifyLogin(@Query("username") String username,@Query("passwod") String password);

    @POST("signup")
    Call<User> signUp(@Query("username") String username, @Query("passwod") String password);





}
