package ma.ensias.ticket_me.api;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ma.ensias.ticket_me.model.CategoryTicket;
import ma.ensias.ticket_me.model.Event;
import ma.ensias.ticket_me.requests.RequestCategory;
import ma.ensias.ticket_me.response.ResponseCategories;
import ma.ensias.ticket_me.response.ResponseCategory;
import ma.ensias.ticket_me.response.ResponseEventInfo;
import ma.ensias.ticket_me.response.ResponseJoin;
import ma.ensias.ticket_me.response.ResponseListCategory;
import ma.ensias.ticket_me.response.ResponseListEvents;
import ma.ensias.ticket_me.response.ResponseLogin;
import ma.ensias.ticket_me.response.ResponseEvent;
import ma.ensias.ticket_me.response.ResponseSignUp;
import ma.ensias.ticket_me.requests.RequestEvent;
import ma.ensias.ticket_me.response.ResponseTicket;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("login")
    Call<ResponseLogin> VerifyLogin(@Body HashMap<String,String> userinfos);

    @POST("signup")
    Call<ResponseSignUp> signUp(@Body HashMap<String,String> infos );

    @POST("createevent")
    Call<ResponseEvent> createEvent(@Body RequestEvent infos);

    @POST("createcategory")
    Call<ResponseCategory> createCategory(@Body RequestCategory infos);

    @GET("categories")
    Call<ResponseListCategory> getCategories(@Query("id_event") int event);

    @GET("allevents")
    Call<ResponseListEvents> getEvents();

    @GET("myevents")
    Call<ResponseListEvents> getEventadmin(@Query("id_session") int id_session);

    @GET("event")
    Call<ResponseEventInfo> getEvent(@Query("id_event") int id_event);

    @POST("checkticket")
    Call<ResponseTicket> verifyTicket(@Body HashMap<String,String> ticket);

    @POST("validateticket")
    Call<Boolean> validateTicket(@Body HashMap<String,String> ticket);

    @GET("categories")
    Call<ResponseCategories> getCategories2(@Query("id_event") int event);

    @POST("createticket")
    Call<ResponseBody> createTicket(@Body HashMap<String,String> ticket);

    @POST("joinevent")
    Call<ResponseJoin> joinevent(@Body HashMap<String,String> event);

}
