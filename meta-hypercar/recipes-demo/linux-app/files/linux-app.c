#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>   /* for setitimer */
#include <unistd.h>     /* for pause */
#include <signal.h>     /* for signal */
#include <sys/ioctl.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <ctype.h>
#include <errno.h>
#define INTERVAL 1000   /* number of milliseconds to go off */
#define DOMNUM 10
#define MS2S(x) x / 1000
#define MS2US(x) (x * 1000) % 1000000

/* global 8-bit counter */
/* This counter must be somehow communicated to RTOS! */
int *counter;
int domain = -1;

/* function prototype */
void increase_counter(void);

#ifndef MAP_FAILED
#define MAP_FAILED ((void*)-1)
#endif

static int _shmem_fd = -1;

int shmem_open(void){
  _shmem_fd = open("/dev/xen_mem", 0);
  return _shmem_fd;
}

void *shmem_mmap(size_t length){
  int fd;
  if (ioctl(_shmem_fd, 0, &fd) < 0){
    return MAP_FAILED;
  }
  return mmap(NULL, length, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
}



int main(int argc, char *argv[]) {
  char *endptr;
  struct itimerval it_val;  /* for setting itimer */
  if (argc!=2){
    perror("Usage: ./hypercar-app <domain-id>");
    exit(EXIT_FAILURE);
  }
  errno = 0;
  domain = strtol(argv[1], &endptr, 10);
  if (errno != 0){
    perror("Domain ID must be an integer [0-9]");
    exit(EXIT_FAILURE);
  };
  if (domain > 9 || domain < 0){
    perror("Domain ID must be in the range 0..9!");
    exit(EXIT_FAILURE);
  };

  if (shmem_open() == -1){
    perror("Failed to open shared memory device /dev/xen_mem");
  }
  if ((counter = shmem_mmap(10*sizeof(counter))) == MAP_FAILED){
    perror("Failed to map shared memory counter");
  }
  printf("Shared memory mapped to %p\n", counter);
  for (int i=0; i<domain; i++){
    printf("Moving pointer %p \n", counter);
    counter++;
  }
  printf("Current domain pointer %p\n ", counter);
  //  for (int i=0; i<domain; i++){
  //  counter++;
  //}
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
  *counter = 0;
  printf("Domain %d: 8-bit counter started from 0. It will never stop.\n", domain);
  while (1) { 
     pause();
  }
}

void increase_counter(void) 
{
  (*counter)++;
  printf("Domain %d Counter increased to %d.\n", domain, *counter);
  /* Inject an error at value 100 */
  if (*counter > 100) {
    (*counter)++;
  }
}
