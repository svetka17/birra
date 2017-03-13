package luce.birra;
import android.os.AsyncTask;
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
public class /*ExportDatabaseCSVTask*/ Export2Excel extends AsyncTask<String, String, Boolean> {
/*    private final ProgressDialog dialog = new ProgressDialog(SearchResultActivity.this);
    boolean memoryErr = false;

    // to show Loading dialog box
    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Exporting database...");
        this.dialog.show();
    }

    // to write process 
     */
    protected Boolean doInBackground(final String... args) {
   /*
        boolean success = false;

        String currentDateString = new SimpleDateFormat(Constants.SimpleDtFrmt_ddMMyyyy).format(new Date());

        File dbFile = Environment. getDatabasePath("birra.db");
        Log.v("MyLog", "Db path is: " + dbFile); // get the path of db
        File exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + "filename", "");

        long freeBytesInternal = new File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getFreeSpace();           
        long megAvailable = freeBytesInternal / 1048576;

        if (megAvailable < 0.1) {               
            System.out.println("Please check"+megAvailable);
            memoryErr = true;               
        }else {             
            exportDirStr = exportDir.toString();// to show in dialogbox
            Log.v(TAG, "exportDir path::" + exportDir);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }   
            try {
                List<SalesActivity> listdata = salesLst;
                SalesActivity sa = null;
                String lob = null;
                for (int index = 0; index < listdata.size();) {
                    sa = listdata.get(index);
                    lob = sa.getLob();
                    break;
                }
                if (Constants.Common.OCEAN_LOB.equals(lob)) {

                    file = new File(exportDir, Constants.FileNm.FILE_OFS + currentDateString + ".csv");
                } else {
                    file = new File(exportDir, Constants.FileNm.FILE_AFS + currentDateString + ".csv");
                }
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));


                // this is the Column of the table and same for Header of CSV
                // file
                if (Constants.Common.OCEAN_LOB.equals(lob)) {
                    csvWrite.writeNext(Constants.FileNm.CSV_O_HEADER);
                }else{
                    csvWrite.writeNext(Constants.FileNm.CSV_A_HEADER);
                }
                String arrStr1[] = { "SR.No", "CUTSOMER NAME", "PROSPECT", "PORT OF LOAD", "PORT OF DISCHARGE" };
                csvWrite.writeNext(arrStr1);

                if (listdata.size() > 0) {
                    for (int index = 0; index < listdata.size(); index++) {
                        sa = listdata.get(index);
                        String pol;
                        String pod;
                        if (Constants.Common.OCEAN_LOB.equals(sa.getLob())) {
                            pol = sa.getPortOfLoadingOENm();
                            pod = sa.getPortOfDischargeOENm();
                        } else {
                            pol = sa.getAirportOfLoadNm();
                            pod = sa.getAirportOfDischargeNm();
                        }
                        int srNo = index;
                        String arrStr[] = { String.valueOf(srNo + 1), sa.getCustomerNm(), sa.getProspectNm(), pol, pod };
                        csvWrite.writeNext(arrStr);
                    }
                    success = true;
                }
                csvWrite.close();

            } catch (IOException e) {
                Log.e("SearchResultActivity", e.getMessage(), e);
                return success;
            }
        }*/
        return true;//success;
    }

    // close dialog and give msg
    protected void onPostExecute(Boolean success) {
      /*  if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        if (success) {
            dialogBox(Constants.Flag.FLAG_EXPRT_S);
        } else {                
            if (memoryErr==true) {
                dialogBox(Constants.Flag.FLAG_MEMORY_ERR);
            } else {
                dialogBox(Constants.Flag.FLAG_EXPRT_F);
            }
        }*/
    }
}
