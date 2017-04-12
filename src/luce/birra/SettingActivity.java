package luce.birra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
 
public class SettingActivity extends FragmentActivity {

  Button btnExit, btnB1, btnB2, btnB3, btnT1, btnT2, btnT3;
  TextView tvDKol, tvB1, tvB2, tvB3, tvB1V, tvB2V,tvB3V,tvT1,tvT2,tvT3,tvT1V,tvT2V,tvT3V;
  SeekBar sbDKol;
  int tvDialogN=0;
  Dialog dialogg;
  LinearLayout Dview;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.prix);
    //final DialogFragment dlg = new DialogActivity();
    
    btnExit = (Button) findViewById(R.id.btnExitSetting);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnB1 = (Button) findViewById(R.id.btnBigB);
    btnB1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeBigButton);
    btnB1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnBigB;
        	showDialog(1);
        }
      });
    tvB1V = (TextView) findViewById(R.id.tvBigBVal);
    tvB1V.setText(String.valueOf(MainActivity.sizeBigButton));
    tvB1V.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeBigButton);
    tvB1V.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnBigB;
        	showDialog(1);
        }
      });
    tvB1 = (TextView) findViewById(R.id.tvBigB);
    tvB1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeBigButton);
    tvB1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnBigB;
        	showDialog(1);
        }
      });
    ////medium
    btnB2 = (Button) findViewById(R.id.btnMediumB);
    btnB2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeMediumButton);
    btnB2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnMediumB;
        	showDialog(1);
        }
      });
    tvB2V = (TextView) findViewById(R.id.tvMediumBVal);
    tvB2V.setText(String.valueOf(MainActivity.sizeMediumButton));
    tvB2V.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeMediumButton);
    tvB2V.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnMediumB;
        	showDialog(1);
        }
      });
    tvB2 = (TextView) findViewById(R.id.tvMediumB);
    tvB2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeMediumButton);
    tvB2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnMediumB;
        	showDialog(1);
        }
      });
