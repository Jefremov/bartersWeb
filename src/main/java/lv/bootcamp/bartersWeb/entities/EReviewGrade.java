package lv.bootcamp.bartersWeb.entities;

public enum EReviewGrade {
    EXCELLENT("Excellent",5),
    GOOD("Good",4),
    AVERAGE("Average",3),
    POOR("Poor",2),
    FAIL("Fail",1);

    private final String displayName;
    private final int value;

    EReviewGrade(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }
    public int getValue() {
        return value;
    }
}
