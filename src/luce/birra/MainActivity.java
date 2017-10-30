package luce.birra;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import luce.birra.DialogScreen.DialogListener;
import luce.birra.OpenFileDialog.OpenDialogListener;
import luce.birra.OpenFileDialog.OpenDialogListenerDir;
 
public class MainActivity extends Activity implements OnClickListener {
Button btnExit, btnProd, btnOstat, btnPrixod, btnRasxod, btnKassa, btnSetting, btnAbout;
static DB db;
static int w=0;
static int h=0;
static float scale=0;
static int sizeBigButton=15;
static int sizeMediumButton=15;
static int sizeSmallButton=15;
static int sizeBigText=15;
static int sizeMediumText=15;
static int sizeSmallText=15;
static String pathD = "";
static int butTara=21;
static int butPgr=15;
static int butName=15;
static int butNameS=12;
static int tabH=15;
static int tabI=13;
static int tabBut=15;
static int butMenu=20;
static int butKalk=30;
static int tvH=15;
static int tvI=17;
static int seek=50;
static int red1=50;
static int green1=50;
static int blue1=50;
static int red2=50;
static int green2=50;
static int blue2=50;
static String usr = "";
static byte access=0;
static byte postlitr=0;
static int num_id=1;
static int day=1;
int but_menu=0;
static String my_pass="svetka";
// SharedPreferences sPref;
/*static void setSizeFont(ViewGroup mlayout,byte sB, byte sH, byte sI) {
	float b = (sB==1)?sizeBigButton:((sB==2)?sizeMediumButton:sizeSmallButton);
	float th = (sH==1)?sizeBigText:((sH==2)?sizeMediumText:sizeSmallText);
	float ti = (sI==1)?sizeBigText:((sI==2)?sizeMediumText:sizeSmallText);
	ArrayList<View> alv = getViewsByTag(mlayout);
	for (int i=0; i<alv.size(); i++)
    {if (alv.get(i) instanceof CheckBox) ((CheckBox)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , ti);
	else if (alv.get(i) instanceof Button)
    	((Button)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , b);
    	else if (alv.get(i) instanceof EditText ) {
    		((EditText)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , th);
		}
    	else ((TextView)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , ti);	
    }
}*/

/*
Drawable background = imageView.getBackground();
if (background instanceof ShapeDrawable) {
    ((ShapeDrawable)background).getPaint().setColor(ContextCompat.getColor(mContext,R.color.colorToSet));
} else if (background instanceof GradientDrawable) {
    ((GradientDrawable)background).setColor(ContextCompat.getColor(mContext,R.color.colorToSet));
} else if (background instanceof ColorDrawable) {
    ((ColorDrawable)background).setColor(ContextCompat.getColor(mContext,R.color.colorToSet));
}
 * */

/*
 * //Drawable background;
 * background = alv.get(i).getBackground();
if (background instanceof GradientDrawable) {
GradientDrawable gradient = new GradientDrawable(Orientation.BOTTOM_TOP, new int[] {color1, color2});
gradient.setShape(GradientDrawable.RECTANGLE);
 //gradient.setCornerRadius(10.f);
 //if (background.equals(alv.get(i).getContext().getResources().getDrawable(R.drawable.edittexth_style)))
 alv.get(i).setBackgroundDrawable(gradient);
//((GradientDrawable)background).setColor(color2);
}*/


static void excel(Context cntx, Activity act, String dat1, String dat2, String pgr, String tit, byte metod )
{
	final String dat11=dat1;
	final String dat22=dat2;
	final String pgrr=pgr;
	final String titt=tit;
	final byte met=metod;
	final Context context=cntx;
	//final Intent sendIntent;
	final Activity activity=act;
	DialogScreen getkol = new DialogScreen(context,activity,0)
	 	.setDialogScreenListener(new DialogListener() {
	 	@Override
		public void OnSelectedKol(double k) {
	 		//Log.d("MyLog", "k="+k+" met="+met);
			File file = null; //Uri u1; 
			Intent sendIntent;
			switch((byte)k) {
			//case 0: 
			case 1:
				switch(met) {
				case 1:
				file=Export2Excel.oborotka(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), ""); break;
				case 2:
					file=Export2Excel.rasxod(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), ""); break;
				case 3:
					file=Export2Excel.rasxod_ostat(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), ""); break;
				case 4:
					file=Export2Excel.prixod(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), ""); break;
				case 5:
					file=Export2Excel.ostat((int) StrToFloat(pgrr), ""); break;
				case 6:
					file=Export2Excel.all_spr(""); break;
				case 7:
					file=Export2Excel.price((int) StrToFloat(pgrr), ""); break;
				case 8:
					file=Export2Excel.check(getIntData(dat11),getIntData(dat22), ""); break;
				case 9:
					file=Export2Excel.otchet_del(getIntData(dat11),getIntData(dat22), ""); break;
				}
				//u1  =  Uri.fromFile(file);
				//intent = new Intent(Intent.);
				sendIntent = new Intent(Intent.ACTION_VIEW);
				//sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Оборотная ведомость с "+tvDataIns.getText().toString()+" по "+Calendar.getInstance().get(Calendar.DATE)+"."+Calendar.getInstance().get(Calendar.MONTH)+"."+Calendar.getInstance().get(Calendar.YEAR));
				//sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
				sendIntent.setDataAndType(Uri.fromFile(file),"application/vnd.ms-excel");
				//sendIntent.setType("text/html");
				context.startActivity(sendIntent); 
				break;
			case 2:
				OpenFileDialog fileDialog = new OpenFileDialog(context)
	        	.setOpenDialogListenerDir(new OpenDialogListenerDir() {
					@Override
					public void OnSelectedDir(String dirName) {
						switch(met){
						case 1:
						Export2Excel.oborotka(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), dirName); break;
						case 2:
						Export2Excel.rasxod(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), dirName); break;
						case 3:
							Export2Excel.rasxod_ostat(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), dirName); break;
						case 4:
							Export2Excel.prixod(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), dirName); break;
						case 5:
							Export2Excel.ostat((int) StrToFloat(pgrr), dirName); break;
						case 6:
							Export2Excel.all_spr(dirName); break;
						case 7:
							Export2Excel.price((int) StrToFloat(pgrr), dirName); break;
						case 8:
							Export2Excel.check(getIntData(dat11),getIntData(dat22), dirName); break;
						case 9:
							Export2Excel.otchet_del(getIntData(dat11),getIntData(dat22), dirName); break;
						}
					}
				}) ;
	        	fileDialog.show();
	        	break;
			case 3:
				//Log.d("MyLog", "met="+met);
				switch(met){
				case 1:
				file=Export2Excel.oborotka(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), ""); break;
				case 2:
					file=Export2Excel.rasxod(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), ""); break;
				case 3:
					file=Export2Excel.rasxod_ostat(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), ""); break;
				case 4:
					file=Export2Excel.prixod(getIntData(dat11),getIntData(dat22),(int) StrToFloat(pgrr), ""); break;
				case 5:
					file=Export2Excel.ostat((int) StrToFloat(pgrr), ""); break;
				case 6:
					file=Export2Excel.all_spr(""); break;
				case 7:
					file=Export2Excel.price((int) StrToFloat(pgrr), ""); break;
				case 8:
					file=Export2Excel.check(getIntData(dat11),getIntData(dat22), ""); break;
				case 9:
					file=Export2Excel.otchet_del(getIntData(dat11),getIntData(dat22), ""); break;
				}
				//u1  =  Uri.fromFile(file);
				sendIntent = new Intent(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, titt+" "+dat11+" "+dat22/*Calendar.getInstance().get(Calendar.DATE)+"."+Calendar.getInstance().get(Calendar.MONTH)+"."+Calendar.getInstance().get(Calendar.YEAR)*/);
				sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
				sendIntent.setType("text/html");
				context.startActivity(sendIntent); 
				break;
			default:
			}
		}
	}) ;getkol.show();
}

