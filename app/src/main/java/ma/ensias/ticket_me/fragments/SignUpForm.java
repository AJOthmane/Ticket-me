package ma.ensias.ticket_me.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.model.User;
import ma.ensias.ticket_me.requests.APIClient;
import ma.ensias.ticket_me.requests.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
            else
            {
                APIInterface apiInterface = APIClient.createService(APIInterface.class);
                Call<User> call = apiInterface.signUp(loginText,passwordText);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body() != null)
                            {
                                Toast.makeText(getActivity(),"User created",Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(getActivity(),"User creation failed",Toast.LENGTH_LONG).show();
                        }
                        else
                            Log.e("signup message", response.message());

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t)
                    {
                        Log.e("signup failure", t.getMessage());
                    }
                });

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