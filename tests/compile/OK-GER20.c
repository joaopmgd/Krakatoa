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
   _class_A* _A_myself;
   int _A_n;
};

_class_A *new_A(void);

_class_A* _static_A_staticMyself;

typedef enum {_enum_A_get, _enum_A_set, _enum_A_setInt, _enum_A_getInt} _class_A_methods;

_class_A _static_A_get( ){
   return *_static_A_staticMyself;
}

_class_A _A_get( _class_A *this ){
   return *this->_A_myself;
}

void _static_A_set(_class_A *_myself ){
   _static_A_staticMyself = _myself;
}

void _A_set( _class_A *this, _class_A *_myself ){
   this->_A_myself = _myself;
}

void _A_setInt( _class_A *this, int _n ){
   this->_A_n = _n;
}

int _A_getInt( _class_A *this ){
   return this->_A_n;
}

Func VTclass_A[] = {
   ( void (*)() ) _A_get,
   ( void (*)() ) _A_set,
   ( void (*)() ) _A_setInt,
   ( void (*)() ) _A_getInt
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
   _class_A *_a, *_bInstance, *_bStatic;
   puts("");
   puts("Ok-ger20");
   puts("The output should be: ");
   puts("0 1 2");
   _a = new_A();
   _bInstance = new_A();
   _bStatic = new_A();
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_setInt] )( _a, 0) );
   ( ( ( void (*)(_class_A *, int ) ) _bInstance->vt[_enum_A_setInt] )( _bInstance, 1) );
   ( ( ( void (*)(_class_A *, int ) ) _bStatic->vt[_enum_A_setInt] )( _bStatic, 2) );
   ( ( ( void (*)(_class_A *, _class_A* ) ) _a->vt[_enum_A_set] )( _a, _bInstance) );
   _static_A_set(_bStatic);
   printf("%d",( ( ( int (*)(_class_A * ) ) _a->vt[_enum_A_getInt] )( _a) ));
   _class_A *_c;
   _c = (_class_A *) ( ( ( _class_A * (*)(_class_A * ) ) _a->vt[_enum_A_get] )( _a) );
   printf("%d",( ( ( int (*)(_class_A * ) ) _c->vt[_enum_A_getInt] )( _c) ));
   *_c = _static_A_get();
   printf("%d",( ( ( int (*)(_class_A * ) ) _c->vt[_enum_A_getInt] )( _c) ));
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