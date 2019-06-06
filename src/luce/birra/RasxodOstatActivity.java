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
import android.util.TypedValue;
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
 
public class RasxodOstatActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  static TextView tvIdPgr, itogKol, itogSum, itogSkidka, itogNoSkidka, itogNalRO,itogNoNalRO, itogBrak, itogMove;//, tvIdKlient; 
  //static EditText tvDataIns;
  static TextView tvDataIns,tvDataIns2, tvd1, tvd2;
  Spinner spPgr;//, spKlient;
  //static CheckBox cbOst;
  //Cursor cKlient;
  //SimpleCursorAdapter scaKlient;
  
  //LinearLayout ll;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rasxod_ostat);
/*    cbOst = (CheckBox) findViewById(R.id.cb_Ost_RasOst);
    cbOst.setChecked(true);
    cbOst.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			getSupportLoaderManager().getLoader(0).forceLoad();
        	setItog();
		}
	});*/
    itogKol = (TextView) findViewById(R.id.itogRasxodOstKol);
    itogSum = (TextView) findViewById(R.id.itogRasxodOstSumma);
    itogSkidka = (TextView) findViewById(R.id.itogRasxodOstSkidka);
    itogNoSkidka = (TextView) findViewById(R.id.itogRasxodOstNoSkidka);
    itogNoNalRO = (TextView) findViewById(R.id.itogNoNalRO);
    itogNalRO = (TextView) findViewById(R.id.itogNalRO);
    itogBrak = (TextView) findViewById(R.id.itogBrakRO);
    itogMove = (TextView) findViewById(R.id.itogMoveRO);
    
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
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by name", null);
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
        	setItog();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText("0");
        	getSupportLoaderManager().getLoader(0).forceLoad();
        	setItog();
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
        			tvIdPgr.getText().toString(), "Расход с остатком за период", 3);
        	}
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { /*"_id",*/"_id","pgr","name","post","kol","brak","move","ed","sumka","sumkanal","sumkanonal","price","skidka","ostat","keg"};
    int[] to = new int[] {/*R.id.tv_Id_Rasxod_Hist,*/ R.id.tv_Nnom_Rasxod_Ost, R.id.tv_Pgr_Rasxod_Ost, R.id.tv_Name_Rasxod_Ost,R.id.tv_Post_Rasxod_Ost,R.id.tv_Kol_Rasxod_Ost,R.id.tv_Brak_Rasxod_Ost,R.id.tv_Move_Rasxod_Ost,R.id.tv_Ed_Rasxod_Ost,R.id.tv_Sumka_Rasxod_Ost,R.id.tv_Sumka_NalRasxod_Ost,R.id.tv_Sumka_NoNalRasxod_Ost,R.id.tv_Price_Rasxod_Ost,R.id.tv_Skidka_Rasxod_Ost,R.id.tv_Ostat_Rasxod_Ost,R.id.tv_Keg_Rasxod_Ost};
    //int[] toH = new int[] {R.id.tv_Nnom_Rasxod_Hist,R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_DataIns_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelRasxodOst, R.id.btnUpdRasxodOst, 10, this, R.layout.rasxod_ostat_item, null, from, to, 0)
    .setCamdiareListener(new CambiareListener() {
		@Override
		public void OnCambiare(int flag, long id) {
			if (flag==1) {
				//MainActivity.db.delRec("rasxod",id);
				//getSupportLoaderManager().getLoader(0).forceLoad();
				}
		}
	});
    lvData = (ListView) findViewById(R.id.lvRasxodOst);
    lvData.setAdapter(scAdapter);
     
    // создаем лоадер для чтения данных
    //getSupportLoaderManager().initLoader(1, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    setItog();
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.rasxod_ostat_ll));
    itogKol.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogSum.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogSkidka.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogNoSkidka.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogNoNalRO.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogNalRO.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogBrak.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogMove.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
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
      setItog();
    }
    };
    OnDateSetListener myCallBack2 = new OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth) {
      tvDataIns2.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
      //getSupportLoaderManager().getLoader(1).forceLoad();
      getSupportLoaderManager().getLoader(0).forceLoad();
      setItog();
    }
    };
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
    	String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?" 1=1 ":" TT._id="+tvIdPgr.getText().toString(),
        		(tvDataIns.getText().length()==0)?" 1=1 ":" substr(T.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")",
        				(tvDataIns2.getText().length()==0)?" 1=1 ":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")"
        				//,(tvIdKlient.getText().toString().equals("0")||tvIdKlient.getText().length()==0)?"":" K._id="+tvIdKlient.getText().toString()
        						};
        //String where=str[0].toString();
        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
        //if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
     
        //if (where.equals("")||where.length()==0) where=str[2].toString(); else 
        	//if (!str[2].equals("")) where=where+" and "+str[2].toString();
        //if (where.equals("")||where.length()==0) where=" T.ok=0 "; else where=where+" and T.ok=0 ";
