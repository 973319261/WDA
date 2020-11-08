package com.gx.wda.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Toast;

import com.gx.wda.ui.MainActivity;

/**
 * 远程操作（模拟点击）工具类
 */
public class TeleoperationUtil {
    /**
     * 模拟点击或者滑动
     * @param x 点击坐标
     * @param y 点击坐标
     * @param x1 滑动坐标
     * @param y1 滑动坐标
     */
    public static void sendControl(final float x, final float y, final float x1, final float y1){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Instrumentation inst = new Instrumentation();
                try {
                    if(y - y1 > 50) {//向上滑
                        long dowTime = SystemClock.uptimeMillis();
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime,
                                MotionEvent.ACTION_DOWN, x, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime,
                                MotionEvent.ACTION_MOVE, x, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+20,
                                MotionEvent.ACTION_MOVE, x, y-20,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+30,
                                MotionEvent.ACTION_MOVE, x, y-40,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+40,
                                MotionEvent.ACTION_MOVE, x, y-60,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+40,
                                MotionEvent.ACTION_UP, x, y+60,0));
                    } else if(y1 - y > 50) {//向下滑
                        long dowTime = SystemClock.uptimeMillis();
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime,
                                MotionEvent.ACTION_DOWN, x, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime,
                                MotionEvent.ACTION_MOVE, x, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+20,
                                MotionEvent.ACTION_MOVE, x, y+20,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+30,
                                MotionEvent.ACTION_MOVE, x, y+40,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+40,
                                MotionEvent.ACTION_MOVE, x, y+60,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+40,
                                MotionEvent.ACTION_UP, x, y+60,0));
                    } else if(x - x1 > 50) {//向左滑
                        long dowTime = SystemClock.uptimeMillis();
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime,
                                MotionEvent.ACTION_DOWN, x, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime,
                                MotionEvent.ACTION_MOVE, x, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+20,
                                MotionEvent.ACTION_MOVE, x-20, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+30,
                                MotionEvent.ACTION_MOVE, x-40, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+40,
                                MotionEvent.ACTION_MOVE, x-60, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+40,
                                MotionEvent.ACTION_UP, x-60, y,0));
                    } else if(x1 - x > 50) {//向右滑
                        long dowTime = SystemClock.uptimeMillis();
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime,
                                MotionEvent.ACTION_DOWN, x, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime,
                                MotionEvent.ACTION_MOVE, x, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+20,
                                MotionEvent.ACTION_MOVE, x+20, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+30,
                                MotionEvent.ACTION_MOVE, x+40, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+40,
                                MotionEvent.ACTION_MOVE, x+60, y,0));
                        inst.sendPointerSync(MotionEvent.obtain(dowTime,dowTime+40,
                                MotionEvent.ACTION_UP, x+60, y,0));
                    }else {//点击
                        inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0));
                        inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0));
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        }).start();
    }
}
