package tech.fay.matasano

class S01C03 extends UnitSpec {

	"The highest scoring phrase" should "match the known answer" in {
		var a = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
		var ciphertext = Convert.fromHex(a)
		var scoreMap = Metric.evaluateAgainstSingleCharacterKeys(ciphertext)
		//scoreMap.foreach(e => if (e._1 > 100) println(e._1 + "\t" + e._2))

		var values = scoreMap(0)
		var score = values._1
		var phrase = values._2

		assert(phrase == "Cooking MC's like a pound of bacon")
	}
}
