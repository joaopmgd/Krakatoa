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

int _static_A_n;

typedef enum {_enum_A_print} _class_A_methods;

int _static_A_get( ){
   return _static_A_n;
}

void _static_A_set(int _n ){
   _static_A_n = _n;
}

void _A_print( _class_A *this ){
   _static_A_set(2);
   printf("%d",_static_A_get());
}

Func VTclass_A[] = {
   ( void (*)() ) _A_print
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


typedef enum {_enum_B_m} _class_B_methods;

void _static_B_print( ){
   _class_A *_a;
   _a = new_A();
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_print] )( _a) );
}

void _B_m( _class_B *this ){
   _static_A_set(1);
   printf("%d",_static_A_get());
   _static_B_print();
}

Func VTclass_B[] = {
   ( void (*)() ) _B_m
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
   _class_B *_b;
   puts("");
   puts("Ok-ger18");
   puts("The output should be: ");
   puts("0 1 2");
   _static_A_set(0);
   printf("%d",_static_A_get());
   _b = new_B();
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_m] )( _b) );
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