package mine.heroic.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mine.heroic.common.service.BaseService;
import mine.heroic.model.SysResource;
import mine.heroic.model.SysRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Component;

@Component
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	@Autowired
	private BaseService<SysResource> baseService;

	public void loadResource(Boolean enforce) {
		if (resourceMap == null || enforce) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<SysResource> resources = baseService.findAll(SysResource.class);
			for (SysResource resource : resources) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				for (SysRole role : resource.getRoles()) {
					ConfigAttribute configAttribute = new SecurityConfig(role.getKey());
					configAttributes.add(configAttribute);
				}
				resourceMap.put(resource.getUrl(), configAttributes);
			}
		}
	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		loadResource(false);
		UrlMatcher urlMatcher = new AntUrlPathMatcher();
		for (String key : resourceMap.keySet()) {
			if (urlMatcher.pathMatchesUrl(key, requestUrl)) {
				return resourceMap.get(key);
			}
		}
		return resourceMap.get(requestUrl);
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return new ArrayList<ConfigAttribute>();
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}
}
