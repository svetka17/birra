package luce.birra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Import2Excel {
	
	static void load_tmc (String file, Context cnt) {
				 
try {
	
				HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
		        HSSFSheet sheet = wb.getSheetAt(0);
		        //HSSFRow row = sheet.getRow(1);        
		        Iterator<Row> rowIter = sheet.rowIterator();
		        String value="";
		        if (rowIter.hasNext()) rowIter.next();
	            while(rowIter.hasNext()) {
	                HSSFRow myRow = (HSSFRow) rowIter.next();
	                Iterator<Cell> cellIter = myRow.cellIterator();
	                Cursor c; int id=0, idtmc=-1; String n="";  int id_pgr=-1; int ed=-1; String ted=""; String n_pgr="";
	                float price=0; byte vis=0; byte ok=0; byte pos=0; float tara=0; int i=0;
	                while(cellIter.hasNext()) {
	                    HSSFCell cell = (HSSFCell) cellIter.next();
	                //for ( short c = myRow.getFirstCellNum(); c <= myRow.getLastCellNum(); c++ ) {
	                	//HSSFCell cell = myRow.getCell( c );
	                	if ( cell != null ) {
	                        if ( cell.getCellType() == HSSFCell.CELL_TYPE_BLANK ) {
	                          value = "";
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN ) {
	                          value = Boolean.toString( cell.getBooleanCellValue() );
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_ERROR ) {
	                          value = Byte.toString( cell.getErrorCellValue() );
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA ) {
	                          value = cell.getCellFormula().toString();
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ) {
	                          value = Double.toString( cell.getNumericCellValue() );
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_STRING ) {
	                          value = cell.getStringCellValue();
	                        }
	                		switch (i) {
							case 0: id = (int) MainActivity.StrToFloat(value);
								break;
							case 1: //n_pgr = value.trim();
							c = MainActivity.db.getRawData ("select _id from tmc_pgr where trim(name)=trim('"+value+"')", null);
							if (c.moveToFirst()) {   
						        do { id_pgr = c.getInt(c.getColumnIndex("_id"));} 
						        while (c.moveToNext());
						      }
							if (id_pgr==-1) id_pgr = (int) MainActivity.db.addRecTMC_PGRcount(value, MainActivity.getIntDataTime());
								break;
							case 2: n = value.trim();
							c = MainActivity.db.getRawData ("select _id from tmc where trim(name)=trim('"+value+"')", null);
							if (c.moveToFirst()) {   
						        do { idtmc = c.getInt(c.getColumnIndex("_id"));} 
						        while (c.moveToNext());
						      }
							//if (idtmc==-1) idtmc = (int) MainActivity.db.addRecTMC_PGRcount(value, MainActivity.getIntDataTime());
								break;
							case 3: //ted = value.trim();
							c = MainActivity.db.getRawData ("select _id from tmc_ed where trim(name)=trim('"+value+"')", null);
							if (c.moveToFirst()) {   
						        do { ed = (int)c.getInt(c.getColumnIndex("_id"));} 
						        while (c.moveToNext());
						      }
							if (ed==-1) ed = (int) MainActivity.db.addRecTMC_EDcount(value, MainActivity.getIntDataTime());
								break;
							case 4: price = (int) MainActivity.StrToFloat(value);
							break;
							case 5: vis = (byte) MainActivity.StrToFloat(value);
							break;
							case 6: ok = (byte) MainActivity.StrToFloat(value);
							break;
							case 7: tara = MainActivity.StrToFloat(value);
							break;
							case 8: pos = (byte) MainActivity.StrToFloat(value);
							break;
							default:
								break;
							}
	                      }
	                    i++;
	                }
	             if (idtmc==-1)
	                idtmc=(int) MainActivity.db.addRecTMCcount(n, id_pgr, ed, price, vis, pos, tara, MainActivity.getIntDataTime(), ok);
	             else {
	            	 if (id==idtmc) Toast.makeText(cnt , "������������ " +n+" ��� ����" , Toast.LENGTH_SHORT).show();
	            	 //Log.d("MyLog", "exists equal "+n);}
	            	 else Toast.makeText(cnt , "������������ " +n+" ��� ���� � ������ �������������� �������" , Toast.LENGTH_SHORT).show();
	            	 //Log.d("MyLog", "exists other "+n);}
	            	 }
	             }
	                //Log.d("MyLog", "idtmc: " +idtmc+" "+n+" "+id+" "+id_pgr+" "+n_pgr+" "+ed+" "+ted+" "+price);
		        /*		        
		        if(row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
		            Date birthdate = row.getCell(1).getDateCellValue();
		        }*/
	           // }	        

}
catch(FileNotFoundException e) {}
catch(IOException e) {};
		        //myExcelBook.close();

/*	rowNum++;
	row = sheet.createRow(rowNum);
	row.createCell(0).setCellValue(cc.getInt(cc.getColumnIndex("_id")));
	row.createCell(1).setCellValue(cc.getString(cc.getColumnIndex("namet")));
	row.createCell(2).setCellValue(cc.getString(cc.getColumnIndex("name")));
	row.createCell(3).setCellValue(cc.getString(cc.getColumnIndex("ted")));
	row.createCell(4).setCellValue(cc.getFloat(cc.getColumnIndex("price")));row.getCell(4).setCellStyle(styleN2);
	row.createCell(5).setCellValue(cc.getInt(cc.getColumnIndex("vis")));
	row.createCell(6).setCellValue(cc.getInt(cc.getColumnIndex("ok")));
	row.createCell(7).setCellValue(cc.getFloat(cc.getColumnIndex("tara")));row.getCell(7).setCellStyle(styleN2);
	row.createCell(8).setCellValue(cc.getInt(cc.getColumnIndex("pos")));
	row.createCell(9).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));
*/
	
	//return file;
	}

}

