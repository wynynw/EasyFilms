# Easy Flims Movie Booking App

# Client
Android  
APK: android/app/release/  

# Server
A backup of server files. Actully server runs remotely and clients could connect it by android app. 

## Environment
>Mysql 8.0  
>Java JDK-11 JRE-8u102 
----
# database
Remember to create a new database/schema called **androiddb**  
Open mysql workbench-Data Import-sql files folder-Start Import  
![image](https://user-images.githubusercontent.com/44150992/163736380-512bcc4a-2782-4bbb-97f5-20ec12a16905.png)

If there are errors during data import(no database androiddb), please import each sql files to the database by command line.

----
# server
oper server folder as a java application，default port is 12333  
![image](https://user-images.githubusercontent.com/44150992/163736441-561fdea6-a599-4b52-8a98-bfd452c886ae.png)  
<br>
Remember to change the username and password of Mysql database 
![image](https://user-images.githubusercontent.com/44150992/164800889-e4acea78-5dda-4a3f-a543-a7dc0781294a.png)   
<br>
Then, import three external jar files to the project.  
Build path-Configure build path  
![image](https://user-images.githubusercontent.com/44150992/166123333-62f490e5-e480-4dee-8dad-da63436339c7.png)
![image](https://user-images.githubusercontent.com/44150992/166123383-940a0cdf-690d-4863-90ff-b31d84973eff.png)

**server needs to be started first! Do not stop it while using android app**   
If it needs to be closed, please close it manully by the terminate button. Do not directly close the IDE, otherwise the port will be occupied and then you need to reboot your computer or kill the process in cmd.  

----
# android client connect to local server
change all the ip address in java files to yourselves. The port is the same as the server's  
![image](https://user-images.githubusercontent.com/44150992/166123504-bcd8736a-277c-4ba7-8d0c-7997528c6927.png)

How to find your ip:
open CMD，input ipconfig，find the ip with red box. 
![image](https://user-images.githubusercontent.com/44150992/163736838-59bdd1ba-588a-4238-9fd5-c1dccb7db0e9.png)
