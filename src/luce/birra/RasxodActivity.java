package luce.birra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import luce.birra.DialogScreen.DialogListener;

public class RasxodActivity extends FragmentActivity implements OnCheckedChangeListener,
SeekBar.OnSeekBarChangeListener
{
  Button butAdd, butDel, tbHist, tbXX, btnOk,btnOkOk, btnBack, bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btComa, btDD, btXD;
  TextView  // tvDKol, 
  tvIdPgr, tvNamePgr;//, tvCombo;
  TextView titleD, tvNal, tvSkidka, tvIdKlient, etCheckCheck;
  SimpleCursorAdapter scaKlient;
  Spinner spKlient;
  ListView lvComboD;
  LinearLayout Dview;
   
  //LinearLayout llbut;
  ScrollView svBut;
 // LinearLayout llr;
  TableLayout llbut;
  TableRow row;
  //LinearLayout row;
  //TableRow.LayoutParams params;
  
  LinearLayout /*lltara,*/ llL, llR, llRR;
  LinearLayout.LayoutParams llLP;
  LinearLayout.LayoutParams llRP;
  SeekBar sbar;
  HorizontalScrollView hll_tara_button;
  LinearLayout ll_tara_button;
  LinearLayout lltarabutton;
  //LinearLayout llR_;
  int Bpost=0,btnK=0,clrCheck=Color.WHITE;
  
  byte CountTara=0;
  //float otherVal=0;
  String tmcV;
  String kol_skidka="";
  
  String strDialog="", strKol="";
  
  String regexp_numb = "\\-?\\d+(\\.\\d{0,})?";
  
  Dialog dialogg;
  AlertDialog dial;
  //int display_h=0, display_w=0;
  //float scale=0;
//упаковываем данные в понятную для адаптера структуру
  ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
  int combo_pos=0, tvDialogN=0, clrNoCheck=Color.BLACK;
  MySimpleAdapter sAdapter;//, sAdapterD;
  TextView etNal, etSkidka,etSkidkaPer,etSkidkaPerSum, tvKol, tvSdacha, tvDItogo, tvSum;
  ToggleButton tbnKol, tbTara;
  byte Btovar=-1, Btara=-1;
  Map<String, Object> m;
  ArrayList<chB> but = new ArrayList<chB>();
  ArrayList<tranDB> tranz = new ArrayList<tranDB>();
  double sumI=0;
  ListView lvCombo;
  //DialogScreen getkol;
  int count_but_tara=0, count_but=0, count_but_pgr=0;
  
  void tara(byte ii) {
	  Btara=ii;
	  for (int i=0; i<CountTara; i++) 
		  if (Byte.parseByte(but.get(i).tb.getTag().toString())!=ii) 
		  {but.get(i).tb.setChecked(false);but.get(i).tb.setTextColor(clrNoCheck); but.get(i).tb.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
	  if (Byte.parseByte(tbnKol.getTag().toString())!=ii) 
	  {tbnKol.setChecked(false);tbnKol.setTextColor(clrNoCheck);tbnKol.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
  }

  void showMessage(String s, byte dur){
	  LayoutInflater inflater = getLayoutInflater();
	  View layout = inflater.inflate(R.layout.custom_message ,
	  		(LinearLayout) findViewById(R.id.toast_layout));
	  Toast toast = new Toast(RasxodActivity.this); 
	  TextView tv=(TextView) layout.findViewById(R.id.textView);
	  tv.setText(s);
	  //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
	  toast.setDuration((dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
	  toast.setView(layout); 
	  toast.show();
	  //Toast.makeText(RasxodActivity.this, s, (dur==0)?Toast.LENGTH_SHORT:Toast.LENGTH_LONG).show();
  }
  
  class chB {
	   byte indBT;
	   int id_tmc;
	   String tmc_name;
	   double val;
	   double ost;
	   int post;
	   double kol;
	   double price;
	   byte ed;
	   String ted;
	   ToggleButton tb;
	  chB(byte indBT, int id_tmc, String tmc_name, double val, double ost, int post, double kol, double price, byte ed, String ted, ToggleButton tb){
		  this.indBT=indBT;//tag button
		  this.tmc_name=tmc_name;
		  this.id_tmc=id_tmc;
		  this.val=val;
		  this.ost=ost;//kol for tara, price for ostat
		  this.post=post;
		  this.kol=kol;
		  this.price=price;
		  this.ed=ed;
		  this.ted=ted;
		  this.tb=tb;
  }}
  
  static class tranDB {
	  //addRecRASXOD(int id_tmc, float kol, float price, int id_post, int id_klient, String prim, int data_del, int data_ins, byte ok)
	   byte tag; //порядковый номер в чеке в combo (для тары = нмер в комбо что было понятно к какой позиции цека тара - для удаления)
	   byte tagL; //порядковый номер в чеке в combo (для тары = -1)
	   byte tagB; //таг кнопки
	   int id_tmc;
	   double kol;
	   byte ed;
	   double price;
	   //float skidka;
	   int id_post;
	   int id_klient; //пока ничего не пишется
	   String prim;
	  tranDB(byte tag, byte tagL, byte tagB, int id_tmc, double kol, byte ed, double price, /*float skidka,*/ int id_post,int id_klient, String prim){
		  this.tag=tag;
		  this.tagL=tagL;
		  this.tagB=tagB;
		  this.id_tmc=id_tmc;
		  this.kol=kol;
		  this.ed=ed;
		  this.price=price;
		  //this.skidka=skidka;
		  this.id_post=id_post;
		  this.id_klient=id_klient;
		  this.prim=prim;}
	  }

void makeDialog() {
	DialogScreen getkol = new DialogScreen(RasxodActivity.this,RasxodActivity.this,tvDialogN)
	 .setDialogScreenListener(new DialogListener() {
		@Override
		public void OnSelectedKol(double k) {
			if (k!=0) dialogNumOK(tvDialogN, (float)k); else dialogNumCancel(tvDialogN);					
		}
	}) ;getkol.show();
}
   
   //ArrayList<ToggleButton> tbut = new ArrayList<ToggleButton>();
   //ArrayList<LinearLayout> ll = new ArrayList<LinearLayout>();
   Cursor c = MainActivity.db.getRawData ("select distinct T._id as _id, T.name as name from tmc_pgr T left join tmc as TM on T._id=TM.pgr left join ostat as O on O.id_tmc=TM._id where O.kol>0 and TM.vis=1 ",null); 
   
   public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.rasxod);
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.rasxod_ll));
    //count tara button
    Cursor get_count_but = MainActivity.db.getRawData ("select count(*) as c from tmc T left join ostat as O on O.id_tmc=T._id where O.kol>0 and T.ok=1 ",null);
    if (get_count_but.moveToFirst()) { 
        do {count_but_tara=get_count_but.getInt(get_count_but.getColumnIndex("c"));
        } while (get_count_but.moveToNext());
      }; 
    	get_count_but.close();
    //count button pgr
    Cursor c_pgr = MainActivity.db.getRawData ("select count(distinct T._id) as c from tmc_pgr T left join tmc as TM on T._id=TM.pgr left join ostat as O on O.id_tmc=TM._id where O.kol>0 and TM.vis=1 ",null);
 	   //Cursor get_count_but_pgr = MainActivity.db.getRawData ("select count(*) as c from tmc T left join ostat as O on O.id_tmc=T._id where O.kol>0 and T.ok=1 ",null);
 	    if (c_pgr.moveToFirst()) { 
 	        do {count_but_pgr=c_pgr.getInt(get_count_but.getColumnIndex("c"));
 	        } while (c_pgr.moveToNext());
 	      }; 
 	      c_pgr.close();
    	
    	
    	lltarabutton = (LinearLayout) findViewById(R.id.llbuttontara);
    if (count_but_tara<9)
    {  //showMessage("30SP = "+DpToPx(30)+"; W = "+((int)display_w/8)+"; W_DP = "+PxToDp(display_w/8)+"; W_PX = "+DpToPx(display_w/8)+"; S = "+scale+"; W = "+display_w+"; H = "+display_h, (byte)1);
    	ll_tara_button = new LinearLayout(this);
    	ll_tara_button.setOrientation(LinearLayout.HORIZONTAL);
    	lltarabutton.addView(ll_tara_button,
		//	new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
    			new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
    	);  
    	}
    else
    {
    	ll_tara_button = new LinearLayout(this);
    	ll_tara_button.setOrientation(LinearLayout.HORIZONTAL);
    	hll_tara_button = new HorizontalScrollView(this);
    	hll_tara_button.setHorizontalScrollBarEnabled(true); //setScrollBarStyle(HorizontalScrollView. HORIZONTAL);
    	lltarabutton.addView(hll_tara_button,
			new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
    	hll_tara_button.addView(ll_tara_button,
    			new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
    }
    
    ///////////////
    /*llbut = (TableLayout) findViewById(R.id.llBut);
    llbut.setStretchAllColumns(true);
    llbut.setShrinkAllColumns(true);
    */
    llbut = new TableLayout(this);
	//llbut.setOrientation(TableLayout.HORIZONTAL);
	//llbut.setStretchAllColumns(true);
    //llbut.setShrinkAllColumns(true);
    
    svBut = new ScrollView(this);
    //llR_ = (LinearLayout) findViewById(R.id.llR_);
    llL = (LinearLayout) findViewById(R.id.llL);
    llR = (LinearLayout) findViewById(R.id.llR);
    llRR = (LinearLayout) findViewById(R.id.llRR);
    //llr = (LinearLayout) findViewById(R.id.llR);
    llLP = (LinearLayout.LayoutParams) llL.getLayoutParams();
    llRP = (LinearLayout.LayoutParams) llRR.getLayoutParams();
    
    sbar = (SeekBar) findViewById(R.id.seekBar);
    sbar.setProgress(MainActivity.seek);
    llLP.weight = sbar.getProgress();
	llRP.weight = sbar.getMax()-sbar.getProgress();
	llL.requestLayout();
    sbar.setOnSeekBarChangeListener(this);
    
    //lltara = (LinearLayout) findViewById(R.id.llTara);
    
    tvNamePgr = (TextView) findViewById(R.id.tvNamePgrBack);
    btnBack = (Button) findViewById(R.id.btnBackPgr_);
    btnBack.setTag(-1);
    btnBack.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if (Integer.parseInt(v.getTag().toString())!=-1) {
        		v.setTag(-1);
        		tvNamePgr.setText("");
        		setPgr();
        	}
        	else
        		finish();
        }
      });
    
    tvIdPgr = (TextView) findViewById(R.id.tvIdPgrBack);
    tvIdPgr.setText("-1");
    tvIdPgr.setTag(-1);
      
    Cursor cTara = MainActivity.db.getRawData ("select T._id as _id, T.name as name, T.price as price, T.tara as tara, S.id_post as id_post, S.kol as kol, S.ed as ed, E.name as ted from tmc T inner join ostat S on T._id=S.id_tmc inner join tmc_ed E on S.ed=E._id where T.ok=1 and S.kol>0 order by T.pos",/*order by T.tara*/null);
    byte ib=0;//, il=0;
    if (cTara.moveToFirst()) { 
    	 
        do {
        	LinearLayout.LayoutParams PB = new LinearLayout.LayoutParams(/*LinearLayout.LayoutParams.WRAP_CONTENT*/(int)MainActivity.w/8, LinearLayout.LayoutParams.MATCH_PARENT,1);
            PB.weight=1;
            //PB.leftMargin=5;
            //PB.rightMargin=5;
        	//but.add(new chB(byte indBT, int id_tmc, String tmc_name, float val, float ost, int post, float kol, float price, byte ed, String ted, ToggleButton tb))
        	but.add(new chB(
        			ib, 							//indBT
        			cTara.getInt(cTara.getColumnIndex("_id")), //id_tmc
        			cTara.getString(cTara.getColumnIndex("name")), //tmc_name
        			cTara.getDouble(cTara.getColumnIndex("tara")),//val
        			cTara.getDouble(cTara.getColumnIndex("kol")), //ost 
        			cTara.getInt(cTara.getColumnIndex("id_post")),//id_post
        			1,									//kol
        			cTara.getDouble(cTara.getColumnIndex("price")), //price 
        			(byte)cTara.getInt(cTara.getColumnIndex("ed")), //ed
        			cTara.getString(cTara.getColumnIndex("ted")), //ted
        			new ToggleButton(this)
        			));
        	
        	but.get(ib).tb.setTextOff(but.get(ib).tmc_name/*+"\n"+but.get(ib).ost+but.get(ib).ted+" ЦЕНА "+but.get(ib).price*/);
        	but.get(ib).tb.setTextOn(but.get(ib).tmc_name/*+"\n"+but.get(ib).ost+but.get(ib).ted+" ЦЕНА "+but.get(ib).price*/);
        	but.get(ib).tb.setChecked(false);
        	but.get(ib).tb.setTag(ib);
        	but.get(ib).tb.setTextColor(clrNoCheck);
        	
        	/*String regexp = "([\\.,-]+)";//"^([0-9]+)";//"-([а-яА-ЯёЁa-zA-Z0-9 \\.,-]+)$"
			Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(but.get(ib).tmc_name.toString());
			int l = matcher.find()?matcher.group().length():0;*/
        	
        	//float sT = (display_w/(8*scale*scale*(but.get(ib).tmc_name.toString().length()-l)))+scale*10-5;
        			//(2*(display_w/8)/scale)/(scale*but.get(ib).tmc_name.toString().length()); 
        			//((((display_w/8)/scale) / (but.get(ib).tmc_name.toString().length()))/scale)+10;
        	//float sT=MainActivity.sizeMediumButton;
        	but.get(ib).tb.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.butTara );
        	but.get(ib).tb.setBackground(getResources().getDrawable(R.drawable.edittexth_style));
        	but.get(ib).tb.setTag(ib);
        	but.get(ib).tb.setOnCheckedChangeListener(this);
        	/*lltara*/ll_tara_button.addView(but.get(ib).tb,PB);
        	/*GridLayout.LayoutParams params = (GridLayout.LayoutParams)but.get(ib).tb.getLayoutParams();
        	if (cTara.getString(cTara.getColumnIndex("name")).length()>5) {params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 2); params.setGravity(Gravity.FILL_HORIZONTAL); } 
        	else params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1); 
            but.get(ib).tb.setLayoutParams(params);*/
        	ib++;
        } while (cTara.moveToNext());
        
      } else cTara.close();
    
    CountTara=ib;
    
    /*get_count_but = MainActivity.db.getRawData ("select count(*) as c from tmc T left join ostat as O on O.id_tmc=T._id where O.kol>0 and T.ok=1 and T.vis=1 ",null);
    if (get_count_but.moveToFirst()) { 
        do {CountTara=(byte)(CountTara-get_count_but.getInt(get_count_but.getColumnIndex("c")));
        } while (get_count_but.moveToNext());
      }; 
    	get_count_but.close();*/
    	//Log.d("MyLog", " CountTara "+CountTara);
    setPgr();

    
    tvKol = (TextView) findViewById(R.id.tvOtherKol_);
    tvKol.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.butTara );
    tbnKol = (ToggleButton) findViewById(R.id.btnOtherKolRasxod);
    tbnKol.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.butTara );
    tbnKol.setTag(-2);
    tbnKol.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked)
			 {buttonView.setTextColor(clrCheck); 
			 buttonView.setBackground(getResources().getDrawable(R.drawable.btn_chek));
			 tara((byte)-2); 
			 tvDialogN=R.id.tvOtherKol_;
			
			    //showDialog(1);
			 makeDialog();
			     }
			else
				{buttonView.setTextColor(clrNoCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.edittexth_style));
				tvKol.setText(""); //strKol="";
				}
		}
	} );
    
    tbTara = (ToggleButton) findViewById(R.id.btnTaraRasxod_);
    tbTara.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.butTara );
    tbTara.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked)
			 {buttonView.setTextColor(clrCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.tara_chek));}
			else
				{buttonView.setTextColor(clrNoCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.edittexth_style)); }
		}
	} );
 
   lvCombo = (ListView) findViewById(R.id.lvCombo);
   String[] from = { "name", "kol","price", "tara","itog", "summa", "button","summa2","skidka_but_text","skidka_sum_itog"};
   int[] to = { R.id.comboName, R.id.comboKol,R.id.comboPrice, R.id.comboTara,R.id.comboItog, R.id.comboSumma, R.id.comboX,R.id.comboSumma2,R.id.comboSkidka, R.id.comboSkidkaSum };
        // создаем адаптер
        sAdapter = new MySimpleAdapter(this, data, R.layout.combo_item, from, to);
        // определяем список и присваиваем ему адаптер
        //Button bSkidka=(Button)findViewById(R.id.comboSkidka);
 		//bSkidka.setText("CКИДКА");
        sAdapter.setViewBinder(new MyViewBinder());
        lvCombo.setAdapter(sAdapter);
    
    
    tvSum = (TextView) findViewById(R.id.tvSumSum);
    tvSum.setText("");
    
