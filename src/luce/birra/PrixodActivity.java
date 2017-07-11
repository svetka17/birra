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
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class PrixodActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {
 
  Button btnExit, btnAdd, btnSProd, btnSPost, btnHist;
  Spinner spProd, spPost, spPgr;
  TextView spEd;
  TextView tvId,  tvEd, tvIdProd, tvOst;
  static TextView tvIdPgr, tvIdPost;
  EditText tvKol, tvPrice, tvSumma, tvPriceVendor, tvDataIns,tvPrim, tvPos;
  Cursor// cProd, 
  cPost,  cPgr;//, cEd;
  static Cursor cursor;
  static byte flagFocus=0;
  static int tmp=0;
  SimpleCursorAdapter scaProd, scaPost, scaPgr;//, scaEd;
  String[] fromProd, fromPost, fromPgr;//, fromEd;
  int[] toProd, toPost, toPgr;//, toEd;
  String s;
  
  void showMessage(String s, byte dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(PrixodActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.prixod);
    tvId = (TextView) findViewById(R.id.tvIdPri);
    tvOst = (TextView) findViewById(R.id.tvOstatokPri);
    //etPgr = (EditText) findViewById(R.id.etEditIdPgrProd);
    tvIdProd = (TextView) findViewById(R.id.tvIdProdPri);
    tvPos = (EditText) findViewById(R.id.tvPosPri);
    tvIdProd.setText("0");
    tmp=0;
    tvIdPgr = (TextView) findViewById(R.id.tvIdPgrPri);
    tvIdPgr.setText("0");
    tvIdPost = (TextView) findViewById(R.id.tvIdPostPri);
    tvIdPost.setText("0");
    tvDataIns = (EditText) findViewById(R.id.tvDatInsPri);
    /*tvDataIns = (CalendarView) findViewById(R.id.tvDatInsPri);
    tvDataIns.setOnDateChangeListener(new OnDateChangeListener() {
		@Override
		public void onSelectedDayChange(CalendarView view, int year,
				int month, int dayOfMonth) {
			int mYear = year;
			int mMonth = month;
			int mDay = dayOfMonth;
			String selectedDate = new StringBuilder().append(mMonth + 1)
					.append("-").append(mDay).append("-").append(mYear)
					.append(" ").toString();
			Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();

		}
	});*/
    tvKol = (EditText) findViewById(R.id.tvKolPri);
    tvEd = (TextView) findViewById(R.id.tvIdEdPri);
    tvPrice = (EditText) findViewById(R.id.tvPricePri);
    tvPrice.setText("0");
    tvSumma = (EditText) findViewById(R.id.tvSummaPri);
    tvSumma.setText("0");
    tvSumma.setOnFocusChangeListener(new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) flagFocus=1;
			if (!hasFocus && flagFocus==1 && MainActivity.StrToFloat(tvKol.getText().toString())>0) 
				{flagFocus=0;
				tvPrice.setText(
						String.valueOf( MainActivity.round (MainActivity.StrToFloat(tvSumma.getText().toString())/ MainActivity.StrToFloat(tvKol.getText().toString()),2)
								));
				}
		}
	});
    
    
    tvPriceVendor = (EditText) findViewById(R.id.tvPricePriVendor);
    
    //android:layout_height="?android:attr/listPreferredItemHeight"
    tvPrim = (EditText) findViewById(R.id.tvPrimPri);
    spProd = (Spinner) findViewById(R.id.spProdPri);
    spPost = (Spinner) findViewById(R.id.spPostPri);
    spPgr = (Spinner) findViewById(R.id.spPgrPri);
    spEd = (TextView) findViewById(R.id.spEdPri);
    tvPrim.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvPrim.setText("����� �����");
    tvDataIns.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvPrice.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvSumma.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvPriceVendor.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvKol.setImeOptions(EditorInfo.IME_ACTION_DONE);
    //cProd = MainActivity.db.getRawData("select _id, name, pgr,ed,ted from (select _id, name,0 pgr, 0 ed, '-' ted from foo union all select T._id as _id, T.name as name, T.pgr as pgr, T.ed as ed, E.name as ted from tmc as T left join tmc_ed as E on T.ed=E._id) where pgr = "+((tvIdPgr.getText().equals("0") )?"pgr":tvIdPgr.getText()), null);
    //cProd = MainActivity.db.getRawData("select _id, name,0 pgr, 0 ed, '-' ted from foo union all select T._id as _id, T.name as name, T.pgr as pgr, T.ed as ed, E.name as ted from tmc as T left join tmc_ed as E on T.ed=E._id", null);
    cPost = MainActivity.db.getRawData("select _id, name from postav", null);
    cPgr = MainActivity.db.getRawData("select _id, name from tmc_pgr", null);
    /////////////
    spPgr = (Spinner) findViewById(R.id.spPgrPri);
    fromPgr = new String[] {"name"};
    toPgr = new int[] {android.R.id.text1};
    scaPgr = new SimpleCursorAdapter(this, R.layout.spinner_item, cPgr, fromPgr, toPgr);
    scaPgr.setDropDownViewResource(R.layout.spinner_drop_down); 
    spPgr.setAdapter(scaPgr);
    spPgr.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
    //cEd = MainActivity.db.getRawData("select _id, name from foo union all select _id, name from tmc_ed", null);
    // make an adapter from the cursor
    fromProd = new String[] {"name"};
    toProd = new int[] {android.R.id.text1};
    fromPost = new String[] {"name"};
    toPost = new int[] {android.R.id.text1};
    //fromEd = new String[] {"name"};
    //toEd = new int[] {android.R.id.text1};
    scaProd = new SimpleCursorAdapter(this, R.layout.spinner_item, null, fromProd, toProd);
    scaPost = new SimpleCursorAdapter(this, R.layout.spinner_item, cPost, fromPost, toPost);
    //scaEd = new SimpleCursorAdapter(this, R.layout.spinner_item, cEd, fromEd, toEd);
    // set layout for activated adapter
    scaProd.setDropDownViewResource(R.layout.spinner_drop_down); 
    scaPost.setDropDownViewResource(R.layout.spinner_drop_down);
    //scaEd.setDropDownViewResource(R.layout.spinner_drop_down);
    // get xml file spinner and set adapter 
    /*spProd.setPrompt("������������");
    spPost.setPrompt("���������");
    spPost.setPrompt("������� ���������");
    */
    spProd.setAdapter(scaProd);
    spPost.setAdapter(scaPost);
    //spEd.setAdapter(scaEd);
    // set spinner listener to display the selected item id
    //mContext = this;
    spProd.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spProd.setTag(id);
        	//Log.d("MyLog", "onitemselect id="+id);
        	tvIdProd.setText(String.valueOf(id));
        	s=/*cProd*/cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("name"));
        	tvEd.setText(/*cProd*/cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("ed")) );
        	//String ss=String.valueOf( cursor.getDouble(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("ost")) );
        	tvOst.setText(cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("ost")) );
        	//tvPos.setText(/*cProd*/cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("pos")) );
        	//Log.d("MyLog", "cursor pos="+ position+ " "+id + " ost="+tvOst.getText());
        	//String ss=cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("pos"));
        	String ss="-";
        	int tmpi=-1; 
        	double pr=0;
        	try {
        	ss=cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("ted"));
        	tmpi=cursor.getInt(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("pos"));
        	pr=cursor.getDouble(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("price"));
        	//if (ss.equals("�")) tvPrim.setText("����� ����"); 
        	//else if (tvPrim.getText().equals("����� ����") ) tvPrim.setText("");
        	}
        	catch (Exception e) {
        		//Log.d("MyLog", "catch tved=" );
			}
        	
        	spEd.setText( ss );
        	
        	if (tmpi!=-1)
    		{tvPos.setText(String.valueOf(tmpi));
    		}
    	else {
    		tvPos.setText("");
    	}
        	
        	if (tvIdPost.getText().equals("0"))
        	if (pr!=0) tvPriceVendor.setText(String.valueOf(pr)); else tvPriceVendor.setText(""); 
        	setPrice();
        	//Log.d("MyLog", "end itemselect" );
        	//Log.d("MyLog", "cursor pos="+ position+ " "+id + " ted="+spEd.getText()+" pos="+ss);
        	
        	//Log.d("MyLog", "cursor pos="+ position+ " "+id + " ost="+ss
        	///*cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("ed"))*/ );
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spProd.setTag(0);
        	tvIdProd.setText(String.valueOf("0"));
        	tvOst.setText("0");
        	tvPos.setText("");
        	tvPriceVendor.setText("");
        }
        });
    
    spPost.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spPost.setTag(id);
        	tvIdPost.setText(String.valueOf(id));
        	setPrice();
        	//getSupportLoaderManager().getLoader(0).forceLoad();
        	//tvOst.setText(/*cProd*/cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("ost")) );
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPost.setTag(0);
        	tvIdPost.setText(String.valueOf("0"));
        	//getSupportLoaderManager().getLoader(0).forceLoad();
        	//tvOst.setText(/*cProd*/cursor.getString(((Cursor)spProd.getItemAtPosition(1)).getColumnIndex("ost")) );
        }
        });
    /*spEd.setSelection(0);
    spEd.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spEd.setTag(id);
        	tvEd.setText(String.valueOf(id));
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spEd.setTag(1);
        	tvEd.setText(String.valueOf("1"));
        }
        });*/
    
    //tvData = (TextView) findViewById(R.id.etEditDatIns);
    btnAdd = (Button) findViewById(R.id.btnAddPri);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//if (MainActivity.postlitr==1 && Integer.parseInt(tvIdPost.getText().toString())!=0)
        	if  (0!=Integer.parseInt(tvEd.getText().toString()) && 0!=Integer.parseInt(tvIdProd.getText().toString()) && tvPrice.getText().toString().toString().length()!=0 && tvPriceVendor.getText().toString().toString().length()!=0 && tvKol.getText().toString().length()!=0
        			&& !(MainActivity.postlitr==1 && Integer.parseInt(tvIdPost.getText().toString())==0 && Byte.parseByte(tvEd.getText().toString())==1 ))
  			 {if ((tvId.getText().toString().length()==0)) 
        		//MainActivity.db.addRecPRIXOD(id_tmc, kol, price, id_post, prim, data_del, data_ins, ok);
        		{MainActivity.db.addRecPRIXOD(
        				Integer.parseInt(tvIdProd.getText().toString()), 
        				MainActivity.round( Float.parseFloat(tvKol.getText().toString()) ,3), 
        				Byte.parseByte(tvEd.getText().toString()),
        				MainActivity.round( Float.parseFloat(tvPrice.getText().toString()) ,2), 
        				MainActivity.round( Float.parseFloat(tvPriceVendor.getText().toString()) ,2), 
        				Integer.parseInt(tvIdPost.getText().toString()), 
        				MainActivity.usr+" "+tvPrim.getText().toString()+" �����:"+String.valueOf(MainActivity.StrToFloat(tvKol.getText().toString())*MainActivity.StrToFloat(tvPrice.getText().toString()) )+"���", 
        				//0, 
        				(tvDataIns.getText().toString().length()==0)?MainActivity.getIntDataTime():MainActivity.getIntDataTime(tvDataIns.getText().toString()) , 
        				(byte)0); 
        		if ( MainActivity.StrToFloat(tvPos.getText().toString())!=0 ) 
        			MainActivity.db.updRec("tmc", Integer.parseInt(tvIdProd.getText().toString()), "pos", (int)MainActivity.StrToFloat(tvPos.getText().toString()) ); 
        				//(Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id"))
        		//Log.d("MyLog", "datatime "+MainActivity.getIntDataTime()+" ddd"+tvDataIns.getText() );
        		//Toast.makeText(PrixodActivity.this, "������ "+s+" ���-��:"+tvKol.getText().toString()+" ����:"+tvPrice.getText().toString(), Toast.LENGTH_LONG).show();
        		tvOst.setText(String.valueOf(MainActivity.StrToFloat(tvOst.getText().toString())+MainActivity.round(Float.parseFloat(tvKol.getText().toString()),3) ));
        		showMessage("������ "+s+" ���-��:"+tvKol.getText().toString()+" ����:"+tvPrice.getText().toString(), (byte)0);
        		tvPrim.setText("");
        		}
  			 else
  			 {
  				MainActivity.db.updRec("prixod", Integer.parseInt(tvId.getText().toString()), 
  	        			"prim", MainActivity.usr+" "+tvPrim.getText().toString()+" �����:"+String.valueOf(MainActivity.StrToFloat(tvKol.getText().toString())*MainActivity.StrToFloat(tvPrice.getText().toString()) )+"���" );	
  	        	MainActivity.db.updRec("prixod", Integer.parseInt(tvId.getText().toString()), 
  	        			new String[] {"data_ins","id_tmc","id_post","ed"}, 
  	        			new int[] { (tvDataIns.getText().toString().length()==0)?MainActivity.getIntDataTime():MainActivity.getIntDataTime(tvDataIns.getText().toString()),
  	        				Integer.parseInt(tvIdProd.getText().toString()),
  	        				Integer.parseInt(tvIdPost.getText().toString()),
  	        				Integer.parseInt(tvEd.getText().toString())}); 
  	        	MainActivity.db.updRec("prixod", Integer.parseInt(tvId.getText().toString()), 
  	        			new String[] {"price","price_vendor","kol"}, new double[] {MainActivity.round(Float.parseFloat(tvPrice.getText().toString()),2),MainActivity.round(Float.parseFloat(tvPriceVendor.getText().toString()),2),MainActivity.round(Float.parseFloat(tvKol.getText().toString()),3)});  				 
  				//Toast.makeText(PrixodActivity.this, "������ ������� "+s+" ���-��:"+tvKol.getText().toString()+" ����:"+tvPrice.getText().toString(), Toast.LENGTH_LONG).show();
  	        	if ( MainActivity.StrToFloat(tvPos.getText().toString())!=0 ) 
        			MainActivity.db.updRec("tmc", Integer.parseInt(tvIdProd.getText().toString()), "pos", (int)MainActivity.StrToFloat(tvPos.getText().toString()) );
  	        	
  	        	showMessage("������ ������� "+s+" ���-��:"+tvKol.getText().toString()+" ����:"+tvPrice.getText().toString(), (byte)0);
  				finish();
  			 }
        	
  			}
        	else tvPrice.requestFocus();
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitPri);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnSProd = (Button) findViewById(R.id.btnSprProdPri);
    btnSProd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(PrixodActivity.this, ProdActivity.class);
     	   	startActivity(intent);
        }
      });
    
    btnSPost = (Button) findViewById(R.id.btnSprPostPri);
    btnSPost.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(PrixodActivity.this, PostavActivity.class);
     	   	startActivity(intent);
        }
      });
    
    btnHist = (Button) findViewById(R.id.btnHistPri);
    btnHist.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(PrixodActivity.this, PrixodHistActivity.class);
     	   	startActivity(intent);
        }
      });
    
    getSupportLoaderManager().initLoader(0, null, this);
    if( getIntent().getExtras() != null)
    {
    	tvIdPgr.setText(getIntent().getStringExtra("PrixodPgr"));
    	  	
    	tvIdPost.setText(getIntent().getStringExtra("PrixodPost"));
    	//Log.d("MyLog", "prixod post="+getIntent().getStringExtra("PrixodPost") );
    	MainActivity.setSpinnerItemById(spPost, (int) MainActivity.StrToFloat(getIntent().getStringExtra("PrixodPost")) /*Integer.parseInt(getIntent().getStringExtra("PrixodPost"))*/);
    	
    	//Log.d("MyLog", "prixod ed="+getIntent().getStringExtra("PrixodEd") );
    	tvEd.setText(getIntent().getStringExtra("PrixodEd"));
    	//spEd.setSelection(Integer.parseInt(getIntent().getStringExtra("PrixodEd")));
    	//MainActivity.setSpinnerItemById(spEd, Long.parseLong(getIntent().getStringExtra("PrixodEd")));
    	//tvIdProd.setText(getIntent().getStringExtra("PrixodProd"));
    	//Log.d("MyLog", "prixod prod="+getIntent().getStringExtra("PrixodProd")+" "+Integer.parseInt(getIntent().getStringExtra("PrixodProd"))+" "+((long) MainActivity.StrToFloat(getIntent().getStringExtra("PrixodProd"))) );
    	tmp=((int) MainActivity.StrToFloat(getIntent().getStringExtra("PrixodProd")));
    	//MainActivity.setSpinnerItemById(spProd, tmp/*(long) MainActivity.StrToFloat(getIntent().getStringExtra("PrixodProd"))*/ /*Integer.parseInt(getIntent().getStringExtra("PrixodProd"))*/);

    	//spPost.setSelection(Integer.parseInt(getIntent().getStringExtra("PrixodPost")));
    	tvPrice.setText(getIntent().getStringExtra("PrixodPrice"));
    	//Log.d("MyLog", "prixod price="+getIntent().getStringExtra("PrixodPrice") );
    	tvKol.setText(getIntent().getStringExtra("PrixodKol"));
    	//Log.d("MyLog", "prixod kol="+getIntent().getStringExtra("PrixodKol") );
    	//spProd.setSelection(Integer.parseInt(getIntent().getStringExtra("PrixodProd")));
    	
    	tvPrim.setText(getIntent().getStringExtra("PrixodPrim"));
    	tvDataIns.setText(MainActivity.getStringDataTime((int) MainActivity.StrToFloat(getIntent().getStringExtra("PrixodDataIns"))/*Integer.parseInt(getIntent().getStringExtra("PrixodDataIns"))*/));
    	//Log.d("MyLog", "prixod data="+getIntent().getStringExtra("PrixodDataIns") );
    	tvId.setText(getIntent().getStringExtra("PrixodId"));
    	//Log.d("MyLog", "prixod id="+getIntent().getStringExtra("PrixodId") );
    	//Log.d("MyLog", "extra id_tmc="+getIntent().getStringExtra("PrixodProd"));
    	    	
    	tvSumma.setText(String.valueOf(MainActivity.StrToFloat(tvKol.getText().toString())*MainActivity.StrToFloat(tvPrice.getText().toString()) ));
    	//setSpinnerItemById(spPgr, Integer.parseInt(getIntent().getStringExtra("PrixodPgr")));
    }
    
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.prixod_ll));
  }
  
