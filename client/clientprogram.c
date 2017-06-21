#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>

#define PORT 9999

static void showHelp();

int main(int argc, char** argv)
{
	struct sockaddr_in address;
	int sock = 0, valread;
	struct sockaddr_in serv_addr;
	char cmd[1024];
	char buffer[1024] = {0};
	int i;

	printf("ClientProgram!\n");

	if(argc < 2) {
		showHelp();
		return -1;
	}

	if((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) 
	{
		printf("\n Socket creation error \n");
		return -1;
	}
	
	memset(&serv_addr, '0', sizeof(serv_addr));
	
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(PORT);

	if(inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr) <= 0)
	{
		printf("\n Invalid address/ Address not supported \n");
		return -1;
	}

	if(connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0 ) 
	{
		printf("\n Connection Failed \n");
		return -1;
	}
	printf("Connection Ok\n");

	//while(1) 
	{		
		//fgets(cmd, sizeof(cmd), stdin);
		//send(sock, cmd, strlen(cmd), 0);
		strcpy(cmd, argv[1]);
		for(i = 2; i < argc; i++)
		{
			strcat(cmd, " ");
			strcat(cmd, argv[i]);
		}
		strcat(cmd, "\n");
		send(sock, cmd, strlen(cmd), 0);

		valread = read(sock, buffer, 1024);
		//if(strncmp(buffer, "OK",2) == 0) {
		//	break;
		//}
		printf("%s", buffer);
		return atoi(buffer);
	}
	printf("ClientProgram end!\n");
	return -1;
}

void showHelp()
{
	printf("ClientProgram \
	\t -CNF port 0 \n	\
  	\t -RDG 0x44 0x60 \n \
	\t -WRG 0x44 0x60 0xAC \n \
	\n  \
	\t -RCA 0x65 \n \
	\t -RCF PGEN_RED \n \
	\t -WCA 0x65 1 \n \
	\t -WCF PGEN_RED 1 \n \
	\n \
	\t -RPA 50 0xA6 \n \
	\t -RPF 50 LED_CON_R \n \
	\t -WPA 50 0xA6 0x1f \n \
	\t -WPF 50 LED_CON_B 0x1f \n \
	\n \
	\t -RIO 2 3 \n \
	\t -WIO 2 3 1 \n \
	");

}
