package mine.heroic.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mine.heroic.common.BaseClass;
import mine.heroic.common.BaseService;
import mine.heroic.model.SysRole;
import mine.heroic.model.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailService extends BaseClass implements UserDetailsService {

	@Autowired
	private BaseService<SysUser> baseService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SysUser> userList = baseService.findBy("name", username, SysUser.class);
		if (userList.size() > 0) {
			SysUser user = userList.get(0);
			User userdetail = new User(user.getName(), user.getPassword(), true, true, true, true, obtainGrantedAuthorities(user));
			return userdetail;
		} else {
			return null;
		}
	}

	private Set<GrantedAuthority> obtainGrantedAuthorities(SysUser user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Set<SysRole> roles = user.getRoles();
		for (SysRole role : roles) {
			authorities.add(new GrantedAuthorityImpl(role.getKey()));
		}
		return authorities;
	}
}
