package org.mule.extension.slack.internal;

import static org.mule.runtime.api.meta.Category.COMMUNITY;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

@Extension(name = "Slack Connector", vendor = "Esteban Wasinger", category = COMMUNITY)
@ConnectionProviders({TokenConnectionProvider.class, OAuth2ConnectionProvider.class})
@Operations(SlackOperations.class)
@ErrorTypes(SlackErrors.class)
@Xml(prefix = "slack")
public class SlackConnector {
}
