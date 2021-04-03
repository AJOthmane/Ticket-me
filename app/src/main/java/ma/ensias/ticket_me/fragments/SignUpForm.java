package ma.ensias.ticket_me.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ma.ensias.ticket_me.R;


public class SignUpForm extends Fragment {

    EditText login;
    EditText password;
    EditText passwordConfirmation;
    Button signUpButton;

    public SignUpForm() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View signUpView = inflater.inflate(R.layout.signup_fragment, container, false);

        signUpButton = signUpView.findViewById(R.id.signup_button);
        login =  signUpView.findViewById(R.id.signup_login);
        password =  signUpView.findViewById(R.id.signup_password);
        passwordConfirmation = signUpView.findViewById(R.id.signup_password_confirmation);

        signUpButton.setOnClickListener( v -> {
            String loginText = login.getText().toString();
            String passwordText = password.getText().toString();
            String passwordConfirmationText = passwordConfirmation.getText().toString();
            if(loginText.isEmpty() || passwordText.isEmpty() || passwordConfirmationText.isEmpty()
                                    || !passwordText.equals(passwordConfirmationText))
            {
                Toast.makeText(getActivity(),"Les donnes entrez sont errone",Toast.LENGTH_LONG).show();
            }
        });

        return signUpView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}