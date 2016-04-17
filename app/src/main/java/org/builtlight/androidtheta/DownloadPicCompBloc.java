package org.builtlight.androidtheta;

import android.graphics.Bitmap;

public abstract class DownloadPicCompBloc implements Runnable {
    Bitmap imgData = null;
    Boolean picDidDownload = false;

    @Override
    public void run(){
        System.out.println("DPCM OVERRIDE DAMMIT");
    }


}
