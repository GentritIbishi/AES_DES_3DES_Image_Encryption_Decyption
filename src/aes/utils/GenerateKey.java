package aes.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class GenerateKey {

    private SecretKeySpec skeySpec;

    public GenerateKey() {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES"); //initiate with AES algorithm, can be changed to DES but the key size should be 56 bits
            kgen.init(128);
            /**initialize the encryption key with 128,
             key could be 128, 196 or 256 using AES encryption algorithm
             */
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            skeySpec = new SecretKeySpec(raw, "AES");

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public byte[] getKey() {
        return skeySpec.getEncoded();
    }

    public void setKey(byte[] key) {

        skeySpec = new SecretKeySpec(key, "AES");
    }

}

