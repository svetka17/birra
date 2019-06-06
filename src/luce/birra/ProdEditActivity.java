package luce.birra;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class ProdEditActivity extends FragmentActivity {
 
  Button btnExit, btnAdd;
  CheckBox cbVis, cbOk;
  Spinner spPgr, spEd;
  TextView tvId, tvEd, tvIdPgr;//, tvData;
  EditText etName, tvTara, etPrice, etPos;
  
  void showMessage(String s, int dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(ViewGroup) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(ProdEditActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.prod_edit);
    etName = (EditText) findViewById(R.id.tvEditNameProd);
    etPos = (EditText) findViewById(R.id.etEditPos);
    //etPgr = (EditText) findViewById(R.id.etEditIdPgrProd);
    etPrice = (EditText) findViewById(R.id.tvEditPriceProd);
    cbVis = (CheckBox) findViewById(R.id.cbEditVisProd);
    cbOk = (CheckBox) findViewById(R.id.cbEditOkProd);
    tvTara = (EditText) findViewById(R.id.etEditTaraProd);
    spPgr = (Spinner) findViewById(R.id.spEditPgr_Prod);
    spEd = (Spinner) findViewById(R.id.spEd_Prod);
    tvIdPgr = (TextView) findViewById(R.id.etEditIdPgrProd);
    tvEd = (TextView) findViewById(R.id.etEdProd);
    tvId = (TextView) findViewById(R.id.etEditIdProd);
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by name", null);
    Cursor cE = MainActivity.db.getRawData("select _id, name from tmc_ed", null);
    etName.setImeOptions(EditorInfo.IME_ACTION_DONE);
    etPos.setImeOptions(EditorInfo.IME_ACTION_DONE);
    etPrice.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvTara.setImeOptions(EditorInfo.IME_ACTION_DONE);
    /*etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            //if (hasFocus) {
            	//etName.setVisibility(View.VISIBLE);
            //}
        }
    });*/
    // make an adapter from the cursor
    String[] from = new String[] {"name"};
    int[] to = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_item, c, from, to);

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
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText(String.valueOf("0"));
        }
        });
    
    String[] fromE = new String[] {"name"};
    int[] toE = new int[] {android.R.id.text1};
    SimpleCursorAdapter scaE = new SimpleCursorAdapter(this, R.layout.spinner_item, cE, fromE, toE);

    // set layout for activated adapter
    scaE.setDropDownViewResource(R.layout.spinner_drop_down); 

    // get xml file spinner and set adapter 
    spEd.setAdapter(scaE);

    // set spinner listener to display the selected item id
    //mContext = this;
    spEd.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spEd.setTag(id);
        	tvEd.setText(String.valueOf(id));
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spEd.setTag(0);
        	tvEd.setText(String.valueOf("0"));
        }
        });
    
    //tvData = (TextView) findViewById(R.id.etEditDatIns);
   
    btnAdd = (Button) findViewById(R.id.btnOkEditProd);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if  (!etName.getText().toString().equals("") && !etPrice.getText().toString().equals("") )
  			 { int count=0;
        	//	Log.d("MyLog", "0 count="+count );
        		count=(int)MainActivity.StrToFloat(etPos.getText().toString()); 
        		//.length()==0?0:Integer.parseInt(etPos.getText().toString());
        	//	Log.d("MyLog", "1 count="+count );
        		if (etPos.getText().toString().length()==0)
  			 	{
  			   Cursor ccc = MainActivity.db.getRawData ("select count(*) c from tmc T where T.pgr="+tvIdPgr.getText(),null);
  			   if (ccc.moveToFirst()) { 
  			        do {count=ccc.getInt(ccc.getColumnIndex("c"))+1;
  			        } while (ccc.moveToNext());
  			      }; 
  			 //   Log.d("MyLog", "2 count="+count );
  			    etPos.setText(String.valueOf(count));
  			 // Log.d("MyLog", "3 count="+count );
  			 	}
        	
        		if (tvId.getText().toString().length()==0) 
        		{//MainActivity.db.addRecTMC(name, pgr, price, vis, data_del, data_ins, ok);
  				/*Log.d("MyLog", etName.getText().toString()+" "+
        				Integer.parseInt(tvIdPgr.getText().toString())+" "+ 
        				Float.parseFloat(etPrice.getText().toString())+" "+
        				(cbVis.isChecked()?(byte)1:(byte)0) );*/
  				 MainActivity.db.addRecTMC(
        				etName.getText().toString(),
        				Integer.parseInt(tvIdPgr.getText().toString()/*spPgr.getTag().toString()*/),
        				Integer.parseInt(tvEd.getText().toString()/*spPgr.getTag().toString()*/), 
        				Float.parseFloat(etPrice.getText().toString()), 
        				cbVis.isChecked()?1:0, 
        				count,		
        				(tvTara.getText().toString().length()==0||tvTara.getText().equals("0"))?0:Float.parseFloat(tvTara.getText().toString()), 
        				MainActivity.getIntDataTime(), 
        				cbOk.isChecked()?1:0); 
        		/*Log.d("MyLog", "new "+etName.getText().toString()+" "+
        				Integer.parseInt(spPgr.getTag().toString())+" "+ 
        				Float.parseFloat(etPrice.getText().toString())+" "+
        				(cbVis.isChecked()?(byte)1:(byte)0)+" count="+i );*/
  				//Toast.makeText(ProdEditActivity.this, "ÕŒ¬€… “Œ¬¿–: "+etName.getText().toString()+" ÷≈Õ¿ œ–Œƒ¿∆»:"+etPrice.getText().toString(), Toast.LENGTH_LONG).show();
  				 showMessage("ÕŒ¬€… “Œ¬¿–: "+etName.getText().toString()+" ÷≈Õ¿ œ–Œƒ¿∆»:"+etPrice.getText().toString(), 0);
  				etPos.setText("");
  				 //finish();
        		}
  			 
  			 else
  			 {
        	MainActivity.db.updRec("tmc", Integer.parseInt(tvId.getText().toString()), 
        			"name", etName.getText().toString());	
        	MainActivity.db.updRec("tmc", Integer.parseInt(tvId.getText().toString()), 
        			new String[] {"data_ins","vis","pos","pgr","ed","ok"}, 
        			new int[] {MainActivity.getIntDataTime(),
        					cbVis.isChecked()?1:0,
        							count,
        					Integer.parseInt(tvIdPgr.getText().toString()),
        					Integer.parseInt(tvEd.getText().toString()),
        					cbOk.isChecked()?1:0}); 
        	MainActivity.db.updRec("tmc", Integer.parseInt(tvId.getText().toString()), 
        			new String[] {"price","tara"}, new double[] {Float.parseFloat(etPrice.getText().toString()),Float.parseFloat(tvTara.getText().toString())});
        	//Toast.makeText(ProdEditActivity.this, "“Œ¬¿– »«Ã≈Õ≈Õ: "+etName.getText().toString()+" ÷≈Õ¿ œ–Œƒ¿∆»:"+etPrice.getText().toString(), Toast.LENGTH_LONG).show();
        	showMessage("“Œ¬¿– »«Ã≈Õ≈Õ: "+etName.getText().toString()+" ÷≈Õ¿ œ–Œƒ¿∆»:"+etPrice.getText().toString(),0);
				finish();
  			 }
        	//finish();
  			 }
        	else etName.requestFocus();
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitEditProd);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    //Bundle extras = getIntent().getExtras();
    
    if( getIntent().getExtras() != null)
    {
    	etName.setText(getIntent().getStringExtra("ProdName"));
    	etPrice.setText(getIntent().getStringExtra("ProdPrice"));
    	//c.getInt(c.getColumnIndexOrThrow("_id"))
    	MainActivity.setSpinnerItemById(spPgr, Integer.parseInt(getIntent().getStringExtra("ProdPgr")));
    	//spPgr.setSelection(Integer.parseInt(getIntent().getStringExtra("ProdPgr")));
    	tvIdPgr.setText(getIntent().getStringExtra("ProdPgr"));
    	
    	MainActivity.setSpinnerItemById(spEd, Integer.parseInt(getIntent().getStringExtra("ProdEd")));
    	tvEd.setText(getIntent().getStringExtra("ProdEd"));
    	//tvId.setText(getIntent().getIntExtra("ProdId",0));
    	tvId.setText(getIntent().getStringExtra("ProdId"));
    	etPos.setText(getIntent().getStringExtra("ProdPos"));
    	cbOk.setChecked(Integer.parseInt(getIntent().getStringExtra("ProdOk"))==1?true:false);
    	cbVis.setChecked(Integer.parseInt(getIntent().getStringExtra("ProdVis"))==1?true:false);
    	tvTara.setText(getIntent().getStringExtra("ProdTara"));
    }
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.prod_edit_ll));
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
  }
*/
  @Override
  protected void onRestart() {
    super.onRestart();
   // getSupportLoaderManager().getLoader(0).forceLoad();
  }   
  protected void onDestroy() {
    super.onDestroy();
  }
 
}

