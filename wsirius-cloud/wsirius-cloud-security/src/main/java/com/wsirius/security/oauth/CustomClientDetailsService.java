package com.wsirius.security.oauth;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsirius.security.domain.entity.Client;
import com.wsirius.security.domain.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.util.StringUtils;

/**
 * 自定义 ClientDetailsService
 *
 * @author bojiangzhou 2018/11/03
 */
public class CustomClientDetailsService implements ClientDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomClientDetailsService.class);

    private ClientService clientService;
    private OAuthProperties properties;

    public CustomClientDetailsService(ClientService clientService, OAuthProperties properties) {
        this.clientService = clientService;
        this.properties = properties;
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    @SuppressWarnings("unchecked")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = clientService.selectByClientId(clientId);
        if (client == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        CustomClientDetails clientDetails = new CustomClientDetails();
        clientDetails.setClientId(client.getClientId());
        clientDetails.setClientSecret(client.getClientSecret());
        clientDetails.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(client.getGrantTypes()));
        clientDetails.setResourceIds(StringUtils.commaDelimitedListToSet(client.getResourceIds()));
        clientDetails.setScope(StringUtils.commaDelimitedListToSet(client.getScope()));
        clientDetails.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet(client.getRedirectUris()));
        clientDetails.setAuthorities(Collections.emptyList());
        int accessTokenValiditySeconds = Optional
                .ofNullable(client.getAccessTokenValidity())
                .orElse(properties.getAccessTokenValiditySeconds());
        clientDetails.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
        int refreshTokenValiditySeconds = Optional
                .ofNullable(client.getRefreshTokenValidity())
                .orElse(properties.getRefreshTokenValiditySeconds());
        clientDetails.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
        clientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(client.getAutoApproveScopes()));
        String json = client.getAdditionalInformation();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(json)) {
            try {
                Map<String, Object> additionalInformation = mapper.readValue(json, Map.class);
                clientDetails.setAdditionalInformation(additionalInformation);
            } catch (Exception e) {
                LOGGER.warn("parser addition info error: {}", e);
            }
        }
        return clientDetails;
    }
}
