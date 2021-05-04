package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView ticketNumber = (TextView) findViewById(R.id.numero);
        TextView ticketOwner = (TextView) findViewById(R.id.nom);
        TextView ticketCategory = (TextView) findViewById(R.id.categorie);
        TextView ticketCin = (TextView) findViewById(R.id.cin);
        TextView ticketState = (TextView) findViewById(R.id.etat);
        Button validationButton = (Button) findViewById(R.id.valider);
        String code = getIntent().getStringExtra("code");
        ticketNumber.setText(code);
        ticketOwner.setVisibility(View.GONE);
        ticketCategory.setVisibility(View.GONE);
        ticketCin.setVisibility(View.GONE);
        ticketState.setVisibility(View.GONE);
        validationButton.setEnabled(false);
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
                    ticketCin.setText(response.body().getTicket("CIN"));
                    ticketCategory.setText(response.body().getTicket("CATEGORIE"));
                    if(response.body().getTicket("DATE") == null)
                    {
                        ticketState.setText("Ce ticket est valide");
                        validationButton.setEnabled(true);
                    }
                    else
                    {
                        ticketState.setText("Ce ticket est déja utilisé");
                    }

                    ticketOwner.setVisibility(View.VISIBLE);
                    ticketCategory.setVisibility(View.VISIBLE);
                    ticketCin.setVisibility(View.VISIBLE);
                    ticketState.setVisibility(View.VISIBLE);
                }
                else
                {
                    ticketState.setText("Ce ticket est inexistant");
                }
            }

            @Override
            public void onFailure(Call<ResponseTicket> call, Throwable t) {
                Log.e("Fail : ticket check",t.getMessage());
            }
        });
        validationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Call<Boolean> call = apiInterface.validateTicket(reqBody);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Intent intent = new Intent(getBaseContext(), QrScanner.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(TicketCheck.this, "Une erreur est apparue", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}