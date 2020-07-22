package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Context;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Tobias Schäfer
 */
public abstract class AbstractMicronautProcessEngineConfiguration {

    public static final String MICRONAUT_AUTO_DEPLOYMENT_NAME = "MicronautAutoDeployment";

    protected static final Logger log = LoggerFactory.getLogger(AbstractMicronautProcessEngineConfiguration.class);

    protected final ApplicationContext applicationContext;

    protected final Configuration configuration;

    protected final DatasourceConfiguration datasourceConfiguration;

    protected final ProcessEngineConfigurationCustomizer processEngineConfigurationCustomizer;

    public AbstractMicronautProcessEngineConfiguration(ApplicationContext applicationContext, Configuration configuration,
                                                       DatasourceConfiguration datasourceConfiguration,
                                                       ProcessEngineConfigurationCustomizer processEngineConfigurationCustomizer) {
        this.applicationContext = applicationContext;
        this.configuration = configuration;
        this.datasourceConfiguration = datasourceConfiguration;
        this.processEngineConfigurationCustomizer = processEngineConfigurationCustomizer;
    }

    protected abstract void configurateProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration);

    protected ProcessEngine buildProcessEngine(ProcessEngineConfigurationImpl processEngineConfiguration) throws IOException {
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        log.info("Successfully created process engine which is connected to database {}", datasourceConfiguration.getUrl());

        deployProcessModels(processEngine);

        return processEngine;
    }

    /**
     * The {@link ProcessEngine} is started with the application start so that the task scheduler is started immediately.
     *
     * @return the initialized {@link ProcessEngine} in the application context.
     * @throws IOException if a resource, i.e. a model, cannot be loaded.
     */
    @Context
    public ProcessEngine processEngine() throws IOException {
        ProcessEngineConfigurationImpl processEngineConfiguration = new StandaloneProcessEngineConfiguration() {
            @Override
            public HistoryLevel getDefaultHistoryLevel() {
                // Define default history level for history level "auto".
                return HistoryLevel.HISTORY_LEVEL_FULL;
            }
        }
            .setDatabaseSchemaUpdate(configuration.getDatabase().getSchemaUpdate())
            .setHistory(configuration.getHistoryLevel())
            .setJobExecutorActivate(true)
            .setExpressionManager(new MicronautExpressionManager(new ApplicationContextElResolver(applicationContext)));

        configurateProcessEngineConfiguration(processEngineConfiguration);
        processEngineConfigurationCustomizer.customize(processEngineConfiguration);
        
        return buildProcessEngine(processEngineConfiguration);
    }

    /**
     * Deploys all process models found in root directory of the resources.
     * <p>
     * Note: Currently this is not recursive!
     *
     * @param processEngine the {@link ProcessEngine}
     * @throws IOException if a resource, i.e. a model, cannot be loaded.
     */
    private void deployProcessModels(ProcessEngine processEngine) throws IOException {
        log.info("Searching non-recursively for models in the resources");
        PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        // Order of extensions has been chosen as a best fit for inter process dependencies.
        for (String extension : Arrays.asList("dmn", "cmmn", "bpmn")) {
            for (Resource resource : resourceLoader.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "*." + extension)) {
                log.info("Deploying model: {}", resource.getFilename());
                processEngine.getRepositoryService().createDeployment()
                        .name(MICRONAUT_AUTO_DEPLOYMENT_NAME)
                        .addInputStream(resource.getFilename(), resource.getInputStream())
                        .enableDuplicateFiltering(true)
                        .deploy();
            }
        }
    }

}
