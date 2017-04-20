package luce.birra;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import luce.birra.DialogScreen.DialogListener;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
 
public class OtchetActivity extends FragmentActivity {

 // ListView lvData;
  Button btnExit, btnAdd, btnO, btnOO, btnOOO, btnP, btnR;
  AdapterLV scAdapterO;//scAdapterR, scAdapterP ;
  static TextView tvIdPgr; //tvIdKlient, 
  static TextView tvDataIns, tvDataIns2;
  Spinner spPgr;//, spKlient;
  //Cursor cKlient;
  //SimpleCursorAdapter scaKlient;
  TextView d1,d2;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.otchet);
    //final DialogFragment dlg = new DialogActivity();
    d1 = (TextView) findViewById(R.id.tv_otchetD1);
    d1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    
    d2 = (TextView) findViewById(R.id.tv_otchetD2);
    d2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });
    
    tvDataIns = (TextView) findViewById(R.id.tv_Data_Otchet);
    tvDataIns.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    
    tvDataIns2 = (TextView) findViewById(R.id.tv_Data2_Otchet);
    tvDataIns2.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });
    
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Otchet);
    tvIdPgr = (TextView) findViewById(R.id.tv_Id_PgrOtchet);
    tvIdPgr.setText("0");
   
    // make an adapter from the cursor
    String[] fromPgr = new String[] {"name"};
    int[] toPgr = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_item, c, fromPgr, toPgr);

    sca.setDropDownViewResource(R.layout.spinner_drop_down); 
    spPgr.setAdapter(sca);

    spPgr.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spPgr.setTag(id);
        	tvIdPgr.setText(String.valueOf(id));
        	//getSupportLoaderManager().getLoader(0).forceLoad();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText("0");
        	//getSupportLoaderManager().getLoader(0).forceLoad();
        }
        });

    
    btnExit = (Button) findViewById(R.id.btnExitOtchet);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnOO = (Button) findViewById(R.id.btnObO);
    btnOO.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(OtchetActivity.this, OstatActivity.class);
			   startActivity(intent);
        }
      });
    btnOOO = (Button) findViewById(R.id.btnOO);
    btnOOO.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//oborotka();
        	Intent intent = new Intent(OtchetActivity.this, RasxodOstatActivity.class);
			   startActivity(intent);
        }
      });
    btnO = (Button) findViewById(R.id.btnOborotkaOtchet);
    btnO.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(OtchetActivity.this, OborotkaActivity.class);
			   startActivity(intent);
        }
      });
    
    btnP = (Button) findViewById(R.id.btnPrihodOtchet);
    btnP.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//prixod();
        	Intent intent = new Intent(OtchetActivity.this, PrixodHistActivity.class);
			   startActivity(intent);
        }
      });
    btnR = (Button) findViewById(R.id.btnRasxodOtchet);
    btnR.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//rasxod();
        	Intent intent = new Intent(OtchetActivity.this, RasxodHistActivity.class);
			   startActivity(intent);
        }
      });
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.otchet_ll));
}
  //intent = new Intent(this, OtchetActivity.class);
  //startActivity(intent);
