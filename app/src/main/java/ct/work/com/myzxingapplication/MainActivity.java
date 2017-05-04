package ct.work.com.myzxingapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mylhyl.zxing.scanner.common.Intents;

import ct.work.com.myzxingapplication.activity.ScannerActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.button);
        result = (TextView) findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 60);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
//                    ScannerActivity.gotoActivity(MainActivity.this,
//                            checkBox.isChecked(), laserMode);

                    startActivityForResult(new Intent(MainActivity.this,ScannerActivity.class)
                                    .putExtra(ScannerActivity.EXTRA_RETURN_SCANNER_RESULT, false)
                                    .putExtra(ScannerActivity.EXTRA_LASER_LINE_MODE, ScannerActivity.EXTRA_LASER_LINE_MODE_0)
                            , ScannerActivity.REQUEST_CODE_SCANNER);
                }
            }
        });
    }


    private static final int PICK_CONTACT = 1;
    private static final String TAG = "MainActivity";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: ==============data==="+data);
        if (data != null) {
            String stringExtra = data.getStringExtra(Intents.Scan.RESULT);
            result.setText(stringExtra);
        }
        if (resultCode != Activity.RESULT_CANCELED && resultCode == Activity.RESULT_OK) {
            if (requestCode == ScannerActivity.REQUEST_CODE_SCANNER) {
                if (data != null) {
                    String stringExtra = data.getStringExtra(Intents.Scan.RESULT);
                    result.setText(stringExtra);
                }
            } else if (requestCode == PICK_CONTACT) {
                // Data field is content://contacts/people/984
                //showContactAsBarcode(data.getData());
            }
        }
    }
}
