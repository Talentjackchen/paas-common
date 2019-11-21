package com.wondersgroup.cloud.paas.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by shimingjie on 2019/3/21.
 */
public class RsaUtils {

    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPK1mLJktFfB1PcKzyOD/eLe62Oac7acVv4VZt3XTgk50x10VdsLDWEUztk8PYc/xvhNyXa2gaCg4I58YmM5WStosEuOtX0O+2a3YzPHdLaVHaRj8N+q52wFdlyILCSDkzb6owNURds58zxY2VhRR8fOa7OeqjcWQQEyVVn+RRGfAgMBAAECgYEAw19g+oFMFrRmDTL7SPiQH9cYHoaPDPlOoZtywbL2E0Ej6zrhfcCo1XQdigsO8FdrgCeFu/Tv5fB6stf3jy3z0wdSTurPdwuyVFTeEXSu5TeqDLCunV3ctoeR8NfLNqVaVNnnKeXl14KeOfNtP1zwOxZxxXOROdMl4Bvo28LLWJkCQQD7EJNMedDWjH+ev/hk+qv45G/EhnZHstaxYcAFrXGVq3vLOiGCNIfesiPDRT6fU+HXITGtLGMiuJUtsnz5cdtzAkEA93r5fLDVyYZV/MRfsXTOUswSwLLE0/ch9zksgfh2r6aqkFc5ca0uGwL/x4VORboJGH5M1GqKrgVDmgd0dgu+JQJAbpcKizFNcHRK8t6Ux8YzYlsdcG/aQhLgxxnLv7R6x/bVfNuKTxRclRfa8PTbdMs6O8z/WMyojm7IJ2zr5+TjSQJBAKkb+EWzVLM7v6BU1AsgBSjg2GOjBQrO0f/sqrQ5g2it29l8MP2z2FCO8Rd2yTCuWi7Jh1iTYSZe5H3pYDSV+50CQB5Mzon/sD0JMpVyfR0Wp8tvsONekHtdF2g1XtNjr7jl5zJ76w0xvLK0KZ1SnPEyuhhsIHBYaIFc6Y3TYkvkAI4=";

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDytZiyZLRXwdT3Cs8jg/3i3utjmnO2nFb+FWbd104JOdMddFXbCw1hFM7ZPD2HP8b4Tcl2toGgoOCOfGJjOVkraLBLjrV9Dvtmt2Mzx3S2lR2kY/DfqudsBXZciCwkg5M2+qMDVEXbOfM8WNlYUUfHzmuznqo3FkEBMlVZ/kURnwIDAQAB";

    /**
     * 加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String encrypt(String str) throws Exception {
        return encrypt(str, publicKey);
    }

    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        return outStr;
    }

    /**
     * 解密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String decrypt(String str) throws Exception {
        return decrypt(str, privateKey);
    }

    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static void main(String[] args) throws Exception {
        String message = "df723820";
        String messageEn = encrypt(message);
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn);
        System.out.println("还原后的字符串为:" + messageDe);
    }

}