////small
    btnB3 = (Button) findViewById(R.id.btnSmallB);
    btnB3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeSmallButton);
    btnB3.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnSmallB;
        	showDialog(1);
        }
      });
    tvB3V = (TextView) findViewById(R.id.tvSmallBVal);
    tvB3V.setText(String.valueOf(MainActivity.sizeSmallButton));
    tvB3V.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeSmallButton);
    tvB3V.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnSmallB;
        	showDialog(1);
        }
      });
    tvB3 = (TextView) findViewById(R.id.tvSmallB);
    tvB3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeSmallButton);
    tvB3.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnSmallB;
        	showDialog(1);
        }
      });
    ///big text
    btnT1 = (Button) findViewById(R.id.btnBigT);
    btnT1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeBigText);
    btnT1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnBigT;
        	showDialog(1);
        }
      });
    tvT1V = (TextView) findViewById(R.id.tvBigTVal);
    tvT1V.setText(String.valueOf(MainActivity.sizeBigText));
    tvT1V.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeBigText);
    tvT1V.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnBigT;
        	showDialog(1);
        }
      });
    tvT1 = (TextView) findViewById(R.id.tvBigT);
    tvT1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeBigText);
    tvT1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnBigT;
        	showDialog(1);
        }
      });
    ///medium text
    btnT2 = (Button) findViewById(R.id.btnMediumT);
    btnT2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeMediumText);
    btnT2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnMediumT;
        	showDialog(1);
        }
      });
    tvT2V = (TextView) findViewById(R.id.tvMediumTVal);
    tvT2V.setText(String.valueOf(MainActivity.sizeMediumText));
    tvT2V.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeMediumText);
    tvT2V.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnMediumT;
        	showDialog(1);
        }
      });
    tvT2 = (TextView) findViewById(R.id.tvMediumT);
    tvT2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeMediumText);
    tvT2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnMediumT;
        	showDialog(1);
        }
      });
    ///small text
    btnT3 = (Button) findViewById(R.id.btnSmallT);
    btnT3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeSmallText);
    btnT3.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnSmallT;
        	showDialog(1);
        }
      });
    tvT3V = (TextView) findViewById(R.id.tvSmallTVal);
    tvT3V.setText(String.valueOf(MainActivity.sizeSmallText));
    tvT3V.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeSmallText);
    tvT3V.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnSmallT;
        	showDialog(1);
        }
      });
    tvT3 = (TextView) findViewById(R.id.tvSmallT);
    tvT3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeSmallText);
    tvT3.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	tvDialogN=R.id.btnSmallT;
        	showDialog(1);
        }
      });
    //MainActivity.setSizeFont((LinearLayout)findViewById(R.id.prix_ll),(byte)1,(byte)3,(byte)3);
}
  //intent = new Intent(this, OtchetActivity.class);
  //startActivity(intent);
    
  @Override
  protected void onRestart() {
    super.onRestart();
  }
  @Override 
  protected void onDestroy() {
    super.onDestroy();
  }
  
  void dialogNumOK(int tvI) {
	   switch (tvI) {
	   case R.id.btnBigB:
       	 tvB1V.setText(tvDKol.getText());
       	 MainActivity.sizeBigButton = sbDKol.getProgress();
       	 tvB1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeBigButton);
		 tvDialogN=0;
	     break;
	   case R.id.btnMediumB:
	       	 tvB2V.setText(tvDKol.getText());
	       	MainActivity.sizeMediumButton = sbDKol.getProgress();
	       	 tvB2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeMediumButton);
			 tvDialogN=0;
		     break;
	   case R.id.btnSmallB:
	       	 tvB3V.setText(tvDKol.getText());
	       	MainActivity.sizeSmallButton = sbDKol.getProgress();
	       	 tvB3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeSmallButton);
			 tvDialogN=0;
		     break;
	   case R.id.btnBigT:
	       	 tvT1V.setText(tvDKol.getText());
	       	MainActivity.sizeBigText = sbDKol.getProgress();
	       	 tvT1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeBigText);
			 tvDialogN=0;
		     break;
		   case R.id.btnMediumT:
		       	 tvT2V.setText(tvDKol.getText());
		       	MainActivity.sizeMediumText = sbDKol.getProgress();
		       	 tvT2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeMediumText);
				 tvDialogN=0;
			     break;
		   case R.id.btnSmallT:
		       	 tvT3V.setText(tvDKol.getText());
		       	MainActivity.sizeSmallText = sbDKol.getProgress();
		       	 tvT3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.sizeSmallText);
				 tvDialogN=0;
			     break;
	   }
  } 
 
  String dialogNum(int tvI) {
	   switch (tvI) {
	   case R.id.btnBigB:
      	 return String.valueOf( MainActivity.sizeBigButton );
	   case R.id.btnMediumB:
		   return String.valueOf( MainActivity.sizeMediumButton );
	   case R.id.btnSmallB:
		   return String.valueOf( MainActivity.sizeSmallButton );
	   case R.id.btnBigT:
		   return String.valueOf( MainActivity.sizeBigText );
		   case R.id.btnMediumT:
			   return String.valueOf( MainActivity.sizeMediumText );
		   case R.id.btnSmallT:
			   return String.valueOf( MainActivity.sizeSmallText );
			   default: return "";
	   }
 }
 
  int dialogNumI(int tvI) {
	   switch (tvI) {
	   case R.id.btnBigB:
     	 return MainActivity.sizeBigButton ;
	   case R.id.btnMediumB:
		   return  MainActivity.sizeMediumButton ;
	   case R.id.btnSmallB:
		   return  MainActivity.sizeSmallButton ;
	   case R.id.btnBigT:
		   return  MainActivity.sizeBigText ;
		   case R.id.btnMediumT:
			   return  MainActivity.sizeMediumText ;
		   case R.id.btnSmallT:
			   return  MainActivity.sizeSmallText;
			   default: return 10;
	   }
}
  
  void dialogNumCancel(int tvI) {  
				 tvDialogN=0;
  }
  
  String dialogNumTitle(int tvI) {
	   switch (tvI) {
	   case R.id.btnBigB:
		   return "Размер шрифта большой кнопки";
	     
	   case R.id.btnMediumB:
		   return "Размер шрифта средней кнопки";
	     
	   case R.id.btnSmallB:
		   return "Размер шрифта маленькой кнопки";
	   case R.id.btnBigT:
		   return "Размер шрифта большой надписи";
	     
	   case R.id.btnMediumT:
		   return "Размер шрифта средней надписи";
	     
	   case R.id.btnSmallT:
		   return "Размер шрифта маленькой надписи";
	   default: return "";
	   }
  }
  
  @Override
  protected Dialog onCreateDialog(int id) {
    if (id == 1) {
      AlertDialog.Builder adb = new AlertDialog.Builder(this);
      //adb.setTitle(dialogNumTitle(tvDialogN));
      //adb.setMessage("Message");
      //strDialog="";
      Dview = (LinearLayout) getLayoutInflater().inflate(R.layout.size_text, null);
      //Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btComa, btDD, btXD;
      // устанавливаем ее, как содержимое тела диалога
      //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
      
      adb.setView(Dview);
      // находим TexView для отображения кол-ва
      tvDKol = (TextView) Dview.findViewById(R.id.tvsize);
      
      //sbDKol = (SeekBar) Dview.findViewById(R.id.seekBarSize);
      
      adb.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
        	   dialogNumCancel(tvDialogN);
          }
      })
      .setPositiveButton("ГОТОВО", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
         	dialogNumOK(tvDialogN);
          }
      });
   
      dialogg = adb.create();
      dialogg.setTitle(dialogNumTitle(tvDialogN));

      return dialogg;
    }
    ///////////////dialog 2 - number
    
    return super.onCreateDialog(id);
  }

  
  @Override
  protected void onPrepareDialog(int id, Dialog dialog) {
    super.onPrepareDialog(id, dialog);
    //Log.d("MyLog", "Prepare");
    //if (id == 1) tvDKol.setText(tvSum.getText());
    if (id == 1) 
    {tvDKol.setText(dialogNum(tvDialogN));
    tvDKol.setTextSize(TypedValue.COMPLEX_UNIT_PX,dialogNumI(tvDialogN));
    sbDKol.setProgress(dialogNumI(tvDialogN));
    sbDKol.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (seekBar.getProgress()>10 )
			{
				tvDKol.setText(String.valueOf(seekBar.getProgress()));
				tvDKol.setTextSize(TypedValue.COMPLEX_UNIT_PX,seekBar.getProgress());
			}
			
		}
	});
   
  	} 
   
  } 

}

