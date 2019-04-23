package luce.birra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import android.database.Cursor;
import android.os.Environment;

//http://stackoverflow.com/questions/5401104/android-exporting-to-csv-and-sending-as-email-attachment
//http://stackoverflow.com/questions/4632501/android-generate-csv-file-from-table-values
public class /*ExportDatabaseCSVTask*/ Export2Excel //extends AsyncTask<String, String, Boolean> 
{
//http://javadevblog.com/obzor-biblioteki-apache-poi-rabota-s-prilozheniyami-paketa-microsoft-office-v-java.html
//https://habrahabr.ru/sandbox/38101/
/*	private void saveFile(String filename) {
		File fileName = new File(filename);
		String sdState = android.os.Environment.getExternalStorageState();
		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
		File sdDir = android.os.Environment.getExternalStorageDirectory();
		fileName = new File(sdDir, "cache/primer.txt");
		} else {
		//fileName = context.getCacheDir();
		}
		if (!fileName.exists())
		fileName.mkdirs();
		try {
		FileWriter f = new FileWriter(fileName);
		f.write("hello world");
		f.flush();
		f.close();
		} catch (Exception e) {

		}
    }
*/	
	static File inv (int id, String dirN) {
//////////////////////////////////////

Cursor cc = MainActivity.db.getQueryData( 
		"invent as O left join tmc as T on O.id_tmc=T._id "
    			+ "left join postav as P on O.id_post=P._id "
    			+ "left join tmc_ed as E on O.ed=E._id "
    			+ "left join tmc_pgr as TP on O.pgr=TP._id ",
    			
new String[] { "O._id as _id", 
		"O.id_tmc id_tmc", 
		"O.name_tmc name_tmc", 
		"T.name nametmc", 
		"O.pgr pgr", 
		"O.name_pgr name_pgr", 
		"O.keg keg", 
		"O.kol_ostat kol_ostat", 
		"O.kol_real kol_real",
		"O.kol_n kol_n",
		"O.summa_n summa_n",
		"O.kol_p kol_p",
		"O.summa_p summa_p",
		"O.kol_r kol_r",
		"O.summa_r summa_r",
		"O.kol_brak kol_brak",
		"O.summa_brak summa_brak",
		"O.kol_move kol_move",
		"O.summa_move summa_move",
		"O.kol_izl kol_izl",
		"O.summa_izl summa_izl",
		"O.kol_nedo kol_nedo",
		"O.summa_nedo summa_nedo",
		"O.kol_skidka kol_skidka",
		"O.summa_skidka summa_skidka",
		"O.kol_k kol_k",
		"O.summa_k summa_k",
		"O.ed ed",
		"E.name edname",
		"O.price price",
		"O.price_vendor price_vendor",
		"O.id_post id_post",
		"P.name postname",
		"O.prim prim",
		"O.data_ins data_ins",
		"O.ok ok"}, "O.id_inv="+id, null,null,null,"O.pgr, O.name_tmc, O.id_post, O.keg");

String d1="", d2="", u="", d3="";
Cursor cOst1 = MainActivity.db.getRawData ("select id_user, user, name, summa, dat_n, dat_k, prim, data_ins from invent_head where _id="+id,null);
if (cOst1.moveToFirst()) {
	do {
		//Log.d("MyLog", cOst1.getLong(cOst1.getColumnIndex("id_inv"))+" "+cOst1.getLong(cOst1.getColumnIndex("id"))+" tmp="+tmp+" "+cOst.getInt(cOst.getColumnIndex("oid_tmc"))+" "+cOst.getInt(cOst.getColumnIndex("oid_post"))+" "+cOst.getInt(cOst.getColumnIndex("okeg"))+" "+cOst.getInt(cOst.getColumnIndex("oed"))+" ib="+ib);
	u=cOst1.getString(cOst1.getColumnIndex("user"));
	d1=MainActivity.getStringDataTime( cOst1.getInt(cOst1.getColumnIndex("dat_n")) );
	d2=MainActivity.getStringDataTime( cOst1.getInt(cOst1.getColumnIndex("dat_k")) );
	d3=MainActivity.getStringDataTime( cOst1.getInt(cOst1.getColumnIndex("data_ins")));
		//Log.d("MyLog", cOst1.getLong(cOst1.getColumnIndex("id_inv"))+" "+cOst1.getLong(cOst1.getColumnIndex("id"))+" tmp="+tmp+" "+cOst.getInt(cOst.getColumnIndex("oid_tmc"))+" "+cOst.getInt(cOst.getColumnIndex("oid_post"))+" "+cOst.getInt(cOst.getColumnIndex("okeg"))+" "+cOst.getInt(cOst.getColumnIndex("oed"))+" ib="+ib);
	} while (cOst1.moveToNext());
}
else cOst1.close();
//Log.d("MyLog", "d3="+d3+" d2="+d2+" d1="+d1+" u="+u);
File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, u+" Инвентаризационная ведомость №"+id+" c "+d1+" по "+d2//+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)
		+".xls");

try {
FileOutputStream out   =   null;

out = new FileOutputStream(file);

//создание самого excel файла в памяти
HSSFWorkbook workbook = new HSSFWorkbook();

//HSSFDataFormat df = workbook.createDataFormat();
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
//styleN2.setFillForegroundColor(HSSFColor.LIME.index);
//styleN2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
//создание листа с названием "Просто лист"
HSSFSheet sheet = workbook.createSheet("Лист1");
//и применяем к этому стилю жирный шрифт
//style.setFont(font);
//style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
//заполняем список какими-то данными
//List<DataModel> dataList = fillData();
//счетчик для строк
int rowNum = 0;
//создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
HSSFRow row = sheet.createRow(rowNum);

//создаем шрифт
HSSFFont font = workbook.createFont();
//указываем, что хотим его видеть жирным
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
//style.setAlignment(HorizontalAlignment.FILL);
//style.setVerticalAlignment(VerticalAlignment.CENTER);

//id_tmc,name_tmc,nametmc,pgr,name_pgr,keg,kol_ostat,kol_real,kol_n,summa_n,kol_p,summa_p,kol_r,summa_r,kol_brak,summa_brak,
//kol_move,summa_move,kol_izl,summa_izl,kol_nedo,summa_nedo,kol_skidka,summa_skidka,kol_k,summa_k,ed,edname,price,price_vendor,id_post,postname,prim,data_ins

row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("ДАТА 1й ПРОДАЖИ");
row.createCell(5).setCellValue("КЕГА");
row.createCell(6).setCellValue("ЕД.ИЗМ");
row.createCell(7).setCellValue("КОЛ-ВО В СИСТЕМЕ НА "+d1);
row.createCell(8).setCellValue("СУММА В СИСТЕМЕ НА "+d1);
row.createCell(9).setCellValue("КОЛ-ВО ФАКТ");
row.createCell(10).setCellValue("СУММА ФАКТ");
row.createCell(11).setCellValue("КОЛ-ВО НА НАЧАЛО");
row.createCell(12).setCellValue("СУММА НА НАЧАЛО");
row.createCell(13).setCellValue("КОЛ-ВО ПРИХОД ");
row.createCell(14).setCellValue("СУММА ПРИХОД");
row.createCell(15).setCellValue("КОЛ-ВО РАСХОД");
row.createCell(16).setCellValue("СУММА РАСХОД");
row.createCell(17).setCellValue("КОЛ-ВО БРАК");
row.createCell(18).setCellValue("СУММА БРАК");
row.createCell(19).setCellValue("КОЛ-ВО ПЕРЕМЕЩЕНО");
row.createCell(20).setCellValue("СУММА ПЕРЕМЕЩЕНО");
row.createCell(21).setCellValue("КОЛ-ВО ИЗЛИШКИ");
row.createCell(22).setCellValue("СУММА ИЗЛИШКИ");
row.createCell(23).setCellValue("КОЛ-ВО НЕДОСТАЧА");
row.createCell(24).setCellValue("СУММА НЕДОСТАЧА");
row.createCell(25).setCellValue("КОЛ-ВО ПРОДАЖ");
row.createCell(26).setCellValue("СУММА СКИДКА");
row.createCell(27).setCellValue("КОЛ-ВО НА КОНЕЦ "+d2);
row.createCell(28).setCellValue("СУММА НА КОНЕЦ "+d2);
row.createCell(29).setCellValue("ЦЕНА ПРОДАЖИ");
row.createCell(30).setCellValue("ПРИМЕЧАНИЕ "+u+" "+d3);

//row.createCell(15).setCellValue("ЦЕНА ПРОДАЖИ НА "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));

for (int i=0; i<31; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);

if (cc.moveToFirst())  
do {

rowNum++;
row = sheet.createRow(rowNum);

row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));

row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("name_pgr")));

