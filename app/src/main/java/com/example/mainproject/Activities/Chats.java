package com.example.mainproject.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.mainproject.MyAdapters.chat_adapter;
import com.example.mainproject.MyModels.chat_model;
import com.example.mainproject.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

import static com.example.mainproject.Commons.Links.CHAT;
import static com.example.mainproject.Commons.Links.CHAT_POST;
import static com.example.mainproject.Commons.PaperCommons.USERMAIL;

public class Chats extends AppCompatActivity {

    TextView received;
    RecyclerView recyclerView;
    TextInputEditText textInputEditText;
    List<chat_model> mylist;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
       final String Topic= getIntent().getStringExtra("Topic");
        received= findViewById(R.id.received);
        received.setText(Topic);
        mylist= new ArrayList<>();
        imageView= findViewById(R.id.btn_send);
        textInputEditText= findViewById(R.id.user_text);
        recyclerView= findViewById(R.id.chat_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        Date date= new Date();
        SimpleDateFormat simpleDateFormat1= new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
        final String mytime= simpleDateFormat1.format(date);
        Paper.init(Chats.this);

        loadmessage(Topic);




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String reply=  textInputEditText.getText().toString();

                if (reply.isEmpty()){
                    textInputEditText.setError("Field cant be empty");
                }
                else {
//post reply for a specific complaint
                    final String email = Paper.book().read(USERMAIL);
                    StringRequest poststring= new StringRequest(Request.Method.POST, CHAT_POST, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(Chats.this, ""+response, Toast.LENGTH_SHORT).show();
                            textInputEditText.setText("");
                            loadmessage(Topic);


                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }



                    ){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> mymap= new HashMap<>();

                            mymap.put("complaint",Topic);
                            mymap.put("message",reply);
                            mymap.put("time",mytime);
                            mymap.put("mail",email);
                            return mymap;
                        }
                    }

                            ;
                    RequestQueue rq= Volley.newRequestQueue(Chats.this);
                    rq.add(poststring);

                }
            }
        });

    }
    public  void loadmessage(final String Topic){
        //fetch replies for a specific complaint

        StringRequest stringRequest= new StringRequest(Request.Method.POST, CHAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mylist.clear();

                try {
                    JSONArray jsonArray= new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject= jsonArray.getJSONObject(i);

                        String chat_message= jsonObject.getString("complaint");
                        String chat_date= jsonObject.getString("date");
                        String chat_time= jsonObject.getString("time");
                        String chat_email= jsonObject.getString("mail");

                        chat_model chat_model= new chat_model(chat_message,chat_date,chat_time,chat_email);//order according to model
                        mylist.add(chat_model);
                    }

                    chat_adapter chat_adapter= new chat_adapter(mylist,Chats.this);
                    recyclerView.setAdapter(chat_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Chats.this));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> mymap= new HashMap<>();
                mymap.put("complaint",Topic);

                return mymap;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(Chats.this);
        requestQueue.add(stringRequest);

    }
}
