package com.example.gridviewdesign;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<AdapterItems>    listnewsData = new ArrayList<>();
    MyCustomAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gvID);

        listnewsData.add(new AdapterItems(R.drawable.pic1,"developer"," develop apps"));
        listnewsData.add(new AdapterItems(R.drawable.pic2,"tester"," develop apps"));
        listnewsData.add(new AdapterItems(R.drawable.pic3,"admin"," develop apps"));
        myadapter=new MyCustomAdapter(listnewsData);
        gridView.setAdapter(myadapter);
    }

    public void addnew(View view) {
        listnewsData.add(new AdapterItems(R.drawable.pic1,"developer"," develop apps"));
        myadapter.notifyDataSetChanged();
    }

    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket, null);

            final   AdapterItems s = listnewsDataAdpater.get(position);

            ImageView ivImage = myView.findViewById(R.id.ivImage);
            ivImage.setImageResource(s.ID);

            TextView txtJop = myView.findViewById(R.id.txtJop);
            txtJop.setText(s.JobTitle);
            txtJop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),s.JobTitle,Toast.LENGTH_LONG).show();
                }
            });

            TextView txtDesc = myView.findViewById(R.id.txtDesc);
            txtDesc.setText(s.Description);

            return myView;
        }

    }
}
