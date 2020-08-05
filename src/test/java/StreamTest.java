import com.lucifiere.funtion.Function;
import com.lucifiere.funtion.Predicate;
import com.lucifiere.stream.Stream;
import com.lucifiere.stream.Streams;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public class StreamTest {

    @Test
    public void testStreams() {
        long numberGrThen1 = Streams.of("1", "2", "3").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 1;
            }
        }).count();
    }

    @Test
    public void testMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "xxx");
        map.put(2, "sss");
        Stream s = Streams.of(map).filter(new Predicate<Map.Entry<Integer, String>>() {
            @Override
            public boolean test(Map.Entry<Integer, String> integerStringEntry) {
                return integerStringEntry.getKey() > 1;
            }
        });
        System.out.println(s);
    }

}
