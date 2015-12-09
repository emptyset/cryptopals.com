package tech.fay.matasano
{
	// cribbed from http://www.scala-sbt.org/0.13/sxr/sbt/Hash.scala.html
	object Util 
	{
		def fromHex(hex: String): Array[Byte] =
		{
			require ((hex.length & 1) == 0, "Hex string must have length 2n.")
			val array = new Array[Byte](hex.length >> 1)
			for (i <- 0 until hex.length by 2)
			{
				var c1 = hex.charAt(i);
				var c2 = hex.charAt(i+1);
				array(i >> 1) = ((fromHex(c1) << 4) | fromHex(c2)).asInstanceOf[Byte]
			}
			array
		}

		private def fromHex(c: Char): Int = 
		{
			val b = 
				if (c >= '0' && c <= '9')
					(c - '0')
				else if (c >= 'a' && c <= 'f')
					(c - 'a') + 10
				else if (c >= 'A' && c <= 'F')
					(c - 'A') + 10
				else
					throw new RuntimeException("Invalid hex character: '" + c + "'.")
		b
		}
	}
}
