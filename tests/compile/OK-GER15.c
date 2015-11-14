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
   int _A_i;
   int _A_j;
};

_class_A *new_A(void);


typedef enum {_enum_A_init_A, _enum_A_call_p, _enum_A_call_q, _enum_A_r, _enum_A_s} _class_A_methods;

void _A_p( _class_A *this ){
   printf("%d",this->_A_i);
}

void _A_q( _class_A *this ){
   printf("%d",this->_A_j);
}

void _A_init_A( _class_A *this ){
   this->_A_i = 1;
   this->_A_j = 2;
}

void _A_call_p( _class_A *this ){
   _A_p(this);
}

void _A_call_q( _class_A *this ){
   _A_q(this);
}

void _A_r( _class_A *this ){
   printf("%d",this->_A_i);
}

void _A_s( _class_A *this ){
   printf("%d",this->_A_j);
}

Func VTclass_A[] = {
   ( void (*)() ) _A_init_A, 
   ( void (*)() ) _A_call_p, 
   ( void (*)() ) _A_call_q, 
   ( void (*)() ) _A_r, 
   ( void (*)() ) _A_s
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
   int _B_i;
   int _B_j;
};

_class_B *new_B(void);


typedef enum {_enum_A_B_init_A, _enum_B_init_B, _enum_B_call_p, _enum_B_call_q, _enum_B_r, _enum_B_s} _class_B_methods;

void _B_p( _class_B *this ){
   printf("%d",this->_B_i);
}

void _B_q( _class_B *this ){
   printf("%d",this->_B_j);
}

void _B_init_B( _class_B *this ){
   this->_B_i = 3;
   this->_B_j = 4;
}

void _B_call_p( _class_B *this ){
   _B_p(this);
}

void _B_call_q( _class_B *this ){
   _B_q(this);
}

void _B_r( _class_B *this ){
   printf("%d",this->_B_i);
}

void _B_s( _class_B *this ){
   printf("%d",this->_B_j);
}

Func VTclass_B[] = {
   ( void (*)() ) _A_init_A, 
   ( void (*)() ) _B_init_B, 
   ( void (*)() ) _B_call_p, 
   ( void (*)() ) _B_call_q, 
   ( void (*)() ) _B_r, 
   ( void (*)() ) _B_s
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
   int _C_i;
   int _C_j;
};

_class_C *new_C(void);


typedef enum {_enum_A_C_init_A, _enum_C_init_C, _enum_C_call_p, _enum_C_call_q, _enum_C_r, _enum_C_s} _class_C_methods;

void _C_p( _class_C *this ){
   printf("%d",this->_C_i);
}

void _C_q( _class_C *this ){
   printf("%d",this->_C_j);
}

void _C_init_C( _class_C *this ){
   this->_C_i = 5;
   this->_C_j = 6;
}

void _C_call_p( _class_C *this ){
   _C_p(this);
}

void _C_call_q( _class_C *this ){
   _C_q(this);
}

void _C_r( _class_C *this ){
   printf("%d",this->_C_i);
}

void _C_s( _class_C *this ){
   printf("%d",this->_C_j);
}

Func VTclass_C[] = {
   ( void (*)() ) _A_init_A, 
   ( void (*)() ) _C_init_C, 
   ( void (*)() ) _C_call_p, 
   ( void (*)() ) _C_call_q, 
   ( void (*)() ) _C_r, 
   ( void (*)() ) _C_s
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
   puts("Ok-ger15");
   puts("The output should be :");
   puts("1 2 1 2 3 4 3 4 5 6 5 6");
   _a = new_A();
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_init_A] )( _a) );
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_call_p] )( _a) );
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_call_q] )( _a) );
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_r] )( _a) );
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_s] )( _a) );
   _b = new_B();
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_init_B] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_A_B_init_A] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_call_p] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_call_q] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_r] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_s] )( _b) );
   _c = new_C();
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_init_C] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_A_C_init_A] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_init_C] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_call_p] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_call_q] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_r] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_s] )( _c) );
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