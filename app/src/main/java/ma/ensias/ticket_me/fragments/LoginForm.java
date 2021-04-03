package ma.ensias.ticket_me.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ma.ensias.ticket_me.R;


public class LoginForm extends Fragment {

    EditText login;
    EditText password;
    Button loginButton;

    public LoginForm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View loginView =  inflater.inflate(R.layout.login_fragment, container, false);
        login = loginView.findViewById(R.id.login_login);
        password = loginView.findViewById(R.id.login_password);
        loginButton = loginView.findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            String loginText = login.getText().toString();
            String passwordText = password.getText().toString();
            if(loginText.isEmpty() || passwordText.isEmpty())
            {
                Toast.makeText(getActivity(),"Veuillez remplir tous les champs",Toast.LENGTH_LONG ).show();
            }
            else
            {

            }
                });


        return loginView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}