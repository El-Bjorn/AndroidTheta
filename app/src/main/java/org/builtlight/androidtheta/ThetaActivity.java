package org.builtlight.androidtheta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
//import android.content.Context;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ThetaActivity extends AppCompatActivity {
    public static final String TAG = "THETA_ACTIVITY";

    @InjectView(R.id.createSessionButton) Button createSessButton;
    @InjectView(R.id.connectProgressBar) ProgressBar connectProgress;

    //private ThetaSession mThetaSession;
    //private Context mOurContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theta);
        ButterKnife.inject(this);

        final ThetaSession thetaSession = new ThetaSession(this);

        Log.d(TAG, "Started Theta");
        //mOurContext = this;

        connectProgress.setVisibility(View.INVISIBLE);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectProgress.setVisibility(View.VISIBLE);
                //mThetaSession = new ThetaSession(mOurContext);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                connectProgress.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                });

            }
        };
        createSessButton.setOnClickListener(listener);



        /*Log.d(TAG, "new ThetaSession");

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
        }); */
    }
}
