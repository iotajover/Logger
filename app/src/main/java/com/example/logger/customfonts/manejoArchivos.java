package com.example.logger.customfonts;

import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class manejoArchivos extends AppCompatActivity {

    final static String fileName = "data.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/" ;
    final static String TAG = FileBackupHelper.class.getName();

    public static String ReadFile( Context context){
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream (new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(TAG+ ex.getMessage());
        }
        catch(IOException ex) {
            System.out.println(TAG+ ex.getMessage());
        }
        return line;
    }

    public static boolean saveToFile( String data){
        try {
            Gson
            new File(path  ).mkdir();
            File file = new File(path+ System.currentTimeMillis()+".txt");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());

            return true;
        }  catch(FileNotFoundException ex) {
            System.out.println(TAG+ ex.getMessage());
        }  catch(IOException ex) {
            System.out.println(TAG+ ex.getMessage());
        }
        return  false;
    }

    public List<String> leerRutasArchivos(){
        new File(path  ).mkdir();
        List <String> listarFicherosPorCarpeta = new ArrayList<>();
        File file = new File(path);
        for (final File ficheroEntrada : file.listFiles()) {
            if (ficheroEntrada.isDirectory()) {
                listarFicherosPorCarpeta.add(ficheroEntrada.getName());
            } else {
                listarFicherosPorCarpeta.add(ficheroEntrada.getName());
            }
        }
        return listarFicherosPorCarpeta;
    }

    public boolean eliminarArchivo(String ruta){
        try{
            String fileName = path +ruta;
            File myFile = new File(fileName);
            if(myFile.exists())
                myFile.delete();
            return true;
        }catch(Exception ex){
            return false;
        }
    }

}
