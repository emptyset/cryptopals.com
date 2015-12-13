package tech.fay.matasano
{
	object Main extends App 
	{
		// challenge 5
		// pull text from the file "5.txt"
		// encrypt each byte with key "ICE" using repeating-key XOR
		var key = Key.generateFromString("ICE")
		var stream = getClass.getResourceAsStream("/5-plaintext.txt")
		var plaintext = Array[Byte]()
		for (line <- scala.io.Source.fromInputStream(stream).getLines())
		{
			plaintext = ('\n' + line).getBytes()
			var ciphertext = Encrypt.encryptWithRepeatingKeyXor(key, plaintext)
			var hex = Convert.toHex(ciphertext)
			println(hex)

			plaintext = Decrypt.decryptWithRepeatingKeyXor(key, ciphertext)
			hex = Convert.toString(plaintext)
			println(hex)
		}
		
		// original (does not have 2n characters in line1)
		//var line1 = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272"
		//var line2 = "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"

		// slight variant (actually correct)
		var line1 = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20"
		var ciphertext = Convert.fromHex(line1)
		plaintext = Decrypt.decryptWithRepeatingKeyXor(key, ciphertext)
		var hex = Convert.toString(plaintext)
		println(line1)
		println(hex)

		var line2 = "430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"
		ciphertext = Convert.fromHex(line2)
		plaintext = Decrypt.decryptWithRepeatingKeyXor(key, ciphertext)
		hex = Convert.toString(plaintext)
		println(line2)
		println(hex)

		// NOTE: subtle problem with placement of '\n' character to correctly encrypt/decrypt
	}
}
