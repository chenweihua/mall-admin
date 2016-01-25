package com.mall.admin.model.dao.ump.base;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GroupSequence implements Sequence {
	private static final long ONE_STEP = 10;
	private static final Lock LOCK = new ReentrantLock();
	private static long lastTime = System.currentTimeMillis();
	private static short lastCount = 0;
	private static int count = 0;
	@SuppressWarnings("finally")
	@Override
	public long nextValue() throws SequenceException
    {
        LOCK.lock();
        try {
            if (lastCount == ONE_STEP) {
                boolean done = false;
                while (!done) {
                    long now = System.currentTimeMillis();
                    if (now == lastTime) {
                        try {
                            Thread.currentThread();
                            Thread.sleep(1);
                        } catch (java.lang.InterruptedException e) {
                        }
                        continue;
                    } else {
                        lastTime = now;
                        lastCount = 0;
                        done = true;
                    }
                }
            }
            count = lastCount++;
        }
        finally 
        {
            LOCK.unlock();
            StringBuffer  sb = new StringBuffer();
            sb.append(lastTime);
            sb.append(String.format("%03d",count));
            return Long.valueOf(sb.toString()); 
        }
    }
	
	


}
