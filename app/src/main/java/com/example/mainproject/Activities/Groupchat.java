package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyAdapters.Groupchat_adapter;
import com.example.mainproject.MyModels.GroupChat_Conversation_model;
import com.example.mainproject.MyModels.Groupchat_model;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import static com.example.mainproject.Commons.Links.GROUP_GET;
import static com.example.mainproject.Commons.Links.GROUP_POST;
import static com.example.mainproject.Commons.PaperCommons.USERMAIL;
import static com.example.mainproject.Commons.PaperCommons.USERNAME;

public class Groupchat extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText input;
    ImageView send;
    List<GroupChat_Conversation_model> list;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference,see;
    FirebaseAuth firebaseAuth;
    LinearLayout linearLayout;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("GroupChat");
         progressDialog=new ProgressDialog(Groupchat.this);
        progressDialog.setMessage("Loading Conversations");
        progressDialog.show();
        linearLayout=findViewById(R.id.group_linear);
        databaseReference= FirebaseDatabase.getInstance().getReference("GroupChats");
        see=FirebaseDatabase.getInstance().getReference("Viewers");

        see.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(firebaseAuth.getCurrentUser().getUid())){

                    count=(int) dataSnapshot.getChildrenCount();

                }
                else {
                    see.child(firebaseAuth.getCurrentUser().getUid()).setValue(true);
                    count=(int) dataSnapshot.getChildrenCount();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView= findViewById(R.id.group_recycler);
        input= findViewById(R.id.group_input);
        send= findViewById(R.id.group_send);
        list= new ArrayList<>();

//        reload();



        Date date= new Date();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
        final String time=simpleDateFormat.format(date);
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        final String mydate=simpleDateFormat1.format(date);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String message= input.getText().toString();
                final String username="sam";
                final String email=firebaseAuth.getCurrentUser().getEmail();

                if (message.isEmpty()){
                    input.setError("Field can't be empty");
                }
                else{

                    GroupChat_Conversation_model groupChat_conversation_model=new GroupChat_Conversation_model(message,username,email,mydate,time);

                    databaseReference.push().setValue(groupChat_conversation_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        input.setText("");
                                        Toast.makeText(Groupchat.this, "Message send", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(Groupchat.this, "FAILED! Please try again later", Toast.LENGTH_SHORT).show();
                                    }
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
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    GroupChat_Conversation_model chat=data.getValue(GroupChat_Conversation_model.class);
                    list.add(chat);

                }

                if (list.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    linearLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    Groupchat_adapter groupchat_adapter = new Groupchat_adapter(list, Groupchat.this);
                    recyclerView.setAdapter(groupchat_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Groupchat.this));
                    recyclerView.scrollToPosition(groupchat_adapter.getItemCount() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(Groupchat.this, "Error Please Try again later", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
