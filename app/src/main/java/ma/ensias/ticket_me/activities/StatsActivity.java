package ma.ensias.ticket_me.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.fragments.Chart;

public class StatsActivity extends AppCompatActivity {

    int id_category;
    int id_event;
    TextView txtviewtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent i = getIntent();
        id_event = i.getIntExtra("id_event",-1);
        id_category = i.getIntExtra("id_category",-1);

        if (savedInstanceState == null) {
            initializeFragment();
        }

        txtviewtitle = (TextView) findViewById(R.id.title);
        txtviewtitle.setText("VIP");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Buttons for selection of type of chart
        SegmentedButtonGroup sbg = (SegmentedButtonGroup) findViewById(R.id.segmBtnGrp);
        sbg.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
                if(position == 0){
//                    Toast.makeText(StatsActivity.this, "Day", Toast.LENGTH_SHORT).show();
                    loadFragment(new Chart(0, id_event, id_category));
                } else if(position == 1){
//                    Toast.makeText(StatsActivity.this, "Week", Toast.LENGTH_SHORT).show();
                    loadFragment(new Chart(1, id_event, id_category));
                } else if(position == 2){
//                    Toast.makeText(StatsActivity.this, "All", Toast.LENGTH_SHORT).show();
                    loadFragment(new Chart(2, id_event, id_category));
                }
            }
        });
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
        fragmentTransaction.add(R.id.frameLayout, new Chart(1, id_event, id_category));
        fragmentTransaction.commit();
    }
}