/*
  void setSpinnerItemById(Spinner spinner, int _id)
  {
      int spinnerCount = spinner.getCount();
      for (int i = 0; i < spinnerCount; i++)
      {
          Cursor value = (Cursor) spinner.getItemAtPosition(i);
          long id = value.getLong(value.getColumnIndex("_id"));
          if (id == _id)
          {
              spinner.setSelection(i);
          }
      }
  }*/
  
  @Override
  protected void onRestart() {
    super.onRestart();
    getSupportLoaderManager().getLoader(0).forceLoad();
    //spPost.setAdapter(scaPost);
    //spPost.getAdapter().notify();
    //scaPost.changeCursor(cPost);// swapCursor(cPost);
  } 
  
  protected void onDestroy() {
    super.onDestroy();
  }

@Override
public Loader<Cursor> onCreateLoader(int i, Bundle arg1) {

    if (i == 0 ) return new ProdLoader(this, MainActivity.db);
	return null;
}

@Override
public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

	scaProd.swapCursor(cursor);
	MainActivity.setSpinnerItemById(spProd, tmp/*(long) MainActivity.StrToFloat(getIntent().getStringExtra("PrixodProd"))*/ /*Integer.parseInt(getIntent().getStringExtra("PrixodProd"))*/);
	tmp=0;
	/*	switch(loader.getId())
    {
    case 0:
  	  scaProd.swapCursor(cursor);
     break;
    
 }*/
}

