package vn.com.vetc.sencryptor;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Sencryptor {
    private StringEncryptor stringEncryptor;

    public static String DEFAULT_KEYFILE_NAME = "sencryptor.key";

    public static String SENCRYPTOR_PROPRERTY = "sencryptorkeyfile";

    private static Sencryptor sencryptorInst;

    public Sencryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    public static Sencryptor getInstance(){
        if (sencryptorInst == null) {
            String key = null;

            try {
                String keyfile = System.getProperty(SENCRYPTOR_PROPRERTY);

                if (keyfile != null && Files.exists(Paths.get(keyfile)))
                    key = Files.readAllLines(Paths.get(keyfile)).get(0).trim();
                else
                    key = Files.readAllLines(Paths.get(DEFAULT_KEYFILE_NAME)).get(0).trim();

                if (key != null)
                    sencryptorInst = build(key);
            } catch(Exception ex){
                sencryptorInst = new Sencryptor(null);
            }
        }

        return sencryptorInst;
    }

    private static Sencryptor build(String key) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(key);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        return new Sencryptor(encryptor);
    }

    public String decryptIfRequire(String encryptedString){
        if (stringEncryptor != null)
            return stringEncryptor.decrypt(encryptedString);

        return encryptedString;
    }

    public String encryptIfRequire(String data){
        if (stringEncryptor != null)
            return stringEncryptor.encrypt(data);

        return data;
    }

    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
