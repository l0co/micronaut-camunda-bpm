package info.novatec.micronaut.camunda.bpm.feature.test;

import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.function.Supplier;

/**
 * @author Lukasz Frankowski
 */
@Singleton
public class TransactionalExecutor {

	public <R> R doNonTransactionally(@Nonnull Supplier<R> s) {
		return s.get();
	}

	@TransactionalAdvice
	public <R> R doTransactionally(@Nonnull Supplier<R> s) {
		return s.get();
	}

}
