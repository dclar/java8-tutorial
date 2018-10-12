package hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Description:
 * <p>
 * Test for https://github.com/Netflix/Hystrix/wiki/How-To-Use
 *
 * @author dclar
 */
public class HystrixTest {

    @Test
    public void testObservable() throws Exception {

        Observable<String> fWorld = new CommandHelloWorld("World").observe();
        Observable<String> fBob = new CommandHelloWorld("Bob").observe();

        // blocking
//        assertEquals("Hello World!", fWorld.toBlockingObservable().single());
//        assertEquals("Hello Bob!", fBob.toBlockingObservable().single());

        // non-blocking
        // - this is a verbose anonymous inner-class approach and doesn't do assertions
        fWorld.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                // nothing needed here
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }


        });

        // non-blocking
        // - also verbose anonymous inner-class
        // - ignore errors and onCompleted signal
        fBob.subscribe(new Action1<String>() {

            @Override
            public void call(String v) {
                System.out.println("onNext: " + v);
                throw new RuntimeException();
            }

        });
    }


    class CommandHelloWorld extends HystrixObservableCommand<String> {

        private final String name;

        public CommandHelloWorld(String name) {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
            this.name = name;
        }

        @Override
        protected Observable<String> construct() {
            return Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> observer) {
                    try {
                        if (!observer.isUnsubscribed()) {
                            // a real example would do work like a network call here

                            observer.onNext("Hello");
                            observer.onNext(name + "!");

                            //observer.onCompleted();

                        }
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                }
            })
                    //.onErrorResumeNext(e -> Observable.just("errorResumeNext"))

                    .subscribeOn(Schedulers.io());
        }

        @Override
        public Observable<String> resumeWithFallback() {
            return Observable.just("Fallback");
        }

    }

    @Test
    public void testSynchronous() {
        assertEquals("Hello Failure World!", new CommandHelloFailure("World").execute());
        assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").execute());
    }
}
