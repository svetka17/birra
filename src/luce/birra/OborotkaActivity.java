package luce.birra;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import luce.birra.AdapterLV.CambiareListener;
 
public class OborotkaActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapterO;//scAdapterR, scAdapterP ;
  static TextView tvIdPgr, tvDI1, tvDI2; //tvIdKlient, 
  static TextView tvDataIns, tvDataIns2;
  Spinner spPgr;//, spKlient;
  //Cursor cKlient;
  //SimpleCursorAdapter scaKlient;
  //TextView tv;
  //LinearLayout ll;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.oborotka);
    //final DialogFragment dlg = new DialogActivity();
    
    tvDataIns = (TextView) findViewById(R.id.tv_Data_Oborotka);
    //tvDataIns.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    tvDI1 = (TextView) findViewById(R.id.tv_Data_Oborotka1);
    tvDI1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    
    tvDataIns2 = (TextView) findViewById(R.id.tv_Data_Oborotka2);
    //tvDataIns2.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });
    tvDI2 = (TextView) findViewById(R.id.tv_Data_2Oborotka);
    tvDI2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });

    
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by name", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Oborotka);
    tvIdPgr = (TextView) findViewById(R.id.tv_Id_PgrOborotka);
    tvIdPgr.setText("0");
   
    // make an adapter from the cursor
    String[] fromPgr = new String[] {"name"};
    int[] toPgr = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_item, c, fromPgr, toPgr);

    sca.setDropDownViewResource(R.layout.spinner_drop_down); 
    spPgr.setAdapter(sca);

    spPgr.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

    
    btnExit = (Button) findViewById(R.id.btnExitOborotka);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    // формируем столбцы сопоставления
    //String[] fromR = new String[] {"_id", "id_tmc","name","ted",/*"kol_n",*/"sum_n","price_n",/*"kol_pri","sum_pri","price_pri",*/"kol_ras","sum_ras",/*"price_ras",*/"kol_k","sum_k","price_k"};
    //int[] toR = new int[] {R.id.tv_Id_OstatR_oborotka, R.id.tv_Nnom_Oborotka, R.id.tv_Name_Oborotka,R.id.tv_Ted_Oborotka, /*R.id.tv_Kol_Nach_Oborotka,*/R.id.tv_Sum_Nach_Oborotka,R.id.tv_Price_Nach_Oborotka,
    		/*R.id.tv_Kol_Pri_Oborotka,R.id.tv_Sum_Pri_Oborotka,R.id.tv_Price_Pri_Oborotka,*/
    		//R.id.tv_Kol_Ras_Oborotka,R.id.tv_Sum_Ras_Oborotka,/*R.id.tv_Price_Ras_Oborotka,*/
    		//R.id.tv_Kol_Kon_Oborotka,R.id.tv_Sum_Kon_Oborotka,R.id.tv_Price_Kon_Oborotka};
    //String[] fromP = new String[] {"_id", "kol_pri","sum_pri"};
    //int[] toP = new int[] {R.id.tv_Id_OstatP_oborotka, R.id.tv_Kol_Pri_Oborotka,R.id.tv_Sum_Pri_Oborotka};
    		String[] fromO = new String[] {/*"_id",*/ "id_tmc","kkeg","name","ted",/*"kol_n","sum_n","price_n",*/"kol_pri","sum_pri","price_pri","kol_ras","sum_ras","price_ras","kol_k","sum_k","price_k"};
    	    int[] toO = new int[] {/*R.id.tv_Id_OstatR_oborotka,*/ R.id.tv_Nnom_Oborotka, R.id.tv_Keg_Oborotka, R.id.tv_Name_Oborotka,R.id.tv_Ted_Oborotka, /*R.id.tv_Kol_Nach_Oborotka,R.id.tv_Sum_Nach_Oborotka,R.id.tv_Price_Nach_Oborotka,*/
    	    		R.id.tv_Kol_Pri_Oborotka,R.id.tv_Sum_Pri_Oborotka,R.id.tv_Price_Pri_Oborotka,
    	    		R.id.tv_Kol_Ras_Oborotka,R.id.tv_Sum_Ras_Oborotka,R.id.tv_Price_Ras_Oborotka,
    	    		R.id.tv_Kol_Kon_Oborotka,R.id.tv_Sum_Kon_Oborotka,R.id.tv_Price_Kon_Oborotka};
    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    	    /*int[] toH = new int[] {R.id.tv_Nnom_Oborotka, R.id.tv_Name_Oborotka,R.id.tv_Ted_Oborotka, R.id.tv_Kol_Nach_Oborotka,R.id.tv_Sum_Nach_Oborotka,R.id.tv_Price_Nach_Oborotka,
    				R.id.tv_Kol_Pri_Oborotka,R.id.tv_Sum_Pri_Oborotka,R.id.tv_Price_Pri_Oborotka,
    				R.id.tv_Kol_Ras_Oborotka,R.id.tv_Sum_Ras_Oborotka,R.id.tv_Price_Ras_Oborotka,
    				R.id.tv_Kol_Kon_Oborotka,R.id.tv_Sum_Kon_Oborotka,R.id.tv_Price_Kon_Oborotka};
*/
    	    scAdapterO = new AdapterLV(R.id.btnDelOborotka, R.id.btnUpdOborotka, 0, this, R.layout.oborotka_item, null, fromO, toO, 0)
    	    .setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(int flag, long id) {
    				
    				if (flag==1 || flag==2) {
    					
    					Cursor cc = MainActivity.db.getRawData ("select O._id as id, O.keg as keg, O.id_tmc as id_tmc, T.name as name_tmc, O.kol as kol, O.ed as ed, E.name as name_ed, O.id_post as id_post, P.name as name_post from ostat as O left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join postav as P on O.id_post=P._id where CAST(O.id_tmc||O.keg||O.id_post||O.ed as integer)="+id,null);
    					Intent intent = new Intent(OborotkaActivity.this, DvigActivity.class);
    					if (cc.moveToFirst()) { 
    					        do {
    					        	
    			                    intent.putExtra("dvigTmc", String.valueOf(cc.getInt(cc.getColumnIndex("id_tmc"))) );
    			                    intent.putExtra("dvigKeg", String.valueOf(cc.getInt(cc.getColumnIndex("keg"))) );
    			                    //Log.d("MyLog", "dvigTmc "+cc.getInt(cc.getColumnIndex("id_tmc")));
    			                    intent.putExtra("dvigNameTmc", cc.getString(cc.getColumnIndex("name_tmc")));
    			                    //Log.d("MyLog", "dvigNameTmc "+cc.getString(cc.getColumnIndex("name_tmc")));
    			                    intent.putExtra("dvigEd", String.valueOf(cc.getInt(cc.getColumnIndex("ed"))));
    			                    //Log.d("MyLog", "dvigEd "+cc.getInt(cc.getColumnIndex("ed")));
    			                    intent.putExtra("dvigNameEd", cc.getString(cc.getColumnIndex("name_ed")));
    			                    //Log.d("MyLog", "dvigNameEd "+cc.getString(cc.getColumnIndex("name_ed")));
    			                    
    			                    intent.putExtra("dvigPost", String.valueOf(cc.getInt(cc.getColumnIndex("id_post"))));
    			                    //Log.d("MyLog", "dvigPost "+cc.getInt(cc.getColumnIndex("id_post")));
    			                    intent.putExtra("dvigNamePost", cc.getString(cc.getColumnIndex("name_post")));
    			                    //Log.d("MyLog", "dvigNamePost "+cc.getString(cc.getColumnIndex("name_post")));
    			                    intent.putExtra("dvigKol", String.valueOf(cc.getDouble(cc.getColumnIndex("kol"))));
    			                    //Log.d("MyLog", "dvigKol "+cc.getDouble(cc.getColumnIndex("kol")));
    			                    intent.putExtra("dvigID", String.valueOf(cc.getInt(cc.getColumnIndex("id"))));
    			                    //Log.d("MyLog", "dvigID "+cc.getInt(cc.getColumnIndex("id")));
    			        
    			                    } 
    					        while (cc.moveToNext());
    					      };
    					      //Log.d("MyLog", "id="+id);
    					      startActivity(intent);

    				}
    			}
    		});
    lvData = (ListView) findViewById(R.id.lvOborotka);
    lvData.setAdapter(scAdapterO);
    
    //scAdapterP = new AdapterLV(R.id.btnDelOborotka, R.id.btnUpdOborotka, (byte)10, this, R.layout.oborotka_item, null, fromP, toP, 0);
    //lvData.setAdapter(scAdapterP);
     
    btnAdd = (Button) findViewById(R.id.btnAddOborotka);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(OborotkaActivity.this, OborotkaActivity.this, /*tvDataIns.getText().toString()*/"0", /*tvDataIns2.getText().toString()*/"0", 
        			//Calendar.getInstance().get(Calendar.DATE)+"."+Calendar.getInstance().get(Calendar.MONTH)+"."+Calendar.getInstance().get(Calendar.YEAR), 
        			tvIdPgr.getText().toString(), "Оборотная ведомость", 1);
        	}
      });
    
    // создаем лоадер для чтения данных
    //getSupportLoaderManager().initLoader(1, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.oborotka_ll));
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
	  return new OborotkaLoader(this, MainActivity.db);
	  //if (i == 0 ) return new RLoader(this, MainActivity.db);
      //if (i == 1 ) return new PLoader(this, MainActivity.db);
      //return null;
  }
  
  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	  switch(loader.getId())
      {
      case 0:
    	  scAdapterO.swapCursor(cursor);
    	  //scAdapterR.swapCursor(cursor);
       break;
      case 1:
    	  //scAdapterP.swapCursor(cursor);
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
   
  static class OborotkaLoader extends CursorLoader {
 
    DB db;
    public OborotkaLoader(Context context, DB db) {
      super(context);
      this.db = db;
    }
     
    @Override
    public Cursor loadInBackground() {
    	String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP._id="+tvIdPgr.getText().toString()
    			,/*(tvDataIns.getText().length()==0)?"":" substr(P.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"+
        				"and substr(R.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"*/
    			(tvDataIns.getText().length()==0)?"":"where data_ins>="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+"0000 and data_ins<="+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+"5959",
    					(tvDataIns.getText().length()==0)?"":"where data_ins<="+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+"5959"
    	};
        String where=str[0].toString();
        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
        //if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 

        //{ "id_tmc","name","kol_n","sum_n","price_n","kol_pri","sum_pri","price_pri","kol_ras","sum_ras","price_ras","kol_k","sum_k","price_k"}    
            	 Cursor cursor = db.getQueryData( 
            			 /*
             			"(select id_tmc, keg, id_post, ed, ifnull(sum(round(kol,3)),0) as kol, ifnull(sum(round(sumo,2)),0) as sumka from "+ 
             			"("+
             			"select id_tmc, keg, id_post, ed, -ifnull(round(kol,3),0) as kol, -ifnull(round(kol,3)*round(price,2)-round(ifnull(skidka,0),2),0) as sumo from rasxod "+str[2].toString()+
             			" union all "+
             			"select id_tmc, keg, id_post, ed, ifnull(round(kol,3),0) as kol, ifnull(round(kol,3)*round(price,2),0) as sumo from prixod "+str[2].toString()+
             			") group by id_tmc, keg, id_post, ed)"
             			*/
            			 //"ostat as O left join rasxod as R on O.id_tmc=R.id_tmc and O.id_post=R.id_post and O.ed=R.ed "
             			//+ " left join prixod as P on O.id_tmc=P.id_tmc and O.id_post=P.id_post and O.ed=P.ed"
             			//+ " left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id",
            			 "(select CAST(id_tmc||keg||id_post||ed as integer) as _id, id_tmc, keg, id_post, ed, ifnull(sum(round(kol,3)),0) as kol, ifnull(sum(round(sumo,2)),0) as sumka from "+ 
              			"("+
              			"select id_tmc, keg, id_post, ed, -ifnull(round(kol,3),0) as kol, -ifnull(round(kol,3)*round(price,2)-round(ifnull(skidka,0),2),0) as sumo from rasxod "+str[2].toString()+
              			" union all "+
              			"select id_tmc, keg, id_post, ed, ifnull(round(kol,3),0) as kol, ifnull(round(kol,3)*round(price,2),0) as sumo from prixod "+str[2].toString()+
              			") group by id_tmc, keg, id_post, ed)" +
            			 " as O " +
            			 "left join (select id_tmc, keg, id_post, ed, ifnull(sum(round(kol,3)),0) as sumkr, ifnull(sum(round(kol,3)*round(price,2)-round(ifnull(skidka,0),2)),0) as sumsr from rasxod " + str[1].toString() + 
            			 " group by id_tmc, keg, id_post, ed) as R on O.id_tmc=R.id_tmc and O.keg=R.keg and O.id_post=R.id_post and O.ed=R.ed "
              			+ " left join (select id_tmc, keg, id_post, ed, ifnull(sum(round(kol,3)),0) as sumkp, ifnull(sum(round(kol,3)*round(price,2)),0) as sumsp from prixod " + str[1].toString() +
              			" group by id_tmc, keg, id_post, ed) as P on O.id_tmc=P.id_tmc and O.keg=P.keg and O.id_post=P.id_post and O.ed=P.ed"
              			+ " left join ostat as K on O.id_tmc=K.id_tmc and O.keg=K.keg and O.id_post=K.id_post and O.ed=K.ed " +
              			" left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id left join postav as POS on O.id_post=POS._id",
            			 
              			new String[] {"O._id as _id","O.keg as keg", "O.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as kkeg",
            			"O.id_tmc as id_tmc","T.name||case POS.name when '-нет-' then '' else ' '||POS.name end as name","E.name as ted",
            			//"0 as kol_n","0 as sum_n","0 as price_n","sum(round(P.kol,3)) as kol_pri","sum(round(P.kol,3)*round(P.price,2)) sum_pri","0 as price_pri","sum(round(R.kol,3)) kol_ras","sum(round(R.kol,3)*round(R.price,2)) as sum_ras","0 as price_ras",
            			"0 as kol_n","0 as sum_n","0 as price_n","sumkp as kol_pri","sumsp sum_pri","CASE sumkp WHEN 0 then 0 ELSE round(sumsp/sumkp,2) END as price_pri","sumkr kol_ras","sumsr as sum_ras","CASE sumkr WHEN 0 then 0 ELSE round(sumsr/sumkr,2) END as price_ras",
            			"O.kol kol_k","(CASE O.kol WHEN 0 then 0 ELSE round(O.sumka/O.kol,2) END)*O.kol as sum_k",
            			//"ifnull(O.sumka,0) as sum_k",
            			"CASE O.kol WHEN 0 then 0 ELSE round(O.sumka/O.kol,2) END as price_k"		 
            			 //"T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","T.kol||' '||E.name as kol",
            			 //"E.name as ted", "T.ed as ed","'Цена:'||T.price as price","T.prim as prim","TP.pgr as pgr","K.sumka as sumka","'ЧЕК№'||K._id as ch"
            			 }, 
             			 //"TP.pgr = ?"
            			 where, null,
            			 null//"O._id, O.id_tmc, O.kol"
            			 ,null,"O.id_tmc, O.keg");

      return cursor;
    }
     
  }
  /*
  static class RLoader extends CursorLoader {
	  
	    DB db;
	    public RLoader(Context context, DB db) {
	      super(context);
	      this.db = db;
	    }
	     
	    @Override
	    public Cursor loadInBackground() {
	    	String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP._id="+tvIdPgr.getText().toString(),
	        		(tvDataIns.getText().length()==0)?"":" substr(R.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"};
	        String where=str[0].toString();
	        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
	        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
	        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
	 //{ "id_tmc","name","kol_n","sum_n","price_n","kol_pri","sum_pri","price_pri","kol_ras","sum_ras","price_ras","kol_k","sum_k","price_k"}    
	            	 Cursor cursor = db.getQueryData(
	            			 //"rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_ed as E on T.ed = E._id left join klient as K on T.id_klient = K._id", 
	             			"ostat as O left join rasxod as R on O.id_tmc=R.id_tmc and O.id_post=R.id_post and O.ed=R.ed "
	             			+ " left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id",
	            			 new String[] {"O._id as _id",
	            			"O.id_tmc as id_tmc","T.name as name","E.name as ted",
	            			"0 as kol_n","0 as sum_n","0 as price_n", "sum(round(R.kol,3)) kol_ras","sum(round(R.kol,3)*round(R.price,2)) as sum_ras","0 as price_ras",
	            			"O.kol kol_k","0 as sum_k","0 as price_k"		 
	            			 //"T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","T.kol||' '||E.name as kol",
	            			 //"E.name as ted", "T.ed as ed","'Цена:'||T.price as price","T.prim as prim","TP.pgr as pgr","K.sumka as sumka","'ЧЕК№'||K._id as ch"
	            			 }, 
	             			 //"TP.pgr = ?"
	            			 where, null,
	            			 "O._id, O.id_tmc, O.kol"
	            			 ,null,null);

	      return cursor;
	    }
	     
	  }
  
  static class PLoader extends CursorLoader {
	  
	    DB db;
	    public PLoader(Context context, DB db) {
	      super(context);
	      this.db = db;
	    }
	     
	    @Override
	    public Cursor loadInBackground() {
	    	String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP._id="+tvIdPgr.getText().toString(),
	        		(tvDataIns.getText().length()==0)?"":" substr(P.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"};
	        String where=str[0].toString();
	        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
	        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
	        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
	 //{ "id_tmc","name","kol_n","sum_n","price_n","kol_pri","sum_pri","price_pri","kol_ras","sum_ras","price_ras","kol_k","sum_k","price_k"}    
	            	 Cursor cursor = db.getQueryData(
	            			 //"rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_ed as E on T.ed = E._id left join klient as K on T.id_klient = K._id", 
	             			"ostat as O "
	             			+ " left join prixod as P on O.id_tmc=P.id_tmc and O.id_post=P.id_post and O.ed=P.ed"
	             			+ " left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id",
	            			 new String[] {"O._id as _id",
	            			"sum(round(P.kol,3)) as kol_pri","sum(round(P.kol,3)*round(P.price,2)) sum_pri","0 as price_pri"		 
	            			 //"T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","T.kol||' '||E.name as kol",
	            			 //"E.name as ted", "T.ed as ed","'Цена:'||T.price as price","T.prim as prim","TP.pgr as pgr","K.sumka as sumka","'ЧЕК№'||K._id as ch"
	            			 }, 
	             			 //"TP.pgr = ?"
	            			 where, null,
	            			 "O._id, O.id_tmc, O.kol"
	            			 ,null,null);

	      return cursor;
	    }
	     
	  }
*/
}

