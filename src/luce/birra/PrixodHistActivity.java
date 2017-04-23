package luce.birra;
import java.util.Calendar;

import luce.birra.AdapterLV.CambiareListener;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class PrixodHistActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnEx, btnAdd;
  AdapterLV scAdapter;
  static TextView tvIdPgr; 
  //static EditText tvDataIns, tvDataIns2;
  static TextView tvDataIns, tvDataIns2, tv_data1_pri, tv_data2_pri;
  Spinner spPgr;
  
  //LinearLayout ll;
  void showMessage(String s, byte dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(PrixodHistActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.prixod_hist);
    //final DialogFragment dlg = new DialogActivity();
    tvDataIns = (TextView) findViewById(R.id.tv_Data_PtiHist);
    tvDataIns.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        	//dlg.show(getSupportFragmentManager(), "dlg");
        }
      });
    tv_data1_pri = (TextView) findViewById(R.id.tv_data1_pri);
    tv_data1_pri.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    tvDataIns2 = (TextView) findViewById(R.id.tv_Data_PtiHist2);
    tvDataIns2.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        	//dlg.show(getSupportFragmentManager(), "dlg");
        }
      });
    tv_data2_pri = (TextView) findViewById(R.id.tv_data2_pri);
    tv_data2_pri.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_PriHist);
    tvIdPgr = (TextView) findViewById(R.id.tv_Id_PgrPriHist);
    tvIdPgr.setText("0");
    // make an adapter from the cursor
    String[] fromPgr = new String[] {"name"};
    int[] toPgr = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_item, c, fromPgr, toPgr);

    sca.setDropDownViewResource(R.layout.spinner_drop_down); 
    spPgr.setAdapter(sca);

    spPgr.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spPgr.setTag(id);
        	tvIdPgr.setText(String.valueOf(id));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText("0");
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        });
    
    btnAdd = (Button) findViewById(R.id.btnAddPrixodHist);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(PrixodHistActivity.this, PrixodActivity.class);
			   startActivity(intent);
        }
      });
    
    btnEx = (Button) findViewById(R.id.btnExcelPrixodHist);
    btnEx.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(PrixodHistActivity.this, PrixodHistActivity.this, tvDataIns.getText().toString(), 
        			tvDataIns2.getText().toString(), 
        			tvIdPgr.getText().toString(), "Приход за период", (byte)4);
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitPrixodHist);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { /*"_id",*/"id_tmc","name",/*"id_post","pname",*/"kol","ted","price"/*, "data_ins" */};
    int[] to = new int[] {/*R.id.tvIdPrixod_,*/ R.id.tvNnomPrixod_, R.id.tvNamePrixod_,/*R.id.tvIdPostPrixod_,R.id.tvNamePostPrixod_,*/R.id.tvKolPrixod_,R.id.tvEdPrixod_,R.id.tvPricePrixod_/*, R.id.tvDataInsPrixod_ */};
    //int[] toH = new int[] {R.id.tvNnomPrixod_,R.id.tvNamePrixod_,R.id.tvNamePostPrixod_,R.id.tvKolPrixod_,R.id.tvEdPrixod_,R.id.tvPricePrixod_,R.id.tvDataInsPrixod_};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelPrixodHist, R.id.btnUpdPrixodHist, (byte)3, this, R.layout.prixod_hist_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				if (flag==1) {
    					float kolo=0, kolp=0;
    					Cursor cc = MainActivity.db.getRawData ("select O.kol kolo, P.kol kolp from ostat O left join prixod P on O.id_tmc=P.id_tmc and O.ed=P.ed and O.id_post=P.id_post where P._id="+id,null);
    					   if (cc.moveToFirst()) { 
    					        do {kolo=cc.getFloat(cc.getColumnIndex("kolo"));
    					        	kolp=cc.getFloat(cc.getColumnIndex("kolp"));
    					        		//MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), -cc.getFloat(cc.getColumnIndex("kol")), (byte)cc.getInt(cc.getColumnIndex("ed")), 0, cc.getInt(cc.getColumnIndex("id_post")), 0, "обнуление остатка id="+id, MainActivity.getIntDataTime(), 0);
    					        		//cc.getInt(cc.getColumnIndex("c"));//+ " count: tmc "+db.getAllData("tmc").getCount());
    					        } while (cc.moveToNext());
    					      };
    					if (kolp<=kolo)      
    					{MainActivity.db.delRec("prixod",id);
    					getSupportLoaderManager().getLoader(0).forceLoad();
    					}
    					else 
    						showMessage("Нельзя удалять, возникнет отрицательный остаток", (byte)1);    					
    				}
    			}
    		});
    lvData = (ListView) findViewById(R.id.lvPrixodHist);
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
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.prixod_hist_ll));
  }
  
  protected Dialog onCreateDialog(int id) {
      if (id == 1) {
        DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return tpd;
      }
      if (id == 2) {
          DatePickerDialog tpd = new DatePickerDialog(this, myCallBack2, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
          return tpd;
        }
      return super.onCreateDialog(id);
    }
     
    OnDateSetListener myCallBack = new OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth) {
      tvDataIns.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
      getSupportLoaderManager().getLoader(0).forceLoad();
    }
    };
    OnDateSetListener myCallBack2 = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {
          tvDataIns2.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
          getSupportLoaderManager().getLoader(0).forceLoad();
        }
        };
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
    	String []str = {(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP.pgr="+tvIdPgr.getText().toString()
    			,/*(tvDataIns.getText().length()==0)?"":" substr(P.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"+
        				"and substr(R.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"*/
    			(tvDataIns.getText().length()==0)?"":" substr(T.data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"
    			,(tvDataIns2.getText().length()==0)?"":" substr(T.data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")"
        				};
        String where=str[0].toString();
        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
        if (where.equals("")||where.length()==0) where=str[2].toString(); else 
        	if (!str[2].equals("")) where=where+" and "+str[2].toString(); 
    	 Cursor cursor = db.getQueryData("prixod as T left join tmc as TP on T.id_tmc = TP._id left join postav as P on T.id_post = P._id left join tmc_ed as E on T.ed = E._id", 
     			new String[] {"T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","T.kol as kol",
    			 "E.name as ted", "T.ed as ed","T.price as price","P.name as pname","T.prim as prim","T.id_post as id_post","TP.pgr as pgr"}, 
     			 where,//"TP.pgr = ?",// and ?",
     			null,//new String[] {tvIdPgr.getText().toString().equals("0")?"TP.pgr":tvIdPgr.getText().toString() //, 
    					//(tvDataIns.getText().length()==0)?"1=1":"substr(T.data_ins,1,6)=trim("+MainActivity.getIntData(tvDataIns.getText().toString())+")"
    					//		},
    					null,null,null);

      return cursor;
    }
     
  }

}

