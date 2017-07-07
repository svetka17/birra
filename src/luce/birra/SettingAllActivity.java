package luce.birra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
//import android.support.annotation.ColorInt;
import luce.birra.DialogScreen.DialogListener;
 
public class SettingAllActivity extends FragmentActivity implements OnClickListener {

  Button btnExit, btnColor1, btnColor2, butTara1, butTara2, butTara3, butTara4, butTara5, butTara6,butTara7, butPgr1, butPgr2, butPgr3, butName1, butName2, butName3, tabBut1, tabBut2, butMenu1, butMenu2, butMenu3;//btnB1, btnB2, btnB3, btnT1, btnT2, btnT3;
  TextView tvDKol, tabH1, tabH2, tabH3, tab1, tab2, tab3,tv,et ;//tvB1, tvB2, tvB3, tvB1V, tvB2V,tvB3V,tvT1,tvT2,tvT3,tvT1V,tvT2V,tvT3V;
  //SeekBar sbDKol;
  NumberPicker np;
  int tvDialogN=0;
  SeekBar sb;
  CheckBox cb;
  LinearLayout llL, llR;
  LinearLayout.LayoutParams llLP;
  LinearLayout.LayoutParams llRP;
  Dialog dialogg;
  LinearLayout Dview;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.setting);
    //final DialogFragment dlg = new DialogActivity();
    cb = (CheckBox) findViewById(R.id.cbSettingLitr);
    cb.setChecked(MainActivity.postlitr==0?false:true);
    cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					if (arg0.isChecked()) MainActivity.postlitr=1; else MainActivity.postlitr=0; 
		}
	});
    
    llL = (LinearLayout) findViewById(R.id.llLSetting);
    llR = (LinearLayout) findViewById(R.id.llRRsetting);
    llLP = (LinearLayout.LayoutParams) llL.getLayoutParams();
    llRP = (LinearLayout.LayoutParams) llR.getLayoutParams();
    sb = (SeekBar) findViewById(R.id.seekBarSetting);
    sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			if (seekBar.getProgress()>20 && seekBar.getProgress()<80)
			{
			  llLP.weight = seekBar.getProgress();
			  llRP.weight = seekBar.getMax()-seekBar.getProgress();
			  llL.requestLayout();
			}
			
		}
		
		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			// TODO Auto-generated method stub
			
		}
	} );
    
    btnColor1 = (Button) findViewById(R.id.btnSettingColor1);
    btnColor1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	DialogScreen getcol = new DialogScreen(SettingAllActivity.this,SettingAllActivity.this,-3)
       	 .setDialogScreenListener(new DialogListener() {
       		@Override
       		public void OnSelectedKol(double k) {
       			if (k!=0) btnColor1.setBackgroundColor(0xff000000 + MainActivity.red1 * 0x10000 + MainActivity.green1 * 0x100 + MainActivity.blue1);					
       		}
       	}) ;getcol.show();
        	/*final ColorPicker cp = new ColorPicker(SettingAllActivity.this, 0, 10, 10, 10);
        	cp.show();
            cp.setCallback(new ColorPickerCallback() {
                @Override
                public void onColorChosen( int color) {
                    btnColor1.setBackgroundColor(color);
                }
            });*/
        }
      });
    
    btnColor2 = (Button) findViewById(R.id.btnSettingColor2);
    btnColor2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	/*final ColorPicker cp = new ColorPicker(SettingAllActivity.this, 0, 10, 10, 10);
        	cp.show();
            cp.setCallback(new ColorPickerCallback() {
                @Override
                public void onColorChosen(int color) {
                    btnColor2.setBackgroundColor(color);
                }
            });*/
        }
      });
    
    btnExit = (Button) findViewById(R.id.btnExitSetting);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    butTara1=(Button) findViewById(R.id.tvSettingButTara1);
    butTara1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
    butTara2=(Button) findViewById(R.id.tvSettingButTara2); 
    butTara2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
    butTara3=(Button) findViewById(R.id.tvSettingButTara3); 
    butTara3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
    butTara4=(Button) findViewById(R.id.tvSettingButTara4);
    butTara4.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
    butTara5=(Button) findViewById(R.id.tvSettingButTara5); 
    butTara5.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
    butTara6=(Button) findViewById(R.id.tvSettingButTara6); 
    butTara6.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
    butTara7=(Button) findViewById(R.id.tvSettingButTara7); 
    butTara7.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
    btnExit.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
    
    butPgr1=(Button) findViewById(R.id.tvSettingButPgr1); 
    butPgr1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butPgr);
    butPgr2=(Button) findViewById(R.id.tvSettingButPgr2); 
    butPgr2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butPgr);
    butPgr3=(Button) findViewById(R.id.tvSettingButPgr3); 
    butPgr3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butPgr);
    int l1=("ÍÀÈÌÅÍÎÂÀÍÈÅ"+"\n").length(), l2=("ÍÀÈÌÅÍÎÂÀÍÈÅ"+"\n"+"ÎÑÒÀÒÎÊ È ÖÅÍÀ").length();
	
	final SpannableStringBuilder text = new SpannableStringBuilder("ÍÀÈÌÅÍÎÂÀÍÈÅ"+"\n"+"ÎÑÒÀÒÎÊ È ÖÅÍÀ"); 
	//final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(255, 0, 0)); 
	final StyleSpan style2 = new StyleSpan(Typeface.BOLD); 
	final AbsoluteSizeSpan s12 = new AbsoluteSizeSpan(MainActivity.butNameS ,false);
	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
	//text.setSpan(style, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
	//textView.setTypeface(null,Typeface.BOLD);
	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    butName1=(Button) findViewById(R.id.tvSettingButName1);
    butName1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    butName2=(Button) findViewById(R.id.tvSettingButName2); 
    butName2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    butName3=(Button) findViewById(R.id.tvSettingButName3); 
    butName3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
    butName1.setText(text);
    butName2.setText(text);
    butName3.setText(text);
    tabBut1=(Button) findViewById(R.id.btnSettingTab1);
    tabBut1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabBut);
    tabBut2=(Button) findViewById(R.id.btnSettingTab2); 
    tabBut2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabBut);
    butMenu1=(Button) findViewById(R.id.btnSetting1); 
    butMenu1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butMenu);
    butMenu2=(Button) findViewById(R.id.btnSetting2); 
    butMenu2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butMenu);
    butMenu3=(Button) findViewById(R.id.btnSetting3);
    butMenu3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butMenu);
    
    butTara1.setOnClickListener(this);
    butTara2.setOnClickListener(this);
    butTara3.setOnClickListener(this);
    butTara4.setOnClickListener(this);
    butTara5.setOnClickListener(this);
    butTara6.setOnClickListener(this);
    butTara7.setOnClickListener(this);
    butPgr1.setOnClickListener(this);
    butPgr2.setOnClickListener(this);
    butPgr3.setOnClickListener(this); 
    butName1.setOnClickListener(this);
    butName2.setOnClickListener(this);
    butName3.setOnClickListener(this); 
    tabBut1.setOnClickListener(this);
    tabBut2.setOnClickListener(this);
    butMenu1.setOnClickListener(this); 
    butMenu2.setOnClickListener(this); 
    butMenu3.setOnClickListener(this);
    
    tabH1=(TextView) findViewById(R.id.tvSettingTabH1); 
    tabH1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabH);
    tabH2=(TextView) findViewById(R.id.tvSettingTabH2);
    tabH2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabH);
    tabH3=(TextView) findViewById(R.id.tvSettingTabH3); 
    tabH3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabH);
    tab1=(TextView) findViewById(R.id.tvSettingTab1); 
    tab1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabI);
    tab2=(TextView) findViewById(R.id.tvSettingTab2);
    tab2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabI);
    tab3=(TextView) findViewById(R.id.tvSettingTab3); 
    tab3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabI);
    tv=(TextView) findViewById(R.id.tvSettingTV);
    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tvH);
    et=(TextView) findViewById(R.id.tvSettingET);
    et.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tvI);
    
    tabH1.setOnClickListener(this);
    tabH2.setOnClickListener(this); 
    tabH3.setOnClickListener(this); 
    tab1.setOnClickListener(this);
    tab2.setOnClickListener(this);
    tab3.setOnClickListener(this);
    tv.setOnClickListener(this); 
    et.setOnClickListener(this);

    
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
	   
	  switch (tvI) 
	  {
	   case R.id.tvSettingButTara1:
		     //butTara1.setText(tvDKol.getText());
	       	 MainActivity.butTara = np.getValue();//sbDKol.getProgress();
	       	 btnExit.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
	       	 butTara1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
	       	 butTara2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
	       	 butTara3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
	       	butTara4.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
	       	 butTara5.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
	       	 butTara6.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
	       	butTara7.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butTara);
			 tvDialogN=0;
		     break;
	   case R.id.tvSettingButPgr1:
		   	 MainActivity.butPgr = np.getValue();//sbDKol.getProgress();
	       	 butPgr1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butPgr);
	       	 butPgr2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butPgr);
	       	 butPgr3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butPgr);
			 tvDialogN=0;
		     break;
	   case R.id.tvSettingButName1:
		   	 MainActivity.butName = np.getValue();//sbDKol.getProgress();
		   	//MainActivity.butNameS = (int)(MainActivity.butName/MainActivity.scale);
		   	int l1=("ÍÀÈÌÅÍÎÂÀÍÈÅ"+"\n").length(), l2=("ÍÀÈÌÅÍÎÂÀÍÈÅ"+"\n"+"ÎÑÒÀÒÎÊ È ÖÅÍÀ").length();
        	
        	final SpannableStringBuilder text = new SpannableStringBuilder("ÍÀÈÌÅÍÎÂÀÍÈÅ"+"\n"+"ÎÑÒÀÒÎÊ È ÖÅÍÀ"); 
        	//final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(255, 0, 0)); 
        	final StyleSpan style2 = new StyleSpan(Typeface.BOLD); 
        	final AbsoluteSizeSpan s12 = new AbsoluteSizeSpan(MainActivity.butNameS ,false);
        	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
        	//text.setSpan(style, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
        	//textView.setTypeface(null,Typeface.BOLD);
        	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        	 butName1.setText(text);
		   	 butName1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
		   	butName2.setText(text);
	       	 butName2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
	       	butName3.setText(text);
	       	 butName3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
			 tvDialogN=0;
		     break;
	   case R.id.btnSettingTab1:
		   	 MainActivity.tabBut = np.getValue();//sbDKol.getProgress();
		   	tabBut1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabBut);
		   	tabBut2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabBut);
			 tvDialogN=0;
		     break;
	   case R.id.btnSetting1:
		   	 MainActivity.butMenu = np.getValue();//sbDKol.getProgress();
	       	 butMenu1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butMenu);
	       	 butMenu2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butMenu);
	       	 butMenu3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butMenu);
			 tvDialogN=0;
			     break;
	   case R.id.tvSettingTabH1:
		   MainActivity.tabH = np.getValue();//sbDKol.getProgress();
	       	 tabH1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabH);
	       	 tabH2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabH);
	       	 tabH3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabH);
			 tvDialogN=0;
			     break;
	   case R.id.tvSettingTab1:
		   MainActivity.tabI = np.getValue();//sbDKol.getProgress();
	       	 tab1.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabI);
	       	 tab2.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabI);
	       	 tab3.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tabI);
			 tvDialogN=0;
			     break;   
	   case R.id.tvSettingTV:
		   MainActivity.tvH = np.getValue();//sbDKol.getProgress();
	       	 tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tvH);
			 tvDialogN=0;
		     break;
	   case R.id.tvSettingET:
		   MainActivity.tvI = np.getValue();//sbDKol.getProgress();
	       	 et.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.tvI);
			 tvDialogN=0;
		     break;
	  }
	  
  } 
 
  String dialogNum(int tvI) {
	  switch (tvI)
	  {
	   case R.id.tvSettingButTara1:
		   return String.valueOf( MainActivity.butTara );
	   case R.id.tvSettingButPgr1:
		   return String.valueOf( MainActivity.butPgr );
	   case R.id.tvSettingButName1:
		   return String.valueOf( MainActivity.butName );
	   case R.id.btnSettingTab1:
		   return String.valueOf( MainActivity.tabBut );
	   case R.id.btnSetting1:
		   return String.valueOf( MainActivity.butMenu );
	   case R.id.tvSettingTabH1:
		   return String.valueOf( MainActivity.tabH );
	   case R.id.tvSettingTab1:
		   return String.valueOf( MainActivity.tabI );
	   case R.id.tvSettingTV:
		   return String.valueOf( MainActivity.tvH );
	   case R.id.tvSettingET:
		   return String.valueOf( MainActivity.tvI );
		     default: return "";
	  }
	  
 }
 
  int dialogNumI(int tvI) {
	  switch (tvI)
	  {
	   case R.id.tvSettingButTara1:
		   return  MainActivity.butTara ;
	   case R.id.tvSettingButPgr1:
		   return  MainActivity.butPgr ;
	   case R.id.tvSettingButName1:
		   return  MainActivity.butName ;
	   case R.id.btnSettingTab1:
		   return  MainActivity.tabBut ;
	   case R.id.btnSetting1:
		   return  MainActivity.butMenu ;
	   case R.id.tvSettingTabH1:
		   return  MainActivity.tabH ;
	   case R.id.tvSettingTab1:
		   return  MainActivity.tabI ;
	   case R.id.tvSettingTV:
		   return  MainActivity.tvH ;
	   case R.id.tvSettingET:
		   return  MainActivity.tvI ;
		     default: return (int)(MainActivity.h/30);
	  } 
	  
}
  
  void dialogNumCancel(int tvI) {  
				 tvDialogN=0;
  }
  
  String dialogNumTitle(int tvI) {
	  switch (tvI)
	  {
	   case R.id.tvSettingButTara1:
		   return "Êíîïêà ñ íàçâàíèåì òàðû";
	   case R.id.tvSettingButPgr1:
		   return "Êíîïêà íàçâàíèÿ ïîãäðóïïû";
	   case R.id.tvSettingButName1:
		   return "Êíîïêà íàçâàíèÿ";
	   case R.id.btnSettingTab1:
		   return "Êíïêà â òàáëèöå";
	   case R.id.btnSetting1:
		   return "Îñíîâíàÿ êíîïêà â ìåíþ";
	   case R.id.tvSettingTabH1:
		   return "Òåêñò çàãîëîâêà â òàáëèöå";
	   case R.id.tvSettingTab1:
		   return "Òåêñò ïîçèöèè â òàáëèöå";
	   case R.id.tvSettingTV:
		   return "Îñíîâíîé òåêñò";
	   case R.id.tvSettingET:
		   return "Ðåäàêòèðóåìûé òåêñò";
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
      // óñòàíàâëèâàåì åå, êàê ñîäåðæèìîå òåëà äèàëîãà
      adb.setView(Dview);
      // íàõîäèì TexView äëÿ îòîáðàæåíèÿ êîë-âà
      tvDKol = (TextView) Dview.findViewById(R.id.tvsize);
      //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(tvDKol.getWindowToken(),
    			InputMethodManager.HIDE_NOT_ALWAYS);
      //sbDKol = (SeekBar) Dview.findViewById(R.id.seekBarSize);
      np = (NumberPicker) Dview.findViewById(R.id.numberPicker);
      imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
  	imm.hideSoftInputFromWindow(np.getWindowToken(),
  			InputMethodManager.HIDE_NOT_ALWAYS);
      adb.setNegativeButton("ÎÒÌÅÍÀ", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
        	   dialogNumCancel(tvDialogN);
          }
      })
      .setPositiveButton("ÃÎÒÎÂÎ", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
         	dialogNumOK(tvDialogN);
         	//saveSetting();
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
    np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    np.setMaxValue(150);
    np.setMinValue(5);
    np.setWrapSelectorWheel(true);
    np.setValue(dialogNumI(tvDialogN));
    np.setOnValueChangedListener(new OnValueChangeListener() {
		
		@Override
		public void onValueChange(NumberPicker p, int arg1, int arg2) {
			tvDKol.setText(String.valueOf(p.getValue()));
			tvDKol.setTextSize(TypedValue.COMPLEX_UNIT_PX,p.getValue());
		}
	});
    //sbDKol.setProgress(dialogNumI(tvDialogN));
    /*
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
	});*/
   
  	} 
   
  }
