package pl.jaceksen.sqllite1;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBManager dbManager;
    EditText etUserName;
    EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName = (EditText)findViewById(R.id.etUserName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        dbManager = new DBManager(this);
    }

    public void buSave(View view) {


        ContentValues values = new ContentValues();
        values.put(DBManager.ColUserName,etUserName.getText().toString());
        values.put(DBManager.ColPassword,etPassword.getText().toString());

        long id = dbManager.Insert(values);

        if(id>0){
            Toast.makeText(getApplicationContext(),"user id: " + id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"nie udało się zapisać", Toast.LENGTH_SHORT).show();
        }
    }


    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;

    public void buLoad(View view) {


        //add data and view it
//        listnewsData.add(new AdapterItems())



// jeżeli null to odpytuje wszystkie kolumny
//        String[] projection = {"UserName","Password"};
        String[] SelectionArgs={"%"+etUserName.getText().toString()+"%"};


        //czyszczę ekaran aby nie był cały czas wyświetlane to samo
        listnewsData.clear();
        Cursor cursor = dbManager.query(null,"UserName like ?",SelectionArgs,DBManager.ColUserName);

        if(cursor.moveToFirst()){

            String tableData="";

            do {

//                tableData += cursor.getString(cursor.getColumnIndex(DBManager.ColUserName)) + "," +
//                      cursor.getString(cursor.getColumnIndex(DBManager.ColPassword)) + "::";

                listnewsData.add(new AdapterItems(cursor.getString(cursor.getColumnIndex(DBManager.ColID)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColUserName)),cursor.getString(cursor.getColumnIndex(DBManager.ColPassword))));

            } while (cursor.moveToNext());

            Toast.makeText(getApplicationContext(),tableData, Toast.LENGTH_SHORT).show();

        }

        myadapter=new MyCustomAdapter(listnewsData);

        ListView lsNews=(ListView)findViewById(R.id.LVNews);
        lsNews.setAdapter(myadapter); //załaduj dane

    }




    public class MyCustomAdapter extends BaseAdapter{
        public ArrayList<AdapterItems> listnewDataAdapter;

        public MyCustomAdapter(ArrayList<AdapterItems> listnewDataAdapter){
            this.listnewDataAdapter=listnewDataAdapter;
        }

        @Override
        public int getCount() {
            return listnewDataAdapter.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket,null);

            final AdapterItems s = listnewDataAdapter.get(position);

            TextView tvId=(TextView)myView.findViewById(R.id.tvID);
            tvId.setText(s.ID);

            TextView tvUserName=(TextView)myView.findViewById(R.id.tvUserName);
            tvUserName.setText(s.UserName);

            TextView tvPassword=(TextView)myView.findViewById(R.id.tvPassword);
            tvId.setText(s.Password);


            return myView;

        }
    }




}
