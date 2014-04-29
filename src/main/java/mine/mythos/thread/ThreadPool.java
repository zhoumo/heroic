package mine.mythos.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import mine.mythos.base.BaseClass;

public class ThreadPool extends BaseClass {

	private static ThreadPool threadPool;

	private ThreadPoolExecutor executor;

	private ThreadPool() {
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10000);
		executor = new ThreadPoolExecutor(5, 10, 0, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.AbortPolicy());
	}

	public static synchronized ThreadPool getInstance() {
		if (threadPool == null) {
			threadPool = new ThreadPool();
		}
		return threadPool;
	}

	public void executeInThreadPool(Runnable runnable) {
		executor.execute(runnable);
	}
}
