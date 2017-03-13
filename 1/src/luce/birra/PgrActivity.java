package luce.birra;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

 
public class PgrActivity extends FragmentActivity implements /*OnItemClickListener,*/ LoaderCallbacks<Cursor> {
//static int ww=0;
  ListView lvData;
	//PinnedSectionListView plvData;
	//GridView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  TextView tv;
  LinearLayout ll;
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
      super.onWindowFocusChanged(hasFocus);
      if(hasFocus) 
      {
    	  
    	  ll = (LinearLayout) findViewById(R.id.llHPgr);
    	  if (scAdapter.wH<15) ll.setVisibility(LinearLayout.GONE);	  
    	  else
    	  {ll.setVisibility(LinearLayout.VISIBLE);
    	  int [] r = {R.id.tvHIdPgr,R.id.tvHNamePgr,R.id.tvHDataPgr};
    	  for (int i=0;i<r.length;i++) {
    		  tv = (TextView) findViewById(r[i]); 
    		  tv.getLayoutParams().width = scAdapter.wH;
    			}
    	      	  }
    	  }
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
    // ��������� ������� �������������
    String[] from = new String[] { "_id","name"/*, "data_ins"*/ };
    int[] to = new int[] {R.id.tvIdPgr, R.id.tvNamePgr/*, R.id.tvDataInsPgr*/  };
    //int[] toH = new int[] {R.id.tvIdPgr, R.id.tvNamePgr, R.id.tvDataInsPgr  };
    // ������� ������� � ����������� ������ ������� ������ ���, ���, ��� �������
    scAdapter = new AdapterLV(R.id.btnDelPgr, R.id.btnUpdPgr, (byte)2, this, R.layout.pgr_item, null, from, to, 0);
    
    lvData = (ListView) findViewById(R.id.lvPgr);
    //lvData = (GridView) findViewById(R.id.lvPgr);
    //lvData.setOnItemClickListener(this);
    /*!!!!!!!!!!!lvData.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id)
        {
            Log.d("MyLog", position+" "+itemClicked.getId()+" FPgr click delete "+id);
        }
    });*/
    //plvData = (PinnedSectionListView) lvData;
    lvData.setAdapter(scAdapter);

    // ������� ������ ��� ������ ������
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
    	
    	Cursor cursor = db.getRawData (
    			"select TP._id as _id, TP.name as name, TP.data_ins as data_ins "
    			        + "from tmc_pgr as TP "
    			, null); //db.getAllData("tmc_pgr");
      return cursor;
    }
     
  }

}

