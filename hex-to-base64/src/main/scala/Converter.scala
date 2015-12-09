package tech.fay.matasano
{
	// cribbed from https://gist.github.com/lisinge/0c32417c550c9f3b6f63
	import org.apache.commons.codec.binary.{ Base64 => ApacheBase64 }

	object Converter extends App 
	{
		var hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
		var output = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
		//println(hex)
		//println(output)

		var array = Util.fromHex(hex)
		var encoding = new String(ApacheBase64.encodeBase64(array))
		//println(encoding)

		assert(output == encoding)
	}
}
