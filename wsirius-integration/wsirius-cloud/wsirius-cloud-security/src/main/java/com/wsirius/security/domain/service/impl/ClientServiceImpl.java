package com.wsirius.security.domain.service.impl;

import com.wsirius.core.base.BaseService;
import com.wsirius.security.domain.entity.Client;
import com.wsirius.security.domain.service.ClientService;
import org.springframework.stereotype.Service;

/**
 * ClientService
 *
 * @author bojiangzhou 2018/11/03
 */
@Service
public class ClientServiceImpl extends BaseService<Client> implements ClientService {

    @Override
    public Client selectByClientId(String clientId) {
        return select(Client.FIELD_CLIENT_ID, clientId);
    }
}