row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("nametmc")));

row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("postname")));

row.createCell(4).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));

row.createCell(5).setCellValue(cc.getInt(cc.getColumnIndex("keg")));

row.createCell(6).setCellValue(cc.getString(cc.getColumnIndex("edname")));

row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("kol_ostat"))); row.getCell(7).setCellStyle(styleN3);

row.createCell(8).setCellValue(cc.getDouble(cc.getColumnIndex("kol_ostat"))*cc.getDouble(cc.getColumnIndex("price_vendor")));row.getCell(8).setCellStyle(styleN2);

row.createCell(9).setCellValue(cc.getDouble(cc.getColumnIndex("kol_real"))); row.getCell(9).setCellStyle(styleN3);

row.createCell(10).setCellValue(cc.getDouble(cc.getColumnIndex("kol_real"))*cc.getDouble(cc.getColumnIndex("price_vendor")));row.getCell(10).setCellStyle(styleN2);

row.createCell(11).setCellValue(cc.getDouble(cc.getColumnIndex("kol_n"))); row.getCell(11).setCellStyle(styleN3);

row.createCell(12).setCellValue(cc.getDouble(cc.getColumnIndex("summa_n")));row.getCell(12).setCellStyle(styleN2);

row.createCell(13).setCellValue(cc.getDouble(cc.getColumnIndex("kol_p"))); row.getCell(13).setCellStyle(styleN3);

row.createCell(14).setCellValue(cc.getDouble(cc.getColumnIndex("summa_p")));row.getCell(14).setCellStyle(styleN2);

row.createCell(15).setCellValue(cc.getDouble(cc.getColumnIndex("kol_r"))); row.getCell(15).setCellStyle(styleN3);

row.createCell(16).setCellValue(cc.getDouble(cc.getColumnIndex("summa_r")));row.getCell(16).setCellStyle(styleN2);

row.createCell(17).setCellValue(cc.getDouble(cc.getColumnIndex("kol_brak"))); row.getCell(17).setCellStyle(styleN3);

row.createCell(18).setCellValue(cc.getDouble(cc.getColumnIndex("summa_brak")));row.getCell(18).setCellStyle(styleN2);

row.createCell(19).setCellValue(cc.getDouble(cc.getColumnIndex("kol_move"))); row.getCell(19).setCellStyle(styleN3);

row.createCell(20).setCellValue(cc.getDouble(cc.getColumnIndex("summa_move")));row.getCell(20).setCellStyle(styleN2);

row.createCell(21).setCellValue(cc.getDouble(cc.getColumnIndex("kol_izl"))); row.getCell(21).setCellStyle(styleN3);

row.createCell(22).setCellValue(cc.getDouble(cc.getColumnIndex("summa_izl")));row.getCell(22).setCellStyle(styleN2);

row.createCell(23).setCellValue(cc.getDouble(cc.getColumnIndex("kol_nedo"))); row.getCell(23).setCellStyle(styleN3);

row.createCell(24).setCellValue(cc.getDouble(cc.getColumnIndex("summa_nedo")));row.getCell(24).setCellStyle(styleN2);

row.createCell(25).setCellValue(cc.getDouble(cc.getColumnIndex("kol_skidka"))); row.getCell(25).setCellStyle(styleN3);

row.createCell(26).setCellValue(cc.getDouble(cc.getColumnIndex("summa_skidka")));row.getCell(26).setCellStyle(styleN2);

row.createCell(27).setCellValue(cc.getDouble(cc.getColumnIndex("kol_k"))); row.getCell(27).setCellStyle(styleN3);

row.createCell(28).setCellValue(cc.getDouble(cc.getColumnIndex("summa_k")));row.getCell(28).setCellStyle(styleN2);

row.createCell(29).setCellValue(cc.getDouble(cc.getColumnIndex("price_vendor")));row.getCell(29).setCellStyle(styleN2);

row.createCell(30).setCellValue(cc.getString(cc.getColumnIndex("prim")));

} while (cc.moveToNext());

cc.close();

rowNum++;
//

row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(8).setCellFormula("SUM(I2:I"+rowNum+")"); evaluator.evaluateFormulaCell(row.getCell(8));
row.createCell(10).setCellFormula("SUM(K2:K"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(10));
row.createCell(12).setCellFormula("SUM(M2:M"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(12));
row.createCell(14).setCellFormula("SUM(O2:O"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(14));
row.createCell(16).setCellFormula("SUM(Q2:Q"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(16));
row.createCell(18).setCellFormula("SUM(S2:S"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(18));
row.createCell(20).setCellFormula("SUM(U2:U"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(20));
row.createCell(22).setCellFormula("SUM(W2:W"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(22));
row.createCell(24).setCellFormula("SUM(Y2:Y"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(24));
row.createCell(26).setCellFormula("SUM(AA2:AA"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(26));
row.createCell(28).setCellFormula("SUM(AC2:AC"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(28));

sheet.setAutoFilter(CellRangeAddress.valueOf("A1:AE"+rowNum));

workbook.write(out);
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();// out.close();
}

return file;
}	
	
