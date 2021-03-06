package luce.birra;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
 
public class DialogActivity extends android.support.v4.app.DialogFragment  implements OnClickListener {
 
  final String LOG_TAG = "myLogs";
 
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
        .setTitle("Title!").setPositiveButton(R.string.yes, this)
        .setNegativeButton(R.string.no, this)
        .setNeutralButton(R.string.maybe, this)
        .setMessage(R.string.message);
    return adb.create();
  }
 
  public void onClick(DialogInterface dialog, int which) {
    int i = 0;
    switch (which) {
    case Dialog.BUTTON_POSITIVE:
      i = R.string.yes;
      break;
    case Dialog.BUTTON_NEGATIVE:
      i = R.string.no;
      break;
    case Dialog.BUTTON_NEUTRAL:
      i = R.string.maybe;
      break;
    }
    if (i > 0)
      //Log.d(LOG_TAG, "Dialog 2: " + getResources().getString(i));
  }
 
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    //Log.d(LOG_TAG, "Dialog 2: onDismiss");
  }
 
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    //Log.d(LOG_TAG, "Dialog 2: onCancel");
  }
}