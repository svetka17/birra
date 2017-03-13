package luce.birra;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
 
public class ProdEditActivity extends FragmentActivity {
 
  Button btnExit, btnAdd;
  CheckBox cbVis, cbOk;
  Spinner spPgr;
  TextView tvId, tvIdPgr;//, tvData;
  EditText etName, tvTara, etPrice;
 
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.prod_edit);
    etName = (EditText) findViewById(R.id.tvEditNameProd);
    //etPgr = (EditText) findViewById(R.id.etEditIdPgrProd);
    etPrice = (EditText) findViewById(R.id.tvEditPriceProd);
    cbVis = (CheckBox) findViewById(R.id.cbEditVisProd);
    cbOk = (CheckBox) findViewById(R.id.cbEditOkProd);
    tvTara = (EditText) findViewById(R.id.etEditTaraProd);
    spPgr = (Spinner) findViewById(R.id.spEditPgr_Prod);
    tvIdPgr = (TextView) findViewById(R.id.etEditIdPgrProd);
    tvId = (TextView) findViewById(R.id.etEditIdProd);
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr", null);

    // make an adapter from the cursor
    String[] from = new String[] {"name"};
    int[] to = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to);

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
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText(String.valueOf("0"));
        }
        });
    //tvData = (TextView) findViewById(R.id.etEditDatIns);
    
    btnAdd = (Button) findViewById(R.id.btnOkEditProd);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if  (!etName.getText().toString().equals("") && !etPrice.getText().toString().equals("") )
  			 {if ((tvId.getText().toString().length()==0)) 
        		{//MainActivity.db.addRecTMC(name, pgr, price, vis, data_del, data_ins, ok);
  				Log.d("MyLog", etName.getText().toString()+" "+
        				Integer.parseInt(tvIdPgr.getText().toString())+" "+ 
        				Float.parseFloat(etPrice.getText().toString())+" "+
        				(cbVis.isChecked()?(byte)1:(byte)0) );
  				 MainActivity.db.addRecTMC(
        				etName.getText().toString(),
        				Integer.parseInt(tvIdPgr.getText().toString()/*spPgr.getTag().toString()*/), 
        				Float.parseFloat(etPrice.getText().toString()), 
        				cbVis.isChecked()?(byte)1:(byte)0, 
        				(tvTara.getText().toString().length()==0||tvTara.getText().equals("0"))?0:Float.parseFloat(tvTara.getText().toString()), 
        				MainActivity.getIntDataTime(), 
        				cbOk.isChecked()?(byte)1:(byte)0); 
        		/*Log.d("MyLog", "new "+etName.getText().toString()+" "+
        				Integer.parseInt(spPgr.getTag().toString())+" "+ 
        				Float.parseFloat(etPrice.getText().toString())+" "+
        				(cbVis.isChecked()?(byte)1:(byte)0)+" count="+i );*/
        		}
  			 
  			 else
  			 {
        	MainActivity.db.updRec("tmc", Integer.parseInt(tvId.getText().toString()), 
        			"name", etName.getText().toString());	
        	MainActivity.db.updRec("tmc", Integer.parseInt(tvId.getText().toString()), 
        			new String[] {"data_ins","vis","pgr","ok"}, 
        			new int[] {MainActivity.getIntDataTime(),
        					cbVis.isChecked()?(byte)1:(byte)0,
        					Integer.parseInt(tvIdPgr.getText().toString()),
        					cbOk.isChecked()?(byte)1:(byte)0}); 
        	MainActivity.db.updRec("tmc", Integer.parseInt(tvId.getText().toString()), 
        			new String[] {"price","tara"}, new float[] {Float.parseFloat(etPrice.getText().toString()),Float.parseFloat(tvTara.getText().toString())});
  			 }
        	finish();
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
    	setSpinnerItemById(spPgr, Integer.parseInt(getIntent().getStringExtra("ProdPgr")));
    	//spPgr.setSelection(Integer.parseInt(getIntent().getStringExtra("ProdPgr")));
    	tvIdPgr.setText(getIntent().getStringExtra("ProdPgr"));
    	//tvId.setText(getIntent().getIntExtra("ProdId",0));
    	tvId.setText(getIntent().getStringExtra("ProdId"));
    	cbOk.setChecked(Integer.parseInt(getIntent().getStringExtra("ProdOk"))==1?true:false);
    	cbVis.setChecked(Integer.parseInt(getIntent().getStringExtra("ProdVis"))==1?true:false);
    	tvTara.setText(getIntent().getStringExtra("ProdTara"));
    }
  
  }
  
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

  @Override
  protected void onRestart() {
    super.onRestart();
    getSupportLoaderManager().getLoader(0).forceLoad();
  }   
  protected void onDestroy() {
    super.onDestroy();
  }
 
}

