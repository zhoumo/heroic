package mine.heroic.model;

import java.util.List;

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
import mine.heroic.common.annotation.Validate;

@Entity
@Key(value = "resource")
@Table(name = "sys_resource")
@SuppressWarnings("serial")
public class SysResource extends BaseEntity {

	public static final String TYPE_MENU = "MENU";

	public static final String TYPE_REQUEST = "REQUEST";

	@Component
	@Validate(required = true)
	@Column(name = "resource_name")
	private String name;

	@Component
	@Validate(required = true, unique = true)
	@Column(name = "resource_key")
	private String key;

	@Component(type = Component.SELECT, text = { "菜单", "请求" }, value = { "MENU", "REQUEST" })
	@Validate(required = true)
	@Column(name = "resource_type")
	private String type;

	@Component(type = Component.SELECT, url = "getParentResource.do", text = "name", value = "id")
	@Validate(required = true)
	@Column(name = "parent_id")
	private Long parentId;

	@Component
	@Validate(required = true, unique = true)
	@Column(name = "resource_url")
	private String url;

	@Component(type = Component.SELECTOR, url = "role/getAll.do")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "sys_resource_role", joinColumns = { @JoinColumn(name = "resource_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private List<SysRole> roles;

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

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
}
