package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.fragments.EventInfo;


public class CreationEvent extends AppCompatActivity  {

    Button button ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_event);

        if (savedInstanceState == null) {
            initializeFragment();
        }

    }

    public void loadFragment(Fragment fragment)
    {
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        fragmentTransaction.replace(R.id.fragmentLoginSignup,fragment);
        // replace the FrameLayout with new Fragment
        //fragmentTransaction.add(R.id.fragmentLoginSignup,.class,null);
        fragmentTransaction.commit(); // save the changes

    }
    private void initializeFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentCreationEvent, new EventInfo());
        fragmentTransaction.commit();
    }


}