package info.novatec.micronaut.camunda.bpm.feature.test;

import info.novatec.micronaut.camunda.bpm.feature.MicronautEmbeddedProcessEngineConfiguration;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lukasz Frankowski
 */
@MicronautTest
public class MicronautProcessEngineTransactionalConfigurationTest extends MicronautProcessEngineConfigurationTest {

	@Override @Test
	void testMicronautProcessEngineConfigurationClass() {
		assertEquals(MicronautEmbeddedProcessEngineConfiguration.class, micronautProcessEngineConfiguration.getClass());
	}
	
}
