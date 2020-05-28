package de.ur.mi.android.mensaapp.http;

public interface HTTPRequestListener {

    void onResult(String result);
    void onError();

}
