package mine.atlas.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mine.atlas.model.SysResource;
import mine.atlas.model.SysRole;
import mine.mythos.base.BaseClass;
import mine.mythos.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class SecurityMetadataSource extends BaseClass implements FilterInvocationSecurityMetadataSource {

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	@Autowired
	private BaseService<SysResource> baseService;

	private void loadResourceDefine() {
		if (resourceMap == null) {
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
		if (resourceMap == null) {
			loadResourceDefine();
		}
		for (String key : resourceMap.keySet()) {
			String regex = key.replaceAll("\\*\\*", "[\\\\w\\\\W]*"); // 匹配带**的URL
			if (requestUrl.matches(regex)) {
				return resourceMap.get(key);
			}
			regex = key = key.replaceAll("\\*", "((?!/).)*"); // 匹配带*的URL
			if (requestUrl.matches(regex)) {
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
