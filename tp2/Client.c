#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <string.h>
#include <arpa/inet.h>

#define REGISTER "register"
#define LOOKUP "lookup"
#define BUF_LEN 1024


int main( int argc, char * argv[]) {
	int i = 0, socket_fd;

	//socket address
	struct sockaddr_in sock_addr;
	size_t len;
	struct in_addr * inet_address;
	struct ip_mreqn mreqn;

	//msg recieving
	char msg[BUF_LEN];
	int msg_size;

	char* data;

	if ( argc < 5) {
		printf("wrong input");
		return -1;
	} else {
		int socket_fd;
		bzero(&msg, sizeof(msg));

		//Initiate socket
		if (	(socket_fd = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) {
			perror("socket");
			return -1;	
		} 
		printf("Socket initiated");

		//Processes input to give the announced address 
		sock_addr.sin_family = AF_INET;
		sock_addr.sin_port = htons(atoi(argv[2]));
		inet_aton(argv[1], &sock_addr.sin_addr);

		const int on = 1;
		if( setsockopt(socket_fd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) == -1 ) {
			perror("setsockopt1");
			return -1;
		}

		len = sizeof(sock_addr);

		if ( bind( socket_fd, (struct sockaddr *)&sock_addr, sizeof(sock_addr)) == -1) {
			perror("");
			exit(1);
		}
		printf("Socket binded");

		// novas opt do socket
		mreqn.imr_multiaddr = sock_addr.sin_addr;
		if( setsockopt(socket_fd, IPPROTO_IP, IP_ADD_MEMBERSHIP, &mreqn, sizeof(mreqn)) == -1) {
			perror("setsockopt2");
			return -1;
		}

		if ( (msg_size = recvfrom(socket_fd, (void *)msg, BUF_LEN, 0, (struct sockaddr *)&sock_addr,
						&len))== -1) {
			perror("Receive packet failed");
			exit(1);
		}

		printf("Cliente: %s\n", msg);


		data = strtok(msg, ":");

		sock_addr.sin_port = htons(atoi(argv[2]));
		inet_aton(data, &sock_addr.sin_addr);

		printf("cliente - servidor: %s porta: \n", data);

		data = strtok(NULL, ":");
		printf("%s\n", data);


		char msgp[BUF_LEN];
		bzero(msgp, BUF_LEN);


		if (strcmp(argv[3], REGISTER) == 0) {

			strcat(msgp, argv[3]);
			strcat(msgp, " ");
			strcat(msgp, argv[4]);
			strcat(msgp, " ");
			strcat(msgp, argv[4]);
			printf("%s\n",msgp);



		} else if (strcmp(argv[3], LOOKUP) == 0) {

		}
	}
	return 0;
}


int createSocket(char * ip, int port) {

}

void discover_service(int sock_fd) {
	int msg_size;
	char msg[BUF_LEN];


}

void recieve_packets() {

}

