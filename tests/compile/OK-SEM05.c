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


typedef enum {_enum_A_set, _enum_A_get} _class_A_methods;

void _A_set( _class_A *this, int _pn ){
   this->_A_n = _pn;
}

int _A_get( _class_A *this ){
   return this->_A_n;
}

Func VTclass_A[] = {
   ( void (*)() ) _A_set, 
   ( void (*)() ) _A_get
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


typedef enum {_enum_A_B_get, _enum_B_set} _class_B_methods;

void _B_set( _class_B *this, int _pn ){
   printf("%d",_pn);
   _A_set( (_class_A *) this, _pn);
}

Func VTclass_B[] = {
   ( void (*)() ) _A_get, 
   ( void (*)() ) _B_set
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


typedef enum {_enum_Program_m, _enum_Program_p, _enum_Program_run} _class_Program_methods;

_class_B _Program_m( _class_Program *this, _class_A *_a ){
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_set] )( _a, 0) );
   return *new_B();
}

_class_A _Program_p( _class_Program *this, int _i ){
   if (_i > 0)
   {
      return *new_A();
   }
   else
      return *(_class_A*)new_B();
}

void _Program_run( _class_Program *this ){
   _class_A *_a;
   _class_B *_b;
   _a = new_A();
   _b = new_B();
   _a = (_class_A*)_b;
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_set] )( _a, 0) );
   _a = (_class_A *) ( ( ( _class_B * (*) (_class_Program *, _class_A*) ) this->vt[_enum_Program_m] )( (_class_Program *) this , _a) );
   _b = ( ( ( _class_B * (*) (_class_Program *, _class_B*) ) this->vt[_enum_Program_m] )( (_class_Program *) this , _b) );
   _a = ( ( ( _class_A * (*) (_class_Program *, int) ) this->vt[_enum_Program_p] )( (_class_Program *) this , 0) );
}

Func VTclass_Program[] = {
   ( void (*)() ) _Program_m, 
   ( void (*)() ) _Program_p, 
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