package tech.fay.matasano

import scala.collection.mutable.ArrayBuffer

class S01C04 extends UnitSpec {

	"The highest scoring phrase" should "match the known answer" in {
		var topScores = new ArrayBuffer[(Int, String)]
		var stream: java.io.InputStream = getClass.getResourceAsStream("/4.txt")
		for (line <- scala.io.Source.fromInputStream(stream).getLines())
		{
			var ciphertext = Convert.fromHex(line)
			var scoreMap = Metric.evaluateAgainstSingleCharacterKeys(ciphertext)
			topScores.append(scoreMap(0))
		}

		var sorted = scala.util.Sorting.stableSort(topScores, (e1: Tuple2[Int, String], e2: Tuple2[Int, String]) => e1._1 > e2._1)

		var values = sorted(0)
		var score = values._1
		var phrase = values._2

		assert(phrase == "Now that the party is jumping\n")
	}
}
