package ma.ensias.ticket_me.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

import ma.ensias.ticket_me.Database.DBHandler;
import ma.ensias.ticket_me.Database.Event;
import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.adpater.AdapterDBEvent;
import ma.ensias.ticket_me.adpater.AdapterEvent;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.model.CategoryTicket;
import ma.ensias.ticket_me.response.ResponseEvent;
import ma.ensias.ticket_me.response.ResponseListEvents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListEventsFragment extends Fragment {


    private boolean admin;
    private int ID_SESSION;
    public RecyclerView viewEvents;
    public View v;

    public ListEventsFragment(boolean admin,int ID_SESSION)  {
        this.admin = admin;
        this.ID_SESSION = ID_SESSION;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_list_of_events, container, false);

        ProgressDialog progress = new ProgressDialog(this.getContext());
        progress.setTitle("Loading Data");
        progress.setMessage("Wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<ResponseListEvents> call_event = null;
        if(admin){
            call_event = apiInterface.getEventadmin(ID_SESSION);

            call_event.enqueue(new Callback<ResponseListEvents>() {
                @Override
                public void onResponse(Call<ResponseListEvents> call, Response<ResponseListEvents> response) {

                    if (response.code() == 200) {

                        viewEvents = v.findViewById(R.id.list_of_events).findViewById(R.id.list_of_events);
                        viewEvents.addItemDecoration(new DividerItemDecoration(getContext(),
                                DividerItemDecoration.VERTICAL));
                        viewEvents.setLayoutManager(new LinearLayoutManager(getContext()));
                        viewEvents.setAdapter(new AdapterEvent(response.body().getEvents(),getContext()));
                        progress.dismiss();

                    } else {
                        Log.e("error_event", "code : "+response.code());

                    }
                }

                @Override
                public void onFailure(Call<ResponseListEvents> call, Throwable t) {
                    Log.e("error_event", "Error connexion with api");
                    //viewEvents.setAdapter(new AdapterEvent(new LinkedList<Event>(),getContext()));
                }
            });
        }
        else
        {
            //Get Events from local database
            DBHandler dbHandler = DBHandler.getInstance(this.getContext());
            List<Event> eventList = dbHandler.eventDao().getEventList();

            viewEvents = v.findViewById(R.id.list_of_events);
            viewEvents.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            viewEvents.setLayoutManager(new LinearLayoutManager(getContext()));
            viewEvents.setAdapter(new AdapterDBEvent(eventList,getContext()));
            progress.dismiss();
        }


        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        ProgressDialog progress = new ProgressDialog(this.getContext());
        progress.setTitle("Loading Data");
        progress.setMessage("Wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<ResponseListEvents> call_event = null;
        if(admin){
            call_event = apiInterface.getEventadmin(ID_SESSION);

            call_event.enqueue(new Callback<ResponseListEvents>() {
                @Override
                public void onResponse(Call<ResponseListEvents> call, Response<ResponseListEvents> response) {

                    if (response.code() == 200) {

                        viewEvents = v.findViewById(R.id.list_of_events).findViewById(R.id.list_of_events);
                        viewEvents.addItemDecoration(new DividerItemDecoration(getContext(),
                                DividerItemDecoration.VERTICAL));
                        viewEvents.setLayoutManager(new LinearLayoutManager(getContext()));
                        viewEvents.setAdapter(new AdapterEvent(response.body().getEvents(),getContext()));
                        progress.dismiss();

                    } else {
                        Log.e("error_event", "code : "+response.code());

                    }
                }

                @Override
                public void onFailure(Call<ResponseListEvents> call, Throwable t) {
                    Log.e("error_event", "Error connexion with api");
                    //viewEvents.setAdapter(new AdapterEvent(new LinkedList<Event>(),getContext()));
                }
            });
        }
        else
        {
            //Get Events from local database
            DBHandler dbHandler = DBHandler.getInstance(this.getContext());
            List<Event> eventList = dbHandler.eventDao().getEventList();

            viewEvents = v.findViewById(R.id.list_of_events);
            viewEvents.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            viewEvents.setLayoutManager(new LinearLayoutManager(getContext()));
            viewEvents.setAdapter(new AdapterDBEvent(eventList,getContext()));
            progress.dismiss();
        }
    }

}