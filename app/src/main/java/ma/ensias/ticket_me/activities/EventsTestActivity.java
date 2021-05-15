package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ma.ensias.ticket_me.R;

public class EventsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_test);

        Button bt = (Button) findViewById(R.id.button3);

        bt.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatsActivity.class);
            intent.putExtra("title", "Pauvriya");
            this.startActivity(intent);
        });
    }
}