static File oborotka (int dat1, int dat2, int pgr, String dirN) {
//////////////////////////////////////
String []str = {pgr==0?"":" TP._id="+pgr, dat1==0?"":"where data_ins>="+dat1+"0000 and data_ins<="+dat2+"5959",
		dat2==0?"":"where data_ins<="+dat2+"5959"};
//String where=str[0].toString();
//if (str[0].length()!=0) where=where+" and "+str[0];
Cursor cc = MainActivity.db.getQueryData( 
		"(select CAST(id_tmc||keg||id_post||ed as integer) as _id, id_tmc, keg, id_post, ed, ifnull(sum(round(kol,3)),0) as kol, ifnull(sum(round(sumo,2)),0) as sumka from "+ 
      			"("+
      			"select id_tmc, keg, id_post, ed, -ifnull(round(kol,3),0) as kol, -ifnull(round(kol,3)*round(price,2)-round(ifnull(skidka,0),2),0) as sumo from rasxod "+str[2].toString()+
      			" union all "+
      			"select id_tmc, keg, id_post, ed, ifnull(round(kol,3),0) as kol, ifnull(round(kol,3)*round(price,2),0) as sumo from prixod "+str[2].toString()+
      			") group by id_tmc, keg, id_post, ed)" +
    			 " as O " +
    			 "left join (select id_tmc, keg, id_post, ed, ifnull(sum(round(kol,3)),0) as sumkr, ifnull(sum(round(kol,3)*round(price,2)-round(ifnull(skidka,0),2)),0) as sumsr from rasxod " + str[1].toString() + 
    			 " group by id_tmc, keg, id_post, ed) as R on O.id_tmc=R.id_tmc and O.keg=R.keg and O.id_post=R.id_post and O.ed=R.ed "
      			+ " left join (select id_tmc, keg, id_post, ed, ifnull(sum(round(kol,3)),0) as sumkp, ifnull(sum(round(kol,3)*round(price,2)),0) as sumsp from prixod " + str[1].toString() +
      			" group by id_tmc, keg, id_post, ed) as P on O.id_tmc=P.id_tmc and O.keg=P.keg and O.id_post=P.id_post and O.ed=P.ed"
      			+ " left join ostat as K on O.id_tmc=K.id_tmc and O.keg=K.keg and O.id_post=K.id_post and O.ed=K.ed " +
      			" left join tmc_price as TPP on O.id_tmc=TPP.id_tmc and O.id_post=TPP.id_post and O.ed=TPP.ed "+
      			" left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id left join postav as POS on O.id_post=POS._id",
    			 new String[] {"O._id as _id","O.keg as keg", "O.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as kkeg",
    			"O.id_tmc as id_tmc","T.name||' '||POS.name as name","E.name as ted","TP.name as pgrname","POS.name as pname","TPP.price as price",
    			//"0 as kol_n","0 as sum_n","0 as price_n","sum(round(P.kol,3)) as kol_pri","sum(round(P.kol,3)*round(P.price,2)) sum_pri","0 as price_pri","sum(round(R.kol,3)) kol_ras","sum(round(R.kol,3)*round(R.price,2)) as sum_ras","0 as price_ras",
    			"0 as kol_n","0 as sum_n","0 as price_n","sumkp as kol_pri","sumsp sum_pri","CASE sumkp WHEN 0 then 0 ELSE round(sumsp/sumkp,2) END as price_pri","sumkr kol_ras","sumsr as sum_ras","CASE sumkr WHEN 0 then 0 ELSE round(sumsr/sumkr,2) END as price_ras",
    			"O.kol kol_k","ifnull(O.sumka,0) as sum_k","CASE O.kol WHEN 0 then 0 ELSE round(O.sumka/O.kol,2) END as price_k"
}, 
str[0].toString(), null,null,null,"O.id_tmc, O.keg");

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Оборотная ведомость "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;

out = new FileOutputStream(file);

//создание самого excel файла в памяти
HSSFWorkbook workbook = new HSSFWorkbook();

//HSSFDataFormat df = workbook.createDataFormat();
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
//styleN2.setFillForegroundColor(HSSFColor.LIME.index);
//styleN2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
//создание листа с названием "Просто лист"
HSSFSheet sheet = workbook.createSheet("Лист1");
// и применяем к этому стилю жирный шрифт
//style.setFont(font);
//style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
// заполняем список какими-то данными
//List<DataModel> dataList = fillData();
// счетчик для строк
int rowNum = 0;
// создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
HSSFRow row = sheet.createRow(rowNum);
/*XSSFCellStyle style2 = workbook.createCellStyle();
style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
style2.setVerticalAlignment( 
XSSFCellStyle.VERTICAL_CENTER);*/
//cell.setCellValue("Center Aligned"); 
//cell.setCellStyle(style2);
//создаем шрифт
HSSFFont font = workbook.createFont();
// указываем, что хотим его видеть жирным
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
// создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
// и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
//style.setAlignment(HorizontalAlignment.FILL);
//style.setVerticalAlignment(VerticalAlignment.CENTER);

row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("ЕД.ИЗМ");
row.createCell(5).setCellValue("КОЛ-ВО НА "+MainActivity.getStringData(dat1) );
row.createCell(6).setCellValue("СУММА НА "+MainActivity.getStringData(dat1));
row.createCell(7).setCellValue("СРЕДНЯЯ ЦЕНА НА "+MainActivity.getStringData(dat1));
row.createCell(8).setCellValue("КОЛ-ВО ПРИХОД");
row.createCell(9).setCellValue("СУММА ПРИХОД");
row.createCell(10).setCellValue("КОЛ-ВО РАСХОД");
row.createCell(11).setCellValue("СУММА РАСХОД");
row.createCell(12).setCellValue("КОЛ-ВО НА "+MainActivity.getStringData(dat2));
row.createCell(13).setCellValue("СУММА НА "+MainActivity.getStringData(dat2));
row.createCell(14).setCellValue("СРЕДНЯЯ ЦЕНА НА "+MainActivity.getStringData(dat2));
row.createCell(15).setCellValue("ЦЕНА ПРОДАЖИ НА "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(16).setCellValue("КЕГА");
for (int i=0; i<17; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
double koln=0, sumn=0, pricen=0, pricek=0, sumk=0,kolk=0;
if (cc.moveToFirst())  
do {
koln=0; sumn=0; pricen=0; pricek=0; sumk=0; kolk=0;
koln = cc.getDouble(cc.getColumnIndex("kol_k"))+cc.getDouble(cc.getColumnIndex("kol_ras"))-cc.getDouble(cc.getColumnIndex("kol_pri")) ;
sumn = cc.getDouble(cc.getColumnIndex("sum_k"))+cc.getDouble(cc.getColumnIndex("sum_ras"))-cc.getDouble(cc.getColumnIndex("sum_pri")) ;
if (koln!=0) pricen= MainActivity.round2(sumn/koln);

kolk=cc.getDouble(cc.getColumnIndex("kol_k"));
sumk=cc.getDouble(cc.getColumnIndex("sum_k"));
if (kolk!=0) pricek=MainActivity.round2(sumk/kolk);
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("pgrname")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("pname")));
row.createCell(4).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(5).setCellValue(koln); row.getCell(5).setCellStyle(styleN3);
row.createCell(6).setCellValue(sumn);row.getCell(6).setCellStyle(styleN2);
row.createCell(7).setCellValue(pricen);row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellValue(cc.getDouble(cc.getColumnIndex("kol_pri")));row.getCell(8).setCellStyle(styleN3);
row.createCell(9).setCellValue(cc.getDouble(cc.getColumnIndex("sum_pri")));row.getCell(9).setCellStyle(styleN2);
row.createCell(10).setCellValue(cc.getDouble(cc.getColumnIndex("kol_ras")));row.getCell(10).setCellStyle(styleN3);
row.createCell(11).setCellValue(cc.getDouble(cc.getColumnIndex("sum_ras")));row.getCell(11).setCellStyle(styleN2);
row.createCell(12).setCellValue(kolk);row.getCell(12).setCellStyle(styleN3);
row.createCell(13).setCellValue(sumk);row.getCell(13).setCellStyle(styleN2);
row.createCell(14).setCellValue(pricek);row.getCell(14).setCellStyle(styleN2);
row.createCell(15).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(15).setCellStyle(styleN2);
row.createCell(16).setCellValue(cc.getString(cc.getColumnIndex("kkeg")));
} while (cc.moveToNext());

cc.close();

rowNum++;
//

row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(5).setCellFormula("SUM(F2:F"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(5));
row.createCell(6).setCellFormula("SUM(G2:G"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(6));
row.createCell(8).setCellFormula("SUM(I2:I"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(8));
row.createCell(9).setCellFormula("SUM(J2:J"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(9));
row.createCell(10).setCellFormula("SUM(K2:K"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(10));
row.createCell(11).setCellFormula("SUM(L2:L"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(11));
row.createCell(12).setCellFormula("SUM(M2:M"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(12));
row.createCell(13).setCellFormula("SUM(N2:N"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(13));
//rowNum++;
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q"+rowNum));
//sheet.setAutoFilter(new CellRangeAddress(firstCell.getRow(), lastCell.getRow(), firstCell.getCol(), lastCell.getCol()));
//sheet.autoSizeColumn(2);
     //workbook.close();
    //sheet.autoSizeColumn(2,true);sheet.autoSizeColumn(3,true);sheet.autoSizeColumn(4,true);
    workbook.write(out);
    out.close();
} catch (FileNotFoundException ef) {
    ef.printStackTrace();// out.close();
}
catch (IOException e) {
    e.printStackTrace();// out.close();
}

