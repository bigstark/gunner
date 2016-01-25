package com.bigstark.gunner.library;

import android.os.Message;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Gunner {

    private volatile static Gunner instance;

    static Gunner getInstance() {
        if (instance == null) {
            synchronized (Gunner.class) {
                if (instance == null) {
                    instance = new Gunner();
                }
            }
        }

        return instance;
    }

    public static void shoot(Object target) {
        Gunner instance = getInstance();
        Method[] methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {

            Bullet bullet = method.getAnnotation(Bullet.class);
            if (bullet == null) {
                continue;
            }

            method.setAccessible(true);
            int sequence = bullet.sequence();
            long delay = bullet.delay();

            instance.enqueue(method, sequence, delay);
        }

        instance.shootInternal(target);
    }


    public static void cancel(Object target) {
        getInstance().cancelInternal();
    }

    private ArrayList<BulletHandler> methodSet = new ArrayList<>();
    private ArrayList<BulletHandler> runnableSet = new ArrayList<>();

    private GunHandler gunHandler = new GunHandler();

    void enqueue(Method method, int sequence, long delay) {
        if (sequence < 0) {
            throw new IllegalArgumentException(String.format("[%s]'s sequence must be over 0. Please check the sequence", method.getName()));
        }

        if (delay < 0) {
            throw new IllegalArgumentException(String.format("[%s]'s delay must be over 0. Please check the delay", method.getName()));
        }

        BulletHandler handler = new BulletHandler(method, sequence, delay);
        methodSet.add(handler);
    }

    void enqueue(Runnable bullet, int sequence, long delay) {
        if (sequence < 0) {
            throw new IllegalArgumentException("Sequence must be over 0. Please check the sequence");
        }

        if (delay < 0) {
            throw new IllegalArgumentException("Delay must be over 0. Please check the delay");
        }

        BulletHandler handler = new BulletHandler(bullet, sequence, delay);
        runnableSet.add(handler);
    }


    void shootInternal(Object target) {
        Collections.sort(methodSet, bulletComparator);
        gunHandler.setTarget(target);

        long delay = 0;

        for (BulletHandler bulletHandler : methodSet) {
            delay += bulletHandler.getDelay();

            Message msg = gunHandler.obtainMessage(GunHandler.WHAT_METHOD);
            msg.obj = bulletHandler.getMethod();;
            gunHandler.sendMessageDelayed(msg, delay);
        }

        methodSet.clear();
    }

    void shootInternal() {
        Collections.sort(runnableSet, bulletComparator);
        long delay = 0;

        for (BulletHandler bulletHandler : runnableSet) {
            delay += bulletHandler.getDelay();

            Message msg = gunHandler.obtainMessage(GunHandler.WHAT_RUNNABLE);
            msg.obj = bulletHandler.getRunnable();
            gunHandler.sendMessageDelayed(msg, delay);
        }

        runnableSet.clear();
    }

    void cancelInternal() {
        gunHandler.removeMessages(GunHandler.WHAT_METHOD);
        gunHandler.removeMessages(GunHandler.WHAT_RUNNABLE);
    }

    public static class Revolver {

        public Revolver() {}

        public Revolver load(Runnable bullet) {
            return load(bullet, 0, 0);
        }

        public Revolver load(Runnable bullet, long delay) {
            return load(bullet, 0, delay);
        }

        public Revolver load(Runnable bullet, int sequence, long delay) {
            Gunner.getInstance().enqueue(bullet, sequence, delay);
            return this;
        }

        public void shoot() {
            Gunner.getInstance().shootInternal();
        }

        public void cancel() {
            Gunner.getInstance().cancelInternal();
        }

    }

    class BulletHandler {

        private Method method;
        private Runnable runnable;
        private int sequence;
        private long delay;

        public BulletHandler(Method method, int sequence, long delay) {
            this.method = method;
            this.sequence = sequence;
            this.delay = delay;
        }

        public BulletHandler(Runnable runnable, int sequence, long delay) {
            this.runnable = runnable;
            this.sequence = sequence;
            this.delay = delay;
        }

        public Method getMethod() {
            return method;
        }

        public Runnable getRunnable() {
            return runnable;
        }

        public int getSequence() {
            return sequence;
        }

        public long getDelay() {
            return delay;
        }
    }

    Comparator<BulletHandler> bulletComparator = new Comparator<BulletHandler>() {
        @Override
        public int compare(BulletHandler lhs, BulletHandler rhs) {
            int lSeq = lhs.getSequence();
            int rSeq = rhs.getSequence();
            return rSeq < lSeq ? 1 : rSeq == lSeq ? 0 : -1;
        }
    };


}
