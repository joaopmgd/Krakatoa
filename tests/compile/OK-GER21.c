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

void _A_set( _class_A *this, int _n ){
   this->_A_n = _n;
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

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
   _class_A* _Program_a;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_print, _enum_Program_get, _enum_Program_run} _class_Program_methods;

void _Program_set( _class_Program *this, _class_A *_a ){
   this->_Program_a = _a;
}

void _Program_print( _class_Program *this ){
   printf("%d",( ( ( int (*) (_class_A *) ) this->_Program_a->vt[_enum_A_get] )( (_class_A *) this ) ));
}

_class_A _Program_get( _class_Program *this ){
   return *this->_Program_a;
}

void _Program_run( _class_Program *this ){
   puts("");
   puts("Ok-ger21");
   puts("The output should be :");
   puts("0");
   puts("0");
}

Func VTclass_Program[] = {
   ( void (*)() ) _Program_print,
   ( void (*)() ) _Program_get,
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