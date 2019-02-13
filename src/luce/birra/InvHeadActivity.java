package luce.birra;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import luce.birra.AdapterLV.CambiareListener;
 
public class InvHeadActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

  ListView lvData;
  Button btnExit, btnNew;
  AdapterLV scAdapterO;//scAdapterR, scAdapterP ;
  static TextView //tvIdPgr, 
  tvDI1, tvDI2; //tvIdKlient, 
  static TextView tvDataIns, tvDataIns2;
  //Spinner spPgr;//, spKlient;
  //Cursor cKlient;
  //SimpleCursorAdapter scaKlient;
  //TextView tv;
  //LinearLayout ll;
  //Button bll;
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.inv_head);
    //final DialogFragment dlg = new DialogActivity();
    
    tvDataIns = (TextView) findViewById(R.id.tv_Data_InvHead);
    tvDataIns.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    tvDI1 = (TextView) findViewById(R.id.tv_Data_InvHead1);
    tvDI1.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(1);
        }
      });
    
    tvDataIns2 = (TextView) findViewById(R.id.tv_Data_InvHead2);
    tvDataIns2.setText(MainActivity.getStringData(MainActivity.getIntData()));
    tvDataIns2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });
    tvDI2 = (TextView) findViewById(R.id.tv_Data_2InvHead);
    tvDI2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	showDialog(2);
        }
      });

/*    
    Cursor c = MainActivity.db.getRawData("select _id, name from tmc_pgr order by name", null);
    spPgr = (Spinner) findViewById(R.id.sp_Pgr_InvHead);
    tvIdPgr = (TextView) findViewById(R.id.tv_Id_PgrInvHead);
    tvIdPgr.setText("0");
   
    // make an adapter from the cursor
    String[] fromPgr = new String[] {"name"};
    int[] toPgr = new int[] {android.R.id.text1};
    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_item, c, fromPgr, toPgr);

    sca.setDropDownViewResource(R.layout.spinner_drop_down); 
    spPgr.setAdapter(sca);

    spPgr.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
        	spPgr.setTag(id);
        	tvIdPgr.setText(String.valueOf(id));
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        	spPgr.setTag(0);
        	tvIdPgr.setText("0");
        	getSupportLoaderManager().getLoader(0).forceLoad();
        }
        });
*/
    
    btnExit = (Button) findViewById(R.id.btnExitInvHead);
    btnExit.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
      });
    
    btnNew = (Button) findViewById(R.id.btnNewInvHead);
    btnNew.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(InvHeadActivity.this, InvActivity.class);
        	
        		int d= MainActivity.getIntDataTime();
        		long coun=0;
            	//if (MainActivity.invent==0)
            	//{        		
    Cursor cInv = MainActivity.db.getRawData ("select ifnull(max(_id),0) mid from invent_head ",null);
            		        	    if (cInv.moveToFirst()) { 
            		        	    	 
            		        	        do {
            		coun=cInv.getLong(cInv.getColumnIndex("mid"));
            		        	        } while (cInv.moveToNext());
            		        	        
            		        	      } else cInv.close();
            		        	  //Log.d("MyLog", "coun="+coun);
            	long cou=0;
            	if (MainActivity.invent==0)
        		{cou=MainActivity.db.addRecINVHEADcount(0, MainActivity.usr, "", 0, MainActivity.inv_dat_n, d, "INV"+MainActivity.usr+d, d, 0); //MainActivity.StrToFloat(tvSum.getText().toString()
            	MainActivity.invent=cou;
        		}
            	else
            		MainActivity.invent=coun;
            	//Log.d("MyLog", "cou="+cou);
    	        //Log.d("MyLog", "MainActivity.invent="+MainActivity.invent);
