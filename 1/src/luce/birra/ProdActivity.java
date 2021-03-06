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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
 
public class ProdActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
//GridView lvData;
  Button btnExit, btnAdd, btnPgr;
  RadioGroup rgProd;
  static TextView tvIdPgr;
  static CheckBox cbVis, cbOk;
  AdapterLV scAdapter;
  Spinner spPgr;
  TextView tv;
  LinearLayout ll;
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
      super.onWindowFocusChanged(hasFocus);
      if(hasFocus) 
	  {
	  
	  ll = (LinearLayout) findViewById(R.id.llHProd);
	  if (scAdapter.wH<15) ll.setVisibility(LinearLayout.GONE);	  
	  else
	  {ll.setVisibility(LinearLayout.VISIBLE);
	  int [] r = {R.id.tvHIdProd,R.id.tvHVisProd,R.id.tvHNameProd,R.id.tvHPgrProd,R.id.tvHTaraProd,R.id.tvHTaraValProd,R.id.tvHPriceProd};
	  for (int i=0;i<r.length;i++) {
		  tv = (TextView) findViewById(r[i]); 
		  tv.getLayoutParams().width = scAdapter.wH;
			}
	      	  }
	  }
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.prod);
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Prod);
    cbVis = (CheckBox) findViewById(R.id.cb_Vis_Prod);
    cbVis.setChecked(true);
    cbVis.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			getSupportLoaderManager().getLoader(0).forceLoad();
		}
	});
    cbOk = (CheckBox) findViewById(R.id.cb_Ok_Prod);
    cbOk.setChecked(false);
    cbOk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			getSupportLoaderManager().getLoader(0).forceLoad();
		}
	});
    tvIdPgr = (TextView) findViewById(R.id.et_Id_PgrProd);
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
    
   /* RadioGroup rgProd = (RadioGroup) findViewById(R.id.rgProd);
    rgProd.clearCheck();
    rgProd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case -1:
                      //mInfoTextView.setText("");
                    break;
                case R.id.rbNameProd:
                    //mInfoTextView.setText("��������� ��� ���");
                    break;
                case R.id.rbPriceProd:
                    //mInfoTextView.setText("�������� �����!");
                    break;
            }
        }
    });*/
    
    btnAdd = (Button) findViewById(R.id.btnAddProd_);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(ProdActivity.this, ProdEditActivity.class);
			startActivity(intent);
        }
      });
    
    btnPgr = (Button) findViewById(R.id.btnGoPGR_);
    btnPgr.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(ProdActivity.this, PgrActivity.class);
			startActivity(intent);
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitProd_);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    // ��������� ������� �������������
    String[] from = new String[] { "_id", "name", /*"namepgr",*/ "price"/*, "vis"*/};
    int[] to = new int[] {R.id.tvId_Prod_, R.id.tvName_Prod_,  /*R.id.tvName_Pgr_Prod_,*/ R.id.tvPriceProd_/*, R.id.tvVisProd_*/};
    //int[] toH = new int[] {0,0,0,0,R.id.tvId_Prod_, R.id.tvName_Prod_,  /*R.id.tvName_Pgr_Prod_,*/ R.id.tvPriceProd_/*, R.id.tvVisProd_*/};
    // ������� ������� � ����������� ������
    scAdapter = new AdapterLV(R.id.btn_Del_Prod,R.id.btn_Upd_Prod,(byte)1,this, R.layout.prod_item, null, from, to, 0);
    lvData = (ListView) findViewById(R.id.lvProd_);
    /*lvData.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id)
        {
            Log.d("MyLog", "FProd click delete "+id);
        }
    });*/
    lvData.setAdapter(scAdapter);
    //Object obj;
    //HeaderViewListAdapter hvlAdapter = (HeaderViewListAdapter) lvData.getAdapter();
    //obj = hvlAdapter.getItemId(0) ;
    //Log.d("MyLog", "hvlAdapter.getItem(1) = " + obj.toString());
    //obj = hvlAdapter.getItem(2);
    //Log.d("MyLog", "hvlAdapter.getItem(4) = " + obj.toString());
   
    //ArrayAdapter<String> alAdapter = (ArrayAdapter<String>) hvlAdapter.getWrappedAdapter();
    //obj = alAdapter.getItem(1);
    //Log.d("MyLog", "alAdapter.getItem(1) = " + obj.toString());
    
    //obj = alAdapter.getItem(2);
    //Log.d("MyLog", "alAdapter.getItem(4) = " + obj.toString());
    // ��������� ����������� ���� � ������
    //registerForContextMenu(lvData);
     
    // ������� ������ ��� ������ ������
    getSupportLoaderManager().initLoader(0, null, this);
    
  }
 /*
  // ��������� ������� ������
  public void onButtonClick(View view) {
    // ��������� ������
    db.addRec("sometext " + (scAdapter.getCount() + 1), R.drawable.ic_launcher);
    // �������� ����� ������ � �������
    getSupportLoaderManager().getLoader(0).forceLoad();
  }*/
   
  /*public void onCreateContextMenu(ContextMenu menu, View v,
      ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
  }
 
  public boolean onContextItemSelected(MenuItem item) {
    if (item.getItemId() == CM_DELETE_ID) {
      // �������� �� ������ ������������ ���� ������ �� ������ ������
      AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item
          .getMenuInfo();
      // ��������� id ������ � ������� ��������������� ������ � ��
      MainActivity.db.delRec("tmc",acmi.id);
      // �������� ����� ������ � �������
      getSupportLoaderManager().getLoader(0).forceLoad();
      return true;
    }
    return super.onContextItemSelected(item);
  }
 */
  protected void onDestroy() {
    super.onDestroy();
    // ��������� ����������� ��� ������
    //db.close();
  }
  
  @Override
  protected void onRestart() {
    super.onRestart();
    getSupportLoaderManager().getLoader(0).forceLoad();
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
    	/*String sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary "
    	        + "from people as PL "
    	        + "inner join position as PS "
    	        + "on PL.posid = PS.id "
    	        + "where salary > ?";*/
   Cursor cursor = db.getQueryData("tmc as T left join tmc_pgr as TP on T.pgr=TP._id", 
    			new String[] {"T._id as _id","T.name as name","T.pgr as pgr","TP.name as namepgr","T.price as price", "T.vis as vis", "T.ok as ok","T.tara as tara"}, 
    			 "? and T.vis = ? and T.ok = ?",
    			 new String[] {(Integer.parseInt(tvIdPgr.getText().toString())==0)?"1=1":"T.pgr="+tvIdPgr.getText().toString(),cbVis.isChecked()?"1":"0",cbOk.isChecked()?"1":"0"},null,null,null);
   
    	//Cursor cursor = db.getRawData (
    		//	"select T._id as _id, T.name as name, substr(T.data_ins,5)||'.'||substr(T.data_ins,3,2)||'.'||substr(T.data_ins,1,2) as data_ins, "
    			//+"T.vis as vis, T.price as price, TP.name as pgr, T.ok as ok, T.data_del as data_del, T.pgr as id_pgr "
    			//"select T._id as _id, "
    			//+"TP.name as nampgr, T.price as price, T.pgr as idpgr "
    			  //      + "from tmc as T "
    				//	+ "left join tmc_pgr as TP "
    			      //  + "on T.pgr = TP._id "
    					//+ "where T.ok = ?"
    			//,null//new String[] {"0"}
    			//);
      return cursor;
    }
     
  }
}
