package hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Description:
 *
 * @author dclar
 */
public class CommandHelloFailure extends HystrixCommand<String> {

    private final String name;

    public CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        throw new RuntimeException("this command always fails");
    }

    /**
     * 发生Exception的场合，会调用此回调方法
     *
     * @return
     */
    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }


}
