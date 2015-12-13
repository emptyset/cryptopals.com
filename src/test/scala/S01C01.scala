package tech.fay.matasano

class S01C01 extends UnitSpec {

	"The output encoding" should "match the exercise result" in {
		import org.apache.commons.codec.binary.{ Base64 => ApacheBase64 }

		var hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
		var output = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"

		var bytes = Util.fromHex(hex)
		var encoding = new String(ApacheBase64.encodeBase64(bytes))
		assert(output == encoding)
	}
}