void oborotka () {
//////////////////////////////////////
String dataStr="";
String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP._id="+tvIdPgr.getText().toString()
,(tvDataIns.getText().length()==0)?"":"where substr(data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"
};
String where=str[0].toString();
//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
Cursor cc = MainActivity.db.getQueryData( 
"ostat as O left join (select id_tmc, id_post, ed, sum(round(kol,3)) as sumkr, sum(round(kol,3)*round(price,2)) as sumsr from rasxod " + str[1].toString() + 
" group by id_tmc, id_post, ed) as R on O.id_tmc=R.id_tmc and O.id_post=R.id_post and O.ed=R.ed "
+ " left join (select id_tmc, id_post, ed, sum(round(kol,3)) as sumkp, sum(round(kol,3)*round(price,2)) as sumsp from prixod " + str[1].toString() +
" group by id_tmc, id_post, ed) as P on O.id_tmc=P.id_tmc and O.id_post=P.id_post and O.ed=P.ed"
+ " left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id",
new String[] {"O._id as _id",
"O.id_tmc as id_tmc","T.name as name","E.name as ted",
//"O.sumka+R.sumkr-P.sumkp as kol_n","O.sumka+R.sumsr-P.sumsp as sum_n","0 as price_n",
"sumkp as kol_pri","sumsp sum_pri",//"0 as price_pri",
"sumkr kol_ras","sumsr as sum_ras",//"0 as price_ras",
"O.kol kol_k","O.sumka as sum_k"//,"0 as price_k"
}, 
where, null,null,null,null);
///////////////////////////////////////////////////////
String columnString =   "\"ÍÎÌÅÍÊËÀÒÓÐÀ\",\"ÍÀÇÂÀÍÈÅ\",\"ÅÄ.ÈÇÌ\",\"ÊÎË ÍÀ×\",\"ÑÓÌÌÀ ÍÀ×\",\"ÖÅÍÀ ÍÀ×\",\"ÊÎË ÏÐÈ\",\"ÑÓÌÌÀ ÏÐÈ\",\"ÊÎË ÐÀÑ\",\"ÑÓÌÌÀ ÐÀÑ\",\"ÊÎË ÊÎÍ\",\"ÑÓÌÌÀ ÊÎÍ\",\"ÖÅÍÀ ÊÎÍ\"";
//String dataString   =   "\"" + currentUser.userName +"\",\"" + currentUser.gender + "\",\"" + currentUser.street1 + "\",\"" + currentUser.postOFfice.toString()+ "\",\"" + currentUser.age.toString() + "\"";
//String combinedString = columnString + "\n" + dataString;

File file   = null;
File root   = Environment.getExternalStorageDirectory();
if (root.canWrite()){
File dir    =   new File (root.getAbsolutePath() + "/Oborotka");
dir.mkdirs();
file   =   new File(dir, "O"+Calendar.getInstance().get(Calendar.DATE)+Calendar.getInstance().get(Calendar.MONTH)+Calendar.getInstance().get(Calendar.YEAR)+".xls");
FileOutputStream out   =   null;
try {
out = new FileOutputStream(file);
} catch (FileNotFoundException e) {
e.printStackTrace();
}
try {
out.write(columnString.getBytes());
if (cc.moveToFirst()) { 

do {
float koln=0, sumn=0, pricen=0, kolp=0, sump=0, pricep=0, kolr=0, sumr=0, pricer=0, kolk=0, sumk=0, pricek=0;
koln = cc.getFloat(cc.getColumnIndex("kol_k"))+cc.getFloat(cc.getColumnIndex("kol_ras"))-cc.getFloat(cc.getColumnIndex("kol_pri")) ;
sumn = cc.getFloat(cc.getColumnIndex("sum_k"))+cc.getFloat(cc.getColumnIndex("sum_ras"))-cc.getFloat(cc.getColumnIndex("sum_pri")) ;
if (koln!=0) pricen=Math.round((sumn/koln)*100)/100.0f;
kolp = cc.getFloat(cc.getColumnIndex("kol_pri")) ;
sump = cc.getFloat(cc.getColumnIndex("sum_pri")) ;
//if (kolp!=0) pricep=Math.round((sump/kolp)*100)/100.0f;
kolr = cc.getFloat(cc.getColumnIndex("kol_ras")) ;
sumr = cc.getFloat(cc.getColumnIndex("sum_ras")) ;
//if (kolr!=0) pricep=Math.round((sumr/kolr)*100)/100.0f;
kolk = cc.getFloat(cc.getColumnIndex("kol_k")) ;
sumk = cc.getFloat(cc.getColumnIndex("sum_k")) ;
if (kolk!=0) pricep=Math.round((sumr/kolk)*100)/100.0f;

//dataStr   =  "\n" + "\"" + cc.getInt(cc.getColumnIndex("id_tmc")) +"\",\"" + cc.getString(cc.getColumnIndex("name")) + "\",\"" + cc.getString(cc.getColumnIndex("ted")) + "\",\"" +koln+ "\",\""+sumn+ "\",\"" + cc.getFloat(cc.getColumnIndex("price_n"))+"\",\"" 
		//+ cc.getFloat(cc.getColumnIndex("kol_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("kol_ras"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_ras"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_ras"))+"\",\""
		//+ cc.getFloat(cc.getColumnIndex("kol_k"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_k"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_k"))+ "\"" ;
dataStr   =  "\n" + "\"" + cc.getInt(cc.getColumnIndex("id_tmc")) +"\",\"" + cc.getString(cc.getColumnIndex("name")) + "\",\"" + cc.getString(cc.getColumnIndex("ted")) + "\",\"" +koln+ "\",\""+sumn+ "\",\"" + pricen+"\",\"" 
		+ kolp+"\",\""+ sump+"\",\""+ kolr+"\",\""+ sumr+"\",\""
		+ kolk+"\",\""+ sumk+"\",\""+ pricek+ "\"" ;
out.write(dataStr.getBytes());
} while (cc.moveToNext());

} 
} catch (IOException e) {
e.printStackTrace(); cc.close();
}
try {
out.close();
} catch (IOException e) {
e.printStackTrace(); cc.close();
}
Uri u1  =   null;
u1  =   Uri.fromFile(file);

Intent sendIntent = new Intent(Intent.ACTION_SEND);
sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Îáîðîòíàÿ âåäîìîñòü ñ "+tvDataIns.getText().toString()+" ïî "+Calendar.getInstance().get(Calendar.DATE)+"."+Calendar.getInstance().get(Calendar.MONTH)+"."+Calendar.getInstance().get(Calendar.YEAR));
sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
sendIntent.setType("text/html");
startActivity(sendIntent);    
}
cc.close();
///////////////////////////////////////////////////////
}

