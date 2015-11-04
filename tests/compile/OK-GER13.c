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
   int _A_n;
};

_class_A *new_A(void);


typedef enum {_enum_A_get, _enum_A_set, _enum_A_m1} _class_A_methods;

int _A_get( _class_A *this ){
   return this->_A_n;
}

void _A_set( _class_A *this, int _n ){
   this->_A_n = _n;
}

void _A_m1( _class_A *this ){
   printf("%d",this->_A_n);
}

Func VTclass_A[] = {
   ( void (*)() ) _A_get,
   ( void (*)() ) _A_set,
   ( void (*)() ) _A_m1
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


typedef enum {_enum_A_B_get, _enum_A_B_set, _enum_A_B_m1, _enum_B_m2} _class_B_methods;

void _B_m2( _class_B *this ){
}

Func VTclass_B[] = {
   ( void (*)() ) _A_get,
   ( void (*)() ) _A_set,
   ( void (*)() ) _A_m1,
   ( void (*)() ) _B_m2
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


typedef enum {_enum_A_C_get, _enum_A_C_set, _enum_A_C_m1, _enum_B_C_m2, _enum_C_m1, _enum_C_teste} _class_C_methods;

void _C_m1( _class_C *this ){
   printf("%d",8);
}

void _C_teste( _class_C *this ){
   _A_m1( (_class_A *) this);
}

Func VTclass_C[] = {
   ( void (*)() ) _A_get,
   ( void (*)() ) _A_set,
   ( void (*)() ) _A_m1,
   ( void (*)() ) _B_m2,
   ( void (*)() ) _C_m1,
   ( void (*)() ) _C_teste
};

_class_C *new_C(){
   _class_C *t;
   if ( (t = malloc(sizeof(_class_C))) != NULL )
      t->vt = VTclass_C;
   return t;
}

typedef struct _class_D _class_D;

struct _class_D{
   Func *vt;
};

_class_D *new_D(void);


typedef enum {_enum_A_D_get, _enum_A_D_set, _enum_A_D_m1, _enum_B_D_m2, _enum_C_D_m1, _enum_C_D_teste, _enum_D_m1} _class_D_methods;

void _D_m1( _class_D *this ){
   printf("%d",9);
}

Func VTclass_D[] = {
   ( void (*)() ) _A_get,
   ( void (*)() ) _A_set,
   ( void (*)() ) _A_m1,
   ( void (*)() ) _B_m2,
   ( void (*)() ) _C_m1,
   ( void (*)() ) _C_teste,
   ( void (*)() ) _D_m1
};

_class_D *new_D(){
   _class_D *t;
   if ( (t = malloc(sizeof(_class_D))) != NULL )
      t->vt = VTclass_D;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   _class_D *_d;
   puts("");
   puts("Ok-ger09");
   puts("The output should be :");
   puts("0");
   _d = new_D();
   ( ( ( void (*)(_class_D *, int ) ) _d->vt[_enum_A_D_set] )( _d, 0) );
   ( ( ( void (*)(_class_D * ) ) _d->vt[_enum_C_D_teste] )( _d) );
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