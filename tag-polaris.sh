#!/bin/bash

echo "-----------------------------------"
echo "::: Welcome to AchoBeta-Polaris :::"

function usage() {
    echo "Usage: "
    echo "  tag-polaris.sh EnvType MODULE"
    echo "  EnvType  = [test|pro], 必填"
    echo "  MODULE 可以是 [app], 必填"
}

if [ "" = "$1" ] || [ "" = "$2" ];then
  echo "缺少必填参数，环境、服务模块"
  usage
  exit 1
fi

ENVIRONMENT=$1
if `git status | grep "master" &>/dev/null`; then
   ENVIRONMENT="prod"
elif [  "" = "$1" ]; then
   ENVIRONMENT="test"
elif [ "pre" != "$1" ] && [ "test" != "$1" ]; then
  echo "非法环境参数:"$1"，目前该参数可选值：[test|pro]"
  exit 1
fi

if [ "$2" != "app" ]; then
  echo "非法的服务模块参数:"$2"，目前该参数可选值：[app]"
  exit 1
fi

prefix="v_"$ENVIRONMENT"_"$2"_"
echo $prefix

function storage-tag() {
    git push
    git pull --tags
    local branch_name=$(echo $(git status | head -1  | awk '{print $NF}'))
    ## 这是为了在测试环境下多人共同使用测试分支时tag进行区分，不过需要注意tag不能过长，避免资源包截断导致发布失败
    local split_branch_name=${branch_name:0:15}
    local new_tag=$(echo ${prefix}${split_branch_name}_$(date +'%Y%m%d')_$(git tag -l "${prefix}${split_branch_name}_$(date +'%Y%m%d')_*" | wc -l | xargs printf '%02d'))
    git tag ${new_tag}
    git push origin $new_tag
}

storage-tag;
