package luce.birra;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import luce.birra.AdapterLV.CambiareListener;
import luce.birra.DialogScreen.DialogListener;
 
public class RasxodHistActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  static TextView tvIdPgr, tvIdKlient; 
  //static EditText tvDataIns;
  static TextView tvDataIns,tvDataIns2, tvd1, tvd2;
  Spinner spPgr, spKlient;
  //Cursor cKlient;
  SimpleCursorAdapter scaKlient;
  static int tmp=0;
  //LinearLayout ll;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rasxod_hist);
    //final DialogFragment dlg = new DialogActivity();
    
    tvDataIns = (TextView) findViewById(R.id.tv_Data_RasHist);
    tvDataIns.setText(MainActivity.getStringDataTime( Integer.parseInt( String.valueOf( MainActivity.getIntDataTime() ).substring(0,6).concat("0000") ) ));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(1);
        	DialogScreen getDT = new DialogScreen(RasxodHistActivity.this,RasxodHistActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns.setText(MainActivity.getStringDataTime((int)k));
						getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvd1 = (TextView) findViewById(R.id.tv_data_ras1);
    tvd1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(1);
        	DialogScreen getDT = new DialogScreen(RasxodHistActivity.this,RasxodHistActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns.setText(MainActivity.getStringDataTime((int)k));
						getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvDataIns2 = (TextView) findViewById(R.id.tv_Data_RasHist2);
    tvDataIns2.setText(MainActivity.getStringDataTime(MainActivity.getIntDataTime()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(2);
        	DialogScreen getDT = new DialogScreen(RasxodHistActivity.this,RasxodHistActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns2.setText(MainActivity.getStringDataTime((int)k));
						getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvd2 = (TextView) findViewById(R.id.tv_data_ras2);
    tvd2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(2);
        	DialogScreen getDT = new DialogScreen(RasxodHistActivity.this,RasxodHistActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns2.setText(MainActivity.getStringDataTime((int)k));
						getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by _id", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_RasHist);
    tvIdPgr = (TextView) findViewById(R.id.tv_Id_PgrRasHist);
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
    
    tvIdKlient = (TextView) findViewById(R.id.tv_Id_KlientRasHist);
    tvIdKlient.setText("0");
    
    spKlient = (Spinner) findViewById(R.id.sp_Klient_RasHist);
    /*String[] fromKlient = new String[] {"sumka"};
    int[] toKlient = new int[] {android.R.id.text1};
    scaKlient = new SimpleCursorAdapter(this, R.layout.spinner_item, null, fromKlient, toKlient);   
    scaKlient.setDropDownViewResource(R.layout.spinner_drop_down); 

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
    */
    String[] fromKlient = new String[] {"num_id","sumka"};
    int[] toKlient = new int[] {R.id.spt1, R.id.spt2};
    scaKlient = new SimpleCursorAdapter(this, R.layout.spinner_dub, null, fromKlient, toKlient);   
    scaKlient.setDropDownViewResource(R.layout.spinner_dup_down); 

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
    
    btnAdd = (Button) findViewById(R.id.btnExcelRasxodHist);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(RasxodHistActivity.this, RasxodHistActivity.this, tvDataIns.getText().toString(), 
        			tvDataIns2.getText().toString(), 
        			tvIdPgr.getText().toString(), "Расход за период", (byte)2);
        	}
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { /*"_id",*/"id_tmc","name","kol","ch","sumka","price","prim","skidka","sum_no_skidka"};
    int[] to = new int[] {/*R.id.tv_Id_Rasxod_Hist,*/ R.id.tv_Nnom_Rasxod_Hist, R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist,R.id.tv_Skidka_Rasxod_Hist,R.id.tv_Summa_Rasxod_Hist};
    //int[] toH = new int[] {R.id.tv_Nnom_Rasxod_Hist,R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_DataIns_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelRasxodHist, R.id.btnUpdRasxodHist, (byte)4, this, R.layout.rasxod_hist_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				if (flag==1) {
    					//удаляем из чека
    					Cursor cc = MainActivity.db.getRawData ("select R.id_klient as id_klient, ifnull(R.kol*R.price,0) as sumka, ifnull(R.skidka,0) as skidka, ifnull(K.skidka,0) as kskidka, ifnull(K.sumka,0) as ksumka from rasxod R left join klient as K on R.id_klient=K._id where R._id="+id,null);
    					   if (cc.moveToFirst()) { 
    					        do {MainActivity.db.updRec("klient", cc.getInt(cc.getColumnIndex("id_klient")),
    					        		new String[] {"sumka","skidka"}, 
    					        		new double[] {cc.getDouble(cc.getColumnIndex("ksumka"))-(cc.getDouble(cc.getColumnIndex("sumka"))-cc.getDouble(cc.getColumnIndex("skidka"))),
    					        	cc.getDouble(cc.getColumnIndex("kskidka"))-cc.getDouble(cc.getColumnIndex("skidka"))});
    					        	//countT=MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), MainActivity.round(cc.getDouble(cc.getColumnIndex("kol")),6), (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "обнуление остатка id="+id, MainActivity.getIntDataTime(), 1);
    					        		//cc.getInt(cc.getColumnIndex("c"));//+ " count: tmc "+db.getAllData("tmc").getCount());
    					        } while (cc.moveToNext());
    					      };
    					
    					MainActivity.db.delRec("rasxod",id);
    					getSupportLoaderManager().getLoader(0).forceLoad();}
    			}
    		});
    lvData = (ListView) findViewById(R.id.lvRasxodHist);
    lvData.setAdapter(scAdapter);
    

    // создаем лоадер для чтения данных
    
    tmp=0;
    if( getIntent().getExtras() != null)
    {
    	
    	tvDataIns.setText(getIntent().getStringExtra("dat1"));
    	tvDataIns2.setText(getIntent().getStringExtra("dat2"));
    	tmp=Integer.parseInt(getIntent().getStringExtra("id_check"));
    }
    
    getSupportLoaderManager().initLoader(1, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.rasxod_hist_ll));
  }
