package ma.ensias.ticket_me.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ma.ensias.ticket_me.model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {

        public final static String URL_API = "https://module-valide.herokuapp.com/api/";
        private static Retrofit retrofit = null;
        private static Gson gson = new GsonBuilder().create();

        private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);

        private static OkHttpClient okHttpClient = okHttpClientBuilder.build();

        public static <T> T createService(Class<T> serviceClass){
                if(retrofit == null){
                        retrofit = new Retrofit.Builder()
                                .client(okHttpClient)
                                .baseUrl(URL_API)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                }
                return retrofit.create(serviceClass);
        }
}
