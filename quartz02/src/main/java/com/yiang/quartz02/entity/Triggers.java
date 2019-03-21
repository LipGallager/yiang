package com.yiang.quartz02.entity;

public class Triggers {
    private Integer id;

    private String cron;

    private String status;

    private String job_name;

    private String job_group;

    public Triggers(Integer id, String cron, String status, String job_name, String job_group) {
        this.id = id;
        this.cron = cron;
        this.status = status;
        this.job_name = job_name;
        this.job_group = job_group;
    }

    public Triggers() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getJob_group() {
        return job_group;
    }

    public void setJob_group(String job_group) {
        this.job_group = job_group;
    }
}