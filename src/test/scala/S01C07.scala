package tech.fay.matasano

class S01C07 extends UnitSpec {

	import java.security._
	import javax.crypto._
	import javax.crypto.spec._

	"The encrypted message" should "match the decoded output" in 
	{
		var ciphertext = Io.readBase64Resource("/7-ciphertext.txt")
		var key = tech.fay.matasano.Key.generateFromString("YELLOW SUBMARINE")

		var plaintext = Decrypt.decryptWithAesEcbNoPadding(key, ciphertext)
		println(Convert.toString(plaintext))
	}
}