return file;
}

static File brakmove (int pgr, String dirN) {
	/*String []str = {pgr==0?"":" TP._id="+pgr,
			dat1==0?"":" substr(R.data_ins,1,6)>=trim("+dat1+")",
			dat2==0?"":" substr(R.data_ins,1,6)<=trim("+dat2+")"
			};
			String where=str[0].toString();
			if (where.length()==0) where=" ifnull(R.ok,0)=0 "; else where=where+" and ifnull(R.ok,0)=0 ";
			if (where.length()==0) where=str[1].toString(); else where=where+" and "+str[1].toString();
			if (where.length()==0) where=str[2].toString(); else where=where+" and "+str[2].toString();*/
				//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getQueryData( 
			"rasxod as R "
			+ " left join tmc as T on R.id_tmc=T._id left join tmc_ed as E on R.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id left join postav as POS on R.id_post=POS._id ",
			new String[] {"R._id as _id",
			"R.id_tmc as id_tmc","R.keg as keg","T.name as name","E.name as ted","TP.name namet","POS.name as namep",
			//"O.sumka+R.sumkr-P.sumkp as kol_n","O.sumka+R.sumsr-P.sumsp as sum_n","0 as price_n",
			"R.prim as prim", //"'чек№'||K._id||' на сумму '||K.sumka as chek",//"0 as price_pri",
			"R.kol*R.price as sumka","R.data_ins as data_ins",//"0 as price_ras",
			"R.kol as kol","R.price as price","R.skidka as skidka", "R.kol*R.price-R.skidka as sumkaskidka"
			}, 
			"R.ok in (1,2)", null,null,null,"R.prim,TP.name,R.keg");

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Перемещение и брак "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("КОЛ-ВО");
row.createCell(5).setCellValue("ЕД.ИЗМ");
row.createCell(6).setCellValue("СУММА");
//row.createCell(7).setCellValue("СКИДКА");
//row.createCell(8).setCellValue("СУММА СО СКИДКОЙ");
row.createCell(7).setCellValue("ЦЕНА");
row.createCell(8).setCellValue("ПРИМЕЧАНИЕ");
//row.createCell(11).setCellValue("ИД№");
//row.createCell(12).setCellValue("ЧЕК");
row.createCell(9).setCellValue("ДАТА");
row.createCell(10).setCellValue("КЕГА");
for (int i=0; i<11; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("namet")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("namep")));
row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("kol")));row.getCell(4).setCellStyle(styleN3);
row.createCell(5).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(6).setCellValue(cc.getDouble(cc.getColumnIndex("sumka")));row.getCell(6).setCellStyle(styleN2);
//row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("skidka")));row.getCell(7).setCellStyle(styleN2);
//row.createCell(8).setCellValue(cc.getDouble(cc.getColumnIndex("sumkaskidka")));row.getCell(8).setCellStyle(styleN2);
row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellValue(cc.getString(cc.getColumnIndex("prim")));
//row.createCell(11).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
//row.createCell(12).setCellValue(cc.getString(cc.getColumnIndex("chek")));
row.createCell(9).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));
row.createCell(10).setCellValue(cc.getInt(cc.getColumnIndex("keg")));
} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
row.createCell(6).setCellFormula("SUM(G2:G"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(6));row.getCell(6).setCellStyle(styleN2);
//row.createCell(7).setCellFormula("SUM(H2:H"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(7));row.getCell(7).setCellStyle(styleN2);
//row.createCell(8).setCellFormula("SUM(I2:I"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(8));row.getCell(8).setCellStyle(styleN2);
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:N"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}

static File rasxod (int dat1, int dat2, int pgr, String dirN) {
	String []str = {pgr==0?"":" TP._id="+pgr,
			dat1==0?"":" substr(R.data_ins,1,6)>=trim("+dat1+")",
			dat2==0?"":" substr(R.data_ins,1,6)<=trim("+dat2+")"
			};
			String where=str[0].toString();
			if (where.length()==0) where=" ifnull(R.ok,0)=0 "; else where=where+" and ifnull(R.ok,0)=0 ";
			if (where.length()==0) where=str[1].toString(); else where=where+" and "+str[1].toString();
			if (where.length()==0) where=str[2].toString(); else where=where+" and "+str[2].toString();
				//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getQueryData( 
			"rasxod as R "
			+ " left join tmc as T on R.id_tmc=T._id left join tmc_ed as E on R.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id left join postav as POS on R.id_post=POS._id left join klient as K on R.id_klient=K._id",
			new String[] {"R._id as _id",
			"R.id_tmc as id_tmc","R.keg as keg","T.name as name","E.name as ted","TP.name namet","POS.name as namep",
			//"O.sumka+R.sumkr-P.sumkp as kol_n","O.sumka+R.sumsr-P.sumsp as sum_n","0 as price_n",
			"R.prim as prim", "'чек№'||K._id||' на сумму '||K.sumka as chek",//"0 as price_pri",
			"R.kol*R.price as sumka","R.data_ins as data_ins",//"0 as price_ras",
			"R.kol as kol","R.price as price","R.skidka as skidka", "R.kol*R.price-R.skidka as sumkaskidka"
			}, 
			where, null,null,null,"K._id,TP.name,R.keg");

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Расход подробно "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("КОЛ-ВО");
row.createCell(5).setCellValue("ЕД.ИЗМ");
row.createCell(6).setCellValue("СУММА БЕЗ СКИДКИ");
row.createCell(7).setCellValue("СКИДКА");
row.createCell(8).setCellValue("СУММА СО СКИДКОЙ");
row.createCell(9).setCellValue("ЦЕНА");
row.createCell(10).setCellValue("ПРИМЕЧАНИЕ");
row.createCell(11).setCellValue("ИД№");
row.createCell(12).setCellValue("ЧЕК");
row.createCell(13).setCellValue("ДАТА");
row.createCell(14).setCellValue("КЕГА");
for (int i=0; i<15; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("namet")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("namep")));
row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("kol")));row.getCell(4).setCellStyle(styleN3);
row.createCell(5).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(6).setCellValue(cc.getDouble(cc.getColumnIndex("sumka")));row.getCell(6).setCellStyle(styleN2);
row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("skidka")));row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellValue(cc.getDouble(cc.getColumnIndex("sumkaskidka")));row.getCell(8).setCellStyle(styleN2);
row.createCell(9).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(9).setCellStyle(styleN2);
row.createCell(10).setCellValue(cc.getString(cc.getColumnIndex("prim")));
row.createCell(11).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
row.createCell(12).setCellValue(cc.getString(cc.getColumnIndex("chek")));
row.createCell(13).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));
row.createCell(14).setCellValue(cc.getInt(cc.getColumnIndex("keg")));
} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
row.createCell(6).setCellFormula("SUM(G2:G"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(6));row.getCell(6).setCellStyle(styleN2);
row.createCell(7).setCellFormula("SUM(H2:H"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(7));row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellFormula("SUM(I2:I"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(8));row.getCell(8).setCellStyle(styleN2);
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:N"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}

