package com.example.nutritionapp.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateTransform {
    public static Date toDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            log.info("Input date string not valid format");
            return null;
        }
    }
}
