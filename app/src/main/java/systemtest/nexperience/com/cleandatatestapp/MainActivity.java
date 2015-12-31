package systemtest.nexperience.com.cleandatatestapp;

import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
   //
    TextView t2;
    public Handler mHandler;
    public File tempF;

    public void cleanTempData(){
        String tempDir= getCacheDir().getAbsolutePath()+ File.separator +"myTempfile.txt";
        tempF = new File(tempDir);
        if ((tempF.isFile())) {
            trimCache(tempF);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView t = new TextView(this);
        t = (TextView) findViewById(R.id.checkFirstTime);
        t.setText("maybe it's the first time");

        mHandler = new Handler();
         t2 = new TextView(this);
        t2 = (TextView) findViewById(R.id.checkTempData);
        t2.setText("temp data ?");

        //String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String baseDir=getFilesDir().getAbsolutePath();
        String fileName = "myFile.txt";
        File f = new File(baseDir + File.separator + fileName);
//        File outputDir = getCacheDir(); // context being the Activity pointer
//        File outputFile=null;
//        try {
//            outputFile= File.createTempFile("myTemp", "txt", outputDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        String tempDir= getCacheDir().getAbsolutePath()+ File.separator +"myTempfile.txt";
        tempF = new File(tempDir);
        boolean tempFileExists = tempF!=null&tempF.isFile();

         t2 = new TextView(this);
        t2 = (TextView) findViewById(R.id.checkTempData);
        t2.setText("temp data ?");
        if (tempFileExists){
            t2.setText("temp data exits");
        }
        else {
            t2.setText("No temp data");
        }
// Not sure if the / is on the path or not
        boolean fileExists = new File(baseDir + File.separator + fileName).isFile();



        if(fileExists)
        {

            t.setText("Not the first time!!!");

        }
        else
        {


            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(f!=null&f.isFile())
            {

                t.setText("Pop the cherry! first time!!!");

            }
            //And your other stuffs goes here
        }
        Button createTempFile = (Button) findViewById(R.id.create);
        Button delTempFile = (Button) findViewById(R.id.clean);
        createTempFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (!(tempF.isFile())) {
                        tempF.createNewFile();
                    }
                    boolean tempFileExists = tempF.isFile();
                    if (tempFileExists) {
                        t2.setText("temp data exits");
                    } else {
                        t2.setText("No temp data, thats not expected");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        delTempFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                String tempDir= getCacheDir().getAbsolutePath()+ File.separator +"myTempfile.txt";
//                 tempF = new File(tempDir);
                if ((tempF.isFile())) {
                    trimCache(tempF);
                    boolean tempFileExists = tempF.isFile();
                    if (tempFileExists) {
                        t2.setText("temp data exits");
                    } else {
                        t2.setText("No temp data, we just deleted it");
                    }
                }
            }
        });
        Button closeButton = (Button) findViewById(R.id.bClose);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }
    public  void trimCache(File f) {
        try {

            if (f != null && f.isFile()) {
                f.delete();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onDestroy(){

        super.onDestroy();
    }

    @Override
    public void onStop(){
        cleanTempData();
        Log.d("onStop" ," the on stop was called!!!!!");
        super.onStop();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(checkTempStatus, 100);

    }
    private final Runnable checkTempStatus = new Runnable() {
        public void run() {
            // do whatever you want to change here, like:


                //outputFile=  new File(getFilesDir(), "Tempfile.txt");
            if (tempF!=null) {
                boolean tempFileExists = tempF.isFile();


                if (tempFileExists) {
                    t2.setText("temp data exits");
                } else {
                    t2.setText("No temp data");
                }
            }

        }
    };
}
