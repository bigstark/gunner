package com.bigstark.gunner.library;

import android.os.Message;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public static void shoot(Object... targets) {
        for (Object target : targets) {
            shoot(target);
        }
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


    public static void cancel(Object... targets) {
        for (Object target : targets) {
            cancel(target);
        }
    }


    public static void cancel(Object target) {
        getInstance().cancelInternal(target);
    }


    private Map<Integer, GunHandler> gunHandlerMap = new HashMap<>();
    private List<BulletHandler> methodSet = new ArrayList<>();


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


    void shootInternal(Object target) {
        Collections.sort(methodSet, bulletComparator);

        GunHandler gunHandler = new GunHandler();
        gunHandler.setTarget(target);

        long delay = 0;

        for (BulletHandler bulletHandler : methodSet) {
            delay += bulletHandler.getDelay();

            Message msg = gunHandler.obtainMessage(GunHandler.WHAT_METHOD);
            msg.obj = bulletHandler.getMethod();;
            gunHandler.sendMessageDelayed(msg, delay);
        }

        methodSet.clear();

        gunHandlerMap.put(target.hashCode(), gunHandler);
    }


    void cancelInternal(Object target) {
        if (!gunHandlerMap.containsKey(target.hashCode())) {
            return;
        }

        GunHandler gunHandler = gunHandlerMap.get(target.hashCode());
        gunHandler.removeMessages(GunHandler.WHAT_METHOD);

        gunHandlerMap.remove(target.hashCode());
    }


    class BulletHandler {

        private Method method;
        private int sequence;
        private long delay;

        public BulletHandler(Method method, int sequence, long delay) {
            this.method = method;
            this.sequence = sequence;
            this.delay = delay;
        }

        public Method getMethod() {
            return method;
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
