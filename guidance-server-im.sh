#!/usr/bin/env bash

APP_NAME=guidance-server-im-1.0.0.RELEASE.jar
##用来提示输入参数
function usage(){
   echo "Usage sh执行脚本.sh [start|stop|restart|status] [dev|prod|test]"
   exit 1
}
##检查程序是否正在运行
function is_exist(){
   pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
   #如果存在返回0,不存在返回1
   if [ -z "${pid}" ]; then
     return 1
   else
    return 0
  fi
}

##启动方法
## 笔记：方法接受参数 ，取地址符一定要为 $1
function start(){
  profile=$1
  echo "spring.profiles.active is $profile"
  is_exist
  if [ $? -eq "0" ]; then
    echo "pid=${pid}"
    echo "${APP_NAME} is already runing. pid=${pid}"
  else
    nohup java -jar -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms2g -Xmx2g -Xmn256m -Xss256k -XX:SurvivorRatio=8 ${APP_NAME} --spring.profiles.active="$profile" >/dev/null  &
  fi
}
##停止方法
function stop(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "pid=${pid}"
    kill -9 $pid
  else
    echo "${APP_NAME} is not runing"
 fi
}
##输出运行状态
function status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is runing. pid is ${pid}"
  else
    echo "${APP_NAME} is not runing"
  fi
}
##重启
## 笔记：方法接受参数 ，取地址符一定要为 $1
function restart(){
   profile=$1
   stop
   start $1
}
    # 运行指令
    runinstrc=$1
    # Spring boot 启动 spring.profiles.active 指定
    profile=$2
    echo "运行指令 is $runinstrc"
    echo "spring.profiles.active is $profile"
    #根据输入参数执行相应的方法,不输入则提示说明
    case "$runinstrc" in
      "start")
       start $2
       ;;
      "stop")
       stop
       ;;
      "restart")
       restart $2
       ;;
      "status")
       status
       ;;
      *)
       usage
       ;;
    esac