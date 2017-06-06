package org.mule.extension.slack.internal;

import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.Optional;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

/**
 * test-extension-1
 *
 * @author Esteban Wasinger (http://github.com/estebanwasinger)
 */
public class SlackOperations {

    public InputStream postMessage(@Connection SlackConnection slackConnection,
                                   @Content(primary = true) String message,
                                   String channel,
                                   @Content @Optional InputStream attachments,
                                   @Optional String username,
                                   @Optional(defaultValue = "false") boolean asUser) throws IOException, TimeoutException {
        return slackConnection.postMessage(message, channel, attachments, username, asUser);
    }

    public InputStream listChannels(@Connection SlackConnection slackConnection) throws IOException, TimeoutException {
        return slackConnection.listChannels();
    }
}
