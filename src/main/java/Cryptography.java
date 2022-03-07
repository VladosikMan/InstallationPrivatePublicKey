import com.sun.jarsigner.ContentSigner;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class Cryptography {
    //класс для работы с крипктографией, здесь генерируются ключи,
    //существляется расшифровка данных
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * A class for wokr with Cryptography
     *
     * @author Vedernikov Vladislav
     * @version 1
     */

    private static void generatePair(String alias) {
        // generate pair keys
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair pair = keyPairGenerator.generateKeyPair();

            String enc = encrypt(pair.getPublic(), "Chmonua");
            System.out.println(enc);

            String dec = decrypt(pair.getPrivate(), enc);

            System.out.println(dec);
//            System.out.println("Private key - " + pair.getPrivate());
//            System.out.println("Public key - " + pair.getPublic());
//            System.out.println(generateCrt(generateX509Certificate(pair)));
//            String crt = generateCrt(generateX509Certificate(pair));
//            FileOutputStream outputStream = new FileOutputStream("crt.crt");
//            byte[] strToBytes = crt.getBytes();
//            outputStream.write(strToBytes);
//            outputStream.close();

            //generateX509Certificate(pair);
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }

    }


    private static String generateX509Certificate(KeyPair keyPair) {
        // generate certificat
        //генериуем создатель сертификатов

        X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();
        X500Principal serverSubjectName = new X500Principal("CN=Name");
        certificateGenerator.setSerialNumber(new BigInteger("123456789"));
// X509Certificate caCert=null;
        certificateGenerator.setIssuerDN(new X509Principal("CN=localhost"));
        certificateGenerator.setNotBefore(new Date());
        certificateGenerator.setNotAfter(new Date());
        certificateGenerator.setSubjectDN(new X509Principal("CN=localhost"));
        certificateGenerator.setPublicKey(keyPair.getPublic());
        certificateGenerator.setSignatureAlgorithm("MD5WithRSA");
        try {
            certificateGenerator.addExtension(X509Extensions.SubjectKeyIdentifier, false,
                    new SubjectKeyIdentifierStructure(keyPair.getPublic()));
        } catch (CertificateParsingException e) {
            e.printStackTrace();
        }
        X509Certificate x509Certificate = null;
        try {
            x509Certificate = certificateGenerator.generate(keyPair.getPrivate());
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        //System.out.println( Base64.encodeToString(x509Certificate.getEncoded());
        final Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());
        byte[] rawCrtText = null;
        try {
            rawCrtText = x509Certificate.getEncoded();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        String encodedCertText = new String(encoder.encode(rawCrtText));
        System.out.println(encodedCertText);
        return encodedCertText;
    }


    private static String generateCrt(String cert) {
        String crt = "-----BEGIN CERTIFICATE-----" + "\n" + cert + "\n-----END CERTIFICATE-----";
        return crt;
    }

    public static String encrypt(PublicKey publicKey, String plaintext) {
        try {
            // отправить серверу, где тот его зашифрует
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String decrypt(PrivateKey privateKey, String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(plaintext)), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Provider[] list = Security.getProviders();
        for (Provider x : list) {
            System.out.println(x.getInfo());
        }
        //System.out.println(Security.getProviders().toString());
        System.out.println("Alkl");
        generatePair("hwr");



    }
}
