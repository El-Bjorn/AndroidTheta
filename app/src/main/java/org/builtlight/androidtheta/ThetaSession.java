package org.builtlight.androidtheta;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by bjorn on 3/28/16.
 */
public class ThetaSession {
    public static final String TAG = "THETA_SESSION";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    // network constants
    private static final String CAMERA_URL = "http://192.168.1.1/osc";
    private static final String INFO_REQ_PATH = "/info";

    private boolean hasThetaSession;

    private Context mContext;
    private OkHttpClient mOurClient;
    private String mSessionId; // Theta session id

    // constructor always requires a Context from the activity
    public ThetaSession(Context mContext){
        this.mContext = mContext; // need this for system stuff
        hasThetaSession = false; // assume no
        mSessionId = "";
        mOurClient = null;

        if (weHasNetwork()) {
            mOurClient = new OkHttpClient();
            startThetaSession();


            //
        }
    }

    public void startThetaSession() {
        String startSessionURL = CAMERA_URL + "/commands/execute";
        Log.d(TAG,"URL: "+startSessionURL);
        String sessionStartPostParm = "{ \"name\": \"camera.startSession\", \"parameters\":[] }";
        Log.d(TAG,"request body: "+sessionStartPostParm);
        RequestBody body = RequestBody.create(JSON,sessionStartPostParm);

        Request request = new Request.Builder().url(startSessionURL).post(body).build();
        Call call = mOurClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"Session start failed: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()){
                        Log.d(TAG, "got session: " + response.body().string());
                    } else {
                        Log.d(TAG," BOOOOOO!!!!!" + response.body().string());
                    }
                } catch (IOException e){
                    Log.e(TAG,"IO Exception: "+ e);
                }

            }
        });

        //Response response = mOurClient.newCall(request).execute();

        //System.out.println(response.body().string());


    }

    public void getCameraInfo() {
        if (weHasNetwork()){
            String infoUrl = CAMERA_URL + INFO_REQ_PATH;
            Log.d(TAG,"requesting url: " + infoUrl);
            Request request = new Request.Builder().url(infoUrl).build();
            Call call = mOurClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG,"Network info call failure:" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()){
                            Log.d(TAG,"Talking to the camera: " + response.body().string());

                        } else {
                            Log.d(TAG,"We can a bad response: " + response.body().string());
                        }
                    } catch (IOException e){
                        Log.e(TAG, "IO Exception caught: ", e);
                    }
                }
            });
        }
    }


    //private void getComeraInfo

    // Test for minimal connectivity
    private boolean weHasNetwork() {
        ConnectivityManager manager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}
