package in.unicodelabs.kdloggingdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import in.unicodelabs.kdlogging.data.KdLog;
import in.unicodelabs.kdlogging.ui.KdLogListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KdLog.e("Error Message");
                KdLog.d("Debug Message");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, KdLogListActivity.class);
                startActivity(intent);
            }
        });
    }
}
