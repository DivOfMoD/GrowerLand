package com.divofmod.quizer.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private File path;
    private String name_file;
    private String old_name_file;

   public DBHelper(Context context, String dbName, File path, String name_file, String old_name_file) {
        super(context, dbName, null, 1);
        this.path = path;
        this.name_file = name_file;
        this.old_name_file = old_name_file;
    }

    private ArrayList<String> createQueries(File path, String name_file) {
        String queryLine;
        StringBuilder sBuilder = new StringBuilder();
        ArrayList<String> listOfQueries = new ArrayList<>();

        try {
            FileReader fr = new FileReader(new File(path, name_file));
            BufferedReader br = new BufferedReader(fr);

            //Читаем файл SQL построчно.
            while ((queryLine = br.readLine()) != null) {

                sBuilder.append(queryLine);
            }
            br.close();

            new File(path, name_file).delete();
            new File(path, old_name_file).delete();

            String[] splittedQueries = sBuilder.toString().split(";");
            String[] temp;

            for (int i = 0; i < splittedQueries.length - 1; i++) {
                if (!splittedQueries[i].trim().equals("") && !splittedQueries[i].trim().equals("\t")) {
                    temp = splittedQueries[i].split(" values ");

                    listOfQueries.add(temp[0].replaceAll("'", "") + " values " + temp[1] + ";");
                }
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.toString());
        }
        return listOfQueries;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArrayList<String> queries = createQueries(path, name_file);
        for (int i = 0; i < queries.size(); i++) {
            db.execSQL("create table if not exists " + queries.get(i).split(" values ")[0].replaceFirst("insert into ", "").replaceAll(", ", " text, ").replaceFirst("\\)", " text\\)") + ";");
            db.execSQL(queries.get(i));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
