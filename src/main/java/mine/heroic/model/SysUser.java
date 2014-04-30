package mine.heroic.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import mine.heroic.annotation.Component;
import mine.heroic.annotation.Key;
import mine.heroic.annotation.Title;
import mine.heroic.annotation.Validate;
import mine.heroic.common.BaseEntity;

@Entity
@Key(value = "user")
@Table(name = "sys_user")
@SuppressWarnings("serial")
public class SysUser extends BaseEntity {

	@Title(value = "用户标识")
	@Component
	@Validate(required = true, unique = true)
	@Column(name = "user_name")
	private String name;

	@Title(value = "密码")
	@Component
	@Validate(required = true, minLength = 6)
	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private Date email;

	@Column(name = "birthday")
	private String birthday;

	@Title(value = "赋予角色")
	@Component(type = Component.SELECTOR, url = "role/getAll.do")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<SysRole> roles;

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

	public Date getEmail() {
		return email;
	}

	public void setEmail(Date email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}
}
