package lv.bootcamp.bartersWeb.entity;

import java.util.HashMap;
import java.util.Map;

public enum ItemCategory {
    FOODITEMS(1), BEVERAGES(2), CLOTHING(3), TOOLS(4), WEAPONS(5), ELECTRONICS(6), HOMEAPPLIANCES(7), JEWELRY(8), BEAUTYPRODUCTS(9), ARTSUPPLIES(10), SPORTSEQUIPMENT(11), MUSICALINSTRUMENTS(12), BOOKS(13), TOYSANDGAMES(14), OFFICESUPPLIES(15), AUTOMOTIVESUPPLIES(16), CLEANINGSUPPLIES(17), HEALTHANDWELLNESSPRODUCTS(18), GARDENINGTOOLS(19), PETSUPPLIES(20), TRAVELACCESSORIES(21), HOMEDECOR(22), STATIONERY(23), DIYSUPPLIES(24), CAMPINGGEAR(25), FISHINGEQUIPMENT(26), HUNTINGGEAR(27), EXERCISEEQUIPMENT(28), PARTYSUPPLIES(29), EDUCATIONALMATERIALS(30);

    private int value;
    private static Map map = new HashMap<>();
    private ItemCategory(int value){
        this.value = value;
    }
    static {
        for (ItemCategory ic : ItemCategory.values()) {
            map.put(ic.value, ic);
        }
    }

    public static ItemCategory valueOf(int itemCategory) {
        return (ItemCategory) map.get(itemCategory);
    }

    public int getValue() {
        return value;
    }


}
