package mine.heroic.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AccessDecisionManager implements org.springframework.security.access.AccessDecisionManager {

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			String permission = configAttribute.getAttribute();
			for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				if (permission.equals(grantedAuthority.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("Access is denied!");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
