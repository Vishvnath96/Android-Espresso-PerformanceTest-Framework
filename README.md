# Espresso_NFR_Framework
Espresso NFR Framework for extracting memory cpu and network usage data automatically by using google api.
By Using this Framework we can easily capture android performance stats like CPU(user space,system space),Memory(Native,Dalvik,Private shared etc),
Network(RX/TX for mobile and WiFi data usage) and Frozen frames info after every ui test run and 
dump data into my sql data base for version1 and compare this data with previous version.It gives clear picture for if performance
degraded and get info for culprit classes and thread by heap dump and method trace.

# metrices to measure
![](https://github.com/Vishvnath96/Android-Espresso-PerformanceTest-Framework/blob/master/mem.png)
