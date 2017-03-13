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
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
 
public class RasxodActivity extends FragmentActivity implements OnCheckedChangeListener{
  Button /*btnExit,*/tbHist, tbXX, btnOk, btnBack, bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btComa, btDD, btXD;
  ToggleButton /*tb05, tb1, tb15, tb2, tb25, tb3,*/tbnKol, tbTara;
  TextView tvSum, tvKol, tvDKol, tvIdPgr, tvNamePgr;//, tvCombo;
  ListView lvCombo;
  LinearLayout llbut, Dview; LinearLayout lltara;
  int Bpost=0,btnK=0,clrCheck=Color.BLUE, clrNoCheck=Color.BLACK;
  byte Btara=-1, Btovar=-1, CountTara=0;
  float sumI=0, otherVal=0;
  String tmcV;
  String strDialog="";
  String regexp_numb = "\\-?\\d+(\\.\\d{0,})?";
  Map<String, Object> m;
  Dialog dialogg;
//����������� ������ � �������� ��� �������� ���������
  ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
  MySimpleAdapter sAdapter;
  void tara(byte ii){
	  Btara=ii;
	  for (int i=0; i<CountTara; i++) 
		  if (Byte.parseByte(but.get(i).tb.getTag().toString())!=ii) {but.get(i).tb.setChecked(false);but.get(i).tb.setTextColor(clrNoCheck);}
	  if (Byte.parseByte(tbnKol.getTag().toString())!=ii) {tbnKol.setChecked(false);tbnKol.setTextColor(clrNoCheck);}
  }

  class chB {
	   byte indBT;
	   int id_tmc;
	   String tmc_name;
	   float val;
	   float ost;
	   int post;
	   float kol;
	   float price;
	   byte ed;
	   String ted;
	   ToggleButton tb;
	  chB(byte indBT, int id_tmc, String tmc_name, float val, float ost, int post, float kol, float price, byte ed, String ted, ToggleButton tb){
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
  class tranDB {
	  //addRecRASXOD(int id_tmc, float kol, float price, int id_post, int id_klient, String prim, int data_del, int data_ins, byte ok)
	   byte tagL;
	   byte tagB;
	   int id_tmc;
	   float kol;
	   byte ed;
	   float price;
	   int id_post;
	   int id_klient;
	   String prim;
	  tranDB(byte tagL, byte tagB, int id_tmc, float kol, byte ed, float price, int id_post,int id_klient, String prim){
		  this.tagL=tagL;
		  this.tagB=tagB;
		  this.id_tmc=id_tmc;
		  this.kol=kol;
		  this.ed=ed;
		  this.price=price;
		  this.id_post=id_post;
		  this.id_klient=id_klient;
		  this.prim=prim;}
	  }

   ArrayList<chB> but = new ArrayList<chB>();
   ArrayList<tranDB> tranz = new ArrayList<tranDB>();
   //ArrayList<ToggleButton> tbut = new ArrayList<ToggleButton>();
   //ArrayList<LinearLayout> ll = new ArrayList<LinearLayout>();
   Cursor c = MainActivity.db.getRawData ("select distinct T._id as _id, T.name as name from tmc_pgr T left join tmc as TM on T._id=TM.pgr left join ostat as O on O.id_tmc=TM._id where O.kol>0 and TM.vis=1 ",null); 
   
   public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rasxod);
    llbut = (LinearLayout) findViewById(R.id.llBut);
    lltara = (LinearLayout) findViewById(R.id.llTara);
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
    
      
    Cursor cTara = MainActivity.db.getRawData ("select T._id as _id, T.name as name, T.price as price, T.tara as tara, S.id_post as id_post, S.kol as kol, S.ed as ed, E.name as ted from tmc T inner join ostat S on T._id=S.id_tmc inner join tmc_ed E on S.ed=E._id where T.vis=0 and T.ok=1 and S.kol>0 ",null);
    byte ib=0, il=0;
    if (cTara.moveToFirst()) { 
    	 
        do {
        	LinearLayout.LayoutParams PB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            
        	//but.add(new chB(byte indBT, int id_tmc, String tmc_name, float val, float ost, int post, float kol, float price, byte ed, String ted, ToggleButton tb))
        	but.add(new chB(
        			ib, 							//indBT
        			cTara.getInt(cTara.getColumnIndex("_id")), //id_tmc
        			cTara.getString(cTara.getColumnIndex("name")), //tmc_name
        			cTara.getFloat(cTara.getColumnIndex("tara")),//val
        			cTara.getFloat(cTara.getColumnIndex("kol")), //ost 
        			cTara.getInt(cTara.getColumnIndex("id_post")),//id_post
        			1,									//kol
        			cTara.getFloat(cTara.getColumnIndex("price")), //price 
        			(byte)cTara.getInt(cTara.getColumnIndex("ed")), //ed
        			cTara.getString(cTara.getColumnIndex("ted")), //ted
        			new ToggleButton(this)
        			));
        	but.get(ib).tb.setTextOff(but.get(ib).tmc_name/*+"\n"+but.get(ib).ost+but.get(ib).ted+" ���� "+but.get(ib).price*/);
        	but.get(ib).tb.setTextOn(but.get(ib).tmc_name/*+"\n"+but.get(ib).ost+but.get(ib).ted+" ���� "+but.get(ib).price*/);
        	but.get(ib).tb.setChecked(false);
        	but.get(ib).tb.setTag(ib);
        	but.get(ib).tb.setTextColor(clrNoCheck);
        	but.get(ib).tb.setTextSize(20f);
        	but.get(ib).tb.setTag(ib);
        	but.get(ib).tb.setOnCheckedChangeListener(this);
        	lltara.addView(but.get(ib).tb,PB);
        	/*GridLayout.LayoutParams params = (GridLayout.LayoutParams)but.get(ib).tb.getLayoutParams();
        	if (cTara.getString(cTara.getColumnIndex("name")).length()>5) {params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 2); params.setGravity(Gravity.FILL_HORIZONTAL); } 
        	else params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1); 
            but.get(ib).tb.setLayoutParams(params);*/
        	ib++;
        } while (cTara.moveToNext());
        
      } else cTara.close();
    
