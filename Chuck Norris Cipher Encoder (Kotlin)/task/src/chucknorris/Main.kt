package chucknorris

class Output(var output: String, val correct: Boolean)

fun properlyEncoded(s: String): Boolean = (s.firstOrNull { !(it == '0' || it == ' ') } == null)

fun decode(input: String): Output {
    if (!properlyEncoded((input))) {
        return Output("Encoded string is not valid.", false)
    }

    val encoded = input.split(" ")

    if (encoded.size % 2 != 0) {
        return Output("Encoded string is not valid.", false)
    }
    var output = ""
    var value = ""
    var binary = ""
    for (n in encoded.indices) {
        if (n % 2 == 0) {
            value = if (encoded[n] == "0") {
                "1"
            } else if (encoded[n] == "00") {
                "0"
            } else {
                return Output("Encoded string is not valid.", false)
            }
        } else {
            binary += value.repeat(encoded[n].length)
        }
    }

    if (binary.length % 7 != 0) {
        return Output("Encoded string is not valid.", false)
    }

    for (n in binary.indices) {
        if (n % 7 == 0) {
            output += binary.substring(n..n + 6).toInt(2).toChar()
        }
    }

    return Output(output, true)
}

fun encode(input: String): String {
    var encoded = ""
    var bNum = ""
    for (i in input.indices) {
        val aux = Integer.toBinaryString(input[i].code)
        if (aux.length < 7) {
            bNum += "0".repeat(7 - aux.length)
        }
        bNum += aux
    }

    var count = 0
    var last = 'i'

    while (count < bNum.length) {
        if (bNum[count] != last) {
            if (bNum[count] == '0') {
                if (last != 'i') {
                    encoded += " "
                }
                encoded += "00 "
            } else {
                if (last != 'i') {
                    encoded += " "
                }
                encoded += "0 "
            }
        }
        encoded += "0"
        last = bNum[count]
        count++
    }
    return encoded
}

fun main() {
    do {
        println("\nPlease input operation (encode/decode/exit):")
        val action = readln()

        when (action) {
            "encode" -> {
                println("Input string:")
                val input = readln()
                println("Encoded string:")
                println(encode(input))
            }

            "exit" -> println("Bye!")
            "decode" -> {
                println("Input encoded string:")
                val input = readln()
                val output = decode(input)
                if (output.correct) {
                    println("Decoded string:")
                    println(output.output)
                } else {
                    println(output.output)
                }
            }

            else -> println("There is no '$action' operation")
        }
    } while (action != "exit")

}