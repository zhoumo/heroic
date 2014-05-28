package mine.heroic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import mine.heroic.common.BaseEntity;
import mine.heroic.common.annotation.Component;
import mine.heroic.common.annotation.Key;
import mine.heroic.common.annotation.Title;
import mine.heroic.common.annotation.Validate;

@Entity
@Key(value = "resource")
@Table(name = "sys_resource")
@SuppressWarnings("serial")
public class SysResource extends BaseEntity {

	public static final String TYPE_MENU = "MENU";

	public static final String TYPE_REQUEST = "REQUEST";

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

	@Title(value = "资源类型")
	@Component(type = Component.SELECT, text = { "菜单", "请求" }, value = { "MENU", "REQUEST" })
	@Validate(required = true)
	@Column(name = "resource_type")
	private String type;

	@Title(value = "隶属上级", show = false)
	@Component(type = Component.SELECT, url = "getParentResource.do", text = "name", value = "id")
	@Validate(required = true)
	@Column(name = "parent_id")
	private Long parentId;

	@Title(value = "资源URL")
	@Component
	@Validate(required = true, unique = true)
	@Column(name = "resource_url")
	private String url;

	@Title(value = "赋予角色")
	@Component(type = Component.SELECTOR, url = "role/getAll.do")
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
