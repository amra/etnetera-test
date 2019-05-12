package com.etnetera.hr.data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(
        name = "framework_version",
        uniqueConstraints = @UniqueConstraint(columnNames = {"version", "framework_id"})
)
public class FrameworkVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String version;

    @Column(name = "deprecation_date")
    private Date deprecationDate;

    @ManyToOne
    @JoinColumn(name = "framework_id")
    private JavaScriptFramework javaScriptFramework;

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

    public JavaScriptFramework getJavaScriptFramework() {
        return javaScriptFramework;
    }

    public void setJavaScriptFramework(JavaScriptFramework javaScriptFramework) {
        this.javaScriptFramework = javaScriptFramework;
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
