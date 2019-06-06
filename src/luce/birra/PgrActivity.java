package luce.birra;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import luce.birra.AdapterLV.CambiareListener;

 
public class PgrActivity extends FragmentActivity implements /*OnItemClickListener,*/ LoaderCallbacks<Cursor> {
//static int ww=0;
  ListView lvData;
	//PinnedSectionListView plvData;
	//GridView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  TextView tv;
  LinearLayout ll;
  
  void showMessage(String s, int dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(PgrActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pgr);
    
    btnAdd = (Button) findViewById(R.id.btnAddPgr);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(PgrActivity.this, PgrEditActivity.class);
			startActivity(intent);
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitPgr);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    //////////////////////////////////////////////////////
   
    /////////////////////////////////////////
    // формируем столбцы сопоставления
    String[] from = new String[] { "_id","name"/*, "data_ins"*/ };
    int[] to = new int[] {R.id.tvIdPgr, R.id.tvNamePgr/*, R.id.tvDataInsPgr*/  };
    //int[] toH = new int[] {R.id.tvIdPgr, R.id.tvNamePgr, R.id.tvDataInsPgr  };
    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = 
    new AdapterLV(R.id.btnDelPgr, R.id.btnUpdPgr, 2, this, R.layout.pgr_item, null, from, to, 0)
    .setCamdiareListener(new CambiareListener() {
		@Override
		public void OnCambiare(int flag, long id) {
			if (flag==1) {
				int countT=0;
				Cursor cc = MainActivity.db.getRawData ("select count(*) c from tmc T where T.pgr="+id,null);
				   if (cc.moveToFirst()) { 
				        do {countT=cc.getInt(cc.getColumnIndex("c"));//+ " count: tmc "+db.getAllData("tmc").getCount());
				        } while (cc.moveToNext());
				      };
				if (countT==0)      
				{MainActivity.db.delRec("tmc_pgr",id);
				getSupportLoaderManager().getLoader(0).forceLoad();}
				else 
					showMessage("Удалять запрещено, имеются остатки", 1);
				}
			
			//MainActivity.db.delRec("tmc_pgr",id);
			//getSupportLoaderManager().getLoader(0).forceLoad();
		}
	});
    
    lvData = (ListView) findViewById(R.id.lvPgr);
    lvData.setAdapter(scAdapter);

    // создаем лоадер для чтения данных
    getSupportLoaderManager().initLoader(0, null, this);
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.pgr_ll));
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
    	
    	Cursor cursor = db.getRawData (
    			"select TP._id as _id, TP.name as name, TP.data_ins as data_ins "
    			        + "from tmc_pgr as TP "
    			, null); //db.getAllData("tmc_pgr");
      return cursor;
    }
     
  }

}

