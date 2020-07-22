package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.annotation.Factory;
import org.camunda.bpm.engine.*;

import javax.inject.Singleton;

/**
 * Camunda beans moved outside conditional {@link AbstractMicronautProcessEngineConfiguration}.
 *
 * @author Tobias Schäfer
 * @author Lukasz Frankowski
 */
@Factory
public class CamundaBeansFactory {

	/**
	 * Creates a bean for the {@link RuntimeService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link RuntimeService}
	 */
	@Singleton public RuntimeService runtimeService(ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}

	/**
	 * Creates a bean for the {@link RepositoryService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link RepositoryService}
	 */
	@Singleton public RepositoryService repositoryService(ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}

	/**
	 * Creates a bean for the {@link ManagementService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link ManagementService}
	 */
	@Singleton public ManagementService managementService(ProcessEngine processEngine) {
		return processEngine.getManagementService();
	}

	/**
	 * Creates a bean for the {@link AuthorizationService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link AuthorizationService}
	 */
	@Singleton public AuthorizationService authorizationService(ProcessEngine processEngine) {
		return processEngine.getAuthorizationService();
	}

	/**
	 * Creates a bean for the {@link CaseService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link CaseService}
	 */
	@Singleton public CaseService caseService(ProcessEngine processEngine) {
		return processEngine.getCaseService();
	}

	/**
	 * Creates a bean for the {@link DecisionService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link DecisionService}
	 */
	@Singleton public DecisionService decisionService(ProcessEngine processEngine) {
		return processEngine.getDecisionService();
	}

	/**
	 * Creates a bean for the {@link ExternalTaskService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link ExternalTaskService}
	 */
	@Singleton public ExternalTaskService externalTaskService(ProcessEngine processEngine) {
		return processEngine.getExternalTaskService();
	}

	/**
	 * Creates a bean for the {@link FilterService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link FilterService}
	 */
	@Singleton public FilterService filterService(ProcessEngine processEngine) {
		return processEngine.getFilterService();
	}

	/**
	 * Creates a bean for the {@link FormService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link FormService}
	 */
	@Singleton public FormService formService(ProcessEngine processEngine) {
		return processEngine.getFormService();
	}

	/**
	 * Creates a bean for the {@link TaskService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link TaskService}
	 */
	@Singleton public TaskService taskService(ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}

	/**
	 * Creates a bean for the {@link HistoryService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link HistoryService}
	 */
	@Singleton public HistoryService historyService(ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}

	/**
	 * Creates a bean for the {@link IdentityService} in the application context which can be injected if needed.
	 *
	 * @param processEngine the {@link ProcessEngine}
	 * @return the {@link IdentityService}
	 */
	@Singleton public IdentityService identityService(ProcessEngine processEngine) {
		return processEngine.getIdentityService();
	}

}