static File otchet_del (int dat1, int dat2, String dirN) {
	String []str = {//pgr==0?"":" TP._id="+pgr,
			dat1==0?"":" substr(R.data_del,1,6)>=trim("+dat1+")",
			dat2==0?"":" substr(R.data_del,1,6)<=trim("+dat2+")"
			};
			String where=str[0].toString();
			if (where.length()==0) where=str[1].toString(); else where=where+" and "+str[1].toString();
			//if (where.length()==0) where=str[2].toString(); else where=where+" and "+str[2].toString();
			//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getQueryData( 
			"rasxod_del as R "
			+ " left join tmc as T on R.id_tmc=T._id left join tmc_ed as E on R.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id left join postav as POS on R.id_post=POS._id left join klient as K on R.id_klient=K._id",
			new String[] {"R._id as _id",
			"R.id_tmc as id_tmc","R.keg as keg","T.name as name","E.name as ted","TP.name namet","POS.name as namep",
			//"O.sumka+R.sumkr-P.sumkp as kol_n","O.sumka+R.sumsr-P.sumsp as sum_n","0 as price_n",
			"R.prim as prim", "'чек№'||K._id||' на сумму '||K.sumka as chek",//"0 as price_pri",
			"R.kol*R.price as sumka","R.data_ins as data_ins",//"0 as price_ras",
			"R.kol as kol","R.price as price","R.skidka as skidka", "R.kol*R.price-R.skidka as sumkaskidka"
			}, 
			where, null,null,null,null);

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Удаленные позиции "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("КОЛ-ВО");
row.createCell(5).setCellValue("ЕД.ИЗМ");
row.createCell(6).setCellValue("СУММА БЕЗ СКИДКИ");
row.createCell(7).setCellValue("СКИДКА");
row.createCell(8).setCellValue("СУММА СО СКИДКОЙ");
row.createCell(9).setCellValue("ЦЕНА");
row.createCell(10).setCellValue("ПРИМЕЧАНИЕ");
row.createCell(11).setCellValue("ИД№");
row.createCell(12).setCellValue("ЧЕК");
row.createCell(13).setCellValue("ДАТА");
row.createCell(14).setCellValue("КЕГА");
for (int i=0; i<15; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("namet")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("namep")));
row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("kol")));row.getCell(4).setCellStyle(styleN3);
row.createCell(5).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(6).setCellValue(cc.getDouble(cc.getColumnIndex("sumka")));row.getCell(6).setCellStyle(styleN2);
row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("skidka")));row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellValue(cc.getDouble(cc.getColumnIndex("sumkaskidka")));row.getCell(8).setCellStyle(styleN2);
row.createCell(9).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(9).setCellStyle(styleN2);
row.createCell(10).setCellValue(cc.getString(cc.getColumnIndex("prim")));
row.createCell(11).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
row.createCell(12).setCellValue(cc.getString(cc.getColumnIndex("chek")));
row.createCell(13).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));
row.createCell(14).setCellValue(cc.getInt(cc.getColumnIndex("keg")));
} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
row.createCell(6).setCellFormula("SUM(G2:G"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(6));row.getCell(6).setCellStyle(styleN2);
row.createCell(7).setCellFormula("SUM(H2:H"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(7));row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellFormula("SUM(I2:I"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(8));row.getCell(8).setCellStyle(styleN2);
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:N"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}

static File check (int dat1, int dat2, String dirN) {
	String []str = {
			dat1==0?"":" substr(K.data_ins,1,6)>=trim("+dat1+")",
			dat2==0?"":" substr(K.data_ins,1,6)<=trim("+dat2+")"
			};
			String where=str[0].toString();
			if (where.length()==0) where=str[1].toString(); else where=where+" and "+str[1].toString();
			//if (where.length()==0) where=str[2].toString(); else where=where+" and "+str[2].toString();
				//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cur = MainActivity.db.getQueryData("klient as K left join karta_klient KK on K.karta = KK._id", 
         			new String[] {"K._id as _id","K.num_id as num_id","K.sumka as sumka", "K.skidka as skidka", "K.name as name","K.prim as prim","K.data_ins as data_ins","KK._id||' '||KK.name||'-'||KK.sconto_per as karta"}, 
         			 //"TP.pgr = ?"
        			 where, null,null,null,null);

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Чеки "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("ИД ЧЕКА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("№ ЧЕКА");
row.createCell(2).setCellValue("ДАТА");
row.createCell(3).setCellValue("СУММА");
row.createCell(4).setCellValue("СКИДКА");
row.createCell(5).setCellValue("ИМЯ");
row.createCell(6).setCellValue("ПРИМЕЧАНИЕ");
row.createCell(7).setCellValue("КАРТА");
for (int i=0; i<8; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cur.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cur.getInt(cur.getColumnIndex("_id")));
row.createCell(1).setCellValue(cur.getInt(cur.getColumnIndex("num_id")));
row.createCell(2).setCellValue(MainActivity.getStringDataTime( cur.getInt(cur.getColumnIndex("data_ins")) ));
row.createCell(3).setCellValue(cur.getDouble(cur.getColumnIndex("sumka")));row.getCell(3).setCellStyle(styleN2);
row.createCell(4).setCellValue(cur.getDouble(cur.getColumnIndex("skidka")));row.getCell(4).setCellStyle(styleN2);
row.createCell(5).setCellValue(cur.getString(cur.getColumnIndex("name")));
row.createCell(6).setCellValue(cur.getString(cur.getColumnIndex("prim")));
row.createCell(7).setCellValue(cur.getString(cur.getColumnIndex("karta")));
} while (cur.moveToNext());
cur.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN2);
row.createCell(3).setCellFormula("SUM(D2:D"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(3));row.getCell(3).setCellStyle(styleN2);
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:M"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}

