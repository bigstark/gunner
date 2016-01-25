package com.bigstark.gunner.library;

import android.os.Handler;
import android.os.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by BigStarK on 16. 1. 25..
 */
class GunHandler extends Handler {
    public static final int WHAT_METHOD = 0;
    public static final int WHAT_RUNNABLE = 1;

    private Object target;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case WHAT_METHOD:
                handleMethod(msg);
                break;

            case WHAT_RUNNABLE:
                handleRunnable(msg);
                break;
        }
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    private void handleMethod(Message msg) {
        if (target == null) {
            return;
        }

        Method method = (Method) msg.obj;

        try {
            method.invoke(target, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void handleRunnable(Message msg) {
        Runnable runnable = (Runnable) msg.obj;
        runnable.run();
    }
}
