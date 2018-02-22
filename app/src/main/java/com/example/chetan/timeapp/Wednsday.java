package com.example.chetan.timeapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.chetan.timeapp.Wednsday.list2;
import static com.example.chetan.timeapp.Wednsday.s2;

/**
 * Created by HP on 04-10-2017.
 */

public class Wednsday extends AppCompatActivity {
    public static ListView list2;
    Cadapter cadapter ;
    public static String s2;
    int p ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_activity3);
        s2=" day='2'";
        list2= (ListView) findViewById(R.id.list3);

        Cursor c = getContentResolver().query(database.CONTENT_URI,null,s2,null,null) ;
        cadapter = new Cadapter(this,c) ;
        list2.setAdapter( cadapter);
        FloatingActionButton fab;

        fab =(FloatingActionButton) findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Wednsday.this,save_data.class);
                //i.putExtra("id",0);
                Bundle b = new Bundle();
                b.putInt("DAY",2);
                b.putInt("id",0) ;
                i.putExtras(b) ;
                startActivity(i);
            }
        });

    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor c = getContentResolver().query(database.CONTENT_URI,null,s2,null,null) ;
        cadapter = new Cadapter(this,c);
        list2.setAdapter( cadapter);
    }
}

class Singlerow3
{
    String subject,teacher,type,room,timings;


    public Singlerow3(String subject,String teacher,String type,String room,String timings ) {
        this.subject=subject;
        this.room=room;
        this.teacher=teacher;
        this.timings=timings;
        this.type=type;
    }
}

class Cadapter extends BaseAdapter {
    ArrayList<Singlerow3> list2;
    Context context;
    Cursor cursor ;
    String []data=new String[6];

    int _id;
    ImageButton imageButton;
    Cadapter(Context c,Cursor cc){
        context=c;
        cursor=cc;
    }

    @Override
    public int getCount() {
        if(cursor!=null&&cursor.getCount()!=0)
            return cursor.getCount() ;
        else
            return 0 ;
    }

    @Override
    public Object getItem(int position) {
        return list2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //p=position;
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_card_view,parent,false);
        imageButton = (ImageButton) row.findViewById(R.id.imageb);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
                showPopupMenu(view,position);

            }
        });


        TextView title= (TextView) row.findViewById(R.id.textView3);
        TextView title1= (TextView) row.findViewById(R.id.textView11);
        TextView title2= (TextView) row.findViewById(R.id.textView12);
        TextView title3= (TextView) row.findViewById(R.id.textView13);
        TextView title4= (TextView) row.findViewById(R.id.textView14);
        cursor.moveToPosition(position) ;
        _id=cursor.getInt(cursor.getColumnIndex(database._ID));
        Log.d("ids",_id+"");
        data[0]=cursor.getString(cursor.getColumnIndex(database.subject));
        data[1]=cursor.getString(cursor.getColumnIndex(database.teachername));
        data[2]=cursor.getString(cursor.getColumnIndex(database.type));
        data[3]=cursor.getString(cursor.getColumnIndex(database.room_no));
        data[4]=cursor.getString(cursor.getColumnIndex(database.from));
        data[5]=cursor.getString(cursor.getColumnIndex(database.to));

        title.setText(cursor.getString(cursor.getColumnIndex(database.subject)));
        title1.setText(cursor.getString(cursor.getColumnIndex(database.teachername)));
        title2.setText(cursor.getString(cursor.getColumnIndex(database.type)));
        title3.setText(cursor.getString(cursor.getColumnIndex(database.room_no)));
        String a=(cursor.getString(cursor.getColumnIndex(database.from)));
        String b=(cursor.getString(cursor.getColumnIndex(database.to)));
        String d=a+"-"+b;
        title4.setText(d);

        return row;
    }
    private void showPopupMenu(View view,int position) {
        // inflate menu

        PopupMenu popup =null;
        popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popup.getMenu());


        popup.setOnMenuItemClickListener(new MyMenuItemClickListener2(position,context,_id,data));
        popup.show();
    }



}
class MyMenuItemClickListener2 implements PopupMenu.OnMenuItemClickListener {

    int position;
    Context c;
    int id;
    String []data=new String[6];

    public MyMenuItemClickListener2(int positon, Context context, int id,String []data) {
        this.position = positon;
        this.c = context;
        this.id = id;
        this.data=data;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_edit:

                Intent i = new Intent(c,save_data.class) ;
                Bundle b = new Bundle() ;
                b.putInt("id",1) ;
                Cursor cc = c.getContentResolver().query(database.CONTENT_URI,null,s2,null,null) ;
                cc.moveToPosition(position) ;
                b.putInt("pos",cc.getInt(cc.getColumnIndex(database._ID))) ;
                i.putExtras(b) ;
                c.startActivity(i);


                return true;
            case R.id.menu_delete:
                Log.d("check ID",position+"");

                //Cursor
                cc = c.getContentResolver().query(database.CONTENT_URI,null,s2,null,null) ;
                cc.moveToPosition(position) ;
                deleteCard(cc.getInt(cc.getColumnIndex(database._ID)),c);

                cc = c.getContentResolver().query(database.CONTENT_URI,null,s2,null,null) ;
                Cadapter cadapter = new Cadapter(c,cc);
                list2.setAdapter( cadapter);
                // c.getContentResolver().delete(database.CONTENT_URI, null,null);
                return true;
            case R.id.menu_notify:
                Intent share=new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,"Teacher: "+data[1]+"\nSubject: "+data[0]+"\nType: "+data[2]+"\nRoom No."+data[3]+"\nFrom: "+data[4]+"\nTo: "+data[5]);
                c.startActivity(share);
                return true;
//            case R.id.delete:
//                mySharedPreferences.deletePrefs(Constants.REMOVE_CTAGURY,MainActivity.context);
//            default:
        }
        return false;
    }

    public void deleteCard(int id,Context c){
        String where="_ID = ? ";
        String [] s={id+""};
        c.getContentResolver().delete(database.CONTENT_URI,where,s);
    }

}


