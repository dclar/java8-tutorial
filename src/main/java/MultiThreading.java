/**
 * Description:
 *
 * @author dclar
 */
package cn.com.bmsmart.security.authorization.test.impl;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Description:
 *
 * @author dclar
 */
public class MultiThreading {



    public static void main(String[] args) throws ExecutionException, InterruptedException {


//        List<String> stringCollection = new ArrayList<>();
//        stringCollection.add("ddd2");
//        stringCollection.add("aaa2");
//        stringCollection.add("bbb1");
//        stringCollection.add("aaa1");
//        stringCollection.add("bbb3");
//        stringCollection.add("ccc");
//        stringCollection.add("bbb2");
//        stringCollection.add("ddd1");
//
//
//        stringCollection.stream().sorted()
//                //.filter((s) -> s.startsWith("a"))
//                .forEach(System.out::println);
//        //stringCollection.stream().sorted().filter((s) -> s.startsWith("a")).forEach(ServiceBImpl::consume);
//
//        System.out.println();
//
//        stringCollection
//                .stream()
//                .map(String::toUpperCase)
//                .sorted(Comparator.reverseOrder()).reduce((s1, s2) -> s1 + "#" + s2);
//                //.forEach(System.out::println);



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



    }

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
