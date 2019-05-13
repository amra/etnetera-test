package com.etnetera.hr.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera
 *
 */
@Entity
@Table(
		name = "javascript_framework",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "version"})}
)
public class JavaScriptFramework {

    public enum HypeLevel {DEAD, SOSO, NORMAL, AWESOME, TOP}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(name = "hype_level")
	private HypeLevel hypeLevel;

	@Column
	private String version;

	@Column(name = "deprecation_date")
	private Date deprecationDate;

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public HypeLevel getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(HypeLevel hypeLevel) {
        this.hypeLevel = hypeLevel;
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
		return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
	}

}
