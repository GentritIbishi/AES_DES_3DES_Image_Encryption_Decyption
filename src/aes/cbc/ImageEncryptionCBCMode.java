package aes.cbc;

import aes.utils.ImageUtils;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ImageEncryptionCBCMode {

    // CBC Mode
    // Values that should be GLOBAL are: Key and IV
    // We can use generateRandomBytes for encryption and decryption
    // Key length: 32
    // IV length: 16
    byte[] iv = generateRandomBytes(16);
    private SecretKeySpec skeySpec;

    private static byte[] generateRandomBytes(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    private static byte[] encryptAES(byte[] input, byte[] key, byte[] iv, boolean isCBCMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidCipherTextException {
        BlockCipher engine = new AESEngine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                isCBCMode ? new CBCBlockCipher(engine) : engine
        );
        cipher.init(true, new ParametersWithIV(new KeyParameter(key), iv));

        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int bytesWritten = cipher.processBytes(input, 0, input.length, output, 0);
        bytesWritten += cipher.doFinal(output, bytesWritten);

        return output;
    }

    private static byte[] decryptAES(byte[] input, byte[] key, byte[] iv, boolean isCBCMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidCipherTextException {
        BlockCipher engine = new AESEngine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                isCBCMode ? new CBCBlockCipher(engine) : engine
        );
        cipher.init(false, new ParametersWithIV(new KeyParameter(key), iv));

        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int bytesWritten = cipher.processBytes(input, 0, input.length, output, 0);
        bytesWritten += cipher.doFinal(output, bytesWritten);

        return output;
    }

    public byte[] getKey() {
        return skeySpec.getEncoded();
    }

    public void setKey(byte[] key) {
        skeySpec = new SecretKeySpec(key, "AES");
    }

    public BufferedImage encryptBufferedImage(String filePath) throws IOException, InvalidCipherTextException, InvalidAlgorithmParameterException, NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        // Load original image
        BufferedImage imageOrig = ImageUtils.loadImage(filePath);

        // Convert original image data to bytes
        byte[] imageOrigBytes = ImageUtils.imageToBytes(imageOrig);

        byte[] ciphertext = encryptAES(imageOrigBytes, getKey(), iv, true);

        // Convert ciphertext bytes to encrypted image data
        byte[] ivCiphertextVoid = ByteBuffer.allocate(iv.length + ciphertext.length).put(iv).put(ciphertext).array();

        return ImageUtils.bytesToImage(ivCiphertextVoid, imageOrig.getWidth() + 1, imageOrig.getHeight());
    }

    public BufferedImage decryptedBufferedImage(String filePath) throws IOException, InvalidCipherTextException, InvalidAlgorithmParameterException, NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        // Load original image
        BufferedImage imageOrig = ImageUtils.loadImage(filePath);

        // Convert original image data to bytes
        byte[] imageOrigBytes = ImageUtils.imageToBytes(imageOrig);

        byte[] ciphertext = encryptAES(imageOrigBytes, getKey(), iv, true);

        // Decrypt
        byte[] decryptedImageBytes = decryptAES(ciphertext, getKey(), iv, true);

        return ImageUtils.bytesToImage(decryptedImageBytes, imageOrig.getWidth(), imageOrig.getHeight());
    }
}
