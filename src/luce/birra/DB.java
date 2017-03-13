package luce.birra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//import android.util.Log;
 
public class DB {
   //
	/*нумерация чека с 1 каждый день 
	отдельный чек с наличкой и кнопклй отмена и ок
	отчеты:
		- остаток наличия 
		- расход за период
		- приход за период
		- расход + остаток
		
		сорт тары по возрастанию объема
		кнопки 20 шт
		скидка */
		
	
  private static final String DB_NAME = "birraDB";
  private static final int DB_VERSION = 2;
  //private static final String [] TableN = {"tmc","tmc_pgr","prixod","rasxod","ostat","klient","postav"};
  //private static final String DB_TABLE = "mytab";
   
  //public static final String COLUMN_ID = "_id";
  //public static final String COLUMN_IMG = "img";
  //public static final String COLUMN_TXT = "txt";
   
  /*private static final String DB_CREATE = 
    "create table " + DB_TABLE + "(" +
      COLUMN_ID + " integer primary key autoincrement, " +
      COLUMN_IMG + " integer, " +
      COLUMN_TXT + " text" +
    ");";
   */
  private final Context mCtx;
   
   
  private DBHelper mDBHelper;
  private SQLiteDatabase mDB;
   
  public DB(Context ctx) {
    mCtx = ctx;
  }
   
  // открыть подключение
  public void open() {
    mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
    mDB = mDBHelper.getWritableDatabase();
  }
   
  // закрыть подключение
  public void close() {
    if (mDBHelper!=null) mDBHelper.close();
  }
   
  // получить все данные из таблицы DB_TABLE
  public Cursor getAllData(String namTable) {
    return mDB.query(namTable, null, null, null, null, null, null);
  }
  
//получить raw данные 
 public Cursor getRawData(String sql, String[] par) {
   return mDB.rawQuery(sql, null);
 }
 
//получить query данные 
public Cursor getQueryData(String namTable, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
  return mDB.query(namTable, columns, selection, selectionArgs, groupBy, having, orderBy);
}
   	  
	  // добавить запись в DB_TABLE
	  public void addRecTMC(String name, int pgr, int ed, float price, byte vis, float tara, int data_ins, byte ok) {
	    ContentValues cv = new ContentValues();
	    cv.put("name", name);
	    cv.put("pgr", pgr);
	    cv.put("ed", ed);
	    cv.put("price", price);
	    cv.put("vis", vis);
	    cv.put("tara", tara);
	    cv.put("data_ins", data_ins);
	    cv.put("ok", ok);
	    mDB.insert("tmc", null, cv);
	  }

	  public void addRecTMC_PGR(String name, int data_ins) {
		    ContentValues cv = new ContentValues();
		    cv.put("name", name);
		    cv.put("data_ins", data_ins);
		    mDB.insert("tmc_pgr", null, cv);
		  }

	  public void addRecPRIXOD(int id_tmc, float kol,byte ed, float price, int id_post, String prim, /*int data_del,*/ int data_ins, byte ok) {
		    ContentValues cv = new ContentValues();
		    cv.put("id_tmc", id_tmc);
		    cv.put("kol", kol);
		    cv.put("ed", ed);
		    cv.put("price", price);
		    cv.put("id_post", id_post);
		    cv.put("prim", prim);
		    //cv.put("data_del", data_del);
		    cv.put("data_ins", data_ins);
		    cv.put("ok", ok);
		    mDB.insert("prixod", null, cv);
		  }

	  public void addRecRASXOD(int id_tmc, float kol, byte ed, float price, int id_post, int id_klient, String prim, /*int data_del,*/ int data_ins, byte ok) {
		    ContentValues cv = new ContentValues();
		    cv.put("id_tmc", id_tmc);
		    cv.put("kol", kol);
		    cv.put("ed", ed);
		    cv.put("price", price);
		    cv.put("id_post", id_post);
		    cv.put("id_klient", id_klient);
		    cv.put("prim", prim);
		    //cv.put("data_del", data_del);
		    cv.put("data_ins", data_ins);
		    cv.put("ok", ok);
		    mDB.insert("rasxod", null, cv);
		  }
	  
	  public void addRecOSTAT(int id_tmc, float kol, byte ed, float price, float sumka, int id_post, int data_ins, byte ok) {
		    ContentValues cv = new ContentValues();
		    cv.put("id_tmc", id_tmc);
		    cv.put("kol", kol);
		    cv.put("ed", ed);
		    cv.put("price", price);
		    cv.put("sumka", sumka);
		    cv.put("id_post", id_post);
		    cv.put("data_ins", data_ins);
		    cv.put("ok", ok);
		    mDB.insert("ostat", null, cv);
		  }

	  public void addRecKLIENT(String name, float sum, String prim, int data_ins, byte ok) {
		    ContentValues cv = new ContentValues();
		    cv.put("name", name);
		    cv.put("sumka", sum);
		    cv.put("prim", prim);
		    cv.put("data_ins", data_ins);
		    cv.put("ok", ok);
		    mDB.insert("klient", null, cv);
		  }

