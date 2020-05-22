#!/bin/bash

if [ $# -ne 3 ]
then

	echo "Use the script in this way: skripta.sh hourFrom hourTo destFolder"
	exit 1
fi

if [ -d $3 ]
then
 	rm -R $3
fi
mkdir $3

destFolder=$3

function getLoggedInStudents() {
	# INDEX---HOUR
	loggedIn=`last | grep ' May  6' | awk '{print $1, $7;}' | tr ':' ' ' | awk '{print $1, $2;}' | awk '($2 >= '$1') && ($2 < '$2') {printf "%s--%s\n", $1, $2;}'`
}

function copyFilesRecursive() {

		allFilesInUserDirectory=`ls $1`

                fileNumber=0
                for file in $allFilesInUserDirectory
                do
			echo "$1/$file"
			if [ -d "$1/$file" ]
			then
				echo "calling recursive for ${file}"
				copyFilesRecursive "$1/$file" $2 $3
				# call recursive
			else
				echo "listing files for user: $2"
				mdFile=`echo $file | grep '.*\.md' | wc -l`
				if [ $mdFile -eq 1 ]
				then
					cp "$1/$file" "$destFolder/$3_${fileNumber}_$2.md"
					fileNumber=$(($fileNumber + 1))
				fi
			fi
        	done



}

function findAllMDFilesForStudents () {

	for user in $1
	do

		userIndex=`echo $user | awk -F -- '{print $1;}'`
		userLoginTime=`echo $user | awk -F -- '{print $2;}'`

		copyFilesRecursive "./students/${userIndex}" $userIndex $userLoginTime
	done

}

function createData() {

	rm -R ./students
	for student in $*
	do
		mkdir -p "./students/${student}"
		touch "./students/${student}/file1.txt"
		touch "./students/${student}/file11.md"
	done

}

getLoggedInStudents $1 $2
findAllMDFilesForStudents "$loggedIn"