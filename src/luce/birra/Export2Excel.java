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
static File oborotka (int dat, int pgr, String dirN) {
//////////////////////////////////////
String []str = {pgr==0?"":" TP._id="+pgr, dat==0?"":" where substr(data_ins,1,6)>=trim("+dat+")"};
//String where=str[0].toString();
//if (str[0].length()!=0) where=where+" and "+str[0];
Cursor cc = MainActivity.db.getQueryData( 
"ostat as O left join (select id_tmc, keg, id_post, ed, sum(round(kol,3)) as sumkr, sum(round(kol,3)*round(price,2)-round(skidka,2)) as sumsr from rasxod " + str[1].toString() + 
" group by id_tmc, keg, id_post, ed) as R on O.id_tmc=R.id_tmc and O.keg=R.keg and O.id_post=R.id_post and O.ed=R.ed "
+ " left join (select id_tmc, keg, id_post, ed, sum(round(kol,3)) as sumkp, sum(round(kol,3)*round(price,2)) as sumsp from prixod " + str[1].toString() +
" group by id_tmc, keg, id_post, ed) as P on O.id_tmc=P.id_tmc and O.keg=P.keg and O.id_post=P.id_post and O.ed=P.ed"
+ " left join tmc as T on O.id_tmc=T._id left join tmc_ed as E on O.ed=E._id left join tmc_pgr as TP on T.pgr=TP._id left join postav as POS on O.id_post=POS._id",
new String[] {"O._id as _id",
"O.id_tmc as id_tmc","O.keg as keg","T.name as name","E.name as ted", "POS.name as pname", "TP.name as pgrname", "T.price as price",
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
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Îáîğîòíàÿ âåäîìîñòü "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;

out = new FileOutputStream(file);

//ñîçäàíèå ñàìîãî excel ôàéëà â ïàìÿòè
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
//ñîçäàíèå ëèñòà ñ íàçâàíèåì "Ïğîñòî ëèñò"
HSSFSheet sheet = workbook.createSheet("Ëèñò1");
// è ïğèìåíÿåì ê ıòîìó ñòèëş æèğíûé øğèôò
//style.setFont(font);
//style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
// çàïîëíÿåì ñïèñîê êàêèìè-òî äàííûìè
//List<DataModel> dataList = fillData();
// ñ÷åò÷èê äëÿ ñòğîê
int rowNum = 0;
// ñîçäàåì ïîäïèñè ê ñòîëáöàì (ıòî áóäåò ïåğâàÿ ñòğî÷êà â ëèñòå Excel ôàéëà)
HSSFRow row = sheet.createRow(rowNum);
/*XSSFCellStyle style2 = workbook.createCellStyle();
style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
style2.setVerticalAlignment( 
XSSFCellStyle.VERTICAL_CENTER);*/
//cell.setCellValue("Center Aligned"); 
//cell.setCellStyle(style2);
//ñîçäàåì øğèôò
HSSFFont font = workbook.createFont();
// óêàçûâàåì, ÷òî õîòèì åãî âèäåòü æèğíûì
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
// ñîçäàåì ñòèëü äëÿ ÿ÷åéêè
HSSFCellStyle style = workbook.createCellStyle();
// è ïğèìåíÿåì ê ıòîìó ñòèëş æèğíûé øğèôò
style.setFont(font);
style.setWrapText(true);// setShrinkToFit(true);// setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
row.createCell(0).setCellValue("ÍÎÌÅÍÊËÀÒÓĞÀ"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ÃĞÓÏÏÀ");
row.createCell(2).setCellValue("ÍÀÇÂÀÍÈÅ");
row.createCell(3).setCellValue("ÏÎÑÒÀÂÙÈÊ");
row.createCell(4).setCellValue("ÅÄ.ÈÇÌ");
row.createCell(5).setCellValue("ÊÎË-ÂÎ ÍÀ "+MainActivity.getStringData(dat) );
row.createCell(6).setCellValue("ÑÓÌÌÀ ÍÀ "+MainActivity.getStringData(dat));
row.createCell(7).setCellValue("ÑĞÅÄÍßß ÖÅÍÀ ÍÀ "+MainActivity.getStringData(dat));
row.createCell(8).setCellValue("ÊÎË-ÂÎ ÏĞÈÕÎÄ");
row.createCell(9).setCellValue("ÑÓÌÌÀ ÏĞÈÕÎÄ");
row.createCell(10).setCellValue("ÊÎË-ÂÎ ĞÀÑÕÎÄ");
row.createCell(11).setCellValue("ÑÓÌÌÀ ĞÀÑÕÎÄ");
row.createCell(12).setCellValue("ÊÎË-ÂÎ ÍÀ "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(13).setCellValue("ÑÓÌÌÀ ÍÀ "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(14).setCellValue("ÑĞÅÄÍßß ÖÅÍÀ ÍÀ "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(15).setCellValue("ÖÅÍÀ ÏĞÎÄÀÆÈ ÍÀ "+Calendar.getInstance().get(Calendar.DATE)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(16).setCellValue("ÊÅÃÀ");
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
row.createCell(16).setCellValue(cc.getInt(cc.getColumnIndex("keg")));
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
	
static File rasxod (int dat1, int dat2, int pgr, String dirN) {
	String []str = {pgr==0?"":" TP._id="+pgr,
			dat1==0?"":" substr(R.data_ins,1,6)>=trim("+dat1+")",
			dat2==0?"":" substr(R.data_ins,1,6)<=trim("+dat2+")"
			};
			String where=str[0].toString();
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
			"R.prim as prim", "'÷åê¹'||K._id||' íà ñóììó '||K.sumka as chek",//"0 as price_pri",
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
file   =   new File(dir, "Ğàñõîä ïîäğîáíî "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Ëèñò1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
HSSFCellStyle style = workbook.createCellStyle();
style.setFont(font);
style.setWrapText(true);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("ÍÎÌÅÍÊËÀÒÓĞÀ"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ÃĞÓÏÏÀ");
row.createCell(2).setCellValue("ÍÀÇÂÀÍÈÅ");
row.createCell(3).setCellValue("ÏÎÑÒÀÂÙÈÊ");
row.createCell(4).setCellValue("ÊÎË-ÂÎ");
row.createCell(5).setCellValue("ÅÄ.ÈÇÌ");
row.createCell(6).setCellValue("ÑÓÌÌÀ ÁÅÇ ÑÊÈÄÊÈ");
row.createCell(7).setCellValue("ÑÊÈÄÊÀ");
row.createCell(8).setCellValue("ÑÓÌÌÀ ÑÎ ÑÊÈÄÊÎÉ");
row.createCell(9).setCellValue("ÖÅÍÀ");
row.createCell(10).setCellValue("ÏĞÈÌÅ×ÀÍÈÅ");
row.createCell(11).setCellValue("ÈÄ¹");
row.createCell(12).setCellValue("×ÅÊ");
row.createCell(13).setCellValue("ÄÀÒÀ");
row.createCell(14).setCellValue("ÊÅÃÀ");
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
file   =   new File(dir, "×åêè "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Ëèñò1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
HSSFCellStyle style = workbook.createCellStyle();
style.setFont(font);
style.setWrapText(true);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("ÈÄ ×ÅÊÀ"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("¹ ×ÅÊÀ");
row.createCell(2).setCellValue("ÄÀÒÀ");
row.createCell(3).setCellValue("ÑÓÌÌÀ");
row.createCell(4).setCellValue("ÑÊÈÄÊÀ");
row.createCell(5).setCellValue("ÈÌß");
row.createCell(6).setCellValue("ÏĞÈÌÅ×ÀÍÈÅ");
row.createCell(7).setCellValue("ÊÀĞÒÀ");
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
	String []str = {pgr==0?"":" TT._id="+pgr,
			dat1==0?"":" substr(T.data_ins,1,6)>=trim("+dat1+")",
			dat2==0?"":" substr(T.data_ins,1,6)<=trim("+dat2+")"
			};
			String where=str[0].toString();
			if (where.length()==0) where=str[1].toString(); else where=where+" and "+str[1].toString();
			if (where.length()==0) where=str[2].toString(); else where=where+" and "+str[2].toString();
			if (where.equals("")||where.length()==0) where=" T.ok=0 "; else where=where+" and T.ok=0 ";
				//if (!str[1].equals("")) where=where+" and "+str[1].toString(); 
			//Log.d("MyLog", "where="+where+" 0="+str[0]+" 1="+str[1]+" 2="+str[2]);
			Cursor cc = MainActivity.db.getQueryData("rasxod as T left join tmc as TP on T.id_tmc = TP._id left join tmc_pgr as TT on TP.pgr=TT._id left join tmc_ed as E on T.ed = E._id left join ostat as K on T.id_post = K.id_post and T.id_tmc=K.id_tmc and T.keg=K.keg and T.ed=K.ed left join postav as KK on T.id_post=KK._id", 
         			new String[] {"TP._id as _id","T.keg as keg","TT.name as pgr","TP.name as name",/*"T.data_ins as data_ins",*/"KK.name as post","sum(T.kol) as kol","E.name as ed",
        			 "sum(T.price*T.kol-T.skidka) as sumka","K.kol as ostat","T.price as price", "round(sum(T.skidka),2) as skidka"}, 
         			 //"TP.pgr = ?"
        			 where, null,"TP._id, T.keg, TT.name, TP.name, T.price, KK.name, E.name, K.kol",null,"TP.name,K.keg");

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Ğàñõîä è îñòàòîê "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Ëèñò1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
HSSFCellStyle style = workbook.createCellStyle();
style.setFont(font);
style.setWrapText(true);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("ÍÎÌÅÍÊËÀÒÓĞÀ"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ÃĞÓÏÏÀ");
row.createCell(2).setCellValue("ÍÀÇÂÀÍÈÅ");
row.createCell(3).setCellValue("ÏÎÑÒÀÂÙÈÊ");
row.createCell(4).setCellValue("ÏĞÎÄÀÍÎ ÊÎË-ÂÎ ÇÀ ÏÅĞÈÎÄ");
row.createCell(5).setCellValue("ÅÄ.ÈÇÌ");
row.createCell(6).setCellValue("ÏĞÎÄÀÍÎ ÑÓÌÌÀ ÇÀ ÏÅĞÈÎÄ");
row.createCell(7).setCellValue("ÎÑÒÀÒÎÊ ÍÀ "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR));
row.createCell(8).setCellValue("ÑÓÌÌÀ ÑÊÈÄÊÈ");
row.createCell(9).setCellValue("ÖÅÍÀ ÏĞÎÄÀÆÈ");
row.createCell(10).setCellValue("ÊÅÃÀ");
for (int i=0; i<11; i++) row.getCell(i).setCellStyle(style);
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
row.createCell(5).setCellValue(cc.getString(cc.getColumnIndex("ed")));
row.createCell(6).setCellValue(cc.getDouble(cc.getColumnIndex("sumka")));row.getCell(6).setCellStyle(styleN2);
row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("ostat")));row.getCell(7).setCellStyle(styleN3);
row.createCell(8).setCellValue(cc.getDouble(cc.getColumnIndex("skidka")));row.getCell(8).setCellStyle(styleN2);
row.createCell(9).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(9).setCellStyle(styleN2);
row.createCell(10).setCellValue(cc.getInt(cc.getColumnIndex("keg")));
} while (cc.moveToNext());
cc.close();
rowNum++;
row = sheet.createRow(rowNum);
FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
row.createCell(4).setCellFormula("SUM(E2:E"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(4));row.getCell(4).setCellStyle(styleN3);
row.createCell(6).setCellFormula("SUM(G2:G"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(6));row.getCell(6).setCellStyle(styleN2);
row.createCell(8).setCellFormula("SUM(I2:I"+rowNum+")");evaluator.evaluateFormulaCell(row.getCell(8));row.getCell(8).setCellStyle(styleN2);
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
file   =   new File(dir, "Ïğèõîä "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Ëèñò1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
HSSFCellStyle style = workbook.createCellStyle();
style.setFont(font);
style.setWrapText(true);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("ÍÎÌÅÍÊËÀÒÓĞÀ"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ÃĞÓÏÏÀ");
row.createCell(2).setCellValue("ÍÀÇÂÀÍÈÅ");
row.createCell(3).setCellValue("ÏÎÑÒÀÂÙÈÊ");
row.createCell(4).setCellValue("ÊÎË-ÂÎ");
row.createCell(5).setCellValue("ÅÄ.ÈÇÌ");
row.createCell(6).setCellValue("ÖÅÍÀ ÏÎÑÒÀÂÙÈÊÀ");
row.createCell(7).setCellValue("ÑÓÌÌÀ");
row.createCell(8).setCellValue("ÏĞÈÌÅ×ÀÍÈÅ");
row.createCell(9).setCellValue("ÈÄ¹");
row.createCell(10).setCellValue("ÄÀÒÀ");
row.createCell(11).setCellValue("ÊÅÃÀ");
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
	    			"select O._id as _id, O.id_tmc as id_tmc, O.keg as keg, O.kol as kol, E.name as ted, TT.price as price, O.id_post as id_post, O.data_ins as data_ins, "
	    	    			+ "P.name as pname, T.name as tname, TP.name as pgr, O.kol*TT.price as sumka "
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
	    	    			+ "where O.kol<>0 "+((pgr==0)?"":(" and T.pgr="+pgr)) // +"? and ?"
	    	    			, null);

File file   = null, dir = null;
File root   = Environment.getExternalStorageDirectory();
if (dirN.length()!=0) {dir=new File(dirN); }
else //{dir  =   new File (root.getAbsolutePath() + "/Oborotka"); dir.mkdirs();}
if (root.canWrite()){
dir  =   new File (root.getAbsolutePath() + "/birra"); dir.mkdirs();
}
file   =   new File(dir, "Îñòàòîê íà "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Ëèñò1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
HSSFCellStyle style = workbook.createCellStyle();
style.setFont(font);
style.setWrapText(true);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("ÍÎÌÅÍÊËÀÒÓĞÀ"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ÃĞÓÏÏÀ");
row.createCell(2).setCellValue("ÍÀÇÂÀÍÈÅ");
row.createCell(3).setCellValue("ÏÎÑÒÀÂÙÈÊ");
row.createCell(4).setCellValue("ÊÎË-ÂÎ");
row.createCell(5).setCellValue("ÅÄ.ÈÇÌ");
row.createCell(6).setCellValue("ÖÅÍÀ ÏĞÎÄÀÆÈ");
row.createCell(7).setCellValue("ÑÓÌÌÀ");
row.createCell(8).setCellValue("ÈÄ¹");
row.createCell(9).setCellValue("ÄÀÒÀ ÏÎÑËÅÄÍÅÃÎ ÈÇÌÅÍÅÍÈß");
row.createCell(10).setCellValue("ÊÅÃÀ");
for (int i=0; i<11; i++) row.getCell(i).setCellStyle(style);
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
file   =   new File(dir, "Öåíû íà "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Ëèñò1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
HSSFCellStyle style = workbook.createCellStyle();
style.setFont(font);
style.setWrapText(true);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("ÍÎÌÅÍÊËÀÒÓĞÀ"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ÃĞÓÏÏÀ");
row.createCell(2).setCellValue("ÍÀÇÂÀÍÈÅ");
row.createCell(3).setCellValue("ÏÎÑÒÀÂÙÈÊ");
row.createCell(4).setCellValue("ÅÄ.ÈÇÌ");
row.createCell(5).setCellValue("ÖÅÍÀ ÏĞÎÄÀÆÈ");
row.createCell(6).setCellValue("ÈÄ¹");
row.createCell(7).setCellValue("ÄÀÒÀ ÏÎÑËÅÄÍÅÃÎ ÈÇÌÅÍÅÍÈß");
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
file   =   new File(dir, "Ñïğàâî÷íèê òîâàğîâ íà "+Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR)+".xls");

try {
FileOutputStream out   =   null;
out = new FileOutputStream(file);
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet sheet = workbook.createSheet("Ëèñò1");
HSSFDataFormat df2 = workbook.createDataFormat();
HSSFDataFormat df3 = workbook.createDataFormat();
HSSFCellStyle styleN2 = workbook.createCellStyle();
styleN2.setDataFormat(df2.getFormat("0.00"));
HSSFCellStyle styleN3 = workbook.createCellStyle();
styleN3.setDataFormat(df3.getFormat("0.000"));
HSSFFont font = workbook.createFont();
font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
HSSFCellStyle style = workbook.createCellStyle();
style.setFont(font);
style.setWrapText(true);
style.setAlignment(HSSFCellStyle.ALIGN_FILL );
style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
int rowNum = 0;
HSSFRow row = sheet.createRow(rowNum);
row.createCell(0).setCellValue("ÍÎÌÅÍÊËÀÒÓĞÀ"); //row.getCell(0).setCellStyle(style2);
row.createCell(1).setCellValue("ÃĞÓÏÏÀ");
row.createCell(2).setCellValue("ÍÀÇÂÀÍÈÅ");
row.createCell(3).setCellValue("ÅÄ.ÈÇÌ");
row.createCell(4).setCellValue("ÖÅÍÀ ÏĞÎÄÀÆÈ");
row.createCell(5).setCellValue("ÄËß ÏĞÎÄÀÆÈ");
row.createCell(6).setCellValue("ÒÀĞÀ");
row.createCell(7).setCellValue("ÇÍÀ×ÅÍÈÅ ÒÀĞÛ");
row.createCell(8).setCellValue("ÏÎÇÈÖÈß Â ÃĞÓÏÏÅ");
row.createCell(9).setCellValue("ÄÀÒÀ ÏÎÑËÅÄÍÅÃÎ ÈÇÌÅÍÅÍÈß");
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

