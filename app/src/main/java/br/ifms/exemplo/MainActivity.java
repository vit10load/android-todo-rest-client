package br.ifms.exemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TodoAdapter adapter;
    private List<Todo> todos;
    private TodoService todoService;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoService = new TodoService();
        this.button = findViewById(R.id.acao_adicionar);
        inicializarListView();
    }

    public void inicializarListView(){
        listView = findViewById(R.id.main_activity_listview);
        todos = new ArrayList<Todo>();
        adapter = new TodoAdapter(this, todos);
        listView.setAdapter(adapter);
    }

    public void adicionarTodo(View view){
        Intent intent = new Intent(this, TodoForm.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncTask.execute(new Runnable(){
            @Override
            public void run() {
                try {
                    List<Todo> todoObjects = todoService.listar();
                    for (int x = 0; x < todoObjects.size(); x++){
                        todos.add(todoObjects.get(x));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            todos = new ArrayList<>();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