void rasxod () {

//////////////////////////////////////
String dataStr="";
String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP._id="+tvIdPgr.getText().toString()
,(tvDataIns.getText().length()==0)?"":" substr(data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+") and substr(data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")"
};
String where=str[0].toString();
//if (where.equals("")||where.length()==0) where=str[1].toString(); else 
	//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
Cursor cc = MainActivity.db.getQueryData( 
"rasxod as R "
+ " left join tmc as T on R.id_tmc=T._id left join tmc_ed as E on R.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id",
new String[] {"R._id as _id",
"R.id_tmc as id_tmc","T.name as name","E.name as ted","TP.name namet",
//"O.sumka+R.sumkr-P.sumkp as kol_n","O.sumka+R.sumsr-P.sumsp as sum_n","0 as price_n",
"R.prim prim",//"0 as price_pri",
"R.kol*R.price sumka","R.data_ins as data_ins",//"0 as price_ras",
"R.kol kol","R.price as price"//,"0 as price_k"
}, 
where, null,null,null,null);
///////////////////////////////////////////////////////
String columnString =   "\"ÍÎÌÅÍÊËÀÒÓÐÀ\",\"ÍÀÇÂÀÍÈÅ\",\"ÃÐÓÏÏÀ\",\"ÅÄ.ÈÇÌ\",\"ÊÎË-ÂÎ\",\"ÖÅÍÀ\",\"ÑÓÌÌÀ\",\"ÄÀÒÀ\"";
//String dataString   =   "\"" + currentUser.userName +"\",\"" + currentUser.gender + "\",\"" + currentUser.street1 + "\",\"" + currentUser.postOFfice.toString()+ "\",\"" + currentUser.age.toString() + "\"";
//String combinedString = columnString + "\n" + dataString;

