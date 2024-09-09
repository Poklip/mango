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