package luce.birra;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class PrixodActivity extends FragmentActivity {
 
  Button btnExit, btnAdd, btnSProd, btnSPost, btnHist;
  Spinner spProd, spPost, spEd;
  TextView tvId, tvKol, tvEd, tvPrice, tvPrim, tvIdProd, tvIdPost;
  TextView tvDataIns;
  Cursor cProd, cPost, cEd;
  SimpleCursorAdapter scaProd, scaPost, scaEd;
  String[] fromProd, fromPost, fromEd;
  int[] toProd, toPost, toEd;
  String s;
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.prixod);
    tvId = (TextView) findViewById(R.id.tvIdPri);
    //etPgr = (EditText) findViewById(R.id.etEditIdPgrProd);
    tvIdProd = (TextView) findViewById(R.id.tvIdProdPri);
    tvIdPost = (TextView) findViewById(R.id.tvIdPostPri);
    tvDataIns = (TextView) findViewById(R.id.tvDatInsPri);
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
    tvKol = (TextView) findViewById(R.id.tvKolPri);
    tvEd = (TextView) findViewById(R.id.tvIdEdPri);
    tvPrice = (TextView) findViewById(R.id.tvPricePri);
    tvPrim = (TextView) findViewById(R.id.tvPrimPri);
    spProd = (Spinner) findViewById(R.id.spProdPri);
    spPost = (Spinner) findViewById(R.id.spPostPri);
    spEd = (Spinner) findViewById(R.id.spEdPri);
    cProd = MainActivity.db.getRawData("select _id, name from foo union all select _id, name from tmc", null);
    cPost = MainActivity.db.getRawData("select _id, name from postav", null);
    cEd = MainActivity.db.getRawData("select _id, name from foo union all select _id, name from tmc_ed", null);
    // make an adapter from the cursor
    fromProd = new String[] {"name"};
    toProd = new int[] {android.R.id.text1};
    fromPost = new String[] {"name"};
    toPost = new int[] {android.R.id.text1};
    fromEd = new String[] {"name"};
    toEd = new int[] {android.R.id.text1};
    scaProd = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cProd, fromProd, toProd);
    scaPost = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cPost, fromPost, toPost);
    scaEd = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cEd, fromEd, toEd);
    // set layout for activated adapter
    scaProd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
    scaPost.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    scaEd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // get xml file spinner and set adapter 
    /*spProd.setPrompt("Õ¿»Ã≈ÕŒ¬¿Õ»≈");
    spPost.setPrompt("œŒ—“¿¬Ÿ» ");
    spPost.setPrompt("≈ƒ»Õ»÷¿ »«Ã≈–≈Õ»ﬂ");
    */
    spProd.setAdapter(scaProd);
    spPost.setAdapter(scaPost);
    spEd.setAdapter(scaEd);
    // set spinner listener to display the selected item id
    //mContext = this;
    spProd.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spProd.setTag(id);
        	tvIdProd.setText(String.valueOf(id));
        	s=cProd.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("name"));
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spProd.setTag(0);
        	tvIdProd.setText(String.valueOf("0"));
        }
        });
    spPost.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spPost.setTag(id);
        	tvIdPost.setText(String.valueOf(id));
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPost.setTag(0);
        	tvIdPost.setText(String.valueOf("0"));
        }
        });
    spEd.setSelection(0);
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
        });
    
    //tvData = (TextView) findViewById(R.id.etEditDatIns);
    btnAdd = (Button) findViewById(R.id.btnAddPri);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if  (0!=Integer.parseInt(tvEd.getText().toString()) && 0!=Integer.parseInt(tvIdProd.getText().toString()) && tvPrice.getText().toString().toString().length()!=0 && tvKol.getText().toString().length()!=0)
  			 {if ((tvId.getText().toString().length()==0)) 
        		//MainActivity.db.addRecPRIXOD(id_tmc, kol, price, id_post, prim, data_del, data_ins, ok);
        		{MainActivity.db.addRecPRIXOD(
        				Integer.parseInt(tvIdProd.getText().toString()), 
        				Float.parseFloat(tvKol.getText().toString()), 
        				Byte.parseByte(tvEd.getText().toString()),
        				Float.parseFloat(tvPrice.getText().toString()), 
        				Integer.parseInt(tvIdPost.getText().toString()), 
        				tvPrim.getText().toString(), 
        				//0, 
        				(tvDataIns.getText().toString().length()==0)?MainActivity.getIntDataTime():MainActivity.getIntDataTime(tvDataIns.getText().toString()) , 
        				(byte)0); 
        		
        				//(Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id"))
        		//Log.d("MyLog", "datatime "+MainActivity.getIntDataTime()+" ddd"+tvDataIns.getText() );
        		Toast.makeText(PrixodActivity.this, "œ–»’Œƒ "+s+"  ŒÀ-¬Œ:"+tvKol.getText().toString()+" ÷≈Õ¿:"+tvPrice.getText().toString(), Toast.LENGTH_LONG).show();
        		}
  			 else
  			 {
  				MainActivity.db.updRec("prixod", Integer.parseInt(tvId.getText().toString()), 
  	        			"prim", tvPrim.getText().toString());	
  	        	MainActivity.db.updRec("prixod", Integer.parseInt(tvId.getText().toString()), 
  	        			new String[] {"data_ins","id_tmc","id_post","ed"}, 
  	        			new int[] { (tvDataIns.getText().toString().length()==0)?MainActivity.getIntDataTime():MainActivity.getIntDataTime(tvDataIns.getText().toString()),
  	        				Integer.parseInt(tvIdProd.getText().toString()),
  	        				Integer.parseInt(tvIdPost.getText().toString()),
  	        				Integer.parseInt(tvEd.getText().toString())}); 
  	        	MainActivity.db.updRec("prixod", Integer.parseInt(tvId.getText().toString()), 
  	        			new String[] {"price","kol"}, new float[] {Float.parseFloat(tvPrice.getText().toString()),Float.parseFloat(tvKol.getText().toString())});  				 
  				Toast.makeText(PrixodActivity.this, "œ–»’Œƒ »«Ã≈Õ≈Õ "+s+"  ŒÀ-¬Œ:"+tvKol.getText().toString()+" ÷≈Õ¿:"+tvPrice.getText().toString(), Toast.LENGTH_LONG).show();
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
  
    if( getIntent().getExtras() != null)
    {

    	tvEd.setText(getIntent().getStringExtra("PrixodEd"));
    	//spEd.setSelection(Integer.parseInt(getIntent().getStringExtra("PrixodEd")));
    	setSpinnerItemById(spEd, Integer.parseInt(getIntent().getStringExtra("PrixodEd")));
    	tvIdPost.setText(getIntent().getStringExtra("PrixodPost"));
    	setSpinnerItemById(spPost, Integer.parseInt(getIntent().getStringExtra("PrixodPost")));
    	//spPost.setSelection(Integer.parseInt(getIntent().getStringExtra("PrixodPost")));
    	tvPrice.setText(getIntent().getStringExtra("PrixodPrice"));
    	tvKol.setText(getIntent().getStringExtra("PrixodKol"));
    	setSpinnerItemById(spProd, Integer.parseInt(getIntent().getStringExtra("PrixodProd")));
    	//spProd.setSelection(Integer.parseInt(getIntent().getStringExtra("PrixodProd")));
    	tvIdProd.setText(getIntent().getStringExtra("PrixodProd"));
    	tvPrim.setText(getIntent().getStringExtra("PrixodPrim"));
    	tvDataIns.setText(MainActivity.getStringDataTime(Integer.parseInt(getIntent().getStringExtra("PrixodDataIns"))));

    	tvId.setText(getIntent().getStringExtra("PrixodId"));
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
    //getSupportLoaderManager().getLoader(0).forceLoad();
  } 
  
  protected void onDestroy() {
    super.onDestroy();
  }
 
}


