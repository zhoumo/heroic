package mine.atlas.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import mine.mythos.annotation.Component;
import mine.mythos.annotation.Key;
import mine.mythos.annotation.Title;
import mine.mythos.annotation.Validate;
import mine.mythos.base.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Key(value = "role")
@Table(name = "sys_role")
@SuppressWarnings("serial")
public class SysRole extends BaseEntity {

	@Title(value = "角色名")
	@Component
	@Validate(required = true)
	@Column(name = "role_name")
	private String name;

	@Title(value = "角色标识")
	@Component
	@Validate(required = true, unique = true)
	@Column(name = "role_key")
	private String key;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<SysUser> users;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<SysResource> resources;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Set<SysUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	public Set<SysResource> getResources() {
		return resources;
	}

	public void setResources(Set<SysResource> resources) {
		this.resources = resources;
	}
}