static void excel_import(Context cntx, Activity act, byte metod )
{

	final byte met=metod;
	final Context context=cntx;
	//final Intent sendIntent;
	final Activity activity=act;
	DialogScreen getkol = new DialogScreen(context,activity,1)
	 	.setDialogScreenListener(new DialogListener() {
	 	@Override
		public void OnSelectedKol(double k) {
			//File file = null; //Uri u1; 
			//Intent sendIntent;
			switch((byte)k) {
			//case 0: 
			
			case 1:
				OpenFileDialog fileDialog = new OpenFileDialog(context)
	        	.setOpenDialogListener(new OpenDialogListener() {
					
					@Override
					public void OnSelectedFile(String fileName) {
							switch(met){
							case 1:
							Import2Excel.load_tmc(fileName, context); break;
							case 2:
								Import2Excel.load_ost(fileName,context); break; 
							}
					}
				});
	        	
	        	fileDialog.show();
	        	break;
			
			default:
			}
		}
	}) ;getkol.show();
}

static void setSizeFontMain(ViewGroup mlayout) {
	
	ArrayList<View> alv = getViewsByTag(mlayout);
	for (int i=0; i<alv.size(); i++)
    {
	if (!(alv.get(i) instanceof SeekBar || alv.get(i) instanceof NumberPicker))
	
		if (alv.get(i) instanceof CheckBox) 
			((CheckBox)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tvI);
		else if (alv.get(i) instanceof Button)
			((Button)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , butMenu);
    	else if (alv.get(i) instanceof EditText ) 
    		((EditText)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tvI);
    	else if (((TextView)alv.get(i)).getParent() instanceof TableRow) 
    		((TextView)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tabH);
    		else
    		{ //int l=1; 
    		String[] n = ((TextView)alv.get(i)).getText().toString().split(" ");
    		int nn=
    				((TextView)alv.get(i)).getText().length();
        	//for (int ii=0; ii<n.length; ii++ )
        		//if (n[ii].length()>l) l=n[ii].length();
        	
    		//Log.d("MyLog", "text = "+((TextView)alv.get(i)).getText().toString()+" l="+n.length);
        		if (n.length==1 || nn<10)
    			((TextView)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tvH);
        		else
        			((TextView)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tvH/2);
    		}
    }
}

