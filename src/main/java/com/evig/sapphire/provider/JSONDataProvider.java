package com.evig.sapphire.provider;

import com.evig.sapphire.utils.GsonUtils;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class reads data from json file and provides <pre>{@code List<HashMap>}</pre>.
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class JSONDataProvider {

    /**
     * Method to provide data from JSON File
     *
     * @param fileName Requires description
     * @return Requires description
     * @throws IOException Requires description
     */
    public static List<HashMap> getDataFromJSON(String fileName) throws IOException {
        List<HashMap> dataList = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists())
            throw new FileNotFoundException(fileName + " is not exist");
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file));
            dataList.add(GsonUtils.convertToObject(bufferedReader, HashMap.class));
            bufferedReader.close();
            return dataList;
        } catch (JsonSyntaxException e) {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file));
            Type listType = new TypeToken<List<HashMap>>() {
            }.getType();
            dataList = GsonUtils.convertToObject(bufferedReader, listType);
            bufferedReader.close();
            return dataList;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
            return null;
        }
    }
}
