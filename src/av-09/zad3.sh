#!/bin/bash

# Излистајте ги во посебна датотека сите датотеки во вашиот домашен директориум кои се модифицирани во тековниот ден

allFilesInHomeDir=`ls /home/141254`
echo $allFilesInHomeDir

month=`date | awk '{print $2;}'`
day=`date | awk '{print $3;}'`

for file in $allFilesInHomeDir
do

	monthForFile=`ls -l /home/141254 | grep $file | awk '{print $7;}'`
	dayForFile=`ls -l /home/141254 | grep $file | awk '{print $8}'`

	echo $file
	echo $monthForFile
	echo $dayForFile
	echo "-----"

	if [ $monthForFile = $month ] && [ $dayForFile = $day ]
	then
		echo "$file - $dayForFile" >> logfile.txt
	fi

done