package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

/**
 * Standalone process engine configuration using own JDBC connection and transaction management.
 *
 * @author Lukasz Frankowski
 */
@Factory
public class MicronautStandaloneProcessEngineConfiguration extends AbstractMicronautProcessEngineConfiguration {

	public MicronautStandaloneProcessEngineConfiguration(ApplicationContext applicationContext, Configuration configuration,
														 DatasourceConfiguration datasourceConfiguration,
														 ProcessEngineConfigurationCustomizer processEngineConfigurationCustomizer) {
		super(applicationContext, configuration, datasourceConfiguration, processEngineConfigurationCustomizer);
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
