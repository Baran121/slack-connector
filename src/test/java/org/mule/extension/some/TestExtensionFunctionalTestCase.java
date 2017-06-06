package org.mule.extension.some;

import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

import org.junit.Test;

public class TestExtensionFunctionalTestCase extends MuleArtifactFunctionalTestCase {

    @Override
    protected String getConfigFile() {
        return "test-extension-mule-app.xml";
    }

    @Test
    public void postMessage() throws Exception {
        flowRunner("postMessage").run();
    }

    @Test
    public void listChannels() throws Exception {
        flowRunner("listChannels").run();
    }
}
