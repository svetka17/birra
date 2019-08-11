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
import luce.birra.AdapterLV.CambiareListener;
import luce.birra.DialogScreen.DialogListener;
 
public class DvigActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  static TextView tvIdTmc, tvKeg, tvIdPost, tvIdEd; 
  TextView nameTmc, namePost, nameEd, tvKol, tvID; 
  //static EditText tvDataIns;
  static TextView tvDataIns,tvDataIns2, tvd1, tvd2;
  //Spinner spPgr, spKlient;
  //Cursor cKlient;
  //SimpleCursorAdapter scaKlient;
  //static int tmp=0;
  //LinearLayout ll;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dvig);

    tvIdTmc = (TextView) findViewById(R.id.tv_id_tmc_dvig);
    tvKeg = (TextView) findViewById(R.id.tv_keg_dvig);
    nameTmc = (TextView) findViewById(R.id.tv_name_tmc_dvig);
    tvIdPost = (TextView) findViewById(R.id.tv_post_dvig);
    namePost = (TextView) findViewById(R.id.tv_name_post_dvig);
    tvIdEd = (TextView) findViewById(R.id.tv_ed_dvig);
    nameEd = (TextView) findViewById(R.id.tv_name_ed_dvig);
    tvID = (TextView) findViewById(R.id.tv_ID_Dvig);
    tvKol = (TextView) findViewById(R.id.tv_kol_dvig);
    
    tvDataIns = (TextView) findViewById(R.id.te_Data_Ras1Dvig);
    //tvDataIns.setText(MainActivity.getStringDataTime( Integer.parseInt( String.valueOf( MainActivity.getIntDataTime() ).substring(0,6).concat("0000") ) ));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(1);
        	DialogScreen getDT = new DialogScreen(DvigActivity.this,DvigActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns.setText(MainActivity.getStringDataTime((int)k));
						//getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvd1 = (TextView) findViewById(R.id.tv_data_ras1Dvig);
    tvd1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(1);
        	DialogScreen getDT = new DialogScreen(DvigActivity.this,DvigActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns.setText(MainActivity.getStringDataTime((int)k));
						//getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvDataIns2 = (TextView) findViewById(R.id.te_Data_Ras2Dvig);
    //tvDataIns2.setText(MainActivity.getStringDataTime(MainActivity.getIntDataTime()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(2);
        	DialogScreen getDT = new DialogScreen(DvigActivity.this,DvigActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns2.setText(MainActivity.getStringDataTime((int)k));
						//getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    tvd2 = (TextView) findViewById(R.id.tv_data_ras2Dvig);
    tvd2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showDialog(2);
        	DialogScreen getDT = new DialogScreen(DvigActivity.this,DvigActivity.this,-4)
			 .setDialogScreenListener(new DialogListener() {
				@Override
				public void OnSelectedKol(double k) {
					if (k!=0) 
					{
						tvDataIns2.setText(MainActivity.getStringDataTime((int)k));
						//getSupportLoaderManager().getLoader(1).forceLoad();
					      getSupportLoaderManager().getLoader(0).forceLoad();
					}
					//else dialogNumCancel(R.id.cb_Kol_Ostat);					
				}
			}) ;getDT.show();
        }
      });
    
    
    btnExit = (Button) findViewById(R.id.btnExitDvig);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnAdd = (Button) findViewById(R.id.btnExcelDvig);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	/*MainActivity.excel(DvigActivity.this, DvigActivity.this, tvDataIns.getText().toString(), 
        			tvDataIns2.getText().toString(), 
        			tvIdPgr.getText().toString(), "Расход за период", (byte)2);
        	*/
        	}
      });
    
    // формируем столбцы сопоставления
    String[] from = new String[] { /*"_id",*/"_id","dvig","kol","price","sumka","prim"};
    int[] to = new int[] {R.id.tv_IdDvig, R.id.tv_dvig_itemDvig, R.id.tv_kol_Dvig,R.id.tv_Price_Dvig,R.id.tv_Summa_Dvig,R.id.tv_Prim_Dvig};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelDvig, R.id.btnUpdDvig, 12, this, R.layout.dvig_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(int flag, long id) {
    				if (flag==1) {
    					/*
    					Cursor cc = MainActivity.db.getRawData ("select R.id_klient as id_klient, ifnull(R.kol*R.price,0) as sumka, ifnull(R.skidka,0) as skidka, ifnull(K.skidka,0) as kskidka, ifnull(K.sumka,0) as ksumka from rasxod R left join klient as K on R.id_klient=K._id where R._id="+id,null);
    					   if (cc.moveToFirst()) { 
    					        do {MainActivity.db.updRec("klient", cc.getInt(cc.getColumnIndex("id_klient")),
    					        		new String[] {"sumka","skidka"}, 
    					        		new float[] {cc.getFloat(cc.getColumnIndex("ksumka"))-(cc.getFloat(cc.getColumnIndex("sumka"))-cc.getFloat(cc.getColumnIndex("skidka"))),
    					        	cc.getFloat(cc.getColumnIndex("kskidka"))-cc.getFloat(cc.getColumnIndex("skidka"))});
    					        	//countT=MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), MainActivity.round(cc.getFloat(cc.getColumnIndex("kol")),6), (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "обнуление остатка id="+id, MainActivity.getIntDataTime(), 1);
    					        		//cc.getInt(cc.getColumnIndex("c"));//+ " count: tmc "+db.getAllData("tmc").getCount());
    					        } while (cc.moveToNext());
    					      };
    					
    					MainActivity.db.delRec("rasxod",id);
    					getSupportLoaderManager().getLoader(0).forceLoad();*/
    					}
    			}
    		});
    

    if( getIntent().getExtras() != null)
    {
    	
    	//tvDataIns.setText(getIntent().getStringExtra("dat1"));
    	//tvDataIns2.setText(getIntent().getStringExtra("dat2"));
    	//tmp=Integer.parseInt(getIntent().getStringExtra("id_check"));

    	tvIdTmc.setText(getIntent().getStringExtra("dvigTmc"));
    	tvKeg.setText(getIntent().getStringExtra("dvigKeg"));
    	//Log.d("MyLog", "start create4 "+tvIdTmc.getText()+" "+getIntent().getStringExtra("dvigTmc"));
    	nameTmc.setText(getIntent().getStringExtra("dvigNameTmc"));
    	//Log.d("MyLog", "start create5 "+nameTmc.getText());
    	tvIdPost.setText(getIntent().getStringExtra("dvigPost"));
    	//Log.d("MyLog", "start create6 "+tvIdPost.getText());
    	namePost.setText(getIntent().getStringExtra("dvigNamePost"));
    	//Log.d("MyLog", "start create7 "+namePost.getText());
    	tvIdEd.setText(getIntent().getStringExtra("dvigEd"));
    	//Log.d("MyLog", "start create8 "+tvIdEd.getText());
    	nameEd.setText(getIntent().getStringExtra("dvigNameEd"));
    	//Log.d("MyLog", "start create9 "+nameEd.getText());
    	tvKol.setText(String.valueOf( MainActivity.StrToFloat(getIntent().getStringExtra("dvigKol")) ) );
    	//Log.d("MyLog", "start create10 "+tvKol.getText());
    	tvID.setText(getIntent().getStringExtra("dvigID"));
    	//Log.d("MyLog", "start create11 "+tvID.getText());
    }
    
    lvData = (ListView) findViewById(R.id.lvDvig);
    lvData.setAdapter(scAdapter);
    
    //getSupportLoaderManager().initLoader(1, null, this);
    getSupportLoaderManager().initLoader(0, null, this);

    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.dvig_ll));
  }
  
  @Override
  protected void onRestart() {
    super.onRestart();
    getSupportLoaderManager().getLoader(0).forceLoad();
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
 
  @Override
  
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
     // Log.d("Loader<Cursor> onCreateLoader", String.valueOf(i));
      if (i == 0 ) return new DvigLoader(this, MainActivity.db);
      //if (i == 1 )  return new KlientLoader(this, MainActivity.db);
      return null;
  }
  
  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	  switch(loader.getId())
      {
      case 0:
    	  scAdapter.swapCursor(cursor);
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
   
  static class DvigLoader extends CursorLoader {
 
    DB db;
    public DvigLoader(Context context, DB db/*, int id*/) {
      super(context);
      this.db = db;
    }
     
    @Override
    public Cursor loadInBackground() {
    	
    	String []str = {(tvDataIns.getText().length()==0)?"":" R.data_ins>=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString()))+")",
        				//(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")",
    					(tvDataIns2.getText().length()==0)?"":" R.data_ins<=trim("+String.valueOf(MainActivity.getIntDataTime(tvDataIns2.getText().toString()))+")"};
        String where=" where R.id_tmc="+MainActivity.StrToFloat(tvIdTmc.getText().toString())+" and R.id_post="+MainActivity.StrToFloat(tvIdPost.getText().toString())+" and R.ed="+MainActivity.StrToFloat(tvIdEd.getText().toString())+" and R.keg="+MainActivity.StrToFloat(tvKeg.getText().toString());
        //Log.d("MyLog", "dt="+String.valueOf(MainActivity.getIntDataTime(tvDataIns.getText().toString())));
        if (str[0].length()!=0) where=where+" and "+str[0].toString(); 
     
        if (str[1].length()!=0) where=where+" and "+str[1].toString();
        //Log.d("MyLog", "where="+where);
            	 Cursor cursor = db.getRawData
            			 (" select _id, dvig, id_tmc, id_post, ed, kol, price, sumka, prim, data_ins from ( "+
               			" select R._id as _id, 'расход' as dvig, R.id_tmc as id_tmc, R.id_post as id_post, R.ed as ed, round(R.kol,3) as kol, round(R.price,2) as price, round(R.kol,3)*round(R.price,2)-round(R.skidka,2) as sumka, R.prim||' скидка по позиции '||round(R.skidka,2)||' №'||K.num_id||' '||K.prim||' общая сумма чека '||round(K.sumka,2) as prim, R.data_ins as data_ins from rasxod as R left join klient as K on R.id_klient = K._id" + where + 
              			" union all select R._id as _id, 'приход' as dvig, R.id_tmc as id_tmc, R.id_post id_post, R.ed as ed, round(R.kol,3) as kol, round(R.price,2) as price, round(R.kol,3)*round(R.price,2) as sumka, R.prim as prim, R.data_ins as data_ins from prixod as R " + where +
                			" ) order by data_ins", null);
            	 
            			/* getQueryData( " select id_tmc, id_post, ed, kol, sumka, prim, data_ins from ( "
              			" select id_tmc, id_post, ed, round(kol,3) as kol, round(kol,3)*round(price,2)-round(skidka,2) as sumka, prim as prim, data_ins as data_ins from rasxod " + where + 
             			" union all select id_tmc, id_post, ed, round(kol,3) as kol, round(kol,3)*round(price,2) as sumka, prim as prim, data_ins as data_ins from prixod " + where +
               			" ) order by data_ins"
               			+ " left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id",
             			 new String[] {"O._id as _id",
             			"O.id_tmc as id_tmc","T.name as name","E.name as ted",
             			//"0 as kol_n","0 as sum_n","0 as price_n","sum(round(P.kol,3)) as kol_pri","sum(round(P.kol,3)*round(P.price,2)) sum_pri","0 as price_pri","sum(round(R.kol,3)) kol_ras","sum(round(R.kol,3)*round(R.price,2)) as sum_ras","0 as price_ras",
             			"0 as kol_n","0 as sum_n","0 as price_n","sumkp as kol_pri","sumsp sum_pri","0 as price_pri","sumkr kol_ras","sumsr as sum_ras","0 as price_ras",
             			"O.kol kol_k","O.sumka as sum_k","0 as price_k"		 
             			 //"T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","T.kol||' '||E.name as kol",
             			 //"E.name as ted", "T.ed as ed","'Цена:'||T.price as price","T.prim as prim","TP.pgr as pgr","K.sumka as sumka","'ЧЕК№'||K._id as ch"
             			 }, 
              			 //"TP.pgr = ?"
             			 where, null,
             			 null//"O._id, O.id_tmc, O.kol"
             			 ,null,null);*/

      return cursor;
    }
     
  }
  
  

}

