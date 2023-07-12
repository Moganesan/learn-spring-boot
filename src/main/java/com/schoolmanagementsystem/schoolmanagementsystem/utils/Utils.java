package com.schoolmanagementsystem.schoolmanagementsystem.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public static String getTrackingId() {
        return UUID.randomUUID().toString().replace("-","");
    }

}
