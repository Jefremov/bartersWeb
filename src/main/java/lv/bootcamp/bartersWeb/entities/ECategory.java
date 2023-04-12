package lv.bootcamp.bartersWeb.entities;

public enum ECategory {
    FOODITEMS("Food items"),
    BEVERAGES("Beverages"),
    CLOTHING("Clothing"),
    TOOLS("Tools"),
    WEAPONS("Weapons"),
    ELECTRONICS("Electronics"),
    HOMEAPPLIANCES("Home appliances"),
    JEWELRY("Jewelry"),
    BEAUTYPRODUCTS("Beauty products"),
    ARTSUPPLIES("Art supplies"),
    SPORTSEQUIPMENT("Sports equipment"),
    MUSICALINSTRUMENTS("Musical instruments"),
    BOOKS("Books"),
    TOYSANDGAMES("Toys and games"),
    OFFICESUPPLIES("Office supplies"),
    AUTOMOTIVESUPPLIES("Automotive supplies"),
    CLEANINGSUPPLIES("Cleaning supplies"),
    HEALTHANDWELLNESSPRODUCTS("Health and wellness products"),
    GARDENINGTOOLS("Gardening tools"),
    PETSUPPLIES("Pet supplies"),
    TRAVELACCESSORIES("Travel accessories"),
    HOMEDECOR("Home decor"),
    STATIONERY("Stationery"),
    DIYSUPPLIES("DIY supplies"),
    CAMPINGGEAR("Camping gear"),
    FISHINGEQUIPMENT("Fishing equipment"),
    HUNTINGGEAR("Hunting gear"),
    EXERCISEEQUIPMENT("Exercise equipment"),
    PARTYSUPPLIES("Party supplies"),
    EDUCATIONALMATERIALS("Educational materials");

    private String displayName;
    ECategory(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

}
