package tech.fay.matasano
{
	import scala.collection.mutable.ArrayBuffer
	import org.apache.commons.codec.binary.{ Base64 => ApacheBase64 }

	object Io 
	{
		def readBase64Resource(resource: String): Array[Byte] =
		{
			var buffer = new ArrayBuffer[Byte]()
			var stream: java.io.InputStream = getClass.getResourceAsStream(resource)
			for (line <- scala.io.Source.fromInputStream(stream).getLines())
			{
				buffer = buffer ++ ApacheBase64.decodeBase64(line.getBytes)
			}
			buffer.toArray
		}
	}
}
