#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <string.h>
#include <arpa/inet.h>

int bind_socket(char* argv[]); 

int main(int argc, char*argv[]) {
	bind_socket(argv);

	while(1) {
		listen();
	}


	return 0;
}

int bind_socket(char* argv[]) {
	struct sockaddr_in addr;
	int res;
	int socket_fd = socket(AF_INET, SOCK_STREAM, 0 );

	addr.sin_family = AF_INET;
	addr.sin_port = htons(atoi(argv[2]));
	inet_aton(argv[1], &addr.sin_addr);

	if ( bind( socket_fd, (struct sockaddr *)&addr, sizeof)) == -1) {
		perror("");
		exit(1);
	}

	return socket_fd;

}
