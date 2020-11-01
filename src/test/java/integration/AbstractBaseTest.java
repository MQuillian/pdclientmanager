package integration;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pdclientmanager.config.DataPersistenceConfigTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
      classes = {DataPersistenceConfigTest.class})
public abstract class AbstractBaseTest {

    // Creates a docker container to be shared by subclasses to minimize recreating containers
    protected boolean checkIfProjection(final Object testObject) {
        String className = testObject.getClass().getName();
        return className.contains("com.sun.proxy");
    }
    
    BiPredicate<Object, Class<Object>> checkClass = (obj, expectedClass) ->
        obj.getClass().equals(expectedClass);
}
