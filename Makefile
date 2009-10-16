all: server client

server: Server.java
	javac Server.java

client: Client.c
	gcc Client.c -o client -Wall

edit:
	vim -p Server.java Client.c

clean:
	rm *.class client
