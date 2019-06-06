package luce.birra;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class UserEditActivity extends FragmentActivity {
  Button btnExit, btnAdd;
  EditText etName, etPass;
  //TextView etName;
  TextView tvId;//, tvData;
  CheckBox cbOk, cbAdmin;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_edit);
    etName = (EditText) findViewById(R.id.tvEditNameUser);
    
    etPass = (EditText) findViewById(R.id.tvEditPasswordUser);
    
    //tvData = (TextView) findViewById(R.id.tvEditDatInsUser);
    
    cbOk = (CheckBox) findViewById(R.id.cbEditOkUser);
    cbAdmin = (CheckBox) findViewById(R.id.cbEditAdminUser);
    
    tvId = (TextView) findViewById(R.id.etEditIdUser);
    
    btnAdd = (Button) findViewById(R.id.btnOkEditUser);
    btnAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if  (!etName.getText().toString().equals("") && !etPass.getText().toString().equals("")) //(v.getId()==R.id.btnOkSpr)
  			 if ((tvId.getText().toString().length()==0)) 
        		MainActivity.db.addRecUSER(etName.getText().toString(), etPass.getText().toString(), MainActivity.getIntDataTime(), cbOk.isChecked()?1:0, cbAdmin.isChecked()?1:0);
  			 else
  			 {
        	MainActivity.db.updRec("user", Integer.parseInt(tvId.getText().toString()), new String[] {"name","password"}, new String[] {etName.getText().toString(),etPass.getText().toString()});	
        	MainActivity.db.updRec("user", Integer.parseInt(tvId.getText().toString()), new String[] {"data_ins","ok","admin"}, new int[] {MainActivity.getIntDataTime(),cbOk.isChecked()?1:0, cbAdmin.isChecked()?1:0 }); 
  			 }
        	finish();
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitEditUser);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    //Bundle extras = getIntent().getExtras();
    if( getIntent().getExtras() != null)
    {
    	etName.setText(getIntent().getStringExtra("UserName"));
    	etPass.setText(getIntent().getStringExtra("UserPassword"));
    	tvId.setText(getIntent().getStringExtra("UserId"));
    	cbOk.setChecked(MainActivity.StrToFloat(getIntent().getStringExtra("UserOk"))==1?true:false);
    	cbAdmin.setChecked(MainActivity.StrToFloat(getIntent().getStringExtra("UserAdmin"))==1?true:false);
    	//if (MainActivity.StrToFloat(getIntent().getStringExtra("UserDataIns"))<1000) tvData.setText("");
    	//else
    	//tvData.setText(MainActivity.getStringDataTime((int)MainActivity.StrToFloat(getIntent().getStringExtra("UserDataIns"))));
    	//Log.d("MyLog", "new "+MainActivity.StrToFloat(getIntent().getStringExtra("UserDataIns")) );
    }
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.user_edit_ll));
  }
 
  protected void onDestroy() {
    super.onDestroy();
  }
 
}

