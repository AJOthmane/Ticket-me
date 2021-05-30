package ma.ensias.ticket_me.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.LinkedList;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.fragments.Chart;
import ma.ensias.ticket_me.fragments.LoginForm;
import ma.ensias.ticket_me.model.Ticket;
import ma.ensias.ticket_me.response.ResponseEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int id_category;
    LinkedList<Ticket> tickets;

    TextView txtviewtitle;

    /*************** Menu Variables ***************/
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        if (savedInstanceState == null) {
            initializeFragment();
        }

        HashMap<String, Integer> infos = new HashMap<>();
        infos.put("id_category",1);
        infos.put("id_event", 1);
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<LinkedList<Ticket>> call = apiInterface.getTickets(infos);
        call.enqueue(new Callback<LinkedList<Ticket>>() {
            @Override
            public void onResponse(Call<LinkedList<Ticket>> call, Response<LinkedList<Ticket>> response) {
                Toast.makeText(StatsActivity.this, "Response", Toast.LENGTH_SHORT).show();
                if(response.code() == 200)
                {
                    tickets = response.body();
                }

                if(response.code() == 500)
                {
                    Log.e("Error", "Error 500");
                }
            }
            @Override
            public void onFailure(Call<LinkedList<Ticket>> call, Throwable t) {
                Log.e("Stats ",t.getMessage());
            }
        });

        txtviewtitle = (TextView) findViewById(R.id.title);

        /*************** Getting passed values from previous activity ***************/
        if(this.getIntent().getExtras() != null){
            String s = this.getIntent().getExtras().getString("title");
            String title = "Statistics - <b>" + s + "</b>";
            txtviewtitle.setText(Html.fromHtml(title));
        }

        /*************** Menu Hooks ***************/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        /*************** Items to show and to hide ***************/
        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.item1).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer_content, R.string.close_drawer_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView username = (TextView) header.findViewById(R.id.username);
        username.setText(getUsername(1));

        ImageButton arrow_drop = (ImageButton) header.findViewById(R.id.arrow_dropdown);

        arrow_drop.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(StatsActivity.this, arrow_drop);
            popup.inflate(R.menu.popup_menu_layout);

            popup.setOnMenuItemClickListener(item -> menuItemClicked(item));

            popup.show();
        });

        SegmentedButtonGroup sbg = (SegmentedButtonGroup) findViewById(R.id.segmBtnGrp);
        sbg.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
                if(position == 0){
//                    Toast.makeText(StatsActivity.this, "Day", Toast.LENGTH_SHORT).show();
                    loadFragment(new Chart(0, tickets));
                } else if(position == 1){
//                    Toast.makeText(StatsActivity.this, "Week", Toast.LENGTH_SHORT).show();
                    loadFragment(new Chart(1, tickets));
                } else if(position == 2){
//                    Toast.makeText(StatsActivity.this, "All", Toast.LENGTH_SHORT).show();
                    loadFragment(new Chart(2, tickets));
                }
            }
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
                Toast.makeText(this, "Bookmark", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private String getUsername(int id){
        return "CHBAB Omar";
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        fragmentTransaction.replace(R.id.frameLayout, fragment);

        fragmentTransaction.commit();
    }
    private void initializeFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, new Chart(1, tickets));
        fragmentTransaction.commit();
    }
}