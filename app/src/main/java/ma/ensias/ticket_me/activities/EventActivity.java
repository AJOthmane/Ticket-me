package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import ma.ensias.ticket_me.adpater.AdapterCategory;
import ma.ensias.ticket_me.dialog.CategoryCreationDialog;
import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.fragments.LoginForm;
import ma.ensias.ticket_me.model.CategoryTicket;
import ma.ensias.ticket_me.model.Event;
import ma.ensias.ticket_me.response.ResponseEventInfo;
import ma.ensias.ticket_me.response.ResponseListCategory;
import ma.ensias.ticket_me.response.ResponseListEvents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    protected FloatingActionButton add;
    protected int idEvent;
    protected ResponseEventInfo event;
    protected TextView  date_event,empty;
    protected Button name_of_event,location,ticketCreation,ticketValidation;
    protected RecyclerView categories;
    protected ResponseListCategory categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Intent i = getIntent();
        idEvent = i.getIntExtra("id_event",-1);
        name_of_event = (Button) findViewById(R.id.name_event);
        date_event = (TextView) findViewById(R.id.date_values);
        location = (Button)findViewById(R.id.location_button);
        ticketCreation = (Button)findViewById(R.id.ticketcreation);
        ticketValidation = (Button)findViewById(R.id.ticketvalidation);
        add = findViewById(R.id.add_category);
        categories = findViewById(R.id.list_categorie);
        categories.setLayoutManager(new LinearLayoutManager(this));
        categories.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        loadData();

        add.setOnClickListener(v -> {

            CategoryCreationDialog ccd = new CategoryCreationDialog(this,idEvent);
            ccd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ccd.show();

        });
        ticketValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), QrScanner.class);
                intent.putExtra("event",idEvent);
                startActivity(intent);
            }
        });
        ticketCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginForm.SESSION_SP_NAME, Context.MODE_PRIVATE);
                int idUser = sharedPreferences.getInt("ID_SESSION",-1);
                Intent intent = new Intent(getBaseContext(), TicketCreation.class);
                intent.putExtra("event",idEvent);
                intent.putExtra("id",idUser);
                startActivity(intent);
            }
        });
    }
    public void loadData()
    {
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<ResponseListCategory> call_Category = apiInterface.getCategories(idEvent);
        call_Category.enqueue(new Callback<ResponseListCategory>() {
            @Override
            public void onResponse(Call<ResponseListCategory> call, Response<ResponseListCategory> response) {
                if(response.code() == 200)
                {
                    if(response.body().getListOfEvents() != null)
                    {
                        categoriesList = response.body();
                        categories.setAdapter(new AdapterCategory(idEvent ,categoriesList.getListOfEvents(), getApplicationContext()));
                    }
                }
                else
                {
                    Log.e("error_event","No event with the id given");

                }
            }

            @Override
            public void onFailure(Call<ResponseListCategory> call, Throwable t)
            {
                Log.e("error_event","Error connexion with api");
            }
        });

        Call<ResponseEventInfo> call = apiInterface.getEvent(idEvent);
        call.enqueue(new Callback<ResponseEventInfo>() {
            @Override
            public void onResponse(Call<ResponseEventInfo> call, Response<ResponseEventInfo> response) {
                if(response.code() == 200)
                {

                    event = response.body();
                    name_of_event.setText(event.getEvent().getName());
                    date_event.setText(event.getEvent().getDate()+"");
                    location.setOnClickListener(v -> {

                        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+event.getEvent().getLocation());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                    });
                }
                else
                {

                    Log.e("error_event","No event with the id given");

                }
            }
            @Override
            public void onFailure(Call<ResponseEventInfo> call, Throwable t)
            {
                empty.setVisibility(View.GONE);
                categories.setVisibility(View.VISIBLE);
                Log.e("error_event","Error connexion with api");
            }
        });
    }
    @Override
    public void onResume()
    {
        super.onResume();
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<ResponseListCategory> call_Category = apiInterface.getCategories(idEvent);
        call_Category.enqueue(new Callback<ResponseListCategory>() {
            @Override
            public void onResponse(Call<ResponseListCategory> call, Response<ResponseListCategory> response) {
                if(response.code() == 200)
                {
                    if(response.body().getListOfEvents() != null)
                    {
                        categoriesList = response.body();
                        categories.setAdapter(new AdapterCategory(idEvent ,categoriesList.getListOfEvents(), getApplicationContext()));
                    }
                }
                else
                {
                    Log.e("error_event","No event with the id given");

                }
            }

            @Override
            public void onFailure(Call<ResponseListCategory> call, Throwable t)
            {
                Log.e("error_event","Error connexion with api");
            }
        });

    }
}