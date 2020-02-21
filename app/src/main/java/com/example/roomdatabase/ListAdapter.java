package com.example.roomdatabase;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context context;
    List<User> users;
    DeleteEditListener callback;
    public ListAdapter(Context context, List<User> users, DeleteEditListener callback){
        this.context = context;
        this.users = users;
        this.callback = callback;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final User user = users.get(position);
        holder.id.setText(Integer.toString(user.getId()));
        holder.userName.setText(user.getUserName());
        holder.passWord.setText(user.getPassword());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users.remove(position);
                callback.deleteItem(user,position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.editItem(user,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView userName;
        TextView passWord;
        ImageButton delete;
        ImageButton edit;
        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id._id);
            userName = itemView.findViewById(R.id._username);
            passWord = itemView.findViewById(R.id._password);
            delete = itemView.findViewById(R.id.delete_btn);
            edit = itemView.findViewById(R.id.update_btn);
        }
    }
}
