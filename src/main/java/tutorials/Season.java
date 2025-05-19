package tutorials;

import java.time.LocalDate;

public enum Season {
    SUMMER, WINTER, MID_SEASON;

    public static Season getSeason(LocalDate date) {
        int mois = date.getMonthValue();
        if (mois >= 6 && mois <= 8) return Season.SUMMER;
        if (mois == 12 || mois == 1 || mois == 2) return Season.WINTER;
        return Season.MID_SEASON;
    }
}