File file   = null;
File root   = Environment.getExternalStorageDirectory();
if (root.canWrite()){
File dir    =   new File (root.getAbsolutePath() + "/Oborotka");
dir.mkdirs();
file   =   new File(dir, "R"+Calendar.getInstance().get(Calendar.DATE)+Calendar.getInstance().get(Calendar.MONTH)+Calendar.getInstance().get(Calendar.YEAR)+".xls");
FileOutputStream out   =   null;
try {
out = new FileOutputStream(file);
} catch (FileNotFoundException e) {
e.printStackTrace();
}
try {
out.write(columnString.getBytes());
if (cc.moveToFirst()) { 

do {
float 
sumka = cc.getFloat(cc.getColumnIndex("kol"))*cc.getFloat(cc.getColumnIndex("price")) ;
sumka=Math.round(sumka*100)/100.0f;

//dataStr   =  "\n" + "\"" + cc.getInt(cc.getColumnIndex("id_tmc")) +"\",\"" + cc.getString(cc.getColumnIndex("name")) + "\",\"" + cc.getString(cc.getColumnIndex("ted")) + "\",\"" +koln+ "\",\""+sumn+ "\",\"" + cc.getFloat(cc.getColumnIndex("price_n"))+"\",\"" 
//+ cc.getFloat(cc.getColumnIndex("kol_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("kol_ras"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_ras"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_ras"))+"\",\""
//+ cc.getFloat(cc.getColumnIndex("kol_k"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_k"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_k"))+ "\"" ;
dataStr   =  "\n" + "\"" + cc.getInt(cc.getColumnIndex("id_tmc")) +"\",\"" + cc.getString(cc.getColumnIndex("name")) + "\",\""+ cc.getString(cc.getColumnIndex("namet")) + "\",\"" + cc.getString(cc.getColumnIndex("ted")) + "\",\"" +cc.getFloat(cc.getColumnIndex("kol"))+ "\",\""+cc.getFloat(cc.getColumnIndex("price"))+ "\",\"" 
+sumka+ "\",\""
+MainActivity.getStringData( cc.getInt(cc.getColumnIndex("data_ins")) )+ "\"" ;
out.write(dataStr.getBytes());
} while (cc.moveToNext());

} 
} catch (IOException e) {
e.printStackTrace(); cc.close();
}
try {
out.close();
} catch (IOException e) {
e.printStackTrace(); cc.close();
}
Uri u1  =   null;
u1  =   Uri.fromFile(file);

Intent sendIntent = new Intent(Intent.ACTION_SEND);
sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Ðàñõîä ñ "+tvDataIns.getText().toString()+" ïî "+Calendar.getInstance().get(Calendar.DATE)+"."+Calendar.getInstance().get(Calendar.MONTH)+"."+Calendar.getInstance().get(Calendar.YEAR));
sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
sendIntent.setType("text/html");
startActivity(sendIntent);    
}
cc.close();
///////////////////////////////////////////////////////
}
  
void prixod () {
//////////////////////////////////////
String dataStr="";
String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP._id="+tvIdPgr.getText().toString()
,(tvDataIns.getText().length()==0)?"":" substr(data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+") and substr(data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")"
};
String where=str[0].toString();
//if (where.equals("")||where.length()==0) where=str[1].toString(); else 
	//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
Cursor cc = MainActivity.db.getQueryData( 
"prixod as R "
+ " left join tmc as T on R.id_tmc=T._id left join tmc_ed as E on R.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id",
new String[] {"R._id as _id",
"R.id_tmc as id_tmc","T.name as name","E.name as ted","TP.name namet",
//"O.sumka+R.sumkr-P.sumkp as kol_n","O.sumka+R.sumsr-P.sumsp as sum_n","0 as price_n",
"R.prim prim",//"0 as price_pri",
"R.kol*R.price sumka","R.data_ins as data_ins",//"0 as price_ras",
"R.kol kol","R.price as price"//,"0 as price_k"
}, 
where, null,null,null,null);
///////////////////////////////////////////////////////
String columnString =   "\"ÍÎÌÅÍÊËÀÒÓÐÀ\",\"ÍÀÇÂÀÍÈÅ\",\"ÃÐÓÏÏÀ\",\"ÅÄ.ÈÇÌ\",\"ÊÎË-ÂÎ\",\"ÖÅÍÀ\",\"ÑÓÌÌÀ\",\"ÄÀÒÀ\"";
//String dataString   =   "\"" + currentUser.userName +"\",\"" + currentUser.gender + "\",\"" + currentUser.street1 + "\",\"" + currentUser.postOFfice.toString()+ "\",\"" + currentUser.age.toString() + "\"";
//String combinedString = columnString + "\n" + dataString;

