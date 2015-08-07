package com.satish.sqlitejsonparse;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ListDataModel> listdatamodel;
    private CustomAdapter adapter;
    private ProgressDialog progressDialog;
    private DatabaseHandler db;
    private static final String URL = "http://api.androidhive.info/contacts/";
    private JsonParseHandler jsonParseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.list_view);
        listdatamodel=new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        db=new DatabaseHandler(this);
        jsonParseHandler=new JsonParseHandler();
        new DataFetch().execute();
        List<ListDataModel> users = db.getAllUsers();
        for (ListDataModel data : users) {
            String log = "Id: "+data.getId()+" ,Name: " + data.getName() + " ,Phone: " + data.getPhone()+","+data.getEmail();

            ListDataModel item = new ListDataModel();
            item.setName(data.getName());
            item.setPhone(data.getPhone());
            //item.setId(data.getId());
            item.setEmail(data.getEmail());

            listdatamodel.add(item);
            Log.d("Name: ", log);
        }

        adapter=new CustomAdapter(listdatamodel,this);
        listView.setAdapter(adapter);

    }
    private void readJsonParse(String json_data) {
        try {
            JSONObject jsonobject=new JSONObject(json_data);
            JSONArray contacts=jsonobject.getJSONArray("contacts");
            for(int i=0;i<contacts.length();i++){
                JSONObject contactObj=contacts.getJSONObject(i);
                String id=contactObj.getString("id");
                String email=contactObj.getString("email");
                String name=contactObj.getString("name");
                JSONObject phone=contactObj.getJSONObject("phone");
                String home_contact=phone.getString("home");
                Log.d("data is",id+","+name+","+email+","+home_contact);
                db.addUsers(new ListDataModel(id, name, email, home_contact));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  class DataFetch extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json_data=jsonParseHandler.serviceCall(URL,JsonParseHandler.GET);
            Log.d("in inBG()", "fetch data" + json_data);
            readJsonParse(json_data);
//            adapter.notifyDataSetChanged();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }
}
