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


int _static_A_get0( ){
   return 0;
}

int _static_A_get1( ){
   return 1;
}

int _static_A_ident(int _n ){
   return _n;
}

Func VTclass_A[] = {

};

_class_A *new_A(){
   _class_A *t;
   if ( (t = malloc(sizeof(_class_A))) != NULL )
      t->vt = VTclass_A;
   return t;
}

typedef struct _class_B _class_B;

struct _class_B{
   Func *vt;
};

_class_B *new_B(void);


int _static_B_get( ){
   return 3;
}

void _static_B_print( ){
   printf("%d",_static_A_ident(2));
   printf("%d",_static_B_get());
   printf("%d",4);
}

Func VTclass_B[] = {

};

_class_B *new_B(){
   _class_B *t;
   if ( (t = malloc(sizeof(_class_B))) != NULL )
      t->vt = VTclass_B;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   puts("");
   puts("Ok-ger17");
   puts("The output should be: ");
   puts("0 1 2 3 4");
   printf("%d",_static_A_get0());
   printf("%d",_static_A_get1());
   _static_B_print();
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