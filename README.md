#proxy-demo


This is demo project written in few hours as a technical demonstration  

#Introduction

Proxy Server demo project was created based on specification:
Write a proxy server application that can accept requests over a socket, send the request to another server, and then send the response from that server back to the originating client via the proxy.
The functionality of the server should be something simple like adding two numbers, appending a string to the request, etc.
The application should be able to handle multiple concurrent requests

To implement such a Server I have decided to base network communication on well proven and tested network communication library netty.io and Java NIO Channels.

#Design Considerations 
##Assumptions
Main assumption in this project is that a proxy server is always configured to pass communication only to one server specified in configuration file it is not a part of design for client to send to proxy any configuration messages which would change proxy remote server. 
Proxy server should not do any modifications to data forwarded and reviewed from both server and client.
Proxy server is not interested in any data it is not parsing, this data and is not taking any action . 
Proxy server can handle multiple connections with great performance

##Dependencies
Project is using four open source libraries they all have a safe open sorce licences and are widely used across industry.
###Netty
First one is netty it is well established network framework designed for creating advanced network projects it has multiple protocols already implemented and it is very simple to crreate new ones . It has a well proven Architecture and has well established position on the market, used by companies like Facebook , Avast , Red Hat, Twitter... 
Licence  - Apache 2.0 

###SLF4J
Is a logging facade created for java serves as a simple abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at deployment time. 
Licence  - MIT

###Mockito 
“Mockito is a mocking framework that tastes really good. It lets you write beautiful tests with clean & simple API. Mockito doesn't give you hangover because the tests are very readable and they produce clean verification errors.”
Licence - MIT 

###jUnit 
Well known java unint testing framework 
Licence - CPL


#Architecture

##Build
Maven is used as a build tool   , project  is created from  4 modules 
- parent – is a maven parent project which is grouping all other modules 
- common – this module stores protocol classed decoders encoders and shared clases 
- server – this module stores server clases  and its tests
- client – this module stores Client clases .
- proxy -  this module stores Proxy project , configuration for this project and tests 

## Communication protocol
Message sent between client and server has well defined structure it contains two informations an integer number and String UTF-16 message

4 bytes  - int number 
4 bytes  - int message size
message size* 2 bytes  - String message 

####A sample message 
This is a sample message which  sends number 255
And  message “test” 


         +-------------------------------------------------+
         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
+--------+-------------------------------------------------+----------------+
|00000000| 00 00 00 ff 00 00 00 04 00 74 00 65 00 73 00 74 |.........t.e.s.t|
+--------+-------------------------------------------------+----------------+