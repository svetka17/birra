package luce.birra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class DialogScreen extends AlertDialog.Builder {
	
	//public static final int get_kol = 1; // »‰ÂÌÚËÙËÍ‡ÚÓ˚ ‰Ë‡ÎÓ„Ó‚˚ı ÓÍÓÌ
	//public static final int get_data = 2;
	//public static final int IDD_RATE = 3;
	
	String strDialog = "";
	//static TextView tvDKol;
	//String regexp_numb = "\\-?\\d+(\\.\\d{0,})?";
	String regexp_numb = "\\-?\\d+(\\.\\d{0,3})?";
	//float kol;
	private DialogListener listener;
//	private DialogListenerData listenerData;
	
	public interface DialogListener {
        public void OnSelectedKol(double kolichestvo);
        //public void OnSelectedData(String dat);
    }
	
	   String dialogNumTitle(int tvI) {
		   switch (tvI) {
		   case 0:
			   return "‘‡ÈÎ Excel";
		   case 3:
			   return "¬Â‚‰ËÚÂ ÍÓÎË˜ÂÒÚ‚Ó ·‡Í‡";
		   case 2:
			   return "¬Â‚‰ËÚÂ ÍÓÎË˜ÂÒÚ‚Ó ÔÂÂÏÂ˘ÂÌËˇ";
		   case R.id.tvOtherKol_:
			   return "¬‚Â‰ËÚÂ ‚ÂÒ/ÍÓÎË˜ÂÒÚ‚Ó ÚÓ‚‡‡";
		   case R.id.etCheckNal:
			   return "¬‚Â‰ËÚÂ ÒÛÏÏÛ Ì‡ÎË˜Ì˚ı";
		   case R.id.comboSkidkaSum:  
			   return "¬‚Â‰ËÚÂ % ÒÍË‰ÍË ÔÓ ÔÓÁËˆËË";
		   case R.id.etCheckSkidka:
			   return "¬‚Â‰ËÚÂ Ó·˘Û˛ ÒÛÏÏÛ ÒÍË‰ÍË Ì‡ ‚ÂÒ¸ ˜ÂÍ";
		   case R.id.etCheckSkidkaPer:
			   return "¬‚Â‰ËÚÂ Ó·˘ËÈ % ÒÍË‰ÍË ÔÓ ÛÏÓÎ˜‡ÌË˛ Ì‡ ‚ÂÒ¸ ˜ÂÍ";
		   case R.id.cb_Kol_Ostat:
			   return "¬‚Â‰ËÚÂ ÍÓÎË˜ÂÒÚ‚Ó Ô‡‚ËÎ¸ÌÓ„Ó ÓÒÚ‡ÚÍ‡";
		   case R.id.cb_Kol_Price:
			   return "¬‚Â‰ËÚÂ Ô‡‚ËÎ¸ÌÛ˛ ˆÂÌÛ";
		   case R.id.tvKolReal_Inv:
			   return "¬‚Â‰ËÚÂ Â‡Î¸ÌÓÂ ÍÓÎË˜ÂÒÚ‚Ó ÓÒÚ‡ÚÍ‡";
		   default: return "¬‚Â‰ËÚÂ ÍÓÎË˜ÂÒÚ‚Ó";
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
            setPositiveButton("√Œ“Œ¬Œ", new DialogInterface.OnClickListener() {
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
                        MainActivity.access=c.getInt(c.getColumnIndex("c"));
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
            .setNegativeButton("Œ“Ã≈Õ¿", new DialogInterface.OnClickListener() {
				
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
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(2);					
				}
			})
			.setTitle("¬¬≈ƒ»“≈ »Ãﬂ œŒÀ‹«Œ¬¿“≈Àﬂ » œ¿–ŒÀ‹");
            break;
        case -3: 
        	final LinearLayout LLview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.color_picker, null);
    	       //MainActivity.setSizeFontMain((ViewGroup) LLview);
    	       final SeekBar sbr = (SeekBar) LLview.findViewById(R.id.sbRed1);
    	       final SeekBar sbg = (SeekBar) LLview.findViewById(R.id.sbGreen1);
    	       final SeekBar sbb = (SeekBar) LLview.findViewById(R.id.sbBlue1);
    	       //final int tit = titul;
    	       sbr.setProgress(MainActivity.red1);
    	       sbg.setProgress(MainActivity.green1);
    	       sbb.setProgress(MainActivity.blue1);
    	       LLview.setBackgroundColor(0xff000000 + sbr.getProgress() * 0x10000 + sbg.getProgress() * 0x100 + sbb.getProgress());
    	       sbr.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {}
				@Override
				public void onStartTrackingTouch(SeekBar arg0) {}
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					LLview.setBackgroundColor(0xff000000 + sbr.getProgress() * 0x10000 + sbg.getProgress() * 0x100 + sbb.getProgress());
				}
			});
    	       sbg.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
   				@Override
   				public void onStopTrackingTouch(SeekBar arg0) {}
   				@Override
   				public void onStartTrackingTouch(SeekBar arg0) {}
   				@Override
   				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
   					LLview.setBackgroundColor(0xff000000 + sbr.getProgress() * 0x10000 + sbg.getProgress() * 0x100 + sbb.getProgress());
   				}
   			});
    	       sbb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
   				@Override
   				public void onStopTrackingTouch(SeekBar arg0) {}
   				@Override
   				public void onStartTrackingTouch(SeekBar arg0) {}
   				@Override
   				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
   					LLview.setBackgroundColor(0xff000000 + sbr.getProgress() * 0x10000 + sbg.getProgress() * 0x100 + sbb.getProgress());
   				}
   			});
    	       
                    setView(LLview).
            setPositiveButton("√Œ“Œ¬Œ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//Log.d("MyLog", "pass = "+psw.getText());
                    //if (tmp!=-1) listener.OnSelectedKol(1); else listener.OnSelectedKol(2);
                	MainActivity.red1=sbr.getProgress();
                	MainActivity.green1=sbg.getProgress();
                	MainActivity.blue1=sbb.getProgress();
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("Œ“Ã≈Õ¿", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//listener.OnSelectedKol(2);
				}
			})
            //.setCancelable(false)
			/*.setOnDismissListener(new DialogInterface.OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					//Log.d("MyLog", "dismiss");
					listener.OnSelectedKol(2);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(2);					
				}
			})
			.setTitle("¬€¡»–¿…“≈ ÷¬≈“");
            break;
        case -4: 
        	final LinearLayout DTview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.date_time, null);
    	       //MainActivity.setSizeFontMain((ViewGroup) LLview);
    	       final DatePicker dp = (DatePicker) DTview.findViewById(R.id.datePickerD);
    	       final TimePicker tp = (TimePicker) DTview.findViewById(R.id.timePickerD);
    	       tp.setIs24HourView(true);
    	       //final int tit = titul;
    	      
    	       
                    setView(DTview).
            setPositiveButton("√Œ“Œ¬Œ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//Log.d("MyLog", "hh = "+tp.getCurrentHour()+" mm="+tp.getCurrentMinute());
                    //if (tmp!=-1) listener.OnSelectedKol(1); else listener.OnSelectedKol(2);
                	//MainActivity.red1=sbr.getProgress();
                	int tmp= (
                			(
                			((dp.getYear()%2000)*100+dp.getMonth()+1)*100+dp.getDayOfMonth())*100
                			+tp.getCurrentHour() )*100+tp.getCurrentMinute();
                	//Log.d("MyLog", "tmp = "+tmp);
                	//double t=(double)tmp;
                	//Log.d("MyLog", "t = "+t+" "+String.format("%.10g%n", t));
                	//Log.d("MyLog", "double t = "+((double)tmp)+" int "+((int)((double)tmp)));
                	listener.OnSelectedKol(tmp);
                }
            })
            .setNegativeButton("Œ“Ã≈Õ¿", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
            //.setCancelable(false)
            /*.setOnDismissListener(new DialogInterface.OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					//Log.d("MyLog", "dismiss");
					listener.OnSelectedKol(0);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(0);					
				}
			})
			.setTitle("¬€¡»–¿…“≈ ƒ¿“” » ¬–≈Ãﬂ");
            break;
        case -5:
            setPositiveButton("ƒ¿", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("Õ≈“", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
			/*.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(0);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(0);					
				}
			})
			.setTitle("”ƒ¿À»“‹?");
            break;
        case -6:
            setPositiveButton("ƒ¿", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("Õ≈“", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
			/*.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(0);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(0);					
				}
			})
			.setTitle("—ƒ≈À¿“‹ ÕŒ¬€… œ–»’Œƒ ›“Œ√Œ “Œ¬¿–¿?");
            break;
        case -7:
            setPositiveButton("ƒ¿", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("Õ≈“", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
			/*.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(-1);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(-1);					
				}
			})
			.setTitle("“Œ¬¿– ≈—“‹ ¬ Õ¿À»◊»»?");
            break;   
        case -8:
            setPositiveButton("ƒ¿", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("Õ≈“", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
			/*.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(-1);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(-1);					
				}
			})
			.setTitle("œ≈–≈…“» Õ¿ ÕŒ¬”ﬁ  ≈√”?");
            break;
        case -9:
            setPositiveButton("ƒ¿", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("Õ≈“", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
			/*.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(-1);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(-1);					
				}
			})
			.setTitle("¬ Àﬁ◊»“‹ — œŒÃ≈“ Œ… ¡–¿ ?");
            break;
        case -10:
            setPositiveButton("ƒ¿", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("Õ≈“", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
			/*.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(-1);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(-1);					
				}
			})
			.setTitle("¬ Àﬁ◊»“‹ œ≈–≈Ã≈Ÿ≈Õ»≈ ¬ ƒ–”√Œ… Ã¿√¿«»Õ?");
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
            setPositiveButton("Œ“ –€“‹", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("—Œ’–¿Õ»“‹", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(2);
				}
			})
			.setNeutralButton("Œ“œ–¿¬»“‹ œŒ◊“Œ…", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(3);
				}
			})
			/*.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(0);						
				}
			})*/
			.setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(0);					
				}
			})
			.setTitle("‘¿…À ¬ EXCEL");
            break;
		case 1: 
			
            setPositiveButton("«¿√–”«»“‹", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("Œ“Ã≈Õ»“‹", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(0);
				}
			})
			/*.setOnDismissListener(new OnDismissListener() {	
				@Override
				public void onDismiss(DialogInterface arg0) {
					listener.OnSelectedKol(0);						
				}
			})*/
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					listener.OnSelectedKol(0);					
				}
			})
			.setTitle("«¿√–”« ¿ —œ–¿¬Œ◊Õ» ¿");
            break;
		default:
        LinearLayout Dview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.other_kol, null);//inflater.inflate(R.layout.other_kol, null);
        strDialog="";
	       MainActivity.setSizeFontKalk((ViewGroup) Dview);
	       // ÛÒÚ‡Ì‡‚ÎË‚‡ÂÏ ÂÂ, Í‡Í ÒÓ‰ÂÊËÏÓÂ ÚÂÎ‡ ‰Ë‡ÎÓ„‡
	       
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
	        	   String s=""; s.trim();
	           	tvDKol.setText((tvDKol.getText().length()==1||tvDKol.getText().length()==0||tvDKol.getText().equals("")||tvDKol.getText().equals("0"))?"":
	           	tvDKol.getText().subSequence(0, tvDKol.getText().length()-1) );
	           	strDialog=tvDKol.getText().toString();
	           }
	         });
        
                setView(Dview)
                .setPositiveButton("√Œ“Œ¬Œ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                    	listener.OnSelectedKol((double)MainActivity.StrToFloat(tvDKol.getText().toString()));
                    }
                })
                .setNegativeButton("Œ“Ã≈Õ¿", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						listener.OnSelectedKol(0);
					}
				})
				/*.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface arg0) {
						listener.OnSelectedKol(0);						
					}
				})*/
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
    				
    				@Override
    				public void onCancel(DialogInterface dialog) {
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