@Override
public void onLoaderReset(Loader<Cursor> arg0) {

}

void setPrice () {
	
        	 Cursor cursor = MainActivity.db.getQueryData
("tmc_price as T",
         			new String[] {"T.price as price"}, 
         			 //"TP.pgr = ?"
         			" T.id_tmc="+tvIdProd.getText().toString()+" and T.id_post="+tvIdPost.getText().toString()
        			 , null,null,null,null);
        	 if (cursor.moveToFirst())  
      		   
     	        do {//tmpKol=cursor.getDouble(cursor.getColumnIndex("kol"));
     	        	//tmpSum=cursor.getDouble(cursor.getColumnIndex("s"));
     	        	if ( cursor.getDouble(cursor.getColumnIndex("price")) != 0 )
     	        	tvPriceVendor.setText(String.valueOf( cursor.getDouble(cursor.getColumnIndex("price")) ) );
     	        	
     	        } while (cursor.moveToNext());
     	      
        	        cursor.close();    	     
}

static class ProdLoader extends CursorLoader {
	  
    DB db;
    public ProdLoader(Context context, DB db) {
      super(context);
      this.db = db;
    }
     
    @Override
    public Cursor loadInBackground() {
    	/*String []str = {
    			(tvDataIns.getText().length()==0)?"":" substr(data_ins,1,6)>=trim("+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+")"
    			,(tvDataIns2.getText().length()==0)?"":" substr(data_ins,1,6)<=trim("+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+")"
        				};
    	String where=str[0].toString();
        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
        if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
        if (!where.equals("")) where=" where "+where;*/
        cursor = MainActivity.db.getRawData(
        "select _id, name, pgr,ed,ted, ost, pos, price from (select -1 _id, name,0 pgr, 0 ed, '-' ted, 0 ost, -1 pos, 0 price from foo union all select T._id as _id, T.name as name, T.pgr as pgr, T.ed as ed, E.name as ted, sum(O.kol) as ost, ifnull(T.pos,-1) as pos, T.price as price from tmc as T left join tmc_ed as E on T.ed=E._id left join ostat as O on T._id=O.id_tmc where T.vis=1 "
        //((tvIdPost.getText().equals("") )?"0":tvIdPost.getText())
        +" group by T._id, T.name, T.pgr, T.ed, E.name, ifnull(T.pos,-1), T.price ) where pgr = "
        +((tvIdPgr.getText().equals("0") )?"pgr":tvIdPgr.getText())//+((tmp==0)?"":" and _id="+tmp)
        +" order by name"
        , null);    
    	/*cursor = MainActivity.db.getRawData(
    	        "select T._id as _id, T.name as name, T.pgr as pgr, T.ed as ed, E.name as ted, sum(O.kol) as ost, ifnull(T.pos,-1) as pos, T.price as price from tmc as T left join tmc_ed as E on T.ed=E._id left join ostat as O on T._id=O.id_tmc where T.vis=1 and T.pgr = "
    	        +((tvIdPgr.getText().equals("0") )?"T.pgr":tvIdPgr.getText())
    	        //((tvIdPost.getText().equals("") )?"0":tvIdPost.getText())
    	        +" group by T._id, T.name, T.pgr, T.ed, E.name, ifnull(T.pos,-1), T.price" //+((tmp==0)?"":" and _id="+tmp)
    	        //+" order by T.name"
    	        , null); */
    /*07-11 19:16:53.841: E/AndroidRuntime(9814): Caused by: android.database.sqlite.SQLiteException: near "where": 
     * syntax error (code 1): , while compiling: 
     * select T._id as _id, T.name as name, T.pgr as pgr, T.ed as ed, E.name as ted, sum(O.kol) as ost, ifnull(T.pos,-1) as pos, 
     * T.price as price from tmc as T left join tmc_ed as E on T.ed=E._id 
     * left join ostat as O on T._id=O.id_tmc where T.vis=1  
     * group by T._id, T.name, T.pgr, T.ed, E.name, ifnull(T.pos,-1), T.price where T.pgr = T.pgr order by T.name
 */ 
      return cursor;
    }
     
  }
 
}
/*
 near ")": 
select _id, name, pgr,ed,ted, ost from 
(select _id, name,0 pgr, 0 ed, '-' ted, 0 ost from foo 
		union all select T._id as _id, T.name as name, T.pgr as pgr, T.ed as ed, E.name as ted, O.kol as ost 
		from tmc as T left join tmc_ed as E on T.ed=E._id left join ostat as O on T._id=O.id_tmc and T.id_post=) where pgr = pgr*/



