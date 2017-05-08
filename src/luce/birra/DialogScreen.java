package luce.birra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class DialogScreen extends AlertDialog.Builder {
	
	//public static final int get_kol = 1; // Идентификаторы диалоговых окон
	//public static final int get_data = 2;
	//public static final int IDD_RATE = 3;
	
	String strDialog = "";
	//static TextView tvDKol;
	String regexp_numb = "\\-?\\d+(\\.\\d{0,})?";
	//float kol;
	private DialogListener listener;
//	private DialogListenerData listenerData;
	
	public interface DialogListener {
        public void OnSelectedKol(float kolichestvo);
        //public void OnSelectedData(String dat);
    }
	
	   String dialogNumTitle(int tvI) {
		   switch (tvI) {
		   case 0:
			   return "Файл Excel";
		   case R.id.tvOtherKol_:
			   return "Введите вес/количество товара";
		   case R.id.etCheckNal:
			   return "Введите сумму наличных";
		   case R.id.comboSkidkaSum:  
			   return "Введите % скидки по позиции";
		   case R.id.etCheckSkidka:
			   return "Введите общую сумму скидки на весь чек";
		   case R.id.etCheckSkidkaPer:
			   return "Введите общий % скидки по умолчанию на весь чек";
		   case R.id.cb_Kol_Ostat:
			   return "Введите количество правильного остатка";
		   default: return "";
		   }
	   }
	   
	   public DialogScreen setDialogScreenListener(DialogListener l) {
	        this.listener = l;
	        return this;
	    }
	   

	
	public DialogScreen(Context context, Activity activity, int titul) {
        super(context);
        switch(titul) {
        case -2:
        case -1: 
        	TableLayout Lview = (TableLayout) activity.getLayoutInflater().inflate(R.layout.login_app, null);
    	       MainActivity.setSizeFontMain((ViewGroup) Lview);
    	       final EditText lgn = (EditText) Lview.findViewById(R.id.etUserName);
    	       final EditText psw = (EditText) Lview.findViewById(R.id.etUserPassword);
    	       final int tit = titul;
    	       lgn.setText(MainActivity.usr);
    	       
                    setView(Lview).
            setPositiveButton("ГОТОВО", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//Log.d("MyLog", "pass = "+psw.getText());
                	String s_adm="";
                	if (tit==-2) s_adm=" and admin=1 ";
                	if (psw.getText().toString().equals(MainActivity.my_pass)) {MainActivity.access=1; listener.OnSelectedKol(1);}
                	else
                	{
                		 /*c = MainActivity.db.getRawData ("select admin c, name from user where upper(trim(name))=upper(trim('"+lgn.getText().toString()+"')) and password='"+psw.getText().toString()+"'",null);
                    	if (c.moveToFirst()) { 
                            do {if (c.getInt(c.getColumnIndex("c"))==1) {MainActivity.access=1; }
                            } while (c.moveToNext());
                          }; 
                        c.close(); */
                		//c = MainActivity.db.getRawData ("select count(*) c from user where upper(trim(name))=upper(trim('"+lgn.getText().toString()+"')) and password='"+psw.getText().toString()+"'"+s_adm,null);
                		//String ss = "select _id, admin c, name from user where upper(trim(name))=upper(trim('"+lgn.getText().toString()+"')) and password='"+psw.getText().toString()+"'"+s_adm;
                		Cursor c = MainActivity.db.getRawData ("select _id, admin c, name from user where trim(name) = trim('"+lgn.getText().toString()+"') and trim(password) = trim('"+psw.getText().toString()+"')"+s_adm,null);
                		//Log.d("MyLog", "cursor "+ss);
                	int tmp=-1;
                	if (c.moveToFirst()) { 
                        do {
                        	//Log.d("MyLog", "cursor "+c.getInt(c.getColumnIndex("_id")));
                        	tmp=c.getInt(c.getColumnIndex("_id")); MainActivity.usr=c.getString(c.getColumnIndex("name"));
                        MainActivity.access=(byte)c.getInt(c.getColumnIndex("c"));
                        } while (c.moveToNext());
                      }; 
                    c.close();
                    if (tmp!=-1) listener.OnSelectedKol(1); else listener.OnSelectedKol(2);
                	/*if (tmp==1)
                    {MainActivity.usr=lgn.getText().toString(); listener.OnSelectedKol(1);}
                	else
                	{
                		c = MainActivity.db.getRawData ("select count(*) c from user where admin=1 and password='"+psw.getText().toString()+"'",null);
                    	tmp=0;
                    	if (c.moveToFirst()) { 
                            do {tmp=c.getInt(c.getColumnIndex("c"));
                            } while (c.moveToNext());
                          }; 
                        c.close();
                        if (tmp>0) {MainActivity.usr=lgn.getText().toString(); MainActivity.access=1; listener.OnSelectedKol(1);} else listener.OnSelectedKol(2);
                	}*/
                }	
                }
            })
            .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(2);
				}
			})
            .setCancelable(false)
			/*.setOnDismissListener(new DialogInterface.OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					//Log.d("MyLog", "dismiss");
					listener.OnSelectedKol(2);						
				}
			})*/
			.setTitle("ВВЕДИТЕ ИМЯ ПОЛЬЗОВАТЕЛЯ И ПАРОЛЬ");
            break;
		case 0: 
			/*OnDateSetListener myCallBack = new OnDateSetListener() {
			    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
			    {
                	//listener.OnSelectedData(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
			    }
			    };
			DatePickerDialog tpd = new DatePickerDialog(activity, myCallBack, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			*/
			//setView(Dview).
            setPositiveButton("ОТКРЫТЬ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("СОХРАНИТЬ", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(2);
				}
			})
			.setNeutralButton("ОТПРАВИТЬ ПОЧТОЙ", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(3);
				}
			})
			.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(0);						
				}
			})
			.setTitle("ФАЙЛ В EXCEL");
            break;
		case 1: 
			
            setPositiveButton("ЗАГРУЗИТЬ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("ОТМЕНИТЬ", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
			.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(0);						
				}
			})
			.setTitle("ЗАГРУЗКА СПРАВОЧНИКА");
            break;
		default:
        LinearLayout Dview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.other_kol, null);//inflater.inflate(R.layout.other_kol, null);
        strDialog="";
	       MainActivity.setSizeFontKalk((ViewGroup) Dview);
	       // устанавливаем ее, как содержимое тела диалога
	       
	       final TextView tvDKol;
	       tvDKol = (TextView) Dview.findViewById(R.id.tvOtherKolD);
	       tvDKol.setText("");
	    	//tvDKol.setText(strDialog);
	       Button bt0 = (Button) Dview.findViewById(R.id.btn000);
	       Button bt1 = (Button) Dview.findViewById(R.id.btn111);
	       Button bt2 = (Button) Dview.findViewById(R.id.btn222);
	       Button bt3 = (Button) Dview.findViewById(R.id.btn333);
	       Button bt4 = (Button) Dview.findViewById(R.id.btn444);
	       Button bt5 = (Button) Dview.findViewById(R.id.btn555);
	       Button bt6 = (Button) Dview.findViewById(R.id.btn666);
	       Button bt7 = (Button) Dview.findViewById(R.id.btn777);
	       Button bt8 = (Button) Dview.findViewById(R.id.btn888);
	       Button bt9 = (Button) Dview.findViewById(R.id.btn999);
	       Button btComa = (Button) Dview.findViewById(R.id.btnComaCC);
	       Button btXD = (Button) Dview.findViewById(R.id.btnXXX);
	       Button btDD = (Button) Dview.findViewById(R.id.btnDDD);
	       TextView titleD = (TextView) Dview.findViewById(R.id.tvOtherKolDTitle);
	       titleD.setText(dialogNumTitle(titul));

	       OnClickListener listenerDKol = new OnClickListener() {
	     	  @Override
	     	  public void onClick(View v) {
	     			Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
	       			Matcher matcher = pattern.matcher(strDialog.concat(((Button) v).getText().toString()));
	       			if (matcher.matches()) {strDialog=strDialog.concat(((Button) v).getText().toString()); tvDKol.setText(strDialog); }
	       			}
	     	 };
	     	bt0.setOnClickListener(listenerDKol);
	     	bt2.setOnClickListener(listenerDKol);
	     	bt3.setOnClickListener(listenerDKol);
	     	bt4.setOnClickListener(listenerDKol);
	     	bt5.setOnClickListener(listenerDKol);
	     	bt6.setOnClickListener(listenerDKol);
	     	bt7.setOnClickListener(listenerDKol);
	     	bt8.setOnClickListener(listenerDKol);
	     	bt9.setOnClickListener(listenerDKol);
	     	bt1.setOnClickListener(listenerDKol);
	       /*bt0.setOnClickListener(new OnClickListener() {
	           public void onClick(View v) {
	   			Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
	   			Matcher matcher = pattern.matcher(strDialog.concat("0"));
	   			if (matcher.matches()) {strDialog=strDialog.concat("0"); tvDKol.setText(strDialog); }
	   			//Log.d("MyLog", ""+strDialog);
	   			//tvDKol.setText(matcher.matches()?matcher.group():strDialog);
	   			
	        	  // strDialog=strDialog.concat("0");
	           	//tvDKol.setText(""+strDialog);
	           }
	         });*/
	   	
	   	btComa.setOnClickListener(new OnClickListener() {
	           public void onClick(View v) {
	        	   Pattern pattern = Pattern.compile(regexp_numb, Pattern.CASE_INSENSITIVE);
	      			Matcher matcher = pattern.matcher(strDialog.concat("."));
	      			if (matcher.matches()) {strDialog=strDialog.concat("."); tvDKol.setText(strDialog); }
	           }
	         });
	   	
	   	btXD.setOnClickListener(new OnClickListener() {
	           public void onClick(View v) {
	        	   strDialog="";
	           	tvDKol.setText("");
	           }
	         });
	   	
	   	btDD.setOnClickListener(new OnClickListener() {
	           public void onClick(View v) {
	           	tvDKol.setText((tvDKol.getText().length()==0||tvDKol.getText().equals("")||tvDKol.getText().equals("0"))?"":
	           		tvDKol.getText().subSequence(0, tvDKol.getText().length()-1) );
	           }
	         });
        
                setView(Dview)
                .setPositiveButton("ГОТОВО", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                    	listener.OnSelectedKol(MainActivity.StrToFloat(tvDKol.getText().toString()));
                    }
                })
                .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						listener.OnSelectedKol(0);
					}
				})
				.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface arg0) {
						listener.OnSelectedKol(0);						
					}
				})
				.setTitle(dialogNumTitle(titul));
                
        }
        
	}
//////////////////////////////////////////////////////	
	
	//public static AlertDialog getDialog(Activity activity, int ID, String tit) {
		//AlertDialog.Builder builder = new AlertDialog.Builder(activity);			
	//}
}
