package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ma.ensias.ticket_me.R;

public class TicketCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check);
        TextView ticketNumber = (TextView) findViewById(R.id.textView4);
        ticketNumber.setText(getIntent().getStringExtra("code"));
    }
}