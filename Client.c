#include <stdio.h>
#include <netinet/in.h>
#include <sys/socket.h>


int main( int argc, char * argv[]) {
	int i = 0, socket_fd;

	//socket address
	struct sockaddr_in sock_addr;


	if ( argc < 5) {
		printf("wrong input");
	} else {
		int socket_fd;

		if (	socket_fd = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP) == -1) {
			perror("socket");
			return -1;	
		} 

		sock_addr.sin_family = AF_INET;
		sock_addr.sin_port = htons(argv[2]);
		sock_addr.sin_addr = 

	}
	return 0;
}