/*        if (where.equals("")||where.length()==0) where=str[3].toString(); else 
        	if (!str[3].equals("")) where=where+" and "+str[3].toString();
"id_tmc","pgr","name","post","kol","ed","sumka","ostat"
*/     
  /*          	 Cursor cursor = db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id LEFT OUTER JOIN ostat as K on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg left join postav as KK on T.id_post=KK._id", 
             			new String[] {"K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')' as keg","TP._id as _id","TT.name as pgr","TP.name as name","KK.name as post","sum(T.kol) as kol","E.name as ed",
            			 "sum(T.price*T.kol) as sumka","round(sum(T.price*T.kol)/sum(T.kol),2) as price","round(sum(T.skidka),2) as skidka","K.kol as ostat"}, 
             			 //"TP.pgr = ?"
            			 where, null,"K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')', TP._id, TT.name, TP.name, KK.name, E.name, K.kol",null,"TP.name,K.keg");
*/
/*        Cursor cursor = db.getQueryData("ostat as K left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left JOIN rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg left join postav as KK on K.id_post=KK._id", 
     			new String[] {"K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')' as keg","TP._id as _id","TT.name as pgr","TP.name as name","KK.name as post","sum(T.kol) as kol","E.name as ed",
    			 "sum(T.price*T.kol) as sumka","round(sum(T.price*T.kol)/sum(T.kol),2) as price","round(sum(T.skidka),2) as skidka","K.kol as ostat"}, 
     			 //"TP.pgr = ?"
    			 where, null,"K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')', TP._id, TT.name, TP.name, KK.name, E.name, K.kol","K.kol<>0 or sum(T.kol)<>0","TP.name,K.keg");
*/
        Cursor //cursor;
      //  if (cbOst.isChecked())
         cursor = db.getRawData 
         ("select kkeg,keg,_id,pgr,name,post,kol,ed,sumka,price,skidka,ostat,sumkanonal,sumkanal, brak, move from "+
         "("+
         "select sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end,0),2) END else 0 end) as sumkanonal, sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end,0),2) ELSE 0 END else 0 end) as sumkanal, K.keg as kkeg, K.ok||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,sum(case T.ok when 0 then ifnull(T.kol,0) else 0 end) as kol,E.name as ed,sum(T.price* case T.ok when 0 then ifnull(T.kol,0) else 0 end ) as sumka,CASE ifnull(sum(T.kol),0) WHEN 0 THEN 0 ELSE round(sum(T.price*T.kol)/sum(T.kol),2) END as price, round(sum(ifnull(T.skidka,0)),2) as skidka, round(K.kol,3) as ostat, "
         + "sum(ifnull(case T.ok when 1 then T.kol_brak else 0 end,0)) as brak, sum(ifnull(case T.ok when 2 then T.kol_move else 0 end,0)) as move "+ 
         "from ostat as K left join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and "+str[1]+" and "+str[2]+ 
         " left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left join postav as KK on K.id_post=KK._id "+
         "where K.kol<>0 and "+str[0]+
         " group by K.keg, K.ok||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')', TP._id,TT.name,TP.name,KK.name,E.name,round(K.kol,3) "+ 
         " union all "+
         "select sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end,0),2) END else 0 end) as sumkanonal, sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end,0),2) ELSE 0 END else 0 end) as sumkanal, K.keg as kkeg, K.ok||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,sum(case T.ok when 0 then ifnull(T.kol,0) else 0 end) as kol,E.name as ed,sum(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end) as sumka,CASE ifnull(sum(T.kol),0) WHEN 0 THEN 0 ELSE round(sum(T.price*T.kol)/sum(T.kol),2) END as price,round(sum(ifnull(T.skidka,0)),2) as skidka, round(K.kol,3) as ostat, "
         + "sum(ifnull(case T.ok when 1 then T.kol_brak else 0 end,0)) as brak, sum(ifnull(case T.ok when 2 then T.kol_move else 0 end,0)) as move "+ 
         "from ostat as K left join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and "+str[1]+" and "+str[2]+ 
         " left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left join postav as KK on K.id_post=KK._id "+
         "where K.kol=0 and "+str[0]+
         " group by K.keg, K.ok||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')', TP._id,TT.name,TP.name,KK.name,E.name,round(K.kol,3) "+ 
         ") "+
         "where ifnull(ostat,0)+ifnull(kol,0)+ifnull(brak,0)+ifnull(move,0)!=0 order by name, keg"
         ,null);
         /*(
    			"select keg, kkeg, _id, pgr, name, post, kol, ed, sumka, price, skidka, ifnull(ostat,0) ostat from (" +
    			"select K.keg as kkeg, K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,sum(ifnull(T.kol,0)) as kol,E.name as ed,sum(T.price*T.kol) as sumka,round(sum(T.price*T.kol)/sum(T.kol),2) as price,round(sum(T.skidka),2) as skidka,round(K.kol,3) as ostat "
    			+ "from rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id LEFT JOIN ostat as K on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg left join postav as KK on T.id_post=KK._id "
    			+ "where "+where+" group by K.keg, K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')', TP._id, TT.name, TP.name, KK.name, E.name, round(K.kol,3) union " +
    					"select K.keg as kkeg, K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,0 as kol,E.name as ed,null as sumka,null as price,null as skidka, round(K.kol,3) as ostat" +
    					" from ostat as K left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left JOIN rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg left join postav as KK on K.id_post=KK._id " +
    					" where K.kol<>0 "+((tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":"and TP.pgr="+(tvIdPgr.getText().toString()))+" group by K.keg, K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')', TP._id, TT.name, TP.name, KK.name, E.name, round(K.kol,3)" +
    							" )"
    			+" order by name, keg"
    			, null);//new String[] {(Integer.parseInt(tvIdPgr.getText().toString())==0)?"T.pgr ":tvIdPgr.getText().toString()});// new String[] {,});
        else
        	cursor = db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id LEFT OUTER JOIN ostat as K on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg left join postav as KK on T.id_post=KK._id", 
         			new String[] {"K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')' as keg","TP._id as _id","TT.name as pgr","TP.name as name","KK.name as post","sum(T.kol) as kol","E.name as ed",
        			 "sum(T.price*T.kol) as sumka","round(sum(T.price*T.kol)/sum(T.kol),2) as price","round(sum(T.skidka),2) as skidka","round(K.kol,3) as ostat"}, 
         			 //"TP.pgr = ?"
        			 where, null,"K.keg||' ('||substr(K.data_ins,5,2)||'.'||substr(K.data_ins,3,2)||')', TP._id, TT.name, TP.name, KK.name, E.name, K.kol",null,"TP.name,K.keg");
	*/
      return cursor;
    }
     
  }
  
