package ma.ensias.ticket_me.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ma.ensias.ticket_me.Database.DBHandler;
import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.ListOfEventsActivity;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.model.Event;
import ma.ensias.ticket_me.response.ResponseJoin;
import ma.ensias.ticket_me.response.ResponseListEvents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinEventDialog extends Dialog {

    String eventkeySTR;
    EditText eventkey;
    TextView message;
    Button joinevent;
    Spinner eventspinner;

    public JoinEventDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.join_event);

        //Progress Bar of joining event
        ProgressBar pgsBar = (ProgressBar)findViewById(R.id.progressBar);

        eventkey = findViewById(R.id.eventKey);
        joinevent = findViewById(R.id.joinEvent);
        eventspinner = findViewById(R.id.eventsSpinner);
        message = findViewById(R.id.msg);

        //Database Handler Object
        DBHandler dbHandler = DBHandler.getInstance(getContext());

        List<Integer> eventsId = new ArrayList<>();
        List<String> eventSpinner = new ArrayList<>();

        //Recuperation des events dans le spinner
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<ResponseListEvents> call_event = apiInterface.getEvents();

        call_event.enqueue(new Callback<ResponseListEvents>() {
            @Override
            public void onResponse(Call<ResponseListEvents> call, Response<ResponseListEvents> response) {

                if (response.code() == 200) {
                        LinkedList<Event> bresponse = response.body().getEvents();
                        for (Event evnt:bresponse)
                        {
                            eventsId.add(evnt.getId());
                            eventSpinner.add(evnt.getName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, eventSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        eventspinner.setAdapter(adapter);

                } else {
                    Log.e("error_event", "code : "+response.code());
                }
                pgsBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseListEvents> call, Throwable t) {
                Log.e("error_event", "Error connexion with api");
                //viewEvents.setAdapter(new AdapterEvent(new LinkedList<Event>(),getContext()));
            }
        });


        joinevent.setOnClickListener(v -> {
            eventkeySTR = eventkey.getText().toString();
            try {
                if (eventkeySTR.isEmpty() )
                {
                    message.setTextColor(Color.RED);
                    message.setText("Please enter a key!");
                }
                else
                {
                    Boolean eventExists = false;
                    List<ma.ensias.ticket_me.Database.Event> eventList = dbHandler.eventDao().getEventList();
                    for(ma.ensias.ticket_me.Database.Event evnt : eventList){
                        if(evnt.getId() == eventsId.get(eventspinner.getSelectedItemPosition())){
                            eventExists = true;
                        }
                    }
                    if(eventExists){
                        message.setTextColor(Color.RED);
                        message.setText("You have already joined this event ! Check your list !");
                    }
                    else {
                        pgsBar.setVisibility(View.VISIBLE);
                        HashMap<String, String> reqBody = new HashMap<>();
                        reqBody.put("id_event", eventsId.get(eventspinner.getSelectedItemPosition()).toString());
                        reqBody.put("event_key", eventkeySTR);
                        Call<ResponseJoin> call = apiInterface.joinevent(reqBody);
                        call.enqueue(new Callback<ResponseJoin>() {
                            @Override
                            public void onResponse(Call<ResponseJoin> call, Response<ResponseJoin> response) {
                                if (response.code() == 500) {
                                    message.setTextColor(Color.RED);
                                    message.setText("Server Error");
                                } else {
                                    if (response.body().getValid()) {
                                        message.setTextColor(Color.RED);
                                        message.setText("Event joined!");
                                        Toast.makeText(getContext(), "Event Joined !", Toast.LENGTH_SHORT).show();
                                        ma.ensias.ticket_me.Database.Event event = new ma.ensias.ticket_me.Database.Event();
                                        event.id_event = eventsId.get(eventspinner.getSelectedItemPosition());
                                        event.name_event = eventspinner.getSelectedItem().toString();
                                        dbHandler.eventDao().insertEvent(event);
                                        dismiss();
                                    } else {
                                        message.setTextColor(Color.RED);
                                        message.setText("Key Not Valid! Please provide a valid key!");
                                    }
                                }
                                pgsBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<ResponseJoin> call, Throwable t) {
                                Log.e("Fail : Joining Event", t.getMessage());
                                pgsBar.setVisibility(View.GONE);
                            }
                        });
                    }

                }
            }catch(Exception ex)
            {
                System.out.println(ex);
                message.setTextColor(Color.RED);
                message.setText("Veuillez remplir tous les champs");
                pgsBar.setVisibility(View.GONE);

            }

        });


    }
}
