package org.builtlight.androidtheta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThetaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theta);

        ThetaSession thetaSession = new ThetaSession(this);
    }
}
