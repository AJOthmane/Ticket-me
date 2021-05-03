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

import com.google.android.material.snackbar.Snackbar;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.model.User;
import ma.ensias.ticket_me.requests.APIClient;
import ma.ensias.ticket_me.requests.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpForm extends Fragment {

    private static int PASSWORD_MIN_LENGTH = 6;

    EditText username;
    EditText password;
    EditText passwordConfirmation;
    Button signUpButton;

    public SignUpForm() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View signUpView = inflater.inflate(R.layout.signup_fragment, container, false);

        signUpButton = signUpView.findViewById(R.id.signup_button);
        username =  signUpView.findViewById(R.id.signup_username);
        password =  signUpView.findViewById(R.id.signup_password);
        passwordConfirmation = signUpView.findViewById(R.id.signup_password_confirmation);

        signUpButton.setOnClickListener( v -> {
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();
            String passwordConfirmationText = passwordConfirmation.getText().toString();

            if(usernameText.isEmpty() || passwordText.isEmpty() || passwordConfirmationText.isEmpty() )
            {
               //Toast.makeText(getContext(),"Veuillez remplir tous les champs",Toast.LENGTH_LONG).show();
                Snackbar.make(getView(),"Veuillez remplir tous les champs",Snackbar.LENGTH_LONG).show();

            }
            else if(!passwordText.equals(passwordConfirmationText))
            {
               //Toast.makeText(getActivity(),"Le mote de passe et confirmation ne sont pas identiques",Toast.LENGTH_LONG).show();
                Snackbar.make(getView(),"Le mote de passe et confirmation ne sont pas identiques",Snackbar.LENGTH_LONG).show();
            }
            else if (passwordText.length() <= PASSWORD_MIN_LENGTH)
            {
                //Toast.makeText(getActivity(),"Mot de passe doit contenir au minimum 6 caracteres",Toast.LENGTH_LONG).show();
                Snackbar.make(getView(),"Mot de passe doit contenir au minimum 6 caracteres",Snackbar.LENGTH_LONG).show();
            }
            else
            {
                APIInterface apiInterface = APIClient.createService(APIInterface.class);
                Call<User> call = apiInterface.signUp(usernameText,passwordText);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body() != null)
                            {

                                Snackbar.make(getView(),"Utilisateur est cree",Snackbar.LENGTH_LONG).show();
                            }
                            else
                                Snackbar.make(getView(),"Opps un erreur est servenue",Snackbar.LENGTH_LONG).show();
                                //Toast.makeText(getActivity(),"Opps un erreur est servenue",Toast.LENGTH_LONG).show();

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