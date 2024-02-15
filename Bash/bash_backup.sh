#!/bin/bash

DB_USER=UniversityDatabase 
DB_PASSWORD=123
DB_SID=XE

DATE_FORMAT=$(date +"%Y%m%d_%H%M%S")


EXPORT_FILE="backup_${DATE_FORMAT}.dmp"


expdp ${DB_USER}/${DB_PASSWORD}@${DB_SID} DIRECTORY=DATA_PUMP_DIR DUMPFILE=${EXPORT_FILE} FULL=Y


if [ $? -eq 0 ]; then
    echo "Database is backuped up successfully. File: ${EXPORT_FILE}"
else
    echo "Error: Database backup failed. Try again later."
fi