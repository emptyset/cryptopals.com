package tech.fay.matasano
{
	import scala.collection.immutable.HashMap
	// cribbed from https://gist.github.com/lisinge/0c32417c550c9f3b6f63

	object Main extends App 
	{
		// challenge 2
		var a = "1c0111001f010100061a024b53535009181c"
		var b = "686974207468652062756c6c277320657965"
		var output = "746865206b696420646f6e277420706c6179"

		var result = Util.xor(a, b)
		var hex = Util.toHex(result)
		//println(hex)
		assert(hex == output)

		// challenge 3
		a = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
		var ciphertext = Util.fromHex(a)
		var scoreMap = Decrypt.evaluateAgainstSingleCharacterKeys(ciphertext)
		//scoreMap.foreach(e => if (e._1 > 100) println(e._1 + "\t" + e._2))

		// challenge 4
		// iterate through each line in the file, "4.txt"
		// evaluate each line against all single character keys
		// print out just the first element in each evaluation

		// TODO: see if src/main/resources is accessible; should be
		var stream: java.io.InputStream = getClass.getResourceAsStream("/4.txt")
		for (line <- scala.io.Source.fromInputStream(stream).getLines())
		{
			var ciphertext = Util.fromHex(line)
			var scoreMap = Decrypt.evaluateAgainstSingleCharacterKeys(ciphertext)
			//scoreMap.foreach(e => if (e._1 > 200) println(e._1 + "\t" + e._2))
		}

		// challenge 5
		// pull text from the file "5.txt"
		// encrypt each byte with key "ICE" using repeating-key XOR
		var key = "ICE".getBytes()
		stream = getClass.getResourceAsStream("/5-plaintext.txt")
		var plaintext = Array[Byte]()
		for (line <- scala.io.Source.fromInputStream(stream).getLines())
		{
			plaintext = ('\n' + line).getBytes()
			ciphertext = Encrypt.encryptWithRepeatingKeyXor(key, plaintext)
			hex = Util.toHex(ciphertext)
			println(hex)

			plaintext = Decrypt.decryptWithRepeatingKeyXor(key, ciphertext)
			hex = Decrypt.toString(plaintext)
			println(hex)
		}
		
		// original (does not have 2n characters in line1)
		//var line1 = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272"
		//var line2 = "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"

		// slight variant (actually correct)
		var line1 = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20"
		ciphertext = Util.fromHex(line1)
		plaintext = Decrypt.decryptWithRepeatingKeyXor(key, ciphertext)
		hex = Decrypt.toString(plaintext)
		println(line1)
		println(hex)

		var line2 = "430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"
		ciphertext = Util.fromHex(line2)
		plaintext = Decrypt.decryptWithRepeatingKeyXor(key, ciphertext)
		hex = Decrypt.toString(plaintext)
		println(line2)
		println(hex)

		// NOTE: subtle problem with placement of '\n' character to correctly encrypt/decrypt
	}
}
