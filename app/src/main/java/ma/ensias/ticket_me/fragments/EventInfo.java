package ma.ensias.ticket_me.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.CreationEvent;


public class EventInfo extends Fragment {

    Button next ;
    EditText name;
    DatePicker date;
    TimePicker time;


    public EventInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View infoView = inflater.inflate(R.layout.event_info_fragment, container, false);
        next = infoView.findViewById(R.id.button_creation_event_next);
        name = infoView.findViewById(R.id.nameOfEventInput);
        date = infoView.findViewById(R.id.dateEvent);
        time = infoView.findViewById(R.id.timeOfEvent);

        next.setOnClickListener(v -> {

                    String nameText = name.getText().toString();

                    Date dateD = new Date(date.getYear(),date.getMonth(),date.getDayOfMonth(),time.getHour(),time.getMinute());

                    if(nameText.isEmpty() )
                    {
                        Snackbar.make(getView(),"Veuillez remplir tous les champs la date est : ",Snackbar.LENGTH_LONG).show();
                    }
                    else if(dateD.before(new Date()))
                    {
                        Snackbar.make(getView(),"date : "+dateD,Snackbar.LENGTH_LONG).show();
                    }
                    else
                    {
                        Snackbar.make(getView(),""+dateD,Snackbar.LENGTH_LONG).show();
                        
                    }


                }

        );


        return infoView;
    }


}