static File rasxod_ostat (int dat1, int dat2, int pgr, String dirN) {
	String []str = {pgr==0?" 1=1 ":" TT._id="+pgr,
			dat1==0?" 1=1 ":" substr(T.data_ins,1,6)>=trim("+dat1+")",
			dat2==0?" 1=1 ":" substr(T.data_ins,1,6)<=trim("+dat2+")"
			};
			//String where=str[0].toString();
			//if (where.length()==0) where=str[1].toString(); else where=where+" and "+str[1].toString();
			//if (where.length()==0) where=str[2].toString(); else where=where+" and "+str[2].toString();
			//if (where.equals("")||where.length()==0) where=" T.ok=0 "; else where=where+" and T.ok=0 ";
				//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = 
					 MainActivity.db.getRawData 
			         ("select kkeg,keg,_id,pgr,name,post,kol,ed,sumka,price,skidka,ostat,sumkanonal,sumkanal,brak,move from "+
			                 "("+
			                 "select sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*T.kol,0),2) END else 0 end) as sumkanonal, sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*T.kol,0),2) ELSE 0 END else 0 end) as sumkanal, K.keg as kkeg, K.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,sum(ifnull(T.kol,0)) as kol,E.name as ed,sum(T.price*T.kol) as sumka,CASE ifnull(sum(T.kol),0) WHEN 0 THEN 0 ELSE round(sum(T.price*T.kol)/sum(T.kol),2) END as price,round(sum(ifnull(T.skidka,0)),2) as skidka, round(K.kol,3) as ostat, "+ 
			                 "sum(ifnull(case T.ok when 1 then T.kol_brak else 0 end,0)) as brak, sum(ifnull(case T.ok when 2 then T.kol_move else 0 end,0)) as move "+ 
			                 "from ostat as K left join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and "+str[1]+" and "+str[2]+ 
			                 " left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left join postav as KK on K.id_post=KK._id "+
			                 "where K.kol<>0 and "+str[0]+
			                 " group by K.keg, K.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')', TP._id,TT.name,TP.name,KK.name,E.name,round(K.kol,3) "+ 
			                 " union all "+
			                 "select sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN 0 ELSE round(ifnull(T.price*T.kol,0),2) END else 0 end) as sumkanonal, sum(case T.ok when 0 then CASE ifnull(T.cash,0) WHEN 0 THEN round(ifnull(T.price*T.kol,0),2) ELSE 0 END else 0 end) as sumkanal, K.keg as kkeg, K.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')' as keg, TP._id as _id,TT.name as pgr,TP.name as name,KK.name as post,sum(ifnull(T.kol,0)) as kol,E.name as ed,sum(T.price*T.kol) as sumka,CASE ifnull(sum(T.kol),0) WHEN 0 THEN 0 ELSE round(sum(T.price*T.kol)/sum(T.kol),2) END as price,round(sum(ifnull(T.skidka,0)),2) as skidka, round(K.kol,3) as ostat, "+ 
			                 "sum(ifnull(case T.ok when 1 then T.kol_brak else 0 end,0)) as brak, sum(ifnull(case T.ok when 2 then T.kol_move else 0 end,0)) as move "+ 
			                 "from ostat as K left join rasxod as T on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.ed=K.ed and T.keg=K.keg and "+str[1]+" and "+str[2]+ 
			                 " left join tmc as TP on K.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on K.ed = E._id left join postav as KK on K.id_post=KK._id "+
			                 "where K.kol=0 and "+str[0]+
			                 " group by K.keg, K.keg||' ('||substr(ifnull(K.data_upd,K.data_ins),5,2)||'.'||substr(ifnull(K.data_upd,K.data_ins),3,2)||')', TP._id,TT.name,TP.name,KK.name,E.name,round(K.kol,3) "+ 
			                 ") "+
			                 "where ifnull(ostat,0)+ifnull(kol,0)!=0 order by name, keg"
			                 ,null);
					/*MainActivity.db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id left join ostat as K on T.id_tmc=K.id_tmc and T.id_post = K.id_post and T.keg=K.keg and T.ed=K.ed left join postav as KK on T.id_post=KK._id", 
         			new String[] {"TP._id as _id","T.keg as keg","TT.name as pgr","TP.name as name","KK.name as post","sum(T.kol) as kol","E.name as ed",
        			 "sum(T.price*T.kol-T.skidka) as sumka","K.kol as ostat","T.price as price", "round(sum(T.skidka),2) as skidka"}, 
         			 //"TP.pgr = ?"
        			 where, null,"TP._id, T.keg, TT.name, TP.name, T.price, KK.name, E.name, K.kol",null,"TP.name,K.keg");*/

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Расход и остаток "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("ПРОДАНО КОЛ-ВО ЗА ПЕРИОД");
row.createCell(5).setCellValue("БРАК");
row.createCell(6).setCellValue("ПЕРЕМЕЩЕНО");
row.createCell(7).setCellValue("ЕД.ИЗМ");
row.createCell(8).setCellValue("ПРОДАНО СУММА ЗА ПЕРИОД");
row.createCell(9).setCellValue("ПРОДАНО СУММА НАЛ");
row.createCell(10).setCellValue("ПРОДАНО СУММА БЕЗНАЛ");
row.createCell(11).setCellValue("ОСТАТОК НА "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(12).setCellValue("СУММА СКИДКИ");
row.createCell(13).setCellValue("ЦЕНА ПРОДАЖИ");
row.createCell(14).setCellValue("КЕГА");
for (int i=0; i<15; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("pgr")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("post")));
row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("kol")));row.getCell(4).setCellStyle(styleN3);
row.createCell(5).setCellValue(cc.getDouble(cc.getColumnIndex("brak")));row.getCell(5).setCellStyle(styleN3);
row.createCell(6).setCellValue(cc.getDouble(cc.getColumnIndex("move")));row.getCell(6).setCellStyle(styleN3);
row.createCell(7).setCellValue(cc.getString(cc.getColumnIndex("ed")));
row.createCell(8).setCellValue(cc.getDouble(cc.getColumnIndex("sumka")));row.getCell(8).setCellStyle(styleN2);
row.createCell(9).setCellValue(cc.getDouble(cc.getColumnIndex("sumkanal")));row.getCell(9).setCellStyle(styleN2);
row.createCell(10).setCellValue(cc.getDouble(cc.getColumnIndex("sumkanonal")));row.getCell(10).setCellStyle(styleN2);
row.createCell(11).setCellValue(cc.getDouble(cc.getColumnIndex("ostat")));row.getCell(11).setCellStyle(styleN3);

row.createCell(12).setCellValue(cc.getDouble(cc.getColumnIndex("skidka")));row.getCell(12).setCellStyle(styleN2);
row.createCell(13).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(13).setCellStyle(styleN2);
row.createCell(14).setCellValue(cc.getInt(cc.getColumnIndex("keg")));
} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
row.createCell(5).setCellFormula("SUM(F2:F"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(5));row.getCell(5).setCellStyle(styleN3);
row.createCell(6).setCellFormula("SUM(G2:G"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(6));row.getCell(6).setCellStyle(styleN3);

row.createCell(8).setCellFormula("SUM(J2:J"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(8));row.getCell(8).setCellStyle(styleN2);
row.createCell(9).setCellFormula("SUM(K2:K"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(9));row.getCell(9).setCellStyle(styleN2);
row.createCell(10).setCellFormula("SUM(L2:L"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(10));row.getCell(10).setCellStyle(styleN2);
row.createCell(12).setCellFormula("SUM(M2:M"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(12));row.getCell(12).setCellStyle(styleN2);
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:O"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}

static File prixod (int dat1, int dat2, int pgr, String dirN) {
	String []str = {pgr==0?"":" TT._id="+pgr,
			dat1==0?"":" substr(T.data_ins,1,6)>=trim("+dat1+")",
			dat2==0?"":" substr(T.data_ins,1,6)<=trim("+dat2+")"
			};
			String where=str[0].toString();
			if (where.length()==0) where=str[1].toString(); else where=where+" and "+str[1].toString();
			if (where.length()==0) where=str[2].toString(); else where=where+" and "+str[2].toString();
				//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getQueryData("prixod as T left join tmc as TP on T.id_tmc = TP._id left join postav as P on T.id_post = P._id left join tmc_ed as E on T.ed = E._id left join tmc_pgr as TT on TP.pgr=TT._id", 
	     			new String[] {"T._id as _id","T.id_tmc as id_tmc","T.keg as keg","TP.name as name","T.data_ins as data_ins","T.kol as kol","T.kol*T.price as sumka",
	    			 "E.name as ted", "T.ed as ed","T.price as price","P.name as pname","T.prim as prim","T.id_post as id_post","TT.name as pgr"}, 
	     			 where,//"TP.pgr = ?",// and ?",
	     			null,null,null,"TP.name,T.keg");

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Приход "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("КОЛ-ВО");
row.createCell(5).setCellValue("ЕД.ИЗМ");
row.createCell(6).setCellValue("ЦЕНА ПОСТАВЩИКА");
row.createCell(7).setCellValue("СУММА");
row.createCell(8).setCellValue("ПРИМЕЧАНИЕ");
row.createCell(9).setCellValue("ИД№");
row.createCell(10).setCellValue("ДАТА");
row.createCell(11).setCellValue("КЕГА");
for (int i=0; i<12; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("pgr")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("pname")));
row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("kol")));row.getCell(4).setCellStyle(styleN3);
row.createCell(5).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(6).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(6).setCellStyle(styleN2);
row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("sumka")));row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellValue(cc.getString(cc.getColumnIndex("prim")));
row.createCell(9).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
row.createCell(10).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));
row.createCell(11).setCellValue(cc.getInt(cc.getColumnIndex("keg")));
} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
row.createCell(7).setCellFormula("SUM(H2:H"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(7));row.getCell(7).setCellStyle(styleN2);
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:L"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}

