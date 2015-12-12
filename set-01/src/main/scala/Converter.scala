package tech.fay.matasano
{
	import scala.collection.immutable.HashMap
	// cribbed from https://gist.github.com/lisinge/0c32417c550c9f3b6f63
	import org.apache.commons.codec.binary.{ Base64 => ApacheBase64 }

	object Converter extends App 
	{
		// challenge 1
		var hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
		var output = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
		var array = Util.fromHex(hex)
		var encoding = new String(ApacheBase64.encodeBase64(array))
		assert(output == encoding)

		// challenge 2
		var a = "1c0111001f010100061a024b53535009181c"
		var b = "686974207468652062756c6c277320657965"
		output = "746865206b696420646f6e277420706c6179"

		var result = Util.xor(a, b)
		hex = Util.toHex(result)
		//println(hex)
		assert(hex == output)

		// challenge 3
		a = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
		
		var c = 0;
		var letters = ('a' to 'z').toSet ++ ('A' to 'Z').toSet
		var scoreMap : Array[(Int, String)] = Array()
		for (c <- letters.toIterator) {
			var aBytes = Util.fromHex(a)
			var array = new Array[Byte](aBytes.length)
			for (i <- 0 to aBytes.length - 2)
			{
				// apparently, "has been XOR'd against a single character" means
				// a byte array consisting of entirely this character (not a zero 
				// padded byte array with the single character in the last byte)

				//array(i) = 0.asInstanceOf[Byte]
				array(i) = c.asInstanceOf[Byte]
			}
			array(aBytes.length - 1) = c.asInstanceOf[Byte]

			b = Util.toHex(array)
			result = Util.xor(a, b)

			var score = 0;
			var map = HashMap[Char, Int](
				' ' -> 20,
				'e' -> 12,
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
			var phrase = "";
			for (i <- 0 to result.length - 1)
			{
				var character = result(i).asInstanceOf[Char]
				phrase += character;
				score += map getOrElse(character.toLower, 0)
			}

			scoreMap = scoreMap:+(score, phrase)	
		}

		scala.util.Sorting.stableSort(scoreMap, (e1: Tuple2[Int, String], e2: Tuple2[Int, String]) => e1._1 < e2._1)
		scoreMap.foreach(e => println(e._1 + "\t" + e._2))
	}
}
