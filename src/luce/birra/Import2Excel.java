package luce.birra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;


public class Import2Excel {
	
	public static String forJSON(String input) {
        if (input.trim() == null || input.trim().isEmpty()) {
            return "";
        }
        int len = input.length();
        // сделаем небольшой запас, чтобы не выделять память потом
        final StringBuilder result = new StringBuilder(len + len / 4);
        final StringCharacterIterator iterator = new StringCharacterIterator(input);
        char ch = iterator.current();
        while (ch != CharacterIterator.DONE) {
            if (ch == '\n') {
                result.append("\\n");
            } else if (ch == '\r') {
                result.append("\\r");
            } else if (ch == '\'') {
                //result.append("\\\'");
            	result.append("''");
            } else if (ch == '"') {
                result.append("\"");
            } else {
                result.append(ch);
            }
            ch = iterator.next();
        }
        return result.toString().trim();
    }
	
//	@SuppressWarnings("deprecation")
	static void load_tmc (String file, Context cnt) {
				int flag=0; 
try { //Log.d("MyLog", "1");
				//HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
	HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
				//Log.d("MyLog", "2");
		        HSSFSheet sheet = wb.getSheetAt(0);
		       // Log.d("MyLog", "3");  
		        Iterator<Row> rowIter = sheet.rowIterator();
		       // Log.d("MyLog", "4");
		        String value="";
		        if (rowIter.hasNext()) rowIter.next();
		       // Log.d("MyLog", "5");
	            while(rowIter.hasNext()) {
	            	flag=0;
	                //HSSFRow myRow = (HSSFRow) rowIter.next();
	            	HSSFRow myRow = (HSSFRow) rowIter.next();
	                Iterator<Cell> cellIter = myRow.cellIterator();
	                int id=0, idtmc=-1; String n="";  int id_pgr=-1; int ed=-1; //String ted=""; String n_pgr="";
	                float price=0; int vis=0; int ok=0; int pos=0; float tara=0; int i=0;
	                while(cellIter.hasNext()) {
	                    //HSSFCell cell = (HSSFCell) cellIter.next();
	                	HSSFCell cell = (HSSFCell) cellIter.next();
	                //for ( short c = myRow.getFirstCellNum(); c <= myRow.getLastCellNum(); c++ ) {
	                	//HSSFCell cell = myRow.getCell( c );
	                	if ( cell != null ) {
	                		Cursor c;
	                		value="";
	                        if ( cell.getCellType() == HSSFCell.CELL_TYPE_BLANK ) {
	                          value = "";
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN ) {
	                          value = Boolean.toString( cell.getBooleanCellValue() );
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_ERROR ) {
	                          value = Integer.toString( cell.getErrorCellValue() );
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA ) {
	                          value = cell.getCellFormula().toString();
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ) {
	                          value = Double.toString( cell.getNumericCellValue() );
	                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_STRING ) {
	                          value = cell.getStringCellValue();
	                        }
	                        value=forJSON(value);
	                        //Log.d("MyLog", "value="+value);
	                        if (value.equals(""))
	                        {flag=1;
	                        Toast.makeText(cnt , "колонка " +i+" пустая" , Toast.LENGTH_SHORT).show();
	                        }
	                        else
	                        	//Log.d("MyLog", "i="+i);	
	                		switch (i) {
							case 0: id = (int) MainActivity.StrToFloat(value);
							//Log.d("MyLog", "0="+id);
							break;
							case 1: //n_pgr = value.trim();
								c = MainActivity.db.getRawData ("select _id from tmc_pgr where trim(name)=trim('"+value +"')", null);
							if (c.moveToFirst()) {   
						        do { id_pgr = c.getInt(c.getColumnIndex("_id"));
						        //Log.d("MyLog", "id_pgr="+id_pgr);
						        } 
						        while (c.moveToNext());
						      }
							else c.close();
							if (id_pgr==-1 && id!=0 && !value.equals("")) id_pgr = (int) MainActivity.db.addRecTMC_PGRcount(value, MainActivity.getIntDataTime());
							//Log.d("MyLog", "id_pgr="+id_pgr);	
							break;
							case 2: n = value;
							c = MainActivity.db.getRawData ("select _id from tmc where trim(name)=trim('"+value +"')", null);
							if (c.moveToFirst()) {   
						        do { idtmc = c.getInt(c.getColumnIndex("_id"));
						       // Log.d("MyLog", "idtmc1="+idtmc);
						        } 
						        while (c.moveToNext());
						      }
							else c.close();
							//Log.d("MyLog", "idtmc2="+idtmc);
							//if (idtmc==-1) idtmc = (int) MainActivity.db.addRecTMC_PGRcount(value, MainActivity.getIntDataTime());
								break;
							case 3: //ted = value.trim();
							c = MainActivity.db.getRawData ("select _id from tmc_ed where trim(name)=trim('"+value+"')", null);
							if (c.moveToFirst()) {   
						        do { ed = (int)c.getInt(c.getColumnIndex("_id"));} 
						        while (c.moveToNext());
						      }
							else c.close();
							if (ed==-1 && !value.equals("")) ed = (int) MainActivity.db.addRecTMC_EDcount(value/*, MainActivity.getIntDataTime()*/);
							//Log.d("MyLog", "3="+ed);	
							break;
							case 4: price = MainActivity.StrToFloat2(value);
							//Log.d("MyLog", "4="+price);
							break;
							case 5: vis =  (int)MainActivity.StrToFloat(value);
							//Log.d("MyLog", "5="+vis);
							break;
							case 6: ok = (int) MainActivity.StrToFloat(value);
							//Log.d("MyLog", "6="+ok);
							break;
							case 7: tara = MainActivity.StrToFloat(value);
							//Log.d("MyLog", "7="+tara);
							break;
							case 8: pos = (int) MainActivity.StrToFloat(value);
							//Log.d("MyLog", "8="+pos);
							break;
							default:
								break;
							}
	                      }
	                    i++;
	                }
	                //Log.d("MyLog", "id="+id+" n="+n+" r="+n.replace("\'", "").replace("\"", ""));   
	             if (flag!=1)
	             if (idtmc==-1 && !n.equals(""))
	             {//Log.d("MyLog", "insert n="+n+" pgr="+id_pgr+" ed="+ed+" price="+price+" vis="+vis+" pos="+pos+" tara="+tara+" data="+MainActivity.getIntDataTime()+" ok="+ok);	 
	            	 idtmc=(int) MainActivity.db.addRecTMCcount(n, id_pgr, ed, price, vis, pos, tara, MainActivity.getIntDataTime(), ok);
	             //Log.d("MyLog", "insert done");
	             }
	             else {
	            	 if (id==idtmc) Toast.makeText(cnt , "наименование " +n+" уже есть" , Toast.LENGTH_SHORT).show();
	            	 //}
	            	 else Toast.makeText(cnt , "наименование " +n+" уже есть с другим номенклатурным номером" , Toast.LENGTH_SHORT).show();
	            	 //Log.d("MyLog", "exists other "+n);
	            	 //}
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
	row.createCell(4).setCellValue(cc.getDouble(cc.getColumnIndex("price")));row.getCell(4).setCellStyle(styleN2);
	row.createCell(5).setCellValue(cc.getInt(cc.getColumnIndex("vis")));
	row.createCell(6).setCellValue(cc.getInt(cc.getColumnIndex("ok")));
	row.createCell(7).setCellValue(cc.getDouble(cc.getColumnIndex("tara")));row.getCell(7).setCellStyle(styleN2);
	row.createCell(8).setCellValue(cc.getInt(cc.getColumnIndex("pos")));
	row.createCell(9).setCellValue(MainActivity.getStringDataTime( cc.getInt(cc.getColumnIndex("data_ins")) ));
*/
	
	//return file;
	}
	
	static void load_ost (String file, Context cnt) {
		try {
			int flag=0;			
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
				        HSSFSheet sheet = wb.getSheetAt(0);
				        //HSSFRow row = sheet.getRow(1);        
				        Iterator<Row> rowIter = sheet.rowIterator();
				        String value="";
				        if (rowIter.hasNext()) rowIter.next();
			            while(rowIter.hasNext()) {
			                flag=0;
			            	HSSFRow myRow = (HSSFRow) rowIter.next();
			                Iterator<Cell> cellIter = myRow.cellIterator();
			                Cursor c; 
			                int id=0, idtmc=-1; String n="";  int id_pgr=-1; int ed=-1; //String ted=""; String n_pgr="";
			                int idpost=-1;// String n_post="";
			                //long ID_ost=-1;
			                float price=0, kol=0;//, sumka=0; //byte vis=0; byte ok=0; byte pos=0; float tara=0; 
			                int i=0;
			                while(cellIter.hasNext()) {
			                    HSSFCell cell = (HSSFCell) cellIter.next();
			                //for ( short c = myRow.getFirstCellNum(); c <= myRow.getLastCellNum(); c++ ) {
			                	value="";
			                	if ( cell != null ) {
			                        if ( cell.getCellType() == HSSFCell.CELL_TYPE_BLANK ) {
			                          value = "";
			                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN ) {
			                          value = Boolean.toString( cell.getBooleanCellValue() );
			                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_ERROR ) {
			                          value = Integer.toString( cell.getErrorCellValue() );
			                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA ) {
			                          value = cell.getCellFormula().toString();
			                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ) {
			                          value = Double.toString( cell.getNumericCellValue() );
			                        } else if ( cell.getCellType() == HSSFCell.CELL_TYPE_STRING ) {
			                          value = cell.getStringCellValue();
			                        }
			                		value=forJSON(value);
			                		if (value.equals(""))
			                        {flag=1;
			                        Toast.makeText(cnt , "колонка " +i+" пустая" , Toast.LENGTH_SHORT).show();}
			                        else
			                        switch (i) {
									case 0: id = (int) MainActivity.StrToFloat(value);
										break;
									case 1: //n_pgr = value.trim();
									c = MainActivity.db.getRawData ("select _id from tmc_pgr where trim(name)=trim('"+value+"')", null);
									if (c.moveToFirst()) {   
								        do { id_pgr = c.getInt(c.getColumnIndex("_id"));} 
								        while (c.moveToNext());
								      }
									else c.close();
									if (id_pgr==-1 && id!=0 && !value.equals("") ) id_pgr = (int) MainActivity.db.addRecTMC_PGRcount(value, MainActivity.getIntDataTime());
										break;
									case 2: n = value;
									c = MainActivity.db.getRawData ("select _id from tmc where trim(name)=trim('"+value+"')", null);
									
									//String ss="select _id from tmc where trim(name)=trim('CHIPSTER''S (70 гр. - 13.50 грн.)')";
									/*near "S": syntax error (code 1): , while compiling: select _id from tmc where trim(name)=trim('CHIPSTER\'S (70 гр. - 13.50 грн.)')
*/
									if (c.moveToFirst()) {   
								        do { idtmc = c.getInt(c.getColumnIndex("_id"));} 
								        while (c.moveToNext());
								      }
									else c.close();
									//if (idtmc==-1) idtmc = (int) MainActivity.db.addRecTMC_PGRcount(value, MainActivity.getIntDataTime());
										break;
									case 3: //n_pgr = value.trim();
										c = MainActivity.db.getRawData ("select _id from postav where trim(name)=trim('"+value+"')", null);
										if (c.moveToFirst()) {   
									        do { idpost = c.getInt(c.getColumnIndex("_id"));} 
									        while (c.moveToNext());
									      }
										else c.close();
										if (idpost==-1 && !value.equals("")) idpost = (int) MainActivity.db.addRecPOSTAVcount(value,"","","загружено с остатками", MainActivity.getIntDataTime(),0);
											break;
									case 4: kol = MainActivity.StrToFloat(value);
									case 5: //ted = value.trim();
									c = MainActivity.db.getRawData ("select _id from tmc_ed where trim(name)=trim('"+value+"')", null);
									if (c.moveToFirst()) {   
								        do { ed = (int)c.getInt(c.getColumnIndex("_id"));} 
								        while (c.moveToNext());
								      }
									else c.close();
									if (ed==-1 && !value.equals("")) ed = (int) MainActivity.db.addRecTMC_EDcount(value/*, MainActivity.getIntDataTime()*/);
										break;
									case 6: price = MainActivity.StrToFloat2(value);
									break;
									/*case 7: sumka = MainActivity.StrToFloat(value);
									break;
									case 8: ID_ost = (long)MainActivity.StrToFloat(value);
									break;*/
									default:
										break;
									}
			                      }
			                    i++;
			                }
			                //Log.d("MyLog", "id="+id+" n="+n+" r="+n.replace("\'", "").replace("\"", ""));      
			             if (flag!=1) {
			                if (idtmc==-1)
			                { if (!n.equals(""))
			            	{idtmc=(int) MainActivity.db.addRecTMCcount(n, id_pgr, ed, price, /*vis*/1, /*pos*/1, /*tara*/0, MainActivity.getIntDataTime(), /*ok*/0);
			                Toast.makeText(cnt , "добавлено наименование " +n, Toast.LENGTH_SHORT).show();
				            }
			                }
			                /*else {
			            	 if (id==idtmc) Toast.makeText(cnt , "наименование " +n+" уже есть" , Toast.LENGTH_SHORT).show();
			            	 //Log.d("MyLog", "exists equal "+n);}
			            	 else Toast.makeText(cnt , "наименование " +n+" уже есть с другим номенклатурным номером" , Toast.LENGTH_SHORT).show();
			            	 //Log.d("MyLog", "exists other "+n);}
			            	 }*/
			            if (kol>0 && idtmc!=-1 && idpost!=-1 && ed!=-1 && id_pgr!=-1) {
			            int kegs=0;
			            if (ed==1) kegs=(int)MainActivity.db.addRecKEGSCount("загрузка остатков "+n, kol, "из файла", MainActivity.getIntDataTime(), 0);
			             MainActivity.db.addRecPRIXOD(idtmc,kegs, kol,/*0,0,*/ ed, price, price, idpost, "загрузка остатка из файла "+file, MainActivity.getIntDataTime(), 0); 
			             //////
			             if (ed==1) {
	                			Cursor cOst_ = MainActivity.db.getRawData ("select count(*) c from ostat O where O.id_tmc="+idtmc+" and O.id_post="+idpost+" and O.ed=1 ",null);
	            	   int countkeg=-1;    	    
	            	    if (cOst_.moveToFirst()) { 
	            	        do {
	            	        	countkeg=cOst_.getInt(cOst_.getColumnIndex("c"));
	            	        	MainActivity.db.updOstatOk(idtmc, idpost,kegs, 1, countkeg);
	            	        } while (cOst_.moveToNext());
	            	      } else cOst_.close();
	                		}
			             //////
			            Toast.makeText(cnt , "загрузка остатка " +n+" кол-во:"+kol+" цена продажи:"+price , Toast.LENGTH_SHORT).show();
			            }
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
			}

}

/*
 * 
*/

