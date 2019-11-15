package com.wsirius.security.social.core;

import java.util.List;
import java.util.Set;
import javax.sql.DataSource;

import com.wsirius.security.social.exception.UserNotBindException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

/**
 * 定制化 JdbcUsersConnectionRepository，处理未绑定三方账户的情况。如果账号未绑定，抛出异常，通过这种方式禁止自动跳转到默认的注册页面。
 *
 * @author bojiangzhou 2018/10/28
 */
public class CustomJdbcUsersConnectionRepository extends JdbcUsersConnectionRepository {

    public CustomJdbcUsersConnectionRepository(DataSource dataSource, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        super(dataSource, connectionFactoryLocator, textEncryptor);
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        List<String> userIds = super.findUserIdsWithConnection(connection);

        if (CollectionUtils.isEmpty(userIds)) {
            throw new UserNotBindException("user.error.login.provider.not-bind");
        }

        return userIds;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        Set<String> userIds = super.findUserIdsConnectedTo(providerId, providerUserIds);

        if (CollectionUtils.isEmpty(userIds)) {
            throw new UserNotBindException("user.error.login.provider.not-bind");
        }

        return userIds;
    }
}
