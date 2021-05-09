package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    protected TextView name_of_event , date_event;
    protected Button location;
    protected RecyclerView categories;
    protected ResponseListCategory categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Intent i = getIntent();
        idEvent = i.getIntExtra("id_event",-1);
        name_of_event = (TextView)findViewById(R.id.name_event);
        date_event = (TextView) findViewById(R.id.date_values);
        location = (Button)findViewById(R.id.location_button);
        add = findViewById(R.id.add_category);
        categories = findViewById(R.id.list_categorie);
        categories.setLayoutManager(new LinearLayoutManager(this));
        loadDate();

        add.setOnClickListener(v -> {

            CategoryCreationDialog ccd = new CategoryCreationDialog(this,idEvent);
            ccd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ccd.show();

        });
    }
    public void loadDate()
    {
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<ResponseListCategory> call_Category = apiInterface.getCategories(idEvent);
        call_Category.enqueue(new Callback<ResponseListCategory>() {
            @Override
            public void onResponse(Call<ResponseListCategory> call, Response<ResponseListCategory> response) {
                if(response.code() == 200)
                {
                    categoriesList = response.body();
                    categories.setAdapter(new AdapterCategory(categoriesList.getListOfEvents(),getApplicationContext()));
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
                Log.e("error_event","Error connexion with api");
            }
        });
    }
}