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

import ma.ensias.ticket_me.activities.Home;

import ma.ensias.ticket_me.activities.ListOfEventsActivity;
import ma.ensias.ticket_me.activities.MainActivity;

import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.response.ResponseLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginForm extends Fragment {

    public static final String SESSION_SP_NAME = "LoginForm";

    EditText username;
    EditText password;
    Button loginButton;

    public LoginForm() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View loginView =  inflater.inflate(R.layout.login_fragment, container, false);
        username = loginView.findViewById(R.id.login_username);
        password = loginView.findViewById(R.id.login_password);
        loginButton = loginView.findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {

            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();
            if(usernameText.isEmpty() || passwordText.isEmpty())
            {
                Snackbar.make(getView(),"Veuillez remplir tous les champs",Snackbar.LENGTH_LONG).show();
            }
            else
            {

                ProgressDialog progress = new ProgressDialog(this.getContext());
                progress.setTitle("Connexion");
                progress.setMessage("Wait while Connecting...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();


                APIInterface apiInterface = APIClient.createService(APIInterface.class);
                HashMap<String,String> cred = new HashMap<>();
                cred.put(Home.USERNAME_FIELD,usernameText);
                cred.put(Home.PASSWORD_FIELD,passwordText);
                Call<ResponseLogin> call = apiInterface.VerifyLogin(cred);
                call.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                        if(response.isSuccessful())
                        {
                            if(response.body().isAuth())
                            {
                                SharedPreferences sp = getActivity().getSharedPreferences(SESSION_SP_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putInt("ID_SESSION",response.body().getId());
                                edit.commit();
                                Intent i = new Intent(getActivity(), ListOfEventsActivity.class);
                                startActivity(i);
                            }
                        }
                        else {
                            if(response.code() == 401)
                            {
                                progress.dismiss();
                                Snackbar.make(getView(), "Username ou mot de passe incorrect", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Log.e("Fail : login",t.getMessage());
                    }
                });

            }
                });


        return loginView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}