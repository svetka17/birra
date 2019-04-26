package luce.birra;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import luce.birra.AdapterLV.CambiareListener;
import luce.birra.DialogScreen.DialogListener;
 
public class InvActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button //btnExit, 
  btnAdd, btnSave;
  Button btnMake;
  AdapterLV scAdapter;
  //static CheckBox cbVis;
//  static TextView tvIdPgr;
static TextView tvIdInv, itogSumFact, itogSumReal, itogIN;
TextView tvTextInv;
static TextView tvIdPgr;
Spinner spPgr;
//  Spinner spPgr;
  
  void showMessage(String s, byte dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(InvActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }
  
  /*void makeDialog() {
		DialogScreen getkol = new DialogScreen(InvActivity.this,InvActivity.this,R.id.tvKolReal_Inv)
		 .setDialogScreenListener(new DialogListener() {
			@Override
			public void OnSelectedKol(double k) {
				if (k!=0) dialogNumOK(R.id.tvKolReal_Inv, (float)k); else dialogNumCancel(R.id.tvKolReal_Inv);					
			}
		}) ;getkol.show();
	}*/
  
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.inv);
    tvIdInv = (TextView) findViewById(R.id.tv_Id_Inv);
    tvIdInv.setText("0");
    tvTextInv = (TextView) findViewById(R.id.tv_TextInv);
    tvTextInv.setText("-");
    
    itogSumFact = (TextView) findViewById(R.id.itogInvSumFact);
    itogSumReal = (TextView) findViewById(R.id.itogInvSumReal);
    //itogRaz = (TextView) findViewById(R.id.itogInvRaznica);
    //itogIzlNedo = (TextView) findViewById(R.id.itogInvSumIzlNedo);
    itogIN = (TextView) findViewById(R.id.itogInvRaznicaSum);
    
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by name", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_Inv);

    /*cbVis = (CheckBox) findViewById(R.id.cb_Kol_Inv);
    cbVis.setChecked(false);
    cbVis.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			getSupportLoaderManager().getLoader(0).forceLoad();
		}
	});*/
    tvIdPgr = (TextView) findViewById(R.id.et_Id_PgrInv);
    tvIdPgr.setText("0");
    // make an adapter from the cursor
    String[] fromPgr = new String[] {"name"};
    int[] toPgr = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_item, c, fromPgr, toPgr);

    // set layout for activated adapter
    sca.setDropDownViewResource(R.layout.spinner_drop_down); 
    
    // get xml file spinner and set adapter 
    spPgr.setAdapter(sca);

    spPgr.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spPgr.setTag(id);
        	tvIdPgr.setText(String.valueOf(id));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        	setItog();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText(String.valueOf("0"));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        	setItog();
        }
        });
    
    btnAdd = (Button) findViewById(R.id.btnAddInv);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(InvActivity.this, InvActivity.this, "","", 
        		/*String.valueOf(MainActivity.invent)*/tvIdInv.getText().toString(), "Инвентаризация", (byte)10);
        }
      });
    
    /*btnExit = (Button) findViewById(R.id.btnExitInv);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });*/
    
    btnSave = (Button) findViewById(R.id.btnSaveInv);
    btnSave.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnMake = (Button) findViewById(R.id.btnMakeInv);
    btnMake.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	
        	Cursor cNan = MainActivity.db.getRawData ("select count(*) c  "
    	    		+ "from invent as O where O.id_inv="+MainActivity.invent/*tvIdInv.getText().toString()*/+" and O.kol_real is null",null);
    int nan=-1;
    
    if (cNan.moveToFirst()) { 
    	 
        do {
        	nan=cNan.getInt(cNan.getColumnIndex("c"));
        } while (cNan.moveToNext());
        
      } else cNan.close();
        	
        	
        	if (nan==0) {
        	MainActivity.db.delAll("prixod");
        	MainActivity.db.delAll("rasxod");
        	MainActivity.db.delAll("ostat");
        	Cursor cOst = MainActivity.db.getRawData ("select O._id as id, O.id_tmc as oid_tmc, O.keg as okeg, O.kol_ostat as okol, O.kol_nedo as okol_nedo, O.kol_izl as okol_izl, O.ed as oed, O.id_post as oid_post, O.kol_real kol_real, O.price_vendor ttprice, O.data_ins data_ins "
        	                	    		+ "from invent as O where O.id_inv="+MainActivity.invent/*tvIdInv.getText().toString()*/+" and ifnull(O.kol_real,0)!=0",null);
        	        	   // byte ib=0;
        	        	   // byte tmp=0;
        	        	    
        	        	    if (cOst.moveToFirst()) { 
        	        	    	 
        	        	        do {
        	        	        	MainActivity.db.addRecPRIXOD(cOst.getInt(cOst.getColumnIndex("oid_tmc")), cOst.getInt(cOst.getColumnIndex("okeg")), cOst.getDouble(cOst.getColumnIndex("kol_real")), (byte)cOst.getInt(cOst.getColumnIndex("oed")), cOst.getDouble(cOst.getColumnIndex("ttprice")), cOst.getDouble(cOst.getColumnIndex("ttprice")), cOst.getInt(cOst.getColumnIndex("oid_post")), "после инвентаризации "+cOst.getInt(cOst.getColumnIndex("id"))+MainActivity.usr, MainActivity.getIntDataTime(), (byte)0);
        	        	        	MainActivity.db.updOstatKegInv(cOst.getInt(cOst.getColumnIndex("oid_tmc")), cOst.getInt(cOst.getColumnIndex("oid_post")), cOst.getInt(cOst.getColumnIndex("okeg")), cOst.getInt(cOst.getColumnIndex("oed")), cOst.getInt(cOst.getColumnIndex("data_ins")));
        	        	        	//////
        	        	        	if (cOst.getInt(cOst.getColumnIndex("oed"))==1) {
        	                			Cursor cOst_ = MainActivity.db.getRawData ("select count(*) c from ostat O where O.id_tmc="+cOst.getInt(cOst.getColumnIndex("oid_tmc"))+" and O.id_post="+cOst.getInt(cOst.getColumnIndex("oid_post"))+" and O.ed=1 ",null);
        	            	   int countkeg=-1;    	    
        	            	    if (cOst_.moveToFirst()) { 
        	            	        do {
        	            	        	countkeg=cOst_.getInt(cOst_.getColumnIndex("c"));
        	            	        	MainActivity.db.updOstatOk(cOst.getInt(cOst.getColumnIndex("oid_tmc")), cOst.getInt(cOst.getColumnIndex("oid_post")),cOst.getInt(cOst.getColumnIndex("okeg")), 1, countkeg);
        	            	        } while (cOst_.moveToNext());
        	            	      } else cOst_.close();
        	                		}
        	        	        	//////
        	        	        	
        	        	        } while (cOst.moveToNext());
        	        	        
        	        	      } else cOst.close();
        	        	    MainActivity.inv_dat_n=MainActivity.getIntDataTime();
        	        	    MainActivity.invent=0;
        	        	    finish();
        	}
        	else showMessage("количества факта указаны не по всем позициям", (byte)1);
        }
      });
    
    //ll = (LinearLayout) getLayoutInflater().inflate(R.layout.inv, null);
	//bll = (Button) ll.findViewById(R.id.btnMakeInv);
	//bll.setVisibility(LinearLayout.GONE);
    // формируем столбцы сопоставления
    String[] from = new String[] { 
    		/*"_id",*/"name_pgr","id_tmc","name_tmc","postname","keg","edname",/*"price",*/"price_vendor","kol_ostat","kol_real","summa","summa_fact",/*"izlnedo","izlnedokol",*/"izlnedosum","kol_izl","kol_nedo"
    		};
    int[] to = new int[] {/*R.id.tvId_Inv,*/R.id.tvNamePgr_Inv, R.id.tvId_Tmc_Inv, R.id.tvNameTmc_Inv, R.id.tvNamePost_Inv, R.id.tvKeg_Inv, R.id.tvTed_Inv, /*R.id.tvPrice_Inv,*/ R.id.tvPriceVen_Inv,R.id.tvKol_Inv, R.id.tvKolReal_Inv,R.id.tvSumma_Inv,R.id.tvSummaFact_Inv,/*R.id.tvSummaIzlNedo_Inv,R.id.tvSummaIzlNedo_InvKol,*/R.id.tvSummaIzlNedo_InvSum,R.id.tvKol_Inv_Izl,R.id.tvKol_Inv_Nedo} ;
    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelInv, R.id.btnUpdInv, (byte)15, this, R.layout.inv_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				/*if (flag==11) {

    					final long idd=id;
    					
    					DialogScreen getkol = new DialogScreen(InvActivity.this,InvActivity.this,-1)
    							 .setDialogScreenListener(new DialogListener() {
    								@Override
    								public void OnSelectedKol(double k) {
    									if (k!=0) 
    									{
    										long countT=0;
    				    					Cursor cc = MainActivity.db.getRawData ("select id_tmc from invent where _id="+idd,null);
    				    					   if (cc.moveToFirst()) { 
    				    					        do {countT=1;
    				    					        //Log.d("MyLog", "price="+MainActivity.round2(k) );
    				    					        	MainActivity.db.updRec("invent", idd, "kol_real", MainActivity.round3(k));    				    					        		
    				    					        } while (cc.moveToNext());
    				    					      };
    				    					if (countT!=0)      
    				    					{
    				    					getSupportLoaderManager().getLoader(0).forceLoad();
    				    						//showMessage("Количество обнулено", (byte)1);
    				    					}
    									}				
    								}
    							}) ;getkol.show();
    					
    			} */
    				if (flag==1) {
    					final long iid=id;
    					MainActivity.db.updRec("invent", iid, "kol_real", 0);  
    					getSupportLoaderManager().getLoader(0).forceLoad();
    					setItog();
						//showMessage("Колчество обнулено", (byte)1);		
    				}
    				if (flag==3) {
    					////////////
    					final long iid=id;
    					DialogScreen getYes = new DialogScreen(InvActivity.this,InvActivity.this,R.id.tvKolReal_Inv).setDialogScreenListener(new DialogListener() {
        					
	    					@Override
	    					public void OnSelectedKol(double k) {
	    						//Log.d("MyLog",k+" iid1="+iid );
	    						if (k!=0)
	    						{MainActivity.db.updRec("invent", iid, "kol_real", k);  
	        					getSupportLoaderManager().getLoader(0).forceLoad();
	        					setItog();
	    						}
	    					}
	    				}); 
	    				getYes.show();
    					////////////
						//showMessage("Колчество обнулено", (byte)1);		
    				}
    				}
    		});
    
    
    if( getIntent().getExtras() != null)
    {
    	//Log.d("MyLog","id="+getIntent().getStringExtra("id_inv") );
    	tvIdInv.setText(getIntent().getStringExtra("id_inv"));
    	tvTextInv.setText(getIntent().getStringExtra("text_inv"));
    }
    
    lvData = (ListView) findViewById(R.id.lvInv);
    //lvData.setOnItemClickListener(this);
    /*lvData.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id)
        {
            
        }
    });*/
    lvData.setAdapter(scAdapter);
     
    // создаем лоадер для чтения данных
    getSupportLoaderManager().initLoader(0, null, this);
    
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.inv_ll));
    if (MainActivity.no_inv==0)
    	btnMake.setVisibility(LinearLayout.GONE);
    setItog();
    itogSumFact.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogSumReal.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    //itogRaz.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    //itogIzlNedo.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    itogIN.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    
  }
  
  @Override
  protected void onRestart() {
    super.onRestart();
    getSupportLoaderManager().getLoader(0).forceLoad();
    setItog ();
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

//_id,id_inv,id_tmc,name_tmc,pgr,name_pgr,keg,kol_ostat,kol_real,kol_n,summa_n,kol_p,summa_p,kol_r,summa_r,kol_brak,summa_brak,kol_move,summa_move,kol_izl,summa_izl,kol_nedo,summa_nedo,kol_skidka,kol_k,summa_k,ed,price,price_vendor,id_post,prim,data_ins,ok
    	
    	Cursor cursor = db.getRawData (
    			"select O._id as _id, O.id_tmc id_tmc, O.name_tmc name_tmc, T.name nametmc, O.pgr pgr, O.name_pgr name_pgr, O.ok keg, "
    			+ "round(O.kol_ostat,3) kol_ostat, O.kol_real kol_real, round(O.kol_ostat*O.price_vendor,2) summa, round(O.kol_real*O.price_vendor,2) summa_fact,"
    			+ "O.kol_n kol_n, O.summa_n summa_n, "
    			+ "O.kol_p kol_p, O.summa_p summa_p, O.kol_r kol_r, O.summa_r summa_r, "
    			+ "O.kol_brak kol_brak, O.summa_brak summa_brak, O.kol_move kol_move, O.summa_move summa_move, "
    			+ " case when round(case when ifnull(O.kol_ostat,0)<0 then ifnull(O.kol_real,0) else ifnull(O.kol_real,0)-ifnull(O.kol_ostat,0) end + ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0),3)>0 then round(case when ifnull(O.kol_ostat,0)<0 then ifnull(O.kol_real,0) else ifnull(O.kol_real,0)-ifnull(O.kol_ostat,0) end + ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0),3) else null end kol_izl, "
    			+ "round(O.summa_izl,2) summa_izl, "
    			+ " case when round(case when ifnull(O.kol_ostat,0)<0 then ifnull(O.kol_real,0) else ifnull(O.kol_real,0)-ifnull(O.kol_ostat,0) end + ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0),3)<0 then round(case when ifnull(O.kol_ostat,0)<0 then ifnull(O.kol_real,0) else ifnull(O.kol_real,0)-ifnull(O.kol_ostat,0) end + ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0),3) else null end kol_nedo, "
    			+ "round((-ifnull(O.kol_ostat,0)+ifnull(O.kol_real,0)+ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0))*O.price_vendor,2)+round(O.kol_real*O.price_vendor,2) izlnedo, round(O.summa_nedo,2) summa_nedo, "
    			+ "round(case when ifnull(O.kol_ostat,0)<0 then ifnull(O.kol_real,0) else ifnull(O.kol_real,0)-ifnull(O.kol_ostat,0) end + ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0),3) izlnedokol, "
    			+ "round((case when ifnull(O.kol_ostat,0)<0 then ifnull(O.kol_real,0) else ifnull(O.kol_real,0)-ifnull(O.kol_ostat,0) end + ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0))*O.price_vendor,2) izlnedosum, O.kol_skidka kol_skidka, O.summa_skidka summa_skidka, "
    			+ "O.kol_k kol_k, O.summa_k summa_k, "
    			+ "O.ed ed, E.name edname, O.price price, O.price_vendor price_vendor, O.id_post id_post, P.name postname, O.prim prim, O.data_ins data_ins, O.ok ok "
    			+ "from invent as O "			
    			+ "left join tmc as T "
    			+ "on O.id_tmc=T._id "
    			+ "left join postav as P "
    			+ "on O.id_post=P._id "
    			+ "left join tmc_ed as E "
    			+ "on O.ed=E._id "
    			+ "left join tmc_pgr as TP "
    			+ "on O.pgr=TP._id "
    			+ "where O.id_inv="+ ((Long.parseLong(tvIdInv.getText().toString())<=0)?"-1":tvIdInv.getText().toString()) 
    			+ ((Integer.parseInt(tvIdPgr.getText().toString())==0)?"":" and T.pgr="+tvIdPgr.getText().toString()) 
    			+ " order by O.pgr, O.name_tmc, O.id_post, O.keg"
    			, null);
/*    	
    	Cursor cOst = MainActivity.db.getRawData ("select count(*) c from invent as O "			
    			//+ "left join tmc as T "
    			//+ "on O.id_tmc=T._id "
    			//+ "left join postav as P "
    			//+ "on O.id_post=P._id "
    			//+ "left join tmc_ed as E "
    			//+ "on O.ed=E._id "
    			//+ "left join tmc_pgr as TP "
    			//+ "on O.pgr=TP._id "
    			+ "where O.id_inv="+ ((Long.parseLong(tvIdInv.getText().toString())<=0)?"-1":tvIdInv.getText().toString()) //+ " order by O.pgr, O.name_tmc, O.id_post, O.keg"
    			, null);
int ib=-1;

if (cOst.moveToFirst()) { 
	 
    do {
    	ib=cOst.getInt(cOst.getColumnIndex("c"));
    } while (cOst.moveToNext());
    
  } else cOst.close();

    	Log.d("MyLog", "Selected ID=" + ((Long.parseLong(tvIdInv.getText().toString())<=0)?"-1":tvIdInv.getText().toString())+" "+ib);*/
      return cursor;
    }
     
  }
  
  void setItog () {
  	Cursor cursor = MainActivity.db.getRawData (
  			"select round(sum(ifnull(O.kol_ostat,0)*ifnull(O.price_vendor,0)),2) summa, round(sum(ifnull(O.kol_real,0)*ifnull(O.price_vendor,0)),2) summa_fact, "
  			+ "round(sum((case when ifnull(O.kol_ostat,0)<0 then ifnull(O.kol_real,0) else ifnull(O.kol_real,0)-ifnull(O.kol_ostat,0) end + ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0))*O.price_vendor),2)+round(sum(ifnull(O.kol_real,0)*ifnull(O.price_vendor,0)),2) summa_izlnedo, "
  			+ " round(sum((case when ifnull(O.kol_ostat,0)<0 then ifnull(O.kol_real,0) else ifnull(O.kol_real,0)-ifnull(O.kol_ostat,0) end + ifnull(O.kol_izl,0)-ifnull(O.kol_nedo,0))*O.price_vendor),2) sumin "
  			+ "from invent as O "			
  			+ "left join tmc as T "
  			+ "on O.id_tmc=T._id "
  			//+ "left join postav as P "
  			//+ "on O.id_post=P._id "
  		//	+ "left join tmc_ed as E "
  	//		+ "on O.ed=E._id "
  //			+ "left join tmc_pgr as TP "
//  			+ "on O.pgr=TP._id "
  			+ "where O.id_inv="+ ((Long.parseLong(tvIdInv.getText().toString())<=0)?"-1":tvIdInv.getText().toString()) 
  			+ ((Integer.parseInt(tvIdPgr.getText().toString())==0)?"":" and T.pgr="+tvIdPgr.getText().toString()) 
  			, null);
      			
          	 if (cursor.moveToFirst())  
        		   
       	        do {
       	        	itogSumReal.setText(String.valueOf( MainActivity.round2(cursor.getDouble(cursor.getColumnIndex("summa")) )) );
       	        	itogSumFact.setText(String.valueOf( MainActivity.round2(cursor.getDouble(cursor.getColumnIndex("summa_fact")) )) );
       	        	//itogRaz.setText(String.valueOf(MainActivity.round2(cursor.getDouble(cursor.getColumnIndex("summa_izlnedo"))-cursor.getDouble(cursor.getColumnIndex("summa") ))) );
       	        	//itogIzlNedo.setText(String.valueOf( MainActivity.round2(cursor.getDouble(cursor.getColumnIndex("summa_izlnedo")) )) );
       	        	itogIN.setText(String.valueOf( MainActivity.round2(cursor.getDouble(cursor.getColumnIndex("sumin")) )) );
       	        } while (cursor.moveToNext());
          	        cursor.close();
       	     
  }
  
}
/*

*/



