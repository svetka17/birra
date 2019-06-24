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
//import android.content.DialogInterface.OnDismissListener;
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
  static TextView  tvIdPgr, tvNamePgr;
  TextView titleD, tvNal, tvSkidka, tvIdKlient, etCheckCheck;
  SimpleCursorAdapter scaKlient;
  Spinner spKlient;
  ListView lvComboD;
  LinearLayout Dview;

  ScrollView svBut;
  TableLayout llbut;
  TableRow row;
  
  LinearLayout llL, llR, llRR;
  LinearLayout svPgr;
  LinearLayout.LayoutParams llLP;
  LinearLayout.LayoutParams llRP;
  SeekBar sbar;
  HorizontalScrollView hll_tara_button;
  LinearLayout ll_tara_button;
  LinearLayout lltarabutton;
  int Bpost=0,btnK=0,clrCheck=Color.WHITE;
  int flag_cash=0;
  int CountTara=0;
  int tmp_minus=0;
  //double kol_in_chek=0;
  String tmcV;
  String kol_skidka="";
  String strDialog="", strKol="";
  
  String regexp_numb = "\\-?\\d+(\\.\\d{0,})?";
  
  Dialog dialogg;
  AlertDialog dial;

//упаковываем данные в понятную для адаптера структуру
  ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
  int combo_pos=0, tvDialogN=0, clrNoCheck=Color.BLACK;
  MySimpleAdapter sAdapter;//, sAdapterD;
  TextView etNal, etSkidka,etSkidkaPer,etSkidkaPerSum, tvKol, tvSdacha,tvDItogo, tvSum;
  ToggleButton tbnKol, tbTara;//, tbBrak, tbMove;
  int Btovar=-1, Btara=-1;
  Map<String, Object> m;
  ArrayList<chB> but = new ArrayList<chB>();
  ArrayList<tranDB> tranz = new ArrayList<tranDB>();
  double sumI=0;
  ListView lvCombo;
  //DialogScreen getkol;
  int count_but_tara=0, count_but=0, count_but_pgr=0;
 // static int tmp_pgr=0;
  void tara(int ii) {
	  Btara=ii;
	  for (int i=0; i<CountTara; i++) 
		  if (Integer.parseInt(but.get(i).tb.getTag().toString())!=ii) 
		  {but.get(i).tb.setChecked(false);but.get(i).tb.setTextColor(clrNoCheck); 
		  but.get(i).tb.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
	  if (Integer.parseInt(tbnKol.getTag().toString())!=ii) 
	  {tbnKol.setChecked(false);tbnKol.setTextColor(clrNoCheck);tbnKol.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
  }

  void showMessage(String s, int dur){
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
	   int indBT;
	   int id_tmc;
	   int keg;
	   String tmc_name;
	   String string_keg;
	   double val;
	   double ost;
	   int post;
	   double kol;
	   double price;
	   int ed;
	   String ted;
	   int count_rasxod;
	   double kol_in_chek;
	   //double nedo;
	   //double izl;
	   ToggleButton tb;
	  chB(int indBT, int id_tmc, int keg, String tmc_name, String string_keg, double val, double ost, int post, double kol, double price, int ed, String ted, int count_rasxod, double kol_in_chek, /*double nedo, double izl,*/ ToggleButton tb){
		  this.indBT=indBT;//tag button
		  this.tmc_name=tmc_name;
		  this.string_keg=string_keg;
		  this.id_tmc=id_tmc;
		  this.keg=keg; //keg for ed=1 - litr
		  this.val=val;
		  this.ost=ost;//kol for tara, price for ostat
		  this.post=post;
		  this.kol=kol;
		  this.price=price;
		  this.ed=ed;
		  this.ted=ted;
		  this.count_rasxod=count_rasxod;
		  this.kol_in_chek=kol_in_chek;
		  //this.nedo=nedo;
		  //this.izl=izl;
		  this.tb=tb;
  }}
  /*
   * ok 
   * 0 - нормальный расход
   * 1 - брак
   * 2 - перемещение в другой магазин
   * 3 - излишки
   * 4 - недостача
   * 5 - исправление остатка*
   */ 
  static class tranDB {
	  //addRecRASXOD(int id_tmc, float kol, float price, int id_post, int id_klient, String prim, int data_del, int data_ins, byte ok)
	   int brak; //0-norm, 1-brak, 2-move
	   int move;
	   int tag; //порядковый номер в чеке в combo (для тары = нмер в комбо что было понятно к какой позиции цека тара - для удаления)
	   int tagL; //порядковый номер в чеке в combo (для тары = -1)
	   int tagB; //таг кнопки
	   int id_tmc;
	   int keg;
	   double kol;
	   int ed;
	   double price;
	   //float skidka;
	   int id_post;
	   int id_klient; //пока ничего не пишется
	   String prim;
	   //double nedo;// недостачи проходят по кнопке <->
	   double izl;
	  tranDB(int brak, int move, int tag, int tagL, int tagB, int id_tmc, int keg, double kol, int ed, double price, /*float skidka,*/ int id_post,int id_klient, String prim, /*double nedo,*/ double izl){
		  this.brak=brak;
		  this.move=move;
		  this.tag=tag;
		  this.tagL=tagL;
		  this.tagB=tagB;
		  this.id_tmc=id_tmc;
		  this.keg=keg;
		  this.kol=kol;
		  this.ed=ed;
		  this.price=price;
		  //this.skidka=skidka;
		  this.id_post=id_post;
		  this.id_klient=id_klient;
		  this.prim=prim;
		  //this.nedo=nedo;
		  this.izl=izl;
		  }
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
    svPgr = (LinearLayout) findViewById(R.id.svPgr);
    llLP = (LinearLayout.LayoutParams) llL.getLayoutParams();
    llRP = (LinearLayout.LayoutParams) llRR.getLayoutParams();
    
    sbar = (SeekBar) findViewById(R.id.seekBar);
    sbar.setProgress(MainActivity.seek);
    llLP.weight = sbar.getProgress();
	llRP.weight = sbar.getMax()-sbar.getProgress();
	llL.requestLayout();
    sbar.setOnSeekBarChangeListener(this);
    
    tvNamePgr = (TextView) findViewById(R.id.tvNamePgrBack);
    btnBack = (Button) findViewById(R.id.btnBackPgr_);
    btnBack.setTag(-1);
    btnBack.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	/*if (Integer.parseInt(v.getTag().toString())!=-1) {
        		v.setTag(-1);
        		tvNamePgr.setText("");
        		setBut();
        	}
        	else*/
        		finish();
        }
      });
    
    tvIdPgr = (TextView) findViewById(R.id.tvIdPgrBack);
    
    //Log.d("MyLog", " Create ");
    tvIdPgr.setText("-1");
    tvIdPgr.setTag(-1);
      
    Cursor cTara = MainActivity.db.getRawData ("select T._id as _id, T.name as name, TT.price as price, T.tara as tara, S.id_post as id_post, S.kol as kol, S.ed as ed, E.name as ted from tmc T inner join ostat S on T._id=S.id_tmc left join tmc_ed E on S.ed=E._id left join tmc_price as TT on S.id_tmc=TT.id_tmc and S.id_post=TT.id_post and S.ed=TT.ed  where T.ok=1 and S.kol>0 order by T.pos",/*order by T.tara*/null);
    int ib=0;//, il=0;
    if (cTara.moveToFirst()) { 
    	 
        do {
        	LinearLayout.LayoutParams PB = new LinearLayout.LayoutParams(/*LinearLayout.LayoutParams.WRAP_CONTENT*/(int)MainActivity.w/8, LinearLayout.LayoutParams.MATCH_PARENT,1);
            PB.weight=1;
          	but.add(new chB(
        			ib, 							//indBT
        			cTara.getInt(cTara.getColumnIndex("_id")), //id_tmc
        			0,//keg
        			cTara.getString(cTara.getColumnIndex("name")), //tmc_name
        			"",
        			cTara.getDouble(cTara.getColumnIndex("tara")),//val
        			cTara.getDouble(cTara.getColumnIndex("kol")), //ost 
        			cTara.getInt(cTara.getColumnIndex("id_post")),//id_post
        			1,									//kol
        			MainActivity.round2( 
        					cTara.getDouble(cTara.getColumnIndex("price")) ), //price 
        			cTara.getInt(cTara.getColumnIndex("ed")), //ed
        			cTara.getString(cTara.getColumnIndex("ted")), //ted
        			1,
        			0,
        			//0,//nedo
        			//0,//izl
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

        	ib++;
        } while (cTara.moveToNext());
        
      } else cTara.close();
    
    CountTara=ib;
    
    	//Log.d("MyLog", " CountTara "+CountTara);
    setPgr();

    
    tvKol = (TextView) findViewById(R.id.tvOtherKol_);
    tvKol.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.butTara );
    tvKol.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			tbnKol.setChecked(true);			
		}
	});
    
    tbnKol = (ToggleButton) findViewById(R.id.btnOtherKolRasxod);
    tbnKol.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.butTara );
    tbnKol.setTag(-2);
    tbnKol.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked)
			 {buttonView.setTextColor(clrCheck); 
			 buttonView.setBackground(getResources().getDrawable(R.drawable.btn_chek));
			 tara(-2); 
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
   String[] from = { "name", "kol","price","summakp","summa", "tara","itog",  /*"button",*/"summa2","skidka_but_text","skidka_sum_itog"};
   int[] to = {  R.id.comboName, R.id.comboKol,R.id.comboPrice,R.id.comboSummaKP, R.id.comboSumma, R.id.comboTara,R.id.comboItog,  /*R.id.comboX,*/R.id.comboSumma2,R.id.comboSkidka, R.id.comboSkidkaSum };
        // создаем адаптер
        sAdapter = new MySimpleAdapter(this, data, R.layout.combo_item, from, to);
        
        // определяем список и присваиваем ему адаптер
        //Button bSkidka=(Button)findViewById(R.id.comboSkidka);
 		//bSkidka.setText("CКИДКА");
        sAdapter.setViewBinder(new MyViewBinder());
        lvCombo.setAdapter(sAdapter);
    
    
    tvSum = (TextView) findViewById(R.id.tvSumSum);
    tvSum.setText("");
    
  
    tbXX = (Button) findViewById(R.id.btnXRasxod_x);
    tbXX.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
////16.05.18
        	for (int i=tranz.size()-1; i>=0; i--) 
        	{	
            			if (tranz.get(i).tagL!=-1) {
            			   but.get(tranz.get(i).tagB).ost=but.get(tranz.get(i).tagB).ost+tranz.get(i).kol;
            			   String l = "";
            			   int l1=0,l2=0;
            			   SpannableStringBuilder text; 
            			   StyleSpan style2; 
            			   AbsoluteSizeSpan s12;
            			   l = but.get(tranz.get(i).tagB).tmc_name+"\n"
            					   	  +(but.get(tranz.get(i).tagB).ed==1?MainActivity.round2(but.get(tranz.get(i).tagB).ost):MainActivity.round3(but.get(tranz.get(i).tagB).ost))
            					   	  +but.get(tranz.get(i).tagB).ted+" ЦЕНА "+MainActivity.round2(but.get(tranz.get(i).tagB).price)+but.get(tranz.get(i).tagB).string_keg;
            						        	l1=(but.get(tranz.get(i).tagB).tmc_name+"\n").length(); 
            						        	l2=l.length();
            						        	text = new SpannableStringBuilder(l); 
            						        	style2 = new StyleSpan(Typeface.BOLD); 
            						        	s12 = new AbsoluteSizeSpan(MainActivity.butNameS ,false);
            						        	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
            						        	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);	  
            						        	but.get(tranz.get(i).tagB).tb.setTextOff(text);
            						        	but.get(tranz.get(i).tagB).tb.setTextOn(text);
            			}
        	}
