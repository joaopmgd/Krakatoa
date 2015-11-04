#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef int boolean;
#define true  1
#define false 0

typedef void (*Func)();

typedef struct _class_A _class_A;

struct _class_A{
   Func *vt;
};

_class_A *new_A(void);


typedef enum {_enum_A_m} _class_A_methods;

void _A_m( _class_A *this ){
   printf("%d",6);
   printf("%d",1);
   printf("%d",1 + 1);
   printf("%d",4 - 1);
   printf("%d",(6 - 3   ) + 1);
   printf("%d",10 / 2);
   printf("%d",2 * 3);
}

Func VTclass_A[] = {
   ( void (*)() ) _A_m
};

_class_A *new_A(){
   _class_A *t;
   if ( (t = malloc(sizeof(_class_A))) != NULL )
      t->vt = VTclass_A;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   _class_A *_a;
   puts("");
   puts("Ok-ger02");
   puts("The output should be :");
   puts("6 1 2 3 4 5 6");
   _a = new_A();
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_m] )( _a) );
}

Func VTclass_Program[] = {
   ( void (*)() ) _Program_run
};

_class_Program *new_Program(){
   _class_Program *t;
   if ( (t = malloc(sizeof(_class_Program))) != NULL )
      t->vt = VTclass_Program;
   return t;
}

int main() {
   _class_Program *program;
   program = new_Program();
   ( ( void (*)(_class_Program *) ) program->vt[0] )(program);
   return 0;
}