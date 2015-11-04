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


typedef enum {_enum_A_run, _enum_A_m} _class_A_methods;

void _A_run( _class_A *this ){
}

void _A_m( _class_A *this ){
   _class_A *_a;
   _a = NULL;
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_m] )( _a) );
}

Func VTclass_A[] = {
   ( void (*)() ) _A_run,
   ( void (*)() ) _A_m
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


typedef enum {_enum_B_run, _enum_B_m} _class_B_methods;

int _B_run( _class_B *this, int _n ){
   return _n;
}

void _B_m( _class_B *this ){
}

Func VTclass_B[] = {
   ( void (*)() ) _B_run,
   ( void (*)() ) _B_m
};

_class_B *new_B(){
   _class_B *t;
   if ( (t = malloc(sizeof(_class_B))) != NULL )
      t->vt = VTclass_B;
   return t;
}

typedef struct _class_C _class_C;

struct _class_C{
   Func *vt;
   _class_B* _C_b;
};

_class_C *new_C(void);


typedef enum {_enum_C_m, _enum_C_get} _class_C_methods;

void _C_m( _class_C *this ){
   ( ( ( void (*) (_class_B *) ) this->_C_b->vt[_enum_B_m] )( (_class_B *) this ) );
}

_class_B _C_get( _class_C *this ){
   return *this->_C_b;
}

Func VTclass_C[] = {
   ( void (*)() ) _C_m,
   ( void (*)() ) _C_get
};

_class_C *new_C(){
   _class_C *t;
   if ( (t = malloc(sizeof(_class_C))) != NULL )
      t->vt = VTclass_C;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   _class_C *_c;
   _c = NULL;
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_m] )( _c) );
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