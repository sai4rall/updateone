


package com.telstra.udmf.quoll;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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
import java.util.*;


public class DataSyncPostProcessing {

    private static int index=0;
    private static int MAX_ROWS=8;

    private static String accessKey = "AKIAR3XB4X6IWSQR3EWO";
    private static String accessSecret = "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";
    private static String region = "us-east-1";
    static String sourceBucketName = "saitestbkt";
    static String sourceBucketPath = "testrel/";
    static String outputBucketPath = "dataSyncOutput/";
    static String outputBucketName = "saitestbkt";
    String sourceBucketNamepp;
    private static String tempPath = "D:\\tmp\\temp_quoll";

//s3://udmfmigrationdatasource/eai_objects/
    public static void main(String[] args) throws IOException {
String rarr="[{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Windsor\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Windsor area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25596},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":622},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WILBERFORCE\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7B49B3C7FCB41\"}],\"$refId\":\"WIFG111\",\"$action\":\"createOrUpdate\",\"name\":\"WIFG111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"cgi\":\"50501026029CD\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"NEW 07/10/02, Transfered from KBSA\"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WIFG111 Quoll Migration\",\"name\":\"WIFG111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WIFG111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage along Wahroonga Braeside.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25134},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":359},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA BRAESIDE AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7305AFE7EBBCF61\"}],\"$refId\":\"WCJB000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJB000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0334 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJB000 Quoll Migration\",\"name\":\"WCJB000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJB000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage at Wahroonga Lucinda.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25137},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":403},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA LUCINDA AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"CCFA38ED268741417B19\"}],\"$refId\":\"WCJM000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJM000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"cgi\":\"5050101605CD0\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0614 completed Reparent proposed - HBSB to NHB2 on 17-DEC-07 (MCB0614) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJM000 Quoll Migration\",\"name\":\"WCJM000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJM000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wickham\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Wickham area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - HUNTER CENTRAL COAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25138},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":317},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WICKHAM L/YARD\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7F4788D0EB741\"}],\"$refId\":\"WCKD111\",\"$action\":\"createOrUpdate\",\"name\":\"WCKD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB01034 completed Reparent proposed - NEBS to DBSI on 30-JUL-08 (MCB01034) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCKD111 Quoll Migration\",\"name\":\"WCKD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCKD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Springvale\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Springvale area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - MELBOURNE SOUTH EAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"VIC-TAS-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":26140},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":562},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WESTALL\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"53B83CED3EDBC3EC32\"}],\"$refId\":\"WSAD111\",\"$action\":\"createOrUpdate\",\"name\":\"WSAD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"NO\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0797 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WSAD111 Quoll Migration\",\"name\":\"WSAD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WSAD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Windsor\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Windsor area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25596},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":622},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WILBERFORCE\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7B49B3C7FCB41\"}],\"$refId\":\"WIFG111\",\"$action\":\"createOrUpdate\",\"name\":\"WIFG111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"cgi\":\"50501026029CD\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"NEW 07/10/02, Transfered from KBSA\"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WIFG111 Quoll Migration\",\"name\":\"WIFG111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WIFG111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage along Wahroonga Braeside.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25134},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":359},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA BRAESIDE AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7305AFE7EBBCF61\"}],\"$refId\":\"WCJB000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJB000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0334 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJB000 Quoll Migration\",\"name\":\"WCJB000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJB000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage at Wahroonga Lucinda.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25137},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":403},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA LUCINDA AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"CCFA38ED268741417B19\"}],\"$refId\":\"WCJM000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJM000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"cgi\":\"5050101605CD0\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0614 completed Reparent proposed - HBSB to NHB2 on 17-DEC-07 (MCB0614) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJM000 Quoll Migration\",\"name\":\"WCJM000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJM000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wickham\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Wickham area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - HUNTER CENTRAL COAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25138},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":317},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WICKHAM L/YARD\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7F4788D0EB741\"}],\"$refId\":\"WCKD111\",\"$action\":\"createOrUpdate\",\"name\":\"WCKD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB01034 completed Reparent proposed - NEBS to DBSI on 30-JUL-08 (MCB01034) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCKD111 Quoll Migration\",\"name\":\"WCKD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCKD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Springvale\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Springvale area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - MELBOURNE SOUTH EAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"VIC-TAS-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":26140},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":562},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WESTALL\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"53B83CED3EDBC3EC32\"}],\"$refId\":\"WSAD111\",\"$action\":\"createOrUpdate\",\"name\":\"WSAD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"NO\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0797 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WSAD111 Quoll Migration\",\"name\":\"WSAD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WSAD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Windsor\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Windsor area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25596},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":622},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WILBERFORCE\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7B49B3C7FCB41\"}],\"$refId\":\"WIFG111\",\"$action\":\"createOrUpdate\",\"name\":\"WIFG111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"cgi\":\"50501026029CD\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"NEW 07/10/02, Transfered from KBSA\"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WIFG111 Quoll Migration\",\"name\":\"WIFG111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WIFG111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage along Wahroonga Braeside.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25134},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":359},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA BRAESIDE AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7305AFE7EBBCF61\"}],\"$refId\":\"WCJB000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJB000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0334 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJB000 Quoll Migration\",\"name\":\"WCJB000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJB000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage at Wahroonga Lucinda.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25137},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":403},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA LUCINDA AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"CCFA38ED268741417B19\"}],\"$refId\":\"WCJM000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJM000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"cgi\":\"5050101605CD0\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0614 completed Reparent proposed - HBSB to NHB2 on 17-DEC-07 (MCB0614) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJM000 Quoll Migration\",\"name\":\"WCJM000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJM000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wickham\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Wickham area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - HUNTER CENTRAL COAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25138},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":317},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WICKHAM L/YARD\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7F4788D0EB741\"}],\"$refId\":\"WCKD111\",\"$action\":\"createOrUpdate\",\"name\":\"WCKD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB01034 completed Reparent proposed - NEBS to DBSI on 30-JUL-08 (MCB01034) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCKD111 Quoll Migration\",\"name\":\"WCKD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCKD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Springvale\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Springvale area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - MELBOURNE SOUTH EAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"VIC-TAS-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":26140},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":562},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WESTALL\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"53B83CED3EDBC3EC32\"}],\"$refId\":\"WSAD111\",\"$action\":\"createOrUpdate\",\"name\":\"WSAD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"NO\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0797 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WSAD111 Quoll Migration\",\"name\":\"WSAD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WSAD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Windsor\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Windsor area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25596},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":622},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WILBERFORCE\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7B49B3C7FCB41\"}],\"$refId\":\"WIFG111\",\"$action\":\"createOrUpdate\",\"name\":\"WIFG111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"cgi\":\"50501026029CD\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"NEW 07/10/02, Transfered from KBSA\"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WIFG111 Quoll Migration\",\"name\":\"WIFG111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WIFG111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage along Wahroonga Braeside.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25134},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":359},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA BRAESIDE AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7305AFE7EBBCF61\"}],\"$refId\":\"WCJB000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJB000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0334 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJB000 Quoll Migration\",\"name\":\"WCJB000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJB000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage at Wahroonga Lucinda.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25137},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":403},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA LUCINDA AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"CCFA38ED268741417B19\"}],\"$refId\":\"WCJM000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJM000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"cgi\":\"5050101605CD0\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0614 completed Reparent proposed - HBSB to NHB2 on 17-DEC-07 (MCB0614) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJM000 Quoll Migration\",\"name\":\"WCJM000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJM000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wickham\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Wickham area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - HUNTER CENTRAL COAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25138},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":317},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WICKHAM L/YARD\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7F4788D0EB741\"}],\"$refId\":\"WCKD111\",\"$action\":\"createOrUpdate\",\"name\":\"WCKD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB01034 completed Reparent proposed - NEBS to DBSI on 30-JUL-08 (MCB01034) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCKD111 Quoll Migration\",\"name\":\"WCKD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCKD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Springvale\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Springvale area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - MELBOURNE SOUTH EAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"VIC-TAS-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":26140},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":562},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WESTALL\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"53B83CED3EDBC3EC32\"}],\"$refId\":\"WSAD111\",\"$action\":\"createOrUpdate\",\"name\":\"WSAD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"NO\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0797 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WSAD111 Quoll Migration\",\"name\":\"WSAD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WSAD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Windsor\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Windsor area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25596},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":622},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WILBERFORCE\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7B49B3C7FCB41\"}],\"$refId\":\"WIFG111\",\"$action\":\"createOrUpdate\",\"name\":\"WIFG111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"cgi\":\"50501026029CD\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"NEW 07/10/02, Transfered from KBSA\"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WIFG111 Quoll Migration\",\"name\":\"WIFG111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WIFG111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage along Wahroonga Braeside.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25134},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":359},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA BRAESIDE AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7305AFE7EBBCF61\"}],\"$refId\":\"WCJB000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJB000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0334 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJB000 Quoll Migration\",\"name\":\"WCJB000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJB000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wahroonga\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"roamingAgreement\",\"attributeValue\":\"HTW\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Tertiary\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Improve coverage at Wahroonga Lucinda.\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - SYDNEY NOR EAST & CBD\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25137},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":403},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WAHROONGA LUCINDA AVE UBTS\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"CCFA38ED268741417B19\"}],\"$refId\":\"WCJM000\",\"$action\":\"createOrUpdate\",\"name\":\"WCJM000\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MICRO\",\"sectorNumber\":0,\"cgi\":\"5050101605CD0\",\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0614 completed Reparent proposed - HBSB to NHB2 on 17-DEC-07 (MCB0614) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCJM000 Quoll Migration\",\"name\":\"WCJM000 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCJM000\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Wickham\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Coverage\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Wickham area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - HUNTER CENTRAL COAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"NSW-ACT-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":25138},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":317},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WICKHAM L/YARD\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"D7F4788D0EB741\"}],\"$refId\":\"WCKD111\",\"$action\":\"createOrUpdate\",\"name\":\"WCKD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"YES\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB01034 completed Reparent proposed - NEBS to DBSI on 30-JUL-08 (MCB01034) \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WCKD111 Quoll Migration\",\"name\":\"WCKD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WCKD111\"]},\n" +
        "{\"$type\":\"ocw/gsmCell\",\"dynamicAttributes\":[{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"billingName\",\"attributeValue\":\"Springvale\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"cellFunction\",\"attributeValue\":\"Capacity\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageClassification\",\"attributeValue\":\"Unassigned\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"coverageStatement\",\"attributeValue\":\"Coverage of the Springvale area\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasPriorityAssistCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasWirelessLocalLoopCustomers\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"wirelessServiceOwner\",\"attributeValue\":\"TCW - MELBOURNE SOUTH EAST\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasSignificantSpecialEvent\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"mobileSwitchingCentre\",\"attributeValue\":\"VIC-TAS-MSCPOOL\"},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"quollIndex\",\"attributeValue\":26140},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"hasHighSeasonality\",\"attributeValue\":false},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"broadcastCode\",\"attributeValue\":562},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"plmn\",\"attributeValue\":50501},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"evdoEnabled\",\"attributeValue\":false},{\"groupName\":\"telstraCellAttributes\",\"attributeName\":\"baseStationName\",\"attributeValue\":\"WESTALL\"},{\"groupName\":\"telstraGsmCellAttributes\",\"attributeName\":\"gsm338Coding\",\"attributeValue\":\"53B83CED3EDBC3EC32\"}],\"$refId\":\"WSAD111\",\"$action\":\"createOrUpdate\",\"name\":\"WSAD111\",\"status\":\"DECOMMISSIONED\",\"band\":\"900 MHz\",\"cellType\":\"MACRO\",\"sectorNumber\":1,\"egprsActivated\":\"NO\",\"gprsActivated\":\"YES\",\"comments\":\"Reparent MCB0797 completed \"},{\"$type\":\"ocp/project\",\"$action\":\"createOrUpdate\",\"$refId\":\"WSAD111 Quoll Migration\",\"name\":\"WSAD111 Quoll Migration\",\"projectTypeName\":\"Migration\",\"$members\":[\"WSAD111\"]}\n" +
        "]";
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode resultData =(ArrayNode)mapper.readTree(rarr);

        System.out.println(resultData);
        System.out.println(resultData);
        System.out.println(resultData);
        splitfiles(resultData,MAX_ROWS,"relationships","gsmCell");

     //   buildJobFile("gsmCell");
