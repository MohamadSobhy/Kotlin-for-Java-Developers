package rationals

import java.math.BigInteger


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println("Sum: $sum")
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

fun String.toRational(): Rational {
    val splitted = this.split("/")

    if (splitted.size == 1) return Rational.createRational(this.toBigInteger(), 1.toBigInteger())

    val nominator = splitted[0].toBigInteger()
    val denominator = splitted[1].toBigInteger()

    return Rational.createRational(nominator, denominator)
}

@Suppress("DataClassPrivateConstructor")
data class Rational private constructor(val nominator: BigInteger, val denominator: BigInteger) : Comparable<Rational> {

    companion object {
        fun createRational(n: BigInteger, d: BigInteger): Rational = normalize(n, d)
        private fun normalize(nominator: BigInteger, denominator: BigInteger): Rational {
            
            require(denominator != BigInteger.ZERO) { "Denominator must be non-zero" }

            val gcd = nominator.gcd(denominator)

            val newNominator = nominator / gcd
            var newDenominator = denominator / gcd
            val sign = denominator.signum().toBigInteger()

            return Rational(sign * newNominator, sign * newDenominator)
        }
    }

    override fun toString(): String {
        if (denominator.toInt() == 1) return "$nominator"

        return "$nominator/$denominator"
    }

    override fun equals(other: Any?): Boolean {

        if (other is Rational) {
            val firstValue = this.nominator.toDouble() / this.denominator.toDouble()
            val otherValue = other.nominator.toDouble() / other.denominator.toDouble()


//            println(this)
//            println(other)
//            println(firstValue)
//            println(otherValue)

            if (this === other) return true
            if ((this.nominator == other.nominator && this.denominator == other.denominator)
                    || (firstValue == otherValue))
                return true
        }
        return false
    }

    override fun compareTo(other: Rational): Int {
        val firstValue = (this.nominator.toDouble() / this.denominator.toDouble())
        val otherValue = (other.nominator.toDouble() / other.denominator.toDouble())

        return if (firstValue > otherValue) 1
        else if (otherValue > firstValue) -1
        else 0
    }
}

infix fun Int.divBy(other: Int): Rational {
    return Rational.createRational(this.toBigInteger(), other.toBigInteger())
}

infix fun BigInteger.divBy(other: BigInteger): Rational {
    return Rational.createRational(this, other)
}

infix fun Long.divBy(other: Long): Rational {
    return Rational.createRational(this.toBigInteger(), other.toBigInteger())
}

operator fun Rational.times(other: Rational): Rational {
    return Rational.createRational(
            this.nominator * other.nominator,
            this.denominator * other.denominator
    )
}

operator fun Rational.div(other: Rational): Rational {
    return Rational.createRational(
            this.nominator * other.denominator,
            this.denominator * other.nominator
    )
}

operator fun Rational.plus(other: Rational): Rational {
    val newDenominator = this.denominator * other.denominator
    val newNominator = this.denominator * other.nominator + this.nominator * other.denominator

    return Rational.createRational(newNominator, newDenominator)
}

operator fun Rational.minus(other: Rational): Rational {
    val newDenominator = this.denominator * other.denominator
    val newNominator = other.denominator * this.nominator - other.nominator * this.denominator

    return Rational.createRational(newNominator, newDenominator)
}

operator fun Rational.unaryMinus(): Rational {
    return Rational.createRational(-this.nominator, this.denominator)
}