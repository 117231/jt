package com.jt.common.util;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * 定期清理无效的http连接
 */
public class IdleConnectionEvictor extends Thread {

    private final HttpClientConnectionManager connMgr;
    
    private Integer waitTime;

    //当前标识的属性属于全部线程共有
    //如果有一个线程修改了数据.
    //则全部线程立即修改.
    private volatile boolean shutdown; //默认值false

    public IdleConnectionEvictor(HttpClientConnectionManager connMgr,Integer waitTime) {
        this.connMgr = connMgr;
        this.waitTime = waitTime;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(waitTime);
                    // 关闭失效的连接
                    connMgr.closeExpiredConnections();
                }
            }
        } catch (InterruptedException ex) {
            // 结束
        }
    }

    /**
     * 销毁释放资源
     */
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();//起来别睡了 鬼子来了 wait数据直接执行
        }
    }
}
