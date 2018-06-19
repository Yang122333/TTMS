package com.zwy.ttms.model.Seats;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SeatParseJSON {

    public static List<Seats> toSeatList(String jsonData) {              //  查询所有用这个
        List<Seats> seatsList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String data = jsonObject.getString("seatList");
            JSONArray jsonArray = new JSONArray(data);


            Log.i("length = ", String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject seat_Object = jsonArray.getJSONObject(i);

                String unionId = seat_Object.getString("unionId");
                String studioId = seat_Object.getString("studioId");
                String row = seat_Object.getString("row");
                String col = seat_Object.getString("col");
                String status = seat_Object.getString("status");
                Seats seat = new Seats();
                seat.setUnionId(unionId);
                seat.setStudioId(studioId);
                seat.setRow(row);
                seat.setCol(col);
                seat.setStatus(status);
                seatsList.add(seat);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seatsList;
    }

    public static Seats toSeat(String jsonData) {                   //单条目查询用这个
        Seats seat = new Seats();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String seat_info = jsonObject.getString("seat");

            JSONObject seat_Object = new JSONObject(seat_info);
            String unionId = seat_Object.getString("unionId");
            String studioId = seat_Object.getString("studioId");
            String row = seat_Object.getString("row");
            String col = seat_Object.getString("col");
            String status = seat_Object.getString("status");

            seat.setUnionId(unionId);
            seat.setStudioId(studioId);
            seat.setRow(row);
            seat.setCol(col);
            seat.setStatus(status);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return seat;
    }

    public static String getStatus(String jsonData) {
        String status = null;
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            status = jsonObject.getString("result");

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return status;
    }
}
