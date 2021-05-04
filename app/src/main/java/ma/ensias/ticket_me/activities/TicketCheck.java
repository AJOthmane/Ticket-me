package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.response.ResponseLogin;
import ma.ensias.ticket_me.response.ResponseTicket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check);
        TextView ticketNumber = (TextView) findViewById(R.id.textView4);
        TextView ticketOwner = (TextView) findViewById(R.id.textView7);
        String code = getIntent().getStringExtra("code");
        ticketNumber.setText(code);
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        HashMap<String,String> reqBody = new HashMap<>();
        reqBody.put("ticket",code);
        Call<ResponseTicket> call = apiInterface.verifyTicket(reqBody);
        call.enqueue(new Callback<ResponseTicket>() {
            @Override
            public void onResponse(Call<ResponseTicket> call, Response<ResponseTicket> response) {
                if(response.body().getValid())
                {
                    ticketOwner.setText(response.body().getTicket("NOM")+" "+response.body().getTicket("PRENOM"));
                }
            }

            @Override
            public void onFailure(Call<ResponseTicket> call, Throwable t) {
                Log.e("Fail : ticket check",t.getMessage());
            }
        });
    }
}