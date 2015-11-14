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


typedef enum {_enum_A_m1, _enum_A_m2, _enum_A_m3, _enum_A_m4} _class_A_methods;

int _A_m1( _class_A *this, boolean _ok ){
   return 0;
}

void _A_m2( _class_A *this ){
}

char * _A_m3( _class_A *this, char *_s, boolean _ok ){
   return "A";
}

char * _A_m4( _class_A *this, int _i, boolean _ok ){
   return "Am4";
}

Func VTclass_A[] = {
   ( void (*)() ) _A_m1, 
   ( void (*)() ) _A_m2, 
   ( void (*)() ) _A_m3, 
   ( void (*)() ) _A_m4
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


typedef enum {_enum_A_B_m3, _enum_A_B_m4, _enum_B_m1, _enum_B_m2, _enum_B_mB} _class_B_methods;

int _B_m1( _class_B *this, boolean _ok ){
   return 1;
}

void _B_m2( _class_B *this ){
}

int _B_mB( _class_B *this ){
   return 1;
}

Func VTclass_B[] = {
   ( void (*)() ) _A_m3, 
   ( void (*)() ) _A_m4, 
   ( void (*)() ) _B_m1, 
   ( void (*)() ) _B_m2, 
   ( void (*)() ) _B_mB
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


typedef enum {_enum_A_C_m3, _enum_B_C_m2, _enum_B_C_mB, _enum_C_m1, _enum_C_m4, _enum_C_m5} _class_C_methods;

int _C_m1( _class_C *this, boolean _ok ){
   return 2;
}

char * _C_m4( _class_C *this, int _i, boolean _ok ){
   return "C";
}

char * _C_m5( _class_C *this ){
   return "finally";
}

Func VTclass_C[] = {
   ( void (*)() ) _A_m3, 
   ( void (*)() ) _B_m2, 
   ( void (*)() ) _B_mB, 
   ( void (*)() ) _C_m1, 
   ( void (*)() ) _C_m4, 
   ( void (*)() ) _C_m5
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


typedef enum {_enum_A_D_m1, _enum_A_D_m2, _enum_A_D_m3, _enum_A_D_m4} _class_D_methods;

Func VTclass_D[] = {
   ( void (*)() ) _A_m1, 
   ( void (*)() ) _A_m2, 
   ( void (*)() ) _A_m3, 
   ( void (*)() ) _A_m4
};

_class_D *new_D(){
   _class_D *t;
   if ( (t = malloc(sizeof(_class_D))) != NULL )
      t->vt = VTclass_D;
   return t;
}

typedef struct _class_E _class_E;

struct _class_E{
   Func *vt;
};

_class_E *new_E(void);


typedef enum {_enum_A_E_m3, _enum_E_m1, _enum_E_m2, _enum_E_m4} _class_E_methods;

int _E_m1( _class_E *this, boolean _ok ){
   return 5;
}

void _E_m2( _class_E *this ){
}

char * _E_m4( _class_E *this, int _i, boolean _ok ){
   return "Em4";
}

Func VTclass_E[] = {
   ( void (*)() ) _A_m3, 
   ( void (*)() ) _E_m1, 
   ( void (*)() ) _E_m2, 
   ( void (*)() ) _E_m4
};

_class_E *new_E(){
   _class_E *t;
   if ( (t = malloc(sizeof(_class_E))) != NULL )
      t->vt = VTclass_E;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   _class_C *_c;
   _c = new_C();
   printf("%d",( ( ( int (*)(_class_C *, boolean ) ) _c->vt[_enum_C_m1] )( _c, true) ));
   ( ( ( void (*)(_class_C * ) ) _c->vt[_enum_B_C_m2] )( _c) );
   puts(( ( ( char * (*)(_class_C *, char *, boolean ) ) _c->vt[_enum_A_C_m3] )( _c, "ok", false) ));
   puts(( ( ( char * (*)(_class_C *, int, boolean ) ) _c->vt[_enum_C_m4] )( _c, 0, false) ));
   puts(( ( ( char * (*)(_class_C * ) ) _c->vt[_enum_C_m5] )( _c) ));
   printf("%d",( ( ( int (*)(_class_C * ) ) _c->vt[_enum_B_C_mB] )( _c) ) + 1);
   _class_B *_b;
   _b = new_B();
   printf("%d",( ( ( int (*)(_class_B *, boolean ) ) _b->vt[_enum_B_m1] )( _b, true) ));
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_m2] )( _b) );
   puts(( ( ( char * (*)(_class_B *, char *, boolean ) ) _b->vt[_enum_A_B_m3] )( _b, "ok", false) ));
   puts(( ( ( char * (*)(_class_B *, int, boolean ) ) _b->vt[_enum_A_B_m4] )( _b, 0, false) ));
   printf("%d",( ( ( int (*)(_class_C * ) ) _c->vt[_enum_B_C_mB] )( _c) ) + 1);
   _class_A *_a;
   _a = new_A();
   printf("%d",( ( ( int (*)(_class_A *, boolean ) ) _a->vt[_enum_A_m1] )( _a, true) ));
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_m2] )( _a) );
   puts(( ( ( char * (*)(_class_A *, char *, boolean ) ) _a->vt[_enum_A_m3] )( _a, "ok", false) ));
   puts(( ( ( char * (*)(_class_A *, int, boolean ) ) _a->vt[_enum_A_m4] )( _a, 0, false) ));
   _class_D *_d;
   _d = new_D();
   printf("%d",( ( ( int (*)(_class_D *, boolean ) ) _d->vt[_enum_A_D_m1] )( _d, true) ));
   ( ( ( void (*)(_class_D * ) ) _d->vt[_enum_A_D_m2] )( _d) );
   puts(( ( ( char * (*)(_class_D *, char *, boolean ) ) _d->vt[_enum_A_D_m3] )( _d, "ok", false) ));
   puts(( ( ( char * (*)(_class_D *, int, boolean ) ) _d->vt[_enum_A_D_m4] )( _d, 0, false) ));
   _class_E *_e;
   _e = new_E();
   printf("%d",( ( ( int (*)(_class_E *, boolean ) ) _e->vt[_enum_E_m1] )( _e, true) ));
   ( ( ( void (*)(_class_E * ) ) _e->vt[_enum_E_m2] )( _e) );
   puts(( ( ( char * (*)(_class_E *, char *, boolean ) ) _e->vt[_enum_A_E_m3] )( _e, "ok", false) ));
   puts(( ( ( char * (*)(_class_E *, int, boolean ) ) _e->vt[_enum_E_m4] )( _e, 0, false) ));
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