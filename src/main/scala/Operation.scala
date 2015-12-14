package tech.fay.matasano
{
	// cribbed from http://www.scala-sbt.org/0.13/sxr/sbt/Hash.scala.html
	object Operation 
	{
		def xor(a: Array[Byte], b: Array[Byte]): Array[Byte] =
		{
			var result = new Array[Byte](a.length)

			for (i <- 0 until a.length) 
			{
				var xor = (a(i) ^ b(i)).asInstanceOf[Byte]
				result(i) = xor
			}
			result
		}

		def hammingDistance(a: Array[Byte], b: Array[Byte]): Int =
		{
			var result = xor(a, b)
			var distance = 0
			for (i <- 0 until result.length)
			{
				var byte = result(i)
				while (byte != 0)
				{
					if ((byte & 0x01) == 0x01) {
						distance += 1
					}
					byte = (byte >> 1).asInstanceOf[Byte]
				}
			}
			distance
		}
	}
}