	  public long addRecKLIENTcount(String name, float sum, String prim, int data_ins, byte ok) {
		    ContentValues cv = new ContentValues();
		    cv.put("name", name);
		    cv.put("sumka", sum);
		    cv.put("prim", prim);
		    cv.put("data_ins", data_ins);
		    cv.put("ok", ok);
		    return mDB.insert("klient", null, cv);
		  }
	  public void addRecPOSTAV(String name, String adres, String telef, String prim, int data_ins, byte ok) {
		    ContentValues cv = new ContentValues();
		    cv.put("name", name);
		    cv.put("adres", adres);
		    cv.put("telef", telef);
		    cv.put("prim", prim);
		    cv.put("data_ins", data_ins);
		    cv.put("ok", ok);
		    mDB.insert("postav", null, cv);
		  }
	  
	  /*public void updMyltyRecTMC_PGR(String name, int data_ins) {
		    ContentValues cv = new ContentValues();
		    cv.put("name", name);
		    cv.put("data_ins", data_ins);
		    mDB.insert("tmc_pgr", null, cv);
		  }*/
	  
	  public void updRec(String namT, long id, String[] name_f, int[] val) {
		    ContentValues cv = new ContentValues();
		    for (int i=0; i<name_f.length; i++)
		    cv.put(name_f[i], val[i]);
		    mDB.update(namT, cv, "_id = "+id, null);
		  }
	  
