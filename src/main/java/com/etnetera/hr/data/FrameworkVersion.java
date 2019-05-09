package com.etnetera.hr.data;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class FrameworkVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String version;

    @Column(name = "deprecation_date")
    private Date deprecationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(Date deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    @Override
    public String toString() {
        return "FrameworkVersion{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", deprecationDate='" + deprecationDate + '\'' +
                '}';
    }
}
