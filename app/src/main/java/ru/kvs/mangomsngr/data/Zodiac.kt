package ru.kvs.mangomsngr.data

import ru.kvs.mangomsngr.R

enum class Zodiac(val image: Int, val dates: Pair<Int, Int>) {
    WaterBearer(R.drawable.water_bearer, 121 to 219),
    Fishes(R.drawable.fishes, 220 to 320),
    Ram(R.drawable.ram, 321 to 420),
    Bull(R.drawable.bull, 421 to 521),
    Twins(R.drawable.twins, 522 to 621),
    Crab(R.drawable.crab, 622 to 722),
    Lion(R.drawable.lion, 723 to 821),
    Maiden(R.drawable.maiden, 822 to 923),
    Scales(R.drawable.scales, 924 to 1023),
    Scorpion(R.drawable.scorpoin, 1024 to 1122),
    Archer(R.drawable.archer, 1123 to 1222),
    SeaGoat(R.drawable.sea_goat, 1223 to 120)
}

fun getZodiac(birthday: String?) : Zodiac? {
    if (birthday.isNullOrEmpty()) return null
    val dateAsInteger = try {
        birthday
            .substringBeforeLast('.')
            .replace(".", "")
            .toInt()
    } catch (e: NumberFormatException) {
        return null
    }
    if (dateAsInteger.isIntegerBetweenBorders(Zodiac.WaterBearer.dates)) {
        return Zodiac.WaterBearer
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Fishes.dates)) {
        return Zodiac.Fishes
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Ram.dates)) {
        return Zodiac.Ram
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Bull.dates)) {
        return Zodiac.Bull
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Twins.dates)) {
        return Zodiac.Twins
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Crab.dates)) {
        return Zodiac.Crab
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Lion.dates)) {
        return Zodiac.Lion
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Maiden.dates)) {
        return Zodiac.Maiden
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Scales.dates)) {
        return Zodiac.Scales
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Scorpion.dates)) {
        return Zodiac.Scorpion
    } else if (dateAsInteger.isIntegerBetweenBorders(Zodiac.Archer.dates)) {
        return Zodiac.Archer
    } else if (Zodiac.SeaGoat.dates.first >= dateAsInteger || Zodiac.SeaGoat.dates.second <= dateAsInteger) {
        return Zodiac.SeaGoat
    } else {
        return null
    }
}

fun Int.isIntegerBetweenBorders(borders: Pair<Int, Int>): Boolean {
    return borders.first <= this && this <= borders.second
}