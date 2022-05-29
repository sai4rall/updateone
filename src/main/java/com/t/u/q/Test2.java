package com.t.u.q;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test2 {
    private static Map<String, ArrayList<String>> userRefData;

    public static void main(String[] args) throws JsonProcessingException {
String s="[{\"$type\":\"tlm/nodeCode\",\"$refId\":\"STRP\",\"$action\":\"lookup\",\"name\":\"STRP\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"YAIL\",\"$action\":\"lookup\",\"name\":\"YAIL\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"BMUO\",\"$action\":\"lookup\",\"name\":\"BMUO\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"FANY\",\"$action\":\"lookup\",\"name\":\"FANY\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"CEQW\",\"$action\":\"lookup\",\"name\":\"CEQW\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"PICG\",\"$action\":\"lookup\",\"name\":\"PICG\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"HYUP\",\"$action\":\"lookup\",\"name\":\"HYUP\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"CZ2D\",\"$action\":\"lookup\",\"name\":\"CZ2D\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"DG3Q\",\"$action\":\"lookup\",\"name\":\"DG3Q\"},\n" +
        "{\"$type\":\"tlm/nodeCode\",\"$refId\":\"CCAV\",\"$action\":\"lookup\",\"name\":\"CCAV\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduSTRP19\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"STRP\"],\"name\":\"STRP19\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduSTRP15\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"STRP\"],\"name\":\"STRP15\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduYAIL19\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"YAIL\"],\"name\":\"YAIL19\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduBMUO19\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"BMUO\"],\"name\":\"BMUO19\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduBMUO15\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"BMUO\"],\"name\":\"BMUO15\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduFANY25\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"FANY\"],\"name\":\"FANY25\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduCEQW19\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"CEQW\"],\"name\":\"CEQW19\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduPICG15\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"PICG\"],\"name\":\"PICG15\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduHYUP19\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"HYUP\"],\"name\":\"HYUP19\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduCZ2D19\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"CZ2D\"],\"name\":\"CZ2D19\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduDG3Q15\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"DG3Q\"],\"name\":\"DG3Q15\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduDG3Q19\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"DG3Q\"],\"name\":\"DG3Q19\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduCCAV15\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"CCAV\"],\"name\":\"CCAV15\"},\n" +
        "{\"$type\":\"ocw/gnbdu\",\"$refId\":\"gnbduCCAV19\",\"$action\":\"createOrUpdate\",\"$nodeCode\":[\"CCAV\"],\"name\":\"CCAV19\"},\n" +
        "]";
    }


//    private static String getProjectData(String s) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        //  StringBuilder rdata=readFile("C:\\Users\\sai\\Downloads\\spark2\\updateone\\src\\main\\resources\\memberRefData.json");
//        JsonNode jsonNode = mapper.readTree(s);
//        ObjectNode elementData = mapper.createObjectNode();
//        elementData.put("$type", jsonNode.get("$type").toString());
//        elementData.put("$action", jsonNode.get("$action").toString());
//        elementData.put("$refId", jsonNode.get("$refId").toString() + " Quoll Migration");
//        elementData.put("name", jsonNode.get("name").toString() + " Quoll Migration");
//        elementData.put("projectTypeName", "Migration");
//        elementData.put("$members", memberRefData.get(jsonNode.get("$refId").toString()).toString());
//        return elementData.toString();
//
//    }

//    private static Map<String, ArrayList<String>> getUserDetails() throws JsonProcessingException {
//        StringBuffer content = new StringBuffer("");
//
//        s3Client().listObjectsV2(sourceBucketName, sourceBucketPath + "designer" + "/").getObjectSummaries().stream()
//                .filter(key -> key.getKey().contains("part-") && key.getKey().endsWith(".json"))
//                .forEach(k -> {
//                    S3Object oo = s3Client().getObject(sourceBucketName, k.getKey());
//                    try {
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(oo.getObjectContent()));
//
//                        String s = null;
//                        while ((s = reader.readLine()) != null) {
//                            content.append(s.replace("}", "},\n"));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode refJsonNode = mapper.readTree(content.toString());
//        Map<String, ArrayList<String>> memberRefData = new HashMap<>();
//        refJsonNode.forEach(jsonNode -> {
//            String refId = jsonNode.get("$refId").toString();
//            ArrayList<String> names = memberRefData.get(refId) == null ? new ArrayList<String>() : memberRefData.get(refId);
//            names.add(memberRefData.get("name").toString());
//            memberRefData.put(refId, names);
//        });
//        return memberRefData;
//
//    }
}
