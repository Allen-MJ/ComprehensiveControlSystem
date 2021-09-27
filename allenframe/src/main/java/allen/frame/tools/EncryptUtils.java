package allen.frame.tools;

import android.annotation.SuppressLint;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * escape编码,解码，MD5加密
 * @author maojing
 *
 */
public class EncryptUtils {
	
    private final static String[] digits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	/**
	 * MD5加密
	 * @param data
	 * @return
	 */
	public static String MD5Encoder(String data){
		if(StringUtils.empty(data)){
			return "";
		}
		String resultString = "";
        try {
            resultString = new String(data);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(data.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
	} 
	
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return digits[iD1] + digits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    
    /**
     * escape解码
     * @param param
     * @return
     */
 	public static String getObjectEscape(String param) {
 		try {
 			return unescape(param);
 		} catch (Exception e) {
 			return param;
 		}
 	}
 	
 	/**
 	 * unescape解码
 	 * @param object JSONObject
 	 * @param param key
 	 * @return
 	 */
 	public static String getJsonObjectEscape(JSONObject object, String param){
		return unescape(object.optString(param));
 	}
 	
 	/**
	 *  escape加密
	 * @param src
	 * @return
	 */
    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }
 
    /**
     *  unescape解密
     * @param src
     * @return
     */
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
    private static byte[] keys = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90,
		(byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	// 解密数据 的 实际处理
	private static String decrypt(String message, String key) throws Exception {
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(keys);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}
	
	// 加密算法 的 实际处理
	@SuppressLint("TrulyRandom")
	private static byte[] encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(keys);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(message.getBytes("UTF-8"));
	}
	
	// 字符串数据转化 为数组
	private static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}
	
	// 数组 转化为 字符串
	private static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}
	
	// 可以直接应用的 加密算法
	public static String encryptString(String message, String key) {
		try {
			byte[] str3 = encrypt(message, key);
			String strstring = new String(Base64.encode(str3, Base64.DEFAULT),
					"UTF-8");
			return strstring.trim();
		} catch (Exception e) {
			return message;
		}
	}
	
	// 可以直接调用的 解密算法
	@SuppressLint("DefaultLocale")
	public static String decryptString(String message, String key) {
		try {
			byte[] content = Base64.decode(message.getBytes("utf-8"),
					Base64.DEFAULT);
			String strsString = decrypt(toHexString(content).toUpperCase(), key);
			return strsString.trim();
		} catch (Exception e) {
			return message;
		}
	}

	public static String urlEncode(String src){
		try {
			return URLEncoder.encode(src,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return src;
		}
	}
	public static String urlDecode(String src){
		try {
			return URLDecoder.decode(src,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return src;
		}
	}
}
