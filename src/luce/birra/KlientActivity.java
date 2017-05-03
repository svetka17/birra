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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import luce.birra.AdapterLV.CambiareListener;
 
public class KlientActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  void showMessage(String s, byte dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(KlientActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.klient);
    
    btnAdd = (Button) findViewById(R.id.btnAddKlient);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(KlientActivity.this, KlientEditActivity.class);
			startActivity(intent);
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitKlient);
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
    String[] from = new String[] { "_id","name","sconto_sum","sconto_per", "adres","telef","prim","data_ins" };
    int[] to = new int[] {R.id.tvIdKlient, R.id.tvNameKlient,R.id.tvSconto_sumKlient,R.id.tvSconto_perKlient,R.id.tvAdresKlient,R.id.tvTelefKlient,R.id.tvPrimKlient, R.id.tvDataInsKlient  };
    //int[] toH = new int[] {R.id.tvIdPostav,R.id.tvNamePostav,R.id.tvAdresPostav,R.id.tvTelefPostav,R.id.tvPrimPostav};
    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelKlient, R.id.btnUpdKlient, (byte)8, this, R.layout.klient_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				if (flag==1) {
    					//MainActivity.db.delRec("karta_klient",id);
    					//getSupportLoaderManager().getLoader(0).forceLoad();
    					int countT=0;
    					Cursor cc = MainActivity.db.getRawData ("select count(*) c from klient T where T.karta="+id,null);
    					   if (cc.moveToFirst()) { 
    					        do {countT=cc.getInt(cc.getColumnIndex("c"));
    					        } while (cc.moveToNext());
    					      };
    					if (countT!=0)      
    					{MainActivity.db.delRec("klient_karta",id);
    					getSupportLoaderManager().getLoader(0).forceLoad();}
    					else 
    						showMessage("Удалять запрещено, у клиента имеются чеки", (byte)1);
    					}
    			}
    		});
    lvData = (ListView) findViewById(R.id.lvKlient);
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
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.klient_ll));
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
    			"select TP._id as _id, TP.name as name, TP.sconto_sum as sconto_sum, TP.sconto_per as sconto_per, substr(TP.data_ins,5)||'.'||substr(TP.data_ins,3,2)||'.'||substr(TP.data_ins,1,2) as data_ins,"
    			        + "TP.adres as adres, TP.telef as telef, TP.prim as prim from karta_klient as TP "
    			, null); //db.getAllData("tmc_pgr");
      return cursor;
    }
     
  }

}

