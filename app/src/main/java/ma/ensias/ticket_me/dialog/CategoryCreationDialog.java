package ma.ensias.ticket_me.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.requests.RequestCategory;
import ma.ensias.ticket_me.response.ResponseCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreationDialog extends Dialog {

    public Activity activity;
    public int idEvent;
    public EditText nameET,priceET,placesET;
    public TextView message;
    public Button create;
    public String name;
    public double price;
    public int places;

    public CategoryCreationDialog(Activity activity,int idEvent) {

        super(activity);
        this.activity = activity;
        this.idEvent = idEvent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_category);
        nameET = findViewById(R.id.category_name);
        priceET = findViewById(R.id.category_price);
        placesET = findViewById(R.id.category_places);
        message = findViewById(R.id.category_message);
        create = findViewById(R.id.button_creation_category);



        create.setOnClickListener(v -> {
            name = nameET.getText().toString();
            try {
                price = Double.parseDouble(priceET.getText().toString());
                places = Integer.parseInt(placesET.getText().toString());
                if (name.isEmpty() )
                {
                    message.setTextColor(Color.RED);
                    message.setText("Veuillez remplir tous les champs"); //
                }
                else
                {
                    RequestCategory request = new RequestCategory(name,price,places,idEvent);
                    APIInterface apiInterface = APIClient.createService(APIInterface.class);
                    Call<ResponseCategory> call = apiInterface.createCategory(request);
                    call.enqueue(new Callback<ResponseCategory>() {
                        @Override
                        public void onResponse(Call<ResponseCategory> call, Response<ResponseCategory> response)
                        {
                            if(response.code() == 500)
                            {
                                message.setTextColor(Color.RED);
                                message.setText("Creation Failed ! pleaser Retry later");
                            }
                            else
                            {
                                message.setTextColor(Color.RED);
                                message.setText("Category has been created successfully ");
                                dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseCategory> call, Throwable t)
                        {
                            Log.e("Fail : login",t.getMessage());
                        }
                    });

                }
            }catch(Exception ex)
            {

                message.setTextColor(Color.RED);
                message.setText("Veuillez remplir tous les champs"); //

            }

        });


    }
}
