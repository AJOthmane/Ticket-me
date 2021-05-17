package ma.ensias.ticket_me.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

import com.google.android.material.navigation.NavigationView;

import ma.ensias.ticket_me.R;

public class StatsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtviewtitle;

    /*************** Menu Variables ***************/
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout header_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        txtviewtitle = (TextView) findViewById(R.id.title);

        /*************** Getting passed values from previous activity ***************/
        if(this.getIntent().getExtras() != null){
            String s = this.getIntent().getExtras().getString("title");
            String title = "Statistics for category <b>" + s + "</b>";
            System.out.println(Html.fromHtml(title));
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

        System.out.println(arrow_drop);

        arrow_drop.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(StatsActivity.this, arrow_drop);
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
}