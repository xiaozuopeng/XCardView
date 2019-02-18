package cn.cty.cardview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.cv1).setOnClickListener(this);
        findViewById(R.id.cv2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        view.setSelected(!view.isSelected());
    }
}
