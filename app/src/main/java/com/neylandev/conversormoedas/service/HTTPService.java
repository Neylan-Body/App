package com.neylandev.conversormoedas.service;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.neylandev.conversormoedas.model.Moedas;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HTTPService extends AsyncTask<Void, Void, Moedas> {

    @Override
    protected Moedas doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        try {
            URL url = new URL("http://economia.awesomeapi.com.br/json/all/USD-BRL,EUR-BRL");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()){
                resposta.append(scanner.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = resposta.toString().replace("\"", "").replace("{", "}").replace("}", "");
        String []vetor = string.split(":|,");
//        resposta.toString().split()
//        Log.d("Dolar", vetor[16]);
//        Log.d("Euro", vetor[41]);
        //  resposta.toString().substring(132,136)
        //  resposta.toString().substring(339, 343)

        Moedas moedas = new Moedas();
        moedas.setDolar(Double.parseDouble(vetor[16]));
        moedas.setEuro(Double.parseDouble(vetor[41]));

//        Log.d("Antes", resposta.toString().substring(132,136)+" DOLAR, "+resposta.toString().substring(339, 343)+" EURO");
        return moedas;
    }
}