static void setSizeFontKalk(ViewGroup mlayout) {
	
	ArrayList<View> alv = getViewsByTag(mlayout);
	for (int i=0; i<alv.size(); i++)
    {
	//if (!(alv.get(i) instanceof SeekBar || alv.get(i) instanceof NumberPicker))
	//if (alv.get(i) instanceof CheckBox) ((CheckBox)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tvI);
	//else 
		if (alv.get(i) instanceof Button)
    	((Button)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , butKalk);
    	//else if (alv.get(i) instanceof EditText ) {
    		//((EditText)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , butPgr);
		//}
    	else 
    		//if (((TextView)alv.get(i)).getParent() instanceof TableRow) ((TextView)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tabH);
    		//else
    		((TextView)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , butKalk-5);	
    }
}

static void setSizeFontItem(ViewGroup mlayout) {
	
	ArrayList<View> alv = getViewsByTag(mlayout);
	for (int i=0; i<alv.size(); i++)
    {
		if (alv.get(i) instanceof CheckBox) ((CheckBox)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tabI);
	else if (alv.get(i) instanceof Button)
    	((Button)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tabBut);
    	//else if (alv.get(i) instanceof EditText ) {
    		//((EditText)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tvI);
		//}
    	else ((TextView)alv.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX , tabI);	
    }
}

static ArrayList<View> getViewsByTag(ViewGroup root){
    ArrayList<View> views = new ArrayList<View>();
    final int childCount = root.getChildCount();
    for (int i = 0; i < childCount; i++) {
        final View child = root.getChildAt(i);
        if (child instanceof ViewGroup) {
            views.addAll(getViewsByTag((ViewGroup) child));
        } 
        else
            views.add(child);
    }
    return views;
}

