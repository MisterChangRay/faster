package com.github.misterchangray.financial.v001.intf;


/**
 * 日志同步服务
 *
 * 在数据库网络异常情况
 * 日志将会按分钟级别储存到本地日志文件
 * 等待数据库网络恢复后通过此服务将操作日志同步到数据库
 *
 */
public interface SynchService {
}
