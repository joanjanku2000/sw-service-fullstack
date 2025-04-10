package org.swisscom.service_app.model;

import java.util.UUID;

/**
 * Object stored directly into mongodb
 */
public class Owner {
    private UUID id;
    private String name;
    private String accountNumber;
    private Integer level;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Owner{" + "id=" + id + ", name='" + name + '\'' + ", accountNumber='" + accountNumber + '\'' + ", level=" + level + '}';
    }
}
