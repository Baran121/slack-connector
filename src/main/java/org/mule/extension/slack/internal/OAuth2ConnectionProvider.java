package org.mule.extension.slack.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.connectivity.oauth.AuthorizationCode;
import org.mule.runtime.extension.api.connectivity.oauth.AuthorizationCodeState;
import org.mule.runtime.http.api.HttpService;

import javax.inject.Inject;

@AuthorizationCode(accessTokenUrl = "https://slack.com/api/oauth.access",
        authorizationUrl = "https://slack.com/oauth/authorize",
        defaultScopes = "chat:write:user")
@Alias("oauth")
public class OAuth2ConnectionProvider implements ConnectionProvider<SlackConnection> {

    @Inject
    HttpService httpService;

    AuthorizationCodeState state;

    @Override
    public SlackConnection connect() throws ConnectionException {
        return new SlackConnection(httpService, state.getAccessToken());
    }

    @Override
    public void disconnect(SlackConnection connection) {

    }

    @Override
    public ConnectionValidationResult validate(SlackConnection connection) {
        return ConnectionValidationResult.success();
    }
}
