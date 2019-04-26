package luce.birra;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

public class AdapterLV extends SimpleCursorAdapter {
	int bD; int bU; byte nT; String namT; Context contxt; 
	TextView tv;
	CheckBox cb;
	//Dialog dialogg;
	//public int[] wH;
	//public int wH;
	//byte countH;
	//int i;
	//int[] r;
	//ViewTreeObserver viewTreeObserver;
	//LinearLayout.LayoutParams p;
	//public enum Tables  {tmc, tmc_pgr, prixod, rasxod, ostat, klient, postav};
	//public enum Tables  {tmc-1, tmc_pgr-2, prixod-3, rasxod-4, ostat-5, klient-6, postav-7};
	//private static final String [] TableN = {"tmc","tmc_pgr","prixod","rasxod","ostat","klient","postav"};
public AdapterLV(/*int[] r,*/ int bD, int bU, byte nT, Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
    super(context, layout, c, from, to, flags);
    //this.wH=new int[r.length];
    //this.r=r;
    this.bD=bD;
    this.bU=bU;
    this.nT=nT;
    this.contxt=context;
   // this.wH=0;
    switch(nT) {
    case 0: namT=""; break;
    //case 10: namT=""; break;
    case 1: namT="tmc"; break;
    case 2: namT="tmc_pgr"; break;
    case 3: namT="prixod"; break;
    case 4: namT="rasxod"; break;
    case 5: namT="ostat"; break;
    case 6: namT="klient"; break;
    case 7: namT="postav"; break;
    case 8: namT="karta_klient"; break;
    case 9: namT="user"; break;
    case 10: namT=""; break;
    case 11: namT="tmc_price"; break;
    case 12: namT=""; break;
    case 13: namT="rasxod_del"; break;
    case 14: namT="invent_head"; break;
    case 15: namT="invent"; break;
    case 16: namT="rasxod"; break;
    case 17: namT="ostat"; break;
    }
}

private CambiareListener listenerFlag;

public interface CambiareListener {
    public void OnCambiare(byte flag, long id);
}

public AdapterLV setCamdiareListener(CambiareListener listener) {
    this.listenerFlag = listener;
    return this;
}

@Override
public void bindView(View view, Context context, final Cursor cursor) {
	MainActivity.setSizeFontItem((TableRow)/*findViewById(R.id.oborotka_item_tr)*/view);
	switch (nT) {
	case 0:
//{"_id", "id_tmc","name","ted","kol_n","sum_n","price_n","kol_pri","sum_pri","price_pri","kol_ras","sum_ras","price_ras","kol_k","sum_k","price_k"}		
//{R.id.tv_Id_Ostat_oborotka, R.id., R.id.tv_Name_Oborotka,R.id.tv_Ted_Oborotka, R.id.tv_Kol_Nach_Oborotka,R.id.tv_Sum_Nach_Oborotka,R.id.tv_Price_Nach_Oborotka,
	//	R.id.tv_Kol_Pri_Oborotka,R.id.tv_Sum_Pri_Oborotka,R.id.tv_Price_Pri_Oborotka,
		//R.id.tv_Kol_Ras_Oborotka,R.id.tv_Sum_Ras_Oborotka,R.id.tv_Price_Ras_Oborotka,
		//R.id.tv_Kol_Kon_Oborotka,R.id.tv_Sum_Kon_Oborotka,R.id.tv_Price_Kon_Oborotka}
		
		/*if (MainActivity.access!=1) {
			((Button) view.findViewById(bD)).setVisibility(LinearLayout.GONE); ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
		}*/
		((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
		
/*        tv = (TextView) view.findViewById(R.id.tv_Price_Pri_Oborotka);  	
        double kol=cursor.getDouble(cursor.getColumnIndex("kol_pri"));
        if (kol!=0)
        tv.setText( String.format("%.2f",cursor.getDouble(cursor.getColumnIndex("sum_pri"))/kol ) ); else tv.setText("");
        
        tv = (TextView) view.findViewById(R.id.tv_Price_Ras_Oborotka);  	
        kol=cursor.getDouble(cursor.getColumnIndex("kol_ras"));
        if (kol!=0)
        tv.setText( String.format("%.2f",cursor.getDouble(cursor.getColumnIndex("sum_ras"))/kol ) ); else tv.setText("");

        tv = (TextView) view.findViewById(R.id.tv_Price_Kon_Oborotka);  	
        kol=cursor.getDouble(cursor.getColumnIndex("kol_k"));
        if (kol!=0)
        tv.setText( String.format("%.2f",cursor.getDouble(cursor.getColumnIndex("sum_k"))/kol ) ); else tv.setText("");
*/
        tv = (TextView) view.findViewById(R.id.tv_Sum_Nach_Oborotka);  	
        tv.setText( String.format("%.2f",cursor.getDouble(cursor.getColumnIndex("sum_k"))+cursor.getDouble(cursor.getColumnIndex("sum_ras"))-cursor.getDouble(cursor.getColumnIndex("sum_pri")) ) );
        //tv.setText(String.valueOf(MainActivity.round2(cursor.getDouble(cursor.getColumnIndex("sum_k"))+cursor.getDouble(cursor.getColumnIndex("sum_ras"))-cursor.getDouble(cursor.getColumnIndex("sum_pri")) )) );
        double s=MainActivity.StrToFloat(tv.getText().toString());
        
        tv = (TextView) view.findViewById(R.id.tv_Kol_Nach_Oborotka);  	
        tv.setText( String.format("%.3f",cursor.getDouble(cursor.getColumnIndex("kol_k"))+cursor.getDouble(cursor.getColumnIndex("kol_ras"))-cursor.getDouble(cursor.getColumnIndex("kol_pri")) ) );
        double kol=MainActivity.StrToFloat(tv.getText().toString());
        tv = (TextView) view.findViewById(R.id.tv_Price_Nach_Oborotka);
        
        /*String s=((TextView) view.findViewById(R.id.tv_Kol_Nach_Oborotka)).getText().toString().replace(",", ".");
        //Log.d("MyLog","kol="+s );
        //s=((TextView) view.findViewById(R.id.tv_Sum_Nach_Oborotka)).getText().toString();
        //Log.d("MyLog","sum="+s );
        if (s.equals("")||s.length()==0||s.equals("0,00")) kol=0; else
        kol=Float.parseFloat(s);//  cursor.getDouble(cursor.getColumnIndex("kol_k"));
        s=((TextView) view.findViewById(R.id.tv_Sum_Nach_Oborotka)).getText().toString().replace(",", ".");
        float sum;
        if (s.equals("")||s.length()==0||s.equals("0,00")) sum=0; else
        sum=Float.parseFloat(s);*/
        
        if (kol!=0)
        tv.setText( String.format("%.2f",s/kol ) ); else tv.setText("0");

        //for (i=0;i<r.length;i++) {
			//tv = (TextView) view.findViewById(r[i]);
//	        viewTreeObserver = tv.getViewTreeObserver(); 
//	        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
//	            @Override 
//	            public void onGlobalLayout() { 
//	                int w = tv.getWidth();
//	                if (w>0) wH=w;
//	                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//	            } 
//	        });
		//}    	
	break;

    case 1:
    	cb = (CheckBox) view.findViewById(R.id.tvVisProd_);  	
        cb.setChecked( ((cursor.getInt(cursor.getColumnIndex("vis"))==1)?true:false) );
        cb.setText(((cursor.getInt(cursor.getColumnIndex("vis"))==1)?"ДА":"НЕТ"));
        /*viewTreeObserver = cb.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = cb.getWidth();
                if (w>0) wH[1]=w;
                cb.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });*/
        
        cb = (CheckBox) view.findViewById(R.id.tvTaraOkProd_);  	
        cb.setChecked( ((cursor.getInt(cursor.getColumnIndex("ok"))==1)?true:false) );
        cb.setText(((cursor.getInt(cursor.getColumnIndex("ok"))==1)?"ТАРА":""));
        /*viewTreeObserver = cb.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = cb.getWidth();
                if (w>0) wH[4]=w;
                cb.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });
        tv = (TextView) view.findViewById(R.id.tvId_Prod_);
        viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[0]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });
        
        tv = (TextView) view.findViewById(R.id.tvName_Prod_);
        viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[2]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });
        
        tv = (TextView) view.findViewById(R.id.tvPriceProd_);
        viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[6]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });
        */
        tv = (TextView) view.findViewById(R.id.tvTaraProd_);  	
        tv.setText( ((cursor.getInt(cursor.getColumnIndex("ok"))==1)?cursor.getString(cursor.getColumnIndex("tara")):"") );
        
        tv = (TextView) view.findViewById(R.id.tvName_Pgr_Prod_);  	
        tv.setText( ((cursor.getString(cursor.getColumnIndex("namepgr")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("namepgr"))) );
        /*viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[3]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });*/
    	break;
    case 5:
    	if (MainActivity.access!=1) {((Button) view.findViewById(bD)).setVisibility(LinearLayout.GONE); ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);}
    	//((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
    	tv = (TextView) view.findViewById(R.id.tvNamePost_Ostat);  	
        tv.setText( ((cursor.getString(cursor.getColumnIndex("pname")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("pname"))) );
    	tv = (TextView) view.findViewById(R.id.tvDataIns_Ostat);  	
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );

    	break;
    case 14:
/*       
        tv = (TextView) view.findViewById(R.id.atN_InvHead); 
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("dat_n"))) );
        //tv.setText( ((cursor.getInt(cursor.getColumnIndex("dat_n"))==0)?"":MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("dat_n")))) );
        
        tv = (TextView) view.findViewById(R.id.atK_InvHead);  	
        //tv.setText( ((cursor.getInt(cursor.getColumnIndex("dat_k"))==0)?"":MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("dat_k")))) );
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("dat_k"))) );
        
tv = (TextView) view.findViewById(R.id.ataIns_InvHead);  	
tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
*/
        ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
    	break;
    case 15:
    	//if (MainActivity.access!=1) {((Button) view.findViewById(bD)).setVisibility(LinearLayout.GONE); ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);}
    	//((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
    	//tv = (TextView) view.findViewById(R.id.tvNamePost_Inv);  	
        //tv.setText( ((cursor.getString(cursor.getColumnIndex("pname")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("pname"))) );
    	//tv = (TextView) view.findViewById(R.id.tvDataIns_Inv);  	
        //tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
    	if (MainActivity.no_inv==0)
    	{((Button) view.findViewById(bD)).setVisibility(LinearLayout.GONE); ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);}
    	tv = (TextView) view.findViewById(R.id.tvDataIns_Inv);  	
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))).substring(0, 5) );
        
        tv = (TextView) view.findViewById(R.id.tvKolReal_Inv);  	
        tv.setBackgroundColor(Color.TRANSPARENT);
        
        tv = (TextView) view.findViewById(R.id.tvKol_Inv);  	
        tv.setBackgroundColor(Color.LTGRAY);
        
    	break;	
    case 2:
        //for (i=0;i<r.length;i++) {
        	/*tv = (TextView) view.findViewById(r[i]);
	        viewTreeObserver = tv.getViewTreeObserver(); 
	        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
	            @Override 
	            public void onGlobalLayout() { 
	                int w = tv.getWidth();
	                if (w>0) wH[Integer.parseInt(tv.getTag().toString())]=w;
	                Log.d("MyLog", "tag = " +Integer.parseInt(tv.getTag().toString())+" adapter.whi="+wH[Integer.parseInt(tv.getTag().toString())]);
	                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	            } 
	        });*/
			/*tv = (TextView) view.findViewById(r[0]);
	        viewTreeObserver = tv.getViewTreeObserver(); 
	        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
	            @Override 
	            public void onGlobalLayout() { 
	                int w = tv.getWidth();
	                if (w>0) wH[0]=w;
	                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	            } 
	        });
	        tv = (TextView) view.findViewById(r[1]);
	        viewTreeObserver = tv.getViewTreeObserver(); 
	        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
	            @Override 
	            public void onGlobalLayout() { 
	                int w = tv.getWidth();
	                if (w>0) wH[1]=w;
	                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	            } 
	        });*/
	        tv = (TextView) view.findViewById(R.id.tvDataInsPgr);
	        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );

		//}
        
    	break;
    case 6:        
	        tv = (TextView) view.findViewById(R.id.tv_data_chek_klient);
	        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
        
    	break;
    case 3:
    	/*tv = (TextView) view.findViewById(r[0]);
        viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[0]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });
        tv = (TextView) view.findViewById(r[1]);
        viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[1]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });*/
    	//удалять можно всем, изменять нельзя никому
    	//if (MainActivity.access!=1) {((Button) view.findViewById(bD)).setVisibility(LinearLayout.GONE); ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);}
    	((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
    	tv = (TextView) view.findViewById(R.id.tvNamePostPrixod_); 
      //  tv = (TextView) view.findViewById(r[2]);
        tv.setText( ((cursor.getString(cursor.getColumnIndex("pname")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("pname"))) );

    	/* 	
        tv = (TextView) view.findViewById(r[3]);
        viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[3]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });
        tv = (TextView) view.findViewById(r[4]);
        viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[4]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });
        tv = (TextView) view.findViewById(r[5]);
        viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[5]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });*/
        tv = (TextView) view.findViewById(R.id.tvDataInsPrixod_);
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
        /*viewTreeObserver = tv.getViewTreeObserver(); 
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() { 
                int w = tv.getWidth();
                if (w>0) wH[6]=w;
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } 
        });*/
        //tv = (TextView) view.findViewById(R.id.tvDataInsPrixod_);  	
/*
		for (i=0;i<r.length;i++) {
			tv = (TextView) view.findViewById(r[i]);
	        viewTreeObserver = tv.getViewTreeObserver(); 
	        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
	            @Override 
	            public void onGlobalLayout() { 
	                int w = tv.getWidth();
	                if (w>0) wH[i]=w;
	                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	            } 
	        });
		}*/
    	break;
    case 4:
    	((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
    	//tv = (TextView) view.findViewById(R.id.tvNamePostPrixod_);  	
        //tv.setText( ((cursor.getString(cursor.getColumnIndex("pname")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("pname"))) );
        tv = (TextView) view.findViewById(R.id.tv_DataIns_Rasxod_Hist);  	
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
		
    	break;
    case 16:
    	((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
    	//tv = (TextView) view.findViewById(R.id.tvNamePostPrixod_);  	
        //tv.setText( ((cursor.getString(cursor.getColumnIndex("pname")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("pname"))) );
        tv = (TextView) view.findViewById(R.id.tvDataIns_BrakMoveHist);  	
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
		
    	break;
    case 13:

    	//tv = (TextView) view.findViewById(R.id.tvNamePostPrixod_);  	
        //tv.setText( ((cursor.getString(cursor.getColumnIndex("pname")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("pname"))) );
        tv = (TextView) view.findViewById(R.id.tv_DataIns_Del_Hist);  	
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
        //tv.setText(String.valueOf( cursor.getInt(cursor.getColumnIndex("data_ins")) ) );
    	break;
    case 7: break;
    case 9: 
    	cb = (CheckBox) view.findViewById(R.id.tvAdminUser);  	
        cb.setChecked( ((cursor.getInt(cursor.getColumnIndex("admin"))==1)?true:false) );
        cb.setText(((cursor.getInt(cursor.getColumnIndex("admin"))==1)?"ДА":"НЕТ"));
        
        tv = (TextView) view.findViewById(R.id.tvDataInsUser);  	
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
        
        tv = (TextView) view.findViewById(R.id.tvPasswordUser);  	
        tv.setText("********");
        break;
    case 10:
		//for (i=0;i<r.length;i++) {
			//tv = (TextView) view.findViewById(R.id.tvNamePostav);
		//}
    	//if (MainActivity.access!=1) {
    		((Button) view.findViewById(bD)).setVisibility(LinearLayout.GONE); ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
    	//}
    	break;
    case 11:
    	//if (MainActivity.access!=1) {((Button) view.findViewById(bD)).setVisibility(LinearLayout.GONE); ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);}
    	tv = (TextView) view.findViewById(R.id.tvNamePost_Price);  	
        tv.setText( ((cursor.getString(cursor.getColumnIndex("pname")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("pname"))) );
    	tv = (TextView) view.findViewById(R.id.tvDataIns_Price);  	
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );

    	break;
    case 12:
    	tv = (TextView) view.findViewById(R.id.tv_DataIns_Dvig);
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );
    	break;
    case 17:
    	//if (MainActivity.access!=1) {((Button) view.findViewById(bD)).setVisibility(LinearLayout.GONE); ((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);}
    	//((Button) view.findViewById(bU)).setVisibility(LinearLayout.GONE);
    	tv = (TextView) view.findViewById(R.id.tvNamePost_Brak);  	
        tv.setText( ((cursor.getString(cursor.getColumnIndex("pname")).equals("-нет-"))?"":cursor.getString(cursor.getColumnIndex("pname"))) );
    	tv = (TextView) view.findViewById(R.id.tvDataIns_Brak);  	
        tv.setText(MainActivity.getStringDataTime(cursor.getInt(cursor.getColumnIndex("data_ins"))) );

    	break;
        }
	//Button btn = (Button) view.findViewById(R.id.btnDelPgr );
	
	((Button) view.findViewById(bD)).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parent_row = (View) ((View) v.getParent()).getParent() ;
            ListView lv = (ListView) parent_row.getParent();
            final int position = lv.getPositionForView(parent_row);
            try{
            	//if (nT!=0 && nT!=10)
            	//{
            		
            	//MainActivity.db.delRec(namT,getItemId(position));
            	listenerFlag.OnCambiare((byte)1, getItemId(position));
            	//}
                /*switch (nT) {
                case 1:
                	//context.get MyCursorLoader.forceLoad();
                	//contxt.getApplicationContext().getApplicationContext().getClassLoader(). .getSupportLoaderManager().getLoader(0).forceLoad() ;
                	break;
                case 2:
                	//((PgrActivity) contxt.getApplicationContext()).getSupportLoaderManager().getLoader(0).forceLoad() ;
                	break;
                case 3:
                	//((PrixodHistActivity) contxt.getApplicationContext()).getSupportLoaderManager().getLoader(0).forceLoad() ;
                	break;
                case 4:
                	//RasxodHistActivity.this.getApplicationContext().getSupportLoaderManager().getLoader(0).forceLoad() ;
                	break;
                case 7:
                	//if (getItemId(position)>2)
                	//((PostavActivity) contxt.getApplicationContext()).getSupportLoaderManager().getLoader(0).forceLoad() ;
                	break;
                case 5:	
                case 8:
                    break;
                    }*/
                }
                catch(Exception e){
                	throw new ClassCastException("Table not found: " + namT);
                }
            
            //Log.d("MyLog", "Dposition = " + position);
            //Log.d("MyLog", "Did = " + getItemId(position));
        }
    });
	
	
	((Button) view.findViewById(bU)).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //View parent_row = (View) v.getParent();
        	View parent_row = (View) ((View) v.getParent()).getParent() ;
            ListView lv = (ListView) parent_row.getParent();
            final int position = lv.getPositionForView(parent_row);
            //Cursor cursor = (Cursor) lv.getItemAtPosition(position);
            //String s = cursor.getString(cursor.getColumnIndexOrThrow(MainTable._TEXT));
            Intent intent;
            //String enumTable = Tables.valueOf(nT);
            try{
            	
                //Tables tables  = Tables.valueOf(nT);
                switch (/*Tables.valueOf(nT)*/nT) {
                case 2:
                	intent = new Intent(contxt, PgrEditActivity.class);
                    intent.putExtra("PgrName", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("name")));
                    intent.putExtra("PgrId", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id")));
                    contxt.startActivity(intent);
                   break;
                case 7:
                	intent = new Intent(contxt, PostavEditActivity.class);
                    intent.putExtra("PostavName", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("name")));
                    intent.putExtra("PostavId", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id")));
                    intent.putExtra("PostavPrim", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("prim")));
                    intent.putExtra("PostavAdres", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("adres")));
                    intent.putExtra("PostavTel", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("telef")));
                    contxt.startActivity(intent);
                   break;
                case 8:
                	intent = new Intent(contxt, KlientEditActivity.class);
                    intent.putExtra("KartaName", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("name")));
                    intent.putExtra("KartaId", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id")));
                    intent.putExtra("KartaPrim", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("prim")));
                    intent.putExtra("KartaAdres", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("adres")));
                    intent.putExtra("KartaTel", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("telef")));
                    intent.putExtra("KartaScontoSum", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("sconto_sum")));
                    intent.putExtra("KartaScontoPer", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("sconto_per")));
                    contxt.startActivity(intent);
                   break;
                case  1:
             	    intent = new Intent(contxt, ProdEditActivity.class);
                    intent.putExtra("ProdName", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("name")));
                    intent.putExtra("ProdId", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id")));
                    intent.putExtra("ProdPos", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("pos")));
                    intent.putExtra("ProdPrice", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("price")));
                    intent.putExtra("ProdPgr", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("pgr")));
                    intent.putExtra("ProdEd", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("ed")));
                    intent.putExtra("ProdTara", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("tara")));
                    intent.putExtra("ProdOk", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("ok")));
                    intent.putExtra("ProdVis", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("vis")));
                    contxt.startActivity(intent);
                    break;
                case  3:
             	    intent = new Intent(contxt, PrixodActivity.class);
             	    
             	    intent.putExtra("PrixodEd", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("ed")));
             	    intent.putExtra("PrixodPgr", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("pgr")));
             	    intent.putExtra("PrixodId", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id")));
                    intent.putExtra("PrixodProd", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("id_tmc")));
                    intent.putExtra("PrixodPrice", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("price")));
                    intent.putExtra("PrixodPriceVendor", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("price")));
                    intent.putExtra("PrixodKol", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("kol")));
                    intent.putExtra("PrixodPrim", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("prim")));
                    intent.putExtra("PrixodPost", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("id_post")));
                    intent.putExtra("PrixodDataIns", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("data_ins")));
                    
                    contxt.startActivity(intent);
                    break;
                case 9:
                	intent = new Intent(contxt, UserEditActivity.class);
                    intent.putExtra("UserName", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("name")));
                    intent.putExtra("UserPassword", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("password")));
                    intent.putExtra("UserId", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("_id")));
                    intent.putExtra("UserOk", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("ok")));
                    intent.putExtra("UserAdmin", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("admin")));
                    intent.putExtra("UserDataIns", ((Cursor) lv.getItemAtPosition(position)).getString(((Cursor) lv.getItemAtPosition(position)).getColumnIndex("data_ins")));
                    contxt.startActivity(intent);
                   break;
                case  6: //break;
                //case  3:
                case  4:
                case  5: listenerFlag.OnCambiare((byte)2, getItemId(position));
                		break;
                case 10:
                case 11:listenerFlag.OnCambiare((byte)2, getItemId(position));
        		break;
                case 14: MainActivity.no_inv=0; break;
                case 15:
                	
                	listenerFlag.OnCambiare((byte)3, getItemId(position));
                    break;
                case 0:
                	listenerFlag.OnCambiare((byte)2, getItemId(position));
                    break;
                case 16:
                	listenerFlag.OnCambiare((byte)2, getItemId(position));
                    break;
                case 17:
                	listenerFlag.OnCambiare((byte)2, getItemId(position));
                    break;
                }
                }
                catch(Exception e){
                	throw new ClassCastException("Table not found:_ " + namT);
                }

        }
    });
	
	/*if (nT==15) {
		((TextView) view.findViewById(R.id.tvKolReal_Inv)).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            View parent_row = (View) ((View) v.getParent()).getParent() ;
	            ListView lv = (ListView) parent_row.getParent();
	            final int position = lv.getPositionForView(parent_row);
	            
	            //tv = (TextView) v.findViewById(R.id.tvKolReal_Inv);  	
	            //tv.setText("100");
	            
	            try{
	            	
	            	listenerFlag.OnCambiare((byte)3, getItemId(position));
	            	
	                }
	                catch(Exception e){
	                	throw new ClassCastException("Не удалось изменить количество");
	                }
	            
	            //Log.d("MyLog", "Dposition = " + position);
	            //Log.d("MyLog", "Did = " + getItemId(position));
	        }
	    });	
	}*/
	
	super.bindView(view, context, cursor);
   }

}

