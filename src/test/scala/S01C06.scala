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
		var buffer = new ArrayBuffer[Byte]()
		var stream: java.io.InputStream = getClass.getResourceAsStream("/6-ciphertext.txt")
		for (line <- scala.io.Source.fromInputStream(stream).getLines())
		{
			buffer = buffer ++ ApacheBase64.decodeBase64(line.getBytes)
		}
		var ciphertext = buffer.toArray

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


}
