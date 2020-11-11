package com.geek.demo;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @program: Week_04
 * @description: 一共用了十种方式
 * @author: yarne
 * @create: 2020-11-11 12:14
 **/
public class ThreadCallBackDemo {
    /**
     * 主线程的await
     */
    public static void countDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Integer[] result = new Integer[1];
        try {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "------------------countDownLatch线程执行");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result[0] = genRandom();
                countDownLatch.countDown();
            }).start();

            countDownLatch.await();
            System.out.println("countDownLatch线程执行完毕" + result[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 每个线程的await
     */
    public static void cyclicBarrier() {
        final Integer[] result = new Integer[1];
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> {
            System.out.println("cyclicBarrier线程执行完毕" + result[0]);
        });

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "------------------cyclicBarrier线程执行");
            try {
                Thread.sleep(2000);
                result[0] = genRandom();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 普通线程join
     */
    public static void join() {
        final Integer[] object = new Integer[1];
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "------------------join线程执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            object[0] = genRandom();
        });
        thread.start();
        try {
            thread.join();
            System.out.println("join线程执行完毕" + object[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * future只能配合线程池进行使用
     */
    public static void future() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> submit = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "------------------future线程执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return genRandom();
        });
        try {
            System.out.println("future线程执行完毕" + submit.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * futureTask可以用在任何线程中
     */
    public static void futureTask() {
        FutureTask<Object> futureTask = new FutureTask<Object>(() -> {
            System.out.println(Thread.currentThread().getName() + "------------------futureTask线程执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return genRandom();
        });

        new Thread(futureTask).start();
        try {
            System.out.println("futureTask线程执行完毕" + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * stream Future方式
     */
    public static void completableFuture() {
        try {
            Integer integer = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "------------------completableFuture线程执行");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return genRandom();
            }).get();
            System.out.println("completableFuture线程执行完毕" + integer);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * lockSupport 锁定主线程
     */
    public static void lockSupport() {
        final Integer[] object = new Integer[1];
        Thread thread = Thread.currentThread();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "------------------lockSupport线程执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            object[0] = genRandom();
            LockSupport.unpark(thread);
        }).start();
        LockSupport.park(thread);
        System.out.println("lockSupport线程执行完毕" + object[0]);
    }

    /**
     * 使用过期方法，这个方法容器造成死锁，所以才会被标注过期
     */
    public static void currentThread() {
        Thread thread = Thread.currentThread();
        final Integer[] object = new Integer[1];
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "------------------currentThread线程执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            object[0] = genRandom();
            thread.resume();
        }).start();
        thread.suspend();
        System.out.println("currentThread线程执行完毕" + object[0]);
    }

    /**
     * 直接使用循环判断
     */
    public static void circuit() {
        final Integer[] object = new Integer[1];
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "------------------circuit线程执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            object[0] = genRandom();
        }).start();

        while (object[0] == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("circuit线程执行完毕" + object[0]);
    }


    /**
     * 信号量semaphore
     * 使用这种方式，我需要让线程先执行acquire才有效果，所以让主线程睡了1秒
     */
    public static void semaphore() {
        final Integer[] object = new Integer[1];
        Semaphore semaphore = new Semaphore(1);
        new Thread(() -> {
            try {
                semaphore.acquire();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "------------------semaphore线程执行");
            object[0] = genRandom();
            semaphore.release(1);
        }).start();

        try {
            Thread.sleep(1000);
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.release(1);


        System.out.println("semaphore线程执行完毕" + object[0]);

    }


    //使用的方法
    public static int genRandom() {
        return new Random().nextInt();
    }

    public static void main(String[] args) {
        countDownLatch();
        join();
        cyclicBarrier();
        future();
        futureTask();
        completableFuture();
        lockSupport();
        currentThread();
        circuit();
        semaphore();
    }
}
