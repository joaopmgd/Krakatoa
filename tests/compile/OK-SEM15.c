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


typedef enum {_enum_A_print, _enum_A_accept} _class_A_methods;

void _A_print( _class_A *this ){
   printf("%d",0);
}

void _A_accept( _class_A *this, _class_A *_x ){
   ( ( ( void (*)(_class_A * ) ) _x->vt[_enum_A_print] )( _x) );
}

Func VTclass_A[] = {
   ( void (*)() ) _A_print,
   ( void (*)() ) _A_accept
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


typedef enum {_enum_A_B_print, _enum_A_B_accept, _enum_B_m} _class_B_methods;

void _B_m( _class_B *this ){
   _A_accept( (_class_A *) this, (_class_A *) this);
}

Func VTclass_B[] = {
   ( void (*)() ) _A_print,
   ( void (*)() ) _A_accept,
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