/////
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
						intent.putExtra("PrixodClose", /*tvIdPgr.getText()*/"1" );
						intent.putExtra("PrixodPgr", "0");
						intent.putExtra("PrixodPrice", "0" );
						intent.putExtra("PrixodPriceVendor", "0" );
						intent.putExtra("PrixodKol", "1");
						intent.putExtra("PrixodProd", "-1");
						intent.putExtra("PrixodEd", "1");
						intent.putExtra("PrixodPost","-1");
						intent.putExtra("PrixodPrim", "ДОБАВЛЕНИЕ ТОВАРА ИЗ МЕНЮ "+MainActivity.usr);
						intent.putExtra("PrixodDataIns", String.valueOf( MainActivity.getIntDataTime() ));
						if (Btovar!=-1) {
						intent.putExtra("PrixodPost", String.valueOf(but.get(Btovar).post));
						intent.putExtra("PrixodEd", String.valueOf( but.get(Btovar).ed ) );
						//Log.d("MyLog", "ed="+String.valueOf( but.get(Btovar).ed ));
	             	    
	             	    //intent.putExtra("PrixodId", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id")));
	                    intent.putExtra("PrixodProd", String.valueOf(but.get(Btovar).id_tmc));
	                    //intent.putExtra("PrixodKeg", String.valueOf(but.get(Btovar).keg));
	                    
	                    intent.putExtra("PrixodPriceVendor", String.valueOf(but.get(Btovar).price) );
	                   // Log.d("MyLog", "price="+String.valueOf( but.get(Btovar).price ));
	                    if (but.get(Btovar).ed==1)
	                    intent.putExtra("PrixodKol", "50");
	                    else 
	                    	intent.putExtra("PrixodKol", "1");
	                    
	                   // Log.d("MyLog", "post="+String.valueOf( but.get(Btovar).post ));
	                    
						}
    					      //Log.d("MyLog", "id="+id);
    					      startActivity(intent);
    					      setBut();
					} else setBut();
				}
			}); 
			getYes.show();
			//setBut();
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
							Cursor cc = MainActivity.db.getRawData ("select id_tmc, keg, kol, ed, id_post from ostat where kol<>0 and id_tmc="+but.get(Btovar).id_tmc+" and id_post="+but.get(Btovar).post+" and keg="+but.get(Btovar).keg+" and ed="+but.get(Btovar).ed , null);
							   if (cc.moveToFirst()) { 
							        do {countT=
							        		
							        		MainActivity.db.addRecRASXODcount
							        		(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getInt(cc.getColumnIndex("keg")), cc.getDouble(cc.getColumnIndex("kol")), 
							        				(but.get(Btovar).ost>0?/*cc.getDouble(cc.getColumnIndex("kol"))*/but.get(Btovar).ost:0),
							        				//tranz.size()>0?( (but.get(Btovar).ost>0?/*cc.getDouble(cc.getColumnIndex("kol"))*/but.get(Btovar).ost:0) ):0, 
							        				0,//(but.get(Btovar).ost<0?/*cc.getDouble(cc.getColumnIndex("kol"))*/-but.get(Btovar).ost:0), 
							        				0,0,0, cc.getInt(cc.getColumnIndex("ed")), but.get(Btovar).price,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "удаление по кнопке из меню продаж "+MainActivity.usr, MainActivity.getIntDataTime(), 5);
							        } while (cc.moveToNext());
							      };
							if (countT!=0) showMessage("Остаток обнулен", 1);

			        		setBut();
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
        	
        	flag_cash=1;
        	tvDialogN=R.id.btnOkRasxod_ok;
        	//showMessage(flag_cash+" ok "+tvDialogN, (byte)1);
        	showDialog(2);
        }
      });
    btnOkOk = (Button) findViewById(R.id.btnOkRasxod_okok);
    btnOkOk.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	flag_cash=0;
        	//getCheckDialog((byte)0);// Check();
        	tvDialogN=R.id.btnOkRasxod_okok;
        	//showMessage(flag_cash+" okok "+tvDialogN, (byte)1);
        	showDialog(2);
        }
      });
    
  }
   
   void setKeg() {
	   llbut.removeAllViewsInLayout();
	   svBut.removeAllViewsInLayout();

	    if (count_but_pgr<16)
	    {  // llbut = new TableLayout(this);
	    	//llbut.setOrientation(TableLayout.HORIZONTAL);
	    	llR.removeView(llbut);
	    	llR.removeView(svBut);	    	

	    	llR.addView(llbut,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1)); 
	    	}
	    else
	    {
	    	llR.removeView(llbut);
	    	llR.removeView(svBut);
	    	
	    	//svBut = new ScrollView(this);
	    	//hll_tara_button.setHorizontalScrollBarEnabled(true); //setScrollBarStyle(HorizontalScrollView. HORIZONTAL);
	    	llR.addView(svBut,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1));
	    	svBut.addView(llbut,
	    			new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT,1));
	    }
	   
	  } 
   
   void setPgr() {
	  
   	svPgr.removeAllViewsInLayout();

	   int i=1;
	   Cursor c = MainActivity.db.getRawData ("select distinct T._id as _id, T.name as name from tmc_pgr T left join tmc as TM on T._id=TM.pgr left join ostat as O on O.id_tmc=TM._id where O.kol<>0 and TM.vis=1 order by T.name",null); 
	   
	   if (c.moveToFirst()) { 
		   
	        do {
	        	        	
	        	Button btnPgr = new Button(this);
	        	
	        	btnPgr.setText(c.getString(c.getColumnIndex("name")));
	        	//Log.d("MyLog", "pgr="+btnPgr.getText());
	        	btnPgr.setTag(c.getInt(c.getColumnIndex("_id")));//+"-"+c.getString(c.getColumnIndex("name")));
	        	btnPgr.setTextSize(TypedValue.COMPLEX_UNIT_PX,MainActivity.butPgr);
	        	btnPgr.setBackground(getResources().getDrawable(R.drawable.edittexth_style));
	        	btnPgr.setMinimumHeight((int)(MainActivity.h / 7));
	        	//btnPgr.setMinimumWidth((int)(MainActivity.w / 8));
		    	
	        	btnPgr.setOnClickListener(new OnClickListener() {
	                public void onClick(View v) {
	        //Log.d("MyLog", String.valueOf((new Date()).getTime()) );
			String regexp = "^([0-9]+)";
			Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(v.getTag().toString());
			tvIdPgr.setText(matcher.find()?matcher.group():"-1");
			
			ArrayList<View> alv = MainActivity.getViewsByTag(svPgr);//(LinearLayout)findViewById(R.id.svPgr));
			for (int i=0; i<alv.size(); i++)
		    {
			//if (!(alv.get(i) instanceof SeekBar || alv.get(i) instanceof NumberPicker))
		    	((Button)alv.get(i)).setBackground(getResources().getDrawable(R.drawable.edittexth_style));
		    	((Button)alv.get(i)).setTextColor(clrNoCheck);
		    }
	        
			v.setBackground(getResources().getDrawable(R.drawable.btn_chek));
	    	((Button)v).setTextColor(clrCheck);

	                	setBut();
	                	//Log.d("MyLog", String.valueOf((new Date()).getTime()) );
	                }
	              });
	        	
	        	svPgr.addView(btnPgr, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
	        	
	        	i++;        	
	        } while (c.moveToNext());
	        
	      } 
	    //c.close();   
   }
   
  void setBut() {
	   //Log.d("MyLog", "start "+String.valueOf((new Date()).getTime()) );
	   llbut.removeAllViewsInLayout();
	   svBut.removeAllViewsInLayout();
	   //
	   
	   int count_but=0;
	   Cursor cc = MainActivity.db.getRawData 
//			   ("select S.id_tmc as _id, S.keg as keg, T.name as name, P.name as namep, TP.price as price, S.id_post as id_post, S.kol as kol, S.ed as ed, E.name as ted "
	//			+ " from ostat S left join tmc T on T._id=S.id_tmc left join tmc_price as TP on S.id_tmc=TP.id_tmc and S.id_post=TP.id_post and S.ed=TP.ed left join tmc_ed E on S.ed=E._id left join postav P on S.id_post=P._id where T.pgr="+tvIdPgr.getText()+" and T.vis=1 and S.kol!=0 order by T.pos, S.id_tmc",null);
/*
("select G._id as _id, min(O.keg) as keg, G.countkeg as countkeg, G.name as name, G.namep as namep, G.price as price, G.id_post as id_post, G.minkol as kol, round(G.minkol,2) as kol2, round(G.minkol,3) as kol3, G.ed as ed, G.ted as ted, G.pos as pos from (" +
		"select S.id_tmc as _id, T.pos as pos, T.name as name, P.name as namep, TP.price as price, S.id_post as id_post, S.ed as ed, E.name as ted " 
		//+",min(S.kol) over (partition by S.id_tmc, S.id_post, S.ed order by S.kol rows between unbounded preceding and current row) as minkol"
		+",count(S.kol) as countkeg, "
		//+ "min(S.kol) as minkol"
		+ "S.kol as minkol, "
		+ "min(S.data_ins) as mindat"
+ " from ostat S left join tmc T on T._id=S.id_tmc left join tmc_price as TP on S.id_tmc=TP.id_tmc and S.id_post=TP.id_post and S.ed=TP.ed left join tmc_ed E on S.ed=E._id left join postav P on S.id_post=P._id where T.pgr="+tvIdPgr.getText()+" and T.vis=1 and S.kol!=0 " +
" group by S.id_tmc, T.pos, T.name, P.name, TP.price, S.id_post, S.ed, E.name) as G left join ostat as O on G._id=O.id_tmc and G.id_post=O.id_post and G.ed=O.ed "
//+ "and G.minkol=O.kol " +
+ " and G.mindat=O.data_ins where O.kol!=0 " +
" group by G._id, G.countkeg, G.name, G.namep, G.price, G.id_post, G.minkol, G.ed, G.ted, G.pos order by G.pos, G._id",null);*/

					   ("select G._id as _id, min(O.keg) as keg, max(O.data_ins) data_ins, count(R.keg) as count_rasxod, G.countkeg as countkeg, G.name as name, G.namep as namep, G.price as price, G.id_post as id_post, G.minkol as kol, round(G.minkol,2) as kol2, round(G.minkol,3) as kol3, G.ed as ed, G.ted as ted, G.pos as pos from (" +
						"select S.id_tmc as _id, T.pos as pos, T.name as name, P.name as namep, TP.price as price, S.id_post as id_post, S.ed as ed, E.name as ted " 
						//+",min(S.kol) over (partition by S.id_tmc, S.id_post, S.ed order by S.kol rows between unbounded preceding and current row) as minkol"
						+",count(S.kol) as countkeg, "
						//+ "min(S.kol) as minkol"
						+ "S.kol as minkol, "
						+ "min(S.data_ins) as mindat"
				+ " from ostat S left join tmc T on T._id=S.id_tmc left join tmc_price as TP on S.id_tmc=TP.id_tmc and S.id_post=TP.id_post and S.ed=TP.ed left join tmc_ed E on S.ed=E._id left join postav P on S.id_post=P._id where T.pgr="+tvIdPgr.getText()+" and T.vis=1 and S.kol!=0 " +
				" group by S.id_tmc, T.pos, T.name, P.name, TP.price, S.id_post, S.ed, E.name) as G left join ostat as O on G._id=O.id_tmc and G.id_post=O.id_post and G.ed=O.ed and G.mindat=O.data_ins"
				+ " left join rasxod as R on R.id_tmc=O.id_tmc and R.id_post=O.id_post and R.ed=O.ed and R.keg=O.keg where O.kol!=0 " +
				" group by G._id, G.countkeg, G.name, G.namep, G.price, G.id_post, G.minkol, G.ed, G.ted, G.pos order by G.pos, G._id",null);

/*
	   ("select _id, keg, data_ins,count_rasxod, countkeg, name, namep, price, id_post, kol, kol2, kol3, ed, ted, pos from "
	   		+ "(select 1 as _id, 1 as keg, 1 data_ins, 0 as count_rasxod, 1 as countkeg, 'name1' as name, 'pgr1' as namep, 1.1 as price, 0 as id_post, 1.1 as kol, 1.1 as kol2, 1.1 as kol3, 1 as ed, 'l' as ted, 1 as pos  union all "
	   		+ "select 2 as _id, 2 as keg, 2 data_ins, 0 as count_rasxod, 1 as countkeg, 'name2' as name, 'pgr2' as namep, 1.2 as price, 0 as id_post, 1.2 as kol, 1.2 as kol2, 1.2 as kol3, 1 as ed, 'l' as ted, 1 as pos  union all "
	   		+ "select 3 as _id, 3 as keg, 3 data_ins, 0 as count_rasxod, 1 as countkeg, 'name3' as name, 'pgr3' as namep, 1.3 as price, 0 as id_post, 1.3 as kol, 1.3 as kol2, 1.3 as kol3, 1 as ed, 'l' as ted, 1 as pos  union all "
	   		+ "select 4 as _id, 4 as keg, 4 data_ins, 0 as count_rasxod, 1 as countkeg, 'name4' as name, 'pgr4' as namep, 1.4 as price, 0 as id_post, 1.4 as kol, 1.4 as kol2, 1.4 as kol3, 1 as ed, 'l' as ted, 1 as pos  union all "
	   		+ "select 5 as _id, 5 as keg, 5 data_ins, 0 as count_rasxod, 1 as countkeg, 'name5' as name, 'pgr5' as namep, 1.5 as price, 0 as id_post, 1.5 as kol, 1.5 as kol2, 1.5 as kol3, 1 as ed, 'l' as ted, 1 as pos  union all "
	   		+ "select 6 as _id, 6 as keg, 6 data_ins, 0 as count_rasxod, 1 as countkeg, 'name6' as name, 'pgr6' as namep, 1.6 as price, 0 as id_post, 1.6 as kol, 1.6 as kol2, 1.6 as kol3, 1 as ed, 'l' as ted, 1 as pos  union all "
	   		+ "select 7 as _id, 7 as keg, 7 data_ins, 0 as count_rasxod, 1 as countkeg, 'name7' as name, 'pgr7' as namep, 1.7 as price, 0 as id_post, 1.7 as kol, 1.7 as kol2, 1.7 as kol3, 1 as ed, 'l' as ted, 1 as pos  ) order by pos, _id",null);
*/	   
	    llR.removeView(llbut);
	    llR.removeView(svBut);
	    if (cc.moveToFirst()) { 
	        do {count_but++;
	        	//count_but=cc.getInt(cc.getColumnIndex("c"));
	        } while (cc.moveToNext());
	      };
	    if (count_but<16)
	    {  
	    	llR.addView(llbut,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1));  
	    }
	    else
	    {
	    	llR.addView(svBut,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1));
	    	svBut.addView(llbut,
	    			new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT,1));
	    	//llR_.setVisibility(LinearLayout.VISIBLE);
	    }    
	   
	   int ib;
	   
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
			   
		   ib=but.size();
		   }

       int w = (int)((4*MainActivity.h/5)/8);
	   
	   int tmp_row=1;
	   
	   //Log.d("MyLog", "start while "+String.valueOf((new Date()).getTime()) );
	   
	   if (cc.moveToFirst()) { 
		   
	        do {
	        	
	        	if (/*ib%3==CountTara%3*/tmp_row%3==1) {
	        
	        		row = new TableRow(this);
	        		//row = new LinearLayout(this);
	        		//row.setOrientation(LinearLayout.HORIZONTAL);
	                
	        		//int w = (int)(4*MainActivity.h/5)/8;
	                row.setMinimumHeight(w);
	                llbut.addView(row,
	                		new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, /*TableLayout.LayoutParams.MATCH_PARENT*/0,1));
	                //row.addView(llbut, params);
	        	}
	        	//	Log.d("MyLog", "start while " );
	        		        	but.add(new chB(
	        			ib, 							//indBT
	        			cc.getInt(cc.getColumnIndex("_id")), //id_tmc
	        			cc.getInt(cc.getColumnIndex("keg")), //keg
	        			//cc.getInt(cc.getColumnIndex("count_rasxod"))+
	        			cc.getString(cc.getColumnIndex("name"))+//(cc.getInt(cc.getColumnIndex("ed"))==1?(" "+MainActivity.getStringDataTime(cc.getInt(cc.getColumnIndex("data_ins"))).substring(0, 8) ):"") +
	        			//( (cc.getString(cc.getColumnIndex("namep")).equals("")||cc.getString(cc.getColumnIndex("namep")).equals("-нет-")||cc.getString(cc.getColumnIndex("namep")).equals("НЕТ") )?"":"\n"+cc.getString(cc.getColumnIndex("namep")) ) , //tmc_name
	        			( (cc.getString(cc.getColumnIndex("namep")).equals("")||cc.getString(cc.getColumnIndex("namep")).equals("-нет-"))?"":"\n"+cc.getString(cc.getColumnIndex("namep")) ) , //tmc_name
	        			"",
	        			0, 									//val
	        			cc.getDouble(cc.getColumnIndex("kol")), //ost 
	        			cc.getInt(cc.getColumnIndex("id_post")),//id_post
	        			0,									//kol
	        			cc.getDouble(cc.getColumnIndex("price")), //price 
	        			cc.getInt(cc.getColumnIndex("ed")), //ed
	        			cc.getString(cc.getColumnIndex("ted")), //ted
	        			cc.getInt(cc.getColumnIndex("count_rasxod")),//count_rasxod
	        			0,
	        			//0,//nedo
	        			//0,//izl
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
	        	String l = but.get(ib).tmc_name+"\n"+(but.get(ib).ed==1?cc.getDouble(cc.getColumnIndex("kol2")):/*MainActivity.round3(but.get(ib).ost)*/cc.getDouble(cc.getColumnIndex("kol3")))+but.get(ib).ted+" ЦЕНА "+MainActivity.round2(but.get(ib).price);
	        	
	        	if (cc.getInt(cc.getColumnIndex("countkeg"))>1) 
	        		{but.get(ib).string_keg="\n"+cc.getInt(cc.getColumnIndex("countkeg"))+" кеги"; l=l+but.get(ib).string_keg;} else but.get(ib).string_keg="";
	        	int l1=(but.get(ib).tmc_name+"\n").length(), l2=l.length();
	        	
	        	final SpannableStringBuilder text = new SpannableStringBuilder(l
	        			//but.get(ib).tmc_name+"\n"+MainActivity.round3(but.get(ib).ost)+but.get(ib).ted+" ЦЕНА "+MainActivity.round2(but.get(ib).price)
	        			); 
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
	        			//Log.d("MyLog", "butsize start "+but.size() );
	        			int tmp=0;
	        			if (isChecked)
	        			{
	        			//buttonView.setTextColor(clrCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.btn_chek));
	        			Btovar=Integer.parseInt(buttonView.getTag().toString());
	        			//showMessage("Btovar "+Btovar, (byte)1);
	        			buttonView.setTextColor(clrCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.btn_chek));
	        			//Btovar=Byte.parseByte(buttonView.getTag().toString());
	        			///!!!!
	        			
	        			for (int i=CountTara; i<but.size(); i++) {
	        		    	if ((i)!= Integer.parseInt(buttonView.getTag().toString())) 
	        		    	{but.get(i).tb.setChecked(false); but.get(i).tb.setTextColor(clrNoCheck); but.get(i).tb.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
	        		    	else tmp=but.get(i).ed ;
	        				}
	        			
	        			//tmp=but.get(Btovar).ed;
	        			//06.04!! if (Btara!=-1) fixV();// buttonView.setChecked(false);}
	        			//проверка остатка по кнопке на 0 или <0
	        			//tmp_minus=0 - ost>0; tmp_minus=2 - ost<0 && yes; tmp_minus=1 - ost<0 && no
	        			tmp_minus=0;
	        			//Log.d("MyLog", "tmp_minus=0 "+but.get(Btovar).keg+" "+String.valueOf((new Date()).getTime()) );
	        			
	        			if (but.get(Btovar).ost<0.001)
	        			{tmp_minus=2;
	        			if (Btara!=-1) {
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
	        		   	Btara=-1;}
	        			// проверяем есть ли другая кега
	        			long countT=0;
						Cursor cc = MainActivity.db.getRawData ("select count(*) c from ostat where kol>0.0001 and id_tmc="+but.get(Btovar).id_tmc+" and id_post="+but.get(Btovar).post+" and ed="+but.get(Btovar).ed+" and keg<>"+but.get(Btovar).keg, null);
					   if (cc.moveToFirst()) { 
					        do {countT=cc.getInt(cc.getColumnIndex("c"));
					        } while (cc.moveToNext());
					      };
					cc.close();
					//Log.d("MyLog", "countkeg="+countT+" keg="+but.get(Btovar).keg);
					//Log.d("MyLog", "new keg countT="+countT+" "+String.valueOf((new Date()).getTime()) );
					    if (countT>0/*=1*/) {
					    	//перейти на новую кегу
					    	DialogScreen getYes = new DialogScreen(RasxodActivity.this,RasxodActivity.this,-8).setDialogScreenListener(new DialogListener() {
	        					
		    					@Override
		    					public void OnSelectedKol(double k) {
		    						//1-да 0-нет -1-дисмис
		    						if (k==1) {
		    						long countT1=0;
		    						//Log.d("MyLog", "countT="+countT);
		    							Cursor cc = MainActivity.db.getRawData ("select id_tmc, keg, kol, ed, id_post from ostat where kol<>0 and id_tmc="+but.get(Btovar).id_tmc+" and id_post="+but.get(Btovar).post+" and keg="+but.get(Btovar).keg+" and ed="+but.get(Btovar).ed , null);
		 							   if (cc.moveToFirst()) { 
		 							        do {
		 							        	//if (tranz.size()>0) {
		 							        		/*for (int i=0; i<tranz.size(); i++) {
		 							        			if (tranz.get(i).id_tmc==cc.getInt(cc.getColumnIndex("id_tmc")) && tranz.get(i).id_post==cc.getInt(cc.getColumnIndex("id_post")) && tranz.get(i).keg==cc.getInt(cc.getColumnIndex("keg")) && tranz.get(i).ed==((byte)cc.getInt(cc.getColumnIndex("ed")))) 
		 							        				kol_in_chek=kol_in_chek+tranz.get(i).kol;
		 							        		}*/
		 							        	//}
		 							        	countT1=		 							        
		 							        MainActivity.db.addRecRASXODcount
		 							        (cc.getInt(cc.getColumnIndex("id_tmc")), cc.getInt(cc.getColumnIndex("keg")), cc.getDouble(cc.getColumnIndex("kol")), 
		 							        		0,//MainActivity.round3(cc.getDouble(cc.getColumnIndex("kol")))>0.001?cc.getDouble(cc.getColumnIndex("kol")):0,
		 							        		0,//cc.getDouble(cc.getColumnIndex("kol"))<0?-cc.getDouble(cc.getColumnIndex("kol")):0,
		 							        		0,0,/*(cc.getDouble(cc.getColumnIndex("kol"))>0?cc.getDouble(cc.getColumnIndex("kol")):0),
		 							        		 (cc.getDouble(cc.getColumnIndex("kol"))<0?-cc.getDouble(cc.getColumnIndex("kol")):0),*/ 
		 							        		0,cc.getInt(cc.getColumnIndex("ed")), but.get(Btovar).price, 0, cc.getInt(cc.getColumnIndex("id_post")), 0, MainActivity.usr+"новая кега - обнуление из меню продаж ", MainActivity.getIntDataTime(), 5);
		 							        showMessage("Кнопка с текущей кегой обнулена", 5);
		 							       Btovar=-1; Btara=-1;
		 							        //Log.d("MyLog", "countT="+countT+" "+String.valueOf(cc.getDouble(cc.getColumnIndex("kol")))-kol+" "+but.get(Btovar).keg+" keg="+cc.getInt(cc.getColumnIndex("keg")) );
		 							       /*
		 							       11-07 14:20:07.025: D/MyLog(30918): countT=0
		 							       11-07 14:20:07.056: D/MyLog(30918): countT=19 -9.99999
											*/
		 							        } while (cc.moveToNext());
		 							      };
		 							cc.close();
		 					    	setBut();
		    						} 
		    						//15.05.2018
									else tmp_minus=1;
		    						
		    					}
		    				}); 
		    				getYes.show();
///////////////////////dialog					    	
					    	/**/
					    } 
					    else
					    {
	        				DialogScreen getYes = new DialogScreen(RasxodActivity.this,RasxodActivity.this,-7).setDialogScreenListener(new DialogListener() {
	        					
	    					@Override
	    					public void OnSelectedKol(double k) {
	    						//tmp_minus=2;
	    						int upd=0;
	    						//Log.d("MyLog", "tmp_minus="+tmp_minus+" "+String.valueOf((new Date()).getTime()) );
	    						if (k==0) {
	    						
	    						//удаляем кнопку спрашиваем нужно ли оприходовать
	    						long countT2=0;
								Cursor cc = MainActivity.db.getRawData ("select id_tmc, keg, kol, ed, id_post from ostat where kol<0.0001 and id_tmc="+but.get(Btovar).id_tmc+" and id_post="+but.get(Btovar).post+" and keg="+but.get(Btovar).keg+" and ed="+but.get(Btovar).ed , null);
								//Log.d("MyLog", "countT="+countT+" "+but.get(Btovar).id_tmc+" "+but.get(Btovar).keg);   
								if (cc.moveToFirst()) { 
								        do {countT2=
								        		MainActivity.db.addRecRASXODcount
								        		(cc.getInt(cc.getColumnIndex("id_tmc")), cc.getInt(cc.getColumnIndex("keg")), cc.getDouble(cc.getColumnIndex("kol")), 
								        				 /*(cc.getDouble(cc.getColumnIndex("kol"))>0?cc.getDouble(cc.getColumnIndex("kol")):0),*/0, 
								        				 /*(cc.getDouble(cc.getColumnIndex("kol"))<0?-cc.getDouble(cc.getColumnIndex("kol")):0),*/0,
								        				 0,0,0,
								        				cc.getInt(cc.getColumnIndex("ed")), but.get(Btovar).price,0, cc.getInt(cc.getColumnIndex("id_post")), 0, "кпнопка с 0 кол-ом - обнуление из меню продаж "+MainActivity.usr, MainActivity.getIntDataTime(), 5);
								        
								        } while (cc.moveToNext());
								      };
								cc.close();
								if (countT2!=0) {showMessage("Остаток обнулен", 1);
								
								upd=1;
									//setBut();
	    			        		}
								
	    						} // else {tmp_minus=2;}
	    						
	    						///////////////////
	    						if (upd!=1)
	    						{ //да-продолжаем продавать с отрицательного
	    							tmp_minus=3; if (but.get(Btovar).ed!=1) {tbnKol.setTextColor(clrCheck);
		        					 tbnKol.setBackground(getResources().getDrawable(R.drawable.btn_chek));
		        					 tara(-2);
		        					 tvDialogN=R.id.tvOtherKol_;
		        					 makeDialog();}
								    } else {butAdd.callOnClick(); //setBut();
								    }
	    						//////////////////
	    						
	    					}
	    				}); 
	    				getYes.show();
	        			}
	        			}

	        			if (tmp_minus==0 || tmp_minus==3)
	        			{//Log.d("MyLog", "tmp_minus!=1 "+but.get(Btovar).keg+" "+String.valueOf((new Date()).getTime()) );
	        				buttonView.setTextColor(clrCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.btn_chek));
		        			//Btovar=Byte.parseByte(buttonView.getTag().toString());
		        			///!!!
	        				/*
	        				for (int i=CountTara; i<but.size(); i++) {
		        		    	if ((i)!= Integer.parseInt(buttonView.getTag().toString())) 
		        		    	{but.get(i).tb.setChecked(false); but.get(i).tb.setTextColor(clrNoCheck) ; but.get(i).tb.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
		        		    	else tmp=but.get(i).ed ;
		        				}
		        			*/	
	        				tmp=but.get(Btovar).ed;
	        				//Log.d("MyLog", "tmp= "+tmp+" btara="+Btara );
	        				//if (tmp!=1) tara(-2);
	        			if (Btara!=-1) 
	        				{if (!(tmp!=1 && Btara!=-2 )) fixV();
	        				//{if (tmp_minus!=2) fixV();}// buttonView.setChecked(false);}
	        				else //
	        					{//tara(-2);
	        					tbnKol.setTextColor(clrCheck);
	        					 tbnKol.setBackground(getResources().getDrawable(R.drawable.btn_chek));
	        					 tara(-2);
	        					 tvDialogN=R.id.tvOtherKol_;
	        					    //showDialog(1);
	        					 makeDialog();
	        					}	
	        				}
	        			else if (tmp!=1) 
	        			{tbnKol.setTextColor(clrCheck); 
   					 	tbnKol.setBackground(getResources().getDrawable(R.drawable.btn_chek));
   					 	tara(-2); 
   					 	tvDialogN=R.id.tvOtherKol_;
   					 	makeDialog();
   					    }
	        			
	        			} //else setBut();//{if (tmp_minus==0) setBut(); {Log.d("MyLog", "tmp_minus=0 setBut"+but.get(Btovar).keg+" "+String.valueOf((new Date()).getTime()) ); setBut();}}
	        			}
	        			else
	        				{buttonView.setTextColor(clrNoCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
	        			//Log.d("MyLog", "butsize end "+but.size() );
	        		}
	        	} );
	        	
	        	//llbut.addView(but.get(ib).tb/*,PB*/);
	        	
	        	row.addView(but.get(ib).tb,
	        			new TableRow/*LinearLayout*/.LayoutParams(0,TableRow/*LinearLayout*/.LayoutParams.MATCH_PARENT,1) );
	        	
	        	//Log.d("MyLog", "add button");
	        	ib++;
	        	tmp_row++;
	        	//Log.d("MyLog", "ib="+ib+" "+String.valueOf((new Date()).getTime()) );
	        } while (cc.moveToNext());
	        
	      } 
	    cc.close();
	    btnBack.setTag(Integer.parseInt(tvIdPgr.getText().toString()));
   }
  
  
  void fixV() {
	   float tmp=0;//, tmpkol=0;
	   String l = "";
	   String b = "";
	   int brak = 0;
	   int move = 0;
	   int l1=0,l2=0;
	   SpannableStringBuilder text; 
	   StyleSpan style2; 
	   AbsoluteSizeSpan s12;
	   
	   if (tbTara.isChecked()) 
  	{	m = new HashMap<String, Object>();
  	//if (tbBrak.isChecked()) {m.put("brak", 1); brak = 1;  b=b+" (БРАК)";} else m.put("brak", 0);
  	//if (tbMove.isChecked()) {m.put("move", 1); move = 1; b=b+" (ПЕРЕМЕЩЕНИЕ)";} else m.put("move", 0);
  		m.put("skidka_sum", 0);
  		m.put("skidka_but", 0);
  		m.put("skidka_but_text", "0%");
  		  m.put("name", but.get(Btovar).tmc_name+" (СВОЯ ТАРА)"+b);
  		//m.put("name", but.get(Btovar).tmc_name);
  		   m.put("price", MainActivity.round2(but.get(Btovar).price));
  		   if (Btara!=-2) 
  		   {m.put("kol", MainActivity.round3(but.get(Btara).val)+" "+but.get(Btovar).ted); 
  		   //tmpkol=MainActivity.round3(but.get(Btara).val);
  		   tmp=MainActivity.round2(but.get(Btovar).price*(but.get(Btara).val/*/0.5*/));
  		   m.put("summa", tmp/*/0.5*/);
  		m.put("summakp", tmp);
  		m.put("summa2", tmp);
  		m.put("skidka_sum_itog", tmp);
  		   m.put("itog", MainActivity.round3(but.get(Btara).val)+" * "+MainActivity.round2(but.get(Btovar).price)+" = ");} 
  		   else
  		   {m.put("kol", tvKol.getText().toString()+" "+but.get(Btovar).ted); 
  		   //tmpkol=MainActivity.StrToFloat(tvKol.getText().toString());
  		   tmp = MainActivity.round2(but.get(Btovar).price*MainActivity.StrToFloat(tvKol.getText().toString()));
  		   m.put("summa", tmp //((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
  				   );
  		m.put("summakp", tmp );
  		m.put("summa2", tmp //((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
				   );
  		m.put("skidka_sum_itog", tmp //((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
				   );
  		m.put("itog", tvKol.getText().toString()//((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString()))
  				+" * "+MainActivity.round2(but.get(Btovar).price)+" = ");}
  		      m.put("tara", "СВОЯ ТАРА");
  		      data.add(m);
  		      sAdapter.notifyDataSetChanged();
  		   if (Btara!=-2)	
  	{
  			//Log.d("MyLog", "Btovar1="+Btovar);
  			sumI=sumI+but.get(Btovar).price*(but.get(Btara).val/*/0.5f*/);
  		   	
  		   	but.get(Btovar).ost=but.get(Btovar).ost-but.get(Btara).val;
  		   	but.get(Btovar).kol_in_chek=but.get(Btovar).kol_in_chek+but.get(Btara).val;
  		   	
  	tranz.add(new tranDB(
  			brak,
  			move,
  			(data.size()-1),
  			(data.size()-1), 
  			Btovar, 
  			but.get(Btovar).id_tmc, 
  			but.get(Btovar).keg, 
  			but.get(Btara).val, 
  			but.get(Btovar).ed, 
  			but.get(Btovar).price, 
  			but.get(Btovar).post, 
  			0, "своя тара",
  			//0,
  			(but.get(Btovar).ost<0?((but.get(Btovar).ost+but.get(Btara).val)>0?-but.get(Btovar).ost:but.get(Btara).val):0) 
  			));
  	
  	}
  		   else
  		{//Log.d("MyLog", "Btovar2="+Btovar);
  			sumI=sumI+but.get(Btovar).price*MainActivity.StrToFloat(tvKol.getText().toString()) //((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
  	   	   			;
  	   	 but.get(Btovar).ost=but.get(Btovar).ost-MainActivity.StrToFloat(tvKol.getText().toString());
  	   	 but.get(Btovar).kol_in_chek=but.get(Btovar).kol_in_chek+MainActivity.StrToFloat(tvKol.getText().toString());
  	   	 
  			   tranz.add(new tranDB(
  				brak,
  				move,
  				(data.size()-1),
  				(data.size()-1), 
  				Btovar, 
  				but.get(Btovar).id_tmc, 
  				but.get(Btovar).keg,
  				MainActivity.StrToFloat(tvKol.getText().toString())//((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/) 
  				,but.get(Btovar).ed, 
  				but.get(Btovar).price, 
  				but.get(Btovar).post, 0, "своя тара",
  				//0,
  				(but.get(Btovar).ost<0?((but.get(Btovar).ost+MainActivity.StrToFloat(tvKol.getText().toString()))>0?-but.get(Btovar).ost:MainActivity.StrToFloat(tvKol.getText().toString())):0)
  				//(but.get(Btovar).ost<0?((but.get(Btovar).ost+MainActivity.StrToFloat(tvKol.getText().toString()))>0?-(but.get(Btovar).ost):MainActivity.StrToFloat(tvKol.getText().toString())):0) 
  				) );
  	   		
  		}
  		
  	}
  	else
  	{//Log.d("MyLog", "Btovar fixV1="+Btovar);
  	  m = new HashMap<String, Object>();
  	//if (tbBrak.isChecked()) {m.put("brak", 1); brak = 1;  b=b+" (БРАК)";} else m.put("brak", 0);
  	//if (tbMove.isChecked()) {m.put("move", 1); move = 1; b=b+" (ПЕРЕМЕЩЕНИЕ)";} else m.put("move", 0);
  	  	m.put("skidka_sum", 0);
		m.put("skidka_but", 0);
		m.put("skidka_but_text", "0%");
  	  	//m.put("name", but.get(Btovar).tmc_name);
  	 m.put("price", MainActivity.round2(but.get(Btovar).price));
  	 if (Btara!=-2) {m.put("kol", MainActivity.round3(but.get(Btara).val)+" "+but.get(Btovar).ted);
  	 //tmpkol=MainActivity.round3(but.get(Btara).val);
		      m.put("tara", " + ТАРА "+but.get(Btara).tmc_name);
		      m.put("name", but.get(Btovar).tmc_name+" (ТАРА "+but.get(Btara).tmc_name+")"+b);
		      tmp = MainActivity.round2(but.get(Btovar).price*(but.get(Btara).val/*/0.5*/)+but.get(Btara).price);
		      //Log.d("MyLog", "price="+but.get(Btara).price);
		      m.put("summa", tmp);
		      m.put("summakp", tmp);
		      m.put("summa2", tmp);
		      m.put("skidka_sum_itog", tmp);
		      m.put("itog", MainActivity.round3(but.get(Btara).val)+" * "+MainActivity.round2(but.get(Btovar).price)+" + "+MainActivity.round2(but.get(Btara).price)+" = ");}
  	 else
  	{m.put("kol", tvKol.getText()+" "+but.get(Btovar).ted);
  	//tmpkol=MainActivity.StrToFloat(tvKol.getText().toString());
   m.put("tara", /*" + ПАКЕТ "*/"");
   m.put("name", but.get(Btovar).tmc_name+b);
   tmp = MainActivity.round2(but.get(Btovar).price*MainActivity.StrToFloat(tvKol.getText().toString()));
   m.put("summakp", tmp);
   m.put("summa", tmp);
   m.put("summa2", tmp);
   m.put("skidka_sum_itog", tmp);
   m.put("itog", tvKol.getText().toString()//((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString()))
   		+" * "+MainActivity.round2(but.get(Btovar).price)+" = ");}
		      data.add(m);
		      
  		
		      sAdapter.notifyDataSetChanged();
		      if (Btara!=-2) {	
		    	 // Log.d("MyLog", "tara1="+(byte)(data.size()-1));
		    	 // Log.d("MyLog", "Btovar3="+Btovar);
		    	  but.get(Btovar).ost=but.get(Btovar).ost-but.get(Btara).val;
		    	   	but.get(Btovar).kol_in_chek=but.get(Btovar).kol_in_chek+but.get(Btara).val;
		    	   	but.get(Btara).ost=but.get(Btara).ost-but.get(Btara).kol;
		    	   	
		    	   	sumI=sumI+(but.get(Btovar).price*(but.get(Btara).val/*/0.5f*/)+but.get(Btara).price);
		    	   	
	tranz.add(new tranDB(
			brak,
			move,
			(data.size()-1),
			(data.size()-1), 
			Btovar, 
			but.get(Btovar).id_tmc, 
			but.get(Btovar).keg, 
			but.get(Btara).val,
			but.get(Btovar).ed, 
			but.get(Btovar).price, 
			but.get(Btovar).post, 0, "с тарой",//0,
			(but.get(Btovar).ost<0?((but.get(Btovar).ost+but.get(Btara).val)>0?-but.get(Btovar).ost:but.get(Btara).val):0)
			));	
	//Log.d("MyLog", "Btovar4="+Btovar+" "+Btara);
  	tranz.add(new tranDB(
  			brak,
  			move,
  			(data.size()-1),
  			 -1 /*если тара, то позиция в чеке -1*/, 
  			Btara,
  			but.get(Btara).id_tmc, 
  			but.get(Btara).keg, 
  			but.get(Btara).kol, 
  			but.get(Btara).ed, 
  			but.get(Btara).price, 
  			but.get(Btara).post, 
  			0, "тара",//0,
  			(but.get(Btara).ost<0?((but.get(Btara).ost+but.get(Btara).kol)>0?-but.get(Btara).ost:but.get(Btara).kol):0)
  			));

		      }
		      else
			      
		    	   	{//Log.d("MyLog", "Btovar5="+Btovar);
		    	  but.get(Btovar).ost=but.get(Btovar).ost-MainActivity.StrToFloat(tvKol.getText().toString());     
		    	  sumI=sumI+(but.get(Btovar).price*MainActivity.StrToFloat(tvKol.getText().toString())//((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)
		    	    		   +0);
		    	  tranz.add(new tranDB(
		    			  brak,
		    			  move,
		    			  (data.size()-1),
		    			  (data.size()-1), 
		    			  Btovar,
		    			  but.get(Btovar).id_tmc,
		    			  but.get(Btovar).keg, 
		    			  MainActivity.StrToFloat(tvKol.getText().toString()),
		    			  but.get(Btovar).ed, 
		    			  but.get(Btovar).price, 
		    			  but.get(Btovar).post, 0, "пакет",//0,
		    			  (but.get(Btovar).ost<0?((but.get(Btovar).ost+MainActivity.StrToFloat(tvKol.getText().toString()))>0?-but.get(Btovar).ost:MainActivity.StrToFloat(tvKol.getText().toString())):0)
		    			  ));	
		    	      // tranz.add(new tranDB((byte)(data.size()-1), Btara,but.get(Btara).id_tmc, but.get(Btara).kol, but.get(Btara).ed, but.get(Btara).price, but.get(Btara).post, 0, "tara"));
		    	  }
  	}

	   l = but.get(Btovar).tmc_name+"\n"
			   	  +(but.get(Btovar).ed==1?MainActivity.round2(but.get(Btovar).ost):MainActivity.round3(but.get(Btovar).ost))
			   	  +but.get(Btovar).ted+" ЦЕНА "+MainActivity.round2(but.get(Btovar).price)+but.get(Btovar).string_keg;
				        	l1=(but.get(Btovar).tmc_name+"\n").length(); 
				        	l2=l.length();
				        	text = new SpannableStringBuilder(l); 
				        	style2 = new StyleSpan(Typeface.BOLD); 
				        	s12 = new AbsoluteSizeSpan(MainActivity.butNameS ,false);
				        	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
				        	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);	  
				        	but.get(Btovar).tb.setTextOff(text);
				        	but.get(Btovar).tb.setTextOn(text);
				        	
  	tvSum.setText(String.valueOf(MainActivity.round2(sumI)));
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

   void getCheckDialog(int cash) /*0-nalichnie, 1-karta*/ {
	   
	   if (data.size()!=0)
   	{
		   // int klient=0; if (!=0) 
    long cou=0; cou=MainActivity.db.addRecKLIENTcount(MainActivity.num_id, "чек№ "+MainActivity.num_id, MainActivity.StrToFloat(tvSum.getText().toString()), 
    		MainActivity.round2(MainActivity.StrToFloat((etSkidka.getText().toString()))+MainActivity.StrToFloat((etSkidkaPerSum.getText().toString()))), "чек "+(cash==0?"наличными":"безналичный") , MainActivity.getIntDataTime(),(int) MainActivity.StrToFloat(tvIdKlient.getText().toString()) , 0);
    
    //это общая скидка, если она есть (сeйчас она =0 не заполняется и invisible)
    /*if (MainActivity.StrToFloat(etSkidka.getText().toString())!=0)
   		MainActivity.db.addRecRASXOD(0, -MainActivity.StrToFloat(etSkidka.getText().toString()), (byte)4, 0, 0, 0, (int)cou, "СКИДКА "+etSkidka.getText().toString()+"ГРН", MainActivity.getIntDataTime(), (byte)0);
общая скидка по чеку в klient.skidka делится на количество позиций в чеке и прибавляется к rasxod.skidka */
    //Log.d("MyLog", "0tranz="+tranz.size());
    double sk=0, skid=0;
    if (MainActivity.StrToFloat(etSkidka.getText().toString())!=0) sk=/*MainActivity.round(*/MainActivity.StrToFloat(etSkidka.getText().toString())/tranz.size()/*,2)*/;
    
    for (int i=0; i<tranz.size(); i++) {
    	
    	skid=0;	
    if (tranz.get(i).tagL!=-1) skid=MainActivity.StrToFloat( data.get(tranz.get(i).tag).get("skidka_sum").toString() );
    
    Cursor cc = MainActivity.db.getRawData ("select id_tmc, keg, kol, ed, id_post from ostat where kol=0 and id_tmc="+tranz.get(i).id_tmc+" and id_post="+tranz.get(i).id_post+" and keg="+tranz.get(i).keg+" and ed="+tranz.get(i).ed , null);
	   if (cc.moveToFirst()) { 
	        do {
	        	MainActivity.db.addRecRASXODcount(tranz.get(i).id_tmc, tranz.get(i).keg, -tranz.get(i).kol, /*(but.get(tranz.get(i).tagB).ost>0?but.get(tranz.get(i).tagB).ost:0)*/0, /*(but.get(tranz.get(i).tagB).ost<0?-but.get(tranz.get(i).tagB).ost:0)*/0 ,0,0,cash, tranz.get(i).ed, but.get(tranz.get(i).tagB).price,0, tranz.get(i).id_post, 0, "новая кега - товар в чеке "+MainActivity.usr, MainActivity.getIntDataTime(), 5);
	   			//MainActivity.db.addRecPRIXOD(but.get(Btovar).id_tmc, but.get(Btovar).keg, but.get(tranz.get(i).tagB).kol_in_chek,0,0, (byte)tranz.get(i).ed, tranz.get(i).price, tranz.get(i).price, tranz.get(i).id_post, "0 остатка в кеге "+but.get(tranz.get(i).tagB).ost+" чек "+cou, MainActivity.getIntDataTime(),(byte)0);
	   			//showMessage("kol_tranz "+tranz.get(i).kol, (byte)1);
	        	}
	        while (cc.moveToNext());
	        }
    
    if (but.get(tranz.get(i).tagB).ost==0 && but.get(tranz.get(i).tagB).ed==1)//если кол-во =0 то добавляем остаток по приходу
	   	{
	   		//MainActivity.db.addRecPRIXOD(tranz.get(i).id_tmc, tranz.get(i).keg, -but.get(tranz.get(i).tagB).ost+0.00001,0,0, (byte)tranz.get(i).ed, tranz.get(i).price, tranz.get(i).price, tranz.get(i).id_post, "автоувеличение+1 остатка "+but.get(tranz.get(i).tagB).ost+" чек "+cou, MainActivity.getIntDataTime(),(byte)0);
	   		MainActivity.db.addRecRASXODcount(tranz.get(i).id_tmc, tranz.get(i).keg, but.get(tranz.get(i).tagB).ost-0.00001, 0,0,0,0,cash, tranz.get(i).ed, 0,0, tranz.get(i).id_post, 0, MainActivity.usr+" новая кега - товар в чеке ", /*MainActivity.getIntDataTime()*/1, 5);
	   		but.get(tranz.get(i).tagB).ost=0.00001;
	   		tranz.get(i).prim="из отрицательного остатка! "+tranz.get(i).prim;
	   	}
    
    //long tmp = MainActivity.db.addRecRASXODcount(tranz.get(i).id_tmc,
   		//	tranz.get(i).kol, tranz.get(i).ed, tranz.get(i).price, tranz.get(i).id_post, /*tranz.get(i).id_klient*/(int)cou, tranz.get(i).prim, MainActivity.getIntDataTime(), (byte)0);	
    	MainActivity.db.addRecRASXOD(tranz.get(i).id_tmc,
    			tranz.get(i).keg,
       			tranz.get(i).kol,/*(but.get(tranz.get(i).tagB).ost<0?tranz.get(i).kol:0)*//*tranz.get(i).nedo*/
       			0, 
       			/*(tranz.get(i).ed==1)?*/tranz.get(i).izl, /*(tranz.get(i).brak==1?tranz.get(i).kol:0),(tranz.get(i).move==1?tranz.get(i).kol:0)*/
       			0,
       			0, 
       			cash, 
       			tranz.get(i).ed, 
       			tranz.get(i).price, 
       			sk+skid, 
       			tranz.get(i).id_post, 
       			/*tranz.get(i).id_klient*/cou, 
       			MainActivity.usr+" чек№ "+MainActivity.num_id+" на сумму "+MainActivity.StrToFloat(tvSum.getText().toString())+" остаток:"+MainActivity.round3(but.get(tranz.get(i).tagB).ost)+" "+tranz.get(i).prim, 
       			MainActivity.getIntDataTime()
       			, 0);	
       	
  	//Log.d("MyLog", "data="+data.get(tranz.get(i).tagL).get("skidka_sum").toString());
   	//эта жесть создает скидку по позиции если она есть в чеке (в поле rasxod.ok пишу _id расхода, по которой скидка)
   	//Log.d("MyLog", tranz.get(i).tagL+" ");
   	//if (tranz.get(i).tagL!=-1 /*&& MainActivity.StrToFloat( data.get(tranz.get(i).tagL).get("skidka_sum").toString() )!=0*/ )
   	// if (MainActivity.StrToFloat( data.get(tranz.get(i).tag).get("skidka_sum").toString() )!=0)
   	//	MainActivity.db.addRecRASXOD(0, -MainActivity.StrToFloat( data.get(tranz.get(i).tag).get("skidka_sum").toString() ), (byte)4, 0, 0, (int)cou, "СКИДКА ПО ПОЗИЦИИ "+tmp+":"+data.get(tranz.get(i).tag).get("skidka_sum").toString()+"ГРН", MainActivity.getIntDataTime(),(int)tmp);

   	if (tranz.get(i).tagB>CountTara-1) {
   		/*16.05.18
   		int l1=(but.get(tranz.get(i).tagB).tmc_name+"\n").length(), 
   			l2=(but.get(tranz.get(i).tagB).tmc_name+"\n"+(but.get(tranz.get(i).tagB).ed==1?MainActivity.roundd( but.get(tranz.get(i).tagB).ost,2):MainActivity.roundd( but.get(tranz.get(i).tagB).ost,3))+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+MainActivity.round2(but.get(tranz.get(i).tagB).price)
   				+but.get(tranz.get(i).tagB).string_keg).length();
       	final SpannableStringBuilder text = new SpannableStringBuilder(but.get(tranz.get(i).tagB).tmc_name+"\n"+(but.get(tranz.get(i).tagB).ed==1?MainActivity.roundd( but.get(tranz.get(i).tagB).ost,2):MainActivity.roundd( but.get(tranz.get(i).tagB).ost,3))+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+MainActivity.round2(but.get(tranz.get(i).tagB).price)
       			+but.get(tranz.get(i).tagB).string_keg); 
       	//final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(255, 0, 0)); 
       	final StyleSpan style2 = new StyleSpan(Typeface.BOLD); 
       	final AbsoluteSizeSpan s12 = new AbsoluteSizeSpan(MainActivity.butNameS,false);
       	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
       	//text.setSpan(style, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
       	//textView.setTypeface(null,Typeface.BOLD);
       	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
       	*/
       	if (but.get(tranz.get(i).tagB).count_rasxod==0 && but.get(tranz.get(i).tagB).ed==1) {
       		 int fla=-1;
       		 Cursor cur = MainActivity.db.getRawData (
       	  			"select count(*) c from ostat where id_tmc="+but.get(tranz.get(i).tagB).id_tmc+" and keg="+but.get(tranz.get(i).tagB).keg+" and id_post="+but.get(tranz.get(i).tagB).post+" and ed="+but.get(tranz.get(i).tagB).ed+" and data_upd is null"
       	  			, null);	
       	          	 if (cur.moveToFirst())  
       	       	        do {
       	       	        	fla= cur.getInt(cur.getColumnIndex("c"));
       	       	      //showMessage("1_fla="+fla, (byte)0);
       	       	        } while (cur.moveToNext());
       	          	        cur.close();	        
       		if (fla>0)
       		MainActivity.db.updOstatKeg(but.get(tranz.get(i).tagB).id_tmc, but.get(tranz.get(i).tagB).post, but.get(tranz.get(i).tagB).keg, but.get(tranz.get(i).tagB).ed);
       		//showMessage("fla="+fla, (byte)0);
       		but.get(tranz.get(i).tagB).count_rasxod=1;
       	}
   	//but.get(tranz.get(i).tagB).tb.setTextOff(text);//(but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price);
   	//but.get(tranz.get(i).tagB).tb.setTextOn(text);//(but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ЦЕНА "+but.get(tranz.get(i).tagB).price);
   	but.get(tranz.get(i).tagB).tb.setChecked(but.get(tranz.get(i).tagB).tb.isChecked());
   	}
   	
   	}
   	
   	//tvCombo.setText("");

   	//Toast.makeText(RasxodActivity.this, "ЧЕК № "+cou+" НА СУММУ:"+tvSum.getText().toString(), Toast.LENGTH_LONG).show();
   	String sChek="";
   	for (int i=0; i<data.size(); i++) 
   	{sChek=sChek+"\n"+data.get(i).get("name").toString()+" "+data.get(i).get("kol").toString()+" "+data.get(i).get("price").toString()+" "+data.get(i).get("tara").toString(); }	
   	
   	//showMessage("ЧЕК № "+cou+" НА СУММУ:"+tvSum.getText().toString()+"\n"+sChek, (byte)1);
   	MainActivity.num_id++;	
   	data.clear();
    etSkidkaPerSum.setText("");
	etSkidkaPer.setText(""); 
	etSkidka.setText("0"); 
	tvIdKlient.setText("0"); 
	etNal.setText("0"); 
	 //tvSdacha.setText("0");
   	tvSum.setText("");sumI=0;tranz.clear();
   	tbTara.setChecked(false);
   	//tbBrak.setChecked(false);
   	//tbMove.setChecked(false);
   	sAdapter.notifyDataSetChanged();
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
		     		
		      	if (valsum!=0) etSkidkaPerSum.setText(String.valueOf(MainActivity.round2(valsum))); else etSkidkaPerSum.setText("");
		      	
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
	      		MainActivity.round2(MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString())+MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())*/)
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
	        		MainActivity.round2( MainActivity.StrToFloat(data.get(combo_pos).get("summa").toString() ) * MainActivity.StrToFloat(data.get(combo_pos).get("skidka_but").toString())/100 )
	        		);
	        		data.get(combo_pos).put("skidka_sum_itog", 
	    	        	MainActivity.round2(MainActivity.StrToFloat(data.get(combo_pos).get("summa2").toString() )-MainActivity.StrToFloat(data.get(combo_pos).get("skidka_sum").toString() ))
	    	        		);

		  // float valsum=0;
	      for (int i=0; i<data.size(); i++) 
	     		valsum=valsum+MainActivity.StrToFloat(data.get(i).get("skidka_sum").toString() ) ;
	     		
	      	if (valsum!=0) etSkidkaPerSum.setText(String.valueOf(MainActivity.round2(valsum))); else etSkidkaPerSum.setText("");
	      	tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
	    			 ));
	      	tvSdacha.setText( String.valueOf (
	      		MainActivity.round2(MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + valsum*/) 
	      			) );
	      	 
		   sAdapter.notifyDataSetChanged();
		   tvDialogN=0;
	     break;
	   case R.id.tvOtherKol_:
        	 tvKol.setText(/*tvDKol.getText()*/String.valueOf(k));
        	 //Log.d("MyLog", "Btovar="+Btovar+" tmp_minus="+tmp_minus);
        	 if (Btovar!=-1 && tmp_minus!=2) fixV();
		   tvDialogN=0;
	     break;
	   case R.id.etCheckNal:
        	 etNal.setText(/*tvDKol.getText()*/String.valueOf(k));
        	 //tvSdacha.setText(String.valueOf (MainActivity.StrToFloat(etNal.getText().toString())-(MainActivity.StrToFloat(tvDItogo.getText().toString())-MainActivity.StrToFloat(etSkidka.getText().toString())) ) );
        	 //tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
        		//	 ));
        	 tvSdacha.setText(String.valueOf ( 
        			MainActivity.round2( MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())*/)
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
      			MainActivity.round2(	MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())*/)
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
     		valsum=valsum+MainActivity.round2( MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) * val/100);
     		data.get(i).put("skidka_sum", 
     		 MainActivity.round2( MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) * val/100)
     				);
     		data.get(i).put("skidka_sum_itog", 
             	MainActivity.round2(MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) -MainActivity.StrToFloat(data.get(i).get("skidka_sum").toString() ))
             				);
     		}
     	}
      	 
      	 if (valsum!=0) etSkidkaPerSum.setText(String.valueOf(MainActivity.round2(valsum))); else etSkidkaPerSum.setText("");
      	tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
   			 ));
      	 tvSdacha.setText(String.valueOf ( 
      		MainActivity.round2(	MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + valsum*/)
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
     //if (id == 2 || id==3) {
	   
         AlertDialog.Builder adb = new AlertDialog.Builder(this);
         //showMessage("create "+tvDialogN, (byte)1);
         if (tvDialogN==R.id.btnOkRasxod_okok)
         {adb.setTitle("ЧЕК НАЛИЧНЫЙ");
         //showMessage("R.id.btnOkRasxod_okok "+R.id.btnOkRasxod_okok, (byte)1);
         }else
         {adb.setTitle("ЧЕК БЕЗНАЛИЧНЫЙ");
         //showMessage("R.id.btnOkRasxod_ok "+R.id.btnOkRasxod_ok, (byte)1);
         }
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
         if (tvDialogN==R.id.btnOkRasxod_ok) 
       	{
       		((LinearLayout) Dview.findViewById(R.id.llCheckSdacha)).setVisibility(LinearLayout.GONE);
       		((LinearLayout) Dview.findViewById(R.id.llCheckNal)).setVisibility(LinearLayout.GONE);
       	}
         else
        	 {((LinearLayout) Dview.findViewById(R.id.llCheckSdacha)).setVisibility(LinearLayout.VISIBLE);
        		((LinearLayout) Dview.findViewById(R.id.llCheckNal)).setVisibility(LinearLayout.VISIBLE);
        		};
      	//etNal.clearFocus();
       	etSkidkaPer = (TextView) Dview.findViewById(R.id.etCheckSkidkaPer);
      	tvIdKlient = (TextView) Dview.findViewById(R.id.tvIdKlientCheck);
      	tvDItogo = (TextView) Dview.findViewById(R.id.etItogSummaD);
      	etCheckCheck = (TextView) Dview.findViewById(R.id.etCheckCheck);
      	etCheckCheck.setText(String.valueOf(MainActivity.num_id));
      	//etSdacha = (TextView) Dview.findViewById(R.id.etCheckSdacha);
        tvSdacha = (TextView) Dview.findViewById(R.id.tvCheckSdacha);
      	etNal = (TextView) Dview.findViewById(R.id.etCheckNal);
      	tvNal = (TextView) Dview.findViewById(R.id.tvCheckNal);
      	
      	//else
      	//{((LinearLayout) Dview.findViewById(R.id.llCheckSdacha)).setVisibility(LinearLayout.VISIBLE );
  		//((LinearLayout) Dview.findViewById(R.id.llCheckNal)).setVisibility(LinearLayout.VISIBLE);
  		//}
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
            		val=val+MainActivity.round2(MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) * MainActivity.StrToFloat(data.get(i).get("skidka_but").toString())/100);
            		data.get(i).put("skidka_sum", 
            		MainActivity.round2( MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) * MainActivity.StrToFloat(data.get(i).get("skidka_but").toString())/100 )
            				);
            		data.get(i).put("skidka_sum_itog", 
                    		MainActivity.round2(MainActivity.StrToFloat(data.get(i).get("summa2").toString() ) -MainActivity.StrToFloat(data.get(i).get("skidka_sum").toString() ))
                    				);
            		}
            	}
            	if (val!=0) etSkidkaPerSum.setText(String.valueOf(MainActivity.round2(val))); else etSkidkaPerSum.setText(String.valueOf(""));
            	
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
            	//etCheckCheck.setText("");
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

      	lvComboD = (ListView) Dview.findViewById(R.id.lvComboDia);

             lvComboD.setAdapter(sAdapter);

         adb.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
            	 spKlient.setTag(0);
             	tvIdKlient.setText("0");
             	etSkidkaPer.setText("");
             	etSkidkaPerSum.setText("");
             	etCheckCheck.setText("");
            	 tvDialogN=0;
             }
         })
         .setPositiveButton("ЗАКРЫТЬ ЧЕК", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
          	  // Log.d("MyLog", "Dismiss");
            		 
            	 getCheckDialog(flag_cash); 
            	 spKlient.setTag(0);
             	tvIdKlient.setText("0");
             	etSkidkaPer.setText("");
             	etSkidkaPerSum.setText("");
             	etCheckCheck.setText("");
             	tvDialogN=0;
            	 //sAdapter.notifyDataSetChanged();
             }
         })
         /*.setOnDismissListener(new OnDismissListener() {
      		
      		@Override
      		public void onDismiss(DialogInterface arg0) {
      			dialogNumCancel(tvDialogN);
      			spKlient.setTag(0);
             	tvIdKlient.setText("0");
             	etSkidkaPer.setText("");
             	etSkidkaPerSum.setText("");
             	etCheckCheck.setText("");
             	tvDialogN=0;
      		}
      	})*/
         .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					dialogNumCancel(tvDialogN);
	      			spKlient.setTag(0);
	             	tvIdKlient.setText("0");
	             	etSkidkaPer.setText("");
	             	etSkidkaPerSum.setText("");
	             	etCheckCheck.setText("");
	             	tvDialogN=0;					
				}
			});
         
        
         dialogg = adb.create();

         return dialogg;
   }
   
 
   
   @Override
   protected void onPrepareDialog(int id, Dialog dialog) {
     super.onPrepareDialog(id, dialog);

     if (tvDialogN==R.id.btnOkRasxod_okok)
     { dialog.setTitle("ЧЕК НАЛИЧНЫЙ");
     //showMessage("R.id.btnOkRasxod_okok "+R.id.btnOkRasxod_okok, (byte)1);
     }else
     {dialog.setTitle("ЧЕК БЕЗНАЛИЧНЫЙ");
     //showMessage("R.id.btnOkRasxod_ok "+R.id.btnOkRasxod_ok, (byte)1);
     };
     if (tvDialogN==R.id.btnOkRasxod_ok) 
    	{
    		((LinearLayout) Dview.findViewById(R.id.llCheckSdacha)).setVisibility(LinearLayout.GONE);
    		((LinearLayout) Dview.findViewById(R.id.llCheckNal)).setVisibility(LinearLayout.GONE);
    		
    	}
     else
	 {((LinearLayout) Dview.findViewById(R.id.llCheckSdacha)).setVisibility(LinearLayout.VISIBLE);
		((LinearLayout) Dview.findViewById(R.id.llCheckNal)).setVisibility(LinearLayout.VISIBLE);
		};;
    	 etCheckCheck.setText(String.valueOf(MainActivity.num_id));
    	 //tvDItogo.setText(tvSum.getText());  
    	 tvDItogo.setText( String.valueOf( MainActivity.StrToFloat(tvSum.getText().toString()) - MainActivity.StrToFloat(etSkidka.getText().toString()) - MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())
    			 ));
	      	tvSdacha.setText( String.valueOf (
	      		MainActivity.round2(MainActivity.StrToFloat(etNal.getText().toString())-MainActivity.StrToFloat(tvDItogo.getText().toString())/*+MainActivity.StrToFloat(etSkidka.getText().toString()) + MainActivity.StrToFloat(etSkidkaPerSum.getText().toString())*//*vals*/)
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

   } 
   
  @Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked)
		{buttonView.setTextColor(clrCheck);
		buttonView.setBackground(getResources().getDrawable(R.drawable.btn_chek));
		 //Log.d("MyLog", "tag="+Byte.parseByte(buttonView.getTag().toString()));
		tara(Integer.parseInt(buttonView.getTag().toString()));
		if (Btovar!=-1) fixV(); 
		}
		else
			{buttonView.setTextColor(clrNoCheck); buttonView.setBackground(getResources().getDrawable(R.drawable.edittexth_style));}
	}
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    MainActivity.seek=sbar.getProgress();
    //c.close();
  }

  @Override
  protected void onStart() {
	  super.onStart();
  }
  
  protected void onResume() {
	  super.onResume();
	  setBut();
  }
  
  @Override
  protected void onRestart() {
    super.onRestart();
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
	      //if (v.getId() == R.id.comboSumma) {
	        //float i = Float.parseFloat(text);
	        //if (i < 0) v.setTextColor(Color.RED); else
	          //if (i > 100) v.setTextColor(Color.RED);
	      //}
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
                final TextView tSumKP=(TextView)v.findViewById(R.id.comboSummaKP);   
                tSumKP.setVisibility(TextView.GONE);
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
            
          TextView tvComboName = (TextView) v.findViewById(R.id.comboName);
            tvComboName.setTextSize(TypedValue.COMPLEX_UNIT_PX , MainActivity.tabI);
            Button b=(Button)v.findViewById(R.id.comboX);
             b.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                	
                	//Toast.makeText(RasxodActivity.this,dataSet.get("summa").toString()+" position "+pos,Toast.LENGTH_SHORT).show();
                	sumI=sumI-Float.parseFloat(dataSet.get("summa").toString());
                	tvSum.setText(String.valueOf(MainActivity.round2(sumI)));
                	//if (tvDialogN!=0) tvDItogo.setText(String.valueOf(sumI));
                	//for (int i=0; i<tranz.size(); i++)
                	for (int i=tranz.size()-1; i>=0; i--) 
                	//{
                		if (tranz.get(i).tag==pos) 
                			{
                			//showMessage("kol_tranz "+but.get(tranz.get(i).tagB).ost, (byte)1);
                			but.get(tranz.get(i).tagB).kol_in_chek=but.get(tranz.get(i).tagB).kol_in_chek-tranz.get(i).kol;
                			if (tranz.get(i).tagL!=-1) {
                			   but.get(tranz.get(i).tagB).ost=but.get(tranz.get(i).tagB).ost+tranz.get(i).kol;
                			   //showMessage("tagB "+tranz.get(i).tagB, (byte)1);
                			   String l = "";
                			   int l1=0,l2=0;
                			   SpannableStringBuilder text; 
                			   StyleSpan style2; 
                			   AbsoluteSizeSpan s12;
                			   l = but.get(tranz.get(i).tagB).tmc_name+"\n"
                					   	  +(but.get(tranz.get(i).tagB).ed==1?MainActivity.round2(but.get(tranz.get(i).tagB).ost):MainActivity.round3(but.get(tranz.get(i).tagB).ost))
                					   	  +but.get(tranz.get(i).tagB).ted+" ЦЕНА "+MainActivity.round2(but.get(tranz.get(i).tagB).price)+but.get(tranz.get(i).tagB).string_keg;
                						        	l1=(but.get(tranz.get(i).tagB).tmc_name+"\n").length(); 
                						        	l2=l.length();
                						        	text = new SpannableStringBuilder(l); 
                						        	style2 = new StyleSpan(Typeface.BOLD); 
                						        	s12 = new AbsoluteSizeSpan(MainActivity.butNameS ,false);
                						        	text.setSpan(s12, l1, l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
                						        	text.setSpan(style2,l1,l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);	
                						        	//showMessage("text "+text, (byte)1);
                						        	but.get(tranz.get(i).tagB).tb.setText(text);
                						        	
                						        	but.get(tranz.get(i).tagB).tb.setTextOff(text);
                						        	but.get(tranz.get(i).tagB).tb.setTextOn(text);
                						        	
                			}
                			tranz.remove(i);
                			} 
                	//}
                	
                	for (int i=tranz.size()-1; i>=0; i--)
                	//{
                		if (tranz.get(i).tag>=pos ) tranz.get(i).tag=(tranz.get(i).tag-1);
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
//	      int i = 0;
	      //MainActivity.setSizeFontItem((LinearLayout)/*findViewById(R.id.oborotka_item_tr)*/view);
//	      switch (view.getId()) {
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
//	      case R.id.comboX:
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
//		        return true;
//	      }
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


