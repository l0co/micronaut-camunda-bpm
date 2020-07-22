package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Embedded process engine configuration using Micronaut-managed {@link DataSource} and {@link SynchronousTransactionManager}.
 *
 * @author Lukasz Frankowski
 */
@Factory @Requires(beans = SynchronousTransactionManager.class)
public class MicronautEmbeddedProcessEngineConfiguration extends AbstractMicronautProcessEngineConfiguration {

	protected DataSource dataSource;
	protected SynchronousTransactionManager<?> transactionManager;

	public MicronautEmbeddedProcessEngineConfiguration(ApplicationContext applicationContext, Configuration configuration,
													   DatasourceConfiguration datasourceConfiguration,
													   ProcessEngineConfigurationCustomizer processEngineConfigurationCustomizer,
													   DataSource dataSource,
													   SynchronousTransactionManager<?> transactionManager) {
		super(applicationContext, configuration, datasourceConfiguration, processEngineConfigurationCustomizer);
		this.dataSource = dataSource;
		this.transactionManager = transactionManager;
	}

	@Override
	protected void configurateProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration) {
		processEngineConfiguration
			.setDataSource(dataSource)
			.setTransactionsExternallyManaged(true);
	}

	@Override
	protected ProcessEngine buildProcessEngine(ProcessEngineConfigurationImpl processEngineConfiguration) throws IOException {
		return transactionManager.executeWrite(status -> super.buildProcessEngine(processEngineConfiguration));
	}

	@Override @Context @Requires(beans = SynchronousTransactionManager.class)
	public ProcessEngine processEngine() throws IOException {
		return super.processEngine();
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public RuntimeService runtimeService(ProcessEngine processEngine) {
		return super.runtimeService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public RepositoryService repositoryService(ProcessEngine processEngine) {
		return super.repositoryService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public ManagementService managementService(ProcessEngine processEngine) {
		return super.managementService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public AuthorizationService authorizationService(ProcessEngine processEngine) {
		return super.authorizationService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public CaseService caseService(ProcessEngine processEngine) {
		return super.caseService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public DecisionService decisionService(ProcessEngine processEngine) {
		return super.decisionService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public ExternalTaskService externalTaskService(ProcessEngine processEngine) {
		return super.externalTaskService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public FilterService filterService(ProcessEngine processEngine) {
		return super.filterService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public FormService formService(ProcessEngine processEngine) {
		return super.formService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public TaskService taskService(ProcessEngine processEngine) {
		return super.taskService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public HistoryService historyService(ProcessEngine processEngine) {
		return super.historyService(processEngine);
	}

	@Override @Singleton @Requires(beans = SynchronousTransactionManager.class)
	public IdentityService identityService(ProcessEngine processEngine) {
		return super.identityService(processEngine);
	}

}
