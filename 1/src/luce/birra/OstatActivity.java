package luce.birra;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
 
public class OstatActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  static CheckBox cbVis;
  static TextView tvIdPgr;
  Spinner spPgr;
  TextView tv;

  LinearLayout ll;
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
      super.onWindowFocusChanged(hasFocus);
      if(hasFocus) 
      { 
    	  ll = (LinearLayout) findViewById(R.id.llHOstat);
    	  if (scAdapter.wH<15) ll.setVisibility(LinearLayout.GONE);	  
    	  else
    	  {ll.setVisibility(LinearLayout.VISIBLE);
    	  int [] r = {R.id.tvHOst1,R.id.tvHOst2,R.id.tvHOst3,R.id.tvHOst4,R.id.tvHOst5,R.id.tvHOst6,R.id.tvHOst7,R.id.tvHOst8};
    	  for (int i=0;i<r.length;i++) {
    		  tv = (TextView) findViewById(r[i]); 
    		  tv.getLayoutParams().width = scAdapter.wH;
    			}
    	      	  }
    	  }
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ostat);
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Ostat);
    cbVis = (CheckBox) findViewById(R.id.cb_Kol_Ostat);
    cbVis.setChecked(true);
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
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, fromPgr, toPgr);

    // set layout for activated adapter
    sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
    
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
        	//Intent intent = new Intent(PgrActivity.this, PgrEditActivity.class);
			//startActivity(intent);
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitOstat);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { "_id","tname","pgr",/*"pname",*/"kol","ted","price"/*, "data_ins"*/ };
    int[] to = new int[] {R.id.tvId_Ostat, R.id.tvNameTmc_Ostat,R.id.tvNamePgr_Ostat, /*R.id.tvNamePost_Ostat,*/R.id.tvKol_Ostat,R.id.tvTed_Ostat,R.id.tvPrice_Ostat/*, R.id.tvDataIns_Ostat */ };
    //int[] toH = new int[] {R.id.tvId_Ostat,R.id.tvNameTmc_Ostat,R.id.tvNamePgr_Ostat,R.id.tvNamePost_Ostat,R.id.tvKol_Ostat,R.id.tvTed_Ostat,R.id.tvPrice_Ostat,R.id.tvDataIns_Ostat};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelOstat, R.id.btnUpdOstat, (byte)5, this, R.layout.ostat_item, null, from, to, 0);
    
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
    			"select O._id as _id, O.id_tmc as id_tmc, O.kol as kol, E.name as ted, O.price as price, O.id_post as id_post, O.data_ins as data_ins, "
    			+ "P.name as pname, T.name as tname, TP.name as pgr "
    			+ "from ostat as O "
    			+ "left join tmc as T "
    			+ "on O.id_tmc=T._id "
    			+ "left join postav as P "
    			+ "on O.id_post=P._id "
    			+ "left join tmc_ed as E "
    			+ "on O.ed=E._id "
    			+ "left join tmc_pgr as TP "
    			+ "on T.pgr=TP._id "
    			+ "where "+(cbVis.isChecked()?" O.kol>0 ":" O.kol<=0")+((Integer.parseInt(tvIdPgr.getText().toString())==0)?"":" and T.pgr="+tvIdPgr.getText().toString()) // +"? and ?"
    			, null);//new String[] {(Integer.parseInt(tvIdPgr.getText().toString())==0)?"T.pgr ":tvIdPgr.getText().toString()});// new String[] {,});
      return cursor;
    }
     
  }

}

