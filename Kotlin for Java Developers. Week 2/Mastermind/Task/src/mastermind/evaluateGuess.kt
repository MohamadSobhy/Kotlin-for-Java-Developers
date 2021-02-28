package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0
    var wrongPosition = 0

    var validatedSecret = secret
    var validatedGuess = guess

    var index = 0

    while (index < secret.length){
        println("S: ${secret[index]}, G: ${guess[index]}")

        if(secret[index] == guess[index]){
            rightPosition++

            validatedSecret = validatedSecret.replaceFirst(secret[index].toString(), "")
            validatedGuess = validatedGuess.replaceFirst(secret[index].toString(), "")

            println("NS: $validatedSecret, NG: $validatedGuess")
        }
        index++
    }

    println("validated secret: $validatedSecret")

    for (letter in validatedSecret){
        if(validatedGuess.contains(letter)){
            wrongPosition++

            validatedGuess = validatedGuess.replaceFirst(letter.toString(), "")

            println("validated guess: $validatedGuess")
        }
    }

    return Evaluation(rightPosition, wrongPosition)
}

