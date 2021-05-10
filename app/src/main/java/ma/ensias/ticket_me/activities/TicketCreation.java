package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        List<String> categoriesId = new ArrayList<String>();
        List<String> categoriesSpinner = new ArrayList<String>();
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<ResponseCategories> call = apiInterface.getCategories2(1);
        call.enqueue(new Callback<ResponseCategories>() {
            @Override
            public void onResponse(Call<ResponseCategories> call, Response<ResponseCategories> response) {
                if(response.body().getValid())
                {
                    List<HashMap<String,String>> bresponse = response.body().getCategories();
                    for (HashMap<String,String> cat:bresponse
                         ) {
                        categoriesId.add(cat.get("id_categorie"));
                        categoriesSpinner.add(cat.get("nom_categorie")+" - "+cat.get("prix_categorie")+" DH");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, categoriesSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categories.setAdapter(adapter);
                }
                else
                {
                    // event dont exist or dont have categories
                }
            }

            @Override
            public void onFailure(Call<ResponseCategories> call, Throwable t) {
                Log.e("Fail : Categories check",t.getMessage());
            }
        });
        creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage( getString(R.string.ticket_creation_confirmation_content) +" "+ categories.getSelectedItem().toString());
                builder.setTitle(R.string.ticket_creation_confirmation_title);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // to replace later
                        finish();
                        Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}