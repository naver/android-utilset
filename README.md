# Android Utilset

### Introduction
&nbsp;&nbsp;Utilset is collections of useful functions to save your valuable time. 
Because there are a ton of different ways to implement a method,<br>
we often spend our valuable time to search and test those ways.
Some may work, but some may not work.<br>
&nbsp;&nbsp;As such, we collected, tested and refined methods so as to prevent seeking code snippet.<br>

-------
### Overview
Utilset includes:

  * Utilset
    + determines if the phone has SMS capability
    + etc...     
  
  * ActivityUtils
    + checks if a user specified package is installed
    + gets information of base activity package name and class name
  
  * NetworkUtils
    + provides listeners which notify when network state changes, , receives a call, or a connection is made
    
    + checks if WiFi is enabled and if WiFi is connected
    + checks if Mobile Network (3G/4G) is connected
    + obtains IP Address in either v4 or v6 form
    + etc...
  
  * DiskUtils
    + obtains external storage path for caching
    + obtains external storage path for temporary files
    + obtains MicroSD Card path
  
  * KeyboardUtils
    + shows, hides, and toggles Software keyboards
    + checks if a device has software menu/home/back button
    
  * CipherUtils
    + provides simple AES based cryptographic methods
    
  * StringUtils
    + provides string compression/decompression methods

  * SystemUtils
    + checks if a device is rooted
    + counts the number of processors(more specifically the number of cores)
    + determines whether the device is mobile phone or not
    
-------
### Download
&nbsp;&nbsp;The latest version can be downloaded in jar and referenced as a library.
Link is here http://repo.nhncorp.com/maven2/com/navercorp/utilset/utilset/1.0/utilset-1.0.jar
Just add downloaded jar file into libs folder in your android project.

or 

You can also configure pom.xml if using Maven :
```xml
<!-- First add repository in pom.xml -->
<repository>
          <id>nhncorp</id>
          <name>Naver Corp. Repository</name>
          <url>http://repo.nhncorp.com/maven2/</url>
      </repository>
      
<!-- Add utilset dependency in pom.xml -->      
<dependency>
	<groupId>com.navercorp.utilset</groupId>
	<artifactId>utilset</artifactId>
	<version>insert latest version</version>
</dependency>
```

-------  
### Usage
Please, refer sample project

-------
### How to run sample project
  (1) Download Utilset Maven Project from http://yobi.navercorp.com:80/weblab/android-utilset<br>
  (2) Import downloaded project as Maven project in Eclipse (Maven plug-in must be installed to run utilset test project)<br>
  (3) Right click on utilset-parent in Package Explorer<br>
  (4) Select Run As and then Maven Build<br>
  (5) If you run utilset-parent for the first time, then Run Configuration will appear<br>
  (6) Type clean install to the Goal tab<br>
  (7) Click run button on the bottom<br>

-------
## License

    Copyright 2013 Navercorp

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.