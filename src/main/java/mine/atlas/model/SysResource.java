package mine.atlas.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import mine.mythos.annotation.Component;
import mine.mythos.annotation.Key;
import mine.mythos.annotation.Title;
import mine.mythos.annotation.Validate;
import mine.mythos.base.BaseEntity;

@Entity
@Key(value = "resource")
@Table(name = "sys_resource")
@SuppressWarnings("serial")
public class SysResource extends BaseEntity {

	@Title(value = "资源名")
	@Component
	@Validate(required = true)
	@Column(name = "resource_name")
	private String name;

	@Title(value = "资源标识")
	@Component
	@Validate(required = true, unique = true)
	@Column(name = "resource_key")
	private String key;

	@Title(value = "隶属上级")
	@Component
	@Validate(required = true)
	@Column(name = "parent_id")
	private Long parentId;

	@Title(value = "资源URL")
	@Component
	@Validate(required = true, unique = true)
	@Column(name = "resource_url")
	private String url;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "sys_resource_role", joinColumns = { @JoinColumn(name = "resource_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<SysRole> roles;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}
}
