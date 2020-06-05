package ru.kpfu.itis.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CurrentUser extends User {
	public static final String ANONYMOUS_AUTHORITY = "ANONYMOUS";
	public static final List<GrantedAuthority> ANONYMOUS_AUTHORITIES =
			AuthorityUtils.createAuthorityList(CurrentUser.ANONYMOUS_AUTHORITY);
	private static final long serialVersionUID = 1299142021617783779L;

	private Long id;

	// As User class already implements builder() generated method should have another name
	@Builder(builderMethodName = "getBuilder")
	public CurrentUser(
			Long id,
			String username,
			boolean enabled,
			boolean accountNonLocked,
			boolean accountNonExpired,
			boolean credentialsNonExpired,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		setId(id);
	}
}