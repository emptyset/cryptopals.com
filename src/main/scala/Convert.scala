package tech.fay.matasano
{
	// cribbed from http://www.scala-sbt.org/0.13/sxr/sbt/Hash.scala.html
	object Convert 
	{
		def fromString(s: String): Array[Byte] =
		{
			s.getBytes()
		}

		def toString(bytes: Array[Byte]): String =
		{
			var phrase = ""
			for (i <- 0 to bytes.length - 1)
			{
				var character = bytes(i).asInstanceOf[Char]
				phrase += character
			}
			phrase
		}

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

		def toHex(bytes: Array[Byte]): String =
		{
			val buffer = new StringBuilder(bytes.length * 2)
			for (i <- 0 until bytes.length)
			{
				val b = bytes(i)
				val bi: Int = if (b < 0) b + 256 else b
				buffer append toHex((bi >>> 4).asInstanceOf[Byte])
				buffer append toHex((bi & 0x0F).asInstanceOf[Byte])
			}
			buffer.toString
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

		private def toHex(b: Byte): Char =
		{
			require(b >= 0 && b <= 15, "Byte " + b + " was not between 0 and 15")
			if (b < 10)
				('0'.asInstanceOf[Int] + b).asInstanceOf[Char]
			else
				('a'.asInstanceOf[Int] + (b-10)).asInstanceOf[Char]
		}
	}
}
