package ma.ensias.ticket_me.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.fragments.EventInfo;


public class CreationEvent extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_event);

        if (savedInstanceState == null) {
            initializeFragment();
        }


    }
    private void initializeFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentCreationEvent, new EventInfo());
        fragmentTransaction.commit();
    }


}