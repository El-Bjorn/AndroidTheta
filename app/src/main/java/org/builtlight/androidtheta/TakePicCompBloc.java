package org.builtlight.androidtheta;

public abstract class TakePicCompBloc implements Runnable {
    String imgURI = null;

    @Override
    public void run() {
        System.out.println("OVERRIDE DAMMIT");
    }
}
