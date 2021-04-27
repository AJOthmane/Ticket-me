package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ma.ensias.ticket_me.CategoryCreationDialog;
import ma.ensias.ticket_me.R;

public class CategoryList extends AppCompatActivity {

    protected Button add;
    protected int idEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Intent i = getIntent();
        idEvent = i.getIntExtra("id_event",-1);

        add = findViewById(R.id.add_category);
        add.setOnClickListener(v -> {
            CategoryCreationDialog ccd = new CategoryCreationDialog(this,idEvent);
            ccd.show();
        });

    }
}