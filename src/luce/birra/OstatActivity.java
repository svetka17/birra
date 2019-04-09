package luce.birra;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import luce.birra.AdapterLV.CambiareListener;
import luce.birra.DialogScreen.DialogListener;
 
public class OstatActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  static CheckBox cbVis;
  static TextView tvIdPgr;
  Spinner spPgr;
  //int tvDialogN=0;
  //LinearLayout ll;
  
  void showMessage(String s, byte dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(OstatActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }
  
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ostat);
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by name", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Ostat);
    cbVis = (CheckBox) findViewById(R.id.cb_Kol_Ostat);
    cbVis.setChecked(false);
    cbVis.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			getSupportLoaderManager().getLoader(0).forceLoad();
		}
	});
    tvIdPgr = (TextView) findViewById(R.id.et_Id_PgrOstat);
    tvIdPgr.setText("0");
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
    
    btnAdd = (Button) findViewById(R.id.btnAddOstat);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(OstatActivity.this, OstatActivity.this, "","", 
        			tvIdPgr.getText().toString(), "Наличие в магазине", (byte)5);
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitOstat);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { "_id","tname","pgr",/*"pname",*/"kol","kol_izl","kol_nedo","ted","price", "keg" };
    int[] to = new int[] {R.id.tvId_Ostat, R.id.tvNameTmc_Ostat,R.id.tvNamePgr_Ostat, /*R.id.tvNamePost_Ostat,*/R.id.tvKol_Ostat,R.id.tvKolNedo_Ostat,R.id.tvKolIzl_Ostat,R.id.tvTed_Ostat,R.id.tvPrice_Ostat, R.id.tvKeg_Ostat };
    //int[] toH = new int[] {R.id.tvId_Ostat,R.id.tvNameTmc_Ostat,R.id.tvNamePgr_Ostat,R.id.tvNamePost_Ostat,R.id.tvKol_Ostat,R.id.tvTed_Ostat,R.id.tvPrice_Ostat,R.id.tvDataIns_Ostat};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelOstat, R.id.btnUpdOstat, (byte)5, this, R.layout.ostat_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				if (flag==1) {
    					long countT=0;
    					Cursor cc = MainActivity.db.getRawData ("select id_tmc, keg, kol, ed, id_post from ostat where kol<>0 and _id="+id,null);
    					   if (cc.moveToFirst()) { 
    					        do {countT=
    					        		MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")),cc.getInt(cc.getColumnIndex("keg")), cc.getDouble(cc.getColumnIndex("kol")), 0, 0, 0, 0, /*cc.getDouble(cc.getColumnIndex("kol")),*/(byte)0, (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "ОБНУЛЕНИЕ ОСТАТКА ID="+id, MainActivity.getIntDataTime(), 5);
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
    				if (flag==2) {
    					final long idd=id;
    					DialogScreen getkol = new DialogScreen(OstatActivity.this,OstatActivity.this,R.id.cb_Kol_Ostat)
    							 .setDialogScreenListener(new DialogListener() {
    								@Override
    								public void OnSelectedKol(double k) {
    									if (k!=0) 
    									{
    										long countT=0;
    				    					Cursor cc = MainActivity.db.getRawData ("select O.id_tmc as id_tmc, O.keg as keg, O.kol as kol, O.ed as ed, O.id_post as id_post, TP.price as price from ostat O left join tmc_price as TP on O.id_tmc=TP.id_tmc and O.id_post=TP.id_post where O.kol<>0 and O._id="+idd,null);
    				    					   if (cc.moveToFirst()) { 
    				    					        do {countT=
    				    					        		MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getInt(cc.getColumnIndex("keg")),cc.getDouble(cc.getColumnIndex("kol"))-k,
    				    					        				/*(cc.getDouble(cc.getColumnIndex("kol"))<0?-cc.getDouble(cc.getColumnIndex("kol")):0),(cc.getDouble(cc.getColumnIndex("kol"))>0?cc.getDouble(cc.getColumnIndex("kol")):0)*/
    				    					        				//(k>0)&&(k<cc.getDouble(cc.getColumnIndex("kol")))?cc.getDouble(cc.getColumnIndex("kol"))-k:0,
    				    					        				//(k>0)&&(k>cc.getDouble(cc.getColumnIndex("kol")))?cc.getDouble(cc.getColumnIndex("kol"))-k:0,
    				    					        				0,0,0,0,//cc.getDouble(cc.getColumnIndex("kol"))-k,
    				    					        				(byte)0,(byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "ИЗМЕНЕНИЕ ОСТАТКА РАСХОД ID="+idd, MainActivity.getIntDataTime(), 5);
    				    					        //MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getInt(cc.getColumnIndex("keg")),-k,/*cc.getDouble(cc.getColumnIndex("kol")),(cc.getDouble(cc.getColumnIndex("kol"))<0?-cc.getDouble(cc.getColumnIndex("kol")):0),(cc.getDouble(cc.getColumnIndex("kol"))>0?cc.getDouble(cc.getColumnIndex("kol")):0)*/0,0, (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "ИЗМЕНЕНИЕ ОСТАТКА РАСХОД ID="+idd, MainActivity.getIntDataTime(), 1);		
    				    					        //MainActivity.db.addRecPRIXOD(cc.getInt(cc.getColumnIndex("id_tmc")),cc.getInt(cc.getColumnIndex("keg")), k,0, 0, (byte)cc.getInt(cc.getColumnIndex("ed")), 0, cc.getDouble(cc.getColumnIndex("price")), cc.getInt(cc.getColumnIndex("id_post")), "ИЗМЕНЕНИЕ ОСТАТКА ПРИХОД ID="+idd, MainActivity.getIntDataTime(), (byte)1);
    				    					        } while (cc.moveToNext());
    				    					      };
    				    					if (countT!=0)      
    				    					{
    				    					getSupportLoaderManager().getLoader(0).forceLoad();
    				    						showMessage("Остаток изменен", (byte)1);
    				    					}
    									}
    									//else dialogNumCancel(R.id.cb_Kol_Ostat);					
    								}
    							}) ;getkol.show();
    							
    				}
    				}
    		});
    
    lvData = (ListView) findViewById(R.id.lvOstat);
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
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.ostat_ll));
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
    			"select O._id as _id, O.id_tmc as id_tmc, O.keg as keg, round(O.kol,3) as kol, E.name as ted, TT.price as price, O.id_post as id_post, ifnull(O.data_upd,O.data_ins) as data_ins, "
    			+ "P.name as pname, T.name as tname, TP.name as pgr, CASE O.ed WHEN 1 then round(O.kol_nedo,2) ELSE round(O.kol_nedo,3) END  as kol_nedo, CASE O.ed WHEN 1 then round(O.kol_izl,2) ELSE round(O.kol_izl,3) END as kol_izl "
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
    			+ "where "+(cbVis.isChecked()?" O.kol!=0 ":" (O.kol!=0 or O.kol_nedo!=0 or O.kol_izl!=0) ")+((Integer.parseInt(tvIdPgr.getText().toString())==0)?"":" and T.pgr="+tvIdPgr.getText().toString())  +" order by T.pgr, T.name, O.id_post, O.keg"
    			, null);//new String[] {(Integer.parseInt(tvIdPgr.getText().toString())==0)?"T.pgr ":tvIdPgr.getText().toString()});// new String[] {,});
      return cursor;
    }
     
  }

}

