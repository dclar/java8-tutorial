import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author dclar
 */
public class Lambda {

    public static void main(String[] args) {


        Stream
                .of("x","y")
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);


    }
}
