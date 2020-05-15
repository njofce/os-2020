#!/bin/bash

# За секој активен процес да се излистаат сите негови деца

listAllProcesses=`ps -ef | awk 'ps -ef | awk '{print $3;}' | grep -v "PPID" | sort -n | uniq`

for processId in $listAllProcesses
do

	childProcesses=`ps -ef | awk '$3 ~ '$processId' { print $2; }'`
	echo "Deca na procesot $processId se:"
	echo $childProcesses
done