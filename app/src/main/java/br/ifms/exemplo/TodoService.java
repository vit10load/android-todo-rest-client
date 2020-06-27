package br.ifms.exemplo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TodoService {

    private final String BASE_URL = "https://test-rest-ifms.herokuapp.com/todo";

    public TodoService(){

    }

    public Todo buscarPorId(Long id) throws IOException {
        URL url = new URL(BASE_URL+"/"+id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if(connection.getResponseCode() == 200){
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String body = br.readLine();

            Gson gson = new Gson();
            Todo todo = gson.fromJson(body,Todo.class);

            connection.disconnect();
            return todo;
        }

        return null;
    }

    public List<Todo> listar() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        if (httpURLConnection.getResponseCode() == 200){
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String body = bufferedReader.readLine();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Todo>>(){}.getType();
            List<Todo> todos = gson.fromJson(body,type);
            return todos;
        }

        return null;
    }

    public boolean adicionar(Todo todo) throws IOException{

        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        Gson gson = new Gson();
        String body = gson.toJson(todo);
        OutputStream out = connection.getOutputStream();
        out.write(body.getBytes());
        out.flush();
        out.close();

        if (connection.getResponseCode() == 200) {
            connection.disconnect();
            return true;
        }else {
            connection.disconnect();
            return false;
        }
    }

    public void remover(Long id) {

    }
}
