# websocket
Step1:
clone the repository
import into eclipse as maven project

Step2:
Download glassfish 4
Configure the server in eclipse
right clink on the project in eclipse--> properties-->target runtime (select glassfish)

Step3: Start the glassfish server 

open the url http://localhost:8080/WebsocketSample in two tab, then add link in any of the page, it will appear in both page.

# websocket for tomcat 

for apache httpd server add the below in httpd.config otherwise the handshake will not happen for webserver url
enable the rewirte loader
# httpd.conf
LoadModule proxy_wstunnel_module modules/mod_proxy_wstunnel.so
LoadModule proxy_module modules/mod_proxy.so (for mod_proxy setup)
LoadModule rewrite_module modules/mod_rewrite.so


RewriteEngine On

RewriteCond %{HTTP:UPGRADE} ^WebSocket$ [NC]

RewriteCond %{HTTP:CONNECTION} Upgrade$ [NC]

RewriteRule .* ws://localhost:8080%{REQUEST_URI} [P]   
