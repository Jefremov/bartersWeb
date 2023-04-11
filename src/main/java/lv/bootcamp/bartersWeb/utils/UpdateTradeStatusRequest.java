package lv.bootcamp.bartersWeb.utils;

import lv.bootcamp.bartersWeb.entities.EStatus;

public class UpdateTradeStatusRequest {
    private EStatus status;

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }
}