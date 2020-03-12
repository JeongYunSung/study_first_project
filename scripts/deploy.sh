#!/bin/bash

repository=/home/ec2-user/app/step2
project_name=jys-firstproject-webservice

echo "> Build파일 복사"

cp $repository/zip/*.jar $repository/

echo "> 현재 구동중인 애플리케이션 pid 확인"

current_pid=$(pgrep -fl $project_name | grep jar | awk '{print $1}')

echo "현재 구동중인 애플리케이션 pid: $current_pid"

if [ -z "$current_pid" ]; then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $current_pid"
    kill -15 $current_pid
    sleep 5
fi

echo "> 새 애플리케이션 배포"

jar_name=$(ls -tr $repository/*.jar | tail -n 1)

echo "> jar name: $jar_name"

echo "> $jar_name 에 실행권한 추가"

chmod +x $jar_name

echo "> $jar_name 실행"

nohup java -jar -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/application-real-db.yml $jar_name > $repository/nohup.out 2>&1 &
