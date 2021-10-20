package com.github.misterchangray.common.util;

import java.util.Date;
import java.util.UUID;

/**
 * id 生成器使用 使用雪花算法
 *
 * 请确保使用的 APP_ID 和 APP_INSTANCE_ID 全局唯一
 * APP_ID 请在项目中统一进行注册
 * APP_INSTANCE_ID为自定义实例ID, 每启动一个服务实例必须使用一个唯一ID
 *
 * APP_ID区间 0 - 4095
 * APP_INSTANCE_ID区间 0 - 127
 *
 * 项目中通过在配置文件中配置以下属性进行配置:
 * com.github.misterchangray.faster.appId=1
 * com.github.misterchangray.faster.appInstanceId=1
 *
 */
public class IDUtil {

    /**
     * 起始的时间戳
     * 时间戳取秒
     */
    private final static long START_STMP = 1634615727794L / 1000;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12;  //序列号占用的位数
    private final static long APP_ID_BIT = 12;    // APPID
    private final static long APP_INSTANCE_BIT = 7; // APP_INSTANCE_ID

    /**
     * 每一部分的最大值
     */
    private final static long MAX_APP_ID_NUM = -1L ^ (-1L << APP_ID_BIT);
    private final static long MAX_APP_INSTANCE_NUM = -1L ^ (-1L << APP_INSTANCE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long APP_ID_LEFT = SEQUENCE_BIT;
    private final static long APP_INSTANCE_LEFT = APP_ID_LEFT + SEQUENCE_BIT;
    private final static long TIMESTAMP_LEFT =   APP_INSTANCE_LEFT + APP_INSTANCE_BIT;

    private long appId;  //数据中心
    private long appInstanceId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public synchronized long getId() {
        return nextId();
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = now();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTAMP_LEFT     //时间戳部分
                | appInstanceId << APP_INSTANCE_LEFT        //应用实例部分
                | appId << APP_ID_LEFT                      //应用ID部分
                | sequence;                                 //序列号部分
    }

    private long getNextMill() {
        long mill = now();
        while (mill <= lastStmp) {
            mill = now();
        }
        return mill;
    }

    private long now() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * 解析ID中包含的日期
     * 精确到秒
     * @param id
     * @return
     */
    public long parseTime(long id) {
        long sec = id >> TIMESTAMP_LEFT;
        if(sec <= 0) return -1;
        return (sec + START_STMP) * 1000;
    }

    /**
     * 解析ID中包含的日期
     * 精确到秒
     * @param id
     * @return
     */
    public Date parseDate(long id) {
        long l = parseTime(id);
        if(-1 == l) return null;
        return new Date(l);
    }

    /**
     * 解析ID中包含的应用ID
     * @param id
     * @return
     */
    public long parseAppId(long id) {
      id = id >>  SEQUENCE_BIT;
      return id & 0xfff;
    }


    /**
     * 解析ID中包含的实例ID
     * @param id
     * @return
     */
    public long parseAppInstanceId(long id) {
        return (id >> APP_INSTANCE_LEFT) & 0x7f;
    }


    /**
     * 应用ID应全局唯一
     * 0 < appId & appId < 4095
     * @param appId
     */
    public void setAppId(long appId) {
        if (appId > MAX_APP_ID_NUM || appId < 0) {
            throw new IllegalArgumentException("appId can't be greater than MAX_APP_ID_NUM or less than 0");
        }
        this.appId = appId;
    }

    /**
     * 应用的实例ID每个服务必须使用唯一的序号
     *
     * 0 < appInstanceId & appInstanceId < 127
     * @param appInstanceId
     */
    public void setAppInstanceId( long appInstanceId) {
        if (appInstanceId > MAX_APP_INSTANCE_NUM || appInstanceId < 0) {
            throw new IllegalArgumentException("appInstanceId can't be greater than MAX_APP_INSTANCE_NUM or less than 0");
        }
        this.appInstanceId = appInstanceId;
    }


    public IDUtil(long appId, long appInstanceId) {
        this.setAppId(appId);
        this.setAppInstanceId(appInstanceId);
    }
}
