package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

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
	
}
