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


typedef enum {_enum_A_put, _enum_A_get} _class_A_methods;

void _A_put( _class_A *this, int _pn ){
   this->_A_n = _pn;
}

int _A_get( _class_A *this ){
   return this->_A_n;
}

Func VTclass_A[] = {
   ( void (*)() ) _A_put,
   ( void (*)() ) _A_get
};

_class_A *new_A(){
   _class_A *t;
   if ( (t = malloc(sizeof(_class_A))) != NULL )
      t->vt = VTclass_A;
   return t;
}

typedef struct _class_No _class_No;

struct _class_No{
   Func *vt;
   _class_No* _No_next;
   _class_A* _No_a;
};

_class_No *new_No(void);


typedef enum {_enum_No_setNext, _enum_No_getNext, _enum_No_set, _enum_No_get} _class_No_methods;

void _No_setNext( _class_No *this, _class_No *_p_next ){
   this->_No_next = _p_next;
}

_class_No _No_getNext( _class_No *this ){
   return *this->_No_next;
}

void _No_set( _class_No *this, _class_A *_pa ){
   this->_No_a = _pa;
}

_class_A _No_get( _class_No *this ){
   return *this->_No_a;
}

Func VTclass_No[] = {
   ( void (*)() ) _No_setNext,
   ( void (*)() ) _No_getNext,
   ( void (*)() ) _No_set,
   ( void (*)() ) _No_get
};

_class_No *new_No(){
   _class_No *t;
   if ( (t = malloc(sizeof(_class_No))) != NULL )
      t->vt = VTclass_No;
   return t;
}

typedef struct _class_B _class_B;

struct _class_B{
   Func *vt;
   int _B_k;
   _class_No* _B_first;
};

_class_B *new_B(void);


typedef enum {_enum_A_B_put, _enum_A_B_get, _enum_B_get, _enum_B_init, _enum_B_buildList, _enum_B_list} _class_B_methods;

int _B_get( _class_B *this ){
   printf("%d",this->_B_k);
   return _A_get( (_class_A *) this);
}

void _B_init( _class_B *this, int _pk ){
   this->_B_k = _pk;
}

void _B_buildList( _class_B *this ){
   int _i;
   _class_No *_w;
   _class_No *_newNo;
   _class_A *_a;
   _i = ( ( ( int (*) (_class_B *) ) this->vt[_enum_B_get] )( (_class_B *) this ) );
   *this->_B_first = *new_No();
   _w = this->_B_first;
   ( ( ( void (*)(_class_No *, _class_No* ) ) _w->vt[_enum_No_setNext] )( _w, NULL) );
   _a = new_A();
   ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_put] )( _a, _i) );
   ( ( ( void (*)(_class_No *, _class_A* ) ) _w->vt[_enum_No_set] )( _w, _a) );
   while( true )
   {
      _i = _i - 1;
      if (_i <= 0)
      {
         break;
      }
      _newNo = new_No();
      ( ( ( void (*)(_class_No *, _class_No* ) ) _newNo->vt[_enum_No_setNext] )( _newNo, this->_B_first) );
      _a = new_A();
      ( ( ( void (*)(_class_A *, int ) ) _a->vt[_enum_A_put] )( _a, _i) );
      ( ( ( void (*)(_class_No *, _class_A* ) ) _newNo->vt[_enum_No_set] )( _newNo, _a) );
      this->_B_first = _newNo;
   }
}

void _B_list( _class_B *this ){
   _class_No *_w;
   _class_A *_a;
   _w = this->_B_first;
   while( _w != NULL )
   {
      _a = (_class_A *) ( ( ( _class_A * (*)(_class_No * ) ) _w->vt[_enum_No_get] )( _w) );
      printf("%d",( ( ( int (*)(_class_A * ) ) _a->vt[_enum_A_get] )( _a) ));
      _w = (_class_No *) ( ( ( _class_No * (*)(_class_No * ) ) _w->vt[_enum_No_getNext] )( _w) );
   }
}

Func VTclass_B[] = {
   ( void (*)() ) _A_put,
   ( void (*)() ) _A_get,
   ( void (*)() ) _B_get,
   ( void (*)() ) _B_init,
   ( void (*)() ) _B_buildList,
   ( void (*)() ) _B_list
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
   ( ( ( void (*)(_class_B *, int ) ) _b->vt[_enum_A_B_put] )( _b, 10) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_buildList] )( _b) );
   ( ( ( void (*)(_class_B * ) ) _b->vt[_enum_B_list] )( _b) );
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