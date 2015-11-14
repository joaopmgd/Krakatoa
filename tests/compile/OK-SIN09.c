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
   int _A_k;
};

_class_A *new_A(void);


typedef enum {_enum_A_m, _enum_A_init} _class_A_methods;

int _A_m1( _class_A *this ){
   return (this->_A_n) + 1;
}

void _A_m2( _class_A *this, int _pk ){
   this->_A_k = _pk;
}

int _A_m( _class_A *this ){
   _A_m2(this, 0);
   return (_A_m1(this)) + (this->_A_k);
}

void _A_init( _class_A *this ){
   this->_A_k = 1;
   this->_A_n = 0;
}

Func VTclass_A[] = {
   ( void (*)() ) _A_m, 
   ( void (*)() ) _A_init
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
   _a = new_A();
   printf("%d",( ( ( int (*)(_class_A * ) ) _a->vt[_enum_A_m] )( _a) ));
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