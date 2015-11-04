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
};

_class_A *new_A(void);


typedef enum {_enum_A_put, _enum_A_get, _enum_A_set} _class_A_methods;

void _A_put( _class_A *this, int _x, int _y, boolean _ok ){
   if ((_x > _y   ) && _ok)
   {
      this->_A_i = 0;
   }
}

int _A_get( _class_A *this ){
   return this->_A_i;
}

void _A_set( _class_A *this, int _i ){
   this->_A_i = _i;
}

Func VTclass_A[] = {
   ( void (*)() ) _A_put,
   ( void (*)() ) _A_get,
   ( void (*)() ) _A_set
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


typedef enum {_enum_A_B_put, _enum_A_B_get, _enum_A_B_set, _enum_B_put} _class_B_methods;

void _B_put( _class_B *this, int _a, int _b, boolean _c ){
   if (((_a + _b   ) < 1   ) && !_c)
   {
      printf("%d",0);
   }
}

Func VTclass_B[] = {
   ( void (*)() ) _A_put,
   ( void (*)() ) _A_get,
   ( void (*)() ) _A_set,
   ( void (*)() ) _B_put
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
   ( ( ( void (*)(_class_B *, int, int, boolean ) ) _b->vt[_enum_B_put] )( _b, 1, 2, true) );
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