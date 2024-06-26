 #!binbash

 # 主类
 APP_MAINCLASS=app.jar

 # 进程ID
 psid=0

 # 记录尝试次数
 num=0

 # 获取进程ID，如果进程不存在则返回0，
 # 当然你也可以在启动进程的时候将进程ID写到一个文件中，
 # 然后使用的使用读取这个文件即可获取到进程ID
 getpid() {
    javaps=`jps -l  grep $APP_MAINCLASS`
    if [[ -n $javaps ]]; then
       psid=`echo $javaps  awk '{print $1}'`
    else
       psid=0
    fi
 }

 stop() {
    getpid
    num=`expr $num + 1`  
    if [ $psid -ne 0 ]; then
     # 重试次数小于3次则继续尝试停止服务
     if [ $num -le 3 ];then
       echo attempt to kill... num$num
       kill $psid
       sleep 1
     else
       # 重试次数大于3次，则强制停止
       echo force kill...
       kill -9 $psid      
     fi
     # 检查上述命令执行是否成功
     if [ $ -eq 0 ]; then
        echo Shutdown success...
     else
        echo Shutdown failed...
     fi
  
     # 重新获取进程ID，如果还存在则重试停止
     getpid
     if [ $psid -ne 0 ]; then
        stop
     fi
    else
       echo App is not running
    fi
 }

 stop