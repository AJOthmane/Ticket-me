package ma.ensias.ticket_me.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.CreationEvent;
import ma.ensias.ticket_me.activities.ListOfEventsActivity;
import ma.ensias.ticket_me.response.ResponseSignUp;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpForm extends Fragment {

    private static final int PASSWORD_MIN_LENGTH = 6;
    public static final String SESSION_SP_NAME = "LoginForm";


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

                Snackbar.make(getView(),"Veuillez remplir tous les champs",Snackbar.LENGTH_LONG).show();

            }
            else if(!passwordText.equals(passwordConfirmationText))
            {

                Snackbar.make(getView(),"Le mote de passe et confirmation ne sont pas identiques",Snackbar.LENGTH_LONG).show();
            }
            else if (passwordText.length() <= PASSWORD_MIN_LENGTH)
            {
                Snackbar.make(getView(),"Mot de passe doit contenir au minimum "+PASSWORD_MIN_LENGTH+" caracteres",Snackbar.LENGTH_LONG).show();
            }
            else
            {
                ProgressDialog progress = new ProgressDialog(this.getContext());
                progress.setTitle("Connexion");
                progress.setMessage("Wait while Signing...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                APIInterface apiInterface = APIClient.createService(APIInterface.class);
                HashMap<String,String> infos = new HashMap<>();
                infos.put("username",usernameText);
                infos.put("password",passwordText);
                Call<ResponseSignUp> call = apiInterface.signUp(infos);
                call.enqueue(new Callback<ResponseSignUp>() {
                    @Override
                    public void onResponse(Call<ResponseSignUp> call, Response<ResponseSignUp> response) {
                        if(response.code() == 200)
                        {
                            if(response.body().getError() == null)
                            {
                                Snackbar.make(getView(),"Utilisateur est cree",Snackbar.LENGTH_LONG).show();
                                SharedPreferences sp = getActivity().getSharedPreferences(SESSION_SP_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                //edit.putInt("ID_SESSION",response.body().getId()); change response to get id
                                edit.commit();
                                Intent i = new Intent(getActivity(), ListOfEventsActivity.class);
                                startActivity(i);
                            }
                            else
                                Snackbar.make(getView(),response.body().getError(),Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            progress.dismiss();
                            Log.e("signup message", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseSignUp> call, Throwable t)
                    {
                        progress.dismiss();
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