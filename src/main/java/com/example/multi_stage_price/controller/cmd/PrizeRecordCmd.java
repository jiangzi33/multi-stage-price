package com.example.multi_stage_price.controller.cmd;

import java.util.Date;

public class PrizeRecordCmd {
    private int userId;
    private String bizScene;
    private String prizeCode;
    private String prizeDate;
    private int prizeStage;
    private int prizeAmount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBizScene() {
        return bizScene;
    }

    public void setBizScene(String bizScene) {
        this.bizScene = bizScene;
    }

    public String getPrizeCode() {
        return prizeCode;
    }

    public void setPrizeCode(String prizeCode) {
        this.prizeCode = prizeCode;
    }

    public String getPrizeDate() {
        return prizeDate;
    }

    public void setPrizeDate(String prizeDate) {
        this.prizeDate = prizeDate;
    }

    public int getPrizeStage() {
        return prizeStage;
    }

    public void setPrizeStage(int prizeStage) {
        this.prizeStage = prizeStage;
    }

    public int getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(int prizeAmount) {
        this.prizeAmount = prizeAmount;
    }
}
