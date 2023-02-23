#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>   /* for setitimer */
#include <unistd.h>     /* for pause */
#include <signal.h>     /* for signal */

#define INTERVAL 1000   /* number of milliseconds to go off */

#define MS2S(x) x / 1000
#define MS2US(x) (x * 1000) % 1000000

/* global 8-bit counter */
/* This counter must be somehow communicated to RTOS! */
unsigned char counter = 0;
char *domain = '\0';

/* function prototype */
void increase_counter(void);

int main(int argc, char *argv[]) {

  struct itimerval it_val;  /* for setting itimer */
  if (argc!=2){
    perror("Usage: ./hypercar-app <domain-id>");
    exit(1);
  }
  
  domain = argv[1];
  /* Upon SIGALRM, increase counter by */
  if (signal(SIGALRM, (void (*)(int)) increase_counter) == SIG_ERR) {
    perror("Unable to catch SIGALRM");
    exit(1);
  }
  it_val.it_value.tv_sec = MS2S(INTERVAL);
  it_val.it_value.tv_usec = MS2US(INTERVAL);   
  it_val.it_interval = it_val.it_value;
  if (setitimer(ITIMER_REAL, &it_val, NULL) == -1) {
    perror("error calling setitimer()");
    exit(1);
  }
  printf("%s: 8-bit counter started from 0. It will never stop.\n", domain);
  while (1) { 
     pause();
  }
}

void increase_counter(void) 
{
  counter++;
  printf("%s Counter increased to %d.\n", domain, counter);
  /* Inject an error at value 100 */
  if (counter > 100) {
    counter++;
  }
}