static File ostat (int pgr, String dirN) {
	//String []str = {pgr==0?"":" TT._id="+pgr};
			//String where=str[0].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getRawData (
	    			"select O._id as _id, O.id_tmc as id_tmc, O.keg as keg, O.kol as kol, E.name as ted, TT.price as price, O.id_post as id_post, ifnull(O.data_upd,O.data_ins) as data_ins, "
	    	    			+ "P.name as pname, T.name as tname, TP.name as pgr, O.kol*TT.price as sumka, O.kol_nedo as kol_nedo, O.kol_izl as kol_izl "
	    	    			+ "from ostat as O "
	    	    			+ "left join tmc_price as TT "
	    	    			+ "on O.id_tmc=TT.id_tmc and O.id_post=TT.id_post and O.ed=TT.ed "
	    	    			+ "left join tmc as T "
	    	    			+ "on O.id_tmc=T._id "
	    	    			+ "left join postav as P "
	    	    			+ "on O.id_post=P._id "
	    	    			+ "left join tmc_ed as E "
	    	    			+ "on O.ed=E._id "
	    	    			+ "left join tmc_pgr as TP "
	    	    			+ "on T.pgr=TP._id "
	    	    			+ "where (O.kol<>0 or O.kol_izl<>0 or O.kol_nedo<>0) "+((pgr==0)?"":(" and T.pgr="+pgr)) // +"? and ?"
	    	    			, null);

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Остаток на "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
//style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("КОЛ-ВО");
row.createCell(5).setCellValue("ЕД.ИЗМ");
row.createCell(6).setCellValue("ЦЕНА ПРОДАЖИ");
row.createCell(7).setCellValue("СУММА");
row.createCell(8).setCellValue("ИД№");
row.createCell(9).setCellValue("ДАТА ПОСЛЕДНЕГО ИЗМЕНЕНИЯ");
row.createCell(10).setCellValue("КЕГА");
row.createCell(11).setCellValue("НЕДОСТАТКИ");
row.createCell(12).setCellValue("ИЗЛИШКИ");
for (int i=0; i<13; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("pgr")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("tname")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("pname")));
row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("kol")));row.getCell(4).setCellStyle(styleN3);
row.createCell(5).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(6).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(6).setCellStyle(styleN2);
row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("sumka")));row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
row.createCell(9).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));
row.createCell(10).setCellValue(cc.getInt(cc.getColumnIndex("keg")));
row.createCell(11).setCellValue(cc.getDouble(cc.getColumnIndex("kol_nedo")));row.getCell(11).setCellStyle(styleN3);
row.createCell(12).setCellValue(cc.getDouble(cc.getColumnIndex("kol_izl")));row.getCell(12).setCellStyle(styleN3);
} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
row.createCell(7).setCellFormula("SUM(H2:H"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(7));row.getCell(7).setCellStyle(styleN2);