File file   = null;
File root   = Environment.getExternalStorageDirectory();
if (root.canWrite()){
File dir    =   new File (root.getAbsolutePath() + "/Oborotka");
dir.mkdirs();
file   =   new File(dir, "P"+Calendar.getInstance().get(Calendar.DATE)+Calendar.getInstance().get(Calendar.MONTH)+Calendar.getInstance().get(Calendar.YEAR)+".xls");
FileOutputStream out   =   null;
try {
out = new FileOutputStream(file);
} catch (FileNotFoundException e) {
e.printStackTrace();
}
try {
out.write(columnString.getBytes());
if (cc.moveToFirst()) { 

do {
float 
sumka = cc.getFloat(cc.getColumnIndex("kol"))*cc.getFloat(cc.getColumnIndex("price")) ;
sumka=Math.round(sumka*100)/100.0f;

//dataStr   =  "\n" + "\"" + cc.getInt(cc.getColumnIndex("id_tmc")) +"\",\"" + cc.getString(cc.getColumnIndex("name")) + "\",\"" + cc.getString(cc.getColumnIndex("ted")) + "\",\"" +koln+ "\",\""+sumn+ "\",\"" + cc.getFloat(cc.getColumnIndex("price_n"))+"\",\"" 
//+ cc.getFloat(cc.getColumnIndex("kol_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_pri"))+"\",\""+ cc.getFloat(cc.getColumnIndex("kol_ras"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_ras"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_ras"))+"\",\""
//+ cc.getFloat(cc.getColumnIndex("kol_k"))+"\",\""+ cc.getFloat(cc.getColumnIndex("sum_k"))+"\",\""+ cc.getFloat(cc.getColumnIndex("price_k"))+ "\"" ;
dataStr   =  "\n" + "\"" + cc.getInt(cc.getColumnIndex("id_tmc")) +"\",\"" + cc.getString(cc.getColumnIndex("name")) + "\",\""+ cc.getString(cc.getColumnIndex("namet")) + "\",\"" + cc.getString(cc.getColumnIndex("ted")) + "\",\"" +cc.getFloat(cc.getColumnIndex("kol"))+ "\",\""+cc.getFloat(cc.getColumnIndex("price"))+ "\",\"" 
		+sumka+ "\",\""
		+MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) )+ "\"" ;
out.write(dataStr.getBytes());
} while (cc.moveToNext());

} 
} catch (IOException e) {
e.printStackTrace(); cc.close();
}
try {
out.close();
} catch (IOException e) {
e.printStackTrace(); cc.close();
}
Uri u1  =   null;
u1  =   Uri.fromFile(file);

Intent sendIntent = new Intent(Intent.ACTION_SEND);
sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Ðàñõîä ñ "+tvDataIns.getText().toString()+" ïî "+Calendar.getInstance().get(Calendar.DATE)+"."+Calendar.getInstance().get(Calendar.MONTH)+"."+Calendar.getInstance().get(Calendar.YEAR));
sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
sendIntent.setType("text/html");
startActivity(sendIntent);    
}
cc.close();
///////////////////////////////////////////////////////
}

protected Dialog onCreateDialog(int id) {
      if (id == 1) {
        DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return tpd;
      };
      if (id == 2) {
          DatePickerDialog tpd = new DatePickerDialog(this, myCallBack2, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
          return tpd;
        };
      return super.onCreateDialog(id);
    }
     
    OnDateSetListener myCallBack = new OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
    {
      tvDataIns.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
    }
    };
    
    OnDateSetListener myCallBack2 = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
        {
          tvDataIns2.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
        }
        };
    
  @Override
  protected void onRestart() {
    super.onRestart();
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
  
}

