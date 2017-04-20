package luce.birra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import android.database.Cursor;
import android.os.Environment;
/*
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
*/

//import android.os.AsyncTask;
/*import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;

import android.os.Environment;
import android.provider.SyncStateContract.Constants;
import android.util.Log;*/
//http://stackoverflow.com/questions/5401104/android-exporting-to-csv-and-sending-as-email-attachment
//http://stackoverflow.com/questions/4632501/android-generate-csv-file-from-table-values
public class /*ExportDatabaseCSVTask*/ Export2Excel //extends AsyncTask<String, String, Boolean> 
{
//http://javadevblog.com/obzor-biblioteki-apache-poi-rabota-s-prilozheniyami-paketa-microsoft-office-v-java.html
//https://habrahabr.ru/sandbox/38101/
	private void saveFile(String filename) {
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
	
static File oborotka (int dat, int pgr, String dirN) {
//////////////////////////////////////
String []str = {pgr==0?"":" TP._id="+pgr, dat==0?"":" where substr(data_ins,1,6)>=trim("+dat+")"};
//String where=str[0].toString();
//if (str[0].length()!=0) where=where+" and "+str[0];
Cursor cc = MainActivity.db.getQueryData( 
"ostat as O left join (select id_tmc, id_post, ed, sum(round(kol,3)) as sumkr, sum(round(kol,3)*round(price,2)) as sumsr from rasxod " + str[1].toString() + 
" group by id_tmc, id_post, ed) as R on O.id_tmc=R.id_tmc and O.id_post=R.id_post and O.ed=R.ed "
+ " left join (select id_tmc, id_post, ed, sum(round(kol,3)) as sumkp, sum(round(kol,3)*round(price,2)) as sumsp from prixod " + str[1].toString() +
" group by id_tmc, id_post, ed) as P on O.id_tmc=P.id_tmc and O.id_post=P.id_post and O.ed=P.ed"
+ " left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id left join postav as POS on O.id_post=POS._id",
new String[] {"O._id as _id",
"O.id_tmc as id_tmc","T.name as name","E.name as ted", "POS.name as pname", "TP.name as pgrname", "T.price as price",
//"O.sumka+R.sumkr-P.sumkp as kol_n","O.sumka+R.sumsr-P.sumsp as sum_n","0 as price_n",
"sumkp as kol_pri","sumsp sum_pri",//"0 as price_pri",
"sumkr kol_ras","sumsr as sum_ras",//"0 as price_ras",
"O.kol kol_k","O.sumka as sum_k"//,"0 as price_k"
}, 
str[0].toString(), null,null,null,null);

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/Oborotka"); //dir.mkdirs();
}
file   =   new File(dir, "O"+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

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
// создаем стиль для ячейки
HSSFCellStyle style = workbook.createCellStyle();
// и применяем к этому стилю жирный шрифт
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_CENTER );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("ЕД.ИЗМ");
row.createCell(5).setCellValue("КОЛ-ВО НА "+MainActivity.getStringData(dat) );
row.createCell(6).setCellValue("СУММА НА "+MainActivity.getStringData(dat));
row.createCell(7).setCellValue("СРЕДНЯЯ ЦЕНА НА "+MainActivity.getStringData(dat));
row.createCell(8).setCellValue("КОЛ-ВО ПРИХОД");
row.createCell(9).setCellValue("СУММА ПРИХОД");
row.createCell(10).setCellValue("КОЛ-ВО РАСХОД");
row.createCell(11).setCellValue("СУММА РАСХОД");
row.createCell(12).setCellValue("КОЛ-ВО НА "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(13).setCellValue("СУММА НА "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(14).setCellValue("СРЕДНЯЯ ЦЕНА НА "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(15).setCellValue("ЦЕНА ПРОДАЖИ НА "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
for (int i=0; i<16; i++) row.getCell(i).setCellStyle(style);
row.setHeight((short)1000);

float koln=0, sumn=0, pricen=0, pricek=0, sumk=0,kolk=0;
if (cc.moveToFirst())  
do {
koln=0; sumn=0; pricen=0; pricek=0; sumk=0; kolk=0;
koln = cc.getFloat(cc.getColumnIndex("kol_k"))+cc.getFloat(cc.getColumnIndex("kol_ras"))-cc.getFloat(cc.getColumnIndex("kol_pri")) ;
sumn = cc.getFloat(cc.getColumnIndex("sum_k"))+cc.getFloat(cc.getColumnIndex("sum_ras"))-cc.getFloat(cc.getColumnIndex("sum_pri")) ;
if (koln!=0) pricen= MainActivity.round(sumn/koln,2);
kolk=cc.getFloat(cc.getColumnIndex("kol_k"));
sumk=cc.getFloat(cc.getColumnIndex("sum_k"));
if (kolk!=0) pricek=MainActivity.round(sumk/kolk,2);
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
row.createCell(8).setCellValue(cc.getFloat(cc.getColumnIndex("kol_pri")));row.getCell(8).setCellStyle(styleN3);
row.createCell(9).setCellValue(cc.getFloat(cc.getColumnIndex("sum_pri")));row.getCell(9).setCellStyle(styleN2);
row.createCell(10).setCellValue(cc.getFloat(cc.getColumnIndex("kol_ras")));row.getCell(10).setCellStyle(styleN3);
row.createCell(11).setCellValue(cc.getFloat(cc.getColumnIndex("sum_ras")));row.getCell(11).setCellStyle(styleN2);
row.createCell(12).setCellValue(kolk);row.getCell(12).setCellStyle(styleN3);
row.createCell(13).setCellValue(sumk);row.getCell(13).setCellStyle(styleN2);
row.createCell(14).setCellValue(pricek);row.getCell(14).setCellStyle(styleN2);
row.createCell(15).setCellValue(cc.getFloat(cc.getColumnIndex("price")));row.getCell(15).setCellStyle(styleN2);
//out.write(dataStr.getBytes());
} while (cc.moveToNext());

cc.close();
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(5).setCellFormula("SUM(F2:F"+rowNum+")"); //row.getCell(5).
row.createCell(6).setCellFormula("SUM(G2:G"+rowNum+")");
row.createCell(8).setCellFormula("SUM(I2:I"+rowNum+")");
row.createCell(9).setCellFormula("SUM(J2:J"+rowNum+")");
row.createCell(10).setCellFormula("SUM(K2:K"+rowNum+")");
row.createCell(11).setCellFormula("SUM(L2:L"+rowNum+")");
row.createCell(12).setCellFormula("SUM(M2:M"+rowNum+")");
row.createCell(13).setCellFormula("SUM(N2:N"+rowNum+")");

sheet.setAutoFilter(CellRangeAddress.valueOf("A1:P1"));
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
/*
Uri u1  =   null;
u1  =   Uri.fromFile(file);

Intent sendIntent = new Intent(Intent.ACTION_SEND);
sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Оборотная ведомость с "+tvDataIns.getText().toString()+" по "+Calendar.getInstance().get(Calendar.DATE)+"."+Calendar.getInstance().get(Calendar.MONTH)+"."+Calendar.getInstance().get(Calendar.YEAR));
sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
sendIntent.setType("text/html");
startActivity(sendIntent);    */
//}
//cc.close();
///////////////////////////////////////////////////////
}
	
static void rasxod (int dat1, int dat2, int pgr, String dirN) {
	String []str = {pgr==0?"":" TP._id="+pgr,
			dat1==0?"":" substr(data_ins,1,6)>=trim("+dat1+") and substr(data_ins,1,6)<=trim("+(dat2==0?MainActivity.getIntData():dat2)+")"
			};
			String where=str[0].toString();
			//if (where.equals("")||where.length()==0) where=str[1].toString(); else 
				//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getQueryData( 
			"rasxod as R "
			+ " left join tmc as T on R.id_tmc=T._id left join tmc_ed as E on R.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id left join postav as POS on R.id_post=POS._id",
			new String[] {"R._id as _id",
			"R.id_tmc as id_tmc","T.name as name","E.name as ted","TP.name namet","POS.name as namep",
			//"O.sumka+R.sumkr-P.sumkp as kol_n","O.sumka+R.sumsr-P.sumsp as sum_n","0 as price_n",
			"R.prim prim",//"0 as price_pri",
			"R.kol*R.price sumka","R.data_ins as data_ins",//"0 as price_ras",
			"R.kol kol","R.price as price"//,"0 as price_k"
			}, 
			where, null,null,null,null);


File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/Oborotka"); //dir.mkdirs();
}
file   =   new File(dir, "R"+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Лист1");
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("НОМЕНКЛАТУРА"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ГРУППА");
row.createCell(2).setCellValue("НАЗВАНИЕ");
row.createCell(3).setCellValue("ПОСТАВЩИК");
row.createCell(4).setCellValue("КОЛ-ВО");
row.createCell(5).setCellValue("ЕД.ИЗМ");
row.createCell(6).setCellValue("СУММА");
row.createCell(7).setCellValue("ЦЕНА");
row.createCell(8).setCellValue("ДАТА");
//for (int i=0; i<15; i++) row.getCell(i).setCellStyle(style);
if (cc.moveToFirst())  
do {
rowNum++;
row = sheet.createRow(rowNum);
row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("id_tmc")));
row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("namet")));
row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("namep")));
row.createCell(4).setCellValue(cc.getFloat(cc.getColumnIndex("kol")));
row.createCell(5).setCellValue(cc.getString(cc.getColumnIndex("ted")));
row.createCell(6).setCellValue(cc.getFloat(cc.getColumnIndex("sumka")));//row.getCell(8).setCellStyle(styleN3);
row.createCell(7).setCellValue(cc.getFloat(cc.getColumnIndex("price")));//row.getCell(9).setCellStyle(styleN2);
row.createCell(8).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));//row.getCell(10).setCellStyle(styleN3);

} while (cc.moveToNext());

cc.close();
workbook.write(out);// workbook.close();
out.close();
} catch (FileNotFoundException ef) {
ef.printStackTrace();// out.close();
}
catch (IOException e) {
e.printStackTrace();
}
}
}
