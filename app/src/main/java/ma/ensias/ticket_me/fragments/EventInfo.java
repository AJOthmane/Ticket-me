package ma.ensias.ticket_me.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import ma.ensias.ticket_me.activities.MapsActivity;
import ma.ensias.ticket_me.R;


public class EventInfo extends Fragment {

    public static final String NAME_OF_EVENT  = "nameOfEvent";
    public static final String DATE_OF_EVENT  = "dateOfEvent";

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

                    Calendar datePicked = Calendar.getInstance();
                    datePicked.set(Calendar.YEAR,date.getYear());
                    datePicked.set(Calendar.MONTH,date.getMonth());
                    datePicked.set(Calendar.DAY_OF_MONTH,date.getDayOfMonth());
                    datePicked.set(Calendar.HOUR,time.getHour()) ;
                    datePicked.set(Calendar.MINUTE,time.getMinute()) ;
                    Calendar dateSys = Calendar.getInstance();
                    GregorianCalendar sysdate = new GregorianCalendar();

                    
                    if(nameText.isEmpty() )
                    {
                        Snackbar.make(getView(),"Veuillez remplir tous les champs la date est : ",Snackbar.LENGTH_LONG).show();
                    }
                    else if(datePicked.compareTo(dateSys) < 0)
                    {
                        Snackbar.make(getView(),"Vous pouvez pas choisir une date dans le passe",Snackbar.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent i = new Intent( (getActivity()), MapsActivity.class);

                        i.putExtra(NAME_OF_EVENT,nameText);
                        i.putExtra(DATE_OF_EVENT,transformDate(datePicked));
                        startActivity(i);

                    }
                }

        );


        return infoView;
    }
    public String transformDate(Calendar gc)
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        fmt.setCalendar(gc);
        return fmt.format(gc.getTime());
    }

}