import net.pushq.soccero.data.Provider;
import net.pushq.soccero.domain.StatsRecord;
import net.pushq.soccero.ranking.lem.Calculator;
import org.junit.Test;

import java.util.List;

public class TestMakeRanking {

    @Test
    public void make() {
        Provider provider = new Provider();

        Calculator calculator = new Calculator(provider.activeGames());
        List<StatsRecord> results = calculator.calculate();
        results.forEach(r -> System.out.println(r.getName() + "-" + r.getRatio()));
    }
}
