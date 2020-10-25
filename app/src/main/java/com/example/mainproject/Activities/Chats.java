package com.example.mainproject.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyAdapters.Chats_adapter;
import com.example.mainproject.MyModels.Chats_model;
import com.example.mainproject.MyModels.FarmerConversationmodel;
import com.example.mainproject.MyModels.FarmerRegistermodel;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.paperdb.Paper;


public class Chats extends AppCompatActivity {

    TextView received;
    RecyclerView recyclerView;
    TextInputEditText textInputEditText;
    List<FarmerConversationmodel> mylist;
    ImageView imageView;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseAuth firebaseAuth;
    String Topic;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Replies");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Conversations");
        progressDialog.show();

       Topic= getIntent().getStringExtra("Topic");
        received= findViewById(R.id.received_text);
        received.setText(Topic);
        mylist= new ArrayList<>();
        imageView= findViewById(R.id.btn_send);
        textInputEditText= findViewById(R.id.user_text);
        recyclerView= findViewById(R.id.chat_recycler);
        linearLayout=findViewById(R.id.linear_conversations);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        Date date= new Date();
        SimpleDateFormat simpleDateFormat1= new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
        final String mytime= simpleDateFormat1.format(date);
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        final String mydate= simpleDateFormat.format(date);
        Paper.init(Chats.this);
        databaseReference= FirebaseDatabase.getInstance().getReference("Replies");
        databaseReference1=FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth=FirebaseAuth.getInstance();



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String reply=  textInputEditText.getText().toString();
                String mail=firebaseAuth.getCurrentUser().getEmail();
                String username="sam";

                if (reply.isEmpty()){
                    textInputEditText.setError("Field cant be empty");
                }
                else {

                    FarmerConversationmodel farmerConversationmodel=new FarmerConversationmodel(Topic,reply,username,mytime,mail,mydate);
                    databaseReference.push().setValue(farmerConversationmodel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    textInputEditText.setText("");
                                    Toast.makeText(Chats.this, "reply send", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                mylist.clear();

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    FarmerConversationmodel farm=data.getValue(FarmerConversationmodel.class);
                    if (farm.getComplaint().equals(Topic)) {
                        mylist.add(farm);
                    }
                }
                if (mylist.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);

                }
                else {
                    linearLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    Chats_adapter chats_adapter=new Chats_adapter(mylist,Chats.this);
                    recyclerView.setAdapter(chats_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Chats.this));
                    recyclerView.scrollToPosition(chats_adapter.getItemCount()-1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
