package com.avi.goldsparrow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avi.goldsparrow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name,address,city,postalCode,phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;
    ImageView back;

    FirebaseFirestore firestore;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        if (getSupportActionBar() != null) {
            toolbar=findViewById(R.id.add_address_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        back=findViewById(R.id.back_arrow);
        name=findViewById(R.id.ad_name);
        address=findViewById(R.id.ad_address);
        city=findViewById(R.id.ad_city);
        phoneNumber=findViewById(R.id.ad_phone);
        postalCode=findViewById(R.id.ad_code);
        addAddressBtn=findViewById(R.id.ad_add_address);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddAddressActivity.this,AddressActivity.class);
                startActivity(intent);
            }
        });


        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=name.getText().toString();
                String userCity=city.getText().toString();
                String userAddress=address.getText().toString();
                String userCode=postalCode.getText().toString();
                String userNumber=phoneNumber.getText().toString();

                String final_address="";

                if (!userName.isEmpty()){
                    final_address+=userName;
                }

                if (!userCity.isEmpty()){
                    final_address+=userCity;
                }

                if (!userAddress.isEmpty()){
                    final_address+=userAddress;
                }
                if (!userCode.isEmpty()){
                    final_address+=userCode;
                }
                if (!userNumber.isEmpty()){
                    final_address+=userNumber;
                }
                if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()){
                    Map<String,String> map=new HashMap<>();
                    map.put("userAddress",final_address);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddAddressActivity.this,DetailedActivity.class));
                                        finish();
                                    }

                                }
                            });
                }else {
                    Toast.makeText(AddAddressActivity.this, "Kindly Fill All Fields", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}