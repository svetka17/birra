package luce.birra;
import android.content.Context;
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
import luce.birra.DialogScreen.DialogListener;
 
public class DelOtchetActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  //static TextView tvIdPgr, tvIdProd, tvIdKlient; 
  //static EditText tvDataIns;
  static TextView tvDataIns,tvDataIns2, tvd1, tvd2, itogKol, itogSum, itogSkidka, itogNoSkidka;
  //Spinner spPgr, spProd, spKlient;
  //Cursor cKlient;
  //SimpleCursorAdapter scaKlient, scaProd;
  //static int tmp=0;
  //LinearLayout ll;
 //static long idd=0;
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.del_hist);
    //final DialogFragment dlg = new DialogActivity();
    itogKol = (TextView) findViewById(R.id.itogDelKol);
    itogSum = (TextView) findViewById(R.id.itogDelSumma);
    itogSkidka = (TextView) findViewById(R.id.itogDelSkidka);
    itogNoSkidka = (TextView) findViewById(R.id.itogDelNoSkidka);
    
    tvDataIns = (TextView) findViewById(R.id.tv_Data_RasHistdel);
    tvDataIns.setText(MainActivity.getStringDataTime( Integer.parseInt( String.valueOf( MainActivity.getIntDataTime() ).substring(0,6).concat("0000") ) ));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(1);
        	DialogScreen getDT = new DialogScreen(DelOtchetActivity.this,DelOtchetActivity.this,-4)
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
    tvd1 = (TextView) findViewById(R.id.tv_data_ras1del);
    tvd1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(1);
        	DialogScreen getDT = new DialogScreen(DelOtchetActivity.this,DelOtchetActivity.this,-4)
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
    tvDataIns2 = (TextView) findViewById(R.id.tv_Data_RasHist2del);
    tvDataIns2.setText(MainActivity.getStringDataTime(MainActivity.getIntDataTime()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(2);
        	DialogScreen getDT = new DialogScreen(DelOtchetActivity.this,DelOtchetActivity.this,-4)
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
    tvd2 = (TextView) findViewById(R.id.tv_data_ras2del);
    tvd2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(2);
        	DialogScreen getDT = new DialogScreen(DelOtchetActivity.this,DelOtchetActivity.this,-4)
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
    
    
    btnExit = (Button) findViewById(R.id.btnExitDelHist);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnAdd = (Button) findViewById(R.id.btnExcelDelHist);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(DelOtchetActivity.this, DelOtchetActivity.this, tvDataIns.getText().toString(), 
        			tvDataIns2.getText().toString(),"", "Удаленные за период", (byte)9);
        	}
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { /*"_id",*/"id_tmc","name","pname","kol","price","prim","skidka","sum_no_skidka","keg"};
    int[] to = new int[] {/*R.id.tv_Id_Rasxod_Hist,*/ R.id.tv_Nnom_Del_Hist, R.id.tv_Name_Del_Hist, R.id.tv_Postav_Del_Hist, R.id.tv_Kol_Del_Hist,R.id.tv_Price_Del_Hist,R.id.tv_Prim_Del_Hist,R.id.tv_Skidka_Del_Hist,R.id.tv_Summa_Del_Hist,R.id.tv_Keg_Del_Hist};
    //int[] toH = new int[] {R.id.tv_Nnom_Rasxod_Hist,R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_DataIns_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelDelHist, R.id.btnUpdDelHist, (byte)13, this, R.layout.del_hist_item, null, from, to, 0)
    		/*.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				idd=id;
    				if (flag==1) {
    					DialogScreen getYes = new DialogScreen(DelOtchetActivity.this,DelOtchetActivity.this,-5).setDialogScreenListener(new DialogListener() {
							
							@Override
							public void OnSelectedKol(double k) {
								if (k==1) {
									//удаляем из чека
			    					Cursor cc = MainActivity.db.getRawData ("select R.id_klient as id_klient, ifnull(R.kol*R.price,0) as sumka, ifnull(R.skidka,0) as skidka, ifnull(K.skidka,0) as kskidka, ifnull(K.sumka,0) as ksumka from rasxod R left join klient as K on R.id_klient=K._id where R._id="+idd,null);
			    					   if (cc.moveToFirst()) { 
			    					        do {MainActivity.db.updRec("klient", cc.getInt(cc.getColumnIndex("id_klient")),
			    					        		new String[] {"sumka","skidka"}, 
			    					        		new double[] {cc.getDouble(cc.getColumnIndex("ksumka"))-(cc.getDouble(cc.getColumnIndex("sumka"))-cc.getDouble(cc.getColumnIndex("skidka"))),
			    					        	cc.getDouble(cc.getColumnIndex("kskidka"))-cc.getDouble(cc.getColumnIndex("skidka"))});
			    					        	//countT=MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), MainActivity.round(cc.getDouble(cc.getColumnIndex("kol")),6), (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "обнуление остатка id="+id, MainActivity.getIntDataTime(), 1);
			    					        		//cc.getInt(cc.getColumnIndex("c"));//+ " count: tmc "+db.getAllData("tmc").getCount());
			    					        } while (cc.moveToNext());
			    					      };
			    					
			    					MainActivity.db.delRec("rasxod",idd);
			    					getSupportLoaderManager().getLoader(0).forceLoad();
			    					setItog();
								}
								
							}
						}); getYes.show();
    					
    					}
    			}
    		})*/;
    lvData = (ListView) findViewById(R.id.lvDelHist);
    lvData.setAdapter(scAdapter);
    

    // создаем лоадер для чтения данных
    
    if( getIntent().getExtras() != null)
    {	
    	tvDataIns.setText(getIntent().getStringExtra("dat1"));
    	tvDataIns2.setText(getIntent().getStringExtra("dat2"));
    }
    
    //getSupportLoaderManager().initLoader(1, null, this);
    //getSupportLoaderManager().initLoader(2, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    setItog();
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.del_hist_ll));
  }
  
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
      if (i == 0 ) return new RasxodDelLoader(this, MainActivity.db);
      //if (i == 1 )  return new KlientLoader(this, MainActivity.db);
      //if (i == 2 )  return new ProdLoader(this, MainActivity.db);
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
       break;
      case 2:
    	  scaProd.swapCursor(cursor);
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
   
  static class RasxodDelLoader extends CursorLoader {
 
    DB db;
    public RasxodDelLoader(Context context, DB db/*, int id*/) {
      super(context);
      this.db = db;
    }
     
    @Override
    public Cursor loadInBackground() {
    	String []str = {//(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP.pgr="+tvIdPgr.getText().toString(),
        		//(tvDataIns.getText().length()==0)?"":" substr(T.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")",
    			(tvDataIns.getText().length()==0)?"":" T.data_del>=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString()))+")",
        				//(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")",
    					(tvDataIns2.getText().length()==0)?"":" T.data_del<=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns2.getText().toString()))+")",
    							(tvDataIns.getText().length()==0)?"":" T.data_ins>=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString()))+")",
    			        				//(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")",
    			    					(tvDataIns2.getText().length()==0)?"":" T.data_ins<=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns2.getText().toString()))+")"};
        String where=str[0].toString();
        //Log.d("MyLog", "dt="+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString())));
        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
        String where1 = str[2].toString();
        if (where1.equals("")||where1.length()==0) where1=str[3].toString(); else 
        	if (!str[1].equals("")) where1=where1+" and "+str[3].toString(); 
     /*
            	 Cursor cursor = db.getQueryData("rasxod_del as T left join tmc as TP on T.id_tmc = TP._id left join tmc_ed as E on T.ed = E._id left join postav as P on T.id_post=P._id", 
             			new String[] {"T.keg as keg","P.name as pname","T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","round(T.kol,3)||' '||E.name as kol",
            			 "E.name as ted", "T.ed as ed","T.price as price","ifnull(T.skidka,0) as skidka","round((T.kol*T.price-ifnull(T.skidka,0)) ,2) as sum_no_skidka","T.prim as prim","TP.pgr as pgr","round(T.kol*T.price,3) as sumka"}, 
             			 //"TP.pgr = ?"
            			 where, null,null,null,null);
            	 */
