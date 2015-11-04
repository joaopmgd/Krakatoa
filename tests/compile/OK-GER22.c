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


typedef enum {_enum_A_f, _enum_A_m} _class_A_methods;

int _A_f( _class_A *this ){
   return 0;
}

void _A_m( _class_A *this ){
   printf("%d",( ( ( int (*) (_class_A *) ) this->vt[_enum_A_f] )( (_class_A *) this ) ));
}

Func VTclass_A[] = {
   ( void (*)() ) _A_f,
   ( void (*)() ) _A_m
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


typedef enum {_enum_A_B_f, _enum_A_B_m, _enum_B_g, _enum_B_p, _enum_B_r, _enum_B_f} _class_B_methods;

int _B_g( _class_B *this ){
   return 1;
}

void _B_p( _class_B *this ){
   printf("%d",(( ( ( int (*) (_class_B *) ) this->vt[_enum_B_g] )( (_class_B *) this ) )) + (( ( ( int (*) (_class_A *) ) this->vt[_enum_A_f] )( (_class_A *) this ) )));
}

void _B_r( _class_B *this ){
   printf("%d",2);
}

int _B_f( _class_B *this ){
   return 10;
}

Func VTclass_B[] = {
   ( void (*)() ) _A_f,
   ( void (*)() ) _A_m,
   ( void (*)() ) _B_g,
   ( void (*)() ) _B_p,
   ( void (*)() ) _B_r,
   ( void (*)() ) _B_f
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


typedef enum {_enum_A_C_f, _enum_A_C_m, _enum_B_C_g, _enum_B_C_p, _enum_B_C_r, _enum_B_C_f, _enum_C_f, _enum_C_g, _enum_C_r} _class_C_methods;

int _C_f( _class_C *this ){
   return 20;
}

int _C_g( _class_C *this ){
   return 101;
}

void _C_r( _class_C *this ){
   printf("%d",200);
}

Func VTclass_C[] = {
   ( void (*)() ) _A_f,
   ( void (*)() ) _A_m,
   ( void (*)() ) _B_g,
   ( void (*)() ) _B_p,
   ( void (*)() ) _B_r,
   ( void (*)() ) _B_f,
   ( void (*)() ) _C_f,
   ( void (*)() ) _C_g,
   ( void (*)() ) _C_r
};

_class_C *new_C(){
   _class_C *t;
   if ( (t = malloc(sizeof(_class_C))) != NULL )
      t->vt = VTclass_C;
   return t;
}

typedef struct _class_D _class_D;

struct _class_D{
   Func *vt;
};

_class_D *new_D(void);


typedef enum {_enum_A_D_f, _enum_A_D_m} _class_D_methods;

Func VTclass_D[] = {
   ( void (*)() ) _A_f,
   ( void (*)() ) _A_m
};

_class_D *new_D(){
   _class_D *t;
   if ( (t = malloc(sizeof(_class_D))) != NULL )
      t->vt = VTclass_D;
   return t;
}

typedef struct _class_F _class_F;

struct _class_F{
   Func *vt;
};

_class_F *new_F(void);


typedef enum {_enum_F_f, _enum_F_m} _class_F_methods;

int _F_f( _class_F *this ){
   return 3;
}

void _F_m( _class_F *this ){
   printf("%d",( ( ( int (*) (_class_F *) ) this->vt[_enum_F_f] )( (_class_F *) this ) ));
}

Func VTclass_F[] = {
   ( void (*)() ) _F_f,
   ( void (*)() ) _F_m
};

_class_F *new_F(){
   _class_F *t;
   if ( (t = malloc(sizeof(_class_F))) != NULL )
      t->vt = VTclass_F;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   puts("");
   puts("Ok-ger22");
   puts("The output should be: ");
   puts("0 10 11 2 20 121 200 20 121 200 0 0 3");
   _class_A *_a;
   _a = new_A();
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_m] )( _a) );
   _class_B *_b;
   _b = new_B();
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_A_B_m] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_p] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_r] )( _b) );
   _class_C *_c;
   _c = new_C();
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_A_C_m] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_B_C_p] )( _c) );
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_C_r] )( _c) );
   _b = (_class_B*)_c;
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_A_B_m] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_p] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_r] )( _b) );
   _class_D *_d;
   _d = new_D();
   ( ( ( void (*)(_class_D * ) ) _d->vt[_enum_A_D_m] )( _d) );
   _a = (_class_A*)_d;
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_m] )( _a) );
   _class_F *_f;
   _f = new_F();
   ( ( ( void (*)(_class_F * ) ) _f->vt[_enum_F_m] )( _f) );
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