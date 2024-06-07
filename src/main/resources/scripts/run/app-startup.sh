#! /bin/sh

nohup java -server -jar -Duser.timezone=Asia/Shanghai -XX:-HeapDumpOnOutOfMemoryError ./app.jar --spring.profiles.active=prod >/dev/null 2>&1 &