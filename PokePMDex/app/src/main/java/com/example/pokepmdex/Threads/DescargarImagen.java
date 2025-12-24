package com.example.pokepmdex.Threads;

import android.widget.ImageView;

import com.example.pokepmdex.NetUtils;

import java.io.IOException;

public class DescargarImagen implements Runnable {

    private ImageView image;
    private String imageURL;

    public DescargarImagen(ImageView image, String imageURL) {
        this.image = image;
        this.imageURL = imageURL;
    }

    @Override
    public void run() {
        try {
            image.setImageBitmap(NetUtils.getURLBitmap(imageURL));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
