package tech.fay.matasano
{
	import scala.collection.immutable.HashMap

	import java.security._
	import javax.crypto._
	import javax.crypto.spec._

	object Decrypt
	{
		def decryptWithXor(key: Array[Byte], ciphertext: Array[Byte]): Array[Byte] =
		{
			Operation.xor(ciphertext, key)
		}

		def decryptWithRepeatingKeyXor(key: Array[Byte], ciphertext: Array[Byte]): Array[Byte] =
		{
			var size = ciphertext.length
			var plaintext = new Array[Byte](size)
			for (i <- 0 to size - 1)
			{
				plaintext(i) = (ciphertext(i) ^ key(i % key.length)).asInstanceOf[Byte]
			}
			plaintext
		}

		def decryptWithAesEcbNoPadding(key: Array[Byte], ciphertext: Array[Byte]): Array[Byte] =
		{
			var keyspec = new SecretKeySpec(key, "AES")
			var cipher = Cipher.getInstance("AES/ECB/NoPadding")
			cipher.init(Cipher.DECRYPT_MODE, keyspec)
			cipher.doFinal(ciphertext)
		}
	}
}
