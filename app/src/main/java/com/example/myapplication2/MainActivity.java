package com.example.myapplication2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.activity.FavoritesActivity;
import com.example.myapplication2.dba.PokemonDatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPokemonName;

    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPokemonName = findViewById(R.id.editTextPokemonName);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        PokemonDatabaseHelper dbHelper = new PokemonDatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pokemonName = editTextPokemonName.getText().toString();
                new PokemonSearchTask().execute(pokemonName);
            }
        });
        Button buttonFavorites = findViewById(R.id.buttonFavorites);
        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFavoritePokemons();
            }
        });
    }

    private void showFavoritePokemons() {
        Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
        startActivity(intent);
    }

    private class PokemonSearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String pokemonName = params[0];
            String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemonName;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : "";
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    // Obtenha as informações do Pokémon do JSON
                    String pokemonName = jsonObject.getString("name");
                    String imageUrl = jsonObject.getJSONObject("sprites").getString("front_default");
                    double weight = jsonObject.getDouble("weight");
                    double height = jsonObject.getDouble("height");
                    JSONArray typesArray = jsonObject.getJSONArray("types");

                    // Exiba as informações (você precisa adicionar a lógica para exibir na interface do usuário)
                    displayPokemonInfo(pokemonName, imageUrl, weight, height, typesArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Erro ao processar os dados do Pokémon.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Erro ao se conectar à API.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Implemente o método displayPokemonInfo para exibir as informações na interface do usuário
    private void displayPokemonInfo(String name, String imageUrl, double weight, double height, JSONArray typesArray) {
        // Implemente a lógica para exibir as informações na interface do usuário
    }
}