	  public void updRec(String namT, long id, String[] name_f, float[] val) {
		    ContentValues cv = new ContentValues();
		    for (int i=0; i<name_f.length; i++)
			    cv.put(name_f[i], val[i]);
		    mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public void updRec(String namT, long id, String[] name_f, String[] val) {
		    ContentValues cv = new ContentValues();
		    for (int i=0; i<name_f.length; i++)
		    	cv.put(name_f[i], val[i]);
		    mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public void updRec(String namT, long id, String name_f, int val) {
		    ContentValues cv = new ContentValues();
		    cv.put(name_f, val);
		    mDB.update(namT, cv, "_id = "+id, null);
		  }
	  
	  public void updRec(String namT, long id, String name_f, float val) {
		    ContentValues cv = new ContentValues();
			    cv.put(name_f, val);
		    mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public void updRec(String namT, long id, String name_f, String val) {
		    ContentValues cv = new ContentValues();
		    	cv.put(name_f, val);
		    mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public int updRecCount(String namT, long id, String[] name_f, int[] val) {
		    ContentValues cv = new ContentValues();
		    for (int i=0; i<name_f.length; i++)
			    cv.put(name_f[i], val[i]);
		    return mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public int updRecCount(String namT, long id, String[] name_f, float[] val) {
		    ContentValues cv = new ContentValues();
		    for (int i=0; i<name_f.length; i++)
			    cv.put(name_f[i], val[i]);
		    return mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public int updRecCount(String namT, long id, String[] name_f, String[] val) {
		    ContentValues cv = new ContentValues();
		    for (int i=0; i<name_f.length; i++)
			    cv.put(name_f[i], val[i]);
		    return mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public int updRecCount(String namT, long id, String name_f, int val) {
		    ContentValues cv = new ContentValues();
			    cv.put(name_f, val);
		    return mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public int updRecCount(String namT, long id, String name_f, float val) {
		    ContentValues cv = new ContentValues();
			    cv.put(name_f, val);
		    return mDB.update(namT, cv, "_id = "+id, null);
		  }
	  public int updRecCount(String namT, long id, String name_f, String val) {
		    ContentValues cv = new ContentValues();
			    cv.put(name_f, val);
		    return mDB.update(namT, cv, "_id = "+id, null);
		  }
   
  // удалить запись из DB_TABLE
  public void delRec(String namTable,long id) {
    mDB.delete(namTable, "_id = " + id, null);
  }
   
  // класс по созданию и управлению БД
  private class DBHelper extends SQLiteOpenHelper {
 
    public DBHelper(Context context, String name, CursorFactory factory,
        int version) {
      super(context, name, factory, version);
    }
 
    // создаем и заполняем БД
    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d("MyLog", " --- onCreate tmc --- ");
    	db.execSQL("create table foo ("
                + "_id integer primary key,"
                + "name text default '-нет-' );");
    // REFERENCES ostat(id_tmc) ON DELETE RESTRICT	
      db.execSQL("create table tmc ("
              + "_id integer primary key autoincrement,"
              + "name text not null UNIQUE ON CONFLICT IGNORE,"
              + "pgr integer DEFAULT 0 REFERENCES tmc_pgr(_id) ON DELETE SET DEFAULT,"
              + "ed integer DEFAULT 0 REFERENCES tmc_ed(_id) ON DELETE SET DEFAULT,"
              + "price real not null CHECK (price>=0),"
              + "data_ins integer,"
              + "vis integer default 1,"
              + "tara real default 0,"
              + "ok integer default 0 );");
   
          db.execSQL("create table tmc_pgr ("
                  + "_id integer primary key autoincrement,"
                  + "name text not null UNIQUE ON CONFLICT IGNORE,"
                  + "data_ins integer);");
   
          db.execSQL("create table tmc_ed ("
                  + "_id integer primary key autoincrement,"
                  + "name text not null UNIQUE ON CONFLICT IGNORE);");
          
          db.execSQL("create table prixod ("
                  + "_id integer primary key autoincrement,"
                  + "id_tmc integer DEFAULT 0 REFERENCES tmc(_id) ON DELETE SET DEFAULT,"
                  + "kol real not null,"
                  + "ed integer DEFAULT 0 REFERENCES tmc_ed(_id) ON DELETE SET DEFAULT,"
                  + "price real not null CHECK (price>=0),"
                  + "id_post integer DEFAULT 0 REFERENCES postav(_id) ON DELETE SET DEFAULT,"
                  + "prim text,"
                  + "data_ins integer,"
                 // + "data_del integer default 0,"
                  + "ok integer default 0);");
          
          db.execSQL("create table prixod_del ("
                  + "_id integer primary key autoincrement,"
                  + "id_id integer,"
                  + "id_tmc integer not null,"
                  + "kol real not null,"
                  + "ed integer not null,"
                  + "price real not null,"
                  + "id_post integer,"
                  + "prim text,"
                  + "data_ins integer,"
                //  + "data_del integer default 0,"
                  + "ok integer default 0);");
          
          db.execSQL("create table rasxod ("
                  + "_id integer primary key autoincrement,"
                  + "id_tmc integer DEFAULT 0 REFERENCES tmc(_id) ON DELETE SET DEFAULT,"
                  + "kol real not null,"
                  + "ed integer DEFAULT 0 REFERENCES tmc_ed(_id) ON DELETE SET DEFAULT,"
                  + "price real not null CHECK (price>=0),"
                  + "id_post integer DEFAULT 0 REFERENCES postav(_id) ON DELETE SET DEFAULT,"
                  + "id_klient integer DEFAULT 0 REFERENCES klient(_id) ON DELETE SET DEFAULT,"
                  + "prim text,"
                  + "data_ins integer,"
                 // + "data_del integer default 0,"
                  + "ok integer default 0);");
          
          db.execSQL("create table rasxod_del ("
                  + "_id integer primary key autoincrement,"
                  + "id_id integer,"
                  + "id_tmc integer not null,"
                  + "kol real not null,"
                  + "ed integer not null,"
                  + "price real not null,"
                  + "id_post integer,"
                  + "id_klient integer,"
                  + "prim text,"
                  + "data_ins integer,"
                //  + "data_del integer default 0,"
                  + "ok integer default 0);");

          db.execSQL("create table ostat ("
                  + "_id integer primary key autoincrement,"
                  + "id_tmc integer DEFAULT 0 REFERENCES tmc(_id) ON DELETE SET DEFAULT,"
                  + "kol real not null,"
                  + "ed integer DEFAULT 0 REFERENCES tmc_ed(_id) ON DELETE SET DEFAULT,"
                  + "price real not null,"
                  + "sumka real,"
                  + "id_post integer DEFAULT 0 REFERENCES postav(_id) ON DELETE SET DEFAULT,"
                  + "data_ins integer,"
                  + "ok integer default 0);");

          db.execSQL("create table klient ("
                  + "_id integer primary key autoincrement,"
                  + "name text not null,"
                  + "sumka real,"
                  + "prim text,"
                  + "data_ins integer,"
                  + "ok integer default 0);");
    
          db.execSQL("create table postav ("
                  + "_id integer primary key autoincrement,"
                  + "name text not null,"
                  + "adres text,"
                  + "telef text,"
                  + "prim text,"
                  + "data_ins integer,"
                  + "ok integer default 0);");

          db.execSQL("INSERT or replace INTO foo (_id,name) VALUES(0,'-нет-')");
          db.execSQL("INSERT or replace INTO postav (_id,name) VALUES(0,'-нет-')");
          db.execSQL("INSERT or replace INTO klient (_id,name) VALUES(0,'-нет-')");
          db.execSQL("INSERT or replace INTO tmc_ed (_id,name) VALUES(1,'Л')");
          db.execSQL("INSERT or replace INTO tmc_ed (_id,name) VALUES(2,'ШТ')");
          db.execSQL("INSERT or replace INTO tmc_ed (_id,name) VALUES(3,'КГ')");
          db.execSQL("INSERT or replace INTO tmc_pgr (_id,name) VALUES(0,'-нет-')");
          db.execSQL("INSERT or replace INTO tmc_pgr (_id,name) VALUES(1,'ТАРА')");
          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(1,'0.5Л',1,2,1,0,1,0.5)");
          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(2,'1Л',1,2,1,0,1,1)");
          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(3,'1.5Л',1,2,1,0,1,1.5)");
          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(4,'2Л',1,2,1,0,1,2)");
          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(5,'2.5Л',1,2,1,0,1,2.5)");
          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(6,'3Л',1,2,1,0,1,3)");
          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(7,'СТАКАН',1,2,1,0,1,0.5)");
          db.execSQL("CREATE INDEX pri_tmc ON prixod (id_tmc);");       
          db.execSQL("CREATE INDEX pri_ed ON prixod (ed);");
          db.execSQL("CREATE INDEX pri_post ON prixod (id_post);");
          db.execSQL("CREATE INDEX ras_tmc ON rasxod (id_tmc);");       
          db.execSQL("CREATE INDEX ras_ed ON rasxod (ed);");
          db.execSQL("CREATE INDEX ras_post ON rasxod (id_post);");
          db.execSQL("CREATE INDEX ras_klient ON rasxod (id_klient);");
          db.execSQL("CREATE INDEX ost_tmc ON ostat (id_tmc);");
          db.execSQL("CREATE INDEX ost_ed ON ostat (ed);");
          db.execSQL("CREATE INDEX ost_post ON ostat (id_post);");
          db.execSQL("CREATE INDEX ost_kol ON ostat (kol);");
          db.execSQL("CREATE INDEX ost_sumka ON ostat (sumka);");
          Log.d("MyLog", " --- onCreate create all table --- ");
          //////prixod
          db.execSQL("CREATE TRIGGER pri_ins1 " +
        		    " AFTER INSERT "+
        		    " ON prixod "+    	        		    
        		    " FOR EACH ROW WHEN not EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
        		    " BEGIN "+
        		    " insert into ostat (id_tmc, kol, ed, price, sumka, id_post, data_ins, ok) values (NEW.id_tmc, NEW.kol, NEW.ed, NEW.price, NEW.price*NEW.kol, NEW.id_post, NEW.data_ins, 0); "+
        		    " END;");
          
          db.execSQL("CREATE TRIGGER pri_ins2 " +
      		    " AFTER INSERT "+
      		    " ON prixod "+    	        		    
      		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
      		    " BEGIN "+
      		    " update ostat set kol=kol+NEW.kol, price=NEW.price, sumka=sumka+NEW.price*NEW.kol, data_ins=NEW.data_ins where ed=NEW.ed and id_tmc=NEW.id_tmc and id_post=NEW.id_post; "+
      		    " END;");
          
          db.execSQL("CREATE TRIGGER pri_upd1 " +
        		    " AFTER UPDATE "+
        		    " ON prixod "+    	        		    
        		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=OLD.ed and id_tmc = OLD.id_tmc and id_post=OLD.id_post) "+
        		    " BEGIN "+
        		    " update ostat set kol=kol-OLD.kol, price=OLD.price, sumka=sumka-OLD.price*OLD.kol, data_ins=OLD.data_ins where ed=OLD.ed and id_tmc=OLD.id_tmc and id_post=OLD.id_post; "+
        		    " END;");
          db.execSQL("CREATE TRIGGER pri_upd2 " +
      		    " AFTER UPDATE "+
      		    " ON prixod "+    	        		    
      		    " FOR EACH ROW WHEN not EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
      		    " BEGIN "+
      		    " insert into ostat (id_tmc, kol, ed, price, sumka, id_post, data_ins, ok) values (NEW.id_tmc, NEW.kol, NEW.ed, NEW.price, NEW.price*New.kol, NEW.id_post, NEW.data_ins, 0); "+
      		    " END;");
          db.execSQL("CREATE TRIGGER pri_upd3 " +
        		    " AFTER UPDATE "+
        		    " ON prixod "+    	        		    
        		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
        		    " BEGIN "+
        		    " update ostat set kol=kol+NEW.kol, price=NEW.price, sumka=sumka+NEW.kol*NEW.price, data_ins=NEW.data_ins where ed=NEW.ed and id_tmc=NEW.id_tmc and id_post=NEW.id_post; "+
        		    " END;");
          db.execSQL("CREATE TRIGGER pri_del1 " +
        		    " BEFORE DELETE "+
        		    " ON prixod "+    	        		    
        		    " FOR EACH ROW "+
        		    " BEGIN "+
        		    " insert into prixod_del (id_id, id_tmc, kol, ed, price, id_post, data_ins, ok) values (OLD._id, OLD.id_tmc, OLD.kol, OLD.ed, OLD.price, OLD.id_post, OLD.data_ins, OLD.ok); "+
        		    " END;");
          db.execSQL("CREATE TRIGGER pri_del2 " +
      		    " AFTER DELETE "+
      		    " ON prixod "+    	        		    
      		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=OLD.ed and id_tmc = OLD.id_tmc and id_post=OLD.id_post) "+
      		    " BEGIN "+
      		    " update ostat set kol=kol-OLD.kol, price=OLD.price, sumka=sumka-OLD.price*OLD.kol, data_ins=OLD.data_ins where ed=OLD.ed and id_tmc=OLD.id_tmc and id_post=OLD.id_post; "+
      		    " END;");
          ////rasxod
          db.execSQL("CREATE TRIGGER ras_ins1 " +
      		    " AFTER INSERT "+
      		    " ON rasxod "+    	        		    
      		    " FOR EACH ROW WHEN not EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
      		    " BEGIN "+
      		    " insert into ostat (id_tmc, kol, ed, price, sumka, id_post, data_ins, ok) values (NEW.id_tmc, -NEW.kol, NEW.ed, NEW.price, NEW.price*NEW.kol, NEW.id_post, NEW.data_ins, 0); "+
      		    " END;");
        
        db.execSQL("CREATE TRIGGER ras_ins2 " +
    		    " AFTER INSERT "+
    		    " ON rasxod "+    	        		    
    		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    		    " BEGIN "+
    		    " update ostat set kol=kol-NEW.kol, price=NEW.price, sumka=sumka-NEW.price*NEW.kol, data_ins=NEW.data_ins where ed=NEW.ed and id_tmc=NEW.id_tmc and id_post=NEW.id_post; "+
    		    " END;");
        
        db.execSQL("CREATE TRIGGER ras_upd1 " +
      		    " AFTER UPDATE "+
      		    " ON rasxod "+    	        		    
      		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=OLD.ed and id_tmc = OLD.id_tmc and id_post=OLD.id_post) "+
      		    " BEGIN "+
      		    " update ostat set kol=kol+OLD.kol, price=OLD.price, sumka=sumka+OLD.price*OLD.kol, data_ins=OLD.data_ins where ed=OLD.ed and id_tmc=OLD.id_tmc and id_post=OLD.id_post; "+
      		    " END;");
        db.execSQL("CREATE TRIGGER ras_upd2 " +
    		    " AFTER UPDATE "+
    		    " ON rasxod "+    	        		    
    		    " FOR EACH ROW WHEN not EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    		    " BEGIN "+
    		    " insert into ostat (id_tmc, kol, ed, price, sumka, id_post, data_ins, ok) values (NEW.id_tmc, -NEW.kol, NEW.ed, NEW.price, -NEW.price*NEW.kol, NEW.id_post, NEW.data_ins, 0); "+
    		    " END;");
        db.execSQL("CREATE TRIGGER ras_upd3 " +
      		    " AFTER UPDATE "+
      		    " ON rasxod "+    	        		    
      		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
      		    " BEGIN "+
      		    " update ostat set kol=kol-NEW.kol, price=NEW.price, sumka=sumka-NEW.price*NEW.kol, data_ins=NEW.data_ins where ed=NEW.ed and id_tmc=NEW.id_tmc and id_post=NEW.id_post; "+
      		    " END;");
        db.execSQL("CREATE TRIGGER ras_del1 " +
      		    " BEFORE DELETE "+
      		    " ON rasxod "+    	        		    
      		    " FOR EACH ROW "+
      		    " BEGIN "+
      		    " insert into rasxod_del (id_id, id_tmc, kol, ed, price, id_post, data_ins, ok) values (OLD._id, OLD.id_tmc, OLD.kol, OLD.ed, OLD.price, OLD.id_post, OLD.data_ins, OLD.ok); "+
      		    " END;");
        db.execSQL("CREATE TRIGGER ras_del2 " +
    		    " AFTER DELETE "+
    		    " ON rasxod "+    	        		    
    		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=OLD.ed and id_tmc = OLD.id_tmc and id_post=OLD.id_post) "+
    		    " BEGIN "+
    		    " update ostat set kol=kol+OLD.kol, price=OLD.price, sumka=sumka+OLD.price*OLD.kol, data_ins=OLD.data_ins where ed=OLD.ed and id_tmc=OLD.id_tmc and id_post=OLD.id_post; "+
    		    " END;");
          Log.d("MyLog", " --- onCreate create all trigger --- ");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	Log.d("MyLog", " --- onUpgrade database --- ");
    	if (newVersion == 2) {
    	    db.beginTransaction();
    	    try {
    	    	
    	    	  db.execSQL("DROP INDEX pri_tmc;");       
    	          db.execSQL("DROP INDEX pri_ed;");
    	          db.execSQL("DROP INDEX pri_post;");
    	          db.execSQL("DROP INDEX ras_tmc;");       
    	          db.execSQL("DROP INDEX ras_ed;");
    	          db.execSQL("DROP INDEX ras_post;");
    	          db.execSQL("DROP INDEX ras_klient;");
    	          db.execSQL("DROP INDEX ost_tmc;");
    	          db.execSQL("DROP INDEX ost_ed;");
    	          db.execSQL("DROP INDEX ost_post;");
    	          db.execSQL("DROP INDEX ost_kol;");
    	          
    	      db.execSQL("drop table foo;");

    	      db.execSQL("drop table tmc;");
    	      
    	      db.execSQL("drop table tmc_ed;");
    	      
    	      db.execSQL("drop table tmc_pgr;");
    
    	      db.execSQL("drop table prixod;");
    	      
    	      db.execSQL("drop table prixod_del;");

    	      db.execSQL("drop table rasxod;");
    	      
    	      db.execSQL("drop table rasxod_del;");
 
    	      db.execSQL("drop table ostat;");
   
    	      db.execSQL("drop table klient;");

    	      db.execSQL("drop table postav;");
    	      
    	      db.execSQL("create table foo ("
    	                + "_id integer primary key,"
    	                + "name text default '-нет-' );");
    	    // REFERENCES ostat(id_tmc) ON DELETE RESTRICT	
    	      db.execSQL("create table tmc ("
    	              + "_id integer primary key autoincrement,"
    	              + "name text not null UNIQUE ON CONFLICT IGNORE,"
    	              + "pgr integer DEFAULT 0 REFERENCES tmc_pgr(_id) ON DELETE SET DEFAULT,"
    	              + "ed integer DEFAULT 0 REFERENCES tmc_ed(_id) ON DELETE SET DEFAULT,"
    	              + "price real not null CHECK (price>=0),"
    	              + "data_ins integer,"
    	              + "vis integer default 1,"
    	              + "tara real default 0,"
    	              + "ok integer default 0 );");
    	   
    	          db.execSQL("create table tmc_pgr ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "name text not null UNIQUE ON CONFLICT IGNORE,"
    	                  + "data_ins integer);");
    	   
    	          db.execSQL("create table tmc_ed ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "name text not null UNIQUE ON CONFLICT IGNORE);");
    	          
    	          db.execSQL("create table prixod ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "id_tmc integer DEFAULT 0 REFERENCES tmc(_id) ON DELETE SET DEFAULT,"
    	                  + "kol real not null,"
    	                  + "ed integer DEFAULT 0 REFERENCES tmc_ed(_id) ON DELETE SET DEFAULT,"
    	                  + "price real not null CHECK (price>=0),"
    	                  + "id_post integer DEFAULT 0 REFERENCES postav(_id) ON DELETE SET DEFAULT,"
    	                  + "prim text,"
    	                  + "data_ins integer,"
    	                 // + "data_del integer default 0,"
    	                  + "ok integer default 0);");
    	          
    	          db.execSQL("create table prixod_del ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "id_id integer,"
    	                  + "id_tmc integer not null,"
    	                  + "kol real not null,"
    	                  + "ed integer not null,"
    	                  + "price real not null,"
    	                  + "id_post integer,"
    	                  + "prim text,"
    	                  + "data_ins integer,"
    	                //  + "data_del integer default 0,"
    	                  + "ok integer default 0);");
    	          
    	          db.execSQL("create table rasxod ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "id_tmc integer DEFAULT 0 REFERENCES tmc(_id) ON DELETE SET DEFAULT,"
    	                  + "kol real not null,"
    	                  + "ed integer DEFAULT 0 REFERENCES tmc_ed(_id) ON DELETE SET DEFAULT,"
    	                  + "price real not null CHECK (price>=0),"
    	                  + "id_post integer DEFAULT 0 REFERENCES postav(_id) ON DELETE SET DEFAULT,"
    	                  + "id_klient integer DEFAULT 0 REFERENCES klient(_id) ON DELETE SET DEFAULT,"
    	                  + "prim text,"
    	                  + "data_ins integer,"
    	                 // + "data_del integer default 0,"
    	                  + "ok integer default 0);");
    	          
    	          db.execSQL("create table rasxod_del ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "id_id integer,"
    	                  + "id_tmc integer not null,"
    	                  + "kol real not null,"
    	                  + "ed integer not null,"
    	                  + "price real not null,"
    	                  + "id_post integer,"
    	                  + "id_klient integer,"
    	                  + "prim text,"
    	                  + "data_ins integer,"
    	                //  + "data_del integer default 0,"
    	                  + "ok integer default 0);");

    	          db.execSQL("create table ostat ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "id_tmc integer DEFAULT 0 REFERENCES tmc(_id) ON DELETE SET DEFAULT,"
    	                  + "kol real not null,"
    	                  + "ed integer DEFAULT 0 REFERENCES tmc_ed(_id) ON DELETE SET DEFAULT,"
    	                  + "price real not null,"
    	                  + "sumka real,"
    	                  + "id_post integer DEFAULT 0 REFERENCES postav(_id) ON DELETE SET DEFAULT,"
    	                  + "data_ins integer,"
    	                  + "ok integer default 0);");

    	          db.execSQL("create table klient ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "name text not null,"
    	                  + "sumka real,"
    	                  + "prim text,"
    	                  + "data_ins integer,"
    	                  + "ok integer default 0);");
    	    
    	          db.execSQL("create table postav ("
    	                  + "_id integer primary key autoincrement,"
    	                  + "name text not null,"
    	                  + "adres text,"
    	                  + "telef text,"
    	                  + "prim text,"
    	                  + "data_ins integer,"
    	                  + "ok integer default 0);");

    	          db.execSQL("INSERT or replace INTO foo (_id,name) VALUES(0,'-нет-')");
    	          db.execSQL("INSERT or replace INTO postav (_id,name) VALUES(0,'-нет-')");
    	          db.execSQL("INSERT or replace INTO klient (_id,name) VALUES(0,'-нет-')");
    	          db.execSQL("INSERT or replace INTO tmc_ed (_id,name) VALUES(1,'Л')");
    	          db.execSQL("INSERT or replace INTO tmc_ed (_id,name) VALUES(2,'ШТ')");
    	          db.execSQL("INSERT or replace INTO tmc_ed (_id,name) VALUES(3,'КГ')");
    	          db.execSQL("INSERT or replace INTO tmc_pgr (_id,name) VALUES(0,'-нет-')");
    	          db.execSQL("INSERT or replace INTO tmc_pgr (_id,name) VALUES(1,'ТАРА')");
    	          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(1,'0.5Л',1,2,1,0,1,0.5)");
    	          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(2,'1Л',1,2,1,0,1,1)");
    	          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(3,'1.5Л',1,2,1,0,1,1.5)");
    	          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(4,'2Л',1,2,1,0,1,2)");
    	          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(5,'2.5Л',1,2,1,0,1,2.5)");
    	          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(6,'3Л',1,2,1,0,1,3)");
    	          db.execSQL("INSERT or replace INTO tmc (_id,name,pgr,ed,price,vis,ok,tara) VALUES(7,'СТАКАН',1,2,1,0,1,0.5)");
    	          db.execSQL("CREATE INDEX pri_tmc ON prixod (id_tmc);");       
    	          db.execSQL("CREATE INDEX pri_ed ON prixod (ed);");
    	          db.execSQL("CREATE INDEX pri_post ON prixod (id_post);");
    	          db.execSQL("CREATE INDEX ras_tmc ON rasxod (id_tmc);");       
    	          db.execSQL("CREATE INDEX ras_ed ON rasxod (ed);");
    	          db.execSQL("CREATE INDEX ras_post ON rasxod (id_post);");
    	          db.execSQL("CREATE INDEX ras_klient ON rasxod (id_klient);");
    	          db.execSQL("CREATE INDEX ost_tmc ON ostat (id_tmc);");
    	          db.execSQL("CREATE INDEX ost_ed ON ostat (ed);");
    	          db.execSQL("CREATE INDEX ost_post ON ostat (id_post);");
    	          db.execSQL("CREATE INDEX ost_kol ON ostat (kol);");
    	          db.execSQL("CREATE INDEX ost_sumka ON ostat (sumka);");
    	          Log.d("MyLog", " --- onUpgrade create all table --- ");
    	          //////prixod
    	          db.execSQL("CREATE TRIGGER pri_ins1 " +
    	        		    " AFTER INSERT "+
    	        		    " ON prixod "+    	        		    
    	        		    " FOR EACH ROW WHEN not EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    	        		    " BEGIN "+
    	        		    " insert into ostat (id_tmc, kol, ed, price, sumka, id_post, data_ins, ok) values (NEW.id_tmc, NEW.kol, NEW.ed, NEW.price, NEW.price*NEW.kol, NEW.id_post, NEW.data_ins, 0); "+
    	        		    " END;");
    	          
    	          db.execSQL("CREATE TRIGGER pri_ins2 " +
    	      		    " AFTER INSERT "+
    	      		    " ON prixod "+    	        		    
    	      		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    	      		    " BEGIN "+
    	      		    " update ostat set kol=kol+NEW.kol, price=NEW.price, sumka=sumka+NEW.price*NEW.kol, data_ins=NEW.data_ins where ed=NEW.ed and id_tmc=NEW.id_tmc and id_post=NEW.id_post; "+
    	      		    " END;");
    	          
    	          db.execSQL("CREATE TRIGGER pri_upd1 " +
    	        		    " AFTER UPDATE "+
    	        		    " ON prixod "+    	        		    
    	        		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=OLD.ed and id_tmc = OLD.id_tmc and id_post=OLD.id_post) "+
    	        		    " BEGIN "+
    	        		    " update ostat set kol=kol-OLD.kol, price=OLD.price, sumka=sumka-OLD.price*OLD.kol, data_ins=OLD.data_ins where ed=OLD.ed and id_tmc=OLD.id_tmc and id_post=OLD.id_post; "+
    	        		    " END;");
    	          db.execSQL("CREATE TRIGGER pri_upd2 " +
    	      		    " AFTER UPDATE "+
    	      		    " ON prixod "+    	        		    
    	      		    " FOR EACH ROW WHEN not EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    	      		    " BEGIN "+
    	      		    " insert into ostat (id_tmc, kol, ed, price, sumka, id_post, data_ins, ok) values (NEW.id_tmc, NEW.kol, NEW.ed, NEW.price, NEW.price*New.kol, NEW.id_post, NEW.data_ins, 0); "+
    	      		    " END;");
    	          db.execSQL("CREATE TRIGGER pri_upd3 " +
    	        		    " AFTER UPDATE "+
    	        		    " ON prixod "+    	        		    
    	        		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    	        		    " BEGIN "+
    	        		    " update ostat set kol=kol+NEW.kol, price=NEW.price, sumka=sumka+NEW.kol*NEW.price, data_ins=NEW.data_ins where ed=NEW.ed and id_tmc=NEW.id_tmc and id_post=NEW.id_post; "+
    	        		    " END;");
    	          db.execSQL("CREATE TRIGGER pri_del1 " +
    	        		    " BEFORE DELETE "+
    	        		    " ON prixod "+    	        		    
    	        		    " FOR EACH ROW "+
    	        		    " BEGIN "+
    	        		    " insert into prixod_del (id_id, id_tmc, kol, ed, price, id_post, data_ins, ok) values (OLD._id, OLD.id_tmc, OLD.kol, OLD.ed, OLD.price, OLD.id_post, OLD.data_ins, OLD.ok); "+
    	        		    " END;");
    	          db.execSQL("CREATE TRIGGER pri_del2 " +
    	      		    " AFTER DELETE "+
    	      		    " ON prixod "+    	        		    
    	      		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=OLD.ed and id_tmc = OLD.id_tmc and id_post=OLD.id_post) "+
    	      		    " BEGIN "+
    	      		    " update ostat set kol=kol-OLD.kol, price=OLD.price, sumka=sumka-OLD.price*OLD.kol, data_ins=OLD.data_ins where ed=OLD.ed and id_tmc=OLD.id_tmc and id_post=OLD.id_post; "+
    	      		    " END;");
    	          ////rasxod
    	          db.execSQL("CREATE TRIGGER ras_ins1 " +
    	      		    " AFTER INSERT "+
    	      		    " ON rasxod "+    	        		    
    	      		    " FOR EACH ROW WHEN not EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    	      		    " BEGIN "+
    	      		    " insert into ostat (id_tmc, kol, ed, price, sumka, id_post, data_ins, ok) values (NEW.id_tmc, -NEW.kol, NEW.ed, NEW.price, NEW.price*NEW.kol, NEW.id_post, NEW.data_ins, 0); "+
    	      		    " END;");
    	        
    	        db.execSQL("CREATE TRIGGER ras_ins2 " +
    	    		    " AFTER INSERT "+
    	    		    " ON rasxod "+    	        		    
    	    		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    	    		    " BEGIN "+
    	    		    " update ostat set kol=kol-NEW.kol, price=NEW.price, sumka=sumka-NEW.price*NEW.kol, data_ins=NEW.data_ins where ed=NEW.ed and id_tmc=NEW.id_tmc and id_post=NEW.id_post; "+
    	    		    " END;");
    	        
    	        db.execSQL("CREATE TRIGGER ras_upd1 " +
    	      		    " AFTER UPDATE "+
    	      		    " ON rasxod "+    	        		    
    	      		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=OLD.ed and id_tmc = OLD.id_tmc and id_post=OLD.id_post) "+
    	      		    " BEGIN "+
    	      		    " update ostat set kol=kol+OLD.kol, price=OLD.price, sumka=sumka+OLD.price*OLD.kol, data_ins=OLD.data_ins where ed=OLD.ed and id_tmc=OLD.id_tmc and id_post=OLD.id_post; "+
    	      		    " END;");
    	        db.execSQL("CREATE TRIGGER ras_upd2 " +
    	    		    " AFTER UPDATE "+
    	    		    " ON rasxod "+    	        		    
    	    		    " FOR EACH ROW WHEN not EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    	    		    " BEGIN "+
    	    		    " insert into ostat (id_tmc, kol, ed, price, sumka, id_post, data_ins, ok) values (NEW.id_tmc, -NEW.kol, NEW.ed, NEW.price, -NEW.price*NEW.kol, NEW.id_post, NEW.data_ins, 0); "+
    	    		    " END;");
    	        db.execSQL("CREATE TRIGGER ras_upd3 " +
    	      		    " AFTER UPDATE "+
    	      		    " ON rasxod "+    	        		    
    	      		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=NEW.ed and id_tmc = NEW.id_tmc and id_post=NEW.id_post) "+
    	      		    " BEGIN "+
    	      		    " update ostat set kol=kol-NEW.kol, price=NEW.price, sumka=sumka-NEW.price*NEW.kol, data_ins=NEW.data_ins where ed=NEW.ed and id_tmc=NEW.id_tmc and id_post=NEW.id_post; "+
    	      		    " END;");
    	        db.execSQL("CREATE TRIGGER ras_del1 " +
    	      		    " BEFORE DELETE "+
    	      		    " ON rasxod "+    	        		    
    	      		    " FOR EACH ROW "+
    	      		    " BEGIN "+
    	      		    " insert into rasxod_del (id_id, id_tmc, kol, ed, price, id_post, data_ins, ok) values (OLD._id, OLD.id_tmc, OLD.kol, OLD.ed, OLD.price, OLD.id_post, OLD.data_ins, OLD.ok); "+
    	      		    " END;");
    	        db.execSQL("CREATE TRIGGER ras_del2 " +
    	    		    " AFTER DELETE "+
    	    		    " ON rasxod "+    	        		    
    	    		    " FOR EACH ROW WHEN EXISTS (SELECT * FROM ostat WHERE ed=OLD.ed and id_tmc = OLD.id_tmc and id_post=OLD.id_post) "+
    	    		    " BEGIN "+
    	    		    " update ostat set kol=kol+OLD.kol, price=OLD.price, sumka=sumka+OLD.price*OLD.kol, data_ins=OLD.data_ins where ed=OLD.ed and id_tmc=OLD.id_tmc and id_post=OLD.id_post; "+
    	    		    " END;");
    	          Log.d("MyLog", " --- onUpgrade create all trigger --- ");
    	          
/*"CREATE TRIGGER update_customer_address UPDATE OF address ON customers 
  BEGIN
    UPDATE orders SET address = new.address WHERE customer_name = old.name;
  END;"
 
CREATE TRIGGER before_insert_trigger BEFORE INSERT ON myTable 
WHEN EXISTS (SELECT * FROM myTable WHERE myfield = NEW.myfield)
BEGIN 
RAISE(ABORT,1);
END;

db.execSQL("CREATE TRIGGER fk_empdept_deptid " +
    " BEFORE INSERT "+
    " ON "+employeeTable+
    
    " FOR EACH ROW BEGIN"+
    " SELECT CASE WHEN ((SELECT "+colDeptID+" FROM "+deptTable+" WHERE "+colDeptID+"=new."+colDept+" ) IS NULL)"+
    " THEN RAISE (ABORT,'Foreign Key Violation') END;"+
    "  END;");
  */
    	      db.setTransactionSuccessful();
    
    	    } finally {
    	      db.endTransaction();
    	    }
    	  }
    }
  }
}
