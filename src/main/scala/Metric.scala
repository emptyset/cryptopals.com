package tech.fay.matasano
{
	import scala.collection.immutable.HashMap

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
	}
}
