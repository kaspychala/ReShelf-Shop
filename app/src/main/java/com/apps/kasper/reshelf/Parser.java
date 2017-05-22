package com.apps.kasper.reshelf;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

public class Parser extends AsyncTask<Void,Integer,Integer> {
    Context c;
    ListAdapter adapter;
    TwoWayView lv;
    String data;
    String title;
    String author;
    ProgressDialog pd;
    public Parser(Context c, String data, TwoWayView lv) {
        this.c = c;
        this.data = data;
        this.lv = lv;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing ....Please wait");
        pd.show();
    }
    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer == 1)
        {
            //ADAPTER
            adapter = new ListAdapter(c, R.layout.row_book, AppConfig.RowBookData);
            //ADAPT TO LISTVIEW
            lv.setAdapter(adapter);
        }else
        {
            Toast.makeText(c,"Unable to Parse",Toast.LENGTH_SHORT).show();
        }
        pd.dismiss();
    }
    //PARSE RECEIVED DATA
    private int parse()
    {
        try
        {
            //ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja=new JSONArray(data);
            //CREATE JO OBJ TO HOLD A SINGLE ITEM
            JSONObject jo=null;
            AppConfig.RowBookData = new RowBook[ja.length()];
            //LOOP THRU ARRAY
            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                //RETRIEVE NAME
                title=jo.getString("Title");
                author=jo.getString("Author");
                //ADD IT TO OUR ARRAYLIST
                AppConfig.RowBookData[i] = new RowBook(R.drawable.add, title, author);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("tagconvertstr", "["+data+"]");
        }
        return 0;
    }
}