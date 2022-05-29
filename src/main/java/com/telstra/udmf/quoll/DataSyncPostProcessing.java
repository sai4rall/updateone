
package com.telstra.udmf.quoll;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class DataSyncPostProcessing {
    private static int index=0;
    private static int MAX_ROWS=2500;

    private static String accessKey = "";
    private static String accessSecret = "";
    private static String region = "ap-southeast-2";
    static String sourceBucketName = "udmfmigrationdatasource";
    static String sourceBucketPath = "eai_objects/";
    static String outputBucketPath = "dataSyncOutput/";
    static String outputBucketName = "udmfmigrationdatasource";
    String sourceBucketNamepp;
    private static String tempPath = "/tmp/temp_quoll";

    //s3://udmfmigrationdatasource/eai_objects/
    public static void main(String[] args) throws IOException {
        buildJobFile("bsDF");
        buildJobFile("enmDF");
        buildJobFile("mbs_before_transform");
        buildJobFile("mbs");

        buildJobFile("site");
        buildJobFile("wirelessNetwork");

        buildJobFile("bsc");
        buildJobFile("rnc");

        buildJobFile("bts");
        buildJobRelationshipFile("bsc_to_bts");

        buildJobFile("nodeB");
        buildJobRelationshipFile("rnc_to_nodeB");

        buildJobFile("eNodeB");
        buildJobFile("gNB_DU");

        buildJobFile("gsmCell");
        buildJobRelationshipFile("bts_to_gsmCell");
        buildJobRelationshipFile("gsmCell_to_optimisationCluster");
        buildJobRelationshipFile("gsmCell_to_mobileServiceArea");

        buildJobFile("umtsCell");
        buildJobRelationshipFile("nodeB_to_umtsCell");
        buildJobRelationshipFile("umtsCell_to_optimisationCluster");
        buildJobRelationshipFile("umtsCell_to_mobileServiceArea");

        buildJobFile("lteCell");
        buildJobRelationshipFile("lteCell_To_wirelessNetwork");
        buildJobRelationshipFile("eNodeB_to_lteCell");
        buildJobRelationshipFile("lteCell_to_optimisationCluster");
        buildJobRelationshipFile("lteCell_to_mobileServiceArea");


        buildJobFile("nrCell");
        buildJobRelationshipFile("nrCells_to_gnbdu");
        buildJobRelationshipFile("nrCells_to_wirelessNetwork");
        buildJobRelationshipFile("nrCell_to_optimisationCluster");
        buildJobRelationshipFile("nrCell_to_mobileServiceArea");

        buildJobRelationshipFile("site_to_rfCell");

        buildJobFile("repeater");
        buildJobRelationshipFile("rfCell_to_repeater");
        buildJobRelationshipFile("repeater_to_optimisationCluster");
        buildJobRelationshipFile("repeater_to_mobileServiceArea");

        buildJobFile("optimisationCluster");
        buildJobFile("mobileServiceArea");

        buildJobRelationshipFile("nodeCode_to_baseStation");
        buildJobRelationshipFile("nodeCode_to_ngRanNode");

        buildJobFile("designer");

    }


    public static AmazonS3 s3Client() {
// AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonS3ClientBuilder.standard()
// .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTHEAST_2).build();
    }

    public static void formatDynamicAttributes(String fileName) throws JsonProcessingException {
// String path = "/tmp/test.json";
// String json = s3Client().getObjectAsString(sourceBucketName, "");
// readFile(tempPath);
        System.out.println("entered dynamic attributes " + fileName);
        StringBuilder json = readFile(tempPath + "_" + fileName + ".json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json.toString());
        ArrayNode resultData = mapper.createArrayNode();

        jsonNode.forEach(x -> {
            ObjectNode elementData = mapper.createObjectNode();
            ArrayNode dynamicAttributes = mapper.createArrayNode();

            x.fieldNames().forEachRemaining(i -> {
                if (i.startsWith("|")) {
                    String splitWord[] = i.split("\\|");
                    ObjectNode node = mapper.createObjectNode();
                    node.put("groupName", splitWord[1]);
                    node.put("attributeName", splitWord[2]);
                    node.put("attributeValue", x.get(i));
                    dynamicAttributes.add(node);

                } else {
                    elementData.put(i, x.get(i));
                }

                elementData.put("dynamicAttributes", dynamicAttributes);
            });
            resultData.add(elementData);
            try {
                resultData.add(getProjectData(elementData));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
//writeFile(opath, resultData.toString());
        splitfiles(resultData, MAX_ROWS, "objects",fileName);
        //TODO writw
        //    s3Client().putObject(outputBucketName, outputBucketPath + "objects/" + fileName + ".json", resultData.toString());
    }

    private static void splitfiles(ArrayNode resultData, int maxrows,String folder, String fileName) {
        index=0;
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
            index++;
            HashMap<String,JsonNode> relMap=new HashMap<>();

            ArrayNode chunkFullnode = mapper.createArrayNode();
            chunkFullnode.addAll(createUpdateChunk);
            for (int i = 0; i < createUpdateChunk.size(); i++) {
                JsonNode jnode = createUpdateChunk.get(i);
                ArrayNode v = (ArrayNode) jnode.get("$nodeCode");
                for (int j = 0; j < v.size(); j++) {
                    relMap.put(jnode.get("$refId").textValue(),lookupsData.get(v.get(j)));
                }

            }
            chunkFullnode.addAll(relMap.values());
//write FIle logic
//            s3Client().putObject(outputBucketName, outputBucketPath + "objects/" + fileName + ".json", chunkFullnode.toString());
            s3Client().putObject(outputBucketName, outputBucketPath + folder+"/" + fileName +"_"+index+ ".json", chunkFullnode.toString());
        });
    }

    private static String getProjectData(ObjectNode jsonNode) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode elementData = mapper.createObjectNode();
        elementData.put("$type", "");
        elementData.put("$action", "");
        elementData.put("$refId", jsonNode.get("$refId").toString() + " Quoll Migration");
        elementData.put("name", jsonNode.get("name").toString() + " Quoll Migration");
        elementData.put("projectTypeName", "Migration");
        ArrayNode arrayNode = elementData.putArray("$members");
        arrayNode.add(jsonNode.get("$refid").toString());
        return elementData.toString();
    }

    /**
     * Formats the Spark output JSON files into a single file ready for Datasync load.
     * The file is saved under the 'jobs' folder of the bucketOutputPath
     *
     * @param obj bucket (str): The name of the bucket
     *            obj (str): The name of the object to process. Assumed to be in a folder under the busketOutputPath.
     */
    public static void buildJobFile(String obj) throws IOException {
        System.out.println("Building Job File for:" + obj);
        File tempFile = new File(tempPath + "_" + obj + ".json");
        tempFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(tempFile);
        fout.write("[".getBytes(StandardCharsets.UTF_8));
        s3Client().listObjectsV2(sourceBucketName, sourceBucketPath + obj + "/").getObjectSummaries().stream()
                .filter(key -> key.getKey().contains("part-") && key.getKey().endsWith(".json"))
                .forEach(k -> {
                    S3Object oo = s3Client().getObject(sourceBucketName, k.getKey());
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(oo.getObjectContent()));

                        StringBuffer content = new StringBuffer("");
                        String s = null;
                        while ((s = reader.readLine()) != null) {
                            content = content.append(s.replace("}", "},\n"));
                        }
                        fout.write(content.toString().getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
        RandomAccessFile out = new RandomAccessFile(tempFile, "rw");
        if (tempFile.length() >= 2)
            out.seek(tempFile.length() - 2);
        out.write("]".getBytes(StandardCharsets.UTF_8));
        fout.close();
        out.close();
        formatDynamicAttributes(obj);


    }


    public static void buildJobRelationshipFile(String obj) throws IOException {
        System.out.println("buildJobRelationship Job File for:" + obj);

        File tempFile = new File(tempPath);

        tempFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(tempFile);
        fout.write("[".getBytes(StandardCharsets.UTF_8));
        s3Client().listObjectsV2(sourceBucketName, sourceBucketPath + obj + "_lookup" + "/").getObjectSummaries().stream().filter(key -> key.getKey().contains("part-") && key.getKey().endsWith(".json")).forEach(k -> {
            S3Object s3obj = s3Client().getObject(sourceBucketName, k.getKey());
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(s3obj.getObjectContent()));
                StringBuffer content = new StringBuffer("");
                String s = null;
                while ((s = reader.readLine()) != null) {
                    content = content.append(s.replace("}", "},\n"));
                }
// String content = new String(oo.getObjectContent().readAllBytes());

                fout.write(content.toString().getBytes(StandardCharsets.UTF_8));


// content = new String(s3obj.getObjectContent().readAllBytes());
// fout.write(content.replace("}\n", "},\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        s3Client().listObjectsV2(sourceBucketName, sourceBucketPath + obj + "/").getObjectSummaries().stream().filter(key -> key.getKey().contains("part-") && key.getKey().endsWith(".json")).forEach(k -> {
            S3Object s3obj = s3Client().getObject(sourceBucketName, k.getKey());

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(s3obj.getObjectContent()));
                StringBuffer content = new StringBuffer("");
                String s = null;
                while ((s = reader.readLine()) != null) {
                    content = content.append(s.replace("}", "},\n"));
                }
                fout.write(content.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        fout.write("]".getBytes(StandardCharsets.UTF_8));
        //s3Client().putObject(outputBucketName, outputBucketPath + "relationships/" + obj + ".json", tempFile);
        fout.close();
        StringBuilder rel_content=readFile(tempPath);
        ObjectMapper objectMapper=new ObjectMapper();
        ArrayNode relArrayNode=(ArrayNode)objectMapper.readTree(rel_content.toString());
        splitfiles(relArrayNode,MAX_ROWS,"relationships",obj);
        tempFile.delete();

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


}