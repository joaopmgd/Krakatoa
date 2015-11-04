#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef int boolean;
#define true  1
#define false 0

typedef void (*Func)();

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   int _a, _b;
   boolean _e, _f;
   _a = 1;
   _b = 1;
   _e = true;
   _f = false;
   if ((((((((_a == _b   ) && (_a == 1   )) && (1 == _b   )) && (_a != _b   )) && (_e == _f   )) && (true == _f   )) && (_e != _f   )) && (true != _f   ))
      puts("impossivel");
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