static void setSpinnerItemById(Spinner spinner, long _id)
{
    int spinnerCount = spinner.getCount();
    //Log.d("MyLog", "spinercount="+spinnerCount );
    for (int i = 0; i < spinnerCount; i++)
    {
        Cursor value = (Cursor) spinner.getItemAtPosition(i);
        long id = value.getLong(value.getColumnIndex("_id"));
        //Log.d("MyLog", "id="+id+" _id="+_id );
        if (id == _id)
        {
            spinner.setSelection(i);
        }
    }
}

float PxToDp(float px) {
	return px
			/ getApplicationContext().getResources().getDisplayMetrics().density;
}

 float DpToPx(float dp) {
	return dp
			* getApplicationContext().getResources().getDisplayMetrics().density;
}

 static int getIntDataTime(){
	return ((((Calendar.getInstance().get(Calendar.YEAR)%2000)*100+Calendar.getInstance().get(Calendar.MONTH)+1)*100+Calendar.getInstance().get(Calendar.DATE))*100
			+Calendar.getInstance().get(Calendar.HOUR_OF_DAY))*100+Calendar.getInstance().get(Calendar.MINUTE);
}
 
static String getStringData(int dat){
	return String.valueOf(dat).substring(4, 6)+"."+String.valueOf(dat).substring(2, 4)+"."+String.valueOf(dat).substring(0, 2);
}

static String getStringDataTime(int dat){
	if (String.valueOf(dat).length()==0||dat==0) return ""; else
	return String.valueOf(dat).substring(4, 6)+"."+String.valueOf(dat).substring(2, 4)+"."+String.valueOf(dat).substring(0, 2)+" "+String.valueOf(dat).substring(6, 8)+":"+String.valueOf(dat).substring(8, 10);
}

static int getIntData(){
	return ((Calendar.getInstance().get(Calendar.YEAR)%2000)*100+Calendar.getInstance().get(Calendar.MONTH)+1)*100+Calendar.getInstance().get(Calendar.DATE);
}
//    02-14 16:46:33.804: D/MyLog(21335): create data=1170102
/*static int getIntData(String dat){
	try{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy"); 
		ParsePosition pos = new ParsePosition(0);
		Date d = sdf.parse(dat,pos); 
		return ((d.getYear()%2000)*100+d.getMonth())*100+d.getDay();
		}catch(ParseException ex){
		    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
			ex.printStackTrace();
			return 0;
		}
}*/

static float round(double number, int scale) {
    int pow = 10;
    for (int i = 1; i < scale; i++)
        pow *= 10;
    double tmp = number * pow;
    return (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) / pow;
}

static double roundd(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}

static float round2(double number/*, int scale*/) {
    /*int pow = 10;
    for (int i = 1; i < scale; i++)
        pow *= 10;
    double tmp = number * pow;
    return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    */
	double tmp = number * 100;
	return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / 100;
}

static float round3(double number/*, int scale*/) {
    /*int pow = 10;
    for (int i = 1; i < scale; i++)
        pow *= 10;
    double tmp = number * pow;
    return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    */
	double tmp = number * 1000;
	return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / 1000;
}

static int getIntData(String dat){
	try{
		return Integer.parseInt(dat.substring(6, 8).concat(dat.substring(3, 5)).concat(dat.substring(0, 2))) ;
		}catch(Exception ex){
		    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
			//ex.printStackTrace();
			return 0;
		}
}

static int getIntDataTime(String dat){
	try{//Log.d("MyLog", "dtm="+dat);
		return Integer.parseInt(dat.substring(6, 8).concat(dat.substring(3, 5)).concat(dat.substring(0, 2)).concat(dat.substring(9, 11)).concat(dat.substring(12, 14))) ;
		//return Integer.parseInt( String.valueOf(Integer.parseInt(dat.substring(6, 10))%2000).concat(dat.substring(3, 5)).concat(dat.substring(0, 2)).concat(dat.substring(10, 12)).concat(dat.substring(13, 15))) ;
		}catch(Exception ex){
		    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
			//ex.printStackTrace();
			return 0;
		}
}

