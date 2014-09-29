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
@Key(value = "user")
@Table(name = "sys_user")
@SuppressWarnings("serial")
public class SysUser extends BaseEntity {

	@Component
	@Validate(required = true, unique = true)
	@Column(name = "user_name")
	private String name;

	@Component
	@Validate(required = true, minLength = 6)
	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "birthday")
	private String birthday;

	@Component(type = Component.SELECTOR, url = "role/getAll.do")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private List<SysRole> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
}
