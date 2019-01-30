#!/usr/bin/env bash

for file in `ls scripts`
do
    if [ "$file" == "GroovuinoML.dsld" ]
    then
        continue
    fi

    echo scripts/$file
    ./run.sh scripts/$file
done