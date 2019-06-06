package luce.birra;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class KlientEditActivity extends FragmentActivity {
 
  Button btnExit, btnAdd;
  
  TextView tvId;
  EditText tvName, tvAdres, tvTel, tvPrim, tvScontoPer, tvScontoSum;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.klient_edit);
    tvName = (EditText) findViewById(R.id.tvEditNameKlient);
    tvAdres = (EditText) findViewById(R.id.tvEditAdresKlient);
    tvPrim = (EditText) findViewById(R.id.tvEditPrimKlient);
    tvTel = (EditText) findViewById(R.id.tvEditTelKlient);
    tvScontoSum = (EditText) findViewById(R.id.tvEditScontoSumKlient);
    tvScontoPer = (EditText) findViewById(R.id.tvEditScontoPerKlient);
    tvId = (TextView) findViewById(R.id.tvEditIdKlient);
    btnAdd = (Button) findViewById(R.id.btnEditOkKlient);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if  (!tvName.getText().toString().equals("") ) //(v.getId()==R.id.btnOkSpr)
  			 if (tvId.getText().toString().equals("")) 
        		MainActivity.db.addRecKARTA_KLIENT//(name, adres, telef, prim, data_ins, ok, sconto_sum, sconto_per); 
        	(tvName.getText().toString(), tvAdres.getText().toString(), tvTel.getText().toString(), tvPrim.getText().toString(),
        				MainActivity.getIntData(), 0, MainActivity.StrToFloat(tvScontoSum.getText().toString()),MainActivity.StrToFloat(tvScontoPer.getText().toString()));
  			 else
  			 {
        	MainActivity.db.updRec("karta_klient", Integer.parseInt(tvId.getText().toString()), 
        			new String[] {"name","adres","telef","prim"},
        			new String[] {tvName.getText().toString(),tvAdres.getText().toString(),tvTel.getText().toString(),tvPrim.getText().toString()});	
        	MainActivity.db.updRec("karta_klient", Integer.parseInt(tvId.getText().toString()), "data_ins", MainActivity.getIntData()); 
        	MainActivity.db.updRec("karta_klient", Integer.parseInt(tvId.getText().toString()),new String[] { "sconto_sum","sconto_per"}, new double[]{MainActivity.StrToFloat(tvScontoSum.getText().toString()),MainActivity.StrToFloat(tvScontoPer.getText().toString())});
  			 }
        	finish();
        }
      });
    tvName.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvScontoSum.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvScontoPer.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvAdres.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvTel.setImeOptions(EditorInfo.IME_ACTION_DONE);
    tvPrim.setImeOptions(EditorInfo.IME_ACTION_DONE);
    
    btnExit = (Button) findViewById(R.id.btnEditExitKlient);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    //Bundle extras = getIntent().getExtras();
    if( getIntent().getExtras() != null)
    {
    	tvName.setText(getIntent().getStringExtra("KartaName"));
    	tvAdres.setText(getIntent().getStringExtra("KartaAdres"));
    	tvTel.setText(getIntent().getStringExtra("KartaTel"));
    	tvPrim.setText(getIntent().getStringExtra("KartaPrim"));
    	tvId.setText(getIntent().getStringExtra("KartaId"));
    	tvScontoSum.setText(getIntent().getStringExtra("KartaScontoSum"));
    	tvScontoPer.setText(getIntent().getStringExtra("KartaScontoPer"));
    }
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.klient_edit_ll));
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
 
}

