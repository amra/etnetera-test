package com.etnetera.hr.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera
 *
 */
@Entity
public class JavaScriptFramework {

    public enum HypeLevel {DEAD, SOSO, NORMAL, AWESOME, TOP}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(name = "hype_level")
	private HypeLevel hypeLevel;

	@OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
		fetch = FetchType.EAGER
	)
	private List<FrameworkVersion> versions = new ArrayList<>();

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

    public List<FrameworkVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<FrameworkVersion> versions) {
        this.versions = versions;
    }

    @Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
	}

}
