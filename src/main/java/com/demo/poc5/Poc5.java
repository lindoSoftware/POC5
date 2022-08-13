package com.demo.poc5;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
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

//Creacion de lista a ordenar
                JSONArray jsonDepartments = jsonObject.getJSONArray("Departments");
                JSONArray sortedDepartments = new JSONArray();
                List<JSONObject> departmentsList = new ArrayList<>();

                for (int i = 0; i < jsonDepartments.length(); i++) {
                    departmentsList.add(jsonDepartments.getJSONObject(i));
                }

//Orden de lista creada por quantity
                Collections.sort(departmentsList, new Comparator<JSONObject>() {

                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        int valA = a.getInt("Quantity");
                        int valB = b.getInt("Quantity");

                        if (valA > valB) {
                            return 1;
                        }
                        if (valA < valB) {
                            return -1;
                        }
                        return 0;
                    }
                });

//Insercion en lista ordenada
                for (int i = 0; i < jsonDepartments.length(); i++) {
                    sortedDepartments.put(departmentsList.get(i));
                }
//Impresion de lista ordenada
                System.out.println("Lista Departments en orden ascendente por Quantity: " + sortedDepartments.toString());

//Creacion de lista a ordenar
                JSONArray jsonBrands = jsonObject.getJSONArray("Brands");
                JSONArray sortedBrands = new JSONArray();
                List<JSONObject> brandsList = new ArrayList<>();

                for (int i = 0; i < jsonBrands.length(); i++) {
                    brandsList.add(jsonBrands.getJSONObject(i));
                }

//Orden de lista creada por name
                Collections.sort(brandsList, new Comparator<JSONObject>() {

                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        String valA = new String();
                        String valB = new String();

                        try {
                            valA = (String) a.get("Name");
                            valB = (String) b.get("Name");
                        } catch (JSONException e) {

                        }

                        return -valA.compareTo(valB);
                    }
                });
//Insercion en lista ordenada
                for (int i = 0; i < jsonBrands.length(); i++) {
                    sortedBrands.put(brandsList.get(i));
                }
//Impresion de lista ordenada
                System.out.println("Lista Brands en orden descendente por Name: " + sortedBrands.toString());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
