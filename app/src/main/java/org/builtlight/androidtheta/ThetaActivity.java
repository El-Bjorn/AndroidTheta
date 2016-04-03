package org.builtlight.androidtheta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ThetaActivity extends AppCompatActivity {
    public static final String TAG = "THETA_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theta);

        final ThetaSession thetaSession = new ThetaSession(this);
        //Log.d.(thetaSession);
        Log.d(TAG, "new ThetaSession");
        thetaSession.getCameraInfo();
        thetaSession.startThetaSession(new Runnable() {
            @Override
            public void run() {
                if (thetaSession.hasThetaSession) {
                    //System.out.printf("got theta session %s\n", thetaSession.mSessionId);
                    Log.d(TAG,"got theta session id: "+thetaSession.mSessionId);
                    thetaSession.takePicture();

                } else {
                    Log.d(TAG,"no theta session id for you");
                    //System.out.printf("no theta sessions");
                }
            }
        });
    }
}
