
import kotlin.text.*
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Logger

fun main() {

    Application().encryptDecryptTest()

}

class Application {
    private val logger = Logger.getLogger(Application::class.toString())
    val DELIMITER = "-"
    val PASSWORD = "SENHA456"
    var PASS = ("1")
    var VERSION = "7.04"
    var CURRENT_TIMESTAMP = System.currentTimeMillis()
    var SECRET_KEY = "SECRETKEY"
    var SALT = "Konhvft45hFth"


    fun encryptDecryptTest() {
        val originalString = PASS + DELIMITER + VERSION + DELIMITER + PASSWORD + DELIMITER + CURRENT_TIMESTAMP

        val encryptedString = encrypt(originalString)
        val decryptedString = decrypt(encryptedString)

        System.out.println(originalString)
        System.out.println(encryptedString)
        System.out.println(decryptedString)
    }


    fun encrypt (strToEncrypt: String) : String  {
        try {
            val iv = byteArrayOf(0, 0, 1, 1, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0)
            val ivspec = IvParameterSpec(iv)

            val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val spec: KeySpec  = PBEKeySpec(SECRET_KEY.toCharArray(), SALT.toByteArray(), 65536, 256)
            val tmp: SecretKey = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.getEncoded(), "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder()
                .encodeToString(cipher.doFinal(strToEncrypt.toByteArray(StandardCharsets.UTF_8)));
        } catch (e: Exception) {
            logger.info("Error while encrypting: " + e.toString());
        }
        return encrypt(strToEncrypt)
    }

    fun decrypt (key:String) : String {

        try{
            val iv = byteArrayOf(0, 0, 1, 1, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0)
            val ivspec = IvParameterSpec(iv)
            val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val spec: KeySpec = PBEKeySpec(SECRET_KEY.toCharArray(), SALT.toByteArray(), 65536, 256)
            val tmp: SecretKey = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.getEncoded(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")

            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec)
            return String(cipher.doFinal(Base64.getDecoder().decode(key)))

        } catch (e: Exception) {
            logger.info(" Decrypting error: " + e.toString())
        }
        return decrypt(key)
    }

}


