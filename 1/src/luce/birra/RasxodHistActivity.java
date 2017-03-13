package luce.birra;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
 
public class RasxodHistActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  static TextView tvIdPgr, tvIdKlient, tvDataIns;
  Spinner spPgr, spKlient;
  //Cursor cKlient;
  SimpleCursorAdapter scaKlient;
  TextView tv;
  
  LinearLayout ll;
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
      super.onWindowFocusChanged(hasFocus);
      if(hasFocus) 
      {
    	  
    	  ll = (LinearLayout) findViewById(R.id.llHRasxodHist);
    	  if (scAdapter.wH<15) ll.setVisibility(LinearLayout.GONE);	  
    	  else
    	  {ll.setVisibility(LinearLayout.VISIBLE);
    	  int [] r = {R.id.tvHR1,R.id.tvHR2,R.id.tvHR3,R.id.tvHR4,R.id.tvHR5,R.id.tvHR6,R.id.tvHR7,R.id.tvHR8};
    	  for (int i=0;i<r.length;i++) {
    		  tv = (TextView) findViewById(r[i]); 
    		  tv.getLayoutParams().width = scAdapter.wH;
    			}
    	      	  }
    	  }
  }
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rasxod_hist);
    //final DialogFragment dlg = new DialogActivity();
    
    tvDataIns = (TextView) findViewById(R.id.tv_Data_RasHist);
    tvDataIns.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_RasHist);
    tvIdPgr = (TextView) findViewById(R.id.tv_Id_PgrRasHist);
    tvIdPgr.setText("0");
   
    // make an adapter from the cursor
    String[] fromPgr = new String[] {"name"};
    int[] toPgr = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, fromPgr, toPgr);

    sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
    spPgr.setAdapter(sca);

    spPgr.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spPgr.setTag(id);
        	tvIdPgr.setText(String.valueOf(id));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText("0");
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        });
    
    tvIdKlient = (TextView) findViewById(R.id.tv_Id_KlientRasHist);
    tvIdKlient.setText("0");
    
    spKlient = (Spinner) findViewById(R.id.sp_Klient_RasHist);
    String[] fromKlient = new String[] {"sumka"};
    int[] toKlient = new int[] {android.R.id.text1};
    scaKlient = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null, fromKlient, toKlient);   
    scaKlient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 

    spKlient.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spKlient.setTag(id);
        	tvIdKlient.setText(String.valueOf(id));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spKlient.setTag(0);
        	tvIdKlient.setText("0");
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        });
    spKlient.setAdapter(scaKlient);
    
    /*btnAdd = (Button) findViewById(R.id.btnAddPrixodHist);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//Intent intent = new Intent(PgrActivity.this, PrihodA.class);
			//startActivity(intent);
        }
      });*/
    
    btnExit = (Button) findViewById(R.id.btnExitRasxodHist);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { "_id","id_tmc","name","kol","ch","sumka","price","prim"};
    int[] to = new int[] {R.id.tv_Id_Rasxod_Hist, R.id.tv_Nnom_Rasxod_Hist, R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist};
    //int[] toH = new int[] {R.id.tv_Nnom_Rasxod_Hist,R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_DataIns_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelRasxodHist, R.id.btnUpdRasxodHist, (byte)4, this, R.layout.rasxod_hist_item, null, from, to, 0);
    lvData = (ListView) findViewById(R.id.lvRasxodHist);
    lvData.setAdapter(scAdapter);
     
    // создаем лоадер для чтения данных
    getSupportLoaderManager().initLoader(1, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    
  }
  
  protected Dialog onCreateDialog(int id) {
      if (id == 1) {
        DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return tpd;
      }
      return super.onCreateDialog(id);
    }
     
    OnDateSetListener myCallBack = new OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth) {
      tvDataIns.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
      getSupportLoaderManager().getLoader(1).forceLoad();
      getSupportLoaderManager().getLoader(0).forceLoad();
    }
    };
    
  @Override
  protected void onRestart() {
    super.onRestart();
    getSupportLoaderManager().getLoader(1).forceLoad();
    getSupportLoaderManager().getLoader(0).forceLoad();
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
 
  @Override
  
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
     // Log.d("Loader<Cursor> onCreateLoader", String.valueOf(i));
      if (i == 0 ) return new RasxodLoader(this, MainActivity.db);
      if (i == 1 ) return new KlientLoader(this, MainActivity.db);
      return null;
  }
  
  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	  switch(loader.getId())
      {
      case 0:
    	  scAdapter.swapCursor(cursor);
       break;
      case 1:
    	  scaKlient.swapCursor(cursor);
       break;
   }
	  
  }
 
  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
	 /* switch(loader.getId())
      {
      case 0:
    	  scAdapter.swapCursor(null);
       break;
      case 1:
    	  scaKlient.swapCursor(null);
       break;
   }*/
  }
   
  static class RasxodLoader extends CursorLoader {
 
    DB db;
    public RasxodLoader(Context context, DB db/*, int id*/) {
      super(context);
      this.db = db;
    }
     
    @Override
    public Cursor loadInBackground() {
    	String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP.pgr="+tvIdPgr.getText().toString(),
        		(tvDataIns.getText().length()==0)?"":" substr(T.data_ins,1,6)=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")",
        				(tvIdKlient.getText().toString().equals("0")||tvIdKlient.getText().length()==0)?"":" K._id="+tvIdKlient.getText().toString()};
        String where=str[0].toString();
        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
     
        if (where.equals("")||where.length()==0) where=str[2].toString(); else 
        	if (!str[2].equals("")) where=where+" and "+str[2].toString();
     
            	 Cursor cursor = db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_ed as E on T.ed = E._id left join klient as K on T.id_klient = K._id", 
             			new String[] {"T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","round(T.kol,3)||' '||E.name as kol",
            			 "E.name as ted", "T.ed as ed","T.price as price","T.prim as prim","TP.pgr as pgr","K.sumka as sumka","'№'||K._id as ch"}, 
             			 //"TP.pgr = ?"
            			 where, null,null,null,null);

      return cursor;
    }
     
  }
  
  static class KlientLoader extends CursorLoader {
	  
	    DB db;
	    public KlientLoader(Context context, DB db/*, int id*/) {
	      super(context);
	      this.db = db;
	    }
	     
	    @Override
	    public Cursor loadInBackground() {
	    	
	            	 Cursor cursor = MainActivity.db.getRawData("select _id, name sumka, null name from foo union all select _id, sumka, name from klient "+
	     	        		((tvDataIns.getText().length()==0)?"":" where substr(data_ins,1,6)=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")")
	    	        		, null);
	    
	      return cursor;
	    }
	     
	  }

}

