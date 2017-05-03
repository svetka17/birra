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
 
public class RasxodOstatActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  static TextView tvIdPgr;//, tvIdKlient; 
  //static EditText tvDataIns;
  static TextView tvDataIns,tvDataIns2, tvd1, tvd2;
  Spinner spPgr;//, spKlient;
  //Cursor cKlient;
  //SimpleCursorAdapter scaKlient;
  
  //LinearLayout ll;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rasxod_ostat);
    //final DialogFragment dlg = new DialogActivity();
    
    tvDataIns = (TextView) findViewById(R.id.tv_Data_RasOst);
    tvDataIns.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    tvd1 = (TextView) findViewById(R.id.tv_data_rasost1);
    tvd1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    tvDataIns2 = (TextView) findViewById(R.id.tv_Data_RasOst2);
    tvDataIns2.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });
    tvd2 = (TextView) findViewById(R.id.tv_data_rasost2);
    tvd2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_RasOst);
    tvIdPgr = (TextView) findViewById(R.id.tv_Id_PgrRasOst);
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
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText("0");
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        });
    
    
    btnExit = (Button) findViewById(R.id.btnExitRasxodOst);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnAdd = (Button) findViewById(R.id.btnExcelRasxodOst);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(RasxodOstatActivity.this, RasxodOstatActivity.this, tvDataIns.getText().toString(), 
        			tvDataIns2.getText().toString(), 
        			tvIdPgr.getText().toString(), "Расход с остатком за период", (byte)3);
        	}
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { /*"_id",*/"_id","pgr","name","post","kol","ed","sumka","ostat"};
    int[] to = new int[] {/*R.id.tv_Id_Rasxod_Hist,*/ R.id.tv_Nnom_Rasxod_Ost, R.id.tv_Pgr_Rasxod_Ost, R.id.tv_Name_Rasxod_Ost,R.id.tv_Post_Rasxod_Ost,R.id.tv_Kol_Rasxod_Ost,R.id.tv_Ed_Rasxod_Ost,R.id.tv_Sumka_Rasxod_Ost,R.id.tv_Ostat_Rasxod_Ost};
    //int[] toH = new int[] {R.id.tv_Nnom_Rasxod_Hist,R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_DataIns_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelRasxodOst, R.id.btnUpdRasxodOst, (byte)10, this, R.layout.rasxod_ostat_item, null, from, to, 0);
    lvData = (ListView) findViewById(R.id.lvRasxodOst);
    lvData.setAdapter(scAdapter);
     
    // создаем лоадер для чтения данных
    //getSupportLoaderManager().initLoader(1, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.rasxod_ostat_ll));
  }
  
  protected Dialog onCreateDialog(int id) {
      if (id == 1) {
        DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return tpd;
      }
      if (id == 2) {
          DatePickerDialog tpd = new DatePickerDialog(this, myCallBack2, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
          return tpd;
        }
      return super.onCreateDialog(id);
    }
     
    OnDateSetListener myCallBack = new OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth) {
      tvDataIns.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
      //getSupportLoaderManager().getLoader(1).forceLoad();
      getSupportLoaderManager().getLoader(0).forceLoad();
    }
    };
    OnDateSetListener myCallBack2 = new OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth) {
      tvDataIns2.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
      //getSupportLoaderManager().getLoader(1).forceLoad();
      getSupportLoaderManager().getLoader(0).forceLoad();
    }
    };
  @Override
  protected void onRestart() {
    super.onRestart();
    //getSupportLoaderManager().getLoader(1).forceLoad();
    getSupportLoaderManager().getLoader(0).forceLoad();
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
 
  @Override
  
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
     // Log.d("Loader<Cursor> onCreateLoader", String.valueOf(i));
      if (i == 0 ) return new RasxodLoader(this, MainActivity.db);
      //if (i == 1 ) return new KlientLoader(this, MainActivity.db);
      return null;
  }
  
  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	  /*switch(loader.getId())
      {
      case 0:*/
    	  scAdapter.swapCursor(cursor);
      /* break;
      case 1:
    	  scaKlient.swapCursor(cursor);
       break;
   }*/
	  
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
        		(tvDataIns.getText().length()==0)?"":" substr(T.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")",
        				(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")"
        				//,(tvIdKlient.getText().toString().equals("0")||tvIdKlient.getText().length()==0)?"":" K._id="+tvIdKlient.getText().toString()
        						};
        String where=str[0].toString();
        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
     
        if (where.equals("")||where.length()==0) where=str[2].toString(); else 
        	if (!str[2].equals("")) where=where+" and "+str[2].toString();
        
/*        if (where.equals("")||where.length()==0) where=str[3].toString(); else 
        	if (!str[3].equals("")) where=where+" and "+str[3].toString();
"id_tmc","pgr","name","post","kol","ed","sumka","ostat"
*/     
            	 Cursor cursor = db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id left join ostat as K on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed left join postav as KK on T.id_post=KK._id", 
             			new String[] {"TP._id as _id","TT.name as pgr","TP.name as name",/*"T.data_ins as data_ins",*/"KK.name as post","sum(T.kol) as kol","E.name as ed",
            			 "sum(T.price*T.kol) as sumka","K.kol as ostat"}, 
             			 //"TP.pgr = ?"
            			 where, null,"TP._id, TT.name, TP.name, KK.name, E.name, K.kol",null,null);

      return cursor;
    }
     
  }
  

}

