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


typedef enum {_enum_A_first} _class_A_methods;

void _A_first( _class_A *this, int _pn ){
}

Func VTclass_A[] = {
   ( void (*)() ) _A_first
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


typedef enum {_enum_A_B_first, _enum_B_second} _class_B_methods;

void _B_second( _class_B *this ){
}

Func VTclass_B[] = {
   ( void (*)() ) _A_first,
   ( void (*)() ) _B_second
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
};

_class_C *new_C(void);


typedef enum {_enum_A_C_first, _enum_B_C_second, _enum_C_third} _class_C_methods;

void _C_third( _class_C *this ){
}

Func VTclass_C[] = {
   ( void (*)() ) _A_first,
   ( void (*)() ) _B_second,
   ( void (*)() ) _C_third
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
   _class_A *_a;
   _class_B *_b;
   _class_C *_c;
   _a = new_A();
   _b = new_B();
   _c = new_C();
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_first] )( _a, 0) );
   ( ( ( void (*)(_class_B *, int ) ) _b->vt[_enum_A_B_first] )( _b, 0) );
   ( ( ( void (*)(_class_C *, int ) ) _c->vt[_enum_A_C_first] )( _c, 0) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_second] )( _b) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_B_C_second] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_third] )( _c) );
   _a = (_class_A*)_b;
   _a = (_class_A*)_c;
   _b = (_class_B*)_c;
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