//        buildJobFile("rnc");
//        buildJobFile("gnbdu");
//        buildJobRelationshipFile("bsc_to_bts");
        /*buildJobFile("bsDF");
        buildJobFile("enmDF");
        buildJobFile("mbs_before_transform");
        buildJobFile("mbs");

        buildJobFile("site");
        buildJobFile("wirelessNetwork");

        buildJobFile("bsc");
        buildJobFile("rnc");

        buildJobFile("bts");*/
    /*    buildJobRelationshipFile("bsc_to_bts");

       // buildJobFile("nodeB");
        buildJobRelationshipFile("rnc_to_nodeB");

       *//* buildJobFile("eNodeB");
        buildJobFile("gNB_DU");*//*

        //buildJobFile("gsmCell");
        buildJobRelationshipFile("bts_to_gsmCell");
        buildJobRelationshipFile("gsmCell_to_optimisationCluster");
        buildJobRelationshipFile("gsmCell_to_mobileServiceArea");
        buildJobRelationshipFile("gsmCellProject_to_designer");

       // buildJobFile("umtsCell");
        buildJobRelationshipFile("nodeB_to_umtsCell");
        buildJobRelationshipFile("umtsCell_to_optimisationCluster");
        buildJobRelationshipFile("umtsCell_to_mobileServiceArea");
        buildJobRelationshipFile("umtsCellProject_to_designer");

       // buildJobFile("lteCell");
        buildJobRelationshipFile("lteCell_To_wirelessNetwork");
        buildJobRelationshipFile("eNodeB_to_lteCell");
        buildJobRelationshipFile("lteCell_to_optimisationCluster");
        buildJobRelationshipFile("lteCell_to_mobileServiceArea");
        buildJobRelationshipFile("lteCellProject_to_designer");


       // buildJobFile("nrCell");
        buildJobRelationshipFile("nrCells_to_gnbdu");
        buildJobRelationshipFile("nrCells_to_wirelessNetwork");
        buildJobRelationshipFile("nrCell_to_optimisationCluster");
        buildJobRelationshipFile("nrCell_to_mobileServiceArea");
        buildJobRelationshipFile("nrCellProject_to_designer");

        buildJobRelationshipFile("site_to_rfCell");

       // buildJobFile("repeater");
        buildJobRelationshipFile("rfCell_to_repeater");
        buildJobRelationshipFile("repeater_to_optimisationCluster");
        buildJobRelationshipFile("repeater_to_mobileServiceArea");
        buildJobRelationshipFile("repeaterProject_to_designer");

       // buildJobFile("optimisationCluster");
      //  buildJobFile("mobileServiceArea");

        buildJobRelationshipFile("nodeCode_to_baseStation");
        buildJobRelationshipFile("nodeCode_to_ngRanNode");*/

      //  buildJobFile("designer");

    }

    public static AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonS3ClientBuilder.standard()
 .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1).build();
    }

    public static void formatDynamicAttributes(String fileName) throws JsonProcessingException {
//        String path = "/tmp/test.json";
//        String json = s3Client().getObjectAsString(sourceBucketName, "");
//        readFile(tempPath);
        System.out.println("entered dynamic attributes "+ fileName);
        StringBuilder json = readFile(tempPath+"_"+fileName+".json");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(json.toString());
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
                if(fileName.equalsIgnoreCase("gsmCell") || fileName.equalsIgnoreCase("lteCell")
                || fileName.equalsIgnoreCase("nrCell") || fileName.equalsIgnoreCase("umtsCell")
                || fileName.equalsIgnoreCase("repeater"))
                        resultData.add(getProjectData(elementData));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        splitfiles(resultData, MAX_ROWS, "objects",fileName);
        s3Client().putObject(outputBucketName, outputBucketPath + "objects/"+ fileName + ".json", resultData.toString());
    }


    /**
     * Formats the Spark output JSON files into a single file ready for Datasync load.
     * The file is saved under the 'jobs' folder of the bucketOutputPath
     *
     * @param obj bucket (str):  The name of the bucket
     *            obj (str):  The name of the object to process.  Assumed to be in a folder under the busketOutputPath.
     */
    public static void buildJobFile(String obj) throws IOException {
        System.out.println("Building Job File for:" + obj);
        File tempFile = new File(tempPath+"_"+obj+".json");
        tempFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(tempFile);
        fout.write("[".getBytes(StandardCharsets.UTF_8));
        s3Client().listObjectsV2(sourceBucketName, sourceBucketPath+obj+"/" ).getObjectSummaries().stream().filter(key->key.getKey().contains("part-")&&key.getKey().endsWith(".json")).forEach(k -> {
            S3Object oo = s3Client().getObject(sourceBucketName, k.getKey());
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(oo.getObjectContent()));

                StringBuffer content=new StringBuffer("");
                String s=null;
                while ((s=reader.readLine())!=null)
                {
                    content=content.append(s.replace("}","},\n"));
                }
                fout.write(content.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        RandomAccessFile out = new RandomAccessFile(tempFile, "rw");
        if(tempFile.length() >=2)
            out.seek(tempFile.length() - 2);
        out.write("]".getBytes(StandardCharsets.UTF_8));
        fout.close();
        out.close();
        formatDynamicAttributes(obj);
    }

    public static void  buildJobRelationshipFile(String obj) throws IOException {
        System.out.println("buildJobRelationship Job File for:" + obj);

        File tempFile = new File(tempPath);

        tempFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(tempFile);
        fout.write("[".getBytes(StandardCharsets.UTF_8));
        s3Client().listObjectsV2(sourceBucketName, sourceBucketPath+obj + "_lookup" + "/").getObjectSummaries().stream().filter(key->key.getKey().contains("part-")&&key.getKey().endsWith(".json")).forEach(k -> {
            S3Object s3obj = s3Client().getObject(sourceBucketName, k.getKey());
            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(s3obj.getObjectContent()));
                StringBuffer content=new StringBuffer("");
                String s=null;
                while ((s=reader.readLine())!=null){
                    content=content.append(s.replace("}","},\n"));
                }
                // String content = new String(oo.getObjectContent().readAllBytes());
                fout.write(content.toString().getBytes(StandardCharsets.UTF_8));


               // content = new String(s3obj.getObjectContent().readAllBytes());
//                fout.write(content.replace("}\n", "},\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        s3Client().listObjectsV2(sourceBucketName, sourceBucketPath+obj + "/").getObjectSummaries().stream().filter(key->key.getKey().contains("part-")&&key.getKey().endsWith(".json")).forEach(k -> {
            S3Object s3obj = s3Client().getObject(sourceBucketName, k.getKey());
//            String content = null;
            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(s3obj.getObjectContent()));
                StringBuffer content=new StringBuffer("");
                String s=null;
                while ((s=reader.readLine())!=null){
                    content=content.append(s.replace("}","},\n"));
                }
                fout.write(content.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        RandomAccessFile out = new RandomAccessFile(tempFile, "rw");
        if(tempFile.length() >=2)
            out.seek(tempFile.length() - 2);
        out.write("]".getBytes(StandardCharsets.UTF_8));
        out.close();
       // fout.write("]".getBytes(StandardCharsets.UTF_8));
        s3Client().putObject(outputBucketName, outputBucketPath + "relationships/" + obj + ".json", tempFile);
        fout.close();
        StringBuilder rel_content=readFile(tempPath);
        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println("=====>"+rel_content.toString());
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

    private static String getRefDataElement(JsonNode jnode) {
        Iterator<String> fields= jnode.fieldNames();
        List<String> mlist=Arrays.asList("$type","$action","$refId");
        String refElement="";
        while (fields.hasNext()){
            String field=fields.next();
            if(field.startsWith("$")&&!mlist.contains(field)){
                refElement=field;
            }
        }
        return refElement;
    }

    private static ObjectNode getProjectData(ObjectNode jsonNode) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode elementData = mapper.createObjectNode();
        elementData.put("$type", "ocp/project");
        elementData.put("$action", "createOrUpdate");
        elementData.put("$refId", jsonNode.get("$refId").asText() + " Quoll Migration");
        elementData.put("name", jsonNode.get("name").asText() + " Quoll Migration");
        elementData.put("projectTypeName", "Migration");
        ArrayNode arrayNode = elementData.putArray("$members");
        arrayNode.add(jsonNode.get("$refId"));
        return elementData;
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
                //ArrayNode v = (ArrayNode) jnode.get("$nodeCode");
                String refdataelement = getRefDataElement(jnode);
                ArrayNode v = (ArrayNode) jnode.get(refdataelement);
                //System.out.println("jsnode -----> $refId === "+jnode);
                if (v != null) {
                    for (int j = 0; j < v.size(); j++) {
                        // System.out.println("jsnode -----> $refId === "+jnode);
                        if (jnode.get("$refId") != null) {
                            relMap.put(jnode.get("$refId").textValue(), lookupsData.get(v.get(j).textValue()));
                        }
                    }
                }

            }
            if(relMap!=null){
                chunkFullnode.addAll(relMap.values());

            }
//write FIle logic
//            s3Client().putObject(outputBucketName, outputBucketPath + "objects/" + fileName + ".json", chunkFullnode.toString());
            s3Client().putObject(outputBucketName, outputBucketPath + folder+"/" + fileName +"_"+index+ ".json", chunkFullnode.toString());
        });
    }
}