@Override
public void onClick(View v) {
	switch (v.getId()) 
	{
	   case R.id.tvSettingButTara1:
	   case R.id.tvSettingButTara2:
	   case R.id.tvSettingButTara3:
	   case R.id.tvSettingButTara4:
	   case R.id.tvSettingButTara5:
	   case R.id.tvSettingButTara6:
	   case R.id.tvSettingButTara7:
		   tvDialogN=R.id.tvSettingButTara1;
	     break;
	   case R.id.tvSettingButPgr1:
	   case R.id.tvSettingButPgr2:
	   case R.id.tvSettingButPgr3:
		   tvDialogN=R.id.tvSettingButPgr1;
		     break;
	   case R.id.tvSettingButName1:
	   case R.id.tvSettingButName2:
	   case R.id.tvSettingButName3:
			 tvDialogN=R.id.tvSettingButName1;
		     break;
	   case R.id.btnSettingTab1:
	   case R.id.btnSettingTab2:
			 tvDialogN=R.id.btnSettingTab1;
		     break;
	   case R.id.btnSetting1:
	   case R.id.btnSetting2:
	   case R.id.btnSetting3:
				 tvDialogN=R.id.btnSetting1;
			     break;
	   case R.id.tvSettingTabH1:
	   case R.id.tvSettingTabH2:
	   case R.id.tvSettingTabH3:
				 tvDialogN=R.id.tvSettingTabH1;
			     break;
	   case R.id.tvSettingTab1:
	   case R.id.tvSettingTab2:
	   case R.id.tvSettingTab3:
				 tvDialogN=R.id.tvSettingTab1;
			     break;   
	   case R.id.tvSettingTV:
			 tvDialogN=R.id.tvSettingTV;
		     break;
	   case R.id.tvSettingET:
			 tvDialogN=R.id.tvSettingET;
		     break;
 }
	showDialog(1);
	
} 

}

