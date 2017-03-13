package luce.birra;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
public class PostavEditActivity extends FragmentActivity {
 
  Button btnExit, btnAdd;
  
  TextView tvId, tvName, tvAdres, tvTel, tvPrim ;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.postav_edit);
    tvName = (TextView) findViewById(R.id.tvEditNamePostav);
    tvAdres = (TextView) findViewById(R.id.tvEditAdresPostav);
    tvPrim = (TextView) findViewById(R.id.tvEditPrimPostav);
    tvTel = (TextView) findViewById(R.id.tvEditTelPostav);
    tvId = (TextView) findViewById(R.id.tvEditIdPostav);
    btnAdd = (Button) findViewById(R.id.btnEditOkPostav);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if  (!tvName.getText().toString().equals("") ) //(v.getId()==R.id.btnOkSpr)
  			 if (tvId.getText().toString().equals("")) 
        		MainActivity.db.addRecPOSTAV(tvName.getText().toString(), tvAdres.getText().toString(), tvTel.getText().toString(), tvPrim.getText().toString(),
        				MainActivity.getIntData(), (byte)0);
  			 else
  			 {
        	MainActivity.db.updRec("postav", Integer.parseInt(tvId.getText().toString()), 
        			new String[] {"name","adres","telef","prim"},
        			new String[] {tvName.getText().toString(),tvAdres.getText().toString(),tvTel.getText().toString(),tvPrim.getText().toString()});	
        	MainActivity.db.updRec("postav", Integer.parseInt(tvId.getText().toString()), "data_ins", MainActivity.getIntData()); 
  			 }
        	finish();
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnEditExitPostav);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    //Bundle extras = getIntent().getExtras();
    if( getIntent().getExtras() != null)
    {
    	tvName.setText(getIntent().getStringExtra("PostavName"));
    	tvAdres.setText(getIntent().getStringExtra("PostavAdres"));
    	tvTel.setText(getIntent().getStringExtra("PostavTel"));
    	tvPrim.setText(getIntent().getStringExtra("PostavPrim"));
    	tvId.setText(getIntent().getStringExtra("PostavId"));
    }
  
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
 
}

