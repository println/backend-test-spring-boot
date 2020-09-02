package zup.hiring.debtcollector;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DebtCollectorApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTest {
}
