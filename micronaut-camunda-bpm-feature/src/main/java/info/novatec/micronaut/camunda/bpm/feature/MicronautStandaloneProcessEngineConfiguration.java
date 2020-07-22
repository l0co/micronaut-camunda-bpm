package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import javax.inject.Singleton;
import java.io.IOException;

/**
 * Standalone process engine configuration using own JDBC connection and transaction management.
 *
 * @author Lukasz Frankowski
 */
@Factory @Requires(missingBeans = SynchronousTransactionManager.class)
public class MicronautStandaloneProcessEngineConfiguration extends AbstractMicronautProcessEngineConfiguration {

	public MicronautStandaloneProcessEngineConfiguration(ApplicationContext applicationContext, Configuration configuration,
														 DatasourceConfiguration datasourceConfiguration,
														 ProcessEngineConfigurationCustomizer processEngineConfigurationCustomizer) {
		super(applicationContext, configuration, datasourceConfiguration, processEngineConfigurationCustomizer);
	}

	@Override
	protected void configurateProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration) {
		processEngineConfiguration
			.setJdbcUrl(datasourceConfiguration.getUrl())
			.setJdbcUsername(datasourceConfiguration.getUsername())
			.setJdbcPassword(datasourceConfiguration.getPassword())
			.setJdbcDriver(datasourceConfiguration.getDriverClassName());
	}

	@Override @Context @Requires(missingBeans = SynchronousTransactionManager.class)
	public ProcessEngine processEngine() throws IOException {
		return super.processEngine();
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public RuntimeService runtimeService(ProcessEngine processEngine) {
		return super.runtimeService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public RepositoryService repositoryService(ProcessEngine processEngine) {
		return super.repositoryService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public ManagementService managementService(ProcessEngine processEngine) {
		return super.managementService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public AuthorizationService authorizationService(ProcessEngine processEngine) {
		return super.authorizationService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public CaseService caseService(ProcessEngine processEngine) {
		return super.caseService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public DecisionService decisionService(ProcessEngine processEngine) {
		return super.decisionService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public ExternalTaskService externalTaskService(ProcessEngine processEngine) {
		return super.externalTaskService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public FilterService filterService(ProcessEngine processEngine) {
		return super.filterService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public FormService formService(ProcessEngine processEngine) {
		return super.formService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public TaskService taskService(ProcessEngine processEngine) {
		return super.taskService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public HistoryService historyService(ProcessEngine processEngine) {
		return super.historyService(processEngine);
	}

	@Override @Singleton @Requires(missingBeans = SynchronousTransactionManager.class)
	public IdentityService identityService(ProcessEngine processEngine) {
		return super.identityService(processEngine);
	}

}
