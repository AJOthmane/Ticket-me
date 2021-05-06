package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.response.ResponseCategories;
import ma.ensias.ticket_me.response.ResponseTicket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_creation);
        EditText cin = (EditText) findViewById(R.id.ccin);
        EditText nom = (EditText) findViewById(R.id.cnom);
        EditText prenom = (EditText) findViewById(R.id.cprenom);
        EditText email = (EditText) findViewById(R.id.cemail);
        Spinner categories = (Spinner) findViewById(R.id.spinner);
        Button creer = (Button) findViewById(R.id.creerTicket);
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        HashMap<String,String> reqBody = new HashMap<>();
        reqBody.put("event","1");
        Call<ResponseCategories> call = apiInterface.getCategories(reqBody);
        call.enqueue(new Callback<ResponseCategories>() {
            @Override
            public void onResponse(Call<ResponseCategories> call, Response<ResponseCategories> response) {
                if(response.body().getValid())
                {

                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseCategories> call, Throwable t) {
                Log.e("Fail : Categories check",t.getMessage());
            }
        });
    }
}