package com.t.u.q;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Test {
    public static void main(String[] args) throws JsonProcessingException {
        String s="{\"$type\":\"ocw/gsmCell\",\"$refId\":\"WIFG111\",\"$action\":\"createOrUpdate\",\"name\":\"WIFG111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"cgi\":\"50501026029CD\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"NEW 07/10/02, Transfered from KBSA\",\"|telstraCellAttributes|billingName\":\"Windsor\",\"|telstraCellAttributes|roamingAgreement\":\"HTW\",\"|telstraCellAttributes|cellFunction\":\"Coverage\",\"|telstraCellAttributes|coverageClassification\":\"Unassigned\",\"|telstraCellAttributes|coverageStatement\":\"Coverage of the Windsor area\",\"|telstraCellAttributes|hasPriorityAssistCustomers\":false,\"|telstraCellAttributes|hasWirelessLocalLoopCustomers\":false,\"|telstraCellAttributes|wirelessServiceOwner\":\"TCW - SYDNEY NOR EAST & CBD\",\"|telstraCellAttributes|hasSpecialEvent\":false,\"|telstraCellAttributes|hasSignificantSpecialEvent\":false,\"|telstraCellAttributes|mobileSwitchingCentre\":\"NSW-ACT-MSCPOOL\",\"|telstraCellAttributes|quollIndex\":25596,\"|telstraCellAttributes|hasHighSeasonality\":false,\"|telstraGsmCellAttributes|broadcastCode\":622,\"|telstraGsmCellAttributes|plmn\":50501,\"|telstraGsmCellAttributes|evdoEnabled\":false,\"|telstraCellAttributes|baseStationName\":\"WILBERFORCE\",\"|telstraGsmCellAttributes|gsm338Coding\":\"D7B49B3C7FCB41\"}\n";
        System.out.println(s);
        System.out.println(s+"\n"+getProjectData(s));
    }


    private static ObjectNode getProjectData(String s) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();


      //  StringBuilder rdata=readFile("C:\\Users\\sai\\Downloads\\spark2\\updateone\\src\\main\\resources\\memberRefData.json");



        JsonNode jsonNode = mapper.readTree(s);
        ObjectNode elementData = mapper.createObjectNode();
        elementData.put("$type",jsonNode.get("$type").toString());
        elementData.put("$action",jsonNode.get("$action"));



        elementData.put("$refId",jsonNode.get("$refId").asText()+" Quoll Migration");







        String nm=jsonNode.get("name").asText();
        elementData.put("name",nm+" Quoll Migration");
        elementData.put("projectTypeName","Migration");
        ArrayNode arrayNode = elementData.putArray("$members");
        arrayNode.add(jsonNode.get("$type"));
//       elementData.put("$members",memberRefData.get(jsonNode.get("$refId").toString()).toString());
        return elementData;

    }
    //


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
}
