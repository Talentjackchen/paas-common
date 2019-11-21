package com.wondersgroup.cloud.paas.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenlong
 * IP工具类
 */
public class IpUtils {

    /**
     * 检查地址是否在网段内
     *
     * @param ip      地址
     * @param segment 网段
     * @return
     */
    public static boolean isInRange(String ip, String segment) {
        String[] ips = ip.split("\\.");
        int ipInt = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        int type = Integer.parseInt(segment.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        String segmentIp = segment.replaceAll("/.*", "");
        String[] segmentIps = segmentIp.split("\\.");
        int segmentIpInt = (Integer.parseInt(segmentIps[0]) << 24)
                | (Integer.parseInt(segmentIps[1]) << 16)
                | (Integer.parseInt(segmentIps[2]) << 8)
                | Integer.parseInt(segmentIps[3]);

        return (ipInt & mask) == (segmentIpInt & mask);

//        IPRange ipRange = new IPRange("10.0.0.1/29");
//        IPAddress ipAddress = new IPAddress("10.0.0.4");
//        System.out.println(ipRange.isIPAddressInRange(ipAddress));
    }

    /**
     * 检查IP合法性
     * @param ip
     * @return
     */
    public static boolean checkIp(String ip) {
        String pattern = "^(?:(?:1[0-9][0-9]\\.)|(?:2[0-4][0-9]\\.)|(?:25[0-5]\\.)|(?:[1-9][0-9]\\.)|(?:[0-9]\\.)){3}(?:(?:1[0-9][0-9])|(?:2[0-4][0-9])|(?:25[0-5])|(?:[1-9][0-9])|(?:[0-9]))$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(ip);
        return m.find();
    }

    /**
     * 检查网段合法性
     *
     * @param ip
     * @return
     */
    public static boolean checkSegment2(String ip) {
        String pattern = "^(?:(?:1[0-9][0-9]\\.)|(?:2[0-4][0-9]\\.)|(?:25[0-5]\\.)|(?:[1-9][0-9]\\.)|(?:[0-9]\\.)){3}(?:(?:1[0-9][0-9])|(?:2[0-4][0-9])|(?:25[0-5])|(?:[1-9][0-9])|(?:[0-9]))\\/[0-9]{1,2}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(ip);
        if (m.find()) {
            String[] values = ip.split("\\/");
            int mask = Integer.valueOf(values[1]);
            return mask > 0 && mask < 33;
        }
        return false;
    }

    /**
     * 检查网段合法性2
     *
     * @param ip
     * @return
     */
    public static boolean checkSegment(String ip) {
        String pattern = "^(?:(?:1[0-9][0-9]\\.)|(?:2[0-4][0-9]\\.)|(?:25[0-5]\\.)|(?:[1-9][0-9]\\.)|(?:[0-9]\\.)){3}(?:(?:1[0-9][0-9])|(?:2[0-4][0-9])|(?:25[0-5])|(?:[1-9][0-9])|(?:[0-9]))\\/[0-9]{1,2}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(ip);
        if (m.find()) {
            boolean flag = true;
            String[] values = ip.split("\\/");
            String[] numbers = values[0].split("\\.");
            int mask = Integer.valueOf(values[1]);
            if (mask >= 1 && mask <= 8) {
                flag = numbers[1].equals("0") && numbers[2].equals("0") && numbers[3].equals("0") && verifyDigit(numbers[0], String.valueOf(mask));
            } else if (mask >= 9 && mask <= 16) {
                flag = numbers[2].equals("0") && numbers[3].equals("0") && verifyDigit(numbers[1], String.valueOf(mask));
            } else if (mask >= 17 && mask <= 24) {
                flag = numbers[3].equals("0") && verifyDigit(numbers[2], String.valueOf(mask));
            } else {
                flag = verifyDigit(numbers[3], String.valueOf(mask));
            }
            return mask > 0 && mask < 33 && flag;
        }
        return false;
    }

    private static boolean verifyDigit(String digit, String mask) {
        boolean flag = false;
        Integer i = Integer.parseInt(mask) % 8;
        switch (i) {
            case 1:
                flag = "128".equals(digit);
                break;
            case 2:
                flag = "192".equals(digit);
                break;
            case 3:
                flag = "224".equals(digit);
                break;
            case 4:
                flag = "240".equals(digit);
                break;
            case 5:
                flag = "248".equals(digit);
                break;
            case 6:
                flag = "252".equals(digit);
                break;
            case 7:
                flag = "254".equals(digit);
                break;
            default:
                flag = "255".equals(digit);
                break;
        }
        return flag;
    }


    //获取本地IP地址
    public static String getHostIP() {
        String tempIP = "127.0.0.1";
        try {
            tempIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            return tempIP;
        }
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements()) {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements()) {
                    ip = addrs.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(tempIP)) {
                        return ip.getHostAddress();
                    }
                }
            }
            return tempIP;
        } catch (Exception e) {
            return tempIP;
        }
    }


    public static void main(String[] args) {
        System.out.println(isInRange("192.168.1.127", "192.168.1.64/66"));
        System.out.println(isInRange("192.168.1.2", "192.168.0.0/23"));
        System.out.println(isInRange("192.168.25.255", "192.168.25.0/24"));
        System.out.println(isInRange("192.168.0.0", "192.168.0.0/66"));
        System.out.println(isInRange("10.254.0.1", "10.0.0.1/31"));

        System.out.println(checkSegment2("32.128.0.128/24"));
    }
}
