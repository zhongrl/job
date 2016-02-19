package cn.xn.job.console.shiro.realm;

import cn.xn.freamwork.common.ShiroConstants;
import cn.xn.freamwork.util.DateUtils;
import cn.xn.job.console.model.UserInfo;
import cn.xn.job.console.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class UserRealm extends AuthorizingRealm
{

    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        Map<String, Object> userMap = new HashMap<String, Object>();
        SimpleAuthenticationInfo authenticationInfo = null;

        String username = (String) token.getPrincipal();

        //CaptchaToken tokenContent = (CaptchaToken) token;
        UserInfo user = userService.findInfoAllByUser(username);

        if(user == null) {
            throw new UnknownAccountException();//娌℃壘鍒板笎鍙�
        }

        userMap.put(ShiroConstants.KEY_SHIRO_USER_INFO, user);
        userMap.put(ShiroConstants.KEY_SHIRO_UID, user.getUserId());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_NAME, user.getUserLoginName());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_AS, user.getUserAs());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_EMAIL, user.getEmail() == null ? "":user.getEmail());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_PHONE, user.getPhoneNumber() == null ? "":user.getPhoneNumber());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_REALNAME,
                user.getIsCertification() == null ? 0:user.getIsCertification());
        userMap.put(ShiroConstants.KEY_SHIRO_QUESTION,
                user.getIsQuestion() == null ? 0:user.getIsQuestion());
        userMap.put(ShiroConstants.KEY_SHIRO_IS_PAYMENTPWD, user.getIsPaymentPwd());

        userMap.put(ShiroConstants.KEY_SHIRO_LOGIN_TIME,
                DateUtils.format(user.getLaseLogintime(), DateUtils.FORMAT_TIME));

        authenticationInfo = new SimpleAuthenticationInfo(
                userMap, //用户信息
                user.getLoginPwd(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
