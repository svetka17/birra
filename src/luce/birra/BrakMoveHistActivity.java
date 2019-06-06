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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import luce.birra.AdapterLV.CambiareListener;
import luce.birra.DialogScreen.DialogListener;
 
public class BrakMoveHistActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnAdd;
  AdapterLV scAdapter;
  static TextView tvIdPgr; 
  //static RadioButton rbBrak;//, rbMove;
  static RadioGroup radioGroup;
  static int brak=1;
  static TextView itogKol;
  Spinner spPgr;
  //Cursor cKlient;
  static int tmp=0;
  //LinearLayout ll;
 static long idd=0;
 //RadioButton checkedRadioButton;
 /*void showMessage(String s, byte dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(BrakMoveHistActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
 }*/
 
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.brakmove_hist);
    //final DialogFragment dlg = new DialogActivity();
    itogKol = (TextView) findViewById(R.id.itogBrakKol);
    
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by name", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_BrakHist);
    tvIdPgr = (TextView) findViewById(R.id.tv_Id_PgrBrakHist);
    tvIdPgr.setText("0");
    
    radioGroup = (RadioGroup) findViewById(R.id.rgBrakMoveHist);        
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() 
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
        	//View radioButton = radioGroup.findViewById(checkedId);
            //int index = radioGroup.indexOfChild(radioButton);
        	//RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
