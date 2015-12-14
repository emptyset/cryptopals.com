package tech.fay.matasano

class S01C06 extends UnitSpec {

	"The Hamming distance between the values" should "equal 37" in {
		var a = "this is a test"
		var b = "wokka wokka!!!"

		var aBytes = Convert.fromString(a)
		var bBytes = Convert.fromString(b)

		var distance = Operation.hammingDistance(aBytes, bBytes)

		assert(distance == 37)
	}


}
