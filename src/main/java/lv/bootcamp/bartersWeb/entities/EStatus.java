package lv.bootcamp.bartersWeb.entities;

public enum EStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    DECLINED("Declined");
    private final String displayName;

    EStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
