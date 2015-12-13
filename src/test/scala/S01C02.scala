package tech.fay.matasano

class S01C02 extends UnitSpec {

	"The XOR result" should "match the expected output" in {
		var a = "1c0111001f010100061a024b53535009181c"
		var b = "686974207468652062756c6c277320657965"
		var expected = "746865206b696420646f6e277420706c6179"

		var aBytes = Util.fromHex(a)
		var bBytes = Util.fromHex(b)
		var result = Operation.xor(aBytes, bBytes)
		// TODO: migrate this to a Convert class
		var hex = Util.toHex(result)
		// TODO: actually compare byte arrays (write method)
		assert(hex == expected)
	}
}
