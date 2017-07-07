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
 
public class PriceActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

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
	  Toast toast = new Toast(PriceActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }
  
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.price);
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by _id", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Price);
    cbVis = (CheckBox) findViewById(R.id.cb_Kol_Price);
    cbVis.setChecked(true);
    cbVis.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			getSupportLoaderManager().getLoader(0).forceLoad();
		}
	});
    tvIdPgr = (TextView) findViewById(R.id.et_Id_PgrPrice);
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
    
    btnAdd = (Button) findViewById(R.id.btnAddPrice);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(PriceActivity.this, PriceActivity.this, "","", 
        			tvIdPgr.getText().toString(), "Цены в магазине", (byte)7);
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitPrice);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { "_id","tname","pgr",/*"pname","kol",*/"ted","price"/*, "data_ins"*/ };
    int[] to = new int[] {R.id.tvId_Price, R.id.tvNameTmc_Price,R.id.tvNamePgr_Price, /*R.id.tvNamePost_Ostat,R.id.tvKol_Ostat,*/R.id.tvTed_Price,R.id.tvPrice_Price/*, R.id.tvDataIns_Ostat */ };
    //int[] toH = new int[] {R.id.tvId_Ostat,R.id.tvNameTmc_Ostat,R.id.tvNamePgr_Ostat,R.id.tvNamePost_Ostat,R.id.tvKol_Ostat,R.id.tvTed_Ostat,R.id.tvPrice_Ostat,R.id.tvDataIns_Ostat};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelPrice, R.id.btnUpdPrice, (byte)11, this, R.layout.price_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				if (flag==1) {
    					long countT=0;
    					Cursor cc = MainActivity.db.getRawData ("select id_tmc, id_post from tmc_price where _id="+id,null);
    					   if (cc.moveToFirst()) { 
    					        do {countT=1;
    					        MainActivity.db.updRec("tmc_price", id, "price", 0);
    					        		//MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), MainActivity.round(cc.getDouble(cc.getColumnIndex("kol")),6), (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "обнуление остатка id="+id, MainActivity.getIntDataTime(), 1);
    					        		//cc.getInt(cc.getColumnIndex("c"));//+ " count: tmc "+db.getAllData("tmc").getCount());
    					        } while (cc.moveToNext());
    					      };
    					if (countT!=0)      
    					{//MainActivity.db.delRec("tmc_pgr",id);
    					getSupportLoaderManager().getLoader(0).forceLoad();
    					//else 
    						showMessage("Цена обнулена", (byte)1);
    					//MainActivity.db.delRec("ostat",id);
    					//getSupportLoaderManager().getLoader(0).forceLoad();
    					}
    			} 
    				if (flag==2) {
    					final long idd=id;
    					DialogScreen getkol = new DialogScreen(PriceActivity.this,PriceActivity.this,R.id.cb_Kol_Price)
    							 .setDialogScreenListener(new DialogListener() {
    								@Override
    								public void OnSelectedKol(double k) {
    									if (k!=0) 
    									{
    										long countT=0;
    				    					Cursor cc = MainActivity.db.getRawData ("select id_tmc, ed, id_post, price from tmc_price where _id="+idd,null);
    				    					   if (cc.moveToFirst()) { 
    				    					        do {countT=1;
    				    					        	MainActivity.db.updRec("tmc_price", idd, "price", (float)k);
    				    					        		//MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getDouble(cc.getColumnIndex("kol")), (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "обнуление остатка id="+idd, MainActivity.getIntDataTime(), 1);
    				    					        		//MainActivity.db.addRecPRIXOD(cc.getInt(cc.getColumnIndex("id_tmc")), k, (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), "изменение остатка id="+idd, MainActivity.getIntDataTime(), (byte)1);
    				    					        } while (cc.moveToNext());
    				    					      };
    				    					if (countT!=0)      
    				    					{
    				    					getSupportLoaderManager().getLoader(0).forceLoad();
    				    						showMessage("Цена изменена", (byte)1);
    				    					}
    									}
    									//else dialogNumCancel(R.id.cb_Kol_Ostat);					
    								}
    							}) ;getkol.show();
    							
    				}
    				}
    		});
    
    lvData = (ListView) findViewById(R.id.lvPrice);
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
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.price_ll));
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
   

    	
    	Cursor cursor = db.getRawData (
    			"select O._id as _id, O.id_tmc as id_tmc, E.name as ted, O.price as price, O.id_post as id_post, O.data_ins as data_ins, "
    			+ "P.name as pname, T.name as tname, TP.name as pgr "
    			+ "from tmc_price as O "
    			+ "left join tmc as T "
    			+ "on O.id_tmc=T._id "
    			+ "left join postav as P "
    			+ "on O.id_post=P._id "
    			+ "left join tmc_ed as E "
    			+ "on O.ed=E._id "
    			+ "left join tmc_pgr as TP "
    			+ "on T.pgr=TP._id "
    			+ "where "+(cbVis.isChecked()?" O.price>0 ":" O.price<=0")+((Integer.parseInt(tvIdPgr.getText().toString())==0)?"":" and T.pgr="+tvIdPgr.getText().toString()) // +"? and ?"
    			, null);//new String[] {(Integer.parseInt(tvIdPgr.getText().toString())==0)?"T.pgr ":tvIdPgr.getText().toString()});// new String[] {,});
      return cursor;
    }
     
  }

}

