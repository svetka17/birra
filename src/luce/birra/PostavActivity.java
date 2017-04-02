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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
 
public class PostavActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;

  
  LinearLayout ll;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.postav);
    
    btnAdd = (Button) findViewById(R.id.btnAddPostav);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(PostavActivity.this, PostavEditActivity.class);
			startActivity(intent);
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitPostav);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    /*btnDel = (Button) findViewById(R.id.btnDelPostav);
    btnDel.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
      });*/
    // формируем столбцы сопоставления
    String[] from = new String[] { "_id","name", "adres","telef","prim","data_ins" };
    int[] to = new int[] {R.id.tvIdPostav, R.id.tvNamePostav,R.id.tvAdresPostav,R.id.tvTelefPostav,R.id.tvPrimPostav, R.id.tvDataInsPostav  };
    //int[] toH = new int[] {R.id.tvIdPostav,R.id.tvNamePostav,R.id.tvAdresPostav,R.id.tvTelefPostav,R.id.tvPrimPostav};
    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelPostav, R.id.btnUpdPostav, (byte)7, this, R.layout.postav_item, null, from, to, 0);
    lvData = (ListView) findViewById(R.id.lvPostav);
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
    MainActivity.setSizeFont((LinearLayout)findViewById(R.id.postav_ll),(byte)2,(byte)3,(byte)3);
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
    			"select TP._id as _id, TP.name as name, substr(TP.data_ins,5)||'.'||substr(TP.data_ins,3,2)||'.'||substr(TP.data_ins,1,2) as data_ins,"
    			        + "TP.adres as adres, TP.telef as telef, TP.prim as prim from postav as TP "
    			, null); //db.getAllData("tmc_pgr");
      return cursor;
    }
     
  }

}