//_id, id_tmc, keg, kol, kol_nedo, kol_izl, ed, price, sumka, id_post, data_ins, data_upd, ok
//_id, id_inv, id_tmc, name_tmc, pgr, name_pgr, keg, kol_ostat, kol_real, kol_n, summa_n, kol_p, summa_p, kol_r, summa_r, kol_brak, summa_brak, kol_move, summa_move,
//kol_izl, summa_izl, kol_nedo, summa_nedo, kol_skidka, summa_skidka, kol_k, summa_k, ed, price, price_vendor, id_post, prim, data_ins, ok        		
        		        		        	    
        		Cursor cOst = MainActivity.db.getRawData ("select TT.price as ttprice, T.name as tname, T.pgr as tpgr, TP.name as tpname, O.id_tmc as oid_tmc, O.keg as okeg, O.kol as okol, O.kol_nedo as okol_nedo, O.kol_izl as okol_izl, O.ed as oed, O.id_post as oid_post, O.data_ins as odata_ins, O.data_upd as odata_upd, "
		+ "sum(I.kol_k) as ikol_n, sum(I.summa_k) as isum_n, sum(R.kol) as rkol, sum(R.kol*R.price) as rsum, sum(R.kol_brak) as rkol_brak, sum(R.kol_brak*R.price) as rsum_brak, sum(R.kol_move) as rkol_move, sum(R.kol_move*R.price) as rsum_move, sum(R.kol_nedo) as rkol_nedo, sum(R.kol_nedo*R.price) as rsum_nedo, sum(R.kol_izl) as rkol_izl, sum(R.kol_izl*R.price) as rsum_izl, count(CASE WHEN ifnull(skidka,0) > 0 THEN 1 ELSE 0 END) as rkol_skidka, sum(R.skidka) as rsum_skidka, sum(P.kol) as pkol, sum(P.price_vendor*P.kol) as psum "
		+ "from ostat O left join rasxod as R on O.id_tmc=R.id_tmc and O.keg=R.keg and O.id_post=R.id_post and O.ed=R.ed "
		+ "left join tmc_price as TT on O.id_tmc=TT.id_tmc and O.id_post=TT.id_post and O.ed=TT.ed left join tmc as T on T._id=O.id_tmc left join tmc_pgr as TP on TP._id=T.pgr "
		+ "left join prixod as P on O.id_tmc=P.id_tmc and O.keg=P.keg and O.id_post=P.id_post and O.ed=P.ed "
		+ "left join invent as I on O.id_tmc=I.id_tmc and O.keg=I.keg and O.id_post=I.id_post and O.ed=I.ed and I.id_inv="+MainActivity.invent
		+ " group by TT.price, T.name, T.pgr, TP.name, O.id_tmc, O.keg, O.kol, O.kol_nedo, O.kol_izl, O.ed, O.id_post, O.data_ins, O.data_upd ",/*order by T.tara*/null);
        	    byte ib=0;
        	    byte tmp=0;
        	    
        	    if (cOst.moveToFirst()) { 
        	    	 
        	        do {
        	        	//Log.d("MyLog", "dvigTmc "+cc.getInt(cc.getColumnIndex("id_tmc")));
        		        //Log.d("MyLog", "ib="+ib);
        	        	if (MainActivity.invent!=0)
                    	{
                	    tmp=0;
                	    Cursor cOst1 = MainActivity.db.getRawData ("select O.id_inv as id_inv, O._id as id, O.id_tmc as oid_tmc, O.keg as okeg, O.kol_ostat as okol, O.kol_nedo as okol_nedo, O.kol_izl as okol_izl, O.ed as oed, O.id_post as oid_post "
                	    		+ " from invent as O where O.id_inv="+MainActivity.invent+" and O.id_tmc="+cOst.getInt(cOst.getColumnIndex("oid_tmc"))+" and O.id_post="+cOst.getInt(cOst.getColumnIndex("oid_post"))+" and O.keg="+cOst.getInt(cOst.getColumnIndex("okeg"))+" and O.ed="+cOst.getInt(cOst.getColumnIndex("oed")),/*order by T.tara*/null);
                	    if (cOst1.moveToFirst()) {
                	    	do {
                	    		//Log.d("MyLog", cOst1.getLong(cOst1.getColumnIndex("id_inv"))+" "+cOst1.getLong(cOst1.getColumnIndex("id"))+" tmp="+tmp+" "+cOst.getInt(cOst.getColumnIndex("oid_tmc"))+" "+cOst.getInt(cOst.getColumnIndex("oid_post"))+" "+cOst.getInt(cOst.getColumnIndex("okeg"))+" "+cOst.getInt(cOst.getColumnIndex("oed"))+" ib="+ib);
                	    	tmp=(byte)MainActivity.db.updRecCount("invent", cOst1.getLong(cOst1.getColumnIndex("id")), 
                	    			new String[]{"kol_ostat",
                	    					"kol_p",
                	    					"summa_p",
                	    					"kol_r",
                	    					"summa_r",
                	    					"kol_brak",
                	    					"summa_brak",
                	    					"kol_move",
                	    					"summa_move",
                	    					"kol_izl",
                	    					"summa_izl",
                	    					"kol_nedo",
                	    					"summa_nedo",
                	    					"kol_skidka",
                	    					"summa_skidka"}, 
                	    			new double[]{cOst.getDouble(cOst.getColumnIndex("okol")),
                	    					cOst.getDouble(cOst.getColumnIndex("pkol")), 
                	    					cOst.getDouble(cOst.getColumnIndex("psum")),
                	    					cOst.getDouble(cOst.getColumnIndex("rkol")), 
                	    					cOst.getDouble(cOst.getColumnIndex("rsum")),
                	    					cOst.getDouble(cOst.getColumnIndex("rkol_brak")), 
                	    					cOst.getDouble(cOst.getColumnIndex("rsum_brak")),
                	    					cOst.getDouble(cOst.getColumnIndex("rkol_move")), 
                	    					cOst.getDouble(cOst.getColumnIndex("rsum_move")), 
                	    					cOst.getDouble(cOst.getColumnIndex("rkol_izl")), 
                	    					cOst.getDouble(cOst.getColumnIndex("rsum_izl")),
                	    					cOst.getDouble(cOst.getColumnIndex("rkol_nedo")), 
                	    					cOst.getDouble(cOst.getColumnIndex("rsum_nedo")),
                	    					cOst.getDouble(cOst.getColumnIndex("rkol_skidka")), 
                	    					cOst.getDouble(cOst.getColumnIndex("rsum_skidka"))});
                	    	//Log.d("MyLog", cOst1.getLong(cOst1.getColumnIndex("id_inv"))+" "+cOst1.getLong(cOst1.getColumnIndex("id"))+" tmp="+tmp+" "+cOst.getInt(cOst.getColumnIndex("oid_tmc"))+" "+cOst.getInt(cOst.getColumnIndex("oid_post"))+" "+cOst.getInt(cOst.getColumnIndex("okeg"))+" "+cOst.getInt(cOst.getColumnIndex("oed"))+" ib="+ib);
                	    	} while (cOst1.moveToNext());
                    	}
        	        	else cOst1.close();
                	    
                	    if (tmp==0)
                	    {//Log.d("MyLog", "add="+ib);
                	    	MainActivity.db.addRecINVENT(
                	    			MainActivity.invent, cOst.getInt(cOst.getColumnIndex("oid_tmc")), cOst.getString(cOst.getColumnIndex("tname")), cOst.getInt(cOst.getColumnIndex("tpgr")), cOst.getString(cOst.getColumnIndex("tpname")), cOst.getInt(cOst.getColumnIndex("okeg")), cOst.getInt(cOst.getColumnIndex("oid_post")), cOst.getInt(cOst.getColumnIndex("oed")), 
                	    			cOst.getDouble(cOst.getColumnIndex("okol")), /*(double)0*/cOst.getDouble(cOst.getColumnIndex("okol")), cOst.getDouble(cOst.getColumnIndex("ikol_n")), cOst.getDouble(cOst.getColumnIndex("isum_n")), cOst.getDouble(cOst.getColumnIndex("pkol")), cOst.getDouble(cOst.getColumnIndex("psum")), 
                	    			cOst.getDouble(cOst.getColumnIndex("rkol")), cOst.getDouble(cOst.getColumnIndex("rsum")), cOst.getDouble(cOst.getColumnIndex("rkol_brak")), cOst.getDouble(cOst.getColumnIndex("rsum_brak")), 
                	    			cOst.getDouble(cOst.getColumnIndex("rkol_move")), cOst.getDouble(cOst.getColumnIndex("rsum_move")), cOst.getDouble(cOst.getColumnIndex("rkol_izl")), cOst.getDouble(cOst.getColumnIndex("rsum_izl")), 
                	    			cOst.getDouble(cOst.getColumnIndex("rkol_nedo")), cOst.getDouble(cOst.getColumnIndex("rsum_nedo")), cOst.getDouble(cOst.getColumnIndex("rkol_skidka")), cOst.getDouble(cOst.getColumnIndex("rsum_skidka")), 
                	    			(double)0, (double)0, cOst.getDouble(cOst.getColumnIndex("ttprice")), (double)0, "", cOst.getInt(cOst.getColumnIndex("odata_ins")), (byte)0
                	    			);
                	    }
                	    
                    	}
                	    /*else {
                	    	MainActivity.db.addRecINVENT(
                	    			cou, cOst.getInt(cOst.getColumnIndex("oid_tmc")), cOst.getString(cOst.getColumnIndex("tname")), cOst.getInt(cOst.getColumnIndex("tpgr")), cOst.getString(cOst.getColumnIndex("tpname")), cOst.getInt(cOst.getColumnIndex("okeg")), cOst.getInt(cOst.getColumnIndex("oid_post")), cOst.getInt(cOst.getColumnIndex("oed")), 
                	    			cOst.getDouble(cOst.getColumnIndex("okol")), (double)0, cOst.getDouble(cOst.getColumnIndex("ikol_n")), cOst.getDouble(cOst.getColumnIndex("isum_n")), cOst.getDouble(cOst.getColumnIndex("pkol")), cOst.getDouble(cOst.getColumnIndex("psum")), 
                	    			cOst.getDouble(cOst.getColumnIndex("rkol")), cOst.getDouble(cOst.getColumnIndex("rsum")), cOst.getDouble(cOst.getColumnIndex("rkol_brak")), cOst.getDouble(cOst.getColumnIndex("rsum_brak")), 
                	    			cOst.getDouble(cOst.getColumnIndex("rkol_move")), cOst.getDouble(cOst.getColumnIndex("rsum_move")), cOst.getDouble(cOst.getColumnIndex("rkol_izl")), cOst.getDouble(cOst.getColumnIndex("rsum_izl")), 
                	    			cOst.getDouble(cOst.getColumnIndex("rkol_nedo")), cOst.getDouble(cOst.getColumnIndex("rsum_nedo")), cOst.getDouble(cOst.getColumnIndex("rkol_skidka")), cOst.getDouble(cOst.getColumnIndex("rsum_skidka")), 
                	    			(double)0, (double)0, cOst.getDouble(cOst.getColumnIndex("ttprice")), (double)0, "", cOst.getInt(cOst.getColumnIndex("odata_ins")), (byte)0
                	    			);
                	    }  */
        	        	       	        	
        	        	ib++;
        	        } while (cOst.moveToNext());
        	        
        	      } else cOst.close();
        	
        	    
			intent.putExtra("id_inv", String.valueOf(MainActivity.invent));
			intent.putExtra("text_inv", "“≈ ”ўјя »Ќ¬≈Ќ“ј–»«ј÷»я");
			 //Log.d("MyLog", "put String.valueOf(cou)="+String.valueOf(cou));
			MainActivity.no_inv=1;
			startActivity(intent);
			//InvActivity.btnMake.setVisibility(LinearLayout.VISIBLE );
			//ll = (LinearLayout) getLayoutInflater().inflate(R.layout.inv, null);
			//bll = (Button) ll.findViewById(R.id.btnMakeInv);
			//bll.setVisibility(LinearLayout.GONE);
        	
        }}
    );
    
    // формируем столбцы сопоставлени€
    //String[] fromR = new String[] {"_id", "id_tmc","name","ted",/*"kol_n",*/"sum_n","price_n",/*"kol_pri","sum_pri","price_pri",*/"kol_ras","sum_ras",/*"price_ras",*/"kol_k","sum_k","price_k"};
    //int[] toR = new int[] {R.id.tv_Id_OstatR_oborotka, R.id.tv_Nnom_Oborotka, R.id.tv_Name_Oborotka,R.id.tv_Ted_Oborotka, /*R.id.tv_Kol_Nach_Oborotka,*/R.id.tv_Sum_Nach_Oborotka,R.id.tv_Price_Nach_Oborotka,
    		/*R.id.tv_Kol_Pri_Oborotka,R.id.tv_Sum_Pri_Oborotka,R.id.tv_Price_Pri_Oborotka,*/
    		//R.id.tv_Kol_Ras_Oborotka,R.id.tv_Sum_Ras_Oborotka,/*R.id.tv_Price_Ras_Oborotka,*/
    		//R.id.tv_Kol_Kon_Oborotka,R.id.tv_Sum_Kon_Oborotka,R.id.tv_Price_Kon_Oborotka};
    //String[] fromP = new String[] {"_id", "kol_pri","sum_pri"};
    //int[] toP = new int[] {R.id.tv_Id_OstatP_oborotka, R.id.tv_Kol_Pri_Oborotka,R.id.tv_Sum_Pri_Oborotka};
    //_id, id_user, user, name, summa, dat_n, dat_k, prim, data_ins, ok		
    String[] fromO = new String[] {"_id", "user","name","summa","dat_n","dat_k","prim","data_ins"};
    	    int[] toO = new int[] {R.id.tv_Id_InvHead, R.id.tv_User_InvHead,R.id.tv_Name_InvHead,
    	    		R.id.tv_Summa_InvHead,R.id.atN_InvHead,R.id.atK_InvHead,R.id.tv_Prim_InvHead,R.id.ataIns_InvHead};
    // создаем адаптер и настраиваем список сначала кнопка ƒел, јпд, им€ таблицы

    	    scAdapterO = new AdapterLV(R.id.btnDelInvHead, R.id.btnUpdInvHead, (byte)14, this, R.layout.inv_head_item, null, fromO, toO, 0)
    	    .setCamdiareListener(new CambiareListener() {
    			@Override
    			public void OnCambiare(byte flag, long id) {
    				
    				if (flag==1 || flag==2) {
    					
    					Cursor cc = MainActivity.db.getRawData ("select substr(data_ins,5,2)||'.'||substr(data_ins,3,2)||'.'||substr(data_ins,1,2) dat, user, substr(dat_n,5,2)||'.'||substr(dat_n,3,2) dat_n,substr(dat_k,5,2)||'.'||substr(dat_k,3,2) dat_k from invent_head where _id="+id,null);
    					Intent intent = new Intent(InvHeadActivity.this, InvActivity.class);
    					intent.putExtra("id_inv", String.valueOf(id));
    					MainActivity.no_inv=0;
    					if (cc.moveToFirst()) { 
    					        do {
    					        	
    			                    
    			                    intent.putExtra("text_inv", cc.getString(cc.getColumnIndex("dat"))+" "+cc.getString(cc.getColumnIndex("user"))+" c "+cc.getString(cc.getColumnIndex("dat_n"))+" по "+cc.getString(cc.getColumnIndex("dat_k")));
    			                    /*intent.putExtra("dvigKeg", String.valueOf(cc.getInt(cc.getColumnIndex("keg"))) );
    			                    //Log.d("MyLog", "dvigTmc "+cc.getInt(cc.getColumnIndex("id_tmc")));
    			                    intent.putExtra("dvigNameTmc", cc.getString(cc.getColumnIndex("name_tmc")));
    			                    //Log.d("MyLog", "dvigNameTmc "+cc.getString(cc.getColumnIndex("name_tmc")));
    			                    intent.putExtra("dvigEd", String.valueOf(cc.getInt(cc.getColumnIndex("ed"))));
    			                    //Log.d("MyLog", "dvigEd "+cc.getInt(cc.getColumnIndex("ed")));
    			                    intent.putExtra("dvigNameEd", cc.getString(cc.getColumnIndex("name_ed")));
    			                    //Log.d("MyLog", "dvigNameEd "+cc.getString(cc.getColumnIndex("name_ed")));
    			                    
    			                    intent.putExtra("dvigPost", String.valueOf(cc.getInt(cc.getColumnIndex("id_post"))));
    			                    //Log.d("MyLog", "dvigPost "+cc.getInt(cc.getColumnIndex("id_post")));
    			                    intent.putExtra("dvigNamePost", cc.getString(cc.getColumnIndex("name_post")));
    			                    //Log.d("MyLog", "dvigNamePost "+cc.getString(cc.getColumnIndex("name_post")));
    			                    intent.putExtra("dvigKol", String.valueOf(cc.getDouble(cc.getColumnIndex("kol"))));
    			                    //Log.d("MyLog", "dvigKol "+cc.getDouble(cc.getColumnIndex("kol")));
    			                    intent.putExtra("dvigID", String.valueOf(cc.getInt(cc.getColumnIndex("id"))));
    			                    //Log.d("MyLog", "dvigID "+cc.getInt(cc.getColumnIndex("id")));
    			                     */
    			                     
    			        
    			                    } 
    					       while (cc.moveToNext());
    					      };
    					      //Log.d("MyLog", "id="+id);
    					      startActivity(intent);
    					     /* ll = (LinearLayout) getLayoutInflater().inflate(R.layout.inv, null);
			        			bll = (Button) ll.findViewById(R.id.btnMakeInv);
			        			bll.setVisibility(LinearLayout.GONE);*/
    					      //InvActivity.btnMake.setVisibility(LinearLayout.GONE);
    					      //Dview = (LinearLayout) getLayoutInflater().inflate(R.layout.chek, null);
    				}
    			}
    		});
    lvData = (ListView) findViewById(R.id.lvInvHead);
    lvData.setAdapter(scAdapterO);
    
    //scAdapterP = new AdapterLV(R.id.btnDelOborotka, R.id.btnUpdOborotka, (byte)10, this, R.layout.oborotka_item, null, fromP, toP, 0);
    //lvData.setAdapter(scAdapterP);
     
    /*

*/
    
    // создаем лоадер дл€ чтени€ данных
    //getSupportLoaderManager().initLoader(1, null, this);
    getSupportLoaderManager().initLoader(0, null, this);
    //Log.d("MyLog", "create data="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString())));
    MainActivity.setSizeFontMain((LinearLayout)findViewById(R.id.inv_head_ll));
  }
  
  protected Dialog onCreateDialog(int id) {
      if (id == 1) {
        DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return tpd;
      }
      if (id == 2) {
          DatePickerDialog tpd = new DatePickerDialog(this, myCallBack2, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
          return tpd;
        }
      return super.onCreateDialog(id);
    }
     
    OnDateSetListener myCallBack = new OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth) {
      tvDataIns.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
      //getSupportLoaderManager().getLoader(1).forceLoad();
      getSupportLoaderManager().getLoader(0).forceLoad();
    }
    };
    
    OnDateSetListener myCallBack2 = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {
          tvDataIns2.setText(String.valueOf(100+dayOfMonth).substring(1) + "." + String.valueOf(100+(monthOfYear+1)).substring(1) + "." + String.valueOf(100+(year%2000)).substring(1));
          //getSupportLoaderManager().getLoader(1).forceLoad();
          getSupportLoaderManager().getLoader(0).forceLoad();
        }
        };
    
  @Override
  protected void onRestart() {
    super.onRestart();
    //getSupportLoaderManager().getLoader(1).forceLoad();
    getSupportLoaderManager().getLoader(0).forceLoad();
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
 
  @Override
  
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
     // Log.d("Loader<Cursor> onCreateLoader", String.valueOf(i));
	  return new OborotkaLoader(this, MainActivity.db);
	  //if (i == 0 ) return new RLoader(this, MainActivity.db);
      //if (i == 1 ) return new PLoader(this, MainActivity.db);
      //return null;
  }
  
  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	  switch(loader.getId())
      {
      case 0:
    	  scAdapterO.swapCursor(cursor);
    	  //scAdapterR.swapCursor(cursor);
       break;
      case 1:
    	  //scAdapterP.swapCursor(cursor);
       break;
   }
	  
  }
 
  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
	 /* switch(loader.getId())
      {
      case 0:
    	  scAdapter.swapCursor(null);
       break;
      case 1:
    	  scaKlient.swapCursor(null);
       break;
   }*/
  }
   
  static class OborotkaLoader extends CursorLoader {
 
    DB db;
    public OborotkaLoader(Context context, DB db) {
      super(context);
      this.db = db;
    }
     
    @Override
    public Cursor loadInBackground() {
    	String []str = {//(tvIdPgr.getText().toString().equals("0")||tvIdPgr.getText().length()==0)?"":" TP._id="+tvIdPgr.getText().toString()
    			//,
    			(tvDataIns.getText().length()==0)?"":"where data_ins>="+String.valueOf(MainActivity.getIntData(tvDataIns.getText().toString()))+"0000 and data_ins<="+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+"5959",
    					(tvDataIns.getText().length()==0)?"":"where data_ins<="+String.valueOf(MainActivity.getIntData(tvDataIns2.getText().toString()))+"5959"
    	};
        String where=str[0].toString();
        //Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
        //if (where.equals("")||where.length()==0) where=str[1].toString(); else 
        	//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
/*id_user, user, name text, summa, dat_n, dat_k, prim, data_ins, ok */
        //{ "id_tmc","name","kol_n","sum_n","price_n","kol_pri","sum_pri","price_pri","kol_ras","sum_ras","price_ras","kol_k","sum_k","price_k"}    
            	 
            	 
            	 Cursor cursor = db.getRawData (
             			"select _id as _id, id_user, user, name, summa, substr(dat_n,5,2)||'.'||substr(dat_n,3,2)||'.'||substr(dat_n,1,2)||' '||substr(dat_n,7,2)||':'||substr(dat_n,9,2) as dat_n, substr(dat_k,5,2)||'.'||substr(dat_k,3,2)||'.'||substr(dat_k,1,2)||' '||substr(dat_k,7,2)||':'||substr(dat_k,9,2) as dat_k, prim, substr(data_ins,5,2)||'.'||substr(data_ins,3,2)||'.'||substr(data_ins,1,2)||' '||substr(data_ins,7,2)||':'||substr(data_ins,9,2) as data_ins "
             			+ " from invent_head "+str[0]+" order by dat_k, user"
             			, null);

      return cursor;
    }
     
  }
  
}

/*
 
*/


