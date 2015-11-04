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
};

_class_A *new_A(void);


typedef enum {_enum_A_m1, _enum_A_m2} _class_A_methods;

void _A_m1( _class_A *this ){
   printf("%d",2);
}

void _A_m2( _class_A *this, int _n ){
   printf("%d",_n);
   ( ( ( void (*) (_class_A *) ) this->vt[_enum_A_m1] )( (_class_A *) this ) );
}

Func VTclass_A[] = {
   ( void (*)() ) _A_m1,
   ( void (*)() ) _A_m2
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


typedef enum {_enum_A_B_m1, _enum_A_B_m2, _enum_B_m1} _class_B_methods;

void _B_m1( _class_B *this ){
   printf("%d",4);
}

Func VTclass_B[] = {
   ( void (*)() ) _A_m1,
   ( void (*)() ) _A_m2,
   ( void (*)() ) _B_m1
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
   _class_B *_b;
   puts("");
   puts("Ok-ger11");
   puts("The output should be :");
   puts("4 1 2 3 4");
   printf("%d",4);
   _a = new_A();
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_m2] )( _a, 1) );
   _a = (_class_A*)new_B();
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_m2] )( _a, 3) );
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