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
   int _A_k;
};

_class_A *new_A(void);


typedef enum {_enum_A_get_A, _enum_A_init} _class_A_methods;

int _A_get_A( _class_A *this ){
   return this->_A_k;
}

void _A_init( _class_A *this ){
   this->_A_k = 1;
}

Func VTclass_A[] = {
   ( void (*)() ) _A_get_A,
   ( void (*)() ) _A_init
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
   int _B_k;
};

_class_B *new_B(void);


typedef enum {_enum_A_B_get_A, _enum_A_B_init, _enum_B_get_B, _enum_B_init} _class_B_methods;

int _B_get_B( _class_B *this ){
   return this->_B_k;
}

void _B_init( _class_B *this ){
   _A_init( (_class_A *) this);
   this->_B_k = 2;
}

Func VTclass_B[] = {
   ( void (*)() ) _A_get_A,
   ( void (*)() ) _A_init,
   ( void (*)() ) _B_get_B,
   ( void (*)() ) _B_init
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
   int _C_k;
};

_class_C *new_C(void);


typedef enum {_enum_A_C_get_A, _enum_A_C_init, _enum_B_C_get_B, _enum_B_C_init, _enum_C_get_C, _enum_C_init} _class_C_methods;

int _C_get_C( _class_C *this ){
   return this->_C_k;
}

void _C_init( _class_C *this ){
   _B_init( (_class_B *) this);
   this->_C_k = 3;
}

Func VTclass_C[] = {
   ( void (*)() ) _A_get_A,
   ( void (*)() ) _A_init,
   ( void (*)() ) _B_get_B,
   ( void (*)() ) _B_init,
   ( void (*)() ) _C_get_C,
   ( void (*)() ) _C_init
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
   int _D_k;
};

_class_D *new_D(void);


typedef enum {_enum_A_D_get_A, _enum_A_D_init, _enum_B_D_get_B, _enum_B_D_init, _enum_C_D_get_C, _enum_C_D_init, _enum_D_get_D, _enum_D_init} _class_D_methods;

int _D_get_D( _class_D *this ){
   return this->_D_k;
}

void _D_init( _class_D *this ){
   _C_init( (_class_C *) this);
   this->_D_k = 4;
}

Func VTclass_D[] = {
   ( void (*)() ) _A_get_A,
   ( void (*)() ) _A_init,
   ( void (*)() ) _B_get_B,
   ( void (*)() ) _B_init,
   ( void (*)() ) _C_get_C,
   ( void (*)() ) _C_init,
   ( void (*)() ) _D_get_D,
   ( void (*)() ) _D_init
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
   _class_A *_a;
   _class_B *_b;
   _class_C *_c;
   _class_D *_d;
   puts("");
   puts("Ok-ger14");
   puts("The output should be :");
   puts("4 3 2 1");
   _d = new_D();
   ( ( ( void (*)(_class_D * ) ) _d->vt[_enum_D_init] )( _d) );
   printf("%d",( ( ( int (*)(_class_D * ) ) _d->vt[_enum_D_get_D] )( _d) ));
   _c = (_class_C*)_d;
   printf("%d",( ( ( int (*)(_class_C * ) ) _c->vt[_enum_C_get_C] )( _c) ));
   _b = (_class_B*)_c;
   printf("%d",( ( ( int (*)(_class_B * ) ) _b->vt[_enum_B_get_B] )( _b) ));
   _a = (_class_A*)_b;
   printf("%d",( ( ( int (*)(_class_A * ) ) _a->vt[_enum_A_get_A] )( _a) ));
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