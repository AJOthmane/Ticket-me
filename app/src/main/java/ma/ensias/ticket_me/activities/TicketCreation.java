package ma.ensias.ticket_me.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.response.ResponseCategories;
import ma.ensias.ticket_me.response.ResponseTicket;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketCreation extends AppCompatActivity {
    public static final int PERMISSION_WRITE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_creation);

        // integration prep work
        int event = 1;
        int id = 1;




        EditText cin = (EditText) findViewById(R.id.ccin);
        EditText nom = (EditText) findViewById(R.id.cnom);
        EditText prenom = (EditText) findViewById(R.id.cprenom);
        EditText email = (EditText) findViewById(R.id.cemail);
        Spinner categories = (Spinner) findViewById(R.id.spinner);
        Button creer = (Button) findViewById(R.id.creerTicket);
        ProgressBar pgsBar = (ProgressBar)findViewById(R.id.progBar);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        List<String> categoriesId = new ArrayList<String>();
        List<String> categoriesSpinner = new ArrayList<String>();
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<ResponseCategories> call = apiInterface.getCategories2(event);
        checkPermission();
        call.enqueue(new Callback<ResponseCategories>() {
            @Override
            public void onResponse(Call<ResponseCategories> call, Response<ResponseCategories> response) {
                if(response.body().getValid())
                {
                    List<HashMap<String,String>> bresponse = response.body().getCategories();
                    for (HashMap<String,String> cat:bresponse
                         ) {
                        categoriesId.add(cat.get("id_categorie"));
                        categoriesSpinner.add(cat.get("nom_categorie")+" - "+cat.get("prix_categorie")+" DH");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, categoriesSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categories.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(TicketCreation.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
                pgsBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseCategories> call, Throwable t) {
                Log.e("Fail : Categories check",t.getMessage());
                Toast.makeText(TicketCreation.this, "Server is offline", Toast.LENGTH_SHORT).show();
                pgsBar.setVisibility(View.GONE);
            }
        });
        creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage( getString(R.string.ticket_creation_confirmation_content) +" "+ categories.getSelectedItem().toString());
                builder.setTitle(R.string.ticket_creation_confirmation_title);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // to replace later
                        //finish();
                        pgsBar.setVisibility(View.VISIBLE);
                        HashMap<String,String> reqBody = new HashMap<>();
                        reqBody.put("cin",cin.getText().toString());
                        reqBody.put("nom",nom.getText().toString());
                        reqBody.put("prenom",prenom.getText().toString());
                        reqBody.put("email",email.getText().toString());
                        reqBody.put("event",Integer.toString(event));
                        reqBody.put("category",categoriesId.get(categories.getSelectedItemPosition()));
                        reqBody.put("user_creation",Integer.toString(id));
                        Call<ResponseBody> tcall = apiInterface.createTicket(reqBody);
                        tcall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Bitmap ticket = BitmapFactory.decodeStream(response.body().byteStream());
                                Uri uri = null;
                                try {
                                    uri = saveBitmap(getApplicationContext(),ticket, Bitmap.CompressFormat.JPEG,"image/png","ticket");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                pgsBar.setVisibility(View.GONE);
                                nom.setText("");
                                prenom.setText("");
                                email.setText("");
                                cin.setText("");
                                Intent share = new Intent(Intent.ACTION_SEND);
                                share.setType("image/*");
                                share.putExtra(Intent.EXTRA_STREAM, uri);
                                startActivity(Intent.createChooser(share, "Partager le ticket"));
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(TicketCreation.this, "Server Image Error", Toast.LENGTH_SHORT).show();
                                pgsBar.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
    //runtime storage permission
    public boolean checkPermission()
    {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }

    @NonNull
    public Uri saveBitmap(@NonNull final Context context, @NonNull final Bitmap bitmap,
                          @NonNull final Bitmap.CompressFormat format,
                          @NonNull final String mimeType,
                          @NonNull final String displayName) throws IOException {

        final ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);

        final ContentResolver resolver = context.getContentResolver();
        Uri uri = null;

        try {
            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            uri = resolver.insert(contentUri, values);

            if (uri == null)
                throw new IOException("Failed to create new MediaStore record.");

            try (final OutputStream stream = resolver.openOutputStream(uri)) {
                if (stream == null)
                    throw new IOException("Failed to open output stream.");

                if (!bitmap.compress(format, 95, stream))
                    throw new IOException("Failed to save bitmap.");
            }

            return uri;
        }
        catch (IOException e) {

            if (uri != null) {
                // Don't leave an orphan entry in the MediaStore
                resolver.delete(uri, null, null);
            }

            throw e;
        }
    }
}