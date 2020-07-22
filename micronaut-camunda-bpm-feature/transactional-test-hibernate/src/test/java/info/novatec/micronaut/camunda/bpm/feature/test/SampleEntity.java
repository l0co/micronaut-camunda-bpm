package info.novatec.micronaut.camunda.bpm.feature.test;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Sample entity is required by the micronaut-hibernate to startup everything properly.
 *
 * @author Lukasz Frankowski
 */
@Entity
public class SampleEntity {

	@Id
	protected long id;

}
