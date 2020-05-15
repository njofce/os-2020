#!/bin/bash

# Да се испечатат, една по една, сите датотеки поголеми од 100К во тековниот директориум. Дајте му на корисникот опција да ја брише датотеката. Во logfile да се испечатат имињата на сите избришани датотеки и времето на бришење.

if [ ! $# -eq 1 ]
then

        echo 'Ve molime da vnesete 1 argument - pateka do direktorium'
	exit 0
fi

allFilesAndFolders=`ls $1`

for item in $allFilesAndFolders
do

	if [ -f $item ]
      	then

		size=`ls -l $1 | grep -v '~$' | grep $item | awk '{ print $6; }'`
		lengthOfVariable=`echo $size | wc -c`
		echo "Vrednosta na size: $size - $lengthOfVariable"

 		if [ $lengthOfVariable -gt 1 ] && [ $size -gt 100000 ]
		then

			echo "Dali sakate da go izbrisete fajlot $item (y/n)"
			read choice

			if [ $choice = "y" ]
			then
				rm $item
				deletionTime=`date | awk '{print $4;}'`
				echo "${item} - ${deletionTime}" >> logfile.txt
			fi

		fi

	fi


done