    CountTara=ib;

    setPgr();

    
    tvKol = (TextView) findViewById(R.id.tvOtherKol_);
    tbnKol = (ToggleButton) findViewById(R.id.btnOtherKolRasxod);
    tbnKol.setTag(-2);
    tbnKol.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked)
			 {buttonView.setTextColor(clrCheck); tara((byte)-2); 
			 	//btnK = buttonView.getId();
			    showDialog(1);
			     }
			else
				{buttonView.setTextColor(clrNoCheck); tvKol.setText("");}
		}
	} );
    
    tbTara = (ToggleButton) findViewById(R.id.btnTaraRasxod_);
    tbTara.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked)
			 buttonView.setTextColor(clrCheck);
			else
				buttonView.setTextColor(clrNoCheck);
		}
	} );
 
   lvCombo = (ListView) findViewById(R.id.lvCombo);
   String[] from = { "name", "kol","price", "tara", "summa", "button"};
   int[] to = { R.id.comboName, R.id.comboKol,R.id.comboPrice, R.id.comboTara, R.id.comboSumma, R.id.comboX };
        // ������� �������
        sAdapter = new MySimpleAdapter(this, data, R.layout.combo_item, from, to);
        // ���������� ������ � ����������� ��� �������
        sAdapter.setViewBinder(new MyViewBinder());
        lvCombo.setAdapter(sAdapter);
    
    
    tvSum = (TextView) findViewById(R.id.tvSumSum);
    tvSum.setText("");
    
    tbHist = (Button) findViewById(R.id.btnRasxodHistory);
    tbHist.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(RasxodActivity.this, RasxodHistActivity.class);
     	   	startActivity(intent);
        }
      });
    
    tbXX = (Button) findViewById(R.id.btnXRasxod_x);
    tbXX.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	data.clear();
        	sAdapter.notifyDataSetChanged();
        	tvSum.setText("");sumI=0;tranz.clear();
        }
      });
    
    btnOk = (Button) findViewById(R.id.btnOkRasxod_ok);
    btnOk.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	if (/*tvCombo.getText().equals("")*/data.size()==0) finish();
        	else
        	{long cou=MainActivity.db.addRecKLIENTcount("svetka", Float.parseFloat(tvSum.getText().toString()), "", MainActivity.getIntDataTime(), (byte)0);
        		
        	for (int i=0; i<tranz.size(); i++) {
        		//public void addRecRASXOD(int id_tmc, float kol, float price, int id_post, int id_klient, String prim, int data_del, int data_ins, byte ok)
        	MainActivity.db.addRecRASXOD(tranz.get(i).id_tmc,
        			tranz.get(i).kol, tranz.get(i).ed, tranz.get(i).price, tranz.get(i).id_post, /*tranz.get(i).id_klient*/(int)cou, tranz.get(i).prim, MainActivity.getIntDataTime(), (byte)0);	
        	Log.d("MyLog", "AtagB="+tranz.get(i).tagB+" i="+i);
        	Log.d("MyLog", "AtagB="+tranz.get(i).tagB+" i="+i+" ost="+but.get(tranz.get(i).tagB).ost);
        	but.get(tranz.get(i).tagB).ost=but.get(tranz.get(i).tagB).ost-tranz.get(i).kol;
        	//Log.d("MyLog", "PtagB="+tranz.get(i).tagB+" ost="+but.get(tranz.get(i).tagB).ost);
        	if (tranz.get(i).tagB>CountTara-1) {
        	//Log.d("MyLog", but.get(tranz.get(i).tagB).tb.getTextOff().toString() );
        	but.get(tranz.get(i).tagB).tb.setTextOff(but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ���� "+but.get(tranz.get(i).tagB).price);
        	but.get(tranz.get(i).tagB).tb.setTextOn(but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ���� "+but.get(tranz.get(i).tagB).price);
        	but.get(tranz.get(i).tagB).tb.setChecked(but.get(tranz.get(i).tagB).tb.isChecked());
        	//Log.d("MyLog", but.get(tranz.get(i).tagB).tb.getTextOff().toString() );
        	
        	//Log.d("MyLog", but.get(tranz.get(i).tagB).tmc_name+"\n"+but.get(tranz.get(i).tagB).ost+but.get(tranz.get(i).tagB).ted+" ���� "+but.get(tranz.get(i).tagB).price );
        	}
        	//but.get(tranz.get(i).tagB).kol=but.get(tranz.get(i).tagB).kol-tranz.get(i).kol;
        	//tbut.get(tranz.get(i).tagB-7).setTextOff(c.getString(c.getColumnIndex("name"))+"\n��� "+but.get(tranz.get(i).tagB).kol+"� ���� "+c.getFloat(c.getColumnIndex("price")));
        	//tbut.get(tranz.get(i).tagB-7).setTextOn(c.getString(c.getColumnIndex("name"))+"\n��� "+but.get(tranz.get(i).tagB).kol+"� ���� "+c.getFloat(c.getColumnIndex("price")));
        	}
        	
        	//tvCombo.setText("");
        		Toast.makeText(RasxodActivity.this, "��� � "+cou+" �� �����:"+tvSum.getText().toString(), Toast.LENGTH_LONG).show();
        		
        	data.clear();
        	sAdapter.notifyDataSetChanged();
        	tvSum.setText("");sumI=0;tranz.clear();
        	}
        }
      });
  
  }
   
   void setPgr() {
	   //byte ib=CountTara;
	   llbut.removeAllViewsInLayout();
	   if (c.moveToFirst()) { 
		   
	        do {
	        	//LinearLayout.LayoutParams PL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
	    		//PL.weight=1;
	        	LinearLayout.LayoutParams PB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
	    		PB.weight=1;
	    		PB.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
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
	        	btnPgr.setTextSize(20f);
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
			regexp="-([�-��-߸�a-zA-Z0-9 \\.,-]+)$";
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
	        	
	        	/*ll.get(il-1)*/llbut.addView(btnPgr,PB);
	        	//ib++;
	        } while (c.moveToNext());
	        
	      } 
	    //c.close();   
   }
   
   void setBut() {
	   //Log.d("MyLog", "setbut__count_tara="+CountTara+" but.size="+but.size()+" idPgr="+tvIdPgr.getText());
	   byte ib;
	   if (tranz.size()==0)
	   {
	   for (int i=but.size()-1; i>=CountTara; i--) but.remove(i);
	   ib=CountTara;
	   
	   }
	   else
		   {for (int i=but.size()-1; i>=CountTara; i--) but.get(i).tb.setVisibility(ToggleButton.INVISIBLE);// remove(i);
		   ib=(byte)but.size();
		   }
	   llbut.removeAllViewsInLayout();   
	   //Log.d("MyLog", "setbut_del_count_tara="+CountTara+" but.size="+but.size()+" idPgr="+tvIdPgr.getText());
	   
	   //Log.d("MyLog", "setBut id_pgr="+tvIdPgr.getText());
	   Cursor cc = MainActivity.db.getRawData ("select T._id as _id, T.name as name, T.price as price, S.id_post as id_post, S.kol as kol, S.ed as ed, E.name as ted from tmc T inner join ostat S on T._id=S.id_tmc inner join tmc_ed E on S.ed=E._id where T.vis=1 and S.kol>0 and T.pgr="+tvIdPgr.getText(),null);
	   //Log.d("MyLog", "setBut id_pgr="+tvIdPgr.getText()+" cc.count="+cc.getCount()+" but.size="+but.size());
	   if (cc.moveToFirst()) { 
		   
	        do {
	        	LinearLayout.LayoutParams PB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
	    		PB.weight=1;
	    		PB.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
	        	
	        	//but.add(new chB(byte indBT, int id_tmc, String tmc_name, float val, float ost, int post, float kol, float price, byte ed, String ted, ToggleButton tb))
	        	but.add(new chB(
	        			ib, 							//indBT
	        			cc.getInt(cc.getColumnIndex("_id")), //id_tmc
	        			cc.getString(cc.getColumnIndex("name")), //tmc_name
	        			0, 									//val
	        			cc.getFloat(cc.getColumnIndex("kol")), //ost 
	        			cc.getInt(cc.getColumnIndex("id_post")),//id_post
	        			0,									//kol
	        			cc.getFloat(cc.getColumnIndex("price")), //price 
	        			(byte)cc.getInt(cc.getColumnIndex("ed")), //ed
	        			cc.getString(cc.getColumnIndex("ted")), //ted
	        			new ToggleButton(this)
	        			));
	        	but.get(ib).tb.setTextOff(but.get(ib).tmc_name+"\n"+but.get(ib).ost+but.get(ib).ted+" ���� "+but.get(ib).price);
	        	but.get(ib).tb.setTextOn(but.get(ib).tmc_name+"\n"+but.get(ib).ost+but.get(ib).ted+" ���� "+but.get(ib).price);
	        	but.get(ib).tb.setChecked(false);
	        	but.get(ib).tb.setTag(ib);
	        	but.get(ib).tb.setTextColor(clrNoCheck);
	        	but.get(ib).tb.setTextSize(20f);
	        	but.get(ib).tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        		@Override
	        		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	        			
	        			if (isChecked)
	        			{ buttonView.setTextColor(clrCheck);
	        			Btovar=Byte.parseByte(buttonView.getTag().toString());
	        			for (int i=CountTara; i<but.size(); i++) {
	        		    	if ((i)!= Integer.parseInt(buttonView.getTag().toString())) {but.get(i).tb.setChecked(false); but.get(i).tb.setTextColor(clrNoCheck) ;}
	        			    }
	        			if (Btara!=-1) fixV();// buttonView.setChecked(false);}
	        			}
	        			else
	        				buttonView.setTextColor(clrNoCheck);
	        		}
	        	} );
	        	
	        	/*ll.get(il-1)*/llbut.addView(but.get(ib).tb,PB);
	        	//Log.d("MyLog", "ib="+ib+" but.get(ib).tb.texton="+but.get(ib).tb.getTextOn()+" but.size="+but.size());
	        	ib++;
	        } while (cc.moveToNext());
	        
	      } //else c.close();
	    
	    //for (int i=0; i<c.getCount(); i++) {
	    	
	    //}
	    cc.close();
	    btnBack.setTag(Integer.parseInt(tvIdPgr.getText().toString()));
   }
   
   void fixV() {
	   if (tbTara.isChecked()) 
   	{m = new HashMap<String, Object>();
		   	  
   		   m.put("name", but.get(Btovar).tmc_name);
   		   m.put("price", but.get(Btovar).price);
   		   if (Btara!=-2) {m.put("kol", but.get(Btara).val+" "+but.get(Btovar).ted); m.put("summa", but.get(Btovar).price*(but.get(Btara).val/*/0.5*/));} else
   		{m.put("kol", tvKol.getText()+" "+but.get(Btovar).ted); m.put("summa", but.get(Btovar).price*((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/));}
   		      m.put("tara", "���� ����");
   		      data.add(m);
   		      sAdapter.notifyDataSetChanged();
   		   if (Btara!=-2)	
   	{tranz.add(new tranDB((byte)(data.size()-1), Btovar,but.get(Btovar).id_tmc, but.get(Btara).val, but.get(Btovar).ed, but.get(Btovar).price, but.get(Btovar).post, 0, "���� ����"));
   	sumI=sumI+but.get(Btovar).price*(but.get(Btara).val/*/0.5f*/);}
   		   else
   		{tranz.add(new tranDB((byte)(data.size()-1), Btovar,but.get(Btovar).id_tmc, ((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/) , but.get(Btovar).ed, but.get(Btovar).price, but.get(Btovar).post, 0, "���� ����"));
   	   	sumI=sumI+but.get(Btovar).price*((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/);}
   	}
   	else
   	{
   	  m = new HashMap<String, Object>();
   	
   	  	m.put("name", but.get(Btovar).tmc_name);
   	 m.put("price", but.get(Btovar).price);
   	 if (Btara!=-2) {m.put("kol", but.get(Btara).val+" "+but.get(Btovar).ted);
		      m.put("tara", " + ���� "+but.get(Btara).tmc_name);
		      m.put("summa", (but.get(Btovar).price*(but.get(Btara).val/*/0.5*/)+but.get(Btara).price));}
   	 else
   	{m.put("kol", tvKol.getText()+" "+but.get(Btovar).ted);
    m.put("tara", " + ����� ");
    m.put("summa", (but.get(Btovar).price*
    		((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)+0));}
		      data.add(m);
		      
   		
		      sAdapter.notifyDataSetChanged();
		      if (Btara!=-2) {	      
   	tranz.add(new tranDB((byte)(data.size()-1), Btovar,but.get(Btovar).id_tmc, but.get(Btara).val,but.get(Btovar).ed, but.get(Btovar).price, but.get(Btovar).post, 0, "� �����"));	
       tranz.add(new tranDB((byte)(data.size()-1), Btara,but.get(Btara).id_tmc, but.get(Btara).kol, but.get(Btara).ed, but.get(Btara).price, but.get(Btara).post, 0, "����"));
       sumI=sumI+(but.get(Btovar).price*(but.get(Btara).val/*/0.5f*/)+but.get(Btara).price);
		      }
		      else
			      
		    	   	{tranz.add(new tranDB((byte)(data.size()-1), Btovar,but.get(Btovar).id_tmc, (tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString()),but.get(Btovar).ed, but.get(Btovar).price, but.get(Btovar).post, 0, "�����"));	
		    	      // tranz.add(new tranDB((byte)(data.size()-1), Btara,but.get(Btara).id_tmc, but.get(Btara).kol, but.get(Btara).ed, but.get(Btara).price, but.get(Btara).post, 0, "tara"));
		    	       sumI=sumI+(but.get(Btovar).price*((tvKol.getText().length()==0||tvKol.getText().equals(""))?0:Float.parseFloat(tvKol.getText().toString())/*/0.5*/)+0);}
   	}
   	
   	tvSum.setText(String.valueOf(sumI));
   	but.get(Btovar).tb.setChecked(false);
   	but.get(Btovar).tb.setTextColor(clrNoCheck);
   	if (Btara!=-2)
   	{but.get(Btara).tb.setChecked(false);
   	but.get(Btara).tb.setTextColor(clrNoCheck);}
   	else
   	{tbnKol.setChecked(false);
   	tbnKol.setTextColor(clrNoCheck);
   	tvKol.setText("");}
   	Btovar=-1; Btara=-1;
   }
  
  
   @Override
   protected Dialog onCreateDialog(int id) {
     if (id == 1) {
       AlertDialog.Builder adb = new AlertDialog.Builder(this);
       adb.setTitle("������� ���/����������� ������");
       //adb.setMessage("Message");
       strDialog="";
       Dview = (LinearLayout) getLayoutInflater().inflate(R.layout.other_kol, null);
       //Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btComa, btDD, btXD;
       // ������������� ��, ��� ���������� ���� �������
       adb.setView(Dview);
       // ������� TexView ��� ����������� ���-��
       tvDKol = (TextView) Dview.findViewById(R.id.tvOtherKolD);
       //strDialog="";
    	tvDKol.setText(strDialog);
       bt0 = (Button) Dview.findViewById(R.id.btn000);
       bt1 = (Button) Dview.findViewById(R.id.btn111);
       bt2 = (Button) Dview.findViewById(R.id.btn222);
       bt3 = (Button) Dview.findViewById(R.id.btn333);
       bt4 = (Button) Dview.findViewById(R.id.btn444);
       bt5 = (Button) Dview.findViewById(R.id.btn555);
       bt6 = (Button) Dview.findViewById(R.id.btn666);
       bt7 = (Button) Dview.findViewById(R.id.btn777);
       bt8 = (Button) Dview.findViewById(R.id.btn888);
       bt9 = (Button) Dview.findViewById(R.id.btn999);
       btComa = (Button) Dview.findViewById(R.id.btnComaCC);
       btXD = (Button) Dview.findViewById(R.id.btnXXX);
       btDD = (Button) Dview.findViewById(R.id.btnDDD);
       bt0.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
   			Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
   			Matcher matcher = pattern.matcher(strDialog.concat("0"));
   			if (matcher.matches()) {strDialog=strDialog.concat("0"); tvDKol.setText(strDialog); }
   			//Log.d("MyLog", ""+strDialog);
   			//tvDKol.setText(matcher.matches()?matcher.group():strDialog);
   			
        	  // strDialog=strDialog.concat("0");
           	//tvDKol.setText(""+strDialog);
           }
         });
   	bt1.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
        	 Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
    			Matcher matcher = pattern.matcher(strDialog.concat("1"));
    			if (matcher.matches()) {strDialog=strDialog.concat("1"); tvDKol.setText(strDialog); }
         }
       });
   	bt2.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("2"));
      			if (matcher.matches()) {strDialog=strDialog.concat("2"); tvDKol.setText(strDialog); }
           }
         });
   	
   	bt3.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("3"));
      			if (matcher.matches()) {strDialog=strDialog.concat("3"); tvDKol.setText(strDialog); }
           }
         });
   	
   	bt4.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("4"));
      			if (matcher.matches()) {strDialog=strDialog.concat("4"); tvDKol.setText(strDialog); }
           }
         });
   	
   	bt5.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("5"));
      			if (matcher.matches()) {strDialog=strDialog.concat("5"); tvDKol.setText(strDialog); }
           }
         });
   	
   	bt6.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("6"));
      			if (matcher.matches()) {strDialog=strDialog.concat("6"); tvDKol.setText(strDialog); }
           }
         });
   	
   	bt7.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("7"));
      			if (matcher.matches()) {strDialog=strDialog.concat("7"); tvDKol.setText(strDialog); }
           }
         });
   	
   	bt8.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("8"));
      			if (matcher.matches()) {strDialog=strDialog.concat("8"); tvDKol.setText(strDialog); }
           }
         });
   	
   	bt9.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("9"));
      			if (matcher.matches()) {strDialog=strDialog.concat("9"); tvDKol.setText(strDialog); }
           }
         });
   	
   	btComa.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
      			Matcher matcher = pattern.matcher(strDialog.concat("."));
      			if (matcher.matches()) {strDialog=strDialog.concat("."); tvDKol.setText(strDialog); }
           }
         });
   	
   	btXD.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
           	tvDKol.setText("");
           }
         });
   	
   	btDD.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
           	tvDKol.setText((tvDKol.getText().length()==0||tvDKol.getText().equals("")||tvDKol.getText().equals("0"))?"":
           		tvDKol.getText().subSequence(0, tvDKol.getText().length()-1) );
           }
         });

       adb.setNegativeButton("������", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	  // Log.d("MyLog", "Cancel");
          	 tvKol.setText("");
          	 tbnKol.setChecked(false); tbnKol.setTextColor(clrNoCheck);
           }
       })
       .setPositiveButton("������", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	  // Log.d("MyLog", "Dismiss");
          	 if (tvDKol.getText().length()==0||tvDKol.getText().equals("")||tvDKol.getText().equals("0")) {
          		 tvKol.setText("");
              	 tbnKol.setChecked(false); tbnKol.setTextColor(clrNoCheck);
          	 } else
          	 {tvKol.setText(tvDKol.getText());
          	 if (Btovar!=-1) fixV();}
           }
       });
       dialogg = adb.create();
  
       // ���������� �����������
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
     tvDKol.setText(""); strDialog="";
     //tvDKol = (TextView) dialog.getWindow().findViewById(R.id.tvOtherKolD);
     /*bt0 = (Button) dialog.getWindow().findViewById(R.id.btn000);
     bt1 = (Button) dialog.getWindow().findViewById(R.id.btn111);
     bt2 = (Button) dialog.getWindow().findViewById(R.id.btn222);
     bt3 = (Button) dialog.getWindow().findViewById(R.id.btn333);
     bt4 = (Button) dialog.getWindow().findViewById(R.id.btn444);
     bt5 = (Button) dialog.getWindow().findViewById(R.id.btn555);
     bt6 = (Button) dialog.getWindow().findViewById(R.id.btn666);
     bt7 = (Button) dialog.getWindow().findViewById(R.id.btn777);
     bt8 = (Button) dialog.getWindow().findViewById(R.id.btn888);
     bt9 = (Button) dialog.getWindow().findViewById(R.id.btn999);
     btComa = (Button) dialog.getWindow().findViewById(R.id.btnComaCC);
     btXD = (Button) dialog.getWindow().findViewById(R.id.btnXXX);
     btDD = (Button) dialog.getWindow().findViewById(R.id.btnDDD);*/
    
 	
     /*if (id == 1) {
       // ������� TextView ��� ����������� ������� � ���������� �������
       // �����
       TextView tvTime = (TextView) dialog.getWindow().findViewById(
           R.id.tvTime);
       tvTime.setText(sdf.format(new Date(System.currentTimeMillis())));
       // ���� ���� ������ ������ ��������
       if (btn == R.id.btnAdd) {
         // ������� ����� TextView, ��������� � ������, ��������� �����
         TextView tv = new TextView(this);
         view.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT,
             LayoutParams.WRAP_CONTENT));
         tv.setText("TextView " + (textViews.size() + 1));
         // ��������� ����� TextView � ���������
         textViews.add(tv);
         // �����
       } else {
         // ���� ��������� ��������� TextView �������
         if (textViews.size() > 0) {
           // ������� � ��������� ��������� TextView
           TextView tv = textViews.get(textViews.size() - 1);
           // ������� �� �������
           view.removeView(tv);
           // ������� �� ���������
           textViews.remove(tv);
         }
       }
       // ��������� �������
       tvCount.setText("���-�� TextView = " + textViews.size());
     }*/
   } 
   
  @Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked)
		{buttonView.setTextColor(clrCheck);
		 //Log.d("MyLog", "tag="+Byte.parseByte(buttonView.getTag().toString()));
		tara(Byte.parseByte(buttonView.getTag().toString()));
		if (Btovar!=-1) fixV(); 
		}
		else
			buttonView.setTextColor(clrNoCheck);
	}
  protected void onDestroy() {
    super.onDestroy();
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
	      // ����� �����-������, ������� ��������� �����
	      super.setViewText(v, text);
	      // ���� ������ ��� TextView, �� ������������� 
	      if (v.getId() == R.id.comboSumma) {
	        float i = Float.parseFloat(text);
	        //if (i < 0) v.setTextColor(Color.RED); else
	          if (i > 100) v.setTextColor(Color.RED);
	      }
	    }
	 
	    @Override
	    public void setViewImage(ImageView v, int value) {
	      // ����� �����-������
	      super.setViewImage(v, value);
	      // ������������� ImageView
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
             Button b=(Button)v.findViewById(R.id.comboX);
             b.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    //Toast.makeText(RasxodActivity.this,dataSet.get("summa").toString()+" position "+pos,Toast.LENGTH_SHORT).show();
                	sumI=sumI-Float.parseFloat(dataSet.get("summa").toString());
                	tvSum.setText(String.valueOf(sumI));
                	for (int i=0; i<tranz.size(); i++) {if (tranz.get(i).tagL==(byte)pos) {tranz.remove(i);} }
                	data.remove(pos);
                	sAdapter.notifyDataSetChanged();
                	
                }
            });
            return v;
        }
	  }
  class MyViewBinder implements SimpleAdapter.ViewBinder {
	     
	    //int red = getResources().getColor(R.color.Red);
	    //int orange = getResources().getColor(R.color.Orange);
	    //int green = getResources().getColor(R.color.Green);
	     
	    @Override
	    public boolean setViewValue(View view, Object data,
	        String textRepresentation) {
	      int i = 0;
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
 
}

