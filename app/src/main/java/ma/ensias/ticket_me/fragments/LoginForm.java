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
import ma.ensias.ticket_me.requests.APIClient;
import ma.ensias.ticket_me.requests.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                APIInterface apiInterface = APIClient.createService(APIInterface.class);
                Call<Boolean> call = apiInterface.VerifyLogin(loginText,passwordText);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body())
                                Toast.makeText(getActivity(),"Username et mot de passe correct",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getActivity(),"Username et mot de passe incorrect",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
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