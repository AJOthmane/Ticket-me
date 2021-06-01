package ma.ensias.ticket_me.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.Chart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.ui.main.SectionsPagerAdapter;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.dialog.CategoryCreationDialog;
import ma.ensias.ticket_me.dialog.JoinEventDialog;
import ma.ensias.ticket_me.model.Event;
import ma.ensias.ticket_me.response.ResponseListEvents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfEventsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public int id_session;

    /*************** Menu Variables ***************/
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_events);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabs.setupWithViewPager(viewPager);

        /******* Join Button *******/
        Button join = findViewById(R.id.joinbtn);
        join.setOnClickListener(v -> {
            JoinEventDialog jed = new JoinEventDialog(this);
            jed.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            jed.show();

            jed.setOnDismissListener(new DialogInterface.OnDismissListener(){

                @Override
                public void onDismiss(DialogInterface dialog) {
                    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getSupportFragmentManager());
                    ViewPager viewPager = findViewById(R.id.view_pager);
                    viewPager.setAdapter(sectionsPagerAdapter);
                    TabLayout tabs = findViewById(R.id.tabs);
                    tabs.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
                    tabs.setupWithViewPager(viewPager);
                    FloatingActionButton fab = findViewById(R.id.create_event);
                    fab.setOnClickListener(v -> {
                        Intent intent = new Intent(getApplicationContext(),CreationEvent.class);
                        startActivity(intent);
                    });
                }
            });
        });

        /*************** Menu Hooks ***************/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        /*************** Items to show and to hide ***************/
        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.item1).setVisible(false);

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView username = (TextView) header.findViewById(R.id.username);
        username.setText("Omarirro");

        ImageButton arrow_drop = (ImageButton) header.findViewById(R.id.arrow_dropdown);

        arrow_drop.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(ListOfEventsActivity.this, arrow_drop);
            popup.inflate(R.menu.popup_menu_layout);

            popup.setOnMenuItemClickListener(item -> menuItemClicked(item));

            popup.show();
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                break;

            case R.id.item2:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.item3:
                break;

            case R.id.version:
                Toast.makeText(this, "Version 0.0.0.1", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                Toast.makeText(this, "Signed Out successfully", Toast.LENGTH_SHORT).show();

                SharedPreferences sp = getSharedPreferences("LoginForm", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.clear();
                ed.commit();

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}