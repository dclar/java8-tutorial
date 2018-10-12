import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.BlockingObservable;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 * <p>
 * 参考：https://developer.couchbase.com/documentation/server/3.x/developer/java-2.0/observables.html
 * 实验Rx的各种接口特性
 *
 * @author dclar
 */
public class Rx {

    public static void main(String[] args) throws InterruptedException {

        /*
        Observable
                .just(1, 2, 3)
                .subscribe(
                        (integer) -> System.out.println("Got : " + integer + " Thread name : " + Thread.currentThread().getName()),
                        (t) -> System.out.println(t.getMessage()),
                        () -> System.out.println("Completed Observable." + Thread.currentThread().getName())
                );
         */
//                        new Subscriber<Integer>() {
//        @Override
//        public void onCompleted() {
//            System.out.println("Completed Observable.");
//        }
//
//        @Override
//        public void onError(Throwable throwable) {
//            System.err.println("Whoops: " + throwable.getMessage());
//        }
//
//        @Override
//        public void onNext(Integer integer) {
//            System.out.println("Got: " + integer);
//        }
//    });

        /**
         * 无法看到打印结果，因为main线程先一步执行完毕
         */
//        Observable
//                .interval(1, TimeUnit.SECONDS)
//                .subscribe(counter -> System.out.println("Got: " + counter + " Thread: " + Thread.currentThread().getName()));

        /**
         * 为了看到打印，使用countdown的lock机制
         */
//        final CountDownLatch latch = new CountDownLatch(5);
//        Observable
//                .interval(1, TimeUnit.SECONDS)
//                .subscribe(counter -> {
//                    latch.countDown();
//                    System.out.println("Got: " + counter + " Thread: " + Thread.currentThread().getName());
//                });
//
//        latch.await();


        /**
         * block的调用方法
         */
        // This does not block.
//        BlockingObservable<Long> observable = Observable
//                .interval(1, TimeUnit.SECONDS)
//                .toBlocking();
//
//        // This blocks and is called for every emitted item.
//        observable.forEach(counter -> System.out.println("Got: " + counter + " Thread: " + Thread.currentThread().getName()));


        /**
         * Observables的create方法
         */
//        Observable.create((Observable.OnSubscribe<Integer>) subscriber -> {
//            try {
//                if (!subscriber.isUnsubscribed()) {
//                    for (int i = 0; i < 5; i++) {
//                        subscriber.onNext(i);
//                    }
//                    subscriber.onCompleted();
//                }
//            } catch (Exception ex) {
//                subscriber.onError(ex);
//            }
//        }).subscribe(integer -> System.out.println("Got: " + integer));


        /**
         *Transforming Observables
         */
//        Observable
//                .interval(10, TimeUnit.MILLISECONDS)
//                .take(20)
//                .map(new Func1<Long, String>() {
//                    @Override
//                    public String call(Long input) {
//                        if (input % 3 == 0) {
//                            return "Fizz" + " " + input;
//                        } else if (input % 5 == 0) {
//                            return "Buzz";
//                        }
//                        return Long.toString(input);
//                    }
//                })
//                .toBlocking()
//                .forEach(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s + " Thread: " + Thread.currentThread().getName());
//                    }
//                });


//        Observable
//                .just(1, 2, 3, 4, 5)
//                .scan(new Func2<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer sum, Integer value) {
//                        return sum + value;
//                    }
//                }).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println("Sum: " + integer);
//            }
//        });


//        Observable
//                .just(1, 2, 3, 4, 5)
//                .groupBy(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer % 2 == 0;
//                    }
//                }).subscribe(new Action1<GroupedObservable<Boolean, Integer>>() {
//            @Override
//            public void call(GroupedObservable<Boolean, Integer> grouped) {
//                grouped.toList().subscribe(new Action1<List<Integer>>() {
//                    @Override
//                    public void call(List<Integer> integers) {
//                        System.out.println(integers + " (Even: " + grouped.getKey() + ")");
//                    }
//                });
//            }
//        });


        /**
         * Filtering Observables
         */
        // This will only let 3 and 4 pass.
//        Observable
//                .just(1, 2, 3, 4)
//                .filter(integer -> integer > 2)
//                .subscribe(System.out::println); // 打印出filter后的结果
//
//        // Only 1 and 2 will pass.
//        Observable
//                .just(1, 2, 3, 4)
//                .take(2)
//                .subscribe(System.out::println);
//
//
//        // Only 1 will pass
//        Observable
//                .just(1, 2, 3, 4)
//                .first()
//                .subscribe();
//
//
//        // Only 4 will pass
//        Observable
//                .just(1, 2, 3, 4)
//                .last()
//                .subscribe();
//
//        // 1, 2, 3, 4 will be emitted
//        Observable
//                .just(1, 2, 1, 3, 4, 2)
//                .distinct()
//                .subscribe();


        /**
         * Combining Observables
         */
//        Observable
//                .merge(
//                        Observable.just(2,4,6),
//                        Observable.just(1,3,5)
//                )
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });

//        Observable<Integer> evens = Observable.just(2, 4, 6, 8, 10);
//        Observable<Integer> odds = Observable.just(1, 3, 5, 7, 9);
//
//        Observable
//                .zip(
//                        evens,
//                        odds,
//                        (v1, v2) -> v1
//                                + " + "
//                                + v2
//                                + " is: "
//                                + (v1 + v2))
//                .subscribe(System.out::println);


        /**
         * Error Handling
         */
        // Prints:
        // Default
        // Oops: I don't like: Apples
//        Observable
//                .just("Apples", "Bananas")
//                .doOnNext(s -> {
//                    throw new RuntimeException("I don't like: " + s);
//                })
//
//                // 发现错误的场合直接返回，不会继续打印Bananas
//                /*
//                .onErrorReturn(throwable -> {
//                    System.err.println("Oops: " + throwable.getMessage());
//                    return "Default";
//                })
//                */
//
//                .onErrorResumeNext(throwable -> {
//                    if (throwable instanceof TimeoutException) {
//                        return Observable.just("exception");
//                    }
//                    return Observable.just("exception's apple", "Bananas");
//                })
//
//                .subscribe(System.out::println);

        /**
         * Retry
         */
//        Observable
//                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//                .doOnNext(integer -> {
//                    if (new Random().nextInt(10) + 1 == 5) {
//                        System.out.println("exception");
//                        throw new RuntimeException("Boo!");
//                    }
//                })
//                .retry() // 实现重试
//                .distinct()
//                .subscribe(System.out::println);

        /**
         * Retry backoff
         */
//        Observable
//                .range(1, 10)
//                .doOnNext(integer -> {
//                    if (new Random().nextInt(10) + 1 == 5) {
//                        System.out.println("Boo exception");
//                        throw new RuntimeException("Boo!");
//                    }
//                })
//                .retryWhen(attempts ->
//                        attempts.zipWith(Observable.range(1, 3), (e, i) -> i)
//                                .flatMap(i -> {
//                                    System.out.println("delay retry by " + i + " second(s)");
//                                    return Observable.timer(i, TimeUnit.SECONDS);
//                                }))
//                //.distinct()
//                .toBlocking() // TODO 有个问题不实用toBlocking的时候貌似中间就会停止
//                .subscribe(System.out::println);


        /**
         * Schedule Threads
         */

        /* Will print like :
        Map: (main)
        Got: 3 (main)
        Map: (main)
        Got: 4 (main)
        Map: (main)
        Got: 5 (main)
        Map: (main)
        Got: 6 (main)
        Map: (main)
        Got: 7 (main)
         */
//        Observable
//                .range(1, 5)
//                .map(integer -> {
//                    System.out.println("Map: (" + Thread.currentThread().getName() + ")");
//                    return integer + 2;
//                })
//                .subscribe(integer ->
//                        System.out.println("Got: " + integer + " (" + Thread.currentThread().getName() + ")")
//                );

        /**
         * 在新建线程中运行
         */
        /* Will print like :
        Map: (RxComputationScheduler-1)
        Got: 3 (RxComputationScheduler-1)
        Map: (RxComputationScheduler-1)
        Got: 4 (RxComputationScheduler-1)
        Map: (RxComputationScheduler-1)
        Got: 5 (RxComputationScheduler-1)
        Map: (RxComputationScheduler-1)
        Got: 6 (RxComputationScheduler-1)
        Map: (RxComputationScheduler-1)
        Got: 7 (RxComputationScheduler-1)
         */
//        final CountDownLatch latch = new CountDownLatch(5);
//        Observable
//                .range(1, 5)
//                .map(integer -> {
//                    System.out.println("Map: (" + Thread.currentThread().getName() + ")");
//                    return integer + 2;
//                })
//                .subscribeOn(Schedulers.computation()) // 所有任务在新建线程中运行
//                .subscribe(integer -> {
//                            System.out.println("Got: " + integer + " (" + Thread.currentThread().getName() + ")");
//                            latch.countDown(); // 不实用CountDownLatch，main线程会先结束，我们看不到任何打印结果
//                        }
//                );
//        latch.await();

        /**
         * 线程在各自的pool中运行
         *
         * 这种场景下，main线程结束后也不会直接结束，会等待Scheduler的线程执行完毕，故不需要使用CountdownLatch来计算CountDown
         */
//        Observable
//                .range(1, 5)
//                .map(integer -> {
//                    System.out.println("Map: (" + Thread.currentThread().getName() + ")");
//                    return integer + 2;
//                })
//                .observeOn(Schedulers.computation())
//                .subscribe(integer ->
//                        System.out.println("Got: " + integer + " (" + Thread.currentThread().getName() + ")")
//                )
//
//        ;
//        System.out.println("Main executed");


        /**
         * 实验一个来回来去On的例子
         */
        Observable
                .range(1, 5)
                //.subscribeOn(Schedulers.computation())
                //.observeOn(Schedulers.computation())
//                .map(integer -> {
//                    System.out.println("Map: (" + Thread.currentThread().getName() + ")");
//                    return integer + 2;
//                })
                .flatMap(t -> {
                    System.out.println("Yes");
                    return Observable.just("ooyeah");

                })
                .observeOn(Schedulers.computation())
                .subscribe(integer ->
                        System.out.println("Got: " + integer + " (" + Thread.currentThread().getName() + ")")
                );
        System.out.println("Main executed");

        Thread.sleep(1000*6);

    }


}
