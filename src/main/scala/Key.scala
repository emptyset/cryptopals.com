package tech.fay.matasano
{
	import scala.collection.immutable.HashMap

	object Key 
	{
		def generateFromSingleCharacter(c: Char, length: Int): Array[Byte] =
		{
			var array = new Array[Byte](length)
			for (i <- 0 to array.length - 1)
			{
				array(i) = c.asInstanceOf[Byte]
			}
			array
		}

		def generateFromString(s: String): Array[Byte] =
		{
			Convert.fromString(s)
		}
	}
}
