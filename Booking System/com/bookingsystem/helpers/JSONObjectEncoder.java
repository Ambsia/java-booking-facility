package com.bookingsystem.helpers;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.util.List;

/**
 * Created by Alex on 04/08/2015
 */
public class JSONObjectEncoder {

    private List<Equipment> equipmentList;


    public static void writeBookingAsJSON(Booking booking) {
        System.out.println(JSONValue.toJSONString(booking));
    }

    public static void writeEquipmentAsJSON(Equipment equipment)  {

        JSONObject object = new JSONObject();
        object.put("Equipment ID", equipment.getEquipmentID());
        object.put("Equipment Name", equipment.getEquipmentName());
        object.put("Equipment Description", equipment.getEquipmentDescription());
        object.put("Equipment Usage", equipment.getEquipmentUsage());

        File f = new File("equipment.JSON");

        if (f.exists()) {

        } else {

        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("equipment.JSON");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(outputStream,true);
        try {
            object.writeJSONString(printWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.close();
    }
}