sheet.setAutoFilter(CellRangeAddress.valueOf("A1:N"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}
/*
static File invent (int pgr, String dirN) {
	//String []str = {pgr==0?"":" TT._id="+pgr};
			//String where=str[0].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getRawData (
					"select O.id_tmc id_tmc, O.name_tmc name_tmc, O.name_pgr name_pgr, O.keg keg, "
			    			+ "O.kol_ostat kol_ostat, O.kol_real kol_real, "
			    			+ "O.kol_n kol_n, O.summa_n summa_n, "
			    			+ "O.kol_p kol_p, O.summa_p summa_p, O.kol_r kol_r, O.summa_r summa_r, "
			    			+ "O.kol_brak kol_brak, O.summa_brak summa_brak, O.kol_move kol_move, O.summa_move summa_move, O.kol_izl kol_izl, O.summa_izl summa_izl, O.kol_nedo kol_nedo, O.summa_nedo summa_nedo, O.kol_skidka kol_skidka, O.summa_skidka summa_skidka, "
			    			+ "O.kol_k kol_k, O.summa_k summa_k, "
			    			+ "O.ed ed, E.name edname, O.price price, O.price_vendor price_vendor, P.name postname, O.prim prim, O.data_ins data_ins, O.ok ok "
			    			+ "from invent as O "			
			    			+ "left join tmc as T "
			    			+ "on O.id_tmc=T._id "
			    			+ "left join postav as P "
			    			+ "on O.id_post=P._id "
			    			+ "left join tmc_ed as E "
			    			+ "on O.ed=E._id "
			    			+ "left join tmc_pgr as TP "
			    			+ "on O.pgr=TP._id "
			    			+ "where id_inv= "+pgr + " order by pgr, name_tmc, id_post, keg"
	    	    			, null);
			Cursor cc1 = MainActivity.db.getRawData (
					"select id_user, user, name, summa, dat_n, dat_k, prim, data_ins, ok "
			    			+ "from invent "
			    			+ "where _id= "+pgr
	    	    			, null);
			

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()) {
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}

if (cc1.moveToFirst())  
do {
	file   =   new File(dir, "Инвентаризация "+pgr+" ("+cc1.getString(cc1.getColumnIndex("user"))+") "+MainActivity.getStringDataTime( cc1.getInt(cc1.getColumnIndex("dat_n")) )+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+MainActivity.getStringDataTime( cc1.getInt(cc1.getColumnIndex("dat_k")) )+".xls");
	} 
while (cc1.moveToNext());
cc1.close();

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("КЕГА");
row.createCell(5).setCellValue("КОЛ-ВО ОСТАТКА");
row.createCell(6).setCellValue("РЕАЛЬНОЕ КОЛ-ВО");
row.createCell(7).setCellValue("ЕД.ИЗМ");
row.createCell(8).setCellValue("ЦЕНА ПРОДАЖИ");
row.createCell(9).setCellValue("КОЛ-ВО НАЧАЛО");
row.createCell(10).setCellValue("СУММА НАЧАЛО");
row.createCell(11).setCellValue("КОЛ-ВО ПРИХОД");
row.createCell(12).setCellValue("СУММА ПРИХОД");
row.createCell(13).setCellValue("КОЛ-ВО РАСХОД");
row.createCell(14).setCellValue("СУММА РАСХОД");
row.createCell(15).setCellValue("КОЛ-ВО БРАК");
row.createCell(16).setCellValue("СУММА БРАК");
row.createCell(17).setCellValue("КОЛ-ВО ПЕРЕМЕЩЕНО");
row.createCell(18).setCellValue("СУММА ПЕРЕМЕЩЕНО");
row.createCell(19).setCellValue("КОЛ-ВО ИЗЛИШКИ");
row.createCell(20).setCellValue("СУММА ИЗЛИШКИ");
row.createCell(21).setCellValue("КОЛ-ВО НЕДОСТАЧИ");
row.createCell(22).setCellValue("СУММА НЕДОСТАЧИ");
row.createCell(23).setCellValue("СУММА СКАДКИ");
row.createCell(24).setCellValue("КОЛ-ВО КОНЕЦ");
row.createCell(25).setCellValue("СУММА КОНЕЦ");

for (int i=0; i<25; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("name_pgr")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name_tmc")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("postname")));
row.createCell(4).setCellValue(cc.getInt(cc.getColumnIndex("keg")));

row.createCell(5).setCellValue(cc.getDouble(cc.getColumnIndex("kol_ostat")));row.getCell(5).setCellStyle(styleN3);
row.createCell(6).setCellValue(cc.getDouble(cc.getColumnIndex("kol_real")));row.getCell(6).setCellStyle(styleN3);
row.createCell(7).setCellValue(cc.getString(cc.getColumnIndex("edname")));
row.createCell(8).setCellValue(cc.getDouble(cc.getColumnIndex("price_vendor")));row.getCell(8).setCellStyle(styleN2);

row.createCell(9).setCellValue(cc.getDouble(cc.getColumnIndex("kol_n")));row.getCell(9).setCellStyle(styleN3);
row.createCell(10).setCellValue(cc.getDouble(cc.getColumnIndex("summa_n")));row.getCell(10).setCellStyle(styleN2);

row.createCell(11).setCellValue(cc.getDouble(cc.getColumnIndex("kol_p")));row.getCell(11).setCellStyle(styleN3);
row.createCell(12).setCellValue(cc.getDouble(cc.getColumnIndex("summa_p")));row.getCell(12).setCellStyle(styleN2);

row.createCell(13).setCellValue(cc.getDouble(cc.getColumnIndex("kol_r")));row.getCell(13).setCellStyle(styleN3);
row.createCell(14).setCellValue(cc.getDouble(cc.getColumnIndex("summa_r")));row.getCell(14).setCellStyle(styleN2);

row.createCell(15).setCellValue(cc.getDouble(cc.getColumnIndex("kol_brak")));row.getCell(15).setCellStyle(styleN3);
row.createCell(16).setCellValue(cc.getDouble(cc.getColumnIndex("summa_brak")));row.getCell(16).setCellStyle(styleN2);

row.createCell(17).setCellValue(cc.getDouble(cc.getColumnIndex("kol_move")));row.getCell(17).setCellStyle(styleN3);
row.createCell(18).setCellValue(cc.getDouble(cc.getColumnIndex("summa_move")));row.getCell(18).setCellStyle(styleN2);

row.createCell(19).setCellValue(cc.getDouble(cc.getColumnIndex("kol_izl")));row.getCell(19).setCellStyle(styleN3);
row.createCell(20).setCellValue(cc.getDouble(cc.getColumnIndex("summa_izl")));row.getCell(20).setCellStyle(styleN2);

row.createCell(21).setCellValue(cc.getDouble(cc.getColumnIndex("kol_nedo")));row.getCell(21).setCellStyle(styleN3);
row.createCell(22).setCellValue(cc.getDouble(cc.getColumnIndex("summa_nedo")));row.getCell(22).setCellStyle(styleN2);
row.createCell(23).setCellValue(cc.getDouble(cc.getColumnIndex("summa_skidka")));row.getCell(23).setCellStyle(styleN2);

row.createCell(24).setCellValue(cc.getDouble(cc.getColumnIndex("kol_k")));row.getCell(24).setCellStyle(styleN3);
row.createCell(25).setCellValue(cc.getDouble(cc.getColumnIndex("summa_k")));row.getCell(25).setCellStyle(styleN2);

} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
//row.createCell(7).setCellFormula("SUM(H2:H"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(7));row.getCell(7).setCellStyle(styleN2);

sheet.setAutoFilter(CellRangeAddress.valueOf("A1:N"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}*/

static File price (int pgr, String dirN) {
	//String []str = {pgr==0?"":" TT._id="+pgr};
			//String where=str[0].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getRawData (
	    			"select O._id as _id, O.id_tmc as id_tmc, E.name as ted, O.price as price, O.id_post as id_post, O.data_ins as data_ins, "
	    	    			+ "P.name as pname, T.name as tname, TP.name as pgr "
	    	    			+ "from tmc_price as O "
	    	    			+ "left join tmc as T "
	    	    			+ "on O.id_tmc=T._id "
	    	    			+ "left join postav as P "
	    	    			+ "on O.id_post=P._id "
	    	    			+ "left join tmc_ed as E "
	    	    			+ "on O.ed=E._id "
	    	    			+ "left join tmc_pgr as TP "
	    	    			+ "on T.pgr=TP._id "
	    	    			+ "where O.price<>0 "+((pgr==0)?"":(" and T.pgr="+pgr)) // +"? and ?"
	    	    			, null);

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Цены на "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("ЕД.ИЗМ");
row.createCell(5).setCellValue("ЦЕНА ПРОДАЖИ");
row.createCell(6).setCellValue("ИД№");
row.createCell(7).setCellValue("ДАТА ПОСЛЕДНЕГО ИЗМЕНЕНИЯ");
for (int i=0; i<8; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("pgr")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("tname")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("pname")));
//row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("kol")));row.getCell(4).setCellStyle(styleN3);
row.createCell(4).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(5).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(5).setCellStyle(styleN2);
//row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("sumka")));row.getCell(7).setCellStyle(styleN2);
row.createCell(6).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
row.createCell(7).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));

} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
//FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
//row.createCell(7).setCellFormula("SUM(H2:H"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(7));row.getCell(7).setCellStyle(styleN2);
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:K"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}

static File all_spr (String dirN) {
	//String []str = {pgr==0?"":" TT._id="+pgr};
			//String where=str[0].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getRawData (
	    			"select O._id as _id, O.name as name, O.pgr as pgr, O.ed as ed, O.price as price, O.data_ins as data_ins, O.vis as vis, O.pos as pos, O.tara as tara, O.ok as ok, "
	    	    			+ "TP.name as namet, E.name as ted "
	    	    			+ "from tmc as O "
	    	    			+ "left join tmc_ed as E "
	    	    			+ "on O.ed=E._id "
	    	    			+ "left join tmc_pgr as TP "
	    	    			+ "on O.pgr=TP._id order by TP._id, O.name"
	    	    			, null);

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Справочник товаров на "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//font.setBold(true);
//создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
//и применяем к этому стилю жирный шрифт
style.setFont(font);
//style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ЕД.ИЗМ");
row.createCell(4).setCellValue("ЦЕНА ПРОДАЖИ");
row.createCell(5).setCellValue("ДЛЯ ПРОДАЖИ");
row.createCell(6).setCellValue("ТАРА");
row.createCell(7).setCellValue("ЗНАЧЕНИЕ ТАРЫ");
row.createCell(8).setCellValue("ПОЗИЦИЯ В ГРУППЕ");
row.createCell(9).setCellValue("ДАТА ПОСЛЕДНЕГО ИЗМЕНЕНИЯ");
for (int i=0; i<10; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);
sheet.createFreezePane(0, 1);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("namet")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(4).setCellStyle(styleN2);
row.createCell(5).setCellValue(cc.getInt(cc.getColumnIndex("vis")));
row.createCell(6).setCellValue(cc.getInt(cc.getColumnIndex("ok")));
row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("tara")));row.getCell(7).setCellStyle(styleN2);
row.createCell(8).setCellValue(cc.getInt(cc.getColumnIndex("pos")));
row.createCell(9).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));

} while (cc.moveToNext());
cc.close();
rowNum++;
sheet.setAutoFilter(CellRangeAddress.valueOf("A1:K"+rowNum));
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
return file;
}
}
/* select O._id as _id, O.name as name, O.pgr as pgr, O.ed as ed, O.price as price, O.data_ins as data_ins, O.vis as vis, O.pos as pos, O.tara as tara, 
 * O.ok as ok, TP.name as namet, E.name as ted from tmp as O left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on O.pgr=TP._id
*/

