package cn.zwz.basics.utils;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.util.concurrent.*;

/**
 * 线程池配置
 * @author 郑为中
 */
public class ThreadPoolUtil {

    @ApiModelProperty(value = "线程的缓冲队列")
    private static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(100);

    @ApiModelProperty(value = "核心线程数")
    private static final int SIZE_CORE_POOL = 5;

    @ApiModelProperty(value = "最大线程数量")
    private static final int SIZE_MAX_POOL = 10;

    @ApiModelProperty(value = "空闲线程存活时间")
    private static final long ALIVE_TIME = 2000;

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME, TimeUnit.MILLISECONDS, bqueue, new ThreadPoolExecutor.CallerRunsPolicy());

    static {
        pool.prestartAllCoreThreads();
    }

    @ApiOperation(value = "获取可用线程")
    public static ThreadPoolExecutor getPool() {
        return pool;
    }
}
