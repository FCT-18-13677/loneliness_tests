package es.uji.giant.DialogFlowTests.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import es.uji.giant.DialogFlowTests.model.Questionnarie;
import es.uji.giant.DialogFlowTests.utils.CSV;
import es.uji.giant.DialogFlowTests.utils.Constants;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataAnalysis {

    public static void main(String[] args) {
        List<Questionnarie> questionnaries = getQuestionnariesFromREST();

        Set<String> cities = new HashSet<>();

        for (Questionnarie questionnarie : questionnaries) {
            if (!cities.contains(questionnarie.getCity())) {
                cities.add(questionnarie.getCity());
                System.out.println(questionnarie.getDiagnosis());
            }
        }

        System.out.println("Hay " + cities.size() + " ciudades no repetidas\n");


        // ELK to CSV
        generateCSV(questionnaries);
    }

    private static void generateCSV(List<Questionnarie> questionnaries) {
        List<String[]> result = new ArrayList<>();

        result.add(new String[]{"GÉNERO", "EDAD", "VIVE SOLO", "CIUDAD", "J1", "J2", "J3", "J4", "J5", "J6", "U1", "U2", "U3", "COMENTARIOS"});
        for (Questionnarie questionnarie : questionnaries) {
            String[] row = new String[14];
            row[0] = questionnarie.getSex();
            row[1] = String.valueOf(questionnarie.getAge());
            row[2] = questionnarie.isLivingAlone() ? "Sí" : "No";
            row[3] = questionnarie.getCity();
            for (int i = 0; i < 9; i++) {
                row[i + 4] = questionnarie.getAnswers().get(i);
                row[i + 4] = getNumericValueForAnswer(questionnarie.getAnswers().get(i), i);
            }
            //row[13] = String.valueOf(questionnarie.getJongScore());
            //row[14] = String.valueOf(questionnarie.getUclaScore());
            row[13] = questionnarie.getUserComments();
            result.add(row);
        }

        CSV csv = new CSV();
        csv.createCSV(result);
    }

    private static String getNumericValueForAnswer(String answer, int index) {
        String output = "";
        switch (Utils.stripAccents(answer)) {
            // Valores UCLA
            case "casi nunca":
                output = "1";
                break;
            case "algunas veces":
                output = "2";
                break;
            case "a menudo":
                output = "3";
                break;

            // Valores JONG
            case "mas o menos":
                output = "1";
                break;
            case "si":
                if (index == 0)
                    output = "1";
                else
                    output = "0";
                break;
            case "no":
                if (index == 5)
                    output = "1";
                else
                    output = "0";
                break;
            default:
                System.out.println(answer);
                output = "ERROR";
        }
        return output;
    }


    public static List<Questionnarie> getQuestionnariesFromREST() {
        try {
            URL urlForGetRequest = new URL("https://inti.init.uji.es:8463/getQuestionnaries");

            String readLine = null;

            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();

            conection.setRequestMethod("GET");

            //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here

            int responseCode = conection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(

                        new InputStreamReader(conection.getInputStream()));

                StringBuffer response = new StringBuffer();

                while ((readLine = in .readLine()) != null) {

                    response.append(readLine);

                } in .close();

                Type listType = new TypeToken<ArrayList<Questionnarie>>(){}.getType();
                List<Questionnarie> questionnaries = new Gson().fromJson(response.toString(), listType);

                return questionnaries;

            } else {

                System.out.println("GET NOT WORKED");
                System.out.println(responseCode);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
