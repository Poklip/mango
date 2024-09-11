package ru.kvs.mangomsngr.data

import ru.kvs.mangomsngr.R

enum class Countries(val flag: Int, val phoneNumberMask: String, val phoneNumberCode: String) {
    Russia(R.drawable.russia, "+_ (___) ___-__-__", "7"),
    Belarus(R.drawable.belarus, "+___ (__) ___-__-__", "375"),
    Georgia(R.drawable.georgia, "+___ (___) __-__-__", "995"),
    Kyrgyzstan(R.drawable.kyrgyzstan, "+___ (___) __-__-__", "996"),
    Uzbekistan(R.drawable.uzbekistan, "+___ (__) ___-__-__", "998"),
    Azerbaijan(R.drawable.azerbaijan, "+___ (__) ___-__-__", "994")
}