void setItog () {
	String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?" 1=1 ":" TT._id="+tvIdPgr.getText().toString(),
    		(tvDataIns.getText().length()==0)?" 1=1 ":" substr(T.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")",
    				(tvDataIns2.getText().length()==0)?" 1=1 ":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")"
    						};
    /*while compiling: select round(sum(kol),3) as kol, round(sum(price*kol),2) as sumka, round(sum(skidka),2) as skidka, 
     * round(sum(sumkanonal),2) as sumkanonal, round(sum(sumkanal),2) as sumkanal from 
     * (select T.price as price, CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*T.kol,0),2) END as sumkanonal, 
     * CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*T.kol,0),2) ELSE 0 END as sumkanal, 
     * K.keg as kkeg, K.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as keg,
     *  TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,ifnull(T.kol,0) as kol,E.name as ed,T.price*T.kol as sumka,round(ifnull(T.skidka,0),2) as skidka, round(K.kol,3) as ostat from ostat as K left join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and T.ok=1  and  substr(T.data_ins,1,6)>=trim(190409) and  substr(T.data_ins,1,6)<=trim(190409) left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left join postav as KK on K.id_post=KK._id where K.kol<>0 and  TT._id=19union all select T.price as price, CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*T.kol,0),2) END as sumkanonal, CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*T.kol,0),2) ELSE 0 END as sumkanal, K.keg as kkeg, K.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,ifnull(T.kol,0) as kol,E.name as ed,T.price*T.kol as sumka,round(ifnull(T.skidka,0),2) as skidka, round(K.kol,3) as ostat from ostat as K left join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and T.ok=1  and  substr(T.data_ins,1,6)>=trim(190409) and  substr(T.data_ins,1,6)<=trim(190409) left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left join postav as KK on K.id_post=KK._id where K.kol=0 and  TT._id=19)
*/
	//String where=str[0].toString();
    //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
    //if (where.equals("")||where.length()==0) where=str[1].toString(); else 
    	//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
 
    //if (where.equals("")||where.length()==0) where=str[2].toString(); else 
    	//if (!str[2].equals("")) where=where+" and "+str[2].toString();
    //if (where.equals("")||where.length()==0) where=" T.ok=0 "; else where=where+" and T.ok=0 ";
      //  float tmpKol=0, tmpSum=0;
    Cursor cursor;
    //if (cbOst.isChecked()) 
    	/*cursor = MainActivity.db.getRawData 
    	("select round(sum(kol),3) as kol, round(sum(price*kol),2) as sumka, round(sum(skidka),2) as skidka, round(sum(sumkanonal),2) as sumkanonal, round(sum(sumkanal),2) as sumkanal, round(sum(brak),3) as sumkabrak, round(sum(move),3) as sumkamove  from "+
    			"("+
    	         "select T.price as price, case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*T.kol,0),2) END else 0 end as sumkanonal, case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*T.kol,0),2) ELSE 0 END else 0 end as sumkanal, case T.ok when 0 then ifnull(T.kol,0) else 0 end as kol,T.price*T.kol as sumka,round(ifnull(T.skidka,0),2) as skidka, "
    	         + "ifnull(case T.ok when 1 then T.kol_brak else 0 end,0) as brak, ifnull(case T.ok when 2 then T.kol_move else 0 end,0) as move "+ 
    	         "from ostat as K left  join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and "+str[1]+" and "+str[2]+ 
    	         " left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id "+
    	         " where K.kol<>0 and "+str[0]+
    	         //" group by K.keg, K.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')', TP._id,TT.name,TP.name,KK.name,E.name,round(K.kol,3) "+ 
    	         " union all "+
    	         "select T.price as price, case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*T.kol,0),2) END else 0 end as sumkanonal, case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*T.kol,0),2) ELSE 0 END else 0 end as sumkanal, case T.ok when 0 then ifnull(T.kol,0) else 0 end as kol,T.price*T.kol as sumka,round(ifnull(T.skidka,0),2) as skidka, "
    	         + "ifnull(case T.ok when 1 then T.kol_brak else 0 end,0) as brak, ifnull(case T.ok when 2 then T.kol_move else 0 end,0) as move "+ 
    	         "from ostat as K left  join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and "+str[1]+" and "+str[2]+
    	         " left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id "+
    	         " where K.kol=0 and "+str[0]+
    	         //" group by K.keg, K.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')', TP._id,TT.name,TP.name,KK.name,E.name,round(K.kol,3) "+ 
    	         ") "
    			,null);
    	*/
    	cursor = MainActivity.db.getRawData 
    	         ("select round(sum(t1.kol),3) as kol, round(sum(t1.sumka),2) as sumka, round(sum(t1.skidka),2) as skidka, round(sum(t1.sumkanonal),2) as sumkanonal, round(sum(t1.sumkanal),2) as sumkanal, round(sum(t1.brak),3) as sumkabrak, round(sum(t1.move),3) as sumkamove "
    	         		+ " from (select kkeg,keg,_id,pgr,name,post,kol,ed,sumka,price,skidka,ostat,sumkanonal,sumkanal, brak, move from "+
    	         "("+
    	         "select sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end,0),2) END else 0 end) as sumkanonal, sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end,0),2) ELSE 0 END else 0 end) as sumkanal, K.keg as kkeg, K.ok||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,sum(case T.ok when 0 then ifnull(T.kol,0) else 0 end) as kol,E.name as ed,sum(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end) as sumka,CASE ifnull(sum(T.kol),0) WHEN 0 THEN 0 ELSE round(sum(T.price*T.kol)/sum(T.kol),2) END as price,round(sum(ifnull(T.skidka,0)),2) as skidka, round(K.kol,3) as ostat, "
    	         + "sum(ifnull(case T.ok when 1 then T.kol_brak else 0 end,0)) as brak, sum(ifnull(case T.ok when 2 then T.kol_move else 0 end,0)) as move "+ 
    	         "from ostat as K left join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and "+str[1]+" and "+str[2]+ 
    	         " left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left join postav as KK on K.id_post=KK._id "+
    	         "where K.kol<>0 and "+str[0]+
    	         " group by K.keg, K.ok||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')', TP._id,TT.name,TP.name,KK.name,E.name,round(K.kol,3) "+ 
    	         " union all "+
    	         "select sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end,0),2) END else 0 end) as sumkanonal, sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end,0),2) ELSE 0 END else 0 end) as sumkanal, K.keg as kkeg, K.ok||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,sum(case T.ok when 0 then ifnull(T.kol,0) else 0 end) as kol,E.name as ed,sum(T.price*case T.ok when 0 then ifnull(T.kol,0) else 0 end) as sumka,CASE ifnull(sum(T.kol),0) WHEN 0 THEN 0 ELSE round(sum(T.price*T.kol)/sum(T.kol),2) END as price,round(sum(ifnull(T.skidka,0)),2) as skidka, round(K.kol,3) as ostat, "
    	         + "sum(ifnull(case T.ok when 1 then T.kol_brak else 0 end,0)) as brak, sum(ifnull(case T.ok when 2 then T.kol_move else 0 end,0)) as move "+ 
    	         "from ostat as K left join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and "+str[1]+" and "+str[2]+ 
    	         " left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left join postav as KK on K.id_post=KK._id "+
    	         "where K.kol=0 and "+str[0]+
    	         " group by K.keg, K.ok||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')', TP._id,TT.name,TP.name,KK.name,E.name,round(K.kol,3) "+ 
    	         ") "+
    	         "where ifnull(ostat,0)+ifnull(kol,0)+ifnull(brak,0)+ifnull(move,0)!=0 ) as t1 "
    	         ,null);
    			/*(
    			"select round(sum(kol),3) as kol, round(sum(price*kol),2) as sumka, round(sum(skidka),2) as skidka from (" +
    			"select K.keg as kkeg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,sum(T.kol) as kol,E.name as ed,sum(T.price*T.kol) as sumka,round(sum(T.price*T.kol)/sum(T.kol),2) as price,round(sum(T.skidka),2) as skidka,K.kol as ostat "
    			+ "from rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id LEFT OUTER JOIN ostat as K on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg left join postav as KK on T.id_post=KK._id "
    			+ "where "+where+" group by K.keg, TP._id, TT.name, TP.name, KK.name, E.name, K.kol union " +
    					"select K.keg as kkeg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,null as kol,E.name as ed,null as sumka,null as price,null as skidka,K.kol as ostat" +
    					" from ostat as K left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left JOIN rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg left join postav as KK on K.id_post=KK._id " +
    					" where K.kol<>0 "+((tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":"and TP.pgr="+(tvIdPgr.getText().toString()))+" group by K.keg, TP._id, TT.name, TP.name, KK.name, E.name, K.kol" +
    							" )"
    			//+" order by name, keg"
    			, null);//new String[] {(Integer.parseInt(tvIdPgr.getText().toString())==0)?"T.pgr ":tvIdPgr.getText().toString()});// new String[] {,});
	
    else
    cursor = MainActivity.db.getQueryData
("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id left join ostat as K on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.keg=K.keg and T.ed=K.ed left join postav as KK on T.id_post=KK._id",
         			new String[] {"round(sum(T.kol),3) as kol","round(sum(T.price*T.kol),2) as sumka","round(sum(T.skidka),2) as skidka"}, 
         			 //"TP.pgr = ?"
        			 where, null,null,null,null);
    */
        	 if (cursor.moveToFirst())  
      		   
     	        do {//tmpKol=cursor.getDouble(cursor.getColumnIndex("kol"));
     	        	//tmpSum=cursor.getDouble(cursor.getColumnIndex("s"));
     	        	itogKol.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("kol")) ) );
     	        	itogSum.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumka")) ) );
     	        	itogSkidka.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("skidka")) ) );
     	        	itogNoSkidka.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumka"))-cursor.getDouble(cursor.getColumnIndex("skidka") )) );
     	        	itogNoNalRO.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumkanonal")) ) );
     	        	itogNalRO.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumkanal")) ) );
     	        	itogBrak.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumkabrak")) ) );
     	        	itogMove.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("sumkamove")) ) );
     	        } while (cursor.moveToNext());
     	      
        	        cursor.close();
     	     
}
 
/*
no such column: T.ok (code 1): , while compiling: 
SELECT sum(T.kol) as kol, sum(T.price*T.kol) as sumka, sum(RR.rrkol) as poskidka, sum(T.price*T.kol)-sum(RR.rrkol) as s  
FROM rasxod as T left join (select w._id as rrid, w.kol rrkol from rasxod as w where w._id=T.ok ) as RR on T.ok=RR.rrid left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id left join ostat as K on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed left join postav as KK on T.id_post=KK._id WHERE  substr(T.data_ins,1,6)>=trim(170512) and  substr(T.data_ins,1,6)<=trim(170512)
*/
  
}

