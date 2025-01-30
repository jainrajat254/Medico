package com.example.medico.data

enum class Gender(val displayName: String) {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    PREFER_NOT_TO_SAY("Prefer Not to say")
}

enum class BloodGroup(val group: String) {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-")
}

enum class Frequency(val fre : String) {
    Mornings("Mornings"),
    Afternoons("Afternoons"),
    Evenings("Evenings"),
    WithMeals("With Meals"),
    MorningAndEvenings("Morning/Evenings"),
}
