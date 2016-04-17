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
    @InjectView(R.id.connectProgressBar) ProgressBar connectProgress; // spinner
    @InjectView(R.id.takePictureButton) Button takePicButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theta);
        ButterKnife.inject(this);

        final ThetaSession thetaSession = new ThetaSession(this);
        takePicButton.setEnabled(false); // disable until we have session id

        Log.d(TAG, "Started Theta");
        //mOurContext = this;

        connectProgress.setVisibility(View.INVISIBLE);

        // take picture listener
        View.OnClickListener takePictureListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d(TAG, "Attempting to Take a picture");
                connectProgress.setVisibility(View.VISIBLE); // show spinner

                thetaSession.takePicture(new TakePicCompBloc() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // dismiss spinner
                                connectProgress.setVisibility(View.INVISIBLE);

                                if (pictureDidComplete) {
                                    Log.d(TAG, "taking picture complete img uri is ....: " + imgURI);
                                    thetaSession.downloadPictureWithUri(imgURI, new
                                            DownloadPicCompBloc() {
                                                @Override
                                                public void run() {
                                                    //Log.d(TAG, "download ");
                                                    if (picDidDownload) {
                                                        Log.d(TAG,"Download complete");
                                                    }
                                                }
                                            });

                                }
                            }
                        });
                    }
                });
            }
        };
        takePicButton.setOnClickListener(takePictureListener);

        // Session creation lister
        View.OnClickListener createSessionListener = new View.OnClickListener() {
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
                            Log.d(TAG, "got theta session id: " + thetaSession.mSessionId);
                            //thetaSession.takePicture(null);

                        } else {
                            Log.d(TAG, "no theta session id for you");
                            //System.out.printf("no theta sessions");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (thetaSession.hasThetaSession) {
                                    takePicButton.setEnabled(true); // enable picture taking
                                }
                                // dismiss spinner
                                connectProgress.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                });

            }
        };
        createSessButton.setOnClickListener(createSessionListener);

    }
}
