#!/bin/bash

log_file="E:\iti\courses\casestudy\Full Project\Bash\Report.tx5"

threshold=50

disk_space=$(df -h | awk 'NR==2 {print $6}' | sed 's/%//')


if [[ $disk_space -gt $threshold ]]; then
	echo "Warning Disk Space Is exceeding $threshold%" >> "$log_file"

else
	echo "Disk Space Is Under $threshold%" >> "$log_file"

fi