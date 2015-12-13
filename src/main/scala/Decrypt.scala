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

		def evaluate(key: Array[Byte], ciphertext: Array[Byte]): Tuple2[Int, String] =
		{
			var result = Decrypt.decryptWithXor(key, ciphertext)
			var phrase = Convert.toString(result)
			var score = Metric.scorePhrase(phrase)
			(score, phrase)
		}

		def evaluateAgainstSingleCharacterKeys(ciphertext: Array[Byte]): Array[(Int, String)] =
		{
			var letters = ('a' to 'z').toSet ++ ('A' to 'Z').toSet ++ ('0' to '9').toSet
			var scoreMap: Array[(Int, String)] = Array()
			for (c <- letters.toIterator)
			{
				var key = Key.generateFromSingleCharacter(c, ciphertext.length)
				scoreMap = scoreMap:+evaluate(key, ciphertext)
			}

			scala.util.Sorting.stableSort(scoreMap, (e1: Tuple2[Int, String], e2: Tuple2[Int, String]) => e1._1 > e2._1)
			scoreMap
		}
	}
}
