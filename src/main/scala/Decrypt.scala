package tech.fay.matasano
{
	import scala.collection.immutable.HashMap

	object Decrypt
	{
		def generateSingleCharacterKey(c: Char, length: Int): Array[Byte] =
		{
			var array = new Array[Byte](length)
			for (i <- 0 to array.length - 1)
			{
				array(i) = c.asInstanceOf[Byte]
			}
			array
		}

		def toString(bytes: Array[Byte]): String =
		{
			var phrase = ""
			for (i <- 0 to bytes.length - 1)
			{
				var character = bytes(i).asInstanceOf[Char]
				phrase += character
			}
			phrase
		}

		def scorePhrase(phrase: String): Int =
		{
			var score = 0
			var map = HashMap[Char, Int](
				' ' -> 24, // according to http://www.data-compression.com/english.html about twice as frequent as 'e'
				'e' -> 12, // just using relative frequencies; works with any monotonic increasing value assignment
				't' -> 9,
				'a' -> 8,
				'o' -> 8,
				'i' -> 7,
				'n' -> 7,
				's' -> 6,
				'h' -> 6,
				'r' -> 6,
				'd' -> 4,
				'l' -> 4,
				'c' -> 3,
				'u' -> 3
			)

			phrase.map(c => score += map getOrElse(c.toLower, 0))
			score
		}

		def evaluate(key: Array[Byte], ciphertext: Array[Byte]): Tuple2[Int, String] =
		{
			var result = Operation.xor(ciphertext, key)
			var phrase = toString(result)
			var score = Decrypt.scorePhrase(phrase)
			(score, phrase)
		}

		def evaluateAgainstSingleCharacterKeys(ciphertext: Array[Byte]): Array[(Int, String)] =
		{
			var letters = ('a' to 'z').toSet ++ ('A' to 'Z').toSet ++ ('0' to '9').toSet
			var scoreMap: Array[(Int, String)] = Array()
			for (c <- letters.toIterator)
			{
				var key = generateSingleCharacterKey(c, ciphertext.length)
				scoreMap = scoreMap:+evaluate(key, ciphertext)
			}

			scala.util.Sorting.stableSort(scoreMap, (e1: Tuple2[Int, String], e2: Tuple2[Int, String]) => e1._1 > e2._1)
			scoreMap
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
