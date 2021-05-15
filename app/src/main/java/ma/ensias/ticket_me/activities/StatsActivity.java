package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import ma.ensias.ticket_me.R;

public class StatsActivity extends AppCompatActivity {

    TextView txtviewtitle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        /*************** Hooks ***************/
        txtviewtitle = (TextView) findViewById(R.id.title);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*************** Getting passed values from previous activity ***************/
        if(this.getIntent().getExtras() != null){
            String s = this.getIntent().getExtras().getString("title");
            String title = "Statistics for category <b>" + s + "</b>";
            System.out.println(Html.fromHtml(title));
            txtviewtitle.setText(Html.fromHtml(title));
        }

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer_content, R.string.close_drawer_content);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



    }
}