Cursor cursor = db.getRawData (
"select keg, pname,_id,id_tmc,name,data_ins,kol,ted,ed,price,skidka,sum_no_skidka,prim,pgr,sumka  "
+ " from (select T.keg as keg,P.name as pname,T._id as _id,T.id_tmc as id_tmc,TP.name as name,T.data_ins as data_ins,round(T.kol,3)||' '||E.name as kol,E.name as ted,T.ed as ed,T.price as price,ifnull(T.skidka,0) as skidka,round((T.kol*T.price-ifnull(T.skidka,0)) ,2) as sum_no_skidka,T.prim as prim,TP.pgr as pgr,round(T.kol*T.price,3) as sumka"
+" from rasxod_del as T left join tmc as TP on T.id_tmc = TP._id left join tmc_ed as E on T.ed = E._id left join postav as P on T.id_post=P._id where "+ where
+ " union all "
+"select T.keg as keg,P.name as pname,T._id as _id,T.id_tmc as id_tmc,TP.name as name,T.data_ins as data_ins,round(T.kol,3)||' '||E.name as kol,E.name as ted,T.ed as ed,T.price as price,ifnull(T.skidka,0) as skidka,round((T.kol*T.price-ifnull(T.skidka,0)) ,2) as sum_no_skidka,T.prim as prim,TP.pgr as pgr,round(T.kol*T.price,3) as sumka"
+" from rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_ed as E on T.ed = E._id left join postav as P on T.id_post=P._id where "+ where1
+" and T.prim like 'кнопка - из меню продаж%') order by data_ins"
, null);
      return cursor;
    }
     
  }
  
  void setItog () {
	  String []str = {//(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP.pgr="+tvIdPgr.getText().toString(),
      		//(tvDataIns.getText().length()==0)?"":" substr(T.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")",
  			(tvDataIns.getText().length()==0)?"":" T.data_del>=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString()))+")",
      				//(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")",
  					(tvDataIns2.getText().length()==0)?"":" T.data_del<=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns2.getText().toString()))+")"
      				//,(tvIdKlient.getText().toString().equals("0")||tvIdKlient.getText().length()==0)?"":" K._id="+tvIdKlient.getText().toString(),
      					//	(tvIdProd.getText().toString().equals("-1")||/*tvIdProd.getText().toString().equals("0")||*/tvIdProd.getText().length()==0)?"":" T.id_tmc="+tvIdProd.getText().toString()
      								};
      String where=str[0].toString();
      //Log.d("MyLog", "dt="+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString())));
      if (where.equals("")||where.length()==0) where=str[1].toString(); else 
      	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 

	        	 Cursor cursor = MainActivity.db.getQueryData
	("rasxod_del as T left join tmc as TP on T.id_tmc = TP._id left join tmc_ed as E on T.ed = E._id left join klient as K on T.id_klient = K._id",
	         			new String[] {"round(sum(T.kol),3) as kol","round(sum(T.price*T.kol),2) as sumka","round(sum(T.skidka),2) as skidka"}, 
	         			 //"TP.pgr = ?"
	        			 where, null,null,null,null);
	        	 if (cursor.moveToFirst())  
	      		   
	     	        do {//tmpKol=cursor.getDouble(cursor.getColumnIndex("kol"));
	     	        	//tmpSum=cursor.getDouble(cursor.getColumnIndex("s"));
	     	        	itogKol.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("kol")) ) );
	     	        	itogSum.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumka")) ) );
	     	        	itogSkidka.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("skidka")) ) );
	     	        	itogNoSkidka.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumka"))-cursor.getDouble(cursor.getColumnIndex("skidka") )) );
	     	        } while (cursor.moveToNext());
	     	      
	        	        cursor.close();
	     	     
	}

}
