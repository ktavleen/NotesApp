package com.tavleen.mynotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText writeNote;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        writeNote=findViewById(R.id.write_note);
        add=findViewById(R.id.add);

       final NoteDao noteDao= NoteDB.getInstance(this).getNoteDao();
       final List<Note> notes = noteDao.fetchNotes();

       recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
       final NoteRecyclerViewAdapter adapter=new NoteRecyclerViewAdapter(notes,MainActivity.this);
       recyclerView.setAdapter((adapter));
       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String noteContent= writeNote.getText().toString();
               if(noteContent.isEmpty()){
                   Toast.makeText(MainActivity.this,"Invalid data",Toast.LENGTH_SHORT).show();
               }else{
                   Note note =new Note(0,noteContent);
                   noteDao.createNote(note);
                   List<Note>newNotes=noteDao.fetchNotes();
                   adapter.notes=noteDao.fetchNotes();
                   adapter.notifyDataSetChanged();
                   writeNote.setText("");
                   writeNote.setHint("Add note from here");
               }
           }
       });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                Note note=adapter.notes.get(position);
                adapter.notes.remove(position);
                noteDao.deleteNote(note);
                adapter.notifyDataSetChanged();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