static float StrToFloat(String s) {
	   Float total = Float.valueOf(0);
	 try
	 {
	     total = Float.valueOf(s);
	 }
	 catch(NumberFormatException ex)
	 {
	     DecimalFormat df = new DecimalFormat();
	     Number n = null;
	     try
	     {
	         n = df.parse(s);// parse(s);
	     } 
	     catch(ParseException ex2){ }
	     if(n != null)
	         total = n.floatValue();
	 }
	 return round(total,3);
};

static float StrToFloat2(String s) {
	   Float total = Float.valueOf(0);
	 try
	 {
	     total = Float.valueOf(s);
	 }
	 catch(NumberFormatException ex)
	 {
	     DecimalFormat df = new DecimalFormat();
	     Number n = null;
	     try
	     {
	         n = df.parse(s);// parse(s);
	     } 
	     catch(ParseException ex2){ }
	     if(n != null)
	         total = n.floatValue();
	 }
	 return round(total,2);
};
/*static int getIntDataTime(String dat){
	try{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm"); // here set the pattern as you date in string was containing like date/month/year
		ParsePosition pos = new ParsePosition(0);
		Date d = sdf.parse(dat,pos); 
		return ((((d.getYear()%2000)*100+d.getMonth())*100+d.getDay())*100+d.getHours())*100+d.getMinutes();
		}catch(ParseException ex){
		    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
			ex.printStackTrace();
			return 0;
		}
}*/
void saveSetting() {
		SharedPreferences sPref = getSharedPreferences("BirraPref", MODE_PRIVATE);//getPreferences(MODE_PRIVATE);
	    Editor ed = sPref.edit();
	    ed.putInt("buttara", butTara );
	    ed.putInt("butpgr", butPgr );
	    ed.putInt("butname", butName );
	    ed.putInt("butnames", butNameS );
	    ed.putInt("tabh", tabH );
	    ed.putInt("tabi", tabI );
	    ed.putInt("tabbut", tabBut );
	    ed.putInt("butmenu", butMenu );
	    ed.putInt("butkalk", butKalk );
	    ed.putInt("tvh", tvH );
	    ed.putInt("tvi", tvI );
	    ed.putInt("seek", seek );
	    ed.putString("usr", usr );
	    ed.putInt("red1", red1 );
	    ed.putInt("red2", red2 );
	    ed.putInt("green1", green1 );
	    ed.putInt("green2", green2 );
	    ed.putInt("blue1", blue1 );
	    ed.putInt("blue2", blue2 );
	    ed.putInt("postlitr", postlitr );
	    ed.putInt("num_id", num_id );
	    day=Calendar.getInstance().get(Calendar.DATE);
	    ed.putInt("day", day );
	    ed.commit();
	    //Toast.makeText(this, "настройки сохранены", Toast.LENGTH_SHORT).show();
	  }

  void loadSetting() {
	SharedPreferences sPref = getSharedPreferences("BirraPref", MODE_PRIVATE);//getPreferences(MODE_PRIVATE);
	butTara = sPref.getInt("buttara", butTara/*(int)(h/15)*/);
	butPgr = sPref.getInt("butpgr", butPgr /*(int)(h/15)*/);
    butName = sPref.getInt("butname", butName /*(int)(h/23)*/);
    butNameS = sPref.getInt("butnames", butNameS /*(int)(h/46)*/);
    tabH = sPref.getInt("tabh", tabH /*(int)(h/25)*/);
    tabI = sPref.getInt("tabi", tabI /*(int)(h/25)*/);
    tabBut = sPref.getInt("tabbut", tabBut /*(int)(h/30)*/);
    butMenu = sPref.getInt("butmenu", butMenu /*(int)(h/25)*/);
    butKalk = sPref.getInt("butkalk", butKalk /*(int)(h/25)*/);
    tvH = sPref.getInt("tvh", tvH /*(int)(h/25)*/);
    tvI = sPref.getInt("tvi", tvI /*(int)(h/25)*/);
    seek = sPref.getInt("seek", 50);
    usr = sPref.getString("usr", "");
    red1 = sPref.getInt("red1", 50);
    red2 = sPref.getInt("red2", 50);
    green1 = sPref.getInt("green1", 50);
    green2 = sPref.getInt("green2", 50);
    blue1 = sPref.getInt("blue1", 50);
    blue2 = sPref.getInt("blue2", 50);
    postlitr = (byte)sPref.getInt("postlitr", 1);
    num_id = sPref.getInt("num_id", num_id);
    day = sPref.getInt("day", day);
    if (day!=Calendar.getInstance().get(Calendar.DATE))
    {
    	num_id=1;
    	day=Calendar.getInstance().get(Calendar.DATE);
    }
    //Toast.makeText(this, "настройки загружены", Toast.LENGTH_SHORT).show();
  }
  
  void makeDialog(int accs) {
	  final int acc = accs;
		DialogScreen getkol = new DialogScreen(MainActivity.this,MainActivity.this,accs)
		 .setDialogScreenListener(new DialogListener() {
			@Override
			public void OnSelectedKol(double k) {
				//Log.d("MyLog", "acc = "+acc);
				//Log.d("MyLog", "k = "+k);
				if (acc==-2) 
				{	Intent intent;
					if (k==1)
						switch (but_menu) {
						   case R.id.btnProd:
							   intent = new Intent(MainActivity.this, UserActivity.class);
							   startActivity(intent);
						     break;
						   case R.id.btnPrixod:
							   intent = new Intent(MainActivity.this, PrixodActivity.class);
							   startActivity(intent);
						     break;
						   case R.id.btnOstat:
							   intent = new Intent(MainActivity.this, OstatActivity.class);
							   startActivity(intent);
						     break;
						   case R.id.btnAbout:
							   intent = new Intent(MainActivity.this, SettingAllActivity.class);
							   startActivity(intent);
							   break;
						   case R.id.btnSettingSpr:
							   intent = new Intent(MainActivity.this, SprActivity.class);
							   startActivity(intent);
							   break;
						   case R.id.btnKassa:
							   intent = new Intent(MainActivity.this, OtchetActivity.class);
							   startActivity(intent);
							   break;
						   }
				} 
				
				else
				if (k!=1) MainActivity.this.finish();
				/*else
				{	Cursor c = MainActivity.db.getRawData ("select count(*) c from user where upper(trim(name))=upper(trim('"+lgn.getText().toString()+"')) and password='"+psw.getText().toString()+"'"+s_adm,null);
            	int tmp=0;
            	if (c.moveToFirst()) { 
                    do {tmp=c.getInt(c.getColumnIndex("c"));
                    } while (c.moveToNext());
                  }; 
                c.close();
					access=;
				}*/
			}
		}) ;getkol.show();
	}  
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
        
        btnProd = (Button) findViewById(R.id.btnProd);
        btnProd.setOnClickListener(this);
        
        btnPrixod = (Button) findViewById(R.id.btnPrixod);
        btnPrixod.setOnClickListener(this);
        
        btnRasxod = (Button) findViewById(R.id.btnRasxod);
        btnRasxod.setOnClickListener(this);
        
        btnOstat = (Button) findViewById(R.id.btnOstat);
        btnOstat.setOnClickListener(this);
        
        btnKassa = (Button) findViewById(R.id.btnKassa);
        btnKassa.setOnClickListener(this);
        
        btnSetting = (Button) findViewById(R.id.btnSettingSpr);
        btnSetting.setOnClickListener(this);
        
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);
     // открываем подключение к БД
        db = new DB(this);
        db.open();
        
        Display display = getWindowManager().getDefaultDisplay();
        //DisplayMetrics metricsB = new DisplayMetrics();
        //display.getMetrics(metricsB);
        //display_h=metricsB.heightPixels; display_w=metricsB.widthPixels;
        scale = getResources().getDisplayMetrics().density;
        w = display.getWidth();  // deprecated, но работает везде
        h = display.getHeight();  // deprecated, но работает 
        loadSetting();
        MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.main_ll));
        makeDialog(-1);
        /*
        int density= getResources().getDisplayMetrics().densityDpi;

        switch(density)
        {
        case DisplayMetrics.DENSITY_LOW:
           Toast.makeText(this, "LDPI", Toast.LENGTH_SHORT).show();
            break;
        case DisplayMetrics.DENSITY_MEDIUM:
             Toast.makeText(this, "MDPI", Toast.LENGTH_SHORT).show();
            break;
        case DisplayMetrics.DENSITY_HIGH:
            Toast.makeText(this, "HDPI", Toast.LENGTH_SHORT).show();
            break;
        case DisplayMetrics.DENSITY_XHIGH:
             Toast.makeText(this, "XHDPI", Toast.LENGTH_SHORT).show();
            break;
        }
        
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                Toast.makeText(this, "Large screen",Toast.LENGTH_LONG).show();
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                Toast.makeText(this, "Normal screen",Toast.LENGTH_LONG).show();
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                Toast.makeText(this, "Small screen",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Screen size is neither large, normal or small" , Toast.LENGTH_LONG).show();
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //nt id = item.getItemId();
        //if (id == R.id.action_settings) {
          //  return true;
        //}
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
      super.onRestart();
      //Log.d(TAG, "MainActivity: onRestart()");
    }
   
    @Override
    protected void onStart() {
      super.onStart();
      //Log.d(TAG, "MainActivity: onStart()");
    }
   
    @Override
    protected void onResume() {
      super.onResume();
      //Log.d(TAG, "MainActivity: onResume()");
    }
   
    @Override
    protected void onPause() {
      super.onPause();
      //Log.d(TAG, "MainActivity: onPause()");
    }
   
    @Override
    protected void onStop() {
      super.onStop();
      //Log.d(TAG, "MainActivity: onStop()");
    }
   
    @Override
    protected void onDestroy() {
      super.onDestroy();
      //db.close();
      saveSetting();
      //this.finishAffinity();
      //Log.d("MyLog", "MainActivity: onDestroy()");
      android.os.Process.killProcess(android.os.Process.myPid());
    }
    
	@Override
	public void onClick(View v) {
		Intent intent;
	if (v.getId() == R.id.btnRasxod)
		   {intent = new Intent(this, RasxodActivity.class);
		   startActivity(intent);}
	if (v.getId() == R.id.btnKassa)
	{intent = new Intent(this, OtchetActivity.class);
		   startActivity(intent);}
	if (v.getId() == R.id.btnExit) finish();
	if (v.getId() == R.id.btnPrixod)
	{  
		intent = new Intent(this, PrixodActivity.class);
		   startActivity(intent);
	}
	else
		
		//else
		//{ 
		/*if (access!=1)
		{but_menu=v.getId();
		makeDialog(-2);}

		else*/
		if (access==1)
		switch (v.getId()) {
		   case R.id.btnProd:
			   intent = new Intent(this, UserActivity.class);
			   startActivity(intent);
		     break;
		   
		   case R.id.btnOstat:
			   intent = new Intent(this, OstatActivity.class);
			   startActivity(intent);
		     break;
		   /*case R.id.btnRasxod:
			   intent = new Intent(this, RasxodActivity.class);
			   startActivity(intent);
			   break;*/
		   case R.id.btnAbout:
			   intent = new Intent(this, SettingAllActivity.class);
			   startActivity(intent);
			   break;
		   case R.id.btnSettingSpr:
			   /*Cursor cc = MainActivity.db.getRawData ("select count(*) c from tmc T where T.pgr=1",null);
			   if (cc.moveToFirst()) { 
			        do { Log.d("MyLog",cc.getInt(cc.getColumnIndex("c"))+ " count: tmc "+db.getAllData("tmc").getCount());
			        } while (cc.moveToNext());
			      };*/
			   intent = new Intent(this, SprActivity.class);
			   startActivity(intent);
			   break;
		   /*case R.id.btnKassa:
			   intent = new Intent(this, OtchetActivity.class);
			   startActivity(intent);
			   break;*/
			   //db.delRec("tmc", 6);
			//   break;
		  /* case R.id.btnExit:
		     finish();
		     break;*/
		   }
		
		//}
	}
}