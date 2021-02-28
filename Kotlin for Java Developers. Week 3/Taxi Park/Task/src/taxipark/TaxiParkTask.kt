package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filter { driver -> driver.name !in trips.map { trip -> trip.driver.name } }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter { passenger -> trips.flatMap { trip -> trip.passengers }.count { it.name == passenger.name } >= minTrips }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    val drivTrips = this.trips.filter {
        it.driver.name == driver.name
    }

    return drivTrips.flatMap { it.passengers }.groupingBy {
        it
    }.eachCount().filter { pair ->
        pair.value > 1
    }.keys.toSet()
}

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.filter { passenger ->
            trips.filter { trip ->
                passenger in trip.passengers
            }.count { tt ->
                tt.discount != null && tt.discount > 0.0
            } > trips.filter { trip ->
                passenger in trip.passengers
            }.count { tt ->
                tt.discount == null || tt.discount == 0.0
            }
        }.toSet();


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    return trips.map {
        (it.duration - (it.duration % 10))..(it.duration + 9 - (it.duration % 10))
    }.groupingBy { i -> i }.eachCount().maxBy { d ->
        d.value
    }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {

    if (trips.isEmpty()) return false

    val driversByIncome = trips.groupBy { it.driver }.mapValues { it.value.sumByDouble { trip -> trip.cost } }

    val totalIncome = driversByIncome.values.sum()

    println("Total income: $totalIncome")

    val driversPercent = (allDrivers.size * 0.2).toInt()
    println("Most Success Drivers count: $driversPercent")

    val contributeCost = (if (driversByIncome.size >= driversPercent) driversByIncome.toList().sortedByDescending { it.second }.subList(0, driversPercent)
    else driversByIncome.toList()).sumByDouble { (it.second) }

    println("Contribution: $contributeCost")
    return ((contributeCost / totalIncome) * 100) >= 80
}