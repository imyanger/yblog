package com.yanger.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

@Component
public class ShiroRealm extends AuthorizingRealm {

//	@Autowired
//	private UserMapper userMapper;
//
//	@Autowired
//	private MenuService menuService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取权限菜单
		//Set<String> set = menuService.listPerms(SubjectUtils.getUserId());
		//info.setStringPermissions(set);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		//更具用户名和密码查找用户
		String username = upToken.getUsername();
		//未加密，还需进行加密
		String password = new String((char[]) upToken.getCredentials());
		Object user = null;
//		// 账号不存在
//		if (user == null) {
//			throw new UnknownAccountException("账号或密码不正确");
//		}
//		// 密码错误
//		if (!password.equals(user.getLoginPassword())) {
//			throw new IncorrectCredentialsException("账号或密码不正确");
//
//		// 账号被锁定
//		if ("0".equals(user.getLocked())) {
//			throw new LockedAccountException("账号已被锁定,请联系管理员");
//		}
		// 盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, credentialsSalt, getName());
		return info;
	}

}
