package org.builtlight.androidtheta;

public abstract class TakePicCompBloc implements Runnable {
    String imgURI = null;
    Boolean pictureDidComplete = false;

    @Override
    public void run() {
        System.out.println("TPCB OVERRIDE DAMMIT");
    }
}

