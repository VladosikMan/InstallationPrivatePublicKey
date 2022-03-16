package vladgad;

import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;


import org.apache.commons.codec.binary.Base64;

public class Cryptography {
    //класс для работы с крипктографией, здесь генерируются ключи,
    //существляется расшифровка данных
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * A class for wokr with vladgad.Cryptography
     *
     * @author Vedernikov Vladislav
     * @version 1
     */

    public static String generatePair(Task task) {
        // generate pair keys
        String certificate = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair pair = keyPairGenerator.generateKeyPair();
            certificate = generateX509Certificate(pair, task);
            String crt = generateCrt(certificate);
            writeCrtToFile(Path.PATH_CERT + task.getId() + ".crt", crt);
            String key = generatePrivateKeyPEM(new String(Base64.encodeBase64(pair.getPrivate().getEncoded())));
            savePrivateKey(key, Path.PATH_PRIVATE_KEYS + task.getId() + ".pem");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return certificate;
    }

    private static void writeCrtToFile(String path, String crt) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(path);
        byte[] strToBytes = crt.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }

    private static void savePrivateKey(String key, String path) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(path);
        byte[] strToBytes = key.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }

    public static PrivateKey PrivateKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
        byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }

    private static String generateX509Certificate(KeyPair keyPair, Task task) {
        // generate certificat
        //генериуем создатель сертификатов
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        end.add(Calendar.YEAR, 30);
        X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();
        X500Principal serverSubjectName = new X500Principal("CN=" + task.getId());
        certificateGenerator.setSerialNumber(BigInteger.valueOf(Math.abs(task.getId().hashCode())));
        certificateGenerator.setIssuerDN(new X509Principal("CN=" + task.getId()));
        certificateGenerator.setNotBefore(start.getTime());
        certificateGenerator.setNotAfter(end.getTime());
        certificateGenerator.setSubjectDN(new X509Principal("CN=" + task.getId()));
        certificateGenerator.setPublicKey(keyPair.getPublic());
        certificateGenerator.setSignatureAlgorithm("sha1WithRSA");
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
        final java.util.Base64.Encoder encoder = java.util.Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());
        byte[] rawCrtText = null;
        try {
            rawCrtText = x509Certificate.getEncoded();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return new String(encoder.encode(rawCrtText));
    }

    private static String generateCrt(String cert) {
        String crt = "-----BEGIN CERTIFICATE-----" + "\n" + cert + "\n-----END CERTIFICATE-----";
        return crt;
    }

    private static String generatePrivateKeyPEM(String key) {
        String crt = "-----BEGIN PRIVATE KEY-----" + "\n" + key + "\n-----END PRIVATE KEY-----";
        return crt;
    }

    public static String encrypt(PublicKey publicKey, String plaintext, String provider) {
        try {
            Cipher cipher = Cipher.getInstance(provider);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64String(cipher.doFinal(plaintext.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(PrivateKey privateKey, String plaintext, String provider) {
        try {
            Cipher cipher = Cipher.getInstance(provider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(Base64.decodeBase64(plaintext)), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static Certificate getCert(InputStream inputStream) {
        CertificateFactory cf = null;
        Certificate cert = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            cert = cf.generateCertificate(inputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return cert;
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String path = new String((Path.PATH_CERT + "k.crt"));
        File file = new File(path);
        InputStream targetStream = new FileInputStream(file);
        Certificate cert = getCert(targetStream);
        String enc = Cryptography.encrypt(cert.getPublicKey(), "Элементарно, Ватсон", "RSA/ECB/PKCS1Padding");
        System.out.println(enc);
    }

}
