@echo off

D:
cd D:\Work\w1\ihxlife\mybatis_generator
java -jar mybatis-generator-core-1.3.1.jar -configfile generatorConfig-mysql.xml -overwrite

pause