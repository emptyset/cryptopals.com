package tech.fay.matasano

class S01C05 extends UnitSpec {

	"The provided phrase" should "encrypt to the expected hex encoded string" in {
		// NOTE: the placement of the '\n' character results in different encodings
		var phrase = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal"
		var encoding = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"

		var key = Key.generateFromString("ICE")
		var plaintext = Convert.fromString(phrase)
		var ciphertext = Encrypt.encryptWithRepeatingKeyXor(key, plaintext)
		var hex = Convert.toHex(ciphertext)
		assert(hex == encoding)

		plaintext = Decrypt.decryptWithRepeatingKeyXor(key, Convert.fromHex(encoding))
		var decrypted = Convert.toString(plaintext)
		assert(decrypted == phrase)
	}
}
