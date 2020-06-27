package br.ifms.exemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class TodoForm extends AppCompatActivity {

    private Todo todo;
    private TodoService todoService;
    private EditText editTextDescricao;
    private EditText editTextPrioridade;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_form);
        this.editTextDescricao = (EditText) findViewById(R.id.editText_descricao);
        this.editTextPrioridade = (EditText) findViewById(R.id.editText_prioridade);
        todoService = new TodoService();
        intent = new Intent(this,MainActivity.class);
    }

    public void salvarTodo(View view){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    todo = new Todo();
                    todo.setDescricao(editTextDescricao.getText().toString());
                    todo.setPrioridade(editTextPrioridade.getText().toString());
                    if (todoService.adicionar(todo)){
                        Toast.makeText(getApplicationContext(),"Uma nova todo adicionada!", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }

                }catch (IOException e){
                    Toast.makeText(getApplicationContext(),"Erro ao inserir uma nova todo",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
