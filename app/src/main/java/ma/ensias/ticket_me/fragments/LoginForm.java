package ma.ensias.ticket_me.fragments;

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
import ma.ensias.ticket_me.requests.APIClient;
import ma.ensias.ticket_me.requests.APIInterface;
import ma.ensias.ticket_me.forms.ReponseLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginForm extends Fragment {

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
                APIInterface apiInterface = APIClient.createService(APIInterface.class);
                HashMap<String,String> cred = new HashMap<>();
                cred.put("username",usernameText);
                cred.put("password",passwordText);
                Call<ReponseLogin> call = apiInterface.VerifyLogin(cred);
                call.enqueue(new Callback<ReponseLogin>() {
                    @Override
                    public void onResponse(Call<ReponseLogin> call, Response<ReponseLogin> response) {
                        Log.i("onresponse","hana");
                        Log.i("reponse",""+response.isSuccessful());
                        if(response.isSuccessful())
                        {
                            Log.i("issucc","hana");
                            if(response.body().isAuth())
                                Snackbar.make(getView(),"Connecte",Snackbar.LENGTH_LONG).show();
                            else
                                Snackbar.make(getView(),"Username et mot de passe incorrect",Snackbar.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ReponseLogin> call, Throwable t) {
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