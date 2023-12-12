package com.example.nutritionapp.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPAddressUtil {
    public static String getIPAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();

        return address.getHostAddress();
    }
}
