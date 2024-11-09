#!/bin/sh

# 使用说明，用来提示输入参数
usage(){
  echo "-----------------------------------"
  echo "::: Welcome to AchoBeta-Polaris :::"
  echo "Usage: sh running.sh [base|services|stop|rm|rmi]"
  exit 1
}

# shellcheck disable=SC2164
cd docs/dev-ops

# Check if Docker is installed
if ! command -v docker >/dev/null 2>&1; then
  echo "Error: Docker is not installed. Please install Docker before running this script."
  exit 1
fi

# Check if Docker Compose is installed and set the appropriate command
if command -v docker-compose &> /dev/null
then
    COMPOSE_COMMAND="docker-compose"
else
    if command -v docker compose &> /dev/null
    then
        COMPOSE_COMMAND="docker compose"
    else
        echo "Error: Docker Compose is not installed. Please install Docker Compose before running this script."
        exit 1
    fi
fi

# 启动基础环境（必须）
base(){
  $COMPOSE_COMMAND -f docker-compose-environment.yml up -d
}

CONTAINER_NAME=polaris
#IMAGE_NAME=ghcr.io/bantanger/achobeta/polaris-app:latest

# 启动程序模块（必须）
services(){
  $COMPOSE_COMMAND -f docker-compose-app.yml up
}

# 关闭服务模块
stop(){
  $COMPOSE_COMMAND -f docker-compose-app.yml stop ${CONTAINER_NAME}
}

# 删除服务模块
rm(){
  $COMPOSE_COMMAND -f docker-compose-app.yml rm ${CONTAINER_NAME}
}

# 删除所有未使用的镜像
rmi(){
  echo "开始删除未使用的镜像 .."
  docker image prune -a -f
  echo "镜像删除成功"
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"base")
  base
;;
"services")
  services
;;
"stop")
  stop
;;
"rm")
  rm
;;
"rmi")
  rmi
;;
*)
  usage
;;
esac
