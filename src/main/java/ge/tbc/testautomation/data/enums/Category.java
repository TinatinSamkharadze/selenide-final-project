package ge.tbc.testautomation.data.enums;

import java.util.List;

public enum Category {
    MOVIE("კინო", List.of()),

    HOLIDAY("დასვენება", List.of(
            Subcategories.KAKHETI,
            Subcategories.GEOERGIA_TOURS,
            Subcategories.HOTELS_WITH_SWIMMING_POOLS,
            Subcategories.MOUNTAIN_RESORTS,
            Subcategories.SEASIDE_HOTELS,
            Subcategories.TBILISI_AND_SURROUNDINGS
    )),

    ENTERTAINMENT("გართობა", List.of(
            Subcategories.NIGHTCLUB,
            Subcategories.BOWLING,
            Subcategories.KARAOKE,
            Subcategories.BILLIARDS,
            Subcategories.MUSEUM,
            Subcategories.LOUNGE,
            Subcategories.TEENAGERS_ENTERTAINMENT,
            Subcategories.GAME_ZONE
    )),

    EAT_AND_DRINKS("კვება", List.of(
    Subcategories.CAFE_BAR,
    Subcategories.RESTAURANT,
    Subcategories.PUB,
    Subcategories.ASIAN_CUISINE,
    Subcategories.BRASSERIE
            )),

    KIDS_LAND("საბავშვო",  List.of(
    Subcategories.CHILDREN_ENTERTAINMENT_CENTER,
    Subcategories.PHOTO_VIDEO_STUDIO,
    Subcategories.PHOTOSHOOT,
    Subcategories.CALLING_HEROES,
    Subcategories.MUSEUM_FOR_KIDS,
    Subcategories.KARTING
            )),

    SPORT("სპორტი", List.of(
    Subcategories.YOGA_PILATES_AEROBICS,
    Subcategories.FITNESS,
    Subcategories.AEROBICS,
    Subcategories.SWIMMING_POOLS,
    Subcategories.BASKETBALL_COURT,
    Subcategories.TENNIS,
    Subcategories.TABLE_TENNIS,
    Subcategories.SPORTS_HALL_RENTAL,
    Subcategories.PADEL,
    Subcategories.HORSE_RIDING
    )),

    ESTHETICS("ესთეტიკა", List.of(
    Subcategories.PLASTIC_SURGERY,
    Subcategories.SAUNA_JACUZZI_SPA,
    Subcategories.HAIR_REMOVAL,
    Subcategories.MASSAGE,
    Subcategories.SOLARIUM,
    Subcategories.AESTHETIC_DENTISTRY,
    Subcategories.INSTRUMENTAL_PROCEDURES,
    Subcategories.NAILS,
    Subcategories.HAIR_BEARD,
    Subcategories.MAKEUP,
    Subcategories.FACIAL_CARE,
    Subcategories.EYEBROW_EYELASHES,
    Subcategories.TATTOO
            )),

    HEALTHCARE ("ჯანმრთელობა", List.of(
    Subcategories.DENTIST,
    Subcategories.LABORATORY_DIAGNOSTIC_CENTER,
    Subcategories.CLININC,
    Subcategories.CONSULTATION,
    Subcategories.THERAPY
            )),
     COURSES("კურსები",List.of(
    Subcategories.DRIVING_SCHOOL
            )),
    PETS("ცხოველები", List.of(
            Subcategories.PET_CARE
    ));

    private final String displayName;
    private final List<Subcategories> subcategories;

    Category(String displayName, List<Subcategories> subcategories) {
        this.displayName = displayName;
        this.subcategories = subcategories;
    }

    public String getDisplayName() {
        return displayName;
    }


    public List<Subcategories> getSubcategories() {
        return subcategories;
    }

    // Add this method to check if a subcategory belongs to this category
    public boolean containsSubcategory(Subcategories subcategory) {
        return subcategories.contains(subcategory);
    }
}


