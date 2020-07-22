package info.novatec.micronaut.camunda.bpm.feature.test;

import info.novatec.micronaut.camunda.bpm.feature.AbstractMicronautProcessEngineConfiguration;
import info.novatec.micronaut.camunda.bpm.feature.MicronautEmbeddedProcessEngineConfiguration;
import io.micronaut.test.annotation.MicronautTest;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lukasz Frankowski
 */
@MicronautTest(transactional = false)
public class MicronautEmbeddedProcessEngineConfigurationTest extends MicronautProcessEngineConfigurationTest {

	@Inject protected TransactionalExecutor transactionalExecutor;

	@Override @Test
	void testDeploymentName() {
		assertEquals(AbstractMicronautProcessEngineConfiguration.MICRONAUT_AUTO_DEPLOYMENT_NAME,
			transactionalExecutor.doTransactionally(() -> repositoryService.createDeploymentQuery().singleResult().getName()));
	}


	@Override @Test
	void testMicronautProcessEngineConfigurationClass() {
		assertEquals(MicronautEmbeddedProcessEngineConfiguration.class, micronautProcessEngineConfiguration.getClass());
	}

	@Test
	void testEmptyProcessAccessNonTransactionally() {
		assertThrows(ProcessEngineException.class,
			() -> transactionalExecutor.doNonTransactionally(this::findProcessDefinition));
	}

	@Test
	void testEmptyProcessAccessTransactionally() {
		assertNotNull(transactionalExecutor.doTransactionally(this::findProcessDefinition));
	}

	@Test
	void testStartProcessWithCommit() {
		String processInstanceId = transactionalExecutor.doTransactionally(this::startProcess);
		// process has been finished with commit and we can find it from the history service
		assertNotNull(transactionalExecutor.doTransactionally(() -> findHistoricProcessById(processInstanceId)));
	}

	@Test
	void testStartProcessWithRollback() {
		try {
			transactionalExecutor.doTransactionally(() -> {
				throw new RuntimeException(startProcess());
			});
			fail();
		} catch (RuntimeException e) {
			// process has been finished but rollback happened and we cannot find it from the history service
			assertNull(transactionalExecutor.doTransactionally(() -> findHistoricProcessById(e.getMessage())));
			return;
		}
		fail();
	}

	protected ProcessDefinition findProcessDefinition() {
		return repositoryService.createProcessDefinitionQuery().processDefinitionKey("ProcessEmpty").latestVersion().singleResult();
	}

	protected String startProcess() {
		return runtimeService.startProcessInstanceById(findProcessDefinition().getId()).getId();
	}

	protected HistoricProcessInstance findHistoricProcessById(@Nonnull String id) {
		return historyService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
	}

}
