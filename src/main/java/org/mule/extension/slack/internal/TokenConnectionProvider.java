package org.mule.extension.slack.internal;

import static org.mule.runtime.api.connection.ConnectionValidationResult.success;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.http.api.HttpService;

import javax.inject.Inject;

public class TokenConnectionProvider implements ConnectionProvider<SlackConnection> {

    @Inject
    HttpService httpService;

    @Parameter
    private String token;


    @Override
    public SlackConnection connect() throws ConnectionException {
        return new SlackConnection(httpService, token);
    }

    @Override
    public void disconnect(SlackConnection slackConnection) {
        slackConnection.disconnect();
    }

    @Override
    public ConnectionValidationResult validate(SlackConnection s) {
        return success();
    }
}
