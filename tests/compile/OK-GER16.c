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


typedef enum {_enum_A_get_A, _enum_A_set, _enum_A_print, _enum_A_init} _class_A_methods;

int _A_get_A( _class_A *this ){
   return this->_A_k;
}

void _A_set( _class_A *this, int _k ){
   this->_A_k = _k;
}

void _A_print( _class_A *this ){
   printf("%d",( ( ( int (*) (_class_A *) ) this->vt[_enum_A_get_A] )( (_class_A *) this ) ));
}

void _A_init( _class_A *this ){
   ( ( ( void (*) (_class_A *, int) ) this->vt[_enum_A_set] )( (_class_A *) this , 0) );
}

Func VTclass_A[] = {
   ( void (*)() ) _A_get_A,
   ( void (*)() ) _A_set,
   ( void (*)() ) _A_print,
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


typedef enum {_enum_A_B_get_A, _enum_A_B_set, _enum_A_B_print, _enum_A_B_init, _enum_B_get_B, _enum_B_init, _enum_B_print} _class_B_methods;

int _B_get_B( _class_B *this ){
   return this->_B_k;
}

void _B_init( _class_B *this ){
   _A_init( (_class_A *) this);
   this->_B_k = 2;
}

void _B_print( _class_B *this ){
   printf("%d",( ( ( int (*) (_class_B *) ) this->vt[_enum_B_get_B] )( (_class_B *) this ) ));
   printf("%d",( ( ( int (*) (_class_A *) ) this->vt[_enum_A_get_A] )( (_class_A *) this ) ));
   _A_print( (_class_A *) this);
}

Func VTclass_B[] = {
   ( void (*)() ) _A_get_A,
   ( void (*)() ) _A_set,
   ( void (*)() ) _A_print,
   ( void (*)() ) _A_init,
   ( void (*)() ) _B_get_B,
   ( void (*)() ) _B_init,
   ( void (*)() ) _B_print
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


typedef enum {_enum_A_C_get_A, _enum_A_C_set, _enum_A_C_print, _enum_A_C_init, _enum_C_get_A} _class_C_methods;

int _C_get_A( _class_C *this ){
   return 0;
}

Func VTclass_C[] = {
   ( void (*)() ) _A_get_A,
   ( void (*)() ) _A_set,
   ( void (*)() ) _A_print,
   ( void (*)() ) _A_init,
   ( void (*)() ) _C_get_A
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
   puts("");
   puts("Ok-ger16");
   puts("The output should be: ");
   puts("2 2 0 0 2 0 0 0 0 0 0");
   _b = new_B();
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_init] )( _b) );
   _c = new_C();
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_A_C_init] )( _c) );
   printf("%d",( ( ( int (*)(_class_B * ) ) _b->vt[_enum_B_get_B] )( _b) ));
   _a = (_class_A*)_b;
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_print] )( _a) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_print] )( _b) );
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_init] )( _a) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_init] )( _b) );
   printf("%d",( ( ( int (*)(_class_A * ) ) _a->vt[_enum_A_get_A] )( _a) ));
   printf("%d",( ( ( int (*)(_class_B * ) ) _b->vt[_enum_A_B_get_A] )( _b) ));
   _a = (_class_A*)_c;
   printf("%d",( ( ( int (*)(_class_A * ) ) _a->vt[_enum_A_get_A] )( _a) ));
   _c = new_C();
   printf("%d",( ( ( int (*)(_class_C * ) ) _c->vt[_enum_C_get_A] )( _c) ));
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