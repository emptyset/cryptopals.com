package tech.fay.matasano
{
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

		var aBytes = Util.fromHex(a)
		var bBytes = Util.fromHex(b)
		var result = new Array[Byte](aBytes.length)

		for (i <- 0 until aBytes.length) 
		{
			var xor = (aBytes(i) ^ bBytes(i)).asInstanceOf[Byte]
			result(i) = xor
		}

		hex = Util.toHex(result)
		println(hex)
		assert(hex == output)
	}
}
