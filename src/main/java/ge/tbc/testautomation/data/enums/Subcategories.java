package ge.tbc.testautomation.data.enums;

public enum Subcategories {
    //დასვენების
    KAKHETI("კახეთი"),
    GEOERGIA_TOURS("ტურები საქართველოში"),
    HOTELS_WITH_SWIMMING_POOLS("სასტუმროები აუზით"),
    MOUNTAIN_RESORTS("მთის კურორტები"),
    SEASIDE_HOTELS("ზღვისპირეთი"),
    TBILISI_AND_SURROUNDINGS("თბილისი და შემოგარენი"),

    //გართობის

    NIGHTCLUB("ღამის კლუბი"),
    BOWLING("ბოულინგი"),
    KARAOKE("კარაოკე"),
    BILLIARDS("საბილიარდო"),
    MUSEUM("მუზეუმი"),
   LOUNGE("ლაუნჯი"),
    TEENAGERS_ENTERTAINMENT("თინეიჯერების გართობა"),
    GAME_ZONE("Game Zone"),

    //კვების
    CAFE_BAR("კაფე-ბარი"),
    RESTAURANT("რესტორანი"),
    PUB("პაბი/ლუდხანა"),
    ASIAN_CUISINE("აზიური სამზარეულო"),
    BRASSERIE("ღვინის ბარი"),

    //საბავშვო
    CHILDREN_ENTERTAINMENT_CENTER("ბავშვთა გასართობი ცენტრი"),
    PHOTO_VIDEO_STUDIO("ფოტო/ვიდეო სტუდია"),
    PHOTOSHOOT("ფოტოსესია"),
    CALLING_HEROES("გმირების გამოძახება"),
    MUSEUM_FOR_KIDS("მუზეუმი"),
    KARTING("კარტინგი"),

    //სპორტის
    YOGA_PILATES_AEROBICS("იოგა/პილატესი/აერობიკა"),
    FITNESS("ფიტნესი"),
    AEROBICS("აერობიკა"),
    SWIMMING_POOLS("საცურაო აუზი"),
    BASKETBALL_COURT("კალათბურთის მოედანი"),
    TENNIS("ჩოგბურთი"),
    TABLE_TENNIS("მაგიდის ჩოგბურთი"),
    SPORTS_HALL_RENTAL("სპორტ დარბაზი"),
    PADEL("პადელი"),
    HORSE_RIDING("ცხენით ჯირითი"),

    //ესთეტიკის
    PLASTIC_SURGERY("პლასტიკური ქირურგია"),
    SAUNA_JACUZZI_SPA("საუნა/ჯაკუზი/სპა"),
    HAIR_REMOVAL("ეპილაცია"),
    MASSAGE("მასაჯი"),
    SOLARIUM("სოლარიუმი"),
    AESTHETIC_DENTISTRY("ესთეტიკური სტომატოლოგია"),
    INSTRUMENTAL_PROCEDURES("აპარატული პროცედურები"),
    NAILS("ფრჩხილები"),
    HAIR_BEARD("თმა/წვერი"),
    MAKEUP("მაკიაჟი"),
    FACIAL_CARE("სახის მოვლა"),
    EYEBROW_EYELASHES("წარბი/წამწამი"),
    TATTOO("ტატუ"),

    //ჯანმრთელობის
    DENTIST("სტომატოლოგია"),
    LABORATORY_DIAGNOSTIC_CENTER("ლაბორატორია, დიაგნოსტიკა"),
    CLININC("კლინიკა"),
    CONSULTATION("კონსულტაცია"),
    THERAPY("თერაპია"),

    //კურსების
    DRIVING_SCHOOL("ავტო სკოლა"),

    //ცხოველების
    PET_CARE("ცხოველთა სამყარო");


    private final String displayName;

    Subcategories(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
