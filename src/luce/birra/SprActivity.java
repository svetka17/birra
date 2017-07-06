package luce.birra;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

 
public class SprActivity extends FragmentActivity {

  Button btnExit, btnProd, btnPgr, btnPostav,btnPrice, btnKlient, btnExport, btnImport, btnImportOst;
  //OpenFileDialog ofd;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.all_spr);
    //final DialogFragment dlg = new DialogActivity();

    btnExit = (Button) findViewById(R.id.btnExitSpr);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnProd = (Button) findViewById(R.id.btnSprProd);
    btnProd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(SprActivity.this, ProdActivity.class);
			   startActivity(intent);
        }
      });
    
    btnPgr = (Button) findViewById(R.id.btnSprPgr);
    btnPgr.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(SprActivity.this, PgrActivity.class);
			   startActivity(intent);
        }
      });
    
    btnPostav = (Button) findViewById(R.id.btnSprPostav);
    btnPostav.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(SprActivity.this, PostavActivity.class);
			   startActivity(intent);
        }
      });
    
    btnPrice = (Button) findViewById(R.id.btnSprPrice);
    btnPrice.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(SprActivity.this, PriceActivity.class);
			   startActivity(intent);
        }
      });
    
    btnKlient = (Button) findViewById(R.id.btnSprKlient);
    btnKlient.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(SprActivity.this, KlientActivity.class);
			   startActivity(intent);
        }
      });
    
    btnExport = (Button) findViewById(R.id.btnSprExport);
    btnExport.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel(SprActivity.this, SprActivity.this, "","", 
        			"", "—œ–¿¬Œ◊Õ» »", (byte)6);

        }
      });
    
    btnImport = (Button) findViewById(R.id.btnSprImport);
    btnImport.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel_import(SprActivity.this, SprActivity.this,(byte)1);

        }
      });
    
    btnImportOst = (Button) findViewById(R.id.btnOstImport);
    btnImportOst.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	MainActivity.excel_import(SprActivity.this, SprActivity.this,(byte)2);

        }
      });
    
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.all_spr_ll));
}

    
  @Override
  protected void onRestart() {
    super.onRestart();
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
  
}

