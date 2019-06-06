package luce.birra;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import luce.birra.AdapterLV.CambiareListener;
import luce.birra.DialogScreen.DialogListener;
 
public class BrakActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd, btnDetail;
  AdapterLV scAdapter;
  //static CheckBox cbVis;
  static TextView tvIdPgr;
  //TextView nedo,izl;
  Spinner spPgr;
  //int tvDialogN=0;
  //LinearLayout ll;
  
  void showMessage(String s, int dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(BrakActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }
  
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.brak);
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by name", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Brak_Move_);
    //cbVis = (CheckBox) findViewById(R.id.cb_Kol_Ostat);
    /*cbVis.setChecked(false);
    cbVis.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			getSupportLoaderManager().getLoader(0).forceLoad();
		}
	});*/
    tvIdPgr = (TextView) findViewById(R.id.et_Id_PgrBrak_Move_);
    tvIdPgr.setText("0");
    
    /*nedo = (TextView) findViewById(R.id.tvOstat_nedo_brak);
    nedo.setText("БРАК");
    izl = (TextView) findViewById(R.id.tvOstat_izl_move);
    izl.setText("ПЕРЕМЕЩЕНИЕ");*/
    // make an adapter from the cursor
    String[] fromPgr = new String[] {"name"};
    int[] toPgr = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_item, c, fromPgr, toPgr);

    // set layout for activated adapter
    sca.setDropDownViewResource(R.layout.spinner_drop_down); 
    
    // get xml file spinner and set adapter 
    spPgr.setAdapter(sca);

    // set spinner listener to display the selected item id
    //mContext = this;
    spPgr.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spPgr.setTag(id);
        	tvIdPgr.setText(String.valueOf(id));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText(String.valueOf("0"));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        });
    
    btnAdd = (Button) findViewById(R.id.btnAddBrak_Move);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(BrakActivity.this, BrakActivity.this, "","", 
        			tvIdPgr.getText().toString(), "Перемещение и брак", 11);
        }
      });
    
    btnDetail = (Button) findViewById(R.id.btnDetailBrak_Move);
    btnDetail.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(BrakActivity.this, BrakMoveHistActivity.class);
			   startActivity(intent);
			   
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitBrak_Move);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { /*"_id",*/"pgr","tname",/*"pname",*/"kol","ted", "keg","kol_izl","kol_nedo" };
    int[] to = new int[] {/*R.id.tvId_Brak,*/ R.id.tvNamePgr_Brak,R.id.tvNameTmc_Brak, /*R.id.tvNamePost_Ostat,*/R.id.tvKol_Brak,R.id.tvTed_Brak,R.id.tvKeg_Brak,R.id.tvKolIzl_Brak,R.id.tvKolNedo_Brak };
    //int[] toH = new int[] {R.id.tvId_Ostat,R.id.tvNameTmc_Ostat,R.id.tvNamePgr_Ostat,R.id.tvNamePost_Ostat,R.id.tvKol_Ostat,R.id.tvTed_Ostat,R.id.tvPrice_Ostat,R.id.tvDataIns_Ostat};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelBrak, R.id.btnUpdBrak, 17, this, R.layout.brak_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(int flag, long id) {
    				final int fl=flag;
    				/*if (flag==1) {
    					long countT=0;
    					Cursor cc = MainActivity.db.getRawData ("select id_tmc, keg, kol, ed, id_post from ostat where kol<>0 and _id="+id,null);
    					   if (cc.moveToFirst()) { 
    					        do {countT=
    					        		MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")),cc.getInt(cc.getColumnIndex("keg")), cc.getDouble(cc.getColumnIndex("kol")), 0, 0, 0, 0, (byte)0, (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "ОБНУЛЕНИЕ ОСТАТКА ID="+id, MainActivity.getIntDataTime(), 5);
    					        		//cc.getInt(cc.getColumnIndex("c"));//+ " count: tmc "+db.getAllData("tmc").getCount());
    					        } while (cc.moveToNext());
    					      };
    					if (countT!=0)      
    					{//MainActivity.db.delRec("tmc_pgr",id);
    					getSupportLoaderManager().getLoader(0).forceLoad();
    					//else 
    						showMessage("Остаток обнулен", (byte)1);
    					//MainActivity.db.delRec("ostat",id);
    					//getSupportLoaderManager().getLoader(0).forceLoad();
    					}
    			} 
    				if (flag==2) {*/
    					final long idd=id;
    					DialogScreen getkol = new DialogScreen(BrakActivity.this,BrakActivity.this, (fl==1?2:3))
    							 .setDialogScreenListener(new DialogListener() {
    								@Override
    								public void OnSelectedKol(double k) {
    									if (k!=0) 
    									{
    										long countT=0;
    				    					Cursor cc = MainActivity.db.getRawData ("select O.id_tmc as id_tmc, O.keg as keg, O.kol as kol, O.ed as ed, O.id_post as id_post, TP.price as price from ostat O left join tmc_price as TP on O.id_tmc=TP.id_tmc and O.id_post=TP.id_post where O._id="+idd,null);
    				    					   if (cc.moveToFirst()) { 
    				    					        do {
    				    					        	if (k<=cc.getDouble(cc.getColumnIndex("kol")))
    				    					        	countT=
    				    					        		MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getInt(cc.getColumnIndex("keg")),/*cc.getDouble(cc.getColumnIndex("kol"))-*/k,
    				    					        				/*(cc.getDouble(cc.getColumnIndex("kol"))<0?-cc.getDouble(cc.getColumnIndex("kol")):0),(cc.getDouble(cc.getColumnIndex("kol"))>0?cc.getDouble(cc.getColumnIndex("kol")):0)*/
    				    					        				//(k>0)&&(k<cc.getDouble(cc.getColumnIndex("kol")))?cc.getDouble(cc.getColumnIndex("kol"))-k:0,
    				    					        				//(k>0)&&(k>cc.getDouble(cc.getColumnIndex("kol")))?cc.getDouble(cc.getColumnIndex("kol"))-k:0,
    				    					        				0,0,(fl==1?0:k),(fl==1?k:0),//cc.getDouble(cc.getColumnIndex("kol"))-k,
    				    					        				0,cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, (fl==1?"ПЕРЕМЕЩЕНИЕ":"БРАК")+" ОСТАТКА РАСХОД ID="+idd, MainActivity.getIntDataTime(), (fl==1?2:1));
    				    					        	else showMessage("Количество остатка не достаточно", 1);
    				    					        	//MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getInt(cc.getColumnIndex("keg")),-k,/*cc.getDouble(cc.getColumnIndex("kol")),(cc.getDouble(cc.getColumnIndex("kol"))<0?-cc.getDouble(cc.getColumnIndex("kol")):0),(cc.getDouble(cc.getColumnIndex("kol"))>0?cc.getDouble(cc.getColumnIndex("kol")):0)*/0,0, (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "ИЗМЕНЕНИЕ ОСТАТКА РАСХОД ID="+idd, MainActivity.getIntDataTime(), 1);		
    				    					        //MainActivity.db.addRecPRIXOD(cc.getInt(cc.getColumnIndex("id_tmc")),cc.getInt(cc.getColumnIndex("keg")), k,0, 0, (byte)cc.getInt(cc.getColumnIndex("ed")), 0, cc.getDouble(cc.getColumnIndex("price")), cc.getInt(cc.getColumnIndex("id_post")), "ИЗМЕНЕНИЕ ОСТАТКА ПРИХОД ID="+idd, MainActivity.getIntDataTime(), (byte)1);
    				    					        } while (cc.moveToNext());
    				    					      };
    				    					if (countT!=0)      
    				    					{
    				    					getSupportLoaderManager().getLoader(0).forceLoad();
    				    						showMessage("Остаток изменен", 1);
    				    					}
    									}
    									//else dialogNumCancel(R.id.cb_Kol_Ostat);					
    								}
    							}) ;getkol.show();
    							
    				//}
    				}
    		});
    
    lvData = (ListView) findViewById(R.id.lvBrak);
    //lvData.setOnItemClickListener(this);
    /*lvData.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id)
        {
            Log.d("MyLog", position+" "+itemClicked.getId()+" FPgr click delete "+id);
        }
    });*/
    lvData.setAdapter(scAdapter);
     
    // создаем лоадер для чтения данных
    getSupportLoaderManager().initLoader(0, null, this);
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.brak_ll));
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
  public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
    return new MyCursorLoader(this, MainActivity.db);
  }
 
  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    scAdapter.swapCursor(cursor);
  }
 
  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
  }
   
  static class MyCursorLoader extends CursorLoader {
 
    DB db;
     
    public MyCursorLoader(Context context, DB db) {
      super(context);
      this.db = db;
    }
     
    @Override
    public Cursor loadInBackground() {
    	//Cursor cursor = db.getQueryData("tmc as T left join tmc_pgr as TP on T.pgr=TP._id", 
    		//	new String[] {"T._id as _id","T.name as name","T.pgr as pgr","TP.name as namepgr","T.price as price", "T.vis as vis"}, 
    			// "? and T.vis = ?",new String[] {(Integer.parseInt(tvIdPgr.getText().toString())==0)?"1=1":"T.pgr="+tvIdPgr.getText().toString(),cbVis.isChecked()?"1":"0"},null,null,null);
/*no such column: O.keg (code 1): , 
select O._id as _id, O.id_tmc as id_tmc, O.keg as keg, O.kol as kol, E.name as ted, TT.price as price, O.id_post as id_post, O.data_ins as data_ins, P.name as pname, T.name as tname, TP.name as pgr 
from ostat as O left join tmc_price as TT on O.id_tmc=TT.id_tmc and O.id_post=TT.id_post and O.ed=TT.ed left join tmc as T on O.id_tmc=T._id left join postav as P on O.id_post=P._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id where  O.kol!=0
*/   

    	
    	Cursor cursor = db.getRawData (
    			"select _id, id_tmc, keg, kol, ted, price, id_post, data_ins, pname, tname, pgr, kol_nedo, kol_izl from ( "
    			+" select O._id as _id, O.id_tmc as id_tmc, O.keg as keg, round(O.kol,3) as kol, E.name as ted, TT.price as price, O.id_post as id_post, ifnull(O.data_upd,O.data_ins) as data_ins, "
    			+ "P.name as pname, T.name as tname, TP.name as pgr, sum(CASE R.ok WHEN 1 then round(R.kol,3) ELSE 0 END) as kol_nedo, sum(CASE R.ok WHEN 2 then round(R.kol,3) ELSE 0 END) as kol_izl "
    			+ "from ostat as O "
    			+ "left join tmc_price as TT "
    			+ "on O.id_tmc=TT.id_tmc and O.id_post=TT.id_post and O.ed=TT.ed "
    			+ "left join tmc as T "
    			+ "on O.id_tmc=T._id "
    			+ "left join postav as P "
    			+ "on O.id_post=P._id "
    			+ "left join tmc_ed as E "
    			+ "on O.ed=E._id "
    			+ "left join tmc_pgr as TP "
    			+ "on T.pgr=TP._id "
    			+ "left join rasxod as R "
    			+ "on O.id_tmc=R.id_tmc and O.id_post=R.id_post and O.ed=R.ed and O.keg=R.keg and ifnull(R.ok,0) in (1,2)"
    			+ "where "+((Integer.parseInt(tvIdPgr.getText().toString())==0)?" 1=1 ":"T.pgr="+tvIdPgr.getText().toString())  
    			+ " group by O._id, O.id_tmc, O.keg, round(O.kol,3), E.name, TT.price, O.id_post, ifnull(O.data_upd,O.data_ins), P.name, T.name, TP.name"
    			+ " ) where ifnull(kol,0)+ifnull(kol_nedo,0)+ifnull(kol_izl,0)!=0 "
    			+" order by pgr, tname, id_post, keg"
    			, null);//new String[] {(Integer.parseInt(tvIdPgr.getText().toString())==0)?"T.pgr ":tvIdPgr.getText().toString()});// new String[] {,});
      return cursor;
    }
     
  }

}


