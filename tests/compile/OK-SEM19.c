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
   int _A_anInt;
   _class_A* _A_anA;
};

_class_A *new_A(void);

int _static_A_staticAnInt;
_class_A* _static_A_staticAnA;

typedef enum {_enum_A_getAnInt, _enum_A_setAnInt, _enum_A_getAnA, _enum_A_setAnA, _enum_A_method} _class_A_methods;

int _A_getAnInt( _class_A *this ){
   return this->_A_anInt;
}

void _A_setAnInt( _class_A *this, int _anInt ){
   this->_A_anInt = _anInt;
}

_class_A _A_getAnA( _class_A *this ){
   return *this->_A_anA;
}

void _A_setAnA( _class_A *this, _class_A *_anA ){
   this->_A_anA = _anA;
}

void _static_A_staticMethod( ){
   char __s[512];
   _class_A *_a;
   _a = new_A();
   _class_A *_b;
   _b = new_A();
   ( ( ( void (*)(_class_A *, int ) ) _b->vt[_enum_A_setAnInt] )( _b, 0) );
   ( ( ( void (*)(_class_A *, _class_A* ) ) _a->vt[_enum_A_setAnA] )( _a, _b) );
   _static_A_staticAnInt = ( ( ( int (*)(_class_A * ) ) _a->vt[_enum_A_getAnInt] )( _a) );
   _static_A_staticAnA = (_class_A *) ( ( ( _class_A * (*)(_class_A * ) ) _a->vt[_enum_A_getAnA] )( _a) );
   gets(__s);
   sscanf(__s,"%d",&_static_A_staticAnInt);
   ( ( ( void (*)( int ) ) _static_A_staticAnA->vt[_enum_A_setAnInt] )(1) );
   printf("%d",( ( ( int (*)(  ) ) _static_A_staticAnA->vt[_enum_A_getAnInt] )() ));
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_setAnInt] )( _a, 2) );
   _static_A_staticAnInt = _static_A_staticAnInt + 1;
}

void _A_method( _class_A *this ){
   this->_A_anInt = 5;
   *this->_A_anA = *new_A();
   ( ( ( void (*) (_class_A *, int) ) this->_A_anA->vt[_enum_A_setAnInt] )( (_class_A *) this , 6) );
   printf("%d",( ( ( int (*) (_class_A *) ) this->_A_anA->vt[_enum_A_getAnInt] )( (_class_A *) this ) ));
   _class_A *_b;
   _b = ( ( ( _class_A * (*) () ) this->_A_anA->vt[_enum_A_getAnA] )( (_class_A *) this ) );
   printf("%d",( ( ( int (*)(_class_A * ) ) _b->vt[_enum_A_getAnInt] )( _b) ));
}

Func VTclass_A[] = {
   ( void (*)() ) _A_getAnInt, 
   ( void (*)() ) _A_setAnInt, 
   ( void (*)() ) _A_getAnA, 
   ( void (*)() ) _A_setAnA, 
   ( void (*)() ) _A_method
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
   _class_A* _B_anAB;
};

_class_B *new_B(void);

int _static_B_staticAnInt;

typedef enum {_enum_A_B_getAnInt, _enum_A_B_setAnInt, _enum_A_B_getAnA, _enum_A_B_setAnA, _enum_B_method} _class_B_methods;

void _static_B_staticMethod( ){
   _static_B_staticAnInt = 10;
}

void _B_method( _class_B *this ){
   *this->_B_anAB = *new_A();
   ( ( ( void (*) (_class_A *, int) ) this->_B_anAB->vt[_enum_A_setAnInt] )( (_class_A *) this , 11) );
}

int _static_B_getAnIntB( ){
   return _static_B_staticAnInt;
}

Func VTclass_B[] = {
   ( void (*)() ) _A_getAnInt, 
   ( void (*)() ) _A_setAnInt, 
   ( void (*)() ) _A_getAnA, 
   ( void (*)() ) _A_setAnA, 
   ( void (*)() ) _B_method
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
   _class_A *_a;
   _a = new_A();
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_method] )( _a) );
   _static_A_staticMethod();
   _class_B *_b;
   _b = new_B();
   _static_B_staticMethod();
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_method] )( _b) );
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