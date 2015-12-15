package tech.fay.matasano

class S01C06 extends UnitSpec {

	import scala.collection.mutable.ArrayBuffer
	import scala.util.Sorting
	import org.apache.commons.codec.binary.{ Base64 => ApacheBase64 }

	"The Hamming distance between the values" should "equal 37" in 
	{
		var a = "this is a test"
		var b = "wokka wokka!!!"

		var aBytes = Convert.fromString(a)
		var bBytes = Convert.fromString(b)

		var distance = Operation.hammingDistance(aBytes, bBytes)

		assert(distance == 37)
	}

	"The 4 smallest key sizes with the smallest normalized edit distance" should "equal 2, 3, 5, 7" in
	{
		var ciphertext = Io.readBase64Resource("/6-ciphertext.txt")

		var scoreBuffer = new ArrayBuffer[Tuple2[Int, Float]]()
		for (keySize <- 2 until 40)
		{
			var score = Metric.scoreKeySize(ciphertext, keySize)
			scoreBuffer.append(new Tuple2(keySize, score))
		}
		var scores = scoreBuffer.toArray
		Sorting.stableSort(scores, (a: Tuple2[Int, Float], b: Tuple2[Int, Float]) => a._2 <= b._2)
		//scores.foreach(score => println(score._1 + "\t" + score._2))

		// TODO: how to get 29 as the keySize value with lowest normalized edit distance?
		// TODO: try averaging among all blocks of keySize pairs in sequence?
		assert(scores(0)._1 == 2)
		assert(scores(1)._1 == 3)
		assert(scores(2)._1 == 29)
		assert(scores(3)._1 == 5)
	}

	"Each break of original ciphertext into blocks" should "result in an Array of blocks of size keySize" in
	{
		var ciphertext = Io.readBase64Resource("/6-ciphertext.txt")
		var keySizes = Array[Int](2, 3, 29, 5)
		for (keySize <- keySizes.toIterator)
		{
			val blocks = Operation.toBlocks(ciphertext, keySize)
			assert(blocks.length == scala.math.ceil(ciphertext.length.toDouble / keySize).toInt)
			assert(blocks(0).length == keySize)
		}
	}

	"Solving transposed blocks" should "produce single-character XOR keys" in
	{
		var ciphertext = Io.readBase64Resource("/6-ciphertext.txt")
		var keySizes = Array[Int](29)
		keySizes.foreach(keySize => 
		{
			val blocks = Operation.toBlocks(ciphertext, keySize)
			val transposed = Operation.transposeBlocks(blocks)

			val decodedKey = new StringBuilder() 
			val letters = (' ' to '~').toSet 
			for (i <- 0 until keySize)
			{
				var character = ' '
				var currentScore = 0
				val singleKeyCiphertext = transposed(i)
				for (c <- letters.toIterator)
				{
					val key = Key.generateFromSingleCharacter(c, singleKeyCiphertext.length)
					var plaintext = Decrypt.decryptWithXor(key, singleKeyCiphertext)
					var phrase = Convert.toString(plaintext)
					var score = Metric.scorePhrase(phrase)
					
					if (score >= currentScore) 
					{
						character = c
						currentScore = score
					}
				}
				decodedKey += character
			}

			assert(decodedKey.toString == "Terminator X: Bring the noise")
			// lyrics to "Play That Funky Music" - Vanilla Ice
			//println(Convert.toString(Decrypt.decryptWithRepeatingKeyXor(decodedKey.toString.getBytes, ciphertext)))
		})
	}
}
