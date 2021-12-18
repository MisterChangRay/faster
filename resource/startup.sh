#!/bin/sh
# 相同应用分布式部署名称应保持一致
APPGROUP="DEVICE_FACTORY"
# 每个应用ID应唯一
APPID="DEVICE_FACTORY_169_208"

#AGENTCONFIG="-javaagent:/ext/pinpoint-agent-2.2.0/pinpoint-bootstrap-2.2.0.jar -Dpinpoint.agentId=$APPID -Dpinpoint.applicationName=$APPGROUP"
AGENTCONFIG=""
JVMPARAM="-XX:+UseG1GC -Xms256m  -Xmx256m  -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -verbose:gc -Xloggc:jdklog/gc.log -XX:-HeapDumpOnOutOfMemoryError -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=jdklog/dump.bin -Djava.security.egd=file:/dev/./urandom"

[ ! -f tpid ] && touch tpid
[ -f nohup.out ] && echo "" > nohup.out

tpid=`cat tpid | awk '{print $1}'`

if [ -n "$tpid" ];then
        tpid=`ps -aef | grep "$tpid" | awk '{print $2}' | grep -w "$tpid"`

fi


if [ ${tpid} ]; then
        echo "server is running,cannot startup"
else
        echo "" > tpid
for file in ./*.jar
do
        if test -f $file
        then
                nohup java $AGENTCONFIG  $JVMPARAM -jar $file > /dev/null 2> nohup.out &
                echo $! > tpid
		sleep 5
		tpid=`cat tpid`
		chkpro=`ps -ef| awk '{print $2}'| grep "$tpid"| wc -l`
		if [ $chkpro -eq 1 ];then
                echo $file has start successed
		else
		echo "$file 启动失败! 详情请查看 nohup.out"
		fi

        fi
done
fi
