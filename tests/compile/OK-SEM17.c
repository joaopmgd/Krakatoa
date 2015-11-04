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

int _static_A_n;

typedef enum {_enum_A_get, _enum_A_set} _class_A_methods;

int _static_A_get( ){
   return _static_A_n;
}

int _A_get( _class_A *this ){
   return this->_A_n;
}

void _static_A_set(int _n ){
   _static_A_n = _n;
}

void _A_set( _class_A *this, int _n ){
   this->_A_n = _n;
}

Func VTclass_A[] = {
   ( void (*)() ) _A_get,
   ( void (*)() ) _A_set
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
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_set] )( _a, 0) );
   printf("%d",( ( ( int (*)(_class_A * ) ) _a->vt[_enum_A_get] )( _a) ));
   _static_A_set(1);
   printf("%d",_static_A_get());
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