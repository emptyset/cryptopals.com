package tech.fay.matasano
{
	import scala.collection.immutable.HashMap

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
	}
}
