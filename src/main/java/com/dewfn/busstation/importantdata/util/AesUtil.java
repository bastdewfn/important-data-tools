package com.dewfn.busstation.importantdata.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Security;

/**
 * @author Jack.Gong
 * @since 2018.10.17
 */
public class AesUtil {
    AesUtil() {
    }

    private static boolean initialized = false;
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING_ECB = "AES/ECB/PKCS7Padding";

    public static void initialize() {
        if (initialized) {
            return;
        }
        removeJceLimit();
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws GeneralSecurityException
     */
    public static String encryptDataEcb(String data, String key) throws GeneralSecurityException {
        initialize();

        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING_ECB);

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return Base64.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public static String decryptDataEcb(String base64Data, String key) throws GeneralSecurityException, UnsupportedEncodingException {
        initialize();
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING_ECB);

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(Base64.decode(base64Data)), StandardCharsets.UTF_8);
    }


    private static void removeJceLimit()
    {
        //去除JCE加密限制，只限于Java1.8
        try {
            Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, false);


        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace(System.err);
        }
    }


}
