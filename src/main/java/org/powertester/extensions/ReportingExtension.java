package org.powertester.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.powertester.extensions.report.PublishResults;
import org.powertester.extensions.report.TestRunMetaData;

public class ReportingExtension implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        TestRunMetaData testRunMetaData = new TestRunMetaData().setBody(context);
        PublishResults.toElastic(testRunMetaData);
    }
}
