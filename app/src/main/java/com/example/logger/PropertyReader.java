package com.example.logger;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private Context context;
    private Properties properties;

    public PropertyReader(Context context) {
        this.context = context;
        this.properties = new Properties();
    }

    public Properties getProperties(String file_name) {
        AssetManager assetManager = this.context.getAssets();
        try {
            InputStream inputStream = assetManager.open(file_name);
            this.properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.properties;
    }
}
