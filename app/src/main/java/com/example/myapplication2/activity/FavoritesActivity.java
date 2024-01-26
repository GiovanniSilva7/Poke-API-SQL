package com.example.myapplication2.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.modal.Pokemon;
import com.example.myapplication2.adapter.PokemonAdapter;
import com.example.myapplication2.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private List<Pokemon> favoritePokemonList;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));

        favoritePokemonList = getFavoritePokemons();
        adapter = new PokemonAdapter(this, favoritePokemonList);
        recyclerViewFavorites.setAdapter(adapter);
    }

    private List<Pokemon> getFavoritePokemons() {
        List<Pokemon> favorites = new ArrayList<>();

        // Recupere todos os Pokémon favoritos do banco de dados
        Cursor cursor = MainActivity.database.query("favorites", new String[]{"_id", "name", "imageUrl", "weight", "height", "types"},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
            double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
            double height = cursor.getDouble(cursor.getColumnIndex("height"));
            String typesString = cursor.getString(cursor.getColumnIndex("types"));

            try {
                JSONArray typesArray = new JSONArray(typesString);
                List<String> typesList = new ArrayList<>();
                for (int i = 0; i < typesArray.length(); i++) {
                    typesList.add(typesArray.getString(i));
                }

                Pokemon pokemon = new Pokemon(name, imageUrl, weight, height, typesList);
                favorites.add(pokemon);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao processar os dados do Pokémon favorito.", Toast.LENGTH_SHORT).show();
            }
        }

        cursor.close();
        return favorites;
    }
}
