package org.builtlight.androidtheta;

public abstract class DownloadPicCompBloc implements Runnable {
    String imgData = null;
    Boolean picDidDownload = false;

    @Override
    public void run(){
        System.out.println("DPCM OVERRIDE DAMMIT");
    }


}
