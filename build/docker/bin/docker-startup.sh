#!/bin/bash

#  Copyright (C) 2024  DeviceLinks

#  This program is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
#  (at your option) any later version.

#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.

#  You should have received a copy of the GNU General Public License
#  along with this program.  If not, see <https://www.gnu.org/licenses/>.

set -x

function join_if_exist(){
    if [ -n "$2" ]; then
        echo "$1$2"
    else
        echo ""
    fi
}

Xms=$(join_if_exist "-Xms" ${JVM_XMS})
Xmx=$(join_if_exist "-Xmx" ${JVM_XMX})
Xmn=$(join_if_exist "-Xmn" ${JVM_XMN})

JAVA_OPT="${JAVA_OPT} -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSClassUnloadingEnabled -XX:SurvivorRatio=8 "
JAVA_OPT="${JAVA_OPT} $Xms $Xmx $Xmn"

JAVA_OPT="${JAVA_OPT} -jar ${HOME_DIR}/target/${JAR}"
JAVA_OPT="${JAVA_OPT} --spring.config.location=${HOME_DIR}/conf/application.yml"

exec java ${JAVA_OPT}
