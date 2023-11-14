package aes.ecb;

import aes.utils.ImageUtils;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESImageEncryptionECBMode {

    // ECB Mode
    // Values that should be GLOBAL are: Key
    // We can use generateRandomBytes for encryption and decryption
    // Key length: 32
    private SecretKeySpec skeySpec;

    public byte[] getKey() {
        return skeySpec.getEncoded();
    }

    public void setKey(byte[] key) {
        skeySpec = new SecretKeySpec(key, "AES");
    }

    private static byte[] generateRandomBytes(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    private static byte[] encryptAES(byte[] input, byte[] key, boolean isCBCMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidCipherTextException {
        BlockCipher engine = new AESEngine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);
        cipher.init(true, new KeyParameter(key));

        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int bytesWritten = cipher.processBytes(input, 0, input.length, output, 0);
        bytesWritten += cipher.doFinal(output, bytesWritten);

        return output;
    }

    private static byte[] decryptAES(byte[] input, byte[] key, boolean isCBCMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidCipherTextException {
        BlockCipher engine = new AESEngine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);
        cipher.init(false, new KeyParameter(key));

        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int bytesWritten = cipher.processBytes(input, 0, input.length, output, 0);
        bytesWritten += cipher.doFinal(output, bytesWritten);

        return output;
    }

    public BufferedImage encryptBufferedImage(String filePath) throws IOException, InvalidCipherTextException, InvalidAlgorithmParameterException, NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        // Load original image
        BufferedImage imageOrig = ImageUtils.loadImage(filePath);

        // Convert original image data to bytes
        byte[] imageOrigBytes = ImageUtils.imageToBytes(imageOrig);

        byte[] ciphertext = encryptAES(imageOrigBytes, getKey(), false);

        return ImageUtils.bytesToImage(ciphertext, imageOrig.getWidth(), imageOrig.getHeight());
    }

    public BufferedImage decryptedBufferedImage(String filePath) throws IOException, InvalidCipherTextException, InvalidAlgorithmParameterException, NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        // Load original image
        BufferedImage imageOrig = ImageUtils.loadImage(filePath);

        // Convert original image data to bytes
        byte[] imageOrigBytes = ImageUtils.imageToBytes(imageOrig);

        byte[] ciphertext = encryptAES(imageOrigBytes, getKey(), false);

        // Decrypt
        byte[] decryptedImageBytes = decryptAES(ciphertext, getKey(), false);

        return ImageUtils.bytesToImage(decryptedImageBytes, imageOrig.getWidth(), imageOrig.getHeight());
    }
}
