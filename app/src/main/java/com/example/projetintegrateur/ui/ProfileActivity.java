package com.example.projetintegrateur.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.example.projetintegrateur.R;
import com.example.projetintegrateur.model.User;
import com.example.projetintegrateur.util.UserClient;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {


    //***********\\
    //  OnCREATE  \\
    //******************************************************************************************************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //HIDE DEFAULT ACTION BAR
        Objects.requireNonNull(getSupportActionBar()).hide();

        User user = ((UserClient) getApplicationContext()).getUser();
        ImageView profilePicture = findViewById(R.id.imageView_profile_picture);
        String photoURL = user.getPhotoUrl();

        //SET/DISPLAY PROFILE INFORMATION
        //*****************************************************************************************************************************
        Picasso.get().load(photoURL).into(profilePicture);

        TextView profileName = findViewById(R.id.textview_fullname);
        String name = user.getName();
        profileName.setText(name);

        TextView profileEmail = findViewById(R.id.textview_email);
        String email = user.getEmail();
        profileEmail.setText(email);


        //BUTTON HISTORY OnClickListener
        //*****************************************************************************************************************************
        TextView btn_history = findViewById(R.id.btn_history);

        btn_history.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, HistoryListActivity.class);
            startActivity(intent);
        });

        TextView btn_logOut = findViewById(R.id.textview_log_out);

        btn_logOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this, MapsActivity.class));
        });

        TextView btn_infos = findViewById(R.id.textview_info);

        btn_infos.setOnClickListener(view -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileActivity.this);
            builder1.setMessage("Midway 1.0");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Ok",
                    (dialog, id) ->
                            dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();
        });

        TextView btn_parametre = findViewById(R.id.textview_parametres);

        btn_parametre.setOnClickListener(view -> {
            String[] colors = {"Muted Blue", "Midnight", "Unsaturated Browns", "Ultra Light","Blue Essence"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choisissez un thème");
            builder.setItems(colors, (dialog, which) -> {
                // the user clicked on colors[which]
                MapsActivity.setMapStyle(colors[which]);
            });
            builder.show();
        });


    }


}