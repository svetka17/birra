package luce.birra;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogScreen extends AlertDialog.Builder {
	
	//public static final int get_kol = 1; // �������������� ���������� ����
	//public static final int get_data = 2;
	//public static final int IDD_RATE = 3;
	
	String strDialog = "";
	static TextView tvDKol;
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
			   return "���� Excel";
		   case R.id.tvOtherKol_:
			   return "������� ���/���������� ������";
		   case R.id.etCheckNal:
			   return "������� ����� ��������";
		   case R.id.comboSkidkaSum:  
			   return "������� % ������ �� �������";
		   case R.id.etCheckSkidka:
			   return "������� ����� ����� ������ �� ���� ���";
		   case R.id.etCheckSkidkaPer:
			   return "������� ����� % ������ �� ��������� �� ���� ���";
		   case R.id.cb_Kol_Ostat:
			   return "������� ���������� ����������� �������";
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
            setPositiveButton("�������", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("���������", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					listener.OnSelectedKol(2);
				}
			})
			.setNeutralButton("��������� ������", new DialogInterface.OnClickListener() {
				
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
			.setTitle("���� � EXCEL");
            break;
		case 1: 
			
            setPositiveButton("���������", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                	listener.OnSelectedKol(1);
                }
            })
            .setNegativeButton("��������", new DialogInterface.OnClickListener() {
				
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
			.setTitle("�������� �����������");
            break;
		default:
        LinearLayout Dview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.other_kol, null);//inflater.inflate(R.layout.other_kol, null);
        strDialog="";
	       MainActivity.setSizeFontKalk((ViewGroup) Dview);
	       // ������������� ��, ��� ���������� ���� �������
	       
	       // ������� TexView ��� ����������� ���-��
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
                .setPositiveButton("������", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	//kol=MainActivity.StrToFloat(tvDKol.getText().toString());
                    	listener.OnSelectedKol(MainActivity.StrToFloat(tvDKol.getText().toString()));
                    }
                })
                .setNegativeButton("������", new DialogInterface.OnClickListener() {
					
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
