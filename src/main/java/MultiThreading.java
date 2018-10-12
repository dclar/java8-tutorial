/**
 * Description:
 *
 * @author dclar
 */


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

/**
 * Description:
 * <p>
 * Sample from https://winterbe.com/posts/2015/04/30/java8-concurrency-tutorial-synchronized-locks-examples/
 *
 * @author dclar
 */
public class MultiThreading {


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");


        stringCollection.stream().sorted()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
        //stringCollection.stream().sorted().filter((s) -> s.startsWith("a")).forEach(ServiceBImpl::consume);

        System.out.println();

        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder()).reduce((s1, s2) -> s1 + "#" + s2);
                //.forEach(System.out::println);



        /*
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }


        long t0 = System.nanoTime();

        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));



        long t0 = System.nanoTime();

        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
*/

        /*
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!");
        */

        /*
        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Foo " + name);
                TimeUnit.SECONDS.sleep(1); // same as Thread.sleep(1000)
                System.out.println("Bar " + name);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        */

        /* 采用ExecutorService方式
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
            System.exit(0);// 否则线程不退出
        });

        // 由于Executor不会被shutdown
        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
        */

        /*
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("++++ Print Thread Name: " + Thread.currentThread().getName());
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future = executor.submit(task);

        System.out.println("future done? " + future.isDone());

        Integer result = future.get();// 这个方法是阻塞式的，如果线程任务没有完成，则会阻塞等待

        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);
        */


        /*
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
        */


        /*
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        //TimeUnit.MILLISECONDS.sleep(1337);
        long remainingDelay = 1L;
        while (remainingDelay > 0) {
            remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
            System.out.printf("Remaining Delay: %sms", remainingDelay);
            System.out.println();
            Thread.currentThread().sleep(200);
        }
        */


        /*
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ServiceBImpl serviceB = new ServiceBImpl();

        IntStream.range(0, 10000).forEach(i ->
                executor.submit(serviceB::increment)
        );

        stop(executor);

        System.out.println(serviceB.count);
        */


        /*
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();


        executor.submit(() -> {
            lock.lock();
            try {
                sleep(1);
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        stop(executor);
        */


        /**
         * ReadWriteLock
         */
        /*
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
//        ReentrantLock lock = new ReentrantLock();

        executor.submit(() -> {
            lock.writeLock().lock();
//            lock.lock();

            try {
                System.out.println("Will sleep for 1 second...");
                sleep(1);
                System.out.println("Wake up!");
                map.put("foo", "bar");
            } finally {
                lock.writeLock().unlock();
//                lock.unlock();
            }
        });
        Runnable readTask = () -> {
            lock.readLock().lock();
//            lock.lock();
            try {
                System.out.println(map.get("foo"));
                sleep(1);
            } finally {
                lock.readLock().unlock();
//                lock.unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);
        */


        /**
         * StampedLock
         */
        /*
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(1);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(2);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
            } finally {
                lock.unlock(stamp);
            }
        });

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("Write Lock acquired");
                sleep(2);
            } finally {
                lock.unlock(stamp);
                System.out.println("Write done");
            }
        });

        stop(executor);
        */

        /**
         * convert to writeLock
         */
        /*
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        // init count
        final int[] count = {100};

        executor.submit(() -> {
            long stamp = lock.readLock();
            try {
                if (count[0] == 0) {
                    stamp = lock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) {
                        System.out.println("Could not convert to write lock");
                        stamp = lock.writeLock();
                    }
                    count[0] = 23;
                }
                System.out.println(count[0]);
            } finally {
                lock.unlock(stamp);
            }
        });

        stop(executor);
        */

        /**
         * Semaphores
         */
        /*
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Semaphore semaphore = new Semaphore(5);

        Runnable longRunningTask = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired");
                    sleep(5);
                } else {
                    System.out.println("Could not acquire semaphore");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10)
                .forEach(i -> executor.submit(longRunningTask));

        stop(executor);
        */


        /**
         * AtomicInteger
         */
        /*
        AtomicInteger atomicInt = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000)
                .forEach(i -> executor.submit(atomicInt::incrementAndGet));

        stop(executor);

        System.out.println(atomicInt.get());    // => 1000
        */

        /*
        AtomicInteger atomicInt = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000)
                .forEach(i -> {
                    Runnable task = () ->
                            atomicInt.updateAndGet(n -> n + 2);
                    executor.submit(task);
                });

        stop(executor);

        System.out.println(atomicInt.get());    // => 2000
        */

        /*
        AtomicInteger atomicInt = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000)
                .forEach(i -> {
                    Runnable task = () ->
                            atomicInt.accumulateAndGet(i, (n, m) -> n + m);
                    executor.submit(task);
                });

        stop(executor);

        System.out.println(atomicInt.get());    // => 499500
        */

        /**
         * LongAdder
         */
        /*
        LongAdder adder = new LongAdder();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000)
                .forEach(i -> executor.submit(adder::increment));

        stop(executor);

        System.out.println(adder.sumThenReset());   // => 1000


        LongBinaryOperator op = (x, y) -> 2 * x + y;
        LongAccumulator accumulator = new LongAccumulator(op, 1L);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10)
                .forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

        stop(executor);

        System.out.println(accumulator.getThenReset());     // => 2539
        */


        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();


//        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");

        map.forEach(3, (key, value) ->
                System.out.printf("key: %s; value: %s; thread: %s\n",
                        key, value, Thread.currentThread().getName()));


    }

    /**
     * Stop the ExecutorService
     *
     * @param executor
     */
    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("termination interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            executor.shutdownNow();
        }
    }

    /**
     * Sleep seconds
     *
     * @param seconds
     */
    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }


    int count = 0;

    /*
    synchronized void increment() {
        count++;
    }
    */

    ReentrantLock lock = new ReentrantLock();

    /**
     * increment of count by multiple threads
     */
    void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

//    public static void consume(Object o) {
//
//    }
}
