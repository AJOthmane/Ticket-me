package ma.ensias.ticket_me;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TicketCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check);
        TextView ticketNumber = (TextView) findViewById(R.id.textView4);
        ticketNumber.setText(getIntent().getStringExtra("code"));
    }
}