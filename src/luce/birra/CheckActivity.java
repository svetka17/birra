package luce.birra;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import luce.birra.AdapterLV.CambiareListener;
import luce.birra.DialogScreen.DialogListener;
 
public class CheckActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  //static TextView //tvIdPgr, 
  //tvIdKlient; 
  //static EditText tvDataIns;
  static TextView tvDataIns,tvDataIns2, tvd1, tvd2, sumCh, sumSkid, sumWithSkid;
  //Spinner //spPgr, 
  //spKlient;
  //Cursor cKlient;
  //SimpleCursorAdapter scaKlient;
  static long idd=0;
  //LinearLayout ll;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.check_klient);
    //final DialogFragment dlg = new DialogActivity();
    
    sumCh = (TextView) findViewById(R.id.itogCheckSumma);
    sumSkid = (TextView) findViewById(R.id.itogCheckSkidka);
    sumWithSkid = (TextView) findViewById(R.id.itogCheckSummaSkidka);
    
    tvDataIns = (TextView) findViewById(R.id.tv_Data_check11);
    tvDataIns.setText(MainActivity.getStringDataTime( Integer.parseInt( String.valueOf( MainActivity.getIntDataTime() ).substring(0,6).concat("0000") ) ));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(1);
        	DialogScreen getDT = new DialogScreen(CheckActivity.this,CheckActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						//Log.d("MyLog", "dat1 int="+((int)(k))+" round=="+MainActivity.round(k,2)+" "+k );
						tvDataIns.setText(MainActivity.getStringDataTime((int)k));
						//getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					      setItog();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvd1 = (TextView) findViewById(R.id.tv_data_check1);
    tvd1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(1);
        	DialogScreen getDT = new DialogScreen(CheckActivity.this,CheckActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns.setText(MainActivity.getStringDataTime((int)k));
						//getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					      setItog();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvDataIns2 = (TextView) findViewById(R.id.tv_Data_check22);
    tvDataIns2.setText(MainActivity.getStringDataTime(MainActivity.getIntDataTime()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(2);
        	DialogScreen getDT = new DialogScreen(CheckActivity.this,CheckActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns2.setText(MainActivity.getStringDataTime((int)k));
						//getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					      setItog();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvd2 = (TextView) findViewById(R.id.tv_data_check2);
    tvd2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(2);
        	DialogScreen getDT = new DialogScreen(CheckActivity.this,CheckActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns2.setText(MainActivity.getStringDataTime((int)k));
						//getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					      setItog();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    /*Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by _id", null);
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
    */
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
*/
    /*btnAdd = (Button) findViewById(R.id.btnAddPrixodHist);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//Intent intent = new Intent(PgrActivity.this, PrihodA.class);
			//startActivity(intent);
        }
      });*/
    
    btnExit = (Button) findViewById(R.id.btnExitCheck);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnAdd = (Button) findViewById(R.id.btnExcelCheck);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(CheckActivity.this, CheckActivity.this, tvDataIns.getText().toString(), 
        			tvDataIns2.getText().toString(),"", "Чеки за период", (byte)8);
        	}
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { "_id","num_id","sumka","skidka","no_skidka","name","prim","karta"};
    int[] to = new int[] {R.id.tv_id_check_klient, R.id.tv_num_id_check_klient, R.id.tv_summa_check_klient,R.id.tv_skidka_check_klient,R.id.tv_summa_skidka_check_klient,R.id.tv_name_check_klient,R.id.tv_prim_check_klient,R.id.tv_karta_check_klient};
    //int[] toH = new int[] {R.id.tv_Nnom_Rasxod_Hist,R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_DataIns_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelCheckKlient, R.id.btnUpdCheck_klient, (byte)6, this, R.layout.check_klient_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				idd=id;
    				if (flag==1) {
    					DialogScreen getYes = new DialogScreen(CheckActivity.this,CheckActivity.this,-5).setDialogScreenListener(new DialogListener() {
							
							@Override
							public void OnSelectedKol(double k) {
								if (k==1) {
									int count=MainActivity.db.delRecs("rasxod","id_klient="+idd);
			    					MainActivity.db.delRec("klient",idd);
			    					getSupportLoaderManager().getLoader(0).forceLoad();
			    					setItog();
								}
								
							}
						}); getYes.show();
    					
    					
    					
    					}
    				if (flag==2) {
    					//Log.d("MyLog", "view id="+id);
    					Intent intent = new Intent(CheckActivity.this, RasxodHistActivity.class);
    					intent.putExtra("dat1", tvDataIns.getText().toString() );
    					intent.putExtra("dat2", tvDataIns2.getText().toString() );
                        intent.putExtra("id_check", String.valueOf(id));
    					   startActivity(intent);
    					//getSupportLoaderManager().getLoader(0).forceLoad();
    					}
    			}
    		});
    lvData = (ListView) findViewById(R.id.lvCheck);
    lvData.setAdapter(scAdapter);
     
    // создаем лоадер для чтения данных
    //getSupportLoaderManager().initLoader(1, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    setItog();
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.check_klient_ll));
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
    //getSupportLoaderManager().getLoader(1).forceLoad();
    getSupportLoaderManager().getLoader(0).forceLoad();
    setItog();
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
	  switch(loader.getId())
      {
      case 0:
    	  scAdapter.swapCursor(cursor);
       break;
      /*case 1:
    	  scaKlient.swapCursor(cursor);
       break;*/
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
    	String []str = {//(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP.pgr="+tvIdPgr.getText().toString(),
        		//(tvDataIns.getText().length()==0)?"":" substr(T.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")",
    			(tvDataIns.getText().length()==0)?"":" K.data_ins>=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString()))+")",
        				//(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")",
    					(tvDataIns2.getText().length()==0)?"":" K.data_ins<=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns2.getText().toString()))+")"//,
        				//(tvIdKlient.getText().toString().equals("0")||tvIdKlient.getText().length()==0)?"":" K._id="+tvIdKlient.getText().toString()
        						};
        String where=str[0].toString();
        //Log.d("MyLog", "dt="+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString())));
        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
     
        /*if (where.equals("")||where.length()==0) where=str[2].toString(); else 
        	if (!str[2].equals("")) where=where+" and "+str[2].toString();
        
        if (where.equals("")||where.length()==0) where=str[3].toString(); else 
        	if (!str[3].equals("")) where=where+" and "+str[3].toString();
     */
            	 Cursor cursor = db.getQueryData("klient as K left join karta_klient KK on K.karta = KK._id", 
             			new String[] {"K._id as _id","K.num_id as num_id","K.sumka as sumka", "K.skidka as skidka","K.sumka-K.skidka as no_skidka", "K.name as name","K.prim as prim","K.data_ins as data_ins","KK._id||' '||KK.name||'-'||KK.sconto_per as karta"}, 
             			 //"TP.pgr = ?"
            			 where, null,null,null,null);

      return cursor;
    }
     
  }
  
  void setItog () {
	  String []str = {(tvDataIns.getText().length()==0)?"":" K.data_ins>=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString()))+")",
      				//(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")",
  					(tvDataIns2.getText().length()==0)?"":" K.data_ins<=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns2.getText().toString()))+")"//,
      				//(tvIdKlient.getText().toString().equals("0")||tvIdKlient.getText().length()==0)?"":" K._id="+tvIdKlient.getText().toString()
      						};
      String where=str[0].toString();
      //Log.d("MyLog", "dt="+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString())));
      if (where.equals("")||where.length()==0) where=str[1].toString(); else 
      	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
      
	        	 Cursor cursor = MainActivity.db.getQueryData("klient as K left join karta_klient KK on K.karta = KK._id", 
	             			new String[] {"round(sum(K.sumka),2) as sumka", "round(sum(K.skidka),2) as skidka","round(sum(K.sumka-K.skidka),2) as no_skidka"}, 
	             			 //"TP.pgr = ?"
	            			 where, null,null,null,null);
	        	 
	        	 if (cursor.moveToFirst())  
	      		   
	     	        do {
	     	        	//itogKol.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("kol")) ) );
	     	        	sumCh.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumka")) ) );
	     	        	sumSkid.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("skidka")) ) );
	     	        	sumWithSkid.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("no_skidka")) ) );
	     	        } while (cursor.moveToNext());
	     	      
	        	        cursor.close();
	     	     
	}

}

