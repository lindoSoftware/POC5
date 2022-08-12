package com.demo.poc5;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Poc5 {

    public static void main(String[] args) {

//Solicitud de peticion
        try {
            URL url = new URL("https://arcordiezb2c.myvtex.com/api/catalog_system/pub/facets/search/_all?map=b");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
//Verificacion de peticion
            int response = conn.getResponseCode();
            if (response != 200) {
                throw new RuntimeException("Error: " + response);
            } else {
//Apertura de scanner
                StringBuilder info = new StringBuilder();
                Scanner scan = new Scanner(url.openStream());

                while (scan.hasNext()) {
                    info.append(scan.nextLine());
                }
//Cierre de scanner
                scan.close();

//Conversion string a objeto
                JSONObject jsonObject = new JSONObject(info.toString());
//Impresion de array Departments
                JSONArray departments = jsonObject.getJSONArray("Departments");
                for (int i = 0; i < departments.length(); i++) {
                    departments.getJSONObject(i);
                    System.out.println(departments.getJSONObject(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}