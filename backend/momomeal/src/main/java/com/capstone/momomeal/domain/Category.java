package com.capstone.momomeal.domain;

public enum Category {
    CHICKEN("Chicken"), PIZZA("Pizza"), KOREAN("Korean"),
    CHINESE("Chinese"), JAPANESE("Japanese"), WESTERN("Western"), SNACKBAR("Snackbar")
    , MIDNIGHTSNACK("MidnightSnack"), BOILEDPORK("BoiledPork"), CAFE("CafeAndDesert"),
    FASTFOOD("Fastfood"), ETC("Etc");

    private String krName;

    Category(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
