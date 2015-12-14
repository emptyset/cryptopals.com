package tech.fay.matasano
{
	import scala.collection.mutable.ArrayBuffer

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

		def toBlocks(bytes: Array[Byte], size: Int): Array[Array[Byte]] =
		{
			// TODO: fix the block size issue (padding with zeroes correctly)
			val buffer = new ArrayBuffer[Array[Byte]]()
			for (i <- 0 until bytes.length - 1 by size)
			{
				val block = new Array[Byte](size)
				for (j <- 0 until size - 1)
				{
					block(j) = bytes(i + j)
				}
				buffer.append(block)
			}
			buffer.toArray
		}

		def transposeBlocks(blocks: Array[Array[Byte]]): Array[Array[Byte]] =
		{
			val buffer = new ArrayBuffer[Array[Byte]]()
			val blockSize = blocks(0).length
			for (k <- 0 until blockSize)
			{
				buffer.append(new Array[Byte](blocks.length))
			}
			val transposed = buffer.toArray

			for (i <- 0 until blocks.length - 1)
			{
				val block = blocks(i)
				for (j <- 0 until blockSize)
				{
					transposed(j)(i) = blocks(i)(j)
				}
			}
			transposed
		}
	}
}
