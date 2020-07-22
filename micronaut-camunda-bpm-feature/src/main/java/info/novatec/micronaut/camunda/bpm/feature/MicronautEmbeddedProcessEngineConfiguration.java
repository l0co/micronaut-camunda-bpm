package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.ApplicationContext;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import javax.sql.DataSource;

/**
 * Embedded process engine configuration using Micronaut-managed {@link DataSource} and {@link SynchronousTransactionManager}.
 *
 * @author Lukasz Frankowski
 */
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
			.setDatabaseSchemaUpdate(configuration.getDatabase().getSchemaUpdate())
			.setJdbcUrl(datasourceConfiguration.getUrl())
			.setJdbcUsername(datasourceConfiguration.getUsername())
			.setJdbcPassword(datasourceConfiguration.getPassword())
			.setJdbcDriver(datasourceConfiguration.getDriverClassName());
	}
	
}
