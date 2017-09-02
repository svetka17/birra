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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import luce.birra.AdapterLV.CambiareListener;
 
public class ProdActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
//GridView lvData;
  Button btnExit, btnAdd, btnPgr;
  RadioGroup rgProd;
  static TextView tvIdPgr;
  static CheckBox cbVis, cbOk;
  AdapterLV scAdapter;
  Spinner spPgr;
  //EditText et;
  LinearLayout ll;
  //LinearLayout ltmp;
  /*
   * @Override
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
 */
  void showMessage(String s, byte dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(ProdActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.prod);
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by _id", null);
    //showMessage("w = "+width+"; h = "+height+"; S = "+scale+"; W = "+display_w+"; H = "+display_h+" SF="+height/15, (byte)1);
    
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Prod);
    //spPgr.setTextSize(20f); 
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
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_item, c, fromPgr, toPgr);

    //((TextView) spPgr.getSelectedView()).setTextColor(0x5e00ff);
    //((TextView) spPgr.getSelectedView()).setTextSize(30);
    
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
        	//((TextView) view).setTextColor(0x5e00ff);
            //((TextView) view).setTextSize(30);
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText(String.valueOf("0"));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        });
  //showMessage("30SP = "+DpToPx(30)+"; W = "+((int)display_w/8)+"; W_DP = "+PxToDp(display_w/8)+"; W_PX = "+DpToPx(display_w/8)+"; S = "+scale+"; W = "+display_w+"; H = "+display_h, (byte)1);
    
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
                    //mInfoTextView.setText("Подумайте ещё раз");
                    break;
                case R.id.rbPriceProd:
                    //mInfoTextView.setText("Отличный выбор!");
                    break;
            }
        }
    });*/
    
    btnAdd = (Button) findViewById(R.id.btnAddProd_);
   // btnAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/15);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(ProdActivity.this, ProdEditActivity.class);
			startActivity(intent);
        }
      });
    
    btnPgr = (Button) findViewById(R.id.btnGoPGR_);
    btnPgr.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//MainActivity.db.updRec("tmc", "pgr", 0);
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
    // формируем столбцы сопоставления
    String[] from = new String[] { "_id", "name", /*"namepgr",*/ "price", "ted", "pos"};
    int[] to = new int[] {R.id.tvId_Prod_, R.id.tvName_Prod_,  /*R.id.tvName_Pgr_Prod_,*/ R.id.tvPriceProd_,R.id.tvEdProd_, R.id.tvPos_Prod_};
    //int[] toH = new int[] {0,0,0,0,R.id.tvId_Prod_, R.id.tvName_Prod_,  /*R.id.tvName_Pgr_Prod_,*/ R.id.tvPriceProd_/*, R.id.tvVisProd_*/};
    // создаем адаптер и настраиваем список
    scAdapter = new AdapterLV(R.id.btn_Del_Prod,R.id.btn_Upd_Prod,(byte)1,this, R.layout.prod_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				if (flag==1) {
    					int countT=0;
    					Cursor cc = MainActivity.db.getRawData ("select count(*) c from ostat T where T.kol<>0 and T.id_tmc="+id,null);
    					   if (cc.moveToFirst()) { 
    					        do {countT=cc.getInt(cc.getColumnIndex("c"));//+ " count: tmc "+db.getAllData("tmc").getCount());
    					        } while (cc.moveToNext());
    					      };
    					if (countT==0)      
    					{MainActivity.db.delRec("tmc",id);
    					getSupportLoaderManager().getLoader(0).forceLoad();}
    					else 
    						showMessage("Удалять запрещено, имеются остатки", (byte)1);
    					}
    			}
    		});
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
    // добавляем контекстное меню к списку
    //registerForContextMenu(lvData);
     
    // создаем лоадер для чтения данных
    getSupportLoaderManager().initLoader(0, null, this);
    //ltmp=(LinearLayout) findViewById(R.id.lllll);
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.prod_ll));
  }
 /*
  // обработка нажатия кнопки
  public void onButtonClick(View view) {
    // добавляем запись
    db.addRec("sometext " + (scAdapter.getCount() + 1), R.drawable.ic_launcher);
    // получаем новый курсор с данными
    getSupportLoaderManager().getLoader(0).forceLoad();
  }*/
   
  /*public void onCreateContextMenu(ContextMenu menu, View v,
      ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
  }
 
  public boolean onContextItemSelected(MenuItem item) {
    if (item.getItemId() == CM_DELETE_ID) {
      // получаем из пункта контекстного меню данные по пункту списка
      AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item
          .getMenuInfo();
      // извлекаем id записи и удаляем соответствующую запись в БД
      MainActivity.db.delRec("tmc",acmi.id);
      // получаем новый курсор с данными
      getSupportLoaderManager().getLoader(0).forceLoad();
      return true;
    }
    return super.onContextItemSelected(item);
  }
 */
  protected void onDestroy() {
    super.onDestroy();
    // закрываем подключение при выходе
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
    	String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" T.pgr="+tvIdPgr.getText().toString()
    			,cbVis.isChecked()?"T.vis=1":"T.vis=0", cbOk.isChecked()?"T.ok=1":"T.ok=0" };
    	
        String where=str[0].toString();
        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[1].toString();
        	//else where=where+str[1].toString();
        if (where.equals("")||where.length()==0) where=str[2].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[2].toString(); 
        	//else where=where+str[2].toString();
       // Log.d("MyLog", "where = " + where);
    	 Cursor cursor = db.getQueryData("tmc as T left join tmc_pgr as TP on T.pgr=TP._id left join tmc_ed as E on T.ed=E._id", 
    			 new String[] {"T._id as _id","T.name as name","T.pgr as pgr","TP.name as namepgr","T.price as price","E._id as ed","E.name as ted", "T.vis as vis", "T.ok as ok","T.tara as tara","T.pos as pos"}, 
     			 where,//"TP.pgr = ?",// and ?",
     			null,//new String[] {tvIdPgr.getText().toString().equals("0")?"TP.pgr":tvIdPgr.getText().toString() //, 
    					//(tvDataIns.getText().length()==0)?"1=1":"substr(T.data_ins,1,6)=trim("+MainActivity.getIntData(tvDataIns.getText().toString())+")"
    					//		},
    					null,null,"T.pgr, T.name");
    	 
      return cursor;
    }
     
  }
}
