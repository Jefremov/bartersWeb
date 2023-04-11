package lv.bootcamp.bartersWeb.entities;
public enum EItemStatus {
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable");
    private String displayName;
    EItemStatus(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}

