package com.mall.admin.tool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Singal
 * 
 */
public class ThreadPool {
	private static class ThreadPoolHolder {
		private static final ThreadPool INSTANCE = new ThreadPool();
	}

	private ThreadPool()
	{
	}

	public static ThreadPool getInstance() {
		return ThreadPoolHolder.INSTANCE;
	}

	private static ExecutorService exec = new ThreadPoolExecutor(50, 50, 5 * 60, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	public void exec(Runnable command) {
		exec.execute(command);
	}

	public <T> Future<T> submit(Callable<T> command) {
		return exec.submit(command);
	}

	public void shutdown() {
		exec.shutdown();
	}

	public boolean isTerminated() {
		return exec.isTerminated();
	}

	public ExecutorService getExecutorService() {
		return exec;
	}

	class TestRunner implements Callable<String> {
		@Override
		public String call() {
			for (int i = 0; i < 10; i++) {
				// System.out.println(i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			}
			return "OK";
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 主线程等待子线程全部结束后运行
		ThreadPool tp = ThreadPool.getInstance();
		TestRunner tr = tp.new TestRunner();
		TestRunner tr1 = tp.new TestRunner();
		Future<String> fr1 = tp.submit(tr);
		Future<String> fr2 = tp.submit(tr1);
		tp.shutdown();
		while (!tp.isTerminated()) {
		}
		System.out.println(fr1.get());
		System.out.println(fr2.get());
		System.out.println("fin");
	}

}
