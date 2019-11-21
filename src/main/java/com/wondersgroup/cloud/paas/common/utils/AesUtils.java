package com.wondersgroup.cloud.paas.common.utils;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public abstract class AesUtils {

    private static final Log LOGGER = LogFactory.getLog(AesUtils.class);

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final String STRIP_STRING = " \0";

    private static KeyGenerator keyGen;

    private static Cipher cipher;

    static {
        init();
    }

    private static void init() {
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("[AesUtils] init KeyGen error", e);
        }
        keyGen.init(128);
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (Exception e) {
            LOGGER.error("[AesUtils] init cihper error", e);
        }
    }

    public synchronized static String encrypt(String content) {
        return encrypt(content, CommonConstant.DEFAULT_ENCRYPT_KEY, CommonConstant.DEFAULT_ENCRYPT_IV_KEY);
    }

    public synchronized static String encrypt(String content, String keyString, String ivKeyString) {
        if (StringUtils.isBlank(content)) {
            return CommonUtils.NULL_STR;
        }
        String encryptText = null;
        try {
            Key key = new SecretKeySpec(keyString.getBytes(StandardCharsets.US_ASCII), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivKeyString.getBytes(StandardCharsets.US_ASCII)));
        } catch (InvalidKeyException e) {
            LOGGER.error("[AesUtils] invalid key " + keyString, e);
        } catch (InvalidAlgorithmParameterException e) {
            LOGGER.error("[AesUtils] invalid algorithm param " + ivKeyString, e);
        }
        try {
            byte[] encryptBytes = cipher.doFinal(extendKey(content.getBytes(StandardCharsets.UTF_8)));
            encryptText = parseByte2Hex(encryptBytes);
        } catch (Exception e) {
            LOGGER.error("[AesUtils] encrypt error ", e);
        }
        return encryptText;
    }

    public synchronized static String decrypt(String content) {
        return decrypt(content, CommonConstant.DEFAULT_ENCRYPT_KEY, CommonConstant.DEFAULT_ENCRYPT_IV_KEY);
    }

    public synchronized static String decrypt(String content, String keyString, String ivKeyString) {
        if (StringUtils.isBlank(content)) {
            return CommonUtils.NULL_STR;
        }
        String decryptText = null;
        try {
            Key key = new SecretKeySpec(keyString.getBytes(StandardCharsets.US_ASCII), "AES");
            IvParameterSpec ivKey = new IvParameterSpec(ivKeyString.getBytes(StandardCharsets.US_ASCII));
            cipher.init(Cipher.DECRYPT_MODE, key, ivKey);
        } catch (InvalidKeyException e) {
            LOGGER.error("[AesUtils]invalid key: " + keyString + " iv: " + ivKeyString, e);
        } catch (InvalidAlgorithmParameterException e) {
            LOGGER.error("[AesUtils] encoding unsupport " + keyString + " iv: " + ivKeyString, e);
        }
        try {
            byte[] encryptBytes = parseHex2Byte(content);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            decryptText = new String(decryptBytes, StandardCharsets.UTF_8);
            decryptText = StringUtils.strip(decryptText, STRIP_STRING);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("[AesUtils]invalid key " + keyString + " iv: " + ivKeyString + " content: " + content, e);
        } catch (BadPaddingException e) {
            LOGGER.error("[AesUtils]invalid key " + keyString + " iv: " + ivKeyString + " content: " + content, e);
        }
        return decryptText;
    }

    private static byte[] parseHex2Byte(String hexText) {
        if (StringUtils.isEmpty(hexText)) {
            return null;
        }
        byte[] result = new byte[hexText.length() / 2];
        for (int i = 0; i < hexText.length() / 2; i++) {
            int high = Integer.parseInt(hexText.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexText.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private static String parseByte2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toLowerCase());
        }
        return sb.toString();
    }

    private static byte[] extendKey(byte[] input) {
        int rest = input.length % 16;
        if (rest > 0) {
            byte[] result = new byte[input.length + (16 - rest)];
            System.arraycopy(input, 0, result, 0, input.length);
            return result;
        }
        return input;
    }

    public static void main(String[] args) {
//        System.out.println("hello encrypt:"+encrypt("hello"));
//
//        System.out.println(decrypt("0d98de67995fbbbfa9b0e3dc1ffd5003db84d220bfc56c8c169e9d4984065aeb"));

        String content = "9b16207b424c307a2804644a0c4b8666215763b8896ffeac4ae5b420ec42b7749dac69d72c3a300cc75fba6ba4eed215";
        String key = "1SU6cmL8qyO8qmYa";
        String iv = "pnL37M3D0xv5aVdl";
        String contentDe = decrypt(content, key, iv);
        System.out.println(contentDe);
    }

}
