package ma.ensias.ticket_me.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.fragments.LoginForm;
import ma.ensias.ticket_me.fragments.SignUpForm;

public class Home extends AppCompatActivity {
    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sp = getSharedPreferences("LoginForm", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        if(sp.getInt("ID_SESSION",0) >  0)
        {
            Intent intent = new Intent(this,ListOfEventsActivity.class);
            startActivity(intent);
        }

        createAccount = findViewById(R.id.createaccount);
        if (savedInstanceState == null) {
            initializeFragment();
        }

        createAccount.setOnClickListener(v -> {

            if(createAccount.getText().equals(getString(R.string.creer_nouveau_compte)))
            {
                loadFragment(new SignUpForm());
                createAccount.setText("Se connecter");
            }
            else
            {
                loadFragment(new LoginForm());
                createAccount.setText(getString(R.string.creer_nouveau_compte));
            }
        });
    }
    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        fragmentTransaction.replace(R.id.fragmentLoginSignup,fragment);

        fragmentTransaction.commit();
    }
    private void initializeFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentLoginSignup, new LoginForm());
        fragmentTransaction.commit();
    }
}