//showMessage("index="+index, (byte)0);
            //boolean isChecked = checkedRadioButton.isChecked();
            // If the radiobutton that has changed in check state is now checked...
        	switch (checkedId) {
            case R.id.rbBrak:
            	brak=1;
            	//group.clearCheck();
            	//checkedRadioButton = (RadioButton)group.findViewById(R.id.rbBrak);
            	//checkedRadioButton.setChecked(true);
            	getSupportLoaderManager().getLoader(0).forceLoad();
            	setItog();
                break;
            case R.id.rbMove:
            	brak=2;
            	//group.clearCheck();
            	//checkedRadioButton = (RadioButton)group.findViewById(R.id.rbMove);
            	//checkedRadioButton.setChecked(true);
            	getSupportLoaderManager().getLoader(0).forceLoad();
            	setItog();
                break;
           
            default:
            	brak=1;
            	//group.clearCheck();
            	//checkedRadioButton = (RadioButton)group.findViewById(R.id.rbBrak);
            	//checkedRadioButton.setChecked(true);
            	getSupportLoaderManager().getLoader(0).forceLoad();
            	setItog();
                break;
        }


        }
    });
           
    
    /*    rbBrak = (RadioButton) findViewById(R.id.rbBrak);
    rbMove = (RadioButton) findViewById(R.id.rbMove);
    
    rbBrak.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getSupportLoaderManager().getLoader(0).forceLoad();
			setItog();
		}
	});
rbMove.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getSupportLoaderManager().getLoader(0).forceLoad();
			setItog();
		}
	});*/
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
        	//getSupportLoaderManager().getLoader(2).forceLoad();
        	setItog();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText("0");
        	getSupportLoaderManager().getLoader(0).forceLoad();
        	//getSupportLoaderManager().getLoader(2).forceLoad();
        	setItog();
        }
        });
    
    btnExit = (Button) findViewById(R.id.btnExitBrakHist);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnAdd = (Button) findViewById(R.id.btnExcelBrakHist);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(BrakMoveHistActivity.this, BrakMoveHistActivity.this, "","", 
        			tvIdPgr.getText().toString(), "Перемещение и брак", 11);
        	}
      });
    // формируем столбцы сопоставления
    String[] from = new String[] { /*"_id",*/"id_tmc","pgr","name","pname","kol","ed",/*"data_ins",*/"prim","keg"};
    int[] to = new int[] {/*R.id.tv_Id_Rasxod_Hist,*/ R.id.tvId_BrakMoveHist, R.id.tvNamePgr_BrakMoveHist, R.id.tvNameTmc_Brak, R.id.tvNamePost_BrakMoveHist,
    		R.id.tvKol_BrakMoveHist,R.id.tvTed_BrakMoveHist,/*R.id.tvDataIns_BrakMoveHist,*/R.id.tvPrim_BrakMoveHist,R.id.tvKeg_BrakMoveHist};
    //int[] toH = new int[] {R.id.tv_Nnom_Rasxod_Hist,R.id.tv_Name_Rasxod_Hist,R.id.tv_Kol_Rasxod_Hist,R.id.tv_Price_Rasxod_Hist,R.id.tv_DataIns_Rasxod_Hist,R.id.tv_Prim_Rasxod_Hist,R.id.tv_CH_Rasxod_Hist,R.id.tv_Sumka_Rasxod_Hist};

    // создаем адаптер и настраиваем список сначала кнопка Дел, Апд, имя таблицы
    scAdapter = new AdapterLV(R.id.btnDelBrakMoveHist, R.id.btnUpdBrakMoveHist, 16, this, R.layout.brakmove_hist_item, null, from, to, 0)
    		.setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(int flag, long id) {
    				idd=id;
    				if (flag==1) {
    					DialogScreen getYes = new DialogScreen(BrakMoveHistActivity.this,BrakMoveHistActivity.this,-5).setDialogScreenListener(new DialogListener() {
							
							@Override
							public void OnSelectedKol(double k) {
								if (k==1) {
									//удаляем из чека			    					
			    					MainActivity.db.delRec("rasxod",idd);
			    					getSupportLoaderManager().getLoader(0).forceLoad();
			    					setItog();
								}
								
							}
						}); getYes.show();
    					
    					}
    			}
    		});
    lvData = (ListView) findViewById(R.id.lvBrakHist);
    lvData.setAdapter(scAdapter);
    

    // создаем лоадер для чтения данных
    
    /*tmp=0;
    if( getIntent().getExtras() != null)
    {
    	
    	tvDataIns.setText(getIntent().getStringExtra("dat1"));
    	tvDataIns2.setText(getIntent().getStringExtra("dat2"));
    	tmp=Integer.parseInt(getIntent().getStringExtra("id_check"));
    }*/
    
    //getSupportLoaderManager().initLoader(1, null, this);
    //getSupportLoaderManager().initLoader(2, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    setItog();
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.brakmove_hist_ll));
    itogKol.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);

  }

  @Override
  protected void onRestart() {
    super.onRestart();
    //getSupportLoaderManager().getLoader(1).forceLoad();
    getSupportLoaderManager().getLoader(0).forceLoad();
    setItog();
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
 
  @Override
  
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
     // Log.d("Loader<Cursor> onCreateLoader", String.valueOf(i));
      if (i == 0 ) return new RasxodLoader(this, MainActivity.db);
     // if (i == 1 )  return new KlientLoader(this, MainActivity.db);
     // if (i == 2 )  return new ProdLoader(this, MainActivity.db);
      return null;
  }
  
  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	  switch(loader.getId())
      {
      case 0:
    	  scAdapter.swapCursor(cursor);
       break;
   }
	  
  }
 
  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }
   
  static class RasxodLoader extends CursorLoader {
 
    DB db;
    public RasxodLoader(Context context, DB db/*, int id*/) {
      super(context);
      this.db = db;
    }
     
    @Override
    public Cursor loadInBackground() {
    	String str = ((tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?" 1=1 ":" TP.pgr="+tvIdPgr.getText().toString()) + " and T.ok = "+brak;//(rbBrak.isChecked()?1:(rbMove.isChecked()?2:-1));

            	 Cursor cursor = db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id left join postav as P on T.id_post=P._id", 
             			new String[] {"T.keg as keg","P.name as pname","T._id as _id","T.id_tmc as id_tmc","TP.name as name","T.data_ins as data_ins","round(T.kol,3) as kol",
            			 "E.name as ted", "T.ed as ed","T.price as price","T.prim as prim","TT.name as pgr"}, 
             			 //"TP.pgr = ?"
            			 str, null,null,null,"T.data_ins desc");
      return cursor;
    }
     
  }
  
  void setItog () {
	  String str = ((tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?" 1=1 ":" TP.pgr="+tvIdPgr.getText().toString()) + " and T.ok = "+brak;//(rbBrak.isChecked()?1:(rbMove.isChecked()?2:-1));

	  Cursor cursor = MainActivity.db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id left join postav as P on T.id_post=P._id", 
   			new String[] {"sum(round(T.kol,3)) as kol"}, 
   			 //"TP.pgr = ?"
  			 str, null,null,null,null);
	  
	        	 if (cursor.moveToFirst())  
	      		   
	     	        do {
	     	        	itogKol.setText(String.valueOf(MainActivity.round3( cursor.getDouble(cursor.getColumnIndex("kol"))) ) );
	     	        } while (cursor.moveToNext());
	     	      
	        	        cursor.close();
	     	     
	}
  

}


