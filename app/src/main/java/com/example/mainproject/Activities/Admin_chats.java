package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyAdapters.AdminConversationAdapter;
import com.example.mainproject.MyAdapters.Chats_adapter;
import com.example.mainproject.MyModels.FarmerConversationmodel;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.Links.ADMIN_POST;
import static com.example.mainproject.Commons.Links.CHAT;
import static com.example.mainproject.Commons.PaperCommons.USERMAIL;
import static com.example.mainproject.Commons.PaperCommons.USERNAME;

public class Admin_chats extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText input;
    ImageView send;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String topic;
    List<FarmerConversationmodel>list;
    TextView complaint;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chats);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Complaints");

        list=new ArrayList<>();
        complaint=findViewById(R.id.received_text);
        recyclerView= findViewById(R.id.admin_recycler);
        linearLayout=findViewById(R.id.linear_admin_conversations);
        input= findViewById(R.id.admin_input);
        send= findViewById(R.id.admin_send);
        databaseReference= FirebaseDatabase.getInstance().getReference("Replies");
        topic=getIntent().getStringExtra("Topic");
        complaint.setText(topic);


        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        final String time=simpleDateFormat.format(date);
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        final String mydate=simpleDateFormat1.format(date);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text=input.getText().toString();
                if (TextUtils.isEmpty(text.trim())){
                    input.setError("Field can't be empty");
                }
                else{
                    final String username= "ADMIN";
                    final String mail=Paper.book().read(USERMAIL);

                    FarmerConversationmodel farmerConversationmodel=new FarmerConversationmodel(topic,text,"ADMIN",time,"admin",mydate);

                    databaseReference.push().setValue(farmerConversationmodel);
                    input.setText("");

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
                for (DataSnapshot data:dataSnapshot.getChildren()){

                    FarmerConversationmodel farm=data.getValue(FarmerConversationmodel.class);

                    if (farm.getComplaint().equals(topic)){
                        list.add(farm);
                    }
                }
                if (list.isEmpty()){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    AdminConversationAdapter chats_adapter = new AdminConversationAdapter(list, Admin_chats.this);
                    recyclerView.setAdapter(chats_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Admin_chats.this));
                    recyclerView.scrollToPosition(chats_adapter.getItemCount() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