/*    tbHist = (Button) findViewById(R.id.btnRasxodHistory);
    tbHist.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(RasxodActivity.this, RasxodHistActivity.class);
     	   	startActivity(intent);
        }
      });
*/    
    tbXX = (Button) findViewById(R.id.btnXRasxod_x);
    tbXX.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	data.clear();
        	sAdapter.notifyDataSetChanged();
        	tvSum.setText("");sumI=0;tranz.clear();
        }
      });
    
    butAdd = (Button) findViewById(R.id.btnAddNewButton);
    butAdd.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	DialogScreen getYes = new DialogScreen(RasxodActivity.this,RasxodActivity.this,-6).setDialogScreenListener(new DialogListener() {
				
				@Override
				public void OnSelectedKol(double k) {
					if (k==1) {
						//intent = new Intent(contxt, PrixodActivity.class);
	                    //contxt.startActivity(intent);
						
						Intent intent = new Intent(RasxodActivity.this, PrixodActivity.class);
						if (Btovar!=-1) {
						intent.putExtra("PrixodEd", String.valueOf( but.get(Btovar).ed ) );
						//Log.d("MyLog", "ed="+String.valueOf( but.get(Btovar).ed ));
	             	    intent.putExtra("PrixodPgr", "0");
	             	    //intent.putExtra("PrixodId", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id")));
	                    intent.putExtra("PrixodProd", String.valueOf(but.get(Btovar).id_tmc));
	                    //Log.d("MyLog", "id_tmc="+String.valueOf( but.get(Btovar).id_tmc ));
	                    intent.putExtra("PrixodPrice", String.valueOf(but.get(Btovar).price) );
	                   // Log.d("MyLog", "price="+String.valueOf( but.get(Btovar).price ));
	                    if (but.get(Btovar).ed==1)
	                    intent.putExtra("PrixodKol", String.valueOf(50));
	                    else 
	                    	intent.putExtra("PrixodKol", String.valueOf(1));
	                    intent.putExtra("PrixodPrim", "ДОБАВЛЕНИЕ ТОВАРА ИЗ МЕНЮ "+MainActivity.usr);
	                    intent.putExtra("PrixodPost", String.valueOf(but.get(Btovar).post));
	                   // Log.d("MyLog", "post="+String.valueOf( but.get(Btovar).post ));
	                    intent.putExtra("PrixodDataIns", String.valueOf( MainActivity.getIntDataTime() ));
						}
    					      //Log.d("MyLog", "id="+id);
    					      startActivity(intent);
    					      
						btnBack.setTag(-1);
		        		tvNamePgr.setText("");
		        		setPgr();
					}	
				}
			}); 
			getYes.show();
        }
      });
    
    butDel = (Button) findViewById(R.id.btnDeleteButton);
    butDel.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if (Btovar!=-1) {
				DialogScreen getYes = new DialogScreen(RasxodActivity.this,RasxodActivity.this,-5).setDialogScreenListener(new DialogListener() {
					
					@Override
					public void OnSelectedKol(double k) {
						if (k==1) {
						
							long countT=0;
							Cursor cc = MainActivity.db.getRawData ("select id_tmc, kol, ed, id_post from ostat where kol<>0 and id_tmc="+but.get(Btovar).id_tmc+" and id_post="+but.get(Btovar).post+" and ed="+but.get(Btovar).ed , null);
							   if (cc.moveToFirst()) { 
							        do {countT=
							        		MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getDouble(cc.getColumnIndex("kol")), (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "ОБНУЛЕНИЕ ОСТАТКА ИЗ МЕНЮ "+MainActivity.usr, MainActivity.getIntDataTime(), 1);
							        } while (cc.moveToNext());
							      };
							if (countT!=0) showMessage("Остаток обнулен", (byte)1);
							btnBack.setTag(-1);
			        		tvNamePgr.setText("");
			        		setPgr();
						}	
					}
				}); 
				getYes.show();

			}
        }
      });
    
    btnOk = (Button) findViewById(R.id.btnOkRasxod_ok);
    btnOk.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//getCheck();
        	tvDialogN=R.id.btnOkRasxod_ok;
        	
        	showDialog(2);
        }
      });
    btnOkOk = (Button) findViewById(R.id.btnOkRasxod_okok);
    btnOkOk.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	getCheck();
        	//tvDialogN=R.id.btnOkRasxod_ok;
        	//showDialog(2);
        }
      });
    
  }
   
   void setPgr() {
	   llbut.removeAllViewsInLayout();
	   svBut.removeAllViewsInLayout();
	   
	   /*int l=1;
	   if (c.moveToFirst()) { 
		   
	        do {	        	
	        	String[] n = (c.getString(c.getColumnIndex("name"))).split(" ");
	        	for (int i=0; i<n.length; i++ )
	        		if (n[i].length()>l) l=n[i].length();
	        } while (c.moveToNext());
	        
	      }*/
	   //float sText=((display_w)/(4*scale*scale*l))+scale*5;
	   //float sText=MainActivity.sizeSmallButton;
	    if (count_but_pgr<16)
	    {  /* llbut = new TableLayout(this);
	    	llbut.setOrientation(TableLayout.HORIZONTAL);*/
	    	llR.removeView(llbut);
	    	llR.removeView(svBut);	    	

	    	llR.addView(llbut,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1)); 
	    	}
	    else
	    {
	    	/*llbut = new TableLayout(this);
	    	llbut.setOrientation(TableLayout.HORIZONTAL);*/
	    	
	    	llR.removeView(llbut);
	    	llR.removeView(svBut);
	    	
	    	//svBut = new ScrollView(this);
	    	//hll_tara_button.setHorizontalScrollBarEnabled(true); //setScrollBarStyle(HorizontalScrollView. HORIZONTAL);
	    	llR.addView(svBut,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1));
	    	svBut.addView(llbut,
	    			new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT,1));
	    }
	   
	   //byte ib=CountTara;
	   //llbut.removeAllViewsInLayout();
	   int i=1;
	   if (c.moveToFirst()) { 
		   
	        do {
	        	//TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
 	    		//params.weight=1;
 	    		
	        	if (i%2==1) {
	        		row = new TableRow(this);
	                //row.setGravity(Gravity.CENTER);
	                //row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1.0f));
    		 
	                llbut.addView(row,
	                		new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, /*TableLayout.LayoutParams.MATCH_PARENT*/0,1));
	                //row.addView(llbut, params);
	        	}
	        	//LinearLayout.LayoutParams PL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
	    		//PL.weight=1;
	        	//LinearLayout.LayoutParams PB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
	    		//PB.weight=1;
	    		//PB.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
	        	/*if (ib%2==mib) {
	        		ll.add(new LinearLayout(this));
	        		llbut.addView(ll.get(il),PL);
	        		ll.get(il).setOrientation(LinearLayout.HORIZONTAL);
	        		il++;
	        	}*/
	        	//but.add(new chB(byte indBT, int id_tmc, String tmc_name, float val, float ost, int post, float kol, float price, byte ed, String ted, ToggleButton tb))
	        	
	        	Button btnPgr = new Button(this);
	        	
	        	btnPgr.setText(c.getString(c.getColumnIndex("name")));
	        	btnPgr.setTag(c.getInt(c.getColumnIndex("_id"))+"-"+c.getString(c.getColumnIndex("name")));
	        	btnPgr.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butPgr);
	        	btnPgr.setBackground(getResources().getDrawable(R.drawable.edittexth_style));
	        	btnPgr.setMinimumWidth((int)(MainActivity.w / 4));
	        	btnPgr.setOnClickListener(new OnClickListener() {
	                public void onClick(View v) {
/////////////////////////////////////
			String regexp = "^([0-9]+)";
			//String goodIp = v.getTag().toString();
			Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);

			Matcher matcher = pattern.matcher(v.getTag().toString());
			tvIdPgr.setText(matcher.find()?matcher.group():"-1");
			btnBack.setTag(matcher.find()?matcher.group():"-1");
			//Log.d("MyLog", matcher.find()?matcher.group()+" starting at index "+matcher.start()+" and ending at index "+matcher.end()+".":"no");
			regexp="-([а-яА-ЯёЁa-zA-Z0-9 \\.\\(\\),-]+)$";
			pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(v.getTag().toString());
			//Log.d("MyLog", matcher.find()?matcher.group()+" starting at index "+matcher.start()+" and ending at index "+matcher.end()+".":"no");
			tvNamePgr.setText(matcher.find()?matcher.group():"-1" /*c.getString(c.getColumnIndex("name"))*/);
	                	
	                	//Log.d("MyLog", "v.tag="+v.getTag()+" id_pgr="+tvIdPgr.getText()+" namePgr="+tvNamePgr.getText());
	                	setBut();
	                	//data.clear();
	                	//sAdapter.notifyDataSetChanged();
	                	//tvSum.setText("");sumI=0;tranz.clear();
	                }
	              });
	        	
	        	//llbut.addView(btnPgr/*,PB*/);
	        	row.addView(btnPgr,
	        			new TableRow.LayoutParams(/*TableRow.LayoutParams.MATCH_PARENT*/0,TableRow.LayoutParams.MATCH_PARENT,1));
	        	i++;//ib++;
	        } while (c.moveToNext());
	        
	      } 
	    //c.close();   
   }
   
   void setBut() {
	   //Log.d("MyLog", "setbut__count_tara="+CountTara+" but.size="+but.size()+" idPgr="+tvIdPgr.getText());
	   llbut.removeAllViewsInLayout();
	   svBut.removeAllViewsInLayout();
	   //
	   
	   int count_but=0;
	   Cursor cc = MainActivity.db.getRawData 
("select T._id as _id, T.name as name, P.name as namep, TP.price as price, S.id_post as id_post, S.kol as kol, S.ed as ed, E.name as ted "
		+ " from tmc T left join ostat S on T._id=S.id_tmc left join tmc_price as TP on S.id_tmc=TP.id_tmc and S.id_post=TP.id_post left join tmc_ed E on S.ed=E._id left join postav P on S.id_post=P._id where T.vis=1 and S.kol>0 and T.pgr="+tvIdPgr.getText()+" order by T.pos, T._id",null);
	   //Cursor cc = MainActivity.db.getRawData ("select count(*) c from tmc T left join ostat S on T._id=S.id_tmc left join tmc_ed E on S.ed=E._id left join postav P on S.id_post=P._id where T.vis=1 and S.kol>0 and T.pgr="+tvIdPgr.getText(),null);
	   if (cc.moveToFirst()) { 
	        do {count_but++;
	        	//count_but=cc.getInt(cc.getColumnIndex("c"));
	        } while (cc.moveToNext());
	      }; 
	     //cc = MainActivity.db.getRawData ("select T._id as _id, T.name as name, P.name as namep, T.price as price, S.id_post as id_post, S.kol as kol, S.ed as ed, E.name as ted from tmc T left join ostat S on T._id=S.id_tmc left join tmc_ed E on S.ed=E._id left join postav P on S.id_post=P._id where T.vis=1 and S.kol>0 and T.pgr="+tvIdPgr.getText(),null);
	   // scale size font
	      /*int l=1;
		   if (cc.moveToFirst()) { 
			   
		        do {	        	
		        	String[] n = (cc.getString(cc.getColumnIndex("name"))).split(" ");
		        	for (int i=0; i<n.length; i++ )
		        		if (n[i].length()>l) l=n[i].length();
		        } while (cc.moveToNext());
		        
		      }*/
		   //float sText=((display_w)/(6*scale*scale*l))+scale*5;
	    	//float sText=MainActivity.sizeSmallButton;
	    llR.removeView(llbut);
	    llR.removeView(svBut);
	    if (count_but<16)
	    {  /* llbut = new TableLayout(this);
	    	llbut.setOrientation(TableLayout.HORIZONTAL);
	    	llbut.setStretchAllColumns(true);
	        llbut.setShrinkAllColumns(true);*/
	    	//llR.removeView(llbut);
	    	//llR.removeView(svBut);
	    
	    	llR.addView(llbut,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1));  
	    //	llR_.setVisibility(LinearLayout.VISIBLE);
	    }
	    else
	    {
	    	/*llbut = new TableLayout(this);
	    	llbut.setOrientation(TableLayout.HORIZONTAL);
	    	llbut.setStretchAllColumns(true);
	        llbut.setShrinkAllColumns(true);*/
	    	//llR.removeView(llbut);
	    	//llR.removeView(svBut);
	    	
	    	//svBut = new ScrollView(this);
	    	//hll_tara_button.setHorizontalScrollBarEnabled(true); //setScrollBarStyle(HorizontalScrollView. HORIZONTAL);
	    	
	    	llR.addView(svBut,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1));
	    	svBut.addView(llbut,
	    			new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT,1));
	    	//llR_.setVisibility(LinearLayout.VISIBLE);
	    }    
	    
	    
	   byte ib;

	   if (tranz.size()==0)
	   {
	   for (int i=but.size()-1; i>=CountTara; i--) but.remove(i);
	   ib=CountTara;

	   }
	   else
		   {
		   for (int i=but.size()-1; i>=CountTara; i--) 
			   {
			
			   but.get(i).tb.setVisibility(ToggleButton.GONE);// remove(i);
			   }
			   
		   ib=(byte)but.size();
		   }
	   //llbut.removeAllViewsInLayout();  
	   
	   //Log.d("MyLog", "setbut_del_count_tara="+CountTara+" but.size="+but.size()+" idPgr="+tvIdPgr.getText());

	   
	   //Log.d("MyLog", "setBut id_pgr="+tvIdPgr.getText()+" cc.count="+cc.getCount()+" but.size="+but.size());
	   //llbut.setMeasureWithLargestChildEnabled(true); 
	   //кнопки дабавления и удаления кнопок меню 
	   /*row = new TableRow(this);
       //row.setMinimumHeight(w);
       llbut.addView(row,
       		new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT,0));
       Button b = new Button(this);
       b.setText("ДОБАВИТЬ ТОВАР");
       b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			DialogScreen getYes = new DialogScreen(RasxodActivity.this,RasxodActivity.this,-6).setDialogScreenListener(new DialogListener() {
				
				@Override
				public void OnSelectedKol(double k) {
					if (k==1) {
						Intent intent = new Intent(RasxodActivity.this, PrixodActivity.class);
    					      //Log.d("MyLog", "id="+id);
    					      startActivity(intent);
    					      
						btnBack.setTag(-1);
		        		tvNamePgr.setText("");
		        		setPgr();
					}	
				}
			}); 
			getYes.show();
			
		}
	});
       
       
       row.addView(b,
   			new TableRow.LayoutParams(0,TableRow.LayoutParams.MATCH_PARENT,1) );
      
       b = new Button(this);
       b.setText("УДАЛИТЬ ТОВАР");
       b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (Btovar!=-1) {
				DialogScreen getYes = new DialogScreen(RasxodActivity.this,RasxodActivity.this,-5).setDialogScreenListener(new DialogListener() {
					
					@Override
					public void OnSelectedKol(double k) {
						if (k==1) {
						
							long countT=0;
							Cursor cc = MainActivity.db.getRawData ("select id_tmc, kol, ed, id_post from ostat where kol<>0 and id_tmc="+but.get(Btovar).id_tmc+" and id_post="+but.get(Btovar).post+" and ed="+but.get(Btovar).ed , null);
							   if (cc.moveToFirst()) { 
							        do {countT=
							        		MainActivity.db.addRecRASXODcount(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getDouble(cc.getColumnIndex("kol")), (byte)cc.getInt(cc.getColumnIndex("ed")), 0,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "обнуление остатка из меню "+MainActivity.usr, MainActivity.getIntDataTime(), 1);
							        } while (cc.moveToNext());
							      };
							if (countT!=0) showMessage("Остаток обнулен", (byte)1);
							btnBack.setTag(-1);
			        		tvNamePgr.setText("");
			        		setPgr();
						}	
					}
				}); 
				getYes.show();

			}
		}
	});
       row.addView(b,
      			new TableRow.LayoutParams(0,TableRow.LayoutParams.MATCH_PARENT,1) );
       */
       int w = (int)((4*MainActivity.h/5)/8);
	   
	   int tmp_row=1;
	   if (cc.moveToFirst()) { 
		   
	        do {
	        	
	        	if (/*ib%3==CountTara%3*/tmp_row%3==1) {
	        
	        		row = new TableRow(this);
	        		//row = new LinearLayout(this);
	        		//row.setOrientation(LinearLayout.HORIZONTAL);
	                
	        		//int w = (int)(4*MainActivity.h/5)/8;
	                row.setMinimumHeight(w);
	        		
	                //row.setGravity(Gravity.CENTER);
	                //row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1));
	        		 //TableRow.LayoutParams params = new TableRow.LayoutParams();//(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
	 	    		//params.weight=1;
	        		 
	                llbut.addView(row,
	                		new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, /*TableLayout.LayoutParams.MATCH_PARENT*/0,1));
	                //row.addView(llbut, params);
	        	}
	        	
	        	//LinearLayout.LayoutParams PB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
	    		//PB.weight=1;
	    		//PB.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
	        	//TableLayout.LayoutParams PB = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
	    		//PB.weight=1;
	    		//PB.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
	        	//params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT);
	            //params.span = 6;
	        	//but.add(new chB(byte indBT, int id_tmc, String tmc_name, float val, float ost, int post, float kol, float price, byte ed, String ted, ToggleButton tb))
	        
	        	but.add(new chB(
	        			ib, 							//indBT
	        			cc.getInt(cc.getColumnIndex("_id")), //id_tmc
	        			cc.getString(cc.getColumnIndex("name"))+
	        			//( (cc.getString(cc.getColumnIndex("namep")).equals("")||cc.getString(cc.getColumnIndex("namep")).equals("-нет-")||cc.getString(cc.getColumnIndex("namep")).equals("НЕТ") )?"":"\n"+cc.getString(cc.getColumnIndex("namep")) ) , //tmc_name
	        			( (cc.getString(cc.getColumnIndex("namep")).equals("")||cc.getString(cc.getColumnIndex("namep")).equals("-нет-"))?"":"\n"+cc.getString(cc.getColumnIndex("namep")) ) , //tmc_name
	        			0, 									//val
	        			cc.getDouble(cc.getColumnIndex("kol")), //ost 
	        			cc.getInt(cc.getColumnIndex("id_post")),//id_post
	        			0,									//kol
	        			cc.getDouble(cc.getColumnIndex("price")), //price 
	        			(byte)cc.getInt(cc.getColumnIndex("ed")), //ed
	        			cc.getString(cc.getColumnIndex("ted")), //ted
	        			new ToggleButton(this)
	        			));
	        	
	        	but.get(ib).tb.setMinimumWidth((int)(MainActivity.w /6));
	        	/*
	        	String[] n = (but.get(ib).tmc_name).split(" ");
	        	
	        	int l = 1;
	        	for (int i=0; i<n.length; i++ )
	        		if (n[i].length()>l) l=n[i].length();
	        	float sText=((display_w)/(6*scale*scale*l))+scale*10;
	        	*/
	        	but.get(ib).tb.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butName);
	        	
	        	int l1=(but.get(ib).tmc_name+"\n").length(), l2=(but.get(ib).tmc_name+"\n"+MainActivity.round(but.get(ib).ost,3)+but.get(ib).ted+" ЦЕНА "+but.get(ib).price).length();
	        	
	        	final SpannableStringBuilder text = new SpannableStringBuilder(but.get(ib).tmc_name
	        			+"\n"+MainActivity.round(but.get(ib).ost,3)+but.get(ib).ted+" ЦЕНА "+but.get(ib).price); 
	        	//final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(255, 0, 0)); 
	        	final StyleSpan style2 = new StyleSpan(Typeface.BOLD); 
	        	final AbsoluteSizeSpan s12 = new AbsoluteSizeSpan(MainActivity.butNameS ,false);
	        	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
	        	//text.setSpan(style, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
	        	//textView.setTypeface(null,Typeface.BOLD);
	        	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	        	//textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); 
	    
	        	but.get(ib).tb.setTextOff(text);//(but.get(ib).tmc_name+"\n"+but.get(ib).ost+but.get(ib).ted+" ЦЕНА "+but.get(ib).price);
	        	but.get(ib).tb.setTextOn(text);//(but.get(ib).tmc_name+"\n"+but.get(ib).ost+but.get(ib).ted+" ЦЕНА "+but.get(ib).price);
	        	but.get(ib).tb.setChecked(false);
	        	but.get(ib).tb.setTag(ib);
	        	but.get(ib).tb.setTextColor(clrNoCheck);
	        	but.get(ib).tb.setBackground(getResources().getDrawable(R.drawable.edittexth_style));
	        	//but.get(ib).tb.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1));

	        	but.get(ib).tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        		@Override
	        		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	        			byte tmp=0;
	        			if (isChecked)
	        			{ buttonView.setTextColor(clrCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.btn_chek));
	        			Btovar=Byte.parseByte(buttonView.getTag().toString());
	        			for (int i=CountTara; i<but.size(); i++) {
	        		    	if ((i)!= Integer.parseInt(buttonView.getTag().toString())) 
	        		    	{but.get(i).tb.setChecked(false); but.get(i).tb.setTextColor(clrNoCheck) ; but.get(i).tb.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
	        		    	else tmp=but.get(i).ed ;
	        				}
	        				
	        			//06.04!! if (Btara!=-1) fixV();// buttonView.setChecked(false);}
	        			
	        			if (Btara!=-1) 
	        				{if (!(tmp!=1 && Btara!=-2 ))
	        				fixV();// buttonView.setChecked(false);}
	        				else //
	        					{//tara(-2);
	        					tbnKol.setTextColor(clrCheck); 
	        					 tbnKol.setBackground(getResources().getDrawable(R.drawable.btn_chek));
	        					 tara((byte)-2); 
	        					 tvDialogN=R.id.tvOtherKol_;
	        					 
	        					    //showDialog(1);
	        					 makeDialog();
	        					}	
	        				}
	        			else if (tmp!=1) {tbnKol.setTextColor(clrCheck); 
   					 tbnKol.setBackground(getResources().getDrawable(R.drawable.btn_chek));
   					 tara((byte)-2); 
   					 tvDialogN=R.id.tvOtherKol_;
   					
   					    //showDialog(1);
   					makeDialog();
   					    }
	        			}
	        			else
	        				{buttonView.setTextColor(clrNoCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
	        		}
	        	} );
	        	
	        	//llbut.addView(but.get(ib).tb/*,PB*/);
	        	row.addView(but.get(ib).tb,
	        			new TableRow/*LinearLayout*/.LayoutParams(0,TableRow/*LinearLayout*/.LayoutParams.MATCH_PARENT,1) );
	        	
	        	//Log.d("MyLog", "add button");
	        	ib++;
	        	tmp_row++;
	        
	        } while (cc.moveToNext());
	        
	      } //else c.close();
	   //llbut.setWeightSum(5);
	    //for (int i=0; i<c.getCount(); i++) {
	    	
	    //}
	    cc.close();
	    btnBack.setTag(Integer.parseInt(tvIdPgr.getText().toString()));
   }
  
   //static Drawable getDraw() {
	//   return getResources().getDrawable(R.drawable.edittexth_style);
   //}
   
  void fixV() {
	   float tmp=0;
	   if (tbTara.isChecked()) 
   	{m = new HashMap<String, Object>();
   		m.put("skidka_sum", 0);
   		m.put("skidka_but", 0);
   		m.put("skidka_but_text", "0%");
   		   m.put("name", but.get(Btovar).tmc_name);
   		   m.put("price", but.get(Btovar).price);
   		   if (Btara!=-2) 
   		   {m.put("kol", but.get(Btara).val+" "+but.get(Btovar).ted); 
   		   m.put("summa", but.get(Btovar).price*(but.get(Btara).val/*/0.5*/));
   		m.put("summa2", but.get(Btovar).price*(but.get(Btara).val/*/0.5*/));
   		m.put("skidka_sum_itog", but.get(Btovar).price*(but.get(Btara).val/*/0.5*/));
   		   m.put("itog", but.get(Btara).val+" * "+but.get(Btovar).price+" = ");} 
   		   else
   		   {m.put("kol", tvKol.getText()+" "+but.get(Btovar).ted); 
   		   tmp = MainActivity.round(but.get(Btovar).price*MainActivity.StrToFloat(tvKol.getText().toString()),2);
   		   m.put("summa", tmp //((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
   				   );
   		m.put("summa2", tmp //((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
				   );
   		m.put("skidka_sum_itog", tmp //((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
				   );
   		m.put("itog", MainActivity.StrToFloat(tvKol.getText().toString())//((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString()))
   				+" * "+but.get(Btovar).price+" = ");}
   		      m.put("tara", "СВОЯ ТАРА");
   		      data.add(m);
   		      sAdapter.notifyDataSetChanged();
   		   if (Btara!=-2)	
   	{
   	tranz.add(new tranDB((byte)(data.size()-1),(byte)(data.size()-1), Btovar,but.get(Btovar).id_tmc, but.get(Btara).val, but.get(Btovar).ed, but.get(Btovar).price, but.get(Btovar).post, 0, "своя тара"));
   	sumI=sumI+but.get(Btovar).price*(but.get(Btara).val/*/0.5f*/);}
   		   else
   		{tranz.add(new tranDB((byte)(data.size()-1),(byte)(data.size()-1), Btovar,but.get(Btovar).id_tmc, MainActivity.StrToFloat(tvKol.getText().toString())//((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/) 
   				, but.get(Btovar).ed, but.get(Btovar).price, but.get(Btovar).post, 0, "своя тара"));
   	   	sumI=sumI+but.get(Btovar).price*MainActivity.StrToFloat(tvKol.getText().toString()) //((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
   	   			;}
   	}
   	else
   	{
   	  m = new HashMap<String, Object>();
   	  	m.put("skidka_sum", 0);
		m.put("skidka_but", 0);
		m.put("skidka_but_text", "0%");
   	  	m.put("name", but.get(Btovar).tmc_name);
   	 m.put("price", but.get(Btovar).price);
   	 if (Btara!=-2) {m.put("kol", but.get(Btara).val+" "+but.get(Btovar).ted);
		      m.put("tara", " + ТАРА "+but.get(Btara).tmc_name);
		      tmp = MainActivity.round(but.get(Btovar).price*(but.get(Btara).val/*/0.5*/)+but.get(Btara).price,2);
		      m.put("summa", tmp);
		      m.put("summa2", tmp);
		      m.put("skidka_sum_itog", tmp);
		      m.put("itog", but.get(Btara).val+" * "+but.get(Btovar).price+" + "+but.get(Btara).price+" = ");}
   	 else
   	{m.put("kol", tvKol.getText()+" "+but.get(Btovar).ted);
    m.put("tara", /*" + ПАКЕТ "*/"");
    tmp = MainActivity.round(but.get(Btovar).price*MainActivity.StrToFloat(tvKol.getText().toString()),2);
    m.put("summa", tmp);
    m.put("summa2", tmp);
    m.put("skidka_sum_itog", tmp);
    m.put("itog", MainActivity.StrToFloat(tvKol.getText().toString())//((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString()))
    		+" * "+but.get(Btovar).price+" = ");}
		      data.add(m);
		      
   		
		      sAdapter.notifyDataSetChanged();
		      if (Btara!=-2) {	
		    	 // Log.d("MyLog", "tara1="+(byte)(data.size()-1));
   	tranz.add(new tranDB((byte)(data.size()-1),(byte)(data.size()-1), Btovar,but.get(Btovar).id_tmc, but.get(Btara).val,but.get(Btovar).ed, but.get(Btovar).price, but.get(Btovar).post, 0, "с тарой"));	
   	
   	tranz.add(new tranDB((byte)(data.size()-1),(byte) -1 /*если тара, то позиция в чеке -1*/, Btara,but.get(Btara).id_tmc, but.get(Btara).kol, but.get(Btara).ed, but.get(Btara).price, but.get(Btara).post, 0, "тара"));
   	  
   	sumI=sumI+(but.get(Btovar).price*(but.get(Btara).val/*/0.5f*/)+but.get(Btara).price);
		      }
		      else
			      
		    	   	{tranz.add(new tranDB((byte)(data.size()-1),(byte)(data.size()-1), Btovar,but.get(Btovar).id_tmc, MainActivity.StrToFloat(tvKol.getText().toString()),but.get(Btovar).ed, but.get(Btovar).price, but.get(Btovar).post, 0, "пакет"));	
		    	      // tranz.add(new tranDB((byte)(data.size()-1), Btara,but.get(Btara).id_tmc, but.get(Btara).kol, but.get(Btara).ed, but.get(Btara).price, but.get(Btara).post, 0, "tara"));
		    	       sumI=sumI+(but.get(Btovar).price*MainActivity.StrToFloat(tvKol.getText().toString())//((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
		    	    		   +0);}
   	}
   	
   	tvSum.setText(String.valueOf(MainActivity.round(sumI,2)));
   	but.get(Btovar).tb.setChecked(false);
   	but.get(Btovar).tb.setTextColor(clrNoCheck);
   	but.get(Btovar).tb.setBackground(tvSum.getContext().getResources().getDrawable(R.drawable.edittexth_style));
   	if (Btara!=-2)
   	{but.get(Btara).tb.setChecked(false);
   	but.get(Btara).tb.setTextColor(clrNoCheck);
   	but.get(Btara).tb.setBackground(tvSum.getContext().getResources().getDrawable(R.drawable.edittexth_style));
   	}
   	else
   	{tbnKol.setChecked(false);
   	tbnKol.setTextColor(clrNoCheck);
   	tbnKol.setBackground(tvSum.getContext().getResources().getDrawable(R.drawable.edittexth_style));
   	tvKol.setText("");}
   	Btovar=-1; Btara=-1;
   	//strKol="";
   lvCombo.smoothScrollToPosition(data.size()-1);
   }
  
   void getCheck() {
	   if (/*tvCombo.getText().equals("")*/data.size()==0) finish();
   	else
   	{//no klient
   		long cou=0; cou=MainActivity.db.addRecKLIENTcount(MainActivity.num_id,"чек№ "+MainActivity.num_id, MainActivity.StrToFloat(tvSum.getText().toString()), 
   				0, "чек автозакрыт", MainActivity.getIntDataTime(), 0, (byte)0);
   		
   	for (int i=0; i<tranz.size(); i++) {
   		//public void addRecRASXOD(int id_tmc, float kol, float price, int id_post, int id_klient, String prim, int data_del, int data_ins, byte ok)
   	MainActivity.db.addRecRASXOD(tranz.get(i).id_tmc,
   			tranz.get(i).kol, tranz.get(i).ed, tranz.get(i).price,0, tranz.get(i).id_post, /*tranz.get(i).id_klient*/(int)cou, tranz.get(i).prim, MainActivity.getIntDataTime(), (byte)0);	
   	//Log.d("MyLog", "AtagB="+tranz.get(i).tagB+" i="+i);
   	//Log.d("MyLog", "AtagB="+tranz.get(i).tagB+" i="+i+" ost="+but.get(tranz.get(i).tagB).ost);
   	
   	but.get(tranz.get(i).tagB).ost=but.get(tranz.get(i).tagB).ost-tranz.get(i).kol;
   	if (but.get(tranz.get(i).tagB).ost<=0 && but.get(tranz.get(i).tagB).ed==1)//если кол-во <0 то добавляем остаток по приходу
   	{
   		MainActivity.db.addRecPRIXOD(tranz.get(i).id_tmc, -but.get(tranz.get(i).tagB).ost+1, (byte)tranz.get(i).ed, tranz.get(i).price, tranz.get(i).price, tranz.get(i).id_post, "автоувеличение+1 остатка "+but.get(tranz.get(i).tagB).ost+" чек "+cou, MainActivity.getIntDataTime(),(byte)0);
   		but.get(tranz.get(i).tagB).ost=1;
   	}
   	//Log.d("MyLog", "PtagB="+tranz.get(i).tagB+" ost="+but.get(tranz.get(i).tagB).ost);
   	if (tranz.get(i).tagB>CountTara-1) {
   		
   	//Log.d("MyLog", but.get(tranz.get(i).tagB).tb.getTextOff().toString() );
   		int l1=(but.get(tranz.get(i).tagB).tmc_name+"\n").length(), l2=(but.get(tranz.get(i).tagB).tmc_name+"\n"+MainActivity.round( but.get(tranz.get(i).tagB).ost,3)+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price).length();
       	final SpannableStringBuilder text = new SpannableStringBuilder(but.get(tranz.get(i).tagB).tmc_name+"\n"+MainActivity.round( but.get(tranz.get(i).tagB).ost,3)+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price); 
       	//final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(255, 0, 0)); 
       	final StyleSpan style2 = new StyleSpan(Typeface.BOLD); 
       	final AbsoluteSizeSpan s12 = new AbsoluteSizeSpan(MainActivity.butNameS,false);
       	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
       	
       	//text.setSpan(style, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
       	//textView.setTypeface(null,Typeface.BOLD);
       	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
   	but.get(tranz.get(i).tagB).tb.setTextOff(text);//(but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price);
   	but.get(tranz.get(i).tagB).tb.setTextOn(text);//(but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price);
   	but.get(tranz.get(i).tagB).tb.setChecked(but.get(tranz.get(i).tagB).tb.isChecked());
   	//Log.d("MyLog", but.get(tranz.get(i).tagB).tb.getTextOff().toString() );
   	
   	//Log.d("MyLog", but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price );
   	}
   	//but.get(tranz.get(i).tagB).kol=but.get(tranz.get(i).tagB).kol-tranz.get(i).kol;
   	//tbut.get(tranz.get(i).tagB-7).setTextOff(c.getString(c.getColumnIndex("name"))+"\nОСТ "+but.get(tranz.get(i).tagB).kol+"Л ЦЕНА "+c.getDouble(c.getColumnIndex("price")));
   	//tbut.get(tranz.get(i).tagB-7).setTextOn(c.getString(c.getColumnIndex("name"))+"\nОСТ "+but.get(tranz.get(i).tagB).kol+"Л ЦЕНА "+c.getDouble(c.getColumnIndex("price")));
   	}
   	
   	//tvCombo.setText("");
   	
   	//Toast.makeText(RasxodActivity.this, "ЧЕК № "+cou+" НА СУММУ:"+tvSum.getText().toString(), Toast.LENGTH_LONG).show();
   	String sChek="";
   	for (int i=0; i<data.size(); i++) 
   	{sChek=sChek+"\n"+data.get(i).get("name").toString()+" "+data.get(i).get("kol").toString()+" "+data.get(i).get("price").toString()+" "+data.get(i).get("tara").toString(); }	
   	
   	showMessage("ЧЕК № "+MainActivity.num_id+" НА СУММУ:"+tvSum.getText().toString()+"\n"+sChek, (byte)1);
   	MainActivity.num_id++;	
   	data.clear();
   	sAdapter.notifyDataSetChanged();
   	tvSum.setText("");sumI=0;tranz.clear();
   	tbTara.setChecked(false);
   	}
   }
  
   void getCheckDialog() {
	   
	   if (data.size()!=0)
   	{
		// int klient=0; if (!=0) 
    long cou=0; cou=MainActivity.db.addRecKLIENTcount(MainActivity.num_id, "чек№ "+MainActivity.num_id, MainActivity.StrToFloat(tvSum.getText().toString()), 
    		MainActivity.round( MainActivity.StrToFloat((etSkidka.getText().toString()))+MainActivity.StrToFloat((etSkidkaPerSum.getText().toString())),2), "чек закрыт с проверкой" , MainActivity.getIntDataTime(),(int) MainActivity.StrToFloat(tvIdKlient.getText().toString()) , (byte)0);
    MainActivity.num_id++;
    //это общая скидка, если она есть (суйчас она =0 не заполняется и invisible)
    /*if (MainActivity.StrToFloat(etSkidka.getText().toString())!=0)
   		MainActivity.db.addRecRASXOD(0, -MainActivity.StrToFloat(etSkidka.getText().toString()), (byte)4, 0, 0, 0, (int)cou, "СКИДКА "+etSkidka.getText().toString()+"ГРН", MainActivity.getIntDataTime(), (byte)0);
общая скидка по чеку в klient.skidka делится на количество позиций в чеке и прибавляется к rasxod.skidka */
    //Log.d("MyLog", "0tranz="+tranz.size());
    float sk=0, skid=0;
    if (MainActivity.StrToFloat(etSkidka.getText().toString())!=0) sk=/*MainActivity.round(*/MainActivity.StrToFloat(etSkidka.getText().toString())/tranz.size()/*,2)*/;
    
    for (int i=0; i<tranz.size(); i++) {
    skid=0;	
    if (tranz.get(i).tagL!=-1) skid=MainActivity.StrToFloat( data.get(tranz.get(i).tag).get("skidka_sum").toString() );
	
   	//long tmp = MainActivity.db.addRecRASXODcount(tranz.get(i).id_tmc,
   		//	tranz.get(i).kol, tranz.get(i).ed, tranz.get(i).price, tranz.get(i).id_post, /*tranz.get(i).id_klient*/(int)cou, tranz.get(i).prim, MainActivity.getIntDataTime(), (byte)0);	
    	MainActivity.db.addRecRASXOD(tranz.get(i).id_tmc,
       			tranz.get(i).kol, tranz.get(i).ed, tranz.get(i).price, sk+skid, tranz.get(i).id_post, /*tranz.get(i).id_klient*/(int)cou, tranz.get(i).prim, MainActivity.getIntDataTime(), (byte)0);	
       	
  	//Log.d("MyLog", "data="+data.get(tranz.get(i).tagL).get("skidka_sum").toString());
   	//эта жесть создает скидку по позиции если она есть в чеке (в поле rasxod.ok пишу _id расхода, по которой скидка)
   	//Log.d("MyLog", tranz.get(i).tagL+" ");
   	//if (tranz.get(i).tagL!=-1 /*&& MainActivity.StrToFloat( data.get(tranz.get(i).tagL).get("skidka_sum").toString() )!=0*/ )
   	// if (MainActivity.StrToFloat( data.get(tranz.get(i).tag).get("skidka_sum").toString() )!=0)
   	//	MainActivity.db.addRecRASXOD(0, -MainActivity.StrToFloat( data.get(tranz.get(i).tag).get("skidka_sum").toString() ), (byte)4, 0, 0, (int)cou, "СКИДКА ПО ПОЗИЦИИ "+tmp+":"+data.get(tranz.get(i).tag).get("skidka_sum").toString()+"ГРН", MainActivity.getIntDataTime(),(int)tmp);
  
   	but.get(tranz.get(i).tagB).ost=but.get(tranz.get(i).tagB).ost-tranz.get(i).kol;
   	if (but.get(tranz.get(i).tagB).ost<=0 && but.get(tranz.get(i).tagB).ed==1 )//если кол-во <0 то добавляем остаток по приходу
   	{
   		MainActivity.db.addRecPRIXOD(tranz.get(i).id_tmc, -but.get(tranz.get(i).tagB).ost+1, (byte)tranz.get(i).ed, tranz.get(i).price, tranz.get(i).price, tranz.get(i).id_post, "остаток "+but.get(tranz.get(i).tagB).ost+" чек "+cou, MainActivity.getIntDataTime(),(byte)0);
   		but.get(tranz.get(i).tagB).ost=1;
   	}
 
   	if (tranz.get(i).tagB>CountTara-1) {
   		int l1=(but.get(tranz.get(i).tagB).tmc_name+"\n").length(), l2=(but.get(tranz.get(i).tagB).tmc_name+"\n"+MainActivity.round(but.get(tranz.get(i).tagB).ost,3)+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price).length();
       	final SpannableStringBuilder text = new SpannableStringBuilder(but.get(tranz.get(i).tagB).tmc_name+"\n"+MainActivity.round(but.get(tranz.get(i).tagB).ost,3)+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price); 
       	//final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(255, 0, 0)); 
       	final StyleSpan style2 = new StyleSpan(Typeface.BOLD); 
       	final AbsoluteSizeSpan s12 = new AbsoluteSizeSpan(MainActivity.butNameS,false);
       	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
       	//text.setSpan(style, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
       	//textView.setTypeface(null,Typeface.BOLD);
       	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
   	but.get(tranz.get(i).tagB).tb.setTextOff(text);//(but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price);
   	but.get(tranz.get(i).tagB).tb.setTextOn(text);//(but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price);
   	but.get(tranz.get(i).tagB).tb.setChecked(but.get(tranz.get(i).tagB).tb.isChecked());
   	}
   	
   	}
   	
   	//tvCombo.setText("");

   	//Toast.makeText(RasxodActivity.this, "ЧЕК № "+cou+" НА СУММУ:"+tvSum.getText().toString(), Toast.LENGTH_LONG).show();
   	String sChek="";
   	for (int i=0; i<data.size(); i++) 
   	{sChek=sChek+"\n"+data.get(i).get("name").toString()+" "+data.get(i).get("kol").toString()+" "+data.get(i).get("price").toString()+" "+data.get(i).get("tara").toString(); }	
   	
   	//showMessage("ЧЕК № "+cou+" НА СУММУ:"+tvSum.getText().toString()+"\n"+sChek, (byte)1);
   		
   	data.clear();
    etSkidkaPerSum.setText("");
	etSkidkaPer.setText(""); 
	etSkidka.setText("0"); 
	tvIdKlient.setText("0"); 
	etNal.setText("0"); 
	 //tvSdacha.setText("0");
   	tvSum.setText("");sumI=0;tranz.clear();
   	tbTara.setChecked(false);
   	}
	   tvDialogN=0;
	 //  sAdapter.notifyDataSetChanged();
   }
   
  /* public void showPic(View v)
   {

        final Dialog d = new Dialog(RasxodActivity.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.pic_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(100);
        np.setMinValue(0);
        np.setWrapSelectorWheel(true);
        //np.setOnValueChangedListener(this);
        b1.setOnClickListener(new OnClickListener()
        {
         @Override
         public void onClick(View v) {
             tvKol.setText(String.valueOf(np.getValue()));
             d.dismiss();
          }    
         });
        b2.setOnClickListener(new OnClickListener()
        {
         @Override
         public void onClick(View v) {
             d.dismiss();
          }    
         });
      d.show();
   }*/
   
  void dialogNumCancel(int tvI) {
	   switch (tvI) {
	////tvDialogN=R.id.comboSkidkaSum
	   case R.id.comboSkidkaSum:
		   data.get(combo_pos).put("skidka_sum", 0);
		   data.get(combo_pos).put("skidka_but", 0);
		   data.get(combo_pos).put("skidka_but_text", "0%");
		   data.get(combo_pos).put("skidka_sum_itog", data.get(combo_pos).get("summa2"));
		   sAdapter.notifyDataSetChanged();
		   //tvDKol.setText("0");
		float valsum=0;
		      for (int i=0; i<data.size(); i++) 
		     		valsum=valsum+MainActivity.StrToFloat(data.get(i).get("skidka_sum").toString() ) ;
		     		
		      	if (valsum!=0) etSkidkaPerSum.setText(String.valueOf(MainActivity.round(valsum,2))); else etSkidkaPerSum.setText("");
		      	
		   tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
	    			 ));
		   tvDialogN=0;
	     break;
	   case R.id.tvOtherKol_:
		   tvKol.setText("");Btara=-1;
		   tbnKol.setChecked(false); tbnKol.setTextColor(clrNoCheck); tbnKol.setBackground(tbnKol.getContext().getResources().getDrawable(R.drawable.edittexth_style));
		   tvDialogN=0;
	     break;
	   case R.id.etCheckNal:
		   etNal.setText("");
		   tvDialogN=0;
	     break;
	   case R.id.etCheckSkidka:
		   etSkidka.setText("");
		   tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
	    			 ));
		   tvDialogN=0;
	     break;
	   case R.id.etCheckSkidkaPer:
		   etSkidkaPer.setText("");
		   //float val = MainActivity.StrToFloat(tvDKol.getText().toString());
	      	//float valsum=0;
	      	 for (int i=0; i<data.size(); i++) 
	     	{
	     	
	     		data.get(i).put("skidka_but", 0);
	     		//valsum=valsum+MainActivity.StrToFloat(data.get(i).get("summa2").toString() );
	     		data.get(i).put("skidka_sum", 0 );
	     		data.get(i).put("skidka_but_text", "0%" );
	     		data.get(i).put("skidka_sum_itog", 
	             		MainActivity.StrToFloat(data.get(i).get("summa2").toString()  )
	             				);
	      	 }
	      	 //if (valsum!=0) etSkidkaPerSum.setText(String.valueOf(valsum)); else etSkidkaPerSum.setText("");
	      	tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
	    			 ));
	      	
	      	 tvSdacha.setText(String.valueOf (
	      		MainActivity.round(MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString())+MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())*/ ,2)
	      		) );
	      	 
		   tvDialogN=0;
	     break;
	     
	   }
   }


   void dialogNumOK(int tvI, float k/*, AlertDialog*//*View dialog*/) {
	   float valsum=0;
	   switch (tvI) {
	   //tvDialogN=R.id.comboSkidkaSum
	   case R.id.comboSkidkaSum:
				   data.get(combo_pos).put("skidka_but", k /*MainActivity.StrToFloat(tvDKol.getText().toString())*/ );
				   data.get(combo_pos).put("skidka_but_text", /*tvDKol.getText().toString()*/k+"%" );
	        		data.get(combo_pos).put("skidka_sum", 
	        				//round(45.457*100)/100.00
	        		MainActivity.round( MainActivity.StrToFloat(data.get(combo_pos).get("summa").toString() ) * MainActivity.StrToFloat(data.get(combo_pos).get("skidka_but").toString())/100 ,2)
	        		);
	        		data.get(combo_pos).put("skidka_sum_itog", 
	    	        	MainActivity.round(	MainActivity.StrToFloat(data.get(combo_pos).get("summa2").toString() )-MainActivity.StrToFloat(data.get(combo_pos).get("skidka_sum").toString() ),2)
	    	        		);

		  // float valsum=0;
	      for (int i=0; i<data.size(); i++) 
	     		valsum=valsum+MainActivity.StrToFloat(data.get(i).get("skidka_sum").toString() ) ;
	     		
	      	if (valsum!=0) etSkidkaPerSum.setText(String.valueOf(MainActivity.round(valsum,2))); else etSkidkaPerSum.setText("");
	      	tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
	    			 ));
	      	tvSdacha.setText( String.valueOf (
	      		MainActivity.round(MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + valsum*/,2) 
	      			) );
	      	 
		   sAdapter.notifyDataSetChanged();
		   tvDialogN=0;
	     break;
	   case R.id.tvOtherKol_:
        	 tvKol.setText(/*tvDKol.getText()*/String.valueOf(k));
        	 if (Btovar!=-1) fixV();
		   tvDialogN=0;
	     break;
	   case R.id.etCheckNal:
        	 etNal.setText(/*tvDKol.getText()*/String.valueOf(k));
        	 //tvSdacha.setText(String.valueOf (MainActivity.StrToFloat(etNal.getText().toString())-(MainActivity.StrToFloat(tvDItogo.getText().toString())-MainActivity.StrToFloat(etSkidka.getText().toString())) ) );
        	 //tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
        		//	 ));
        	 tvSdacha.setText(String.valueOf ( 
        			MainActivity.round( MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())*/ ,2)
        			 ) );
        	 
		   tvDialogN=0;
	     break;
	   case R.id.etCheckSkidka:
      	etSkidka.setText(/*tvDKol.getText()*/String.valueOf(k));
      	tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
      			 ));
      	 if (MainActivity.StrToFloat(etNal.getText().toString())!=0)
      	 //tvSdacha.setText(String.valueOf (MainActivity.StrToFloat(etNal.getText().toString())-(MainActivity.StrToFloat(tvDItogo.getText().toString())-MainActivity.StrToFloat(etSkidka.getText().toString())) ) );
      		
      		 tvSdacha.setText(String.valueOf ( 
      			MainActivity.round(	MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())*/,2)
      			) );
      	 
		   tvDialogN=0;
	     break;
	   case R.id.etCheckSkidkaPer:
      	 etSkidkaPer.setText(/*tvDKol.getText()*/String.valueOf(k)); etSkidkaPer.setTag(etSkidkaPer.getText());
      	float val = /*MainActivity.StrToFloat(tvDKol.getText().toString())*/k;
      	 for (int i=0; i<data.size(); i++) 
     	{
     		if (val !=0 )
     		{
     		data.get(i).put("skidka_but", val);
     		data.get(i).put("skidka_but_text", val+"%");
     		valsum=valsum+MainActivity.round( MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) * val/100,2);
     		data.get(i).put("skidka_sum", 
     		 MainActivity.round( MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) * val/100,2)
     				);
     		data.get(i).put("skidka_sum_itog", 
             	MainActivity.round(MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) -MainActivity.StrToFloat(data.get(i).get("skidka_sum").toString() ),2)
             				);
     		}
     	}
      	 
      	 if (valsum!=0) etSkidkaPerSum.setText(String.valueOf(MainActivity.round(valsum,2))); else etSkidkaPerSum.setText("");
      	tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
   			 ));
      	 tvSdacha.setText(String.valueOf ( 
      		MainActivity.round(	MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + valsum*/,2)
      		) );
      	 sAdapter.notifyDataSetChanged();
		 tvDialogN=0;
	     break;
	     default: tvDialogN=0;
	   }
   }
   
   @Override
   protected Dialog onCreateDialog(int id) {
     ///////////////dialog 2 - number
     if (id == 2) {
         AlertDialog.Builder adb = new AlertDialog.Builder(this);
         //adb.setTitle("ПРОВЕРЬТЕ ЧЕК ПЕРЕД ЗАКРЫТИЕМ");
         //adb.setMessage("Message");
         //strDialog="";
         Dview = (LinearLayout) getLayoutInflater().inflate(R.layout.chek, null);
         MainActivity.setSizeFontMain(Dview);
         //Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btComa, btDD, btXD;
 	    //Window window = getDialog().getWindow();
 	    //Dview.setLayoutParams(new LinearLayout.LayoutParams(display_w/*-(int)(display_w/6)*/, display_h/*-(int)(display_h/8)*/,1)); 
 	    //setLayout(, );
 	    //adb.setGravity(Gravity.CENTER);
         // устанавливаем ее, как содержимое тела диалога
         adb.setView(Dview);
         // tvDKol - сдача

      	//etNal.clearFocus();
       	etSkidkaPer = (TextView) Dview.findViewById(R.id.etCheckSkidkaPer);
      	tvIdKlient = (TextView) Dview.findViewById(R.id.tvIdKlientCheck);
      	tvDItogo = (TextView) Dview.findViewById(R.id.etItogSummaD);
      	etCheckCheck = (TextView) Dview.findViewById(R.id.etCheckCheck);
        
        tvSdacha = (TextView) Dview.findViewById(R.id.tvCheckSdacha);
      	etNal = (TextView) Dview.findViewById(R.id.etCheckNal);
      	tvNal = (TextView) Dview.findViewById(R.id.tvCheckNal);
      	etSkidka = (TextView) Dview.findViewById(R.id.etCheckSkidka);

      	etSkidkaPerSum = (TextView) Dview.findViewById(R.id.etCheckSkidkaPerSum);
      	tvSkidka = (TextView) Dview.findViewById(R.id.tvCheckSkidka);
      	spKlient = (Spinner) Dview.findViewById(R.id.sp_check_klient);
      	String[] fromKlient = new String[] {"_id","name","sconto_per","sconto_sum"};
        int[] toKlient = new int[] {R.id.spt_1, R.id.spt_2, R.id.spt_3, R.id.spt_4};
        //final Cursor cKlient = MainActivity.db.getRawData("select _id, name, 0 sconto_per, 0 sconto_sum from foo union all select _id, name, sconto_per, sconto_sum from karta_klient where ok=0 ",null);
        final Cursor cKlient = MainActivity.db.getRawData("select _id, name, sconto_per, sconto_sum from karta_klient where ok=0 ",null);
        scaKlient = new SimpleCursorAdapter(this, R.layout.spinner_quatro, cKlient, fromKlient, toKlient);   
        scaKlient.setDropDownViewResource(R.layout.spinner_quatro_down); 

        spKlient.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
            	spKlient.setTag(id);
            	tvIdKlient.setText(String.valueOf(id));
            	etSkidkaPer.setText(cKlient.getString(2));
            	
            	float val=0;
            	for (int i=0; i<data.size(); i++) 
            	{
            	//data.get(combo_pos).put("skidka_sum", tvDKol.getText());
            		if (MainActivity.StrToFloat(cKlient.getString(2))!=0 )
            		{
            		data.get(i).put("skidka_but", cKlient.getDouble(2));
            		data.get(i).put("skidka_but_text", cKlient.getDouble(2)+"%");
            		val=val+MainActivity.round(MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) * MainActivity.StrToFloat(data.get(i).get("skidka_but").toString())/100,2);
            		data.get(i).put("skidka_sum", 
            		MainActivity.round( MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) * MainActivity.StrToFloat(data.get(i).get("skidka_but").toString())/100 ,2)
            				);
            		data.get(i).put("skidka_sum_itog", 
                    		MainActivity.round(MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) -MainActivity.StrToFloat(data.get(i).get("skidka_sum").toString() ),2)
                    				);
            		}
            	}
            	if (val!=0) etSkidkaPerSum.setText(String.valueOf(MainActivity.round(val,2))); else etSkidkaPerSum.setText(String.valueOf(""));
            	
            	tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
   	    			 ));
            	
            	sAdapter.notifyDataSetChanged();
    		   tvDialogN=0;
            	//tvEd.setText(/*cProd*/cursor.getString(((Cursor)spProd.getItemAtPosition(position)).getColumnIndex("ed")) );
            	//getSupportLoaderManager().getLoader(0).forceLoad();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	spKlient.setTag(0);
            	tvIdKlient.setText("0");
            	etSkidkaPer.setText("");
            	etSkidkaPerSum.setText("");
            	for (int i=0; i<data.size(); i++) 
            	{
            		data.get(i).put("skidka_but", 0);
            		data.get(i).put("skidka_but_text", "0%");
            		data.get(i).put("skidka_sum",0);
            		data.get(i).put("skidka_sum_itog", 
                    		MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) );
            	}
            	sAdapter.notifyDataSetChanged();
    		    tvDialogN=0;
            }
            });
        spKlient.setAdapter(scaKlient);
        
      	
      	//etSkidka.clearFocus();
      	
      	//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
      	/*
      	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      	imm.hideSoftInputFromWindow(etNal.getWindowToken(),
      			InputMethodManager.HIDE_NOT_ALWAYS);*/
      	lvComboD = (ListView) Dview.findViewById(R.id.lvComboDia);
        /*String[] from = { "name", "kol","price", "tara","itog", "summa","button"};
        int[] to = { R.id.comboName, R.id.comboKol,R.id.comboPrice, R.id.comboTara, R.id.comboItog, R.id.comboSumma, R.id.comboX };
             // создаем адаптер
             sAdapterD = new MySimpleAdapter(this, data, R.layout.combo_item, from, to);
             // определяем список и присваиваем ему адаптер
             sAdapterD.setViewBinder(new MyViewBinder());*/
             lvComboD.setAdapter(sAdapter);

         adb.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
            	 tvDialogN=0;
            	 // Log.d("MyLog", "Cancel");
            	 //tvKol.setText("");
            	 //tbnKol.setChecked(false); tbnKol.setTextColor(clrNoCheck); tbnKol.setBackground(getResources().getDrawable(R.drawable.edittexth_style));
             }
         })
         .setPositiveButton("ЗАКРЫТЬ ЧЕК", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
          	  // Log.d("MyLog", "Dismiss");
            		 
            	 getCheckDialog(); 
            	 //sAdapter.notifyDataSetChanged();
             }
         });
         
        
         dialogg = adb.create();
         
         dialogg.setOnDismissListener(new OnDismissListener() {
     		
     		@Override
     		public void onDismiss(DialogInterface arg0) {
     			dialogNumCancel(tvDialogN);
     		}
     	});
         
        // WindowManager.LayoutParams lp = dialogg.getWindow().getAttributes();
     	//lp.dimAmount = 0.6f; // уровень затемнения от 1.0 до 0.0
     	//lp.width=display_w-(int)(display_w/7);
     	//lp.height=display_h-(int)(display_h/7);
        // WindowManager.LayoutParams lp = dialogg.getWindow().getAttributes();
       //  lp.width=display_h;
       //  lp.height=display_h;
     	//dialogg.getWindow().setAttributes(//lp
     	//		new WindowManager.LayoutParams( WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
     	//		); //setLayout(display_w, display_h); 

     	//dialogg.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
     	// Установите заголовок
     	//dialogg.setTitle("ПРОВЕРЬТЕ ЧЕК ПЕРЕД ЗАКРЫТИЕМ");
         // обработчик отображения
         /*dialogg.setOnShowListener(new OnShowListener() {
           public void onShow(DialogInterface dialog) {
          	 
           }
         });*/

         return dialogg;
       }
     
     return super.onCreateDialog(id);
   }
   
 
   
   @Override
   protected void onPrepareDialog(int id, Dialog dialog) {
     super.onPrepareDialog(id, dialog);
     //Log.d("MyLog", "Prepare");
     //if (id == 1) { strDialog=""; tvDKol.setText("");  titleD.setText(dialogNumTitle(tvDialogN)); }
     if (id == 2) {
    	 //etSkidkaPerSum.setText("");
    	 //etSkidkaPer.setText(""); 
    	 //etSkidka.setText("0"); 
    	 //tvIdKlient.setText("0"); 
    	 //etNal.setText("0"); 
    	 //tvSdacha.setText("0");
    	 etCheckCheck.setText(String.valueOf(MainActivity.num_id));
    	 //tvDItogo.setText(tvSum.getText());  
    	 tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
    			 ));
    	 /*float vals=0;
	      for (int i=0; i<data.size(); i++) 
	     		vals=vals+MainActivity.StrToFloat(data.get(i).get("skidka_sum").toString() ) ;
	     		
	      	if (vals!=0) etSkidkaPerSum.setText(String.valueOf(vals)); else etSkidkaPerSum.setText("");*/
	      	tvSdacha.setText( String.valueOf (
	      		MainActivity.round(	MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())*//*vals*/,2)
	      		) );
	      	 
		   //sAdapter.notifyDataSetChanged();
		   //tvDialogN=0;
		   
    	 
     etNal.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
         	//showPic(v);
         	tvDialogN=R.id.etCheckNal;
         	
         	//showDialog(1);
         	makeDialog();
         }
       });
   	tvNal.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
         	//showPic(v);
         	tvDialogN=R.id.etCheckNal;
         	
         	//showDialog(1);
         	makeDialog();
         }
       });
   	
   	etSkidkaPer.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showPic(v);
        	tvDialogN=R.id.etCheckSkidkaPer;
        	
        	//showDialog(1);
        	makeDialog();
        }
      });
   	
   	etSkidka.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showPic(v);
        	tvDialogN=R.id.etCheckSkidka;
        	//showDialog(1);
        	makeDialog();
        }
      });
  	tvSkidka.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//showPic(v);
        	tvDialogN=R.id.etCheckSkidka;
        	//showDialog(1);
        	makeDialog();
        }
      });
   	
     //strDialog="";
     
     //spKlient.setSelection((int) MainActivity.StrToFloat(tvIdKlient.getText().toString())-1);
     //etSkidkaPer.setText( (int)MainActivity.StrToFloat(etSkidkaPer.getTag().toString()) );
     }
 	
     /*if (id == 1) {
       // Находим TextView для отображения времени и показываем текущее
       // время
       TextView tvTime = (TextView) dialog.getWindow().findViewById(
           R.id.tvTime);
       tvTime.setText(sdf.format(new Date(System.currentTimeMillis())));
       // если была нажата кнопка Добавить
       if (btn == R.id.btnAdd) {
         // создаем новое TextView, добавляем в диалог, указываем текст
         TextView tv = new TextView(this);
         view.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT,
             LayoutParams.WRAP_CONTENT));
         tv.setText("TextView " + (textViews.size() + 1));
         // добавляем новое TextView в коллекцию
         textViews.add(tv);
         // иначе
       } else {
         // если коллекция созданных TextView непуста
         if (textViews.size() > 0) {
           // находим в коллекции последний TextView
           TextView tv = textViews.get(textViews.size() - 1);
           // удаляем из диалога
           view.removeView(tv);
           // удаляем из коллекции
           textViews.remove(tv);
         }
       }
       // обновляем счетчик
       tvCount.setText("Кол-во TextView = " + textViews.size());
     }*/
   } 
   
  @Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked)
		{buttonView.setTextColor(clrCheck);
		buttonView.setBackground(getResources().getDrawable(R.drawable.btn_chek));
		 //Log.d("MyLog", "tag="+Byte.parseByte(buttonView.getTag().toString()));
		tara(Byte.parseByte(buttonView.getTag().toString()));
		if (Btovar!=-1) fixV(); 
		}
		else
			{buttonView.setTextColor(clrNoCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
	}
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    MainActivity.seek=sbar.getProgress();
    c.close();
  }

  @Override
  protected void onStart() {
	  super.onStart();
  }
  
  @Override
  protected void onRestart() {
    super.onRestart();
    
   // btnBack.setTag(-1);
    //Log.d("MyLog", "restart "+btnBack.getTag().toString());
//    c.requery();
    //getSupportLoaderManager().getLoader(0).forceLoad();
  } 
  
  class MySimpleAdapter extends SimpleAdapter {
	  private List<? extends Map<String, ?>> mData;
	    private int mResource;
	    private String[] mFrom;
	    private int[] mTo;
	    public MySimpleAdapter(Context context,
	        List<? extends Map<String, ?>> data, int resource,
	        String[] from, int[] to) {
	      super(context, data, resource, from, to);
	      	mData = data;
	        mResource = resource;
	        mFrom = from;
	        mTo = to;
	    }
	 
	    @Override
	    public void setViewText(TextView v, String text) {
	      // метод супер-класса, который вставляет текст
	      super.setViewText(v, text);
	      // если нужный нам TextView, то разрисовываем 
	      if (v.getId() == R.id.comboSumma) {
	        //float i = Float.parseFloat(text);
	        //if (i < 0) v.setTextColor(Color.RED); else
	          //if (i > 100) v.setTextColor(Color.RED);
	      }
	    }
	 
	    @Override
	    public void setViewImage(ImageView v, int value) {
	      // метод супер-класса
	      super.setViewImage(v, value);
	      // разрисовываем ImageView
	      //if (value == negative) v.setBackgroundColor(Color.RED); else
	        //if (value == positive) v.setBackgroundColor(Color.GREEN);
	    }
	    
	    @Override
        public View getView (int position, View convertView, ViewGroup parent)
        {
            View v = super.getView(position, convertView, parent);
            final int pos=position;
            //View[] columns = (View[]) v.getTag();
            final Map dataSet = mData.get(position);
            //for (int i = 0; i < mTo.length; i++) {
             //   TextView col = (TextView) columns[i];
                //col.setText((String) 
                		//;
            //}   
            if (tvDialogN!=0 && tvDialogN!=R.id.tvOtherKol_) {
            /*TextView tvI=(TextView)v.findViewById(R.id.comboItog);
            tvI.setTextColor(Color.WHITE);
            TextView tvS=(TextView)v.findViewById(R.id.comboSumma);
            tvS.setTextColor(Color.WHITE);*/
            	LinearLayout llHide=(LinearLayout)v.findViewById(R.id.comboHideLL);
                llHide.setVisibility(LinearLayout.GONE);
                final TextView tSum=(TextView)v.findViewById(R.id.comboSumma2);   
                tSum.setVisibility(TextView.VISIBLE);
                final TextView tSkidka=(TextView)v.findViewById(R.id.comboSkidkaSum);   
                tSkidka.setVisibility(TextView.VISIBLE);
                
            Button bSkidka=(Button)v.findViewById(R.id.comboSkidka);
     		
     		//tSkidka.setText(MainActivity.StrToFloat(data.get(pos).get("skidka_but").toString())==0?"":
     			//MainActivity.StrToFloat(data.get(pos).get("summa2").toString())-MainActivity.StrToFloat(data.get(pos).get("skidka_sum").toString())+"");
     		//bSkidka.setText
     		//(MainActivity.StrToFloat(data.get(pos).get("skidka_but").toString())==0?"СКИДКА":MainActivity.StrToFloat(data.get(pos).get("skidka_but").toString())+"%");
            
            bSkidka.setVisibility(Button.VISIBLE);
            bSkidka.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View arg0) {
            	tvDialogN=R.id.comboSkidkaSum;
            	combo_pos=pos;
            	
               	//showDialog(1);
            	makeDialog();
               }
           });
            tSkidka.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
             	tvDialogN=R.id.comboSkidkaSum;
             	combo_pos=pos;
             	
                	//showDialog(1);
             	makeDialog();
                }
            });

            }
            
            
            Button b=(Button)v.findViewById(R.id.comboX);
             b.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                	
                	//Toast.makeText(RasxodActivity.this,dataSet.get("summa").toString()+" position "+pos,Toast.LENGTH_SHORT).show();
                	sumI=sumI-Float.parseFloat(dataSet.get("summa").toString());
                	tvSum.setText(String.valueOf(MainActivity.round(sumI,2)));
                	//if (tvDialogN!=0) tvDItogo.setText(String.valueOf(sumI));
                	//for (int i=0; i<tranz.size(); i++)
                	for (int i=tranz.size()-1; i>=0; i--) 
                	//{
                		if (tranz.get(i).tag==(byte)pos) tranz.remove(i); 
                	//}
                	
                	for (int i=tranz.size()-1; i>=0; i--)
                	//{
                		if (tranz.get(i).tag>=pos ) tranz.get(i).tag=(byte)(tranz.get(i).tag-1);
                	//}
                	data.remove(pos);
                	sAdapter.notifyDataSetChanged();
                
                }
            });
             
            return v;
        }
	//@Override    public void bindV
  }
  
  class MyViewBinder implements SimpleAdapter.ViewBinder {
	     
	    //int red = getResources().getColor(R.color.Red);
	    //int orange = getResources().getColor(R.color.Orange);
	    //int green = getResources().getColor(R.color.Green);
	     
	    @Override
	    public boolean setViewValue(View view, Object data,
	        String textRepresentation) {
	      int i = 0;
	      //MainActivity.setSizeFontItem((LinearLayout)/*findViewById(R.id.oborotka_item_tr)*/view);
	      switch (view.getId()) {
	      // LinearLayout
	      /*case R.id.llLoad:
	        i = ((Integer) data).intValue();
	        if (i < 40) view.setBackgroundColor(green); else
	          if (i < 70) view.setBackgroundColor(orange); else
	        view.setBackgroundColor(red);
	        return true;
	      // ProgressBar  
	      case R.id.pbLoad:
	        i = ((Integer) data).intValue();
	        ((ProgressBar)view).setProgress(i);
	        return true;*/
	      case R.id.comboX:
		        //i = ((Integer) data).intValue();
		        //((ProgressBar)view).setProgress(i);
	    	  /*((Button)view).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					sAdapter.
					AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
					sAdapter.notifyDataSetChanged();
				}
			});*/
		        return true;
	      }
	      return false;
	    }
	  }
@Override
public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	// TODO Auto-generated method stub

}

@Override
public void onStartTrackingTouch(SeekBar seekBar) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStopTrackingTouch(SeekBar seekBar) {
	//int leftValue = progress;
	  //int rightValue = seekBar.getMax() - progress;
	  // настраиваем вес
	if (seekBar.getProgress()>20 && seekBar.getProgress()<80)
	{
	  llLP.weight = seekBar.getProgress();
	  llRP.weight = seekBar.getMax()-seekBar.getProgress();
		
	  llL.requestLayout();
	}
}
 
}

