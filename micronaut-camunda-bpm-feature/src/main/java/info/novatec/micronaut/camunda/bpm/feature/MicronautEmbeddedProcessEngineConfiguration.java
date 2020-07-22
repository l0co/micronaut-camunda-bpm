package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

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
}
