#!/bin/bash

# This script allows full automatic launch (script used for testing delivery + SonarQube analysis).

magentafat='\033[1;35m';
neutral='\033[0;m'

# Compilation by Maven (with the plugin JaCoCo, to generate code coverage reports)
echo -e "\n${magentafat}COMPILATION...${neutral}"
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package

sleep 5

# Analysis by Maven (with the plugin (for) SonarQube...)
echo -e "\n${magentafat}ANALYSIS...${neutral}"
mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.5.0.1254:sonar

sleep 5

# Execution by Maven...
echo -e "\n${magentafat}EXECUTION...${neutral}"
mvn exec:java
