package com.telstra.udmf.quoll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TEST4 {
    int maxCount = 5;

    public static void main(String[] args) throws JsonProcessingException {
        String s = readFile("C:\\Users\\sai\\Downloads\\spark2\\updateone\\src\\main\\resources\\tempfile.json").toString();
        ObjectMapper onjectmapper = new ObjectMapper();
        JsonNode jsonNode = onjectmapper.readTree(s);
      ArrayNode aNode= (ArrayNode) jsonNode;
        System.out.println(aNode);
//        aNode.forEach(node->{
//            System.out.println(node);
//        });
        splitfiles(aNode,4);
    }

    public static StringBuilder readFile(String path) {
        try {
            StringBuilder sbf = new StringBuilder();
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                StringBuilder data = new StringBuilder(myReader.nextLine());
                sbf.append(data);
            }
            myReader.close();
            return sbf;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }


    private static void splitfiles(ArrayNode resultData, int maxrows) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode createupdate = mapper.createArrayNode();
        HashMap<String, JsonNode> lookupsData = new HashMap<>();
        ArrayList<ArrayNode> opList = new ArrayList<ArrayNode>();

//Spliting json based on Action
        for (JsonNode resultDatum : resultData) {
            if (resultDatum.get("$action").textValue().equalsIgnoreCase("createOrUpdate")) {
                createupdate.add(resultDatum);
            } else {
                lookupsData.put(resultDatum.get("$refId").textValue(), resultDatum);
            }
        }

        //spliting create update into multiple chunks
        ArrayNode node = mapper.createArrayNode();
        for (int i = 1; i <= createupdate.size(); i++) {

            if (i % maxrows == 0) {
                opList.add(node);
                node = mapper.createArrayNode();
            } else {
                node.add(createupdate.get(i));
            }
        }

        //merging  data and refid data
        opList.forEach(createUpdateChunk -> {
            ArrayNode chunkFullnode = mapper.createArrayNode();
            chunkFullnode.addAll(createUpdateChunk);
            for (int i = 0; i < createUpdateChunk.size(); i++) {
                JsonNode jnode=createUpdateChunk.get(i);
                ArrayNode v=(ArrayNode) jnode.get("$nodeCode");
                for(int j=0;j<v.size();j++){
                    chunkFullnode.add(lookupsData.get( v.get(j).textValue()));
                }
            }
//write FIle logic

        });
    }
}
