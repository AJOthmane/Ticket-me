package ma.ensias.ticket_me.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.fragments.LoginForm;
import ma.ensias.ticket_me.fragments.SignUpForm;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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