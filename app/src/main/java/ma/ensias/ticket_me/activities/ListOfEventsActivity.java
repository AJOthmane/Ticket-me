package ma.ensias.ticket_me.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.ui.main.SectionsPagerAdapter;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.model.Event;
import ma.ensias.ticket_me.response.ResponseListEvents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfEventsActivity extends AppCompatActivity {

    public int id_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_events);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.create_event);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this,CreationEvent.class);
            startActivity(intent);
        });

    }
}