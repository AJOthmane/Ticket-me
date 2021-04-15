package ma.ensias.ticket_me.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.fragments.LoginForm;
import ma.ensias.ticket_me.fragments.SignUpForm;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    EditText login;
    EditText password;
    TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccount = findViewById(R.id.createaccount);
        if (savedInstanceState == null) {
            initializeFragment(new LoginForm());
        }

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


            }
        });


    }
    private void loadFragment(Fragment fragment)
    {
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        fragmentTransaction.replace(R.id.fragmentLoginSignup,fragment);
// replace the FrameLayout with new Fragment
        //fragmentTransaction.add(R.id.fragmentLoginSignup,.class,null);
        fragmentTransaction.commit(); // save the changes

    }
    private void initializeFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentLoginSignup, new LoginForm());
        fragmentTransaction.commit();
    }


}