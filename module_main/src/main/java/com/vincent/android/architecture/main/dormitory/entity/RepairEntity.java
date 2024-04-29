package com.vincent.android.architecture.main.dormitory.entity;

public class RepairEntity {
    private int id;
    private String date;
    private String repairContent;
    private String brokenDegree;
    private String repairDegree;

    public RepairEntity(String date, String repairContent, String brokenDegree, String repairDegree) {
        this.date = date;
        this.repairContent = repairContent;
        this.brokenDegree = brokenDegree;
        this.repairDegree = repairDegree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getBrokenDegree() {
        return brokenDegree;
    }

    public void setBrokenDegree(String brokenDegree) {
        this.brokenDegree = brokenDegree;
    }

    public String getRepairDegree() {
        return repairDegree;
    }

    public void setRepairDegree(String repairDegree) {
        this.repairDegree = repairDegree;
    }
}
