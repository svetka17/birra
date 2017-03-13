package luce.birra;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
public class PgrEditActivity extends FragmentActivity {
  Button btnExit, btnAdd;
  //EditText etName;
  TextView etName;
  TextView tvId;//, tvData;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pgr_edit);
    etName = (TextView) findViewById(R.id.tvEditNamePgrSpr);
    //tvData = (TextView) findViewById(R.id.etEditDta_insSpr);
    tvId = (TextView) findViewById(R.id.etEditIdSpr);
    btnAdd = (Button) findViewById(R.id.btnOkSpr);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if  (!etName.getText().toString().equals("") ) //(v.getId()==R.id.btnOkSpr)
  			 if ((tvId.getText().toString().length()==0)) 
        		MainActivity.db.addRecTMC_PGR(etName.getText().toString(), MainActivity.getIntDataTime());
  			 else
  			 {
        	MainActivity.db.updRec("tmc_pgr", Integer.parseInt(tvId.getText().toString()), "name", etName.getText().toString());	
        	MainActivity.db.updRec("tmc_pgr", Integer.parseInt(tvId.getText().toString()), "data_ins", MainActivity.getIntDataTime()); 
  			 }
        	finish();
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitSpr);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    //Bundle extras = getIntent().getExtras();
    if( getIntent().getExtras() != null)
    {
    	etName.setText(getIntent().getStringExtra("PgrName"));
    	tvId.setText(getIntent().getStringExtra("PgrId"));
    }
  
  }
 
  protected void onDestroy() {
    super.onDestroy();
  }
 
}

