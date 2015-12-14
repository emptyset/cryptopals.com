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

		assert(scores(0)._1 == 2)
		assert(scores(1)._1 == 3)
		assert(scores(2)._1 == 5)
		assert(scores(3)._1 == 7)
	}

	"Each break of original ciphertext into blocks" should "result in an Array of blocks of size keySize" in
	{
		var ciphertext = Io.readBase64Resource("/6-ciphertext.txt")
		println("ciphertext.length = " + ciphertext.length)
		var keySizes = Array[Int](2, 3, 5, 7)
		for (keySize <- keySizes.toIterator)
		{
			println("keySize = " + keySize)
			val blocks = Operation.toBlocks(ciphertext, keySize)
			println("blocks.length = " + blocks.length)
			//assert(blocks.length == scala.math.ceil(ciphertext.length.toDouble / keySize).toInt)
			assert(blocks(0).length == keySize)
		}
	}

	"Solving transposed blocks" should "produce single-character XOR keys" in
	{
		var ciphertext = Io.readBase64Resource("/6-ciphertext.txt")
		var keySizes = Array[Int](2) // , 3, 5, 7)
		keySizes.foreach(keySize => 
		{
			//println("ciphertext.length = " + ciphertext.length)
			val blocks = Operation.toBlocks(ciphertext, keySize)
			//println("blocks.length = " + blocks.length)
			val transposed = Operation.transposeBlocks(blocks)
			//println("transposed.length = " + transposed.length)

			val letters = ('a' to 'z').toSet ++ ('A' to 'Z').toSet ++ ('0' to '9').toSet
			for (i <- 0 until keySize)
			{
				//println("transposed(i).length = " + transposed(i).length)
				val singleKeyCiphertext = transposed(i)
				for (c <- letters.toIterator)
				{
					val key = Key.generateFromSingleCharacter(c, singleKeyCiphertext.length)
					var plaintext = Decrypt.decryptWithXor(key, singleKeyCiphertext)
					var phrase = Convert.toString(plaintext)
					var score = Metric.scorePhrase(phrase)
					
					//println("===\t" + keySize + "\t" + i + "\t" + c + "\t" + score +"\t===")
					//println("phrase = " + phrase)
				}

				//val scoreMap = Metric.evaluateAgainstSingleCharacterKeys(singleKeyCiphertext)
				//println(scoreMap(0))
			}
					
			assert(false)
		})
	}
}
