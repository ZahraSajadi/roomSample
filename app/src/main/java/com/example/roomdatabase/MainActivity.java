package com.example.roomdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DeleteEditListener {

    UserRoomDatabase db;
    ListAdapter adapter;

    EditText userName;
    EditText passWord;
    RecyclerView recyclerView;

    User userToEdit;
    int userPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.user_view);
        passWord = findViewById(R.id.pass_view);
        recyclerView = findViewById(R.id.list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = UserRoomDatabase.getDatabase(MainActivity.this);

    }

    public void insert(View view) {
        String user_name = userName.getText().toString();
        String pass_word = passWord.getText().toString();

        if(!(user_name.isEmpty() && pass_word.isEmpty())) {
            User newUser = new User(user_name, pass_word);

            db.userDao().insert(newUser);

            clearEditText();
            Toast.makeText(this, "Data insert Successfully!", Toast.LENGTH_SHORT).show();
            fill();
        }
    }

    public void update(View view) {
        String newUserName = userName.getText().toString();
        String newPassWord = passWord.getText().toString();

        if(!(newPassWord.isEmpty()&& newUserName.isEmpty())){
            if(!newPassWord.isEmpty())
                userToEdit.setPassword(newPassWord);
            if(!newUserName.isEmpty())
                userToEdit.setUserName(newUserName);

            db.userDao().update(userToEdit);
            clearEditText();
            adapter.notifyItemChanged(userPosition);

        }
    }

    public void displayAll(View view) {
        fill();
    }


    public void clearEditText(){
        userName.getText().clear();
        passWord.getText().clear();
        userName.setHint("username");
        passWord.setHint("password");
    }
    public void fill() {
        adapter = new ListAdapter(this,db.userDao().selectAll(),this);
        recyclerView.setAdapter(adapter);
    }

    //recyclerView buttons onClickListener
    @Override
    public void deleteItem(User user, int position) {
        db.userDao().delete(user);
        adapter.notifyItemRemoved(position);
    }
    @Override
    public void editItem(final User user, int position) {
        userToEdit = user;
        userPosition = position;
        userName.setHint(userToEdit.getUserName());
        passWord.setHint(userToEdit.getPassword());
    }

}
