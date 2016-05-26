#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <assert.h>

#define N 100000

void swap(int *xp, int *yp) {
  int temp = *xp;
  *xp = *yp;
  *yp = temp;
}

// A function to implement bubble sort
void bubbleSort(int arr[], int n) {
  int i, j;
  for (i = 0; i < n-1; i++)      
    for (j = 0; j < n-i-1; j++)
      if (arr[j] > arr[j+1])
	swap(&arr[j], &arr[j+1]);
}

int isSorted(int *a, int size) {
  int i;
  for (i = 0; i < size - 1; i++) {
    if (a[i] > a[i + 1]) {
      return 0;
    }
  }
  return 1;
}

// Function to print an array
void printArray(int arr[], int size)
{
  int i;
  for (i=0; i < size; i++)
    printf("%d ", arr[i]);
  printf("\n");
}

int* merge(const int arr[], int count, int s){
    int groups = count/s;
    int* out = malloc(sizeof(int) * count);
    int ptr[groups];
    int index = 0;
    int i;
    memset(ptr, 0, sizeof(int) * groups);

    while(1){
        int target_group = -1;
        int target_min = -1;
        for(i = 0; i < groups; i++){
            if(ptr[i] >= s){
                continue;
            }
            int cur_item_id = (i * s) + ptr[i];
            if(target_group == -1 || arr[cur_item_id] < target_min){
                target_group = i;
                target_min = arr[cur_item_id];
            }
        }
        if(target_group == -1){
            break;
        }
        ptr[target_group]++;
        out[index++] = target_min;
    }

    assert(index == count);

    return out;
}


int main(int argc, char** argv) {
	int i, n, id, size, step;
	int* A;
	clock_t start, end;
	double elapsed_time, t1, t2;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD,&id);
	MPI_Comm_size(MPI_COMM_WORLD,&size);

	int s = N / size;

	if(id == 0){
		t1 = MPI_Wtime();
		A = (int *)malloc(sizeof(int)*N);
		if (A == NULL) {
			printf("Fail to malloc\n");
			exit(0);
		}
		for (i=N-1; i>=0; i--)
			A[N-1-i] = i;

		if (isSorted(A, N)) {
            printf("Array is sorted\n");
        } else {
            printf("Array is NOT sorted\n");
        }

        for(i = 1; i < size; i++) {
            MPI_Send(&A[i*s], s, MPI_INT, i, 1, MPI_COMM_WORLD);
        }
        bubbleSort(A, s);

        for(i = 1; i < size; i++) {
            MPI_Recv(&A[i*s], s, MPI_INT, i, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        int* A2 = merge(A, N, s);

        printArray(&A2[N-10], 10);
	
		if (isSorted(A, N))
		  printf("Array is sorted\n");
		else
		  printf("Array is NOT sorted\n");
		 
		free(A2);

		t2 = MPI_Wtime();
		printf( "Elapsed time MPI_Wtime is %f\n", t2 - t1 ); 

	} else {
		int A[s];
        t1 = MPI_Wtime();

        MPI_Recv(A, s, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        bubbleSort(A, s);
        t2 = MPI_Wtime();

        MPI_Send(&A, s, MPI_INT, 0, 2, MPI_COMM_WORLD);
	}
	MPI_Finalize();
	return 0;
}