/*  
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
      getSupportLoaderManager().getLoader(1).forceLoad();
      getSupportLoaderManager().getLoader(0).forceLoad();
    }
    };
    OnDateSetListener myCallBack2 = new OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth) {
      tvDataIns2.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
      getSupportLoaderManager().getLoader(1).forceLoad();
      getSupportLoaderManager().getLoader(0).forceLoad();
    }
    };*/
  
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
      if (i == 1 )  return new KlientLoader(this, MainActivity.db);
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
        		//(tvDataIns.getText().length()==0)?"":" substr(T.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")",
    			(tvDataIns.getText().length()==0)?"":" T.data_ins>=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString()))+")",
        				//(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")",
    					(tvDataIns2.getText().length()==0)?"":" T.data_ins<=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns2.getText().toString()))+")",
        				(tvIdKlient.getText().toString().equals("0")||tvIdKlient.getText().length()==0)?"":" K._id="+tvIdKlient.getText().toString()};
        String where=str[0].toString();
        //Log.d("MyLog", "dt="+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString())));
        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
     
        if (where.equals("")||where.length()==0) where=str[2].toString(); else 
        	if (!str[2].equals("")) where=where+" and "+str[2].toString();
        
        if (where.equals("")||where.length()==0) where=str[3].toString(); else 
        	if (!str[3].equals("")) where=where+" and "+str[3].toString();
     
            	 Cursor cursor = db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_ed as E on T.ed = E._id left join klient as K on T.id_klient = K._id", 
             			new String[] {"T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","round(T.kol,3)||' '||E.name as kol",
            			 "E.name as ted", "T.ed as ed","T.price as price","T.skidka as skidka","round((T.kol*T.price-T.skidka) ,2) as sum_no_skidka","T.prim as prim","TP.pgr as pgr","K.sumka as sumka","'№'||K.num_id as ch"}, 
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

	    	String []str = {
	    			//(tvDataIns.getText().length()==0)?"":" substr(data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"
	    			(tvDataIns.getText().length()==0)?"":" data_ins>=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString()))+")"
	    			,//(tvDataIns2.getText().length()==0)?"":" substr(data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")"
	    			(tvDataIns2.getText().length()==0)?"":" data_ins<=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns2.getText().toString()))+")"
	        		, (tmp==0)?"":" _id="+tmp	};
	    	String where=str[0].toString();
	        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
	        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
	        	if (!str[1].equals("")) where=where+" and "+str[1].toString();
	        if (where.equals("")||where.length()==0) where=str[2].toString(); else 
	        	if (!str[2].equals("")) where=where+" and "+str[2].toString();
	        if (!where.equals("")) where=" where "+where;
	    Cursor cursor = MainActivity.db.getRawData("select 0 _id, 0 num_id, 0 sumka from foo " +((tmp==0)?"":"where _id="+tmp) +" union all select _id, num_id, sumka from klient "+
	    //((tvDataIns.getText().length()==0)?"":" where substr(data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")")
	    where
	    , null);
	    
	      return cursor;
	    }
	     
	  }

}

