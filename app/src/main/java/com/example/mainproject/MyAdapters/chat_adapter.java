package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.MyModels.chat_model;
import com.example.mainproject.R;

import java.util.List;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.PaperCommons.USERMAIL;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.chat_view_holder> {

    List<chat_model> mylist;
    Context context;

    int Layout_left=0, Layout_Right=1;

    public chat_adapter() {
    }

    public chat_adapter(List<chat_model> mylist, Context context) {
        this.mylist = mylist;
        this.context = context;
    }

    @NonNull
    @Override
    public chat_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        Paper.init(context);

        if(viewType==Layout_left){
            View view= layoutInflater.inflate(R.layout.chat_item_left,parent,false);
            return  new chat_adapter.chat_view_holder(view);
        }
        else {
            View view= layoutInflater.inflate(R.layout.chat_item_right,parent,false);
            return  new chat_adapter.chat_view_holder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull chat_view_holder holder, int position) {

        chat_model chat_model= mylist.get(position);

        holder.chat_message.setText(chat_model.getComplaint());
        holder.chat_date.setText(chat_model.getDate());
        holder.chat_time.setText(chat_model.getTime());

    }



    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    public int getItemViewType(int position) {
        String mail = Paper.book().read(USERMAIL);
        if(mylist.get(position).getMail().equals(mail)){
            return Layout_left;
        }
        else{
            return Layout_Right;
        }
    }
    class chat_view_holder extends RecyclerView.ViewHolder{

        TextView chat_message,chat_time,chat_date;

        public chat_view_holder(@NonNull View itemView) {
            super(itemView);
            chat_message= itemView.findViewById(R.id.chat_message);
            chat_date= itemView.findViewById(R.id.chat_date);
            chat_time= itemView.findViewById(R.id.chat_time);

        }
    }
}
