package luce.birra;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class MainActivity extends Activity implements OnClickListener {
Button btnExit, btnProd, btnOstat, btnPrixod, btnRasxod, btnKassa, btnSetting;
static DB db;

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

static int getIntData(String dat){
	try{
		return Integer.parseInt(dat.substring(6, 8).concat(dat.substring(3, 5)).concat(dat.substring(0, 2))) ;
		}catch(ParseException ex){
		    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
			//ex.printStackTrace();
			return 0;
		}
}

static int getIntDataTime(String dat){
	try{
		return Integer.parseInt(dat.substring(6, 8).concat(dat.substring(3, 5)).concat(dat.substring(0, 2)).concat(dat.substring(9, 11)).concat(dat.substring(12, 14))) ;
		}catch(ParseException ex){
		    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
			//ex.printStackTrace();
			return 0;
		}
}
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
        
        btnSetting = (Button) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(this);
     // открываем подключение к БД
        db = new DB(this);
        db.open();
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
      //Log.d(TAG, "MainActivity: onDestroy()");
    }
    
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		   case R.id.btnProd:
			   intent = new Intent(this, ProdActivity.class);
			   startActivity(intent);
		     break;
		   case R.id.btnPrixod:
			   intent = new Intent(this, PrixodActivity.class);
			   startActivity(intent);
		     break;
		   case R.id.btnOstat:
			   intent = new Intent(this, OstatActivity.class);
			   startActivity(intent);
		     break;
		   case R.id.btnRasxod:
			   intent = new Intent(this, RasxodActivity.class);
			   startActivity(intent);
			   break;
		   case R.id.btnKassa:
			   intent = new Intent(this, OborotkaActivity.class);
			   startActivity(intent);
			   //Log.d("MyLog", "MainActivity: tmc "+db.getAllData("tmc").getCount());
			   /////////////////////////////////////////////////////////////////////////////////////////////////
			  /* File file   = null;
			   String columnString =   "\"PersonName\",\"Gender\",\"Street1\",\"postOffice\",\"Age\"";
			   String dataString   =   "\"" + "svetka" +"\",\"" + "dona" + "\",\"" + "kievskay" + "\",\"" + "office"+ "\",\"" + "36.8" + "\"";
			   String combinedString = columnString + "\n" + dataString;
			   File root   = Environment.getExternalStorageDirectory();
			   Uri u1  =   null;
			   
			   if (root.canWrite()){
			       File dir    =   new File (root.getAbsolutePath() + "/PersonData");
			        dir.mkdirs();
			        file   =   new File(dir, "Data.csv");
			        FileOutputStream out   =   null;
			       try {
			           out = new FileOutputStream(file);
			           } catch (FileNotFoundException e) {
			               e.printStackTrace();
			           }
			           try {
			               out.write(combinedString.getBytes());
			           } catch (IOException e) {
			               e.printStackTrace();
			           }
			           try {
			               out.close();
			           } catch (IOException e) {
			               e.printStackTrace();
			           }
			       }
			   
			   //Uri u1  =   null;
			   u1  =   Uri.fromFile(file);

			   Intent sendIntent = new Intent(Intent.ACTION_SEND);
			   sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Person Details");
			   sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
			   sendIntent.setType("text/html");
			   startActivity(sendIntent);
		*/
			   //////////////////////////////////////////////////////////////////////////////////////////////////////
			   //db.delRec("tmc", 6);
			   break;
		   case R.id.btnExit:
		     finish();
		     break;
		   }
	}
}