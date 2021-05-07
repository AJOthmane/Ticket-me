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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    protected FloatingActionButton add;
    protected int idEvent;
    protected Event event;
    protected TextView name_of_event;
    protected Button location;
    protected RecyclerView categories;
    protected LinkedList<CategoryTicket> categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Intent i = getIntent();
        idEvent = i.getIntExtra("id_event",-1);
        name_of_event = (TextView)findViewById(R.id.name_event);
        location = (Button)findViewById(R.id.location_button);
        add = findViewById(R.id.add_category);
        categories = findViewById(R.id.list_categorie);
        categories.setLayoutManager(new LinearLayoutManager(this));


        add.setOnClickListener(v -> {

            CategoryCreationDialog ccd = new CategoryCreationDialog(this,idEvent);
            ccd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ccd.show();

        });

        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        /*
        Call<LinkedList<CategoryTicket>> call_Category = apiInterface.getCategories(idEvent);
        call_Category.enqueue(new Callback<LinkedList<CategoryTicket>>() {
            @Override
            public void onResponse(Call<LinkedList<CategoryTicket>> call, Response<LinkedList<CategoryTicket>> response) {
                if(response.code() == 200)
                {
                    categoriesList = response.body();
                    categories.setAdapter(new AdapterCategory(categoriesList,getApplicationContext()));
                }
                else
                {
                    Log.e("error_event","No event with the id given");
                }
            }

            @Override
            public void onFailure(Call<LinkedList<CategoryTicket>> call, Throwable t)
            {
                Log.e("error_event","Error connexion with api");
            }
        });

        Call<Event> call = apiInterface.getEvent(idEvent);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(response.code() == 200)
                {
                    event = response.body();
                }
                else
                {
                    Log.e("error_event","No event with the id given");
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t)
            {
                Log.e("error_event","Error connexion with api");
            }
        });

        name_of_event.setText(event.getName());


        location.setOnClickListener(v -> {

            Uri gmmIntentUri = Uri.parse("geo:0,0?q="+event.getLocation());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);


        });

         */



    }
}