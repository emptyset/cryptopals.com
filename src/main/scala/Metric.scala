package tech.fay.matasano
{
	import scala.collection.immutable.HashMap
	import scala.util.Sorting

	object Metric
	{
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

		def evaluate(key: Array[Byte], ciphertext: Array[Byte], method: (Array[Byte], Array[Byte]) => Array[Byte]): Tuple2[Int, String] =
		{
			var plaintext = method(key, ciphertext)
			var phrase = Convert.toString(plaintext)
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
				scoreMap = scoreMap :+ evaluate(key, ciphertext, Decrypt.decryptWithXor)
			}

			Sorting.stableSort(scoreMap, (e1: Tuple2[Int, String], e2: Tuple2[Int, String]) => e1._1 > e2._1)
			scoreMap
		}

		def scoreKeySize(ciphertext: Array[Byte], size: Int): Float =
		{
			var first = new Array[Byte](size)
			var second = new Array[Byte](size)
			var third = new Array[Byte](size)
			var fourth = new Array[Byte](size)

			// NOTE: 'until' keyword is exclusive 
			for (i <- 0 until size)
			{
				first(i) = ciphertext(i)
				second(i) = ciphertext(i + size)
				third(i) = ciphertext(i + (size * 2))
				fourth(i) = ciphertext(i + (size * 3))
			}

			var sum = 0
			sum += Operation.hammingDistance(first, second)
			sum += Operation.hammingDistance(second, third)
			sum += Operation.hammingDistance(third, fourth)
			var distance = sum.toFloat / 3
			distance / size
		}
	}
}
