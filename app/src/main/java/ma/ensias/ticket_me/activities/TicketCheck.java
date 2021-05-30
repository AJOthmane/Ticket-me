package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.response.ResponseTicket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check);

        // intergration prep work
        int event = 1;
        int id = 1;


        TextView ticketNumber = (TextView) findViewById(R.id.numero);
        TextView ticketOwner = (TextView) findViewById(R.id.nom);
        TextView ticketCategory = (TextView) findViewById(R.id.categorie);
        TextView ticketCin = (TextView) findViewById(R.id.cin);
        TextView ticketState = (TextView) findViewById(R.id.etat);
        Button validationButton = (Button) findViewById(R.id.valider);
        ProgressBar pgsBar = (ProgressBar)findViewById(R.id.pBar);
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
                    if(!Integer.toString(event).equals(response.body().getTicket("EVENT")))
                    {
                        ticketState.setText("Ce ticket est pour un autre event");
                    }
                    else if(response.body().getTicket("DATE") == null)
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
                    ticketState.setVisibility(View.VISIBLE);
                }
                pgsBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseTicket> call, Throwable t) {
                Log.e("Fail : ticket check",t.getMessage());
                Toast.makeText(TicketCheck.this, "Server is offline", Toast.LENGTH_SHORT).show();
                pgsBar.setVisibility(View.GONE);
            }
        });
        validationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pgsBar.setVisibility(View.VISIBLE);
                Call<Boolean> call = apiInterface.validateTicket(reqBody);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        pgsBar.setVisibility(View.GONE);
                        Toast.makeText(TicketCheck.this, "Le ticket a été validé avec succes", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getBaseContext(), QrScanner.class);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        }, 2000);

                        // wait to show validation
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(TicketCheck.this, "Server Error", Toast.LENGTH_SHORT).show();
                        pgsBar.setVisibility(View.GONE);
                    }
                });

            }
        });
    }
}