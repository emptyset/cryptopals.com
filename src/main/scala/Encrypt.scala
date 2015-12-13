package tech.fay.matasano
{
	object Encrypt
	{
		def encryptWithRepeatingKeyXor(key: Array[Byte], plaintext: Array[Byte]): Array[Byte] =
		{
			var size = plaintext.length
			var ciphertext = new Array[Byte](size)
			for (i <- 0 to size - 1)
			{
				ciphertext(i) = (plaintext(i) ^ key(i % key.length)).asInstanceOf[Byte]
			}
			ciphertext
		}
	}
}
