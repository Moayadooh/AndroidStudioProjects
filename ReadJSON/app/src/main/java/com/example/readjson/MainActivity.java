package com.example.readjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Display(View view) {
        String JsonFromURL = "{" +
                "  'info': {'name': 'Moayad'," +
                "  'age': 23," +
                "  'Languages': [ 'Java', 'PHP', 'SQL' ]}," +
                "  'Jobs': [" +
                "    {" +
                "      'Place': 'Barka'," +
                "      'Title': 'Software Engineer'" +
                "    }," +
                "    {" +
                "      'Place': 'Muscat'," +
                "      'Title': 'Software Developer'" +
                "    }" +
                "  ]" +
                "}";
        try {
            JSONObject jsonObject = new JSONObject(JsonFromURL);

            JSONObject info = jsonObject.getJSONObject("info");
            String name = info.getString("name");
            Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
            int age = info.getInt("age");
            Toast.makeText(this,String.valueOf(age),Toast.LENGTH_SHORT).show();
            JSONArray languages = info.getJSONArray("Languages");
            for (int i=0;i<languages.length();i++)
            {
                String language = languages.getString(i);
                Toast.makeText(this,language,Toast.LENGTH_SHORT).show();
            }

            JSONArray jsonArray = jsonObject.getJSONArray("Jobs");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jobs = jsonArray.getJSONObject(i);
                String place = jobs.getString("Place");
                Toast.makeText(this,place,Toast.LENGTH_SHORT).show();
                String title = jobs.getString("Title");
                Toast.